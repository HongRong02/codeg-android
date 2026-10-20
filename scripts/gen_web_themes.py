"""Regenerate WebThemes.kt from codeg's web palette.

Usage: python scripts/gen_web_themes.py <path-to-codeg>/src/app/globals.css [--out <path>]

Converts every [data-theme="..."] preset (light and .dark) from oklch to sRGB
so the app renders the exact colours of codeg's web UI.
"""
import argparse
import math
import pathlib
import re
import sys

FIELDS = [
    "background", "foreground", "card", "card-foreground", "popover", "popover-foreground",
    "primary", "primary-foreground", "secondary", "secondary-foreground", "muted",
    "muted-foreground", "accent", "accent-foreground", "destructive", "border", "input", "ring",
    "sidebar", "sidebar-foreground", "sidebar-primary", "sidebar-primary-foreground",
    "sidebar-accent", "sidebar-accent-foreground", "sidebar-border",
]
ORDER = ["neutral", "zinc", "slate", "stone", "gray", "red", "rose", "orange", "green", "blue", "yellow", "violet"]
DEFAULT_OUT = pathlib.Path(__file__).resolve().parent.parent / "app/src/main/java/app/codeg/android/core/designsystem/theme/WebThemes.kt"
OUT = DEFAULT_OUT


def oklch_to_argb(value):
    m = re.match(r"oklch\(\s*([\d.]+)(%?)\s+([\d.]+)\s+([\d.]+)\s*(?:/\s*([\d.]+)(%?))?\s*\)", value.strip())
    if not m:
        raise ValueError(value)
    lightness = float(m.group(1)) / (100 if m.group(2) else 1)
    chroma, hue = float(m.group(3)), math.radians(float(m.group(4)))
    a, b = chroma * math.cos(hue), chroma * math.sin(hue)
    l_ = (lightness + 0.3963377774 * a + 0.2158037573 * b) ** 3
    m_ = (lightness - 0.1055613458 * a - 0.0638541728 * b) ** 3
    s_ = (lightness - 0.0894841775 * a - 1.2914855480 * b) ** 3
    rgb = (
        4.0767416621 * l_ - 3.3077115913 * m_ + 0.2309699292 * s_,
        -1.2684380046 * l_ + 2.6097574011 * m_ - 0.3413193965 * s_,
        -0.0041960863 * l_ - 0.7034186147 * m_ + 1.7076147010 * s_,
    )

    def encode(x):
        x = min(max(x, 0.0), 1.0)
        return round(255 * (12.92 * x if x <= 0.0031308 else 1.055 * x ** (1 / 2.4) - 0.055))

    alpha = float(m.group(5)) / (100 if m.group(6) else 1) if m.group(5) else 1.0
    return "0x%02X%02X%02X%02X" % (round(alpha * 255), *(encode(c) for c in rgb))


def camel(name):
    head, *rest = name.split("-")
    return head + "".join(part.title() for part in rest)


def tokens(values, indent):
    lines = "".join(f"{indent}    {camel(f)} = Color({values[f]}),\n" for f in FIELDS)
    return f"WebTokens(\n{lines}{indent})"


def main(css_path, out_path=None):
    css = pathlib.Path(css_path).read_text(encoding="utf8")
    data = {}
    for name, dark, body in re.findall(r'\[data-theme="([a-z]+)"\](\.dark)?\s*\{(.*?)\}', css, re.S):
        values = dict(re.findall(r"--([\w-]+):\s*([^;]+);", body))
        data[(name, bool(dark))] = {f: oklch_to_argb(values[f]) for f in FIELDS}
    missing = set(ORDER) ^ {name for name, _ in data}
    if missing:
        raise SystemExit(f"preset list changed: {sorted(missing)}; update ORDER")

    out = [
        "package app.codeg.android.core.designsystem.theme\n",
        "import androidx.compose.runtime.Immutable",
        "import androidx.compose.ui.graphics.Color\n",
        "// GENERATED from codeg/src/app/globals.css ([data-theme] presets, oklch -> sRGB).",
        "// Regenerate with scripts/gen_web_themes.py when the web palette changes.\n",
        "/** One mode of a web theme preset: the shadcn/ui CSS variables of codeg's web UI. */",
        "@Immutable",
        "data class WebTokens(",
        *(f"    val {camel(f)}: Color," for f in FIELDS),
        ")\n",
        "/** The colour presets of codeg's web UI (Settings > Appearance > Theme colour). */",
        "enum class WebThemePreset(val id: String, val light: WebTokens, val dark: WebTokens) {",
    ]
    for i, name in enumerate(ORDER):
        end = "," if i < len(ORDER) - 1 else ";"
        out.append(
            f'    {name.upper()}(\n        "{name}",\n'
            f"        light = {tokens(data[(name, False)], '        ')},\n"
            f"        dark = {tokens(data[(name, True)], '        ')},\n    ){end}"
        )
    out += [
        "",
        "    fun resolve(isDark: Boolean): WebTokens = if (isDark) dark else light",
        "",
        "    companion object {",
        "        /** The web UI's default preset. */",
        "        val DEFAULT = NEUTRAL",
        "        fun from(id: String?): WebThemePreset = entries.firstOrNull { it.id == id } ?: DEFAULT",
        "    }",
        "}",
    ]
    target = pathlib.Path(out_path) if out_path else DEFAULT_OUT
    target.parent.mkdir(parents=True, exist_ok=True)
    target.write_text("\n".join(out) + "\n", encoding="utf8", newline="\n")


def parse_args(argv=None):
    parser = argparse.ArgumentParser(
        description="Regenerate WebThemes.kt from codeg's web palette."
    )
    parser.add_argument("css_path", help="Path to codeg/src/app/globals.css")
    parser.add_argument(
        "--out",
        dest="out_path",
        default=None,
        help="Output file path (default: WebThemes.kt in app module)",
    )
    return parser.parse_args(argv)


if __name__ == "__main__":
    args = parse_args()
    main(args.css_path, args.out_path)
