# T35 项目分组、别名、颜色

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：无　可并行：与 T31–T34 并行
- 分支：`task/T35-folder-groups`

## 目标
项目列表按网页端侧栏的分组、别名、颜色显示，并能在手机上编辑。

## 需求
- 模型：`FolderDetail` 追加可空字段 `alias: String?`、`groupId: Int?`（服务端 `models/folder.rs` 已返回；注意 JSON 字段名大小写按服务端 serde 规则）。`color` 已存在。
- 显示：项目列表按 `list_folder_groups` 分组（分组标题可折叠），未分组的放顶层；排序与网页端一致（参考 `sidebar-conversation-grouping.ts`）。项目行显示 `别名 [名称]`（有别名时）和颜色圆点。
- 编辑：项目行长按菜单：设置别名 `update_folder_alias`、颜色 `update_folder_color`（用网页端同一组预设颜色）、移到分组 `set_folder_group`。
- 分组管理：新建 `create_folder_group`、改名 `update_folder_group`、删除 `delete_folder_group`（确认）。
- 不做：`apply_sidebar_layout`、拖拽排序。会话列表的分组不在本任务范围。

## 改动范围
- 修改（只追加字段）：`A/core/model/Folder.kt:16 FolderDetail`
- 修改：`A/core/network/CodegClient.kt`（新增 `// region folder-groups`，放在 `// region Transport` 之前）
- 新建：`A/core/model/wire/FolderGroupRequests.kt`、`A/core/model/FolderGroup.kt`
- 修改：`A/feature/projects/ProjectListScreen.kt`、`ProjectsViewModel.kt`
- 新建：`A/feature/projects/groups/` 下文件
- 新建：`R/values/strings_folder_groups.xml`、`R/values-zh-rCN/strings_folder_groups.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/folders.rs`）：list_folder_groups `:164`；create `:180` · `CreateFolderGroupParams :174`；update `:205` · `UpdateFolderGroupParams :197`；delete `:227` · `FolderGroupIdParams :223`；set_folder_group `:257` · `SetFolderGroupParams :251`；update_folder_color `:278` · `UpdateFolderColorParams :273`；update_folder_alias `:295` · `UpdateFolderAliasParams :290`。模型 `codeg/src-tauri/src/models/folder.rs`（`alias`、`group_id`、FolderGroup 结构）。
- app：`A/feature/projects/ProjectListScreen.kt:72 ProjectListScreen`、`:242 FolderRow(` 调用、`:285 FolderRow`、`:398 sortedFolders`；`ProjectsViewModel.kt:27`、`:68 fetch`、`:122 ProjectsUiState`。

## 参考（只读，按需打开）
- `WEB/components/conversations/sidebar-folder-group-header.tsx`、`sidebar-conversation-grouping.ts`；颜色预设在 `WEB/lib/` 下 grep `folder` + `color`

## 验收
- 测试服务端上：新建分组 → 把项目移入 → 设别名和颜色 → 列表正确显示 → 网页端同步可见。
- CI 通过。
