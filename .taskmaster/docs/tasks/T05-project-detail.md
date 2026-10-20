# T05 打磨：项目详情页（文件/改动/提交及各 Sheet）

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T01　可并行：与 T02–T04、T06–T11 并行；T17–T20 依赖本任务
- 分支：`task/T05-project-detail`

## 目标
对照网页端逐项打磨项目详情页（文件/改动/提交及各 Sheet），并把顶栏换成 `CodegTopBar`。

## 需求
- 顶栏替换为 `CodegTopBar` / `CodegIconButton`。
- 对照网页端参考检查：列表行高、内边距、字号层级、次要文字颜色（`--muted-foreground`）、卡片（`Modifier.webSurface`）、分隔线、徽章、空状态/加载/错误态。
- 只做视觉与布局调整，不改业务逻辑、不改 ViewModel 的对外状态结构。

## 改动范围
- 新建：`A/feature/projects/git/GitActionsMenu.kt`——顶栏 actions 里的 overflow 菜单（`CodegIconButton` + `DropdownMenu`），本任务只放现有操作（如刷新、切换分支），供 T17–T20 各追加一项；每项是一个独立的 `DropdownMenuItem` 块，带注释标明归属任务，便于并行合并
- 修改：`A/feature/projects/ProjectDetailScreen.kt`、`FolderFilesView.kt`、`FolderChangesView.kt`、`FolderCommitsView.kt`、`GitStatusStrip.kt`、`GitChangeVisuals.kt`、`BranchPickerSheet.kt`、`CommitSheet.kt`、`CloneRepoSheet.kt`、`DirectoryBrowserSheet.kt`、`GitCredentialSheet.kt`
- 不改：`ProjectDetailViewModel.kt`（T17–T20 会扩展）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `ProjectDetailScreen.kt`：`54 ProjectTab`、`62 ProjectDetailScreen`（顶栏 100 行）、`148 Header`、`173 SegmentedTabs`
- `FolderFilesView.kt:55`、`FolderChangesView.kt:72`（`213 ChangeRow`、`228 WorkingDiff`）、`FolderCommitsView.kt:71`（`216 CommitRow`、`239 CommitDetail`）
- Sheet 顶栏：`CloneRepoSheet.kt:81`、`CommitSheet.kt:126`、`DirectoryBrowserSheet.kt:86`、`GitCredentialSheet.kt:131`
- `GitStatusStrip.kt:42`、`GitChangeVisuals.kt:33 GitChangeBadge`、`BranchPickerSheet.kt:55`

## 参考（只读，按需打开）
- `WEB/components/layout/aux-panel-file-tree-tab.tsx`、`aux-panel-git-changes-tab.tsx`、`aux-panel-git-log-tab.tsx`、`branch-selector-list.tsx`、`commit-dialog.tsx`、`clone-dialog.tsx`
- `WEB/components/diff/`（diff 样式）

## 验收
- 与网页端参考逐项对照：间距、字号、颜色 token、圆角、分隔线、空状态/加载/错误态一致或有意识的移动端适配（在提交说明里列出差异及理由）。
- 明暗两种模式、默认 neutral 主题下截图无异常；不引入新的硬编码颜色。
- 本页面的顶栏已换成 `CodegTopBar`（T01）。
- CI `verify.yml` 通过。
