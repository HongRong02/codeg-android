# T31 任务看板完整版（work tasks 写操作）

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：high　依赖：T21（已合并）　可并行：与 T32–T35 并行
- 分支：`task/T31-work-task-board`

## 目标
把 T21 的只读任务列表升级为与网页端一致的任务看板：四列分组 + 新建/编辑 + 状态流转操作。

## 需求
- 看板：按网页端 `board-columns.ts` 的映射分四组——待办(todo, queued)、进行中(preparing, running)、需处理(awaiting_input, review, merging, failed)、完成(done, canceled)；"以后处理"(deferred) 的 review/failed 归入完成；默认隐藏 canceled，可开关显示。手机上用分段标签切换列（不要横向四列）。每列显示数量，需处理列标红点。
- 新建/编辑：`work_task_create` / `work_task_update`。字段以服务端 `CreateParams`/`UpdateParams` 为准；至少支持标题、描述、项目(folder)、agent。项目和 agent 用现有数据源下拉选择（项目列表已有 `client.listFolders` 一类调用，agent 列表已有，按 code-index 查）。
- 列表操作：开始 `work_task_start`、全部开始 `work_task_start_all`、删除 `work_task_delete`（需确认）、排序 `work_task_reorder`（上移/下移按钮即可，不做拖拽）。
- 详情页操作（按状态显示可用按钮，参考 `task-actions.ts`）：取消 `work_task_cancel`（确认）、重试 `work_task_retry`、重新排队 `work_task_requeue`、退回 `work_task_return`（输入反馈）、定时 `work_task_schedule`、延后 `work_task_defer`、完成 `work_task_complete`、合并 `work_task_merge`（确认）、取消合并排队 `work_task_merge_unqueue`、交付 PR `work_task_deliver_pr`（确认）、归档 `work_task_archive`、清理 worktree `work_task_cleanup`（确认）。
- 模板：`work_task_template_list` 在新建时可选模板填充；`work_task_template_save`/`_delete` 可放到二期，本任务可不做。
- 看板设置 `work_task_settings_*`：本任务不做。
- 操作后刷新列表与详情；失败用现有错误展示（T27 的本地化错误）。
- 服务端无接口时隐藏入口（沿用 T21 的 supported 判断）。

## 改动范围
- 修改：`A/feature/worktasks/WorkTaskScreen.kt`、`WorkTaskViewModel.kt`（可重写）
- 新建：`A/feature/worktasks/` 下任意新文件（如 `WorkTaskBoard.kt`、`WorkTaskEditorSheet.kt`、`WorkTaskActions.kt`）
- 修改：`A/core/network/CodegClient.kt`——在已有 `// region work-tasks`（约 1095 行）内追加写接口
- 修改：`A/core/model/wire/WorkTaskRequests.kt`、`A/core/model/WorkTask.kt`（只追加）
- 新建：`R/values/strings_work_task_board.xml`、`R/values-zh-rCN/strings_work_task_board.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/work_task.rs`）：create `:247` · `CreateParams :41`；update `:257` · `UpdateParams :47`；reorder `:273` · `ReorderParams :54`；delete `:288` · `DeleteParams :61`；start `:298` · `IdParams :27`；start_all `:307` · `StartAllParams :167`；retry `:316` / requeue `:325` · `RestartParams :91`；schedule `:342` · `ScheduleParams :108`；return `:352` · `ReturnParams :75`；cancel `:361` · `CancelParams :119`；merge `:372` · `MergeParams :130`；merge_unqueue `:386`；deliver_pr `:398` · `DeliverPrParams :151`；complete `:412` · `CompleteParams :144`；archive `:421` · `ArchiveParams :175`；defer `:431` · `DeferParams :182`；cleanup `:441`；template_list `:521`。返回模型在 `codeg/src-tauri/src/models/` 里 grep `WorkTask`。
- app：`.taskmaster/docs/code-index/feature-worktasks.md`（现有屏幕和 VM 签名）；`A/core/network/CodegClient.kt:1095 region work-tasks`；`core/designsystem/component/StateViews.kt`；确认对话框写法参考 `A/feature/projects/git/` 下任意 Sheet。

## 参考（只读，按需打开）
- `WEB/components/tasks/board-columns.ts`、`task-actions.ts`、`task-acceptance.ts`、`tasks-page.tsx`、`task-editor-dialog.tsx`、`task-detail-sheet.tsx`、`task-merge-dialog.tsx`、`task-deliver-pr-dialog.tsx`、`task-schedule-dialog.tsx`、`task-cancel-dialog.tsx`

## 验收
- 在 WSL 测试服务端（~/codeg-t13，sandbox 项目）上：新建任务 → 开始 → 运行中 → 取消；再新建一个 → 删除。列与数量正确。
- CI 通过。
