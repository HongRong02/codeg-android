# T20 Git：按文件提交、remote 管理、init、worktree

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：T05　可并行：T17–T20 互相并行
- 分支：`task/T20-git-misc`

## 目标
补齐剩余常用 git 操作：按文件选择提交（如现有 CommitSheet 尚未支持）、remote 增删改、对非仓库目录 git init、worktree 添加/移除。

## 需求
- 先确认现有 `CommitSheet` 是否已支持按文件选择（`CodegClient.gitCommit` 已有 `files` 参数）；若已支持，`git_commit_files` 仅在语义不同时接入，并在提交说明中写明结论。
- remote 管理：列表（已有 `gitListRemotes`）+ 添加 `git_add_remote`、删除 `git_remove_remote`、改 URL `git_set_remote_url`、单独 fetch `git_fetch_remote`。
- git init：项目详情在“非 git 仓库”错误态下提供“初始化仓库”按钮（`git_init`）。
- worktree：`git_worktree_add` / `git_remove_worktree`（已有 `resolveWorktreeFolder`/`openWorktreeFolder` 可复用）。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region git-misc`）
- 新建：`A/core/model/wire/GitMiscRequests.kt`、`A/core/model/GitMisc.kt`
- 新建：`A/feature/projects/git/GitMiscSheet.kt`、`GitMiscViewModel.kt`（独立 ViewModel，不往 `ProjectDetailViewModel` 加状态）
- 修改：`A/feature/projects/git/GitActionsMenu.kt`（T05 建立的顶栏 Git 菜单，只追加一个菜单项及其打开 Sheet 的状态）
- 新建：`R/values/strings_git_misc.xml`、`R/values-zh-rCN/strings_git_misc.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/git.rs`）：`204 git_commit_files` · `GitCommitBranchesParams :95`；`461 git_add_remote` / `475 git_set_remote_url` · `PathNameUrlParams :47`；`468 git_remove_remote` · `PathNameParams :40`；`740 git_fetch_remote` · `GitFetchRemoteParams :734`；`231 git_init`；`297 git_worktree_add` · `GitWorktreeAddParams :289`；`522 git_remove_worktree` · `GitRemoveWorktreeParams :514`
- app 已有 git 调用写法：`core/network/CodegClient.kt:333 gitStatus` … `:420 gitRollbackFile`（照此风格）；`core/model/Git.kt`
- 入口：`feature/projects/git/GitActionsMenu.kt`（T05 新建，顶栏 overflow 菜单）
- Sheet 写法参考：`feature/projects/BranchPickerSheet.kt:55`、`CommitSheet.kt:72`；凭据处理参考 `ProjectDetailViewModel.kt` 中 `GitCredentialPrompt:365` 的用法
- 结果提示：`feature/projects/GitStatusStrip.kt:42`
- 已有：`CodegClient.kt:375 resolveWorktreeFolder`、`:379 openWorktreeFolder`、`:387 gitCommit`、`:411 gitListRemotes`；`feature/projects/CommitSheet.kt:72`

## 参考（只读，按需打开）
- `WEB/components/layout/remote-manage-dialog.tsx`、`commit-dialog.tsx`

## 验收
- 各操作真机验证；删除 remote / 移除 worktree 有二次确认。
- CI 通过。
