# T42 第三轮真机问题修复（T36–T41）

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：high　依赖：T36–T41（已合并）
- 分支：`task/T42-round3-fixes`

## 问题与要求（逐条修，互不相关）

1. **更新横幅常驻、压住状态栏（T41）**：`UpdateBanner` 挂在 `MainShell` 的 `Scaffold(topBar=…)`，没有状态栏 inset；"已是最新版本"之类的提示在关于页手动检查后一直显示在屏幕最顶部（y=0，与状态栏重叠），并把所有页面往下推 72px。
   - 要求：横幅只在"有新版本 / 下载中 / 下载失败"时出现，且加 `windowInsetsPadding(WindowInsets.statusBars)`（只在横幅显示时占位）。"已是最新版本 / 检查失败"只在关于页就地显示（关于页自己读 `UpdateViewModel` 状态），不进全局横幅；离开关于页后清除。
2. **模板按钮文字错（T36）**：模板列表每项的编辑按钮显示"编辑任务"，应为"编辑模板"（新增字符串，不要改 `wtb_edit` 原意）。
3. **模板选择后标签不更新（T36）**：新建任务对话框选了模板后，"模板: 请选择"仍显示"请选择"，应显示所选模板名。
4. **模板表单"保存"按钮被裁掉一半（T36）**：模板新建/编辑表单里"保存"只露出上半截（对话框内滚动区域高度问题）。保证按钮完整可见、可点。
5. **助手入口在不支持的服务端上仍显示（T38）**：`SettingsRoot` 的叶子过滤（约 188 行，参照 `SERVER_LOGS` 的 `showServerLogs` 写法）加上助手支持判断；不支持时隐藏"助手"入口。
6. **MCP 市场搜索超时显示原始英文异常（T39）**：服务端查询外部 registry 可能要 60 秒才返回 `network_error`；app 先超时并显示 `Socket timeout has expired [url=…]`。
   - 要求：市场搜索/详情请求单独放宽超时到 90 秒（只对这两个调用，按 `di/NetworkModule.kt` 现有方式或 Ktor 每请求 `timeout {}`），并把超时和服务端 `network_error` 映射为本地化友好提示（复用 T27 的错误本地化机制，按 code-index 查 `ApiError` / 错误文案函数）。
7. **导入会话时间是原始 ISO（T37）**：`ImportSessionsSheet.kt:62` 的 `session.startedAt` 显示 `2026-10-15T10:47:32.593Z`；改为本地时间 `yyyy-MM-dd HH:mm`（可复用 `feature/worktasks/WorkTaskLabels.kt` 的 `formatWorkTaskTime`，或在 core 放一个通用函数并让两处共用）。

## 改动范围
- `A/core/update/UpdateBanner.kt`、`UpdateViewModel.kt`；`A/feature/main/MainShell.kt`（只动 topBar 那一行附近）；`A/feature/settings/SettingsScreen.kt`（`AboutScreen` 与 `SettingsRoot` 过滤）
- `A/feature/worktasks/WorkTaskTemplates.kt`、`WorkTaskControls.kt`（模板选择标签）
- `A/feature/settings/AssistantsViewModel.kt`（如需暴露 supported）
- `A/feature/settings/mcpmarket/McpMarketViewModel.kt`、`A/core/network/CodegClient.kt` 中 `// region mcp-market` 内、错误本地化所在文件（只加映射）
- `A/feature/projects/importsessions/ImportSessionsSheet.kt`
- 字符串：在对应任务已有的 `strings_*.xml`（en + zh-rCN）里追加
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `A/core/update/UpdateBanner.kt:31 UpdateBanner`；`A/feature/main/MainShell.kt:202 topBar = { …UpdateBanner() }`；`A/feature/settings/SettingsScreen.kt:84 ASSISTANTS`、`:164`、`:178 showServerLogs`、`:188 leaves filter`、`AboutScreen`（grep）
- `A/feature/worktasks/WorkTaskTemplates.kt:51-70`；`WorkTaskControls.kt:100 WorkTaskEditor`
- `A/di/NetworkModule.kt:21`（超时配置说明）；`A/core/network/ApiError.kt`
- `A/feature/projects/importsessions/ImportSessionsSheet.kt:62`
- 其余按 `.taskmaster/docs/code-index/` 查符号

## 验收
- 单测覆盖：横幅显示条件（各状态下显示/不显示）、时间格式化、错误映射。
- CI 通过。真机步骤写进报告。
