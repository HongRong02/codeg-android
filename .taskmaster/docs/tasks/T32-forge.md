# T32 Forge：Issue / PR 浏览与操作

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：与 T31、T33–T35 并行
- 分支：`task/T32-forge`

## 目标
在项目详情中查看该项目远端（GitHub/Gitea/GitLab）的 Issue 和 PR，能评论、改状态、新建 issue、合并 PR，并能从 issue 一键建 work task。

## 需求
- 入口：项目详情右上角 Git 菜单（`GitActionsMenu`）追加一项"Issues 与 PR"，打开全屏 Sheet（沿用该文件里 T17–T20 的"一块 state + sheet、一个菜单项"写法）。`folder_forge_remote` 返回无远端时不显示该项。
- 列表：`forge_list_issues`，分 Issue / PR 两个标签，状态筛选（open/closed），标签页数量用 `forge_tab_count`。行显示编号、标题、作者、标签、更新时间。
- 详情：`forge_change_detail`（PR 详情）+ `forge_change_files`（改动文件列表，可用现有 `DiffView` 显示 patch）；`forge_list_comments` 评论列表，底部输入框 `forge_create_comment`。
- 操作：关闭/重开 `forge_set_item_state`（确认）；新建 issue `forge_create_issue`（标题、正文、标签可选 `forge_list_labels`）；合并 PR：先 `forge_merge_options` 取可用方式，再 `forge_merge_change`（确认）。
- 建任务：详情页"创建任务"按钮调用 `work_task_create_from_forge`；已建过时用 `work_task_lookup_by_source` 显示"已有任务"。
- 不做：`forge_settings_*`、`forge_auto_*`、`forge_webhook`、`forge_identity`。
- 未配置版本控制令牌时，显示服务端返回的错误（T27 本地化），提示去 设置 → 版本控制。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region forge`，放在 `// region Transport` 之前）
- 新建：`A/core/model/wire/ForgeRequests.kt`、`A/core/model/Forge.kt`
- 新建：`A/feature/projects/forge/` 下文件（如 `ForgeSheet.kt`、`ForgeViewModel.kt`、`ForgeDetail.kt`）
- 修改：`A/feature/projects/git/GitActionsMenu.kt`——在 `// T20` 块后加 `// T32: forge` state/sheet 块，菜单末尾加一个 `DropdownMenuItem`
- 新建：`R/values/strings_forge.xml`、`R/values-zh-rCN/strings_forge.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/forge.rs`）：folder_forge_remote `:157` · `FolderParams :26`；list_issues `:166` · `ListIssuesParams :40`；tab_count `:175` · `TabCountParams :50`；list_labels `:184` · `LabelsParams :32`；list_comments `:193` · `ListCommentsParams :63`；create_comment `:202` · `CreateCommentParams :73`；set_item_state `:211` · `SetItemStateParams :82`；create_issue `:220` · `CreateIssueParams :89`；change_detail `:229` · `ChangeDetailParams :96`；change_files `:238` · `ChangeFilesParams :103`；merge_options `:256` · `MergeOptionsParams :118`；merge_change `:265` · `MergeChangeParams :126`；work_task_create_from_forge `:274` · `CreateFromForgeParams :133`；work_task_lookup_by_source `:283` · `LookupParams :139`。返回模型在 `codeg/src-tauri/src/` 下 grep `Forge`。
- app：`A/feature/projects/git/GitActionsMenu.kt`（全文，约 130 行，T17–T20 块示例）；`.taskmaster/docs/code-index/feature-projects.md`；`core/designsystem/diff/DiffView.kt:41`；markdown 渲染组件在 `.taskmaster/docs/code-index/core-designsystem.md` 里查 `Markdown`。

## 参考（只读，按需打开）
- `WEB/components/forge/forge-page.tsx`、`forge-issue-row.tsx`、`forge-issue-detail-sheet.tsx`、`forge-new-issue-dialog.tsx`、`forge-start-dialog.tsx`

## 验收
- 测试服务端无 forge 远端时菜单项隐藏、不报错。有条件时（项目远端为 GitHub 且已配令牌）真机验证列表/详情/评论。
- CI 通过。
