# T23 服务端日志与用量统计

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：T01　可并行：与 T21、T22 并行
- 分支：`task/T23-logs-stats`

## 目标
在设置中查看服务端日志与用量：最近日志、日志文件列表与内容；会话统计与 token 用量报表。

## 需求
- 日志：`get_recent_logs`（级别过滤）、`list_log_files` + `read_log_file`（等宽字体，支持搜索与复制）。
- 统计：`get_stats`（会话统计）、`token_usage_report`（按时间范围的 token 用量，简单表格或条形图）。
- 只读；不接入 `set_log_settings`、`token_usage_sync`。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region logs-stats`）
- 新建：`A/core/model/wire/ServerLogsRequests.kt`、`A/core/model/ServerLogs.kt`
- 新建：`A/feature/serverlogs/ServerLogsScreen.kt`、`ServerLogsViewModel.kt`
- 修改：`A/feature/main/MainShell.kt`（追加一个 `composable(...)` 路由，参考 317 行 `ROUTE_SERVERS` 写法）；入口加在 `A/feature/settings/SettingsScreen.kt` 的 `SettingsRoot`（152 行）列表中一行
- 新建：`R/values/strings_logs_stats.xml`、`R/values-zh-rCN/strings_logs_stats.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端：`SRV/handlers/logging.rs:59 get_recent_logs` · `GetRecentLogsParams :25`；`:69 list_log_files`；`:73 read_log_file` · `ReadLogFileParams :35`；`SRV/handlers/conversations.rs:215 get_stats`；`SRV/handlers/token_usage.rs:30 token_usage_report` · `TokenUsageReportParams :19`
- app：`feature/main/MainShell.kt:317`、`feature/settings/SettingsScreen.kt:152 SettingsRoot`；`core/designsystem/component/CodeBlock.kt:52`

## 参考（只读，按需打开）
- `WEB/components/settings/logs-settings.tsx`、`WEB/app/settings/logs/page.tsx`、`WEB/components/token-usage/`、`WEB/components/layout/status-bar-stats.tsx`

## 验收
- 本地测试服务端上真机验证。
- CI 通过。
