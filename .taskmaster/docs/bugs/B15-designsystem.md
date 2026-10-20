# B15 designsystem：圆角与网页端（radix-maia）不一致；标签页溢出被截断

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 2 轮修复 |
| 区域（冲突域） | designsystem：`A/core/designsystem/component/**`、`A/core/designsystem/theme/Shape.kt`（只允许追加形状常量，不改现有值） |
| 分支 / 基准 | `fix/B15-designsystem` / 最新 `sit` |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件和 `.taskmaster/docs/ui-alignment.md` 里的**圆角对照表**。只读"定位"里列出的代码，只改"改动范围"里的文件。截图在 `/mnt/e/claude/apk/shots/`。

## 问题清单
### B15-1 组件圆角按 shadcn 默认主题做了，应按 radix-maia
- 来源：真机对照网页端源码（2026-10-20）。例如按钮：`WEB/components/ui/button.tsx` 是 `rounded-4xl`（36dp 高时等效胶囊），而 `CodegButton` 用的是 `CodegShapes.extraSmall`（8dp）；旧版 `PrimaryButton` 原本是 `PillShape`，T45 把它改小了。
- 归属原任务：T43–T46、T53（以及当时错误的需求文档）
- 要求：逐个组件对照 `WEB/components/ui/*.tsx` **实际的** `rounded-*` class，按 `ui-alignment.md` 的对照表换算后修正。至少覆盖：
  - `rounded-4xl`：`CodegButton`（所有 variant 和 size，Icon 尺寸用圆形）、`CodegBadge`、`CodegDialog`/`CodegConfirmDialog` 的容器、`CodegTabs` 列表、`CodegSelect`/`CodegCombobox` 触发器、`CodegTextField` 单行输入、`CodegInputGroup`（已是 4xl，确认即可）
  - `rounded-2xl`：`CodegCard`、`CodegPopover`、下拉菜单容器（`Menus.kt`）、`CodegTooltip`、`CodegCommandList` 容器
  - `rounded-xl`：下拉菜单项、`CodegTabs` 的选中项（trigger）、textarea 变体、`CodegSkeleton`
  - `rounded-full`：`CodegSwitch`、`CodegProgress`、`CodegAvatar`
  - 抽屉（`Sheets.kt`）按 `drawer.tsx` 的顶部圆角
  - 其他组件也逐个检查，结果列进修复说明。
- 输出：在修复说明里给出一张表：组件 | 网页端 class | 修改前 | 修改后。
- 注意：只改形状，不改颜色、尺寸、内边距；`PrimaryButton` 等兼容封装要跟着新形状走。

### B15-2 标签页溢出被截断（原 B11）
- 来源：P-117，`shots/sw-工作任务-dark.png`：工作任务看板 4 个分栏标签挤在一行，"进行中 (…"被截断。
- 归属原任务：T46（`Tabs.kt`）
- 预期：标签总宽度超出可用宽度时，`CodegTabs` 自动改为可横向滚动（选中项滚动到可见位置），不截断文字；宽度足够时保持等分。**不改调用处**。

## 改动范围
- `A/core/designsystem/component/**`；`theme/Shape.kt` 只允许追加常量（例如 `rounded-sm`、`rounded-lg` 对应的 6dp、10dp）。
- 相关单测和 Gallery（可以补充展示）。不改 `A/feature/**`。

## 验收
- CI（verify.yml）在 `fix/B15-designsystem` 上通过。
- 复测用例（协调方在 sit 上执行）：Gallery P-142–P-169（明/暗），以及 P-006、P-013、P-039、P-070、P-117。
- 提交信息写 `Fixes B15-k (Txx)`。
