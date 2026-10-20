# T18 Git 分支管理

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T05　可并行：T17–T20 互相并行
- 分支：`task/T18-git-branches`

## 目标
在现有分支选择（`BranchPickerSheet`，支持切换/新建）之外，增加分支管理：删除本地/远程分支、更新分支、与某分支比较。

## 需求
- 分支管理 Sheet：基于 `git_list_branches` 列出本地与远程分支（含 ahead/behind 若服务端返回）。
- 删除本地分支：`git_delete_branch`（未合并时服务端报错要原样提示；提供强制删除选项若参数支持）。
- 删除远程分支：`git_delete_remote_branch`（二次确认，显示 remote/branch）。
- 更新分支：`git_update_branch`。
- 比较：`git_diff_with_branch`，用 `DiffView` 显示。
- 不改 `BranchPickerSheet` 的现有行为。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region git-branches`）
- 新建：`A/core/model/wire/BranchManageRequests.kt`、`A/core/model/BranchManage.kt`
- 新建：`A/feature/projects/git/BranchManageSheet.kt`、`BranchManageViewModel.kt`（独立 ViewModel，不往 `ProjectDetailViewModel` 加状态）
- 修改：`A/feature/projects/git/GitActionsMenu.kt`（T05 建立的顶栏 Git 菜单，只追加一个菜单项及其打开 Sheet 的状态）
- 新建：`R/values/strings_git_branches.xml`、`R/values-zh-rCN/strings_git_branches.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/git.rs`）：`330 git_list_branches`；`504 git_delete_branch` · `GitDeleteBranchParams :498`；`548 git_delete_remote_branch` · `GitDeleteRemoteBranchParams :541`；`657 git_update_branch` · `GitUpdateBranchParams :650`；`417 git_diff_with_branch` · `GitDiffWithBranchParams :411`
- app 已有 git 调用写法：`core/network/CodegClient.kt:333 gitStatus` … `:420 gitRollbackFile`（照此风格）；`core/model/Git.kt`
- 入口：`feature/projects/git/GitActionsMenu.kt`（T05 新建，顶栏 overflow 菜单）
- Sheet 写法参考：`feature/projects/BranchPickerSheet.kt:55`、`CommitSheet.kt:72`；凭据处理参考 `ProjectDetailViewModel.kt` 中 `GitCredentialPrompt:365` 的用法
- 结果提示：`feature/projects/GitStatusStrip.kt:42`
- 已有分支调用：`CodegClient.kt:354 gitListAllBranches`、`:365 gitCheckout`、`:370 gitNewBranch`

## 参考（只读，按需打开）
- `WEB/components/layout/branch-dropdown.tsx`、`branch-selector-list.tsx`、`branch-tree-collapsible.tsx`

## 验收
- 各操作真机验证；删除类操作有二次确认。
- CI 通过。
