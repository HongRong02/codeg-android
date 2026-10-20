# T09 打磨：设置主页与通用类子页

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T01　可并行：与 T02–T08 并行；与 T10、T11 并行（文件不重叠）
- 分支：`task/T09-settings-core`

## 目标
对照网页端逐项打磨设置主页与通用类子页，并把顶栏换成 `CodegTopBar`。设置子页由 `SettingsScreen` 统一承载顶栏，故顶栏替换在本任务完成。

## 需求
- 顶栏替换为 `CodegTopBar` / `CodegIconButton`。
- 对照网页端参考检查：列表行高、内边距、字号层级、次要文字颜色（`--muted-foreground`）、卡片（`Modifier.webSurface`）、分隔线、徽章、空状态/加载/错误态。
- 只做视觉与布局调整，不改业务逻辑、不改 ViewModel 的对外状态结构。

## 改动范围
- 修改：`A/feature/settings/SettingsScreen.kt`、`GeneralScreen.kt`、`LanguageScreen.kt`、`SystemScreen.kt`、`VersionControlScreen.kt`、`OpenSourceLicensesScreen.kt`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `SettingsScreen.kt`：`77 SettingsLeaf`、`96 SettingsScreen`（顶栏 108 行）、`152 SettingsRoot`、`190 AppearanceSettings`、`234 AboutScreen`
- `GeneralScreen.kt:45`（`75 Section`、`84 ToggleRow`、`106 Stepper`）、`LanguageScreen.kt:54`、`SystemScreen.kt:37`（`75 PillButton`）、`VersionControlScreen.kt:40`（`91 Pill`）、`OpenSourceLicensesScreen.kt:31`
- 共享：`core/designsystem/component/Badges.kt:150 SettingsIconBadge`、`Glass.kt:69 GlassRow`

## 参考（只读，按需打开）
- `WEB/components/settings/settings-shell.tsx`、`general-settings.tsx`、`appearance-settings.tsx`、`system-network-settings.tsx`、`version-control-settings.tsx`
- `WEB/components/ui/switch.tsx`、`select.tsx`

## 验收
- 与网页端参考逐项对照：间距、字号、颜色 token、圆角、分隔线、空状态/加载/错误态一致或有意识的移动端适配（在提交说明里列出差异及理由）。
- 明暗两种模式、默认 neutral 主题下截图无异常；不引入新的硬编码颜色。
- 本页面的顶栏已换成 `CodegTopBar`（T01）。
- CI `verify.yml` 通过。
