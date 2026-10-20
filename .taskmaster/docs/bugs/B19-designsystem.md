# B19 designsystem：抽屉/对话框打开时 Toast 被盖住

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 3 轮修复 |
| 区域（冲突域） | designsystem：`A/core/designsystem/component/**` |
| 分支 / 基准 | `fix/B19-designsystem` / 最新 `sit` |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件。只读"定位"里列出的代码，只改"改动范围"里的文件。截图在 `/mnt/e/claude/apk/shots/`。

## 问题清单
### B19-1 Toast 画在主窗口里，被抽屉/对话框窗口挡住
- 来源：P-041 真机。项目 sandbox → Git 菜单 → 切换分支 → 点被 worktree 占用的分支 `automation/1/run-1`。服务端拒绝（`git checkout failed`），面板保持打开，**屏幕上看不到任何反馈**：`shots/git-switch-error.png`。按返回键关掉面板后，才看到底下的"操作失败 · git checkout failed"：`shots/git-switch-behind.png`。
- 归属原任务：T52（`Toasts.kt`），涉及 T44（`Sheets.kt`、`Dialogs.kt`）
- 根因：`CodegToasterHost`（`Toasts.kt`）把 toast 层画在主界面的 Compose 树里；`CodegSheet`/`CodegFullScreenSheet`/`CodegDialog`/`CodegConfirmDialog` 是独立的窗口（Dialog / ModalBottomSheet），盖在主窗口之上。
- 预期：任何时候 toast 都显示在最上层，包括抽屉、对话框打开时；不能重复显示两份。可选方案（选一种并在修复说明里写明理由）：
  1. 在 `CodegSheet`/`CodegDialog` 等弹层的内容里叠加一个 toast 层，读同一个 `LocalCodegToaster` 的状态；主窗口的宿主在有弹层时不画（需要一个"当前有几层弹层"的计数，放在 toaster 里）。
  2. 宿主改用一个在所有窗口之上的 `Popup`（注意 Popup 也是窗口，要确认它能盖过 ModalBottomSheet，以及触摸穿透与 insets 的处理）。
- 要求：toast 位置仍避开导航栏和输入法；弹层关闭后正在显示的 toast 不能消失或重播；滑动关闭仍可用。

### B19-2 补一个可复现的 Gallery 示例
- 在 T52 的 Gallery 里加一个"在抽屉里触发 toast"的按钮（打开 `CodegSheet`，在里面点按钮弹出 error toast），作为回归用例。

## 改动范围
- `A/core/designsystem/component/**`（`Toasts.kt`、`Sheets.kt`、`Dialogs.kt` 及其 Gallery）。不改 `A/feature/**`。

## 验收
- CI（verify.yml）在 `fix/B19-designsystem` 上通过。
- 复测用例（协调方在 sit 上执行）：P-041（切换到被占用分支，面板打开时就能看到错误 toast）、P-153（Gallery 新示例）、P-003、P-045（不重复显示）。
- 提交信息写 `Fixes B19-k (T52)`。
