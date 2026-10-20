# B20 designsystem：输入法弹出时 Toast 盖在键盘上

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 4 轮修复 |
| 区域（冲突域） | designsystem：`A/core/designsystem/component/**` |
| 分支 / 基准 | `fix/B20-designsystem` / 最新 `sit`（fc1e778 之后） |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件。只读"定位"里列出的代码，只改"改动范围"里的文件。截图在 `/mnt/e/claude/apk/shots/`。

## 问题清单
### B20-1 Toast 不避让输入法
- 来源：P-013（断网时发送失败的 error 提示）真机。会话详情，输入框聚焦、键盘弹出，停掉测试服后点发送：约 4 秒后发送失败，乐观气泡撤回、草稿恢复，弹出 error toast"会话提示 · unexpected end of stream on http://127.0.0.1:3082/…"，但 toast **画在键盘上方的同一位置，盖住了键盘的下两排**：`shots/b20-toast-ime.png`；逐帧见 `shots/b20v/grid.png`。
  （最初登记的"没有任何提示"是截图过早：失败要等连接超时约 4 秒。发送失败的业务流程——撤回气泡、恢复草稿、提示一次 error——与 main 一致，不在本单范围。）
- 归属原任务：T52（`Toasts.kt`），由 B19 引入：B19 把 `ToastLayer` 改成放在 `Popup` 里，`Popup` 是独立窗口，拿不到宿主窗口的 IME insets，`Modifier.imePadding()` 在里面不生效。
- 定位：`Toasts.kt` 的 `ToastLayer`（`Popup(...)` 内 `windowInsetsPadding(WindowInsets.safeDrawing).imePadding()`）。主窗口宿主和弹层宿主都走这里，所以两种情况都会盖住键盘。
- 预期：键盘弹出时 toast 底边在键盘之上（与 B19 之前一致，sonner 底部右侧位置）；键盘收起时回到导航栏之上；不影响 B19 的"弹层打开时 toast 在最上层、只显示一份、不重播"。
- 建议做法：在 `Popup` 之外（宿主窗口的组合里）读取 `WindowInsets.ime` 与 `WindowInsets.safeDrawing` 的底部/左右值，换算成 dp 传给 Popup 内容作为 padding（或用 `PopupPositionProvider` 把底边定到这个偏移）；在弹层窗口（Dialog / ModalBottomSheet）里同样从该窗口自身的 insets 读取。需要确认 vivo/Android 11 上 Dialog 窗口里 `WindowInsets.ime` 能拿到值（`decorFitsSystemWindows=false` 的情况）。

### B20-2 Gallery 回归示例
- 在 T52 的 Gallery 里加一个带输入框的示例：输入框获得焦点（键盘弹出）后点按钮弹 error toast，用来复测 B20-1。

## 改动范围
- `A/core/designsystem/component/**`（`Toasts.kt` 及其 Gallery，必要时 `Sheets.kt`、`Dialogs.kt`）。不改 `A/feature/**`。

## 验收
- CI（verify.yml）在 `fix/B20-designsystem` 上通过。
- 复测用例（协调方在 sit 上执行）：P-013 断网发送（键盘打开时 toast 在键盘之上）、P-153（Gallery 新旧示例）；回归 P-041（抽屉里的 toast 仍在最上层）、P-003、P-045（只显示一份）。
- 提交信息写 `Fixes B20-k (T52)`。
