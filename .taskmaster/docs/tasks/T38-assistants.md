# T38 助手（assistants）管理

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：无　可并行：与 T36、T37、T39、T40 并行
- 分支：`task/T38-assistants`

## 目标
与网页端设置里的"助手"面板一致：查看、新建、编辑、删除、排序助手。

## 需求
- 入口：设置 → AI 与智能体 分组新增一项"助手"（`SettingsLeaf` 枚举追加一项 + `when` 分支，参考 `EXPERTS`）。
- 列表 `assistant_list`：头像（按网页端 `assistant-avatar.tsx` 的规则：emoji/首字母+颜色即可）、名称、agent、简介。
- 新建/编辑 `assistant_create` / `assistant_update`：字段以 `AssistantCreateParams` / `AssistantUpdateParams` 为准。
- 删除 `assistant_delete`（确认）；排序 `assistant_reorder`（上移/下移按钮）。

## 改动范围
- 修改：`A/feature/settings/SettingsScreen.kt`——只在 `SettingsLeaf`（约 77 行）追加一项、在 `when (leaf)`（约 162 行）追加一个分支
- 新建：`A/feature/settings/AssistantsScreen.kt`、`AssistantsViewModel.kt`
- 修改：`A/core/network/CodegClient.kt`（新增 `// region assistants`，放在 `// region Transport` 之前）
- 新建：`A/core/model/wire/AssistantRequests.kt`、`A/core/model/Assistant.kt`
- 新建：`R/values/strings_assistants.xml`、`R/values-zh-rCN/strings_assistants.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/assistants.rs`）：list `:11`；create `:25` · `AssistantCreateParams :21`；update `:41` · `AssistantUpdateParams :36`；delete `:62` · `AssistantIdParams :58`；reorder `:77` · `AssistantReorderParams :73`。模型 grep `struct Assistant`。
- app：`A/feature/settings/SettingsScreen.kt:77 SettingsLeaf`、`:84 EXPERTS`、`:162 SettingsLeaf.EXPERTS ->`；`A/feature/settings/ExpertsScreen.kt`（同类列表页写法）。

## 参考（只读，按需打开）
- `WEB/components/settings/assistants-panel.tsx`、`assistant-editor-dialog.tsx`、`WEB/components/assistants/assistant-avatar.tsx`

## 验收
- 测试服务端上：新建助手 → 编辑 → 上移/下移 → 删除；网页端同步可见。
- CI 通过。
