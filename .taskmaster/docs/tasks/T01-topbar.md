# T01 共享顶栏组件 CodegTopBar

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：high　依赖：无
- 分支：`task/T01-topbar`

## 目标
把散落在各页面的 Material `TopAppBar` 统一成一个按网页端 header 样式实现的共享组件，作为后续逐页打磨的前置。

## 需求
- 新建 `CodegTopBar`：参数至少包括 `title: String`、`subtitle: String? = null`、`navigationIcon`（返回/无）、`actions: @Composable RowScope.() -> Unit`、`scrollBehavior: TopAppBarScrollBehavior? = null`。
- 尺寸对齐网页端 header：高度、标题字号/字重、图标按钮尺寸（web `size-8`/`size-9` 的 ghost icon button）与点击热区（≥48dp 触控目标，视觉尺寸按 web）。
- 背景用 `--background`，底部 1px `--border` 分隔线（滚动时出现，可选），不用 Material 默认的 tonal elevation。
- 同时提供 `CodegIconButton`（ghost 样式图标按钮），供顶栏 actions 使用。
- 仅在 `SessionListScreen` 一处替换为新组件作为示范；其它页面由各自打磨任务替换。

## 改动范围
- 新建：`A/core/designsystem/component/TopBar.kt`
- 修改：`A/feature/sessions/SessionListScreen.kt`（仅 105 行附近的 `TopAppBar(...)` 调用）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 现有 TopAppBar 调用（全部，供了解差异；本任务只改第一处）：`feature/sessions/SessionListScreen.kt:105`、`feature/sessiondetail/SessionDetailScreen.kt:191`、`feature/projects/ProjectListScreen.kt:80`、`feature/projects/ProjectDetailScreen.kt:100`、`feature/activity/ActivityScreen.kt:66`、`feature/search/SearchScreen.kt:70`、`feature/server/ServerListScreen.kt:67`、`feature/server/ServerEditorScreen.kt:43`、`feature/settings/SettingsScreen.kt:108`
- 主题 token：`A/core/designsystem/theme/Color.kt:13 CodegColors`、`:35 Color.withOpacity`；尺寸：`theme/Dimens.kt:14 CodegDimens`；圆角：`theme/Shape.kt`
- 现有按钮组件：`component/Buttons.kt:44 PrimaryButton`、`:92 CodegSegmented`

## 参考（只读，按需打开）
- `WEB/components/conversations/conversation-detail-header.tsx`（会话页 header）
- `WEB/components/layout/folder-title-bar.tsx`、`WEB/components/layout/app-title-bar.tsx`
- `WEB/components/ui/button.tsx`（icon/ghost 变体尺寸）
- `WEB/app/globals.css`（只查 `--background` `--border` 等 token 名）

## 验收
- `CodegTopBar` 与 `CodegIconButton` 有 KDoc，参数风格与 `component/` 下其它组件一致。
- 会话列表页顶栏已替换，视觉与网页端 header 一致；明暗模式正常。
- CI `verify.yml` 通过。
