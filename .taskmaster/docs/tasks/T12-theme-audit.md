# T12 12 套主题 × 明暗模式巡检

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T02、T03、T04、T05、T06、T07、T08、T09、T10、T11
- 分支：`task/T12-theme-audit`

## 目标
在所有页面打磨完成后，按 12 套 web 主题预设 × 明/暗模式做一轮截图巡检并修正问题。

## 需求
- 在真机上逐一切换主题预设（设置 → 外观）与明暗模式，截取主要页面：会话列表、会话详情（含工具卡片、权限卡片）、项目详情三个标签页、设置主页。
- 记录问题清单（对比度不足、硬编码颜色、半透明色叠加错误、选中态不可见等），逐项修复。
- 修复只允许改颜色/透明度的取值方式（换 token、改用 `withOpacity()`），不改布局。

## 改动范围
- 修改：问题所在的 Composable 文件（仅颜色相关行）、`A/core/designsystem/theme/Color.kt`、`CodegTheme.kt`
- 不改：`WebThemes.kt`（生成文件，改要走 `scripts/gen_web_themes.py`）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 主题：`core/designsystem/theme/WebThemes.kt:11 WebTokens`、`:40 WebThemePreset`；`Color.kt:13 CodegColors`、`:35 Color.withOpacity`、`:43 codegColors`；`CodegTheme.kt:30 CodegTheme`、`:79 toMaterialScheme`
- 查找硬编码颜色：`grep -rn "Color(0x" app/src/main/java`

## 参考（只读，按需打开）
- `WEB/app/globals.css`（各 `[data-theme=...]` 块）

## 验收
- 提交中附问题清单与修复前后截图（放 PR 描述或 `docs/theme-audit/`）。
- 12 × 2 组合下无不可读文字、无不可见选中态。
- CI 通过。
