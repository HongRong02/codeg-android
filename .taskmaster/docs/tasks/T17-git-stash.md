# T17 Git stash 管理

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T05　可并行：T17–T20 互相并行（各自新建文件；`ProjectDetailScreen.kt` 只各加一个菜单项，合并时冲突很小）
- 分支：`task/T17-git-stash`

## 目标
在项目详情中提供 stash 管理：保存、列表、查看、应用、弹出、删除、清空。

## 需求
- 列表：`git_stash_list`，显示 ref、消息、时间。
- 保存：`git_stash_push`（消息可选、keep index 开关）。
- 查看：`git_stash_show` 返回 diff，用现有 `DiffView` 显示。
- 应用 / 弹出 / 删除：`git_stash_apply`、`git_stash_pop`、`git_stash_drop`；清空 `git_stash_clear`（二次确认）。
- 操作后刷新改动列表（调用方回调即可，不改 `ProjectDetailViewModel` 内部）。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region git-stash`）
- 新建：`A/core/model/wire/StashRequests.kt`、`A/core/model/Stash.kt`
- 新建：`A/feature/projects/git/StashSheet.kt`、`StashViewModel.kt`（独立 ViewModel，不往 `ProjectDetailViewModel` 加状态）
- 修改：`A/feature/projects/git/GitActionsMenu.kt`（T05 建立的顶栏 Git 菜单，只追加一个菜单项及其打开 Sheet 的状态）
- 新建：`R/values/strings_git_stash.xml`、`R/values-zh-rCN/strings_git_stash.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/git.rs`）：`345 git_stash_push` · `GitStashPushParams :339`；`360 git_stash_pop` · `GitStashPopParams :355`；`367 git_stash_list`；`374 git_stash_apply` · `PathStashRefParams :33`；`381 git_stash_drop`；`388 git_stash_clear`；`395 git_stash_show`
- app 已有 git 调用写法：`core/network/CodegClient.kt:333 gitStatus` … `:420 gitRollbackFile`（照此风格）；`core/model/Git.kt`
- 入口：`feature/projects/git/GitActionsMenu.kt`（T05 新建，顶栏 overflow 菜单）
- Sheet 写法参考：`feature/projects/BranchPickerSheet.kt:55`、`CommitSheet.kt:72`；凭据处理参考 `ProjectDetailViewModel.kt` 中 `GitCredentialPrompt:365` 的用法
- 结果提示：`feature/projects/GitStatusStrip.kt:42`
- diff 显示：`core/designsystem/diff/DiffView.kt:41 DiffView`

## 参考（只读，按需打开）
- `WEB/components/layout/stash-dialog.tsx`、`unstash-dialog.tsx`、`WEB/app/stash/page.tsx`

## 验收
- 本地测试服务端上全部操作真机验证；清空/删除有二次确认。
- CI 通过。
