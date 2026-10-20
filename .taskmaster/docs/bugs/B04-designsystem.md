# B04 designsystem：列表行背景、卡片内边距、确认框按钮、菜单贴边、项目头像、标签页选中态

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 1 轮修复 |
| 区域（冲突域） | designsystem：`A/core/designsystem/component/**`；B04-4 如确认根因在页面侧，可改 `A/feature/projects/` 下的项目头像那一处调用 |
| 分支 / 基准 | `fix/B04-designsystem` / 最新 `sit` |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件。只读"定位"里列出的代码，只改"改动范围"里的文件。截图在 Windows 的 `E:\claude\apk\shots\`（WSL：`/mnt/e/claude/apk/shots/`），可以直接查看。网页端参考在 `WEB/components/ui/`。

## 问题清单
### B04-1 列表行自带实心背景（原 B04，影响面最大）
- 来源：P-006、P-071 真机，`shots/p006-settings-dark.png`、`shots/set-general-dark.png`；对比 1.1.139：`shots/rel-settings.png`
- 归属原任务：T46（`CodegListRow`）、T44（`CodegCard`）
- 现象：设置首页、"通用"等页面，卡片里的每一行都有自己的背景。暗色下行背景（`--background`）比卡片（`--card`）深，显示成卡片里一块块深色方块；明色下表现为卡片上下多出大块空白。
- 定位：`ListRows.kt:59` 未选中时 `.background(web.background)`；`Cards.kt` 的内容区内边距；"通用"页那种带说明文字和开关的设置行（`TextFields.kt` 的 `CodegField` 或页面自绘行，先查清楚是哪个组件）。
- 预期：行在未按下、未选中时透明，按下或选中时显示 `--accent`（网页端 `sidebar-conversation-card`、`row-more-button` 的行为）。卡片里装的是一列行时，卡片不再额外加上下内边距，行与卡片边缘的间距和 1.1.139 截图相当。为此可以给 `CodegCard`/`CodegCardContent` 加一个带默认值的参数（例如 `contentPadding`），**调用处不改**（页面侧的调整留到下一轮）。如果确认必须改调用处才能修好，在修复说明里列出需要改的调用点。

### B04-2 确认框底部按钮（原 B05）
- 来源：P-013，`shots/p013-delete-confirm-light.png`
- 归属原任务：T44（`Dialogs.kt` 的 `CodegConfirmDialog`）
- 现象：footer 里"取消"和"删除"没有垂直居中对齐，"取消"偏下；"取消"是纯文字按钮。
- 预期：两个按钮垂直居中对齐；取消按钮用 `CodegButtonVariant.Outline`（网页端 `alert-dialog.tsx` 的 `AlertDialogCancel` = outline）。`CodegDialog` 的 footer 如有同样问题一并修。

### B04-3 下拉菜单贴屏幕边缘（原 B06）
- 来源：P-013、P-047，`shots/p013-menu-light.png`、`shots/p047-gitmenu-dark.png`
- 归属原任务：T43（`Menus.kt`）
- 现象：从右上角打开的菜单，右边缘紧贴屏幕；长文字项（"合并、变基与重置"）一直延伸到屏幕边上。
- 预期：菜单与屏幕边缘保持至少 8dp 的距离（对应网页端 radix 的 `collisionPadding`），`menuPopupPosition` 的单测补上这个边距的用例。

### B04-4 项目头像空白（原 B07）
- 来源：P-039，`shots/p039-project-dark.png`；对比 1.1.139：`shots/rel-folders.png`（有文件夹图标）
- 归属原任务：T53（`Avatar.kt`）或 T56（项目页调用）
- 现象：项目详情页头部的项目头像是一个空的灰色方块，没有图标。
- 定位：先在 `A/feature/projects/` 找到项目详情头部的头像调用，确认用的是哪个组件。T55 的测试文档提到 `CodegAvatar` 缺少着色参数。
- 预期：显示图标（或首字母），明暗两种主题都清晰可见。根因在 `Avatar.kt` 就修组件；只是调用处传参问题，就只改那一处调用，并在修复说明里写明。

### B04-5 标签页选中项多一层背景（原 B08）
- 来源：P-039，`shots/p039-project-dark.png`（"文件/更改/提交"标签）
- 归属原任务：T46（`Tabs.kt`）
- 现象：暗色下选中的标签里面又套了一个深色矩形。
- 定位：`Tabs.kt:52` 附近的 trigger 背景。
- 预期：按 `tabs.tsx`：明色选中 `--background` + `shadow-sm`；暗色选中 `--input/30` 底色 + `--input` 边框，只有一层。明色也检查一遍。

## 改动范围
- `A/core/designsystem/component/**`；B04-4 允许改 `A/feature/projects/` 中项目头像那一处调用。不改其他 feature 文件。
- 相关组件的单测、Gallery（可以补充复现场景）。

## 验收
- CI（verify.yml）在 `fix/B04-designsystem` 上通过。
- 复测用例（协调方在 sit 上执行）：P-006、P-013、P-039、P-047、P-067–P-072，以及 Gallery 的 P-142、P-144、P-148、P-157。
- 提交信息写 `Fixes B04-k (Txx)`。
