# T30 键盘弹出时表单和底部按钮不被遮挡

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：high　依赖：无
- 分支：`task/T30-ime-insets`

## 目标
真机上提交页（`CommitSheet`）输入提交信息时，键盘完全盖住“提交 / 提交并推送”按钮，用户必须先手动收起键盘。全 app 只有 `ServerEditorScreen`、`SessionDetailScreen`、`AgentDetailScreen` 处理了键盘（`imePadding`），其余带输入框的表单/Sheet 都有同样问题。

## 需求
- 下列文件中，让内容区随键盘收缩：在承载表单的根容器（Sheet 内容 / 全屏 Dialog / Scaffold 内容）加 `Modifier.imePadding()`（或对 `ModalBottomSheet` 使用合适的 `windowInsets` / `contentWindowInsets`），保证底部操作按钮在键盘弹出时可见；可滚动内容保持可滚动。
- 不要对已由外层处理键盘的组件重复加（会导致双倍留白）：`ComposeBar.kt`、`interactive/AskQuestionCard.kt`、`interactive/PlanApprovalCard.kt`（在 `SessionDetailScreen` 的 `imePadding` 列内）、`AgentConfigKimiView.kt`、`AgentConfigPiView.kt`（在 `AgentDetailScreen` 内）——除非确认它们在独立的 Dialog/Sheet 窗口中显示。
- 设置子页（`McpScreen`、`ModelProvidersScreen`、`QuickMessagesScreen`、`SkillsScreen`、`SystemScreen`、`VersionControlScreen`、`ChatChannelsScreen`、`ChatGlobalSettingsScreen`）：若输入框在全屏 Dialog/编辑器中，加在该 Dialog 根；若在 `SettingsScreen` 承载的普通内容中，在 `SettingsScreen` 的内容 `Box` 上加一次即可（同时修改 `SettingsScreen.kt`）。
- 注意 Dialog 窗口需 `DialogProperties(decorFitsSystemWindows = false)` 或 `usePlatformDefaultWidth = false` 时 insets 才能被 Compose 获取，按实际情况处理。

## 改动范围
- 修改：`A/feature/projects/BranchPickerSheet.kt`、`CloneRepoSheet.kt`、`CommitSheet.kt`、`GitCredentialSheet.kt`
- 修改：`A/feature/projects/git/BranchManageSheet.kt`、`GitMiscSheet.kt`、`MergeOpsSheet.kt`、`StashSheet.kt`
- 修改：`A/feature/search/SearchScreen.kt`、`A/feature/serverlogs/ServerLogsScreen.kt`
- 修改：`A/feature/sessiondetail/AgentOptionsSheet.kt`、`SessionActions.kt`（仅其中的重命名等输入弹窗）
- 修改：`A/feature/settings/McpScreen.kt`、`ModelProvidersScreen.kt`、`QuickMessagesScreen.kt`、`SkillsScreen.kt`、`SystemScreen.kt`、`VersionControlScreen.kt`、`ChatChannelsScreen.kt`、`ChatGlobalSettingsScreen.kt`、`SettingsScreen.kt`
- 只改 insets 相关的 Modifier / 参数，不改布局结构和业务逻辑。
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 已有正确用法：`feature/sessiondetail/SessionDetailScreen.kt`（`Column(Modifier.imePadding())`，约 228 行）、`feature/settings/AgentDetailScreen.kt`（`Column(Modifier.fillMaxSize().imePadding())`，约 123 行）、`feature/server/ServerEditorScreen.kt`（约 60 行）
- 各文件内输入框：`grep -n "TextField(\|CodegTextField(\|SecretField(" <文件>`

## 参考（只读，按需打开）
- `docs/smoke/real-server-2026-10-20.md` 的 F5

## 验收
- 每个改动的表单：键盘弹出时底部按钮可见可点，收起后无多余留白。
- CI 通过；协调方真机复测提交页。
