# T34 会话标签

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：无　可并行：与 T31–T33、T35 并行
- 分支：`task/T34-conversation-tags`

## 目标
与网页端一致地显示、筛选、编辑会话标签。

## 需求
- 显示：会话列表行显示标签小圆角 chip（颜色用标签自带颜色，参考 `conversation-tag-chip.tsx`）。
- 筛选：会话列表顶部加标签筛选（可多选，参考 `sidebar-tag-filter.tsx`）。
- 打标签：会话行长按菜单（`SessionRow.kt` 已有 `DropdownMenu`）加"标签…"，打开选择 Sheet，调用 `update_conversation_tags`。
- 管理：选择 Sheet 内可新建 `create_conversation_tag`、改名/改色 `update_conversation_tag`、删除 `delete_conversation_tag`（确认）。排序 `reorder_conversation_tags`、分支标签 `*_branch_tag` 不做。
- 会话与标签的关联从哪里取：先查 `list_conversation_tags` 返回结构和 `get_sidebar_data`/`list_conversations` 返回里是否带 tag id；如需给 `ConversationSummary` 加字段，只追加可空字段。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region conversation-tags`，放在 `// region Transport` 之前）
- 新建：`A/core/model/wire/ConversationTagRequests.kt`、`A/core/model/ConversationTag.kt`
- 修改（只追加）：`A/core/model/Conversation.kt` 的 `ConversationSummary`（如需要）
- 修改：`A/feature/sessions/SessionRow.kt`、`SessionListScreen.kt`、`SessionListViewModel.kt`
- 新建：`A/feature/sessions/tags/` 下文件
- 新建：`R/values/strings_conversation_tags.xml`、`R/values-zh-rCN/strings_conversation_tags.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/conversation_tags.rs`）：list `:11`；create `:29` · `CreateConversationTagParams :21`；update `:55` · `UpdateConversationTagParams :47`；delete `:77` · `ConversationTagIdParams :73`；update_conversation_tags `:110` · `UpdateConversationTagsParams :102`。模型在 `codeg/src-tauri/src/models/` grep `ConversationTag`。
- app：`A/feature/sessions/SessionRow.kt:48 SessionRow`、`:136 DropdownMenu`；`SessionListScreen.kt:82 SessionListScreen`、`:252 SessionRow(` 调用处；`SessionListViewModel.kt:44`、`:176 SessionListUiState`；`core/model/Conversation.kt:13 ConversationSummary`。

## 参考（只读，按需打开）
- `WEB/components/conversations/conversation-tag-chip.tsx`、`conversation-tag-picker.tsx`、`conversation-tag-form-dialog.tsx`、`sidebar-tag-filter.tsx`

## 验收
- 测试服务端上：新建标签 → 给会话打标签 → 列表显示 chip → 筛选生效 → 网页端同步可见。
- CI 通过。
