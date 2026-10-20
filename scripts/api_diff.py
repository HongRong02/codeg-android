#!/usr/bin/env python3
"""Compare the codeg server's HTTP routes with the endpoints this app calls.

Reads the server router (default ../codeg/src-tauri/src/web/router.rs) and
every `send("<name>"` call in the app sources, then writes
.taskmaster/docs/api-diff.md listing:
  - server endpoints the app does not call, with their handler function;
  - app calls the server no longer routes (broken after an upstream change).

Supports comparing against a baseline of server routes (--baseline <file>)
to detect added or removed server endpoints for CI drift detection.

Usage:
  python scripts/api_diff.py [path/to/router.rs] [--baseline path/to/baseline.txt]
"""
import argparse
import os
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
DEFAULT_ROUTER = ROOT.parent / "codeg/src-tauri/src/web/router.rs"
APP_SRC = ROOT / "app/src/main/java"
DEFAULT_DOC_OUT = ROOT / ".taskmaster/docs/api-diff.md"

ROUTE = re.compile(r'\.route\(\s*"/([^"]+)"\s*,\s*(?:post|get)\(\s*([\w:]+)\s*\)', re.S)
SEND = re.compile(r'\bsend(?:Raw|Unit)?\(\s*"([a-z0-9_]+)"')


def line_of(text: str, pos: int) -> int:
    return text.count("\n", 0, pos) + 1


def locate(handler: str, handlers_dir: Path) -> str:
    """`handlers::git::git_stash_push` -> `handlers/git.rs:345`, plus the
    request struct's location when the handler takes `Json<...>`."""
    parts = handler.split("::")
    path = handlers_dir / f"{parts[1]}.rs" if len(parts) == 3 else None
    if not path or not path.exists():
        return f"`{handler}`"
    text = path.read_text(encoding="utf-8")
    m = re.search(rf"pub async fn {parts[2]}\b", text)
    if not m:
        return f"`{handler}`"
    loc = f"`handlers/{parts[1]}.rs:{line_of(text, m.start())}`"
    head = text[m.end() : m.end() + 600].split("{", 1)[0]
    req = re.search(r"Json\(\w+\)\s*:\s*Json<\s*(\w+)\s*>", head)
    if req and req.group(1) not in ("Value", "String"):
        sm = re.search(rf"\bstruct {req.group(1)}\b", text)
        loc += f" · `{req.group(1)}`" + (f" :{line_of(text, sm.start())}" if sm else " (defined elsewhere)")
    return loc


def load_baseline(path: Path) -> set[str]:
    lines = [
        line.strip()
        for line in path.read_text(encoding="utf-8").splitlines()
        if line.strip() and not line.strip().startswith("#")
    ]
    return set(lines)


def generate_drift_report(
    routes: dict[str, str],
    calls: dict[str, list[str]],
    baseline_routes: set[str] | None,
    broken: list[str],
    unused: list[str],
    added: list[str],
    removed: list[str],
    handlers_dir: Path,
    tag: str | None = None,
) -> str:
    lines = [
        "## Upstream API Drift Report",
        "",
        "An automatic check detected differences between upstream server routes, app calls, and the API baseline.",
        "",
    ]
    if tag:
        lines.append(f"**Target codeg release**: `{tag}`\n")

    lines.extend([
        "| Metric | Count |",
        "|---|---|",
        f"| Server routes | {len(routes)} |",
        f"| App endpoints called | {len(calls)} |",
    ])
    if baseline_routes is not None:
        lines.append(f"| Baseline routes | {len(baseline_routes)} |")
    lines.extend([
        f"| ⚠️ Broken app calls (missing from server) | {len(broken)} |",
        f"| 🗑️ Removed server endpoints | {len(removed)} |",
        f"| 🆕 Added server endpoints | {len(added)} |",
        "",
    ])

    if broken:
        lines.extend([
            f"### ⚠️ App Calls Missing from Server ({len(broken)})",
            "",
            "These endpoints are invoked in the Android codebase but were not found in the server router:",
            "",
            "| Endpoint | Call site |",
            "|---|---|",
            *[f"| `{n}` | {', '.join(calls.get(n, []))} |" for n in broken],
            "",
        ])

    if removed:
        lines.extend([
            f"### 🗑️ Removed Server Endpoints ({len(removed)})",
            "",
            "These endpoints existed in the API baseline but are no longer present in the server router:",
            "",
            "| Endpoint |",
            "|---|",
            *[f"| `{n}` |" for n in removed],
            "",
        ])

    if added:
        lines.extend([
            f"### 🆕 Added Server Endpoints ({len(added)})",
            "",
            "These endpoints are new in the server router and not yet tracked in the API baseline:",
            "",
            "| Endpoint | Handler · request |",
            "|---|---|",
            *[f"| `{n}` | {locate(routes[n], handlers_dir)} |" for n in added],
            "",
        ])

    if not broken and not removed and not added:
        lines.extend([
            "### Status",
            "",
            "✅ No API drift detected. All app calls are routed and server routes match the baseline.",
            "",
        ])

    lines.extend([
        "---",
        "*Report generated automatically by `scripts/api_diff.py`*",
        "",
    ])
    return "\n".join(lines)


def main() -> None:
    parser = argparse.ArgumentParser(
        description="Compare codeg server HTTP routes with app calls and baseline."
    )
    parser.add_argument(
        "router",
        nargs="?",
        default=str(DEFAULT_ROUTER),
        help=f"Path to router.rs (default: {DEFAULT_ROUTER})",
    )
    parser.add_argument(
        "--baseline",
        type=Path,
        default=None,
        help="Path to baseline file containing expected server route names.",
    )
    parser.add_argument(
        "--report",
        type=Path,
        default=None,
        help="Path to write markdown drift report (e.g. for GitHub issue body).",
    )
    parser.add_argument(
        "--tag",
        type=str,
        default=None,
        help="Upstream release tag name to include in reports.",
    )
    parser.add_argument(
        "--dump-baseline",
        type=Path,
        default=None,
        help="Dump current sorted server routes to specified file and exit.",
    )
    parser.add_argument(
        "--out",
        type=Path,
        default=DEFAULT_DOC_OUT,
        help=f"Path to write docs markdown (default: {DEFAULT_DOC_OUT}).",
    )
    parser.add_argument(
        "--no-doc",
        action="store_true",
        help="Skip writing the .taskmaster/docs/api-diff.md documentation file.",
    )

    args = parser.parse_args()

    router_path = Path(args.router).resolve()
    if not router_path.exists():
        print(f"Error: router.rs not found at {router_path}", file=sys.stderr)
        sys.exit(1)

    handlers_dir = router_path.parent / "handlers"
    routes = {name: handler for name, handler in ROUTE.findall(router_path.read_text(encoding="utf-8"))}

    if args.dump_baseline:
        sorted_routes = sorted(routes.keys())
        args.dump_baseline.write_text("\n".join(sorted_routes) + "\n", encoding="utf-8")
        print(f"Dumped {len(sorted_routes)} routes to {args.dump_baseline}")
        return

    calls: dict[str, list[str]] = {}
    for f in APP_SRC.rglob("*.kt"):
        text = f.read_text(encoding="utf-8")
        for m in SEND.finditer(text):
            line = text.count("\n", 0, m.start()) + 1
            calls.setdefault(m.group(1), []).append(f"{f.relative_to(ROOT).as_posix()}:{line}")

    unused = sorted(set(routes) - set(calls))
    broken = sorted(set(calls) - set(routes))

    baseline_routes: set[str] | None = None
    added: list[str] = []
    removed: list[str] = []

    if args.baseline:
        baseline_path = Path(args.baseline).resolve()
        if not baseline_path.exists():
            print(f"Error: baseline file not found at {baseline_path}", file=sys.stderr)
            sys.exit(1)
        baseline_routes = load_baseline(baseline_path)
        added = sorted(set(routes) - baseline_routes)
        removed = sorted(baseline_routes - set(routes))

    # Write documentation file if requested
    if not args.no_doc and args.out:
        out_lines = [
            "# Server API vs app calls",
            "",
            "Generated by `scripts/api_diff.py`; do not edit by hand.",
            f"Server routes: {len(routes)}; endpoints the app calls: {len(calls)}.",
            "",
            f"## App calls missing from the server ({len(broken)})",
            "",
            "| Endpoint | Call site |",
            "|---|---|",
            *[f"| `{n}` | {', '.join(calls[n])} |" for n in broken],
            "",
            f"## Server endpoints the app does not call ({len(unused)})",
            "",
            "Paths are relative to `codeg/src-tauri/src/web/`. After the handler",
            "comes its `Json<...>` request struct and that struct's line in the",
            "same file (fields are snake_case on the wire unless the struct says",
            "`rename_all`).",
            "",
            "| Endpoint | Handler · request |",
            "|---|---|",
            *[f"| `{n}` | {locate(routes[n], handlers_dir)} |" for n in unused],
            "",
        ]
        try:
            args.out.parent.mkdir(parents=True, exist_ok=True)
            args.out.write_text("\n".join(out_lines), encoding="utf-8")
        except Exception as e:
            print(f"Warning: could not write doc to {args.out}: {e}", file=sys.stderr)

    # Console summary output
    summary = f"routes={len(routes)} calls={len(calls)} unused={len(unused)} broken={len(broken)}"
    if baseline_routes is not None:
        summary += f" baseline={len(baseline_routes)} added={len(added)} removed={len(removed)}"
    print(summary)

    if broken:
        print(f"\nBroken app calls ({len(broken)}):")
        for n in broken:
            print(f"  - {n} ({', '.join(calls.get(n, []))})")

    if removed:
        print(f"\nRemoved server endpoints ({len(removed)}):")
        for n in removed:
            print(f"  - {n}")

    if added:
        print(f"\nAdded server endpoints ({len(added)}):")
        for n in added:
            print(f"  - {n} ({locate(routes[n], handlers_dir)})")

    has_drift = (len(broken) > 0 or len(added) > 0 or len(removed) > 0) if baseline_routes is not None else (len(broken) > 0)

    # Write drift report if requested
    drift_report_text = generate_drift_report(
        routes=routes,
        calls=calls,
        baseline_routes=baseline_routes,
        broken=broken,
        unused=unused,
        added=added,
        removed=removed,
        handlers_dir=handlers_dir,
        tag=args.tag,
    )

    if args.report:
        args.report.parent.mkdir(parents=True, exist_ok=True)
        args.report.write_text(drift_report_text, encoding="utf-8")

    # GitHub Actions Step Summary
    gh_step_summary = os.environ.get("GITHUB_STEP_SUMMARY")
    if gh_step_summary:
        try:
            with open(gh_step_summary, "a", encoding="utf-8") as f:
                f.write(drift_report_text + "\n")
        except Exception as e:
            print(f"Warning: could not write to GITHUB_STEP_SUMMARY: {e}", file=sys.stderr)

    # GitHub Actions Output Variables
    gh_output = os.environ.get("GITHUB_OUTPUT")
    if gh_output:
        try:
            with open(gh_output, "a", encoding="utf-8") as f:
                f.write(f"routes={len(routes)}\n")
                f.write(f"calls={len(calls)}\n")
                f.write(f"unused={len(unused)}\n")
                f.write(f"broken={len(broken)}\n")
                if baseline_routes is not None:
                    f.write(f"baseline={len(baseline_routes)}\n")
                    f.write(f"added={len(added)}\n")
                    f.write(f"removed={len(removed)}\n")
                f.write(f"has_drift={'true' if has_drift else 'false'}\n")
        except Exception as e:
            print(f"Warning: could not write to GITHUB_OUTPUT: {e}", file=sys.stderr)


if __name__ == "__main__":
    main()
