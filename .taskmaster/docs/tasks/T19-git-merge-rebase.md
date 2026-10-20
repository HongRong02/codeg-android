# T19 Git merge / rebase / reset 与冲突处理

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T05　可并行：T17–T20 互相并行
- 分支：`task/T19-git-merge-rebase`

## 目标
支持 merge、rebase、reset，以及操作产生冲突时的处理：列出冲突、按文件选择 ours/theirs 或查看三方版本、继续或中止。

## 需求
- merge / rebase：选择目标分支后执行 `git_merge` / `git_rebase`（二次确认写明“将 X 合并到当前分支 Y”）。
- reset：`git_reset`，模式（soft/mixed/hard，以 `GitResetParams` 为准）+ 目标提交；hard 需要输入分支名确认。
- 冲突：`git_has_merge_head` / `git_list_conflicts` 检测；冲突列表页对每个文件可：查看三方版本（`git_conflict_file_versions`）、选择 ours/theirs 后用 `git_resolve_conflict` 写入；全部解决后 `git_continue_operation`，或 `git_abort_operation` 中止。
- 手机端不做三栏手动编辑器，只做整文件 ours/theirs 选择与查看。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region git-merge`）
- 新建：`A/core/model/wire/MergeOpsRequests.kt`、`A/core/model/MergeOps.kt`
- 新建：`A/feature/projects/git/MergeOpsSheet.kt`、`MergeOpsViewModel.kt`（独立 ViewModel，不往 `ProjectDetailViewModel` 加状态）
- 修改：`A/feature/projects/git/GitActionsMenu.kt`（T05 建立的顶栏 Git 菜单，只追加一个菜单项及其打开 Sheet 的状态）
- 新建：`R/values/strings_git_merge.xml`、`R/values-zh-rCN/strings_git_merge.xml`
- 新建：`A/feature/projects/git/ConflictListScreen.kt`（冲突列表）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/git.rs`）：`482 git_merge` / `489 git_rebase` · `PathBranchParams :26`；`325 git_reset` · `GitResetParams :319`；`250 git_has_merge_head`；`565 git_list_conflicts`；`572 git_conflict_file_versions` · `PathFileParams :19`；`579 git_resolve_conflict` · `PathFileContentParams :62`；`593 git_continue_operation` / `586 git_abort_operation` · `PathOperationParams :55`；`243 git_start_pull_merge` · `GitStartPullMergeParams :238`
- app 已有 git 调用写法：`core/network/CodegClient.kt:333 gitStatus` … `:420 gitRollbackFile`（照此风格）；`core/model/Git.kt`
- 入口：`feature/projects/git/GitActionsMenu.kt`（T05 新建，顶栏 overflow 菜单）
- Sheet 写法参考：`feature/projects/BranchPickerSheet.kt:55`、`CommitSheet.kt:72`；凭据处理参考 `ProjectDetailViewModel.kt` 中 `GitCredentialPrompt:365` 的用法
- 结果提示：`feature/projects/GitStatusStrip.kt:42`

## 参考（只读，按需打开）
- `WEB/components/layout/conflict-dialog.tsx`、`WEB/components/merge/merge-workspace.tsx`、`conflict-parser.ts`、`WEB/app/merge/page.tsx`

## 验收
- 在测试仓库上制造冲突，完成“检测 → 选择 → 继续”和“中止”两条路径的真机验证。
- reset hard 需要输入确认。
- CI 通过。
