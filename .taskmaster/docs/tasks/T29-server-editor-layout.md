# T29 服务器编辑页：测试结果不挤动保存按钮；列表选中反馈

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：无　可并行：与 T27、T28 并行
- 分支：`task/T29-server-editor-layout`

## 目标
测试连接后结果文字插在“测试连接”和“保存”两个按钮之间，保存按钮下移约 100px，用户容易点空。服务器列表中点击服务器行无可见反馈。

## 需求
- 测试结果改为不改变按钮位置：例如放在“测试连接”按钮内/旁的状态行，并预留固定高度（无结果时占位），或移到保存按钮下方。
- 服务器列表：当前选中的服务器有明确标识（勾选图标或 `--primary` 边框）；点击行后切换并给出反馈（返回上一页或 toast，选与网页端/现有导航一致的方式，在提交说明中写明）。

## 改动范围
- 修改：`A/feature/server/ServerEditorScreen.kt`、`A/feature/server/ServerListScreen.kt`
- 新建：`R/values*/strings_server_editor.xml`（如需新文案）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `feature/server/ServerEditorScreen.kt`：`ServerEditorScreen`（两个 `PrimaryButton` 与 `when (val test = viewModel.test)` 之间，约 94–118 行）
- `feature/server/ServerListScreen.kt`：`ServerListScreen`、`ServerRow`（`clickable(onClick = onClick)`）
- 选中态来源：`ServerListScreen(servers, selectedId, onSelect, …)` 参数

## 参考（只读，按需打开）
- `docs/smoke/real-server-2026-10-20.md` 的 F3
- `WEB/components/ui/field.tsx`（表单状态行样式）

## 验收
- 测试连接前后“保存”按钮位置不变；列表中当前服务器一眼可辨。
- CI 通过。
