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

#### 修复说明
- 原因：共享组件沿用了默认主题的小圆角，未按 radix-maia 的实际 class 映射。
- 改法：只调整形状；颜色、尺寸及内边距保持原值，兼容 PrimaryButton 通过 CodegButton 同步修正。Shape.kt 现有刻度足够，无需修改。

| 组件 | 网页端 class | 修改前 | 修改后 |
|---|---|---|---|
| CodegButton / PrimaryButton | button rounded-4xl | 8dp | 26dp；Icon 为 CircleShape |
| CodegIconButton | button rounded-4xl | 8dp | CircleShape |
| CodegBadge / CodegFilterChip | badge / combobox-chip rounded-4xl | 8dp | 26dp |
| CodegDialog / CodegConfirmDialog | dialog / alert-dialog rounded-4xl | 14dp | 26dp |
| CodegTabs 列表 / trigger | rounded-4xl / rounded-xl | 10dp / 8dp | 26dp / 14dp |
| CodegSegmented trigger | tabs rounded-xl | 主题 radiusMd | 14dp |
| CodegSelect / CodegCombobox trigger | rounded-4xl | PillShape | 26dp（高控件也固定半径） |
| CodegTextField 单行 / textarea | input rounded-4xl / textarea rounded-xl | PillShape / 14dp | 26dp / 14dp |
| SecretField | input rounded-4xl | PillShape | 26dp |
| CodegInputGroup | rounded-4xl | 26dp | 26dp，确认无需修改 |
| CodegCard / GlassCard 默认形状 | rounded-2xl | 14dp / 主题 radiusLg | 18dp |
| GlassRow | rounded-xl | 主题 radiusMd | 14dp |
| EmptyState / InlineError 操作按钮 | button rounded-4xl | 8dp | 26dp |
| Menus 下拉容器 / 菜单项 | dropdown-menu rounded-2xl / rounded-xl | 10dp / 8dp | 18dp / 14dp |
| CodegPopover / CodegTooltip | rounded-2xl | 18dp | 18dp，确认无需修改 |
| CodegCommandList 容器 / 项 | command 弹出容器 rounded-2xl / rounded-sm | 8dp / 6dp | 18dp / 6dp |
| CodegSkeleton | rounded-xl | 14dp | 14dp，确认无需修改 |
| CodegSwitch / CodegProgress / CodegAvatar | rounded-full | PillShape / CircleShape | 保持，已符合 |
| CodegSheet 顶部 | drawer rounded-4xl | 14dp | 26dp，底部仍为直角 |
| Select/Combobox 弹层 / 项 | rounded-2xl / rounded-xl | 18dp / 14dp | 保持，已符合 |
| Checkbox / Radio | rounded-[4px] / rounded-full | 4dp / CircleShape | 保持，已符合 |
| Toasts | rounded-lg | 10dp | 保持，已符合现有约定 |

- command.tsx 普通根节点仍为 rounded-md，CommandDialog 为 rounded-2xl；本次依 bug 单要求将共享列表容器用作 18dp 弹出面板形状。
- 其余组件已检查：AgentIcon、CodeBlock、Collapsible、FileViewer、FolderBadge、ImagePreview、ListRows、StateViews 非按钮区域及 Badges 内业务装饰无对应 ui/*.tsx 通用组件形状契约，保留现有形状；全屏 Sheet 无圆角；Feedback 的进度及骨架已符合。
- 复测：Gallery P-142–P-169 明/暗色；P-006、P-013、P-039、P-070，检查按钮全部 variant/size、兼容按钮、输入高度变化、弹窗与抽屉顶部形状。

### B15-2 标签页溢出被截断（原 B11）
- 来源：P-117，`shots/sw-工作任务-dark.png`：工作任务看板 4 个分栏标签挤在一行，"进行中 (…"被截断。
- 归属原任务：T46（`Tabs.kt`）
- 预期：标签总宽度超出可用宽度时，`CodegTabs` 自动改为可横向滚动（选中项滚动到可见位置），不截断文字；宽度足够时保持等分。**不改调用处**。

#### 修复说明
- 原因：每项固定 weight(1f) 且单行 Ellipsis，内容宽于均分宽度时被截断。
- 改法：按实际字体及字号测量单行文字并加原 16dp 水平内边距；所有项可放下时保持等分，否则各项至少为自然文字宽度并启用横向滚动。BringIntoViewRequester 在选中项、标签、可用宽度或字体变化时将选中项滚入视口。调用处无需修改。
- Gallery 增加四个长标签，复用已有本地化资源。
- 复测：P-117 四分栏（含计数）、点击首末标签和外部切换选中项、横竖屏/宽窄窗口、系统大字体、空列表及标签动态变化；宽度足够时仍等分，文字不省略，选中项可见。单项长于屏幕时可继续横向滚动阅读。

## 改动范围
- `A/core/designsystem/component/**`；`theme/Shape.kt` 只允许追加常量（例如 `rounded-sm`、`rounded-lg` 对应的 6dp、10dp）。
- 相关单测和 Gallery（可以补充展示）。不改 `A/feature/**`。

## 验收
- CI（verify.yml）在 `fix/B15-designsystem` 上通过。
- 复测用例（协调方在 sit 上执行）：Gallery P-142–P-169（明/暗），以及 P-006、P-013、P-039、P-070、P-117。
- 提交信息写 `Fixes B15-k (Txx)`。
