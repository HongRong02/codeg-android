# T33 自动化：新建、编辑、删除、立即运行

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T22（已合并）　可并行：与 T31、T32、T34、T35 并行
- 分支：`task/T33-automation-write`

## 目标
在 T22 的自动化列表/详情基础上补齐写操作。

## 需求
- 新建/编辑：`automation_create` / `automation_update`，字段以 `CreateAutomationParams`/`UpdateAutomationParams` 为准（名称、项目、agent、提示词、计划）。计划输入后调用 `automation_compute_next_run` 实时显示"下次运行时间"，非法时显示错误。
- 删除：`automation_delete`（确认）。
- 立即运行：`automation_run_now`；运行中的记录可 `automation_cancel_run`（确认）。
- 进入详情时调用 `automation_mark_seen`（清未读）。
- 列表右上角加"新建"按钮，详情页加"编辑 / 立即运行 / 删除"。

## 改动范围
- 修改：`A/feature/automations/AutomationScreen.kt`、`AutomationViewModel.kt`
- 新建：`A/feature/automations/AutomationEditorSheet.kt`（及其它该目录下新文件）
- 修改：`A/core/network/CodegClient.kt`——在已有 `// region automations`（约 1055 行）内追加
- 修改：自动化相关 wire/model 文件（按 code-index `core-model.md` 查 `Automation`，只追加）
- 新建：`R/values/strings_automation_write.xml`、`R/values-zh-rCN/strings_automation_write.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/automation.rs`）：create `:104` · `CreateAutomationParams :32`；update `:114` · `UpdateAutomationParams :38`；delete `:135` · `DeleteAutomationParams :52`；mark_seen `:145`；compute_next_run `:154` · `ComputeNextRunParams :58`；run_now `:162` · `RunNowParams :65`；cancel_run `:171` · `CancelRunParams :71`。
- app：`.taskmaster/docs/code-index/feature-automations.md`（`AutomationScreen :76`、`AutomationDetailView :297`、`AutomationViewModel`）；`A/core/network/CodegClient.kt:1055 region automations`。

## 参考（只读，按需打开）
- `WEB/components/automations/` 下 editor / dialog 相关文件

## 验收
- 测试服务端上：新建一个每小时运行的自动化 → 立即运行 → 看到运行记录 → 编辑 → 删除。
- CI 通过。
