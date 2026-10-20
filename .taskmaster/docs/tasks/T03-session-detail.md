# T03 打磨：会话详情页剩余细节

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T01　可并行：与 T02、T04–T11 并行；T15 依赖本任务
- 分支：`task/T03-session-detail`

## 目标
对照网页端逐项打磨会话详情页剩余细节，并把顶栏换成 `CodegTopBar`。输入栏（`ComposeBar`）已在 1859b0a 完成，不要重做。

## 需求
- 顶栏替换为 `CodegTopBar` / `CodegIconButton`。
- 对照网页端参考检查：列表行高、内边距、字号层级、次要文字颜色（`--muted-foreground`）、卡片（`Modifier.webSurface`）、分隔线、徽章、空状态/加载/错误态。
- 只做视觉与布局调整，不改业务逻辑、不改 ViewModel 的对外状态结构。

## 改动范围
- 修改：`A/feature/sessiondetail/SessionDetailScreen.kt`、`SessionActions.kt`、`AgentOptionsSheet.kt`、`interactive/*.kt`（仅 Composable 视觉部分）
- 不改：`SessionDetailViewModel.kt`、`ComposeBar.kt`、`timeline/`、`rendering/`（T14/T15 会改 ViewModel）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `feature/sessiondetail/SessionDetailScreen.kt`：`95 SessionDetailScreen`（顶栏在 191 行）、`361 JumpToLatestButton`、`382 SendStatusLine`、`396 NoticeBanner`、`419 AttachmentStrip`
- `SessionActions.kt:56 SessionActionsMenu`、`:162 DetailRow`
- `AgentOptionsSheet.kt:93 AgentOptionsSheet`（及其后各 private 段落）
- `interactive/PermissionRequestCard.kt:50`、`interactive/AskQuestionCard.kt:57`、`interactive/PlanApprovalCard.kt:62`

## 参考（只读，按需打开）
- `WEB/components/conversations/conversation-detail-header.tsx`、`session-details-content.tsx`
- `WEB/components/chat/permission-dialog.tsx`、`ask-question-card.tsx`、`plan-approval-card.tsx`、`session-settings-picker.tsx`、`session-failure-banner.tsx`、`connection-status.tsx`

## 验收
- 与网页端参考逐项对照：间距、字号、颜色 token、圆角、分隔线、空状态/加载/错误态一致或有意识的移动端适配（在提交说明里列出差异及理由）。
- 明暗两种模式、默认 neutral 主题下截图无异常；不引入新的硬编码颜色。
- 本页面的顶栏已换成 `CodegTopBar`（T01）。
- CI `verify.yml` 通过。
