# T37 导入本地会话

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：无　可并行：与 T36、T38–T40 并行
- 分支：`task/T37-import-sessions`

## 目标
在手机上扫描服务端机器上各 agent 已有的本地会话（Claude Code、Codex 等），勾选后导入 codeg。

## 需求
- 入口：文件夹页右上角"添加"菜单（`ProjectListScreen.kt` 约 112 行的 `DropdownMenu`）追加一项"导入会话"，打开全屏 Sheet。
- 扫描：`scan_importable_sessions`，按 agent / 项目路径分组，显示标题、时间、是否已导入；支持全选/按组选。
- 导入：`import_selected_sessions`（选中项），结果显示导入数量与失败项；"全部导入" 可用 `import_local_conversations`（确认）。
- 导入完成后刷新项目列表（调用 `ProjectsViewModel` 现有 refresh）。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region import-sessions`，放在 `// region Transport` 之前）
- 新建：`A/core/model/wire/ImportSessionsRequests.kt`、`A/core/model/ImportSessions.kt`
- 新建：`A/feature/projects/importsessions/` 下文件
- 修改：`A/feature/projects/ProjectListScreen.kt`（只在添加菜单加一项 + 显示 Sheet 的 state）
- 新建：`R/values/strings_import_sessions.xml`、`R/values-zh-rCN/strings_import_sessions.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/conversations.rs`）：import_local_conversations `:235` · `ImportLocalConversationsParams :231`；scan_importable_sessions `:250`；import_selected_sessions `:269` · `ImportSelectedSessionsParams :265`。返回模型 grep `Importable`。
- app：`A/feature/projects/ProjectListScreen.kt:112 DropdownMenu`、`:117 fg_create 菜单项写法`；`ProjectsViewModel.kt:63 refresh`。

## 参考（只读，按需打开）
- `WEB/components/import-sessions/import-sessions-window.tsx`、`import-sessions-rows.tsx`

## 验收
- 测试服务端上扫描出 WSL 中已有的 agent 会话，导入 1 条后会话列表出现该会话。
- CI 通过。
