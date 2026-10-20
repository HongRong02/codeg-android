# T21 Work tasks 只读视图

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：T01　可并行：与 T22、T23 并行
- 分支：`task/T21-work-tasks`

## 目标
在 app 中查看服务端的 work tasks：列表、详情、事件时间线、改动文件与 diff。只读，不做创建/调度/合并。

## 需求
- 列表：`work_task_list`（按状态分组，显示标题、agent、状态、更新时间）；顶部显示 `work_task_attention_count`。
- 详情：`work_task_get` + `work_task_events`（时间线）+ `work_task_changed_files` + `work_task_diff`（`DiffView`）。
- 只读：不接入 create/update/start/cancel/merge 等写接口。
- 服务端无该接口时隐藏入口。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region work-tasks`）
- 新建：`A/core/model/wire/WorkTaskRequests.kt`、`A/core/model/WorkTask.kt`
- 新建：`A/feature/worktasks/WorkTaskScreen.kt`、`WorkTaskViewModel.kt`
- 修改：`A/feature/main/MainShell.kt`（追加一个 `composable(...)` 路由，参考 317 行 `ROUTE_SERVERS` 写法）；入口加在 `A/feature/settings/SettingsScreen.kt` 的 `SettingsRoot`（152 行）列表中一行
- 新建：`R/values/strings_work_tasks.xml`、`R/values-zh-rCN/strings_work_tasks.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/work_task.rs`）：`208 work_task_list` · `ListParams :20`；`218 work_task_get` · `IdParams :27`；`228 work_task_events` · `EventsParams :33`；`238 work_task_attention_count`；`458 work_task_changed_files`；`450 work_task_diff` · `DiffParams :189`；返回模型去 `codeg/src-tauri/src/models/` 里按名称 grep
- app：`feature/main/MainShell.kt:317`（路由写法）、`feature/settings/SettingsScreen.kt:152 SettingsRoot`；`core/designsystem/diff/DiffView.kt:41`；`core/designsystem/component/StateViews.kt`（空/加载/错误）

## 参考（只读，按需打开）
- `WEB/components/tasks/tasks-page.tsx`、`task-list.tsx`、`task-row.tsx`、`task-card.tsx`、`task-detail-sheet.tsx`、`board-columns.ts`

## 验收
- 本地测试服务端上能看到 work task 列表与详情（如无数据，用网页端创建一个）。
- CI 通过。
