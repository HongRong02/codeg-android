# T22 Automations 只读视图

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：T01　可并行：与 T21、T23 并行
- 分支：`task/T22-automations`

## 目标
查看服务端的自动化任务：列表、详情、运行记录。只读，可选支持启用/停用开关。

## 需求
- 列表：`automation_list`（名称、计划描述、启用状态、上次运行）。
- 详情：`automation_get` + `automation_runs`（运行记录）。
- 可选：`automation_set_enabled` 开关（这是唯一允许的写操作）。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region automations`）
- 新建：`A/core/model/wire/AutomationRequests.kt`、`A/core/model/Automation.kt`
- 新建：`A/feature/automations/AutomationScreen.kt`、`AutomationViewModel.kt`
- 修改：`A/feature/main/MainShell.kt`（追加一个 `composable(...)` 路由，参考 317 行 `ROUTE_SERVERS` 写法）；入口加在 `A/feature/settings/SettingsScreen.kt` 的 `SettingsRoot`（152 行）列表中一行
- 新建：`R/values/strings_automations.xml`、`R/values-zh-rCN/strings_automations.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/automation.rs`）：`75 automation_list`；`84 automation_get` · `GetAutomationParams :18`；`94 automation_runs` · `ListRunsParams :24`；`124 automation_set_enabled` · `SetEnabledParams :45`
- app：`feature/main/MainShell.kt:317`、`feature/settings/SettingsScreen.kt:152 SettingsRoot`

## 参考（只读，按需打开）
- `WEB/components/automations/automations-page.tsx`、`schedule-label.tsx`、`WEB/components/layout/aux-panel-automations-tab.tsx`

## 验收
- 本地测试服务端上真机验证。
- CI 通过。
