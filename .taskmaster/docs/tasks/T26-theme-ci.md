# T26 WebThemes.kt 过期检查接入 CI

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：无　可并行：与所有任务并行
- 分支：`task/T26-theme-ci`

## 目标
`WebThemes.kt` 由 `scripts/gen_web_themes.py` 从网页端 `globals.css` 生成。在 CI 中检查官方最新 `globals.css` 生成结果是否与仓库中的一致，不一致时开 issue。

## 需求
- 可与 T25 共用同一个 workflow（若 T25 已完成则追加 job，否则新建 `.github/workflows/theme-drift.yml`）。
- 步骤：sparse checkout 官方 codeg 的 `src/app/globals.css` → 运行 `python scripts/gen_web_themes.py <globals.css>` 到临时位置 → 与 `A/core/designsystem/theme/WebThemes.kt` diff → 有差异则开/更新 issue（附 diff）。
- 如生成脚本只能写固定路径，给它加一个 `--out` 参数（保持默认行为不变）。

## 改动范围
- 修改：`scripts/gen_web_themes.py`（仅加 `--out` 参数，如需要）
- 新建或修改：`.github/workflows/theme-drift.yml`（或 T25 的 `api-drift.yml`）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `scripts/gen_web_themes.py`（读开头用法与输出路径部分）
- `A/core/designsystem/theme/WebThemes.kt`（只看文件头确认生成标记）
- `.github/workflows/sync-upstream.yml`（gh issue 用法）

## 参考（只读，按需打开）
- 无

## 验收
- 手动触发成功；当前无差异时不开 issue；人为改动 WebThemes.kt 一个值后能开出 issue。
