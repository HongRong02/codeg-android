# T36 任务模板与看板设置（T31 二期）

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T31（已合并）　可并行：与 T37–T40 并行
- 分支：`task/T36-work-task-templates`

## 目标
补齐 T31 没做的任务模板管理和按项目的看板设置。

## 需求
- 模板：工作任务页右上角菜单加"模板"，列表 `work_task_template_list`，新建/编辑 `work_task_template_save`，删除 `work_task_template_delete`（确认）。新建任务时选模板填充已有（T31 已做读取，复用）。
- 看板设置：菜单加"看板设置"，选项目后显示 `work_task_settings_effective`（生效值）与 `work_task_settings_get_own`（项目自有值）；可编辑并 `work_task_settings_set`，"恢复默认" 调 `work_task_settings_delete`（确认）。字段以 `SettingsSetParams` 和返回模型为准，只做服务端给出的字段（布尔用开关、枚举用下拉、数字/文本用输入框）。
- 服务端无接口时隐藏对应菜单项。

## 改动范围
- 修改：`A/feature/worktasks/WorkTaskScreen.kt`（只加顶栏菜单入口）、`WorkTaskViewModel.kt`
- 新建：`A/feature/worktasks/WorkTaskTemplates.kt`、`WorkTaskSettingsSheet.kt`
- 修改：`A/core/network/CodegClient.kt` 已有 `// region work-tasks` 内追加
- 修改（只追加）：`A/core/model/WorkTask.kt`、`A/core/model/wire/WorkTaskRequests.kt`
- 新建：`R/values/strings_work_task_templates.xml`、`R/values-zh-rCN/strings_work_task_templates.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/work_task.rs`）：settings_get `:466`、settings_effective `:476`、settings_get_own `:486` · `FolderParams :69`；settings_set `:496` · `SettingsSetParams :197`；settings_delete `:511`；template_list `:521`；template_save `:530` · `TemplateSaveParams :204`；template_delete `:540` · `IdParams :27`。模型在 `codeg/src-tauri/src/models/` grep `WorkTaskSettings`、`WorkTaskTemplate`。
- app：`.taskmaster/docs/code-index/feature-worktasks.md`；`A/feature/worktasks/WorkTaskControls.kt:100 WorkTaskEditor`（模板填充现状）。

## 参考（只读，按需打开）
- `WEB/components/tasks/task-settings-dialog.tsx`、`task-editor-dialog.tsx`（模板部分）

## 验收
- 测试服务端上：新建模板 → 新建任务时选模板填充 → 删除模板；sandbox 项目改一项看板设置 → 恢复默认。
- CI 通过。
