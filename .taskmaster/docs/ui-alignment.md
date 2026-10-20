# UI 对齐网页端：组件映射与约定（T43–T50 共用）

目标：App 的菜单、对话框、面板、按钮、表单、标签页、列表行与 codeg 网页端（shadcn radix-maia，`WEB/components/ui/*.tsx`）视觉一致。主题 token 已在 `A/core/designsystem/theme/WebThemes.kt`（`CodegTheme.colors.web.*`）里，形状在 `CodegShapes`，透明色用 `Color.withOpacity()`。

## 组件归属（第一阶段新建，第二阶段替换）
| 网页端 | App 组件（文件） | 任务 | 替换掉的 M3 原件 |
|---|---|---|---|
| dropdown-menu / context-menu / popover / tooltip | `CodegDropdownMenu`、`CodegMenuItem`、`CodegMenuSeparator`、`CodegMenuLabel`、`CodegContextMenu`（长按）、`CodegPopover`、`CodegTooltip`（`component/Menus.kt`） | T43 | `DropdownMenu`、`DropdownMenuItem` |
| dialog / alert-dialog | `CodegDialog`（标题、描述、内容、footer 按钮区）、`CodegConfirmDialog`（危险操作：目标名 + destructive 按钮）（`component/Dialogs.kt`） | T44 | `AlertDialog` |
| drawer / sheet | `CodegSheet`（底部抽屉：拖动条、标题、描述、滚动内容、固定 footer，自带 imePadding）、`CodegFullScreenSheet`（`component/Sheets.kt`） | T44 | `ModalBottomSheet` |
| card | `CodegCard`/`CodegCardHeader`/`CodegCardContent`/`CodegCardFooter`（`component/Cards.kt`，与现有 `webSurface` 一致） | T44 | 手写 Column+border |
| button / button-group | `CodegButton(variant = Default/Secondary/Outline/Ghost/Destructive/Link, size = Default/Sm/Lg/Icon)`（`component/Buttons.kt` 扩展，保留现有 `PrimaryButton` 等为兼容封装） | T45 | `TextButton`、`Button`、`OutlinedButton` |
| input / textarea / field / label | `CodegTextField`（已有，补 textarea 变体、错误态、说明文字）、`CodegField(label, description, error)`（`component/TextFields.kt`） | T45 | `OutlinedTextField` |
| select / combobox / radio-group / checkbox / switch / slider | `CodegSelect`、`CodegCombobox`、`CodegRadioGroup`、`CodegCheckbox`、`CodegSwitch`（`component/Controls.kt`） | T45 | 手写"项目: 请选择"按钮+菜单、`Checkbox`、`Switch` |
| tabs | `CodegTabs`（分段样式，`TabsList` 背景 `--muted`，选中项 `--background` + 阴影）（`component/Tabs.kt`） | T46 | `SegmentedTabs`、`TabRow`、手写分段 |
| badge / separator / skeleton / progress | `CodegBadge(variant)`（`component/Badges.kt` 扩展）、`CodegSeparator`、`CodegSkeleton`、`CodegProgress`（`component/Feedback.kt`） | T46 | `HorizontalDivider`、文字状态、`LinearProgressIndicator` |
| sidebar 行 / row-more-button / 分区标题 | `CodegListRow(leading, title, subtitle, trailing, onClick, onLongClick)`、`CodegSectionHeader`、`CodegRowMoreButton`（`component/ListRows.kt`） | T46 | 各页手写行 |
| chip / 筛选 | `CodegFilterChip`（按 badge outline + 选中 secondary 样式）（`component/Badges.kt`） | T46 | `FilterChip` |

## 规则
- 第一阶段（T43–T46）：**只新建/扩展 `A/core/designsystem/component/` 下自己那几个文件**，不改任何 feature 文件。每个组件写 `@Preview`（明/暗各一）和最少的单测（如可测的状态逻辑）。另在自己的文件里提供一个 `internal fun XxxGallery()` 汇总所有变体，供第二阶段做组件预览页。
- 数值（圆角、内边距、字号、行高、边框、阴影、选中/hover/disabled 透明度）直接读对应 `WEB/components/ui/*.tsx` 的 Tailwind class 换算：1 单位 = 4dp，`text-sm` = 14sp，`rounded-md/lg/xl` 用 `CodegShapes` 对应级别；颜色只用 `CodegTheme.colors.web.*` token。
- 第二阶段（T47–T50）：只改自己负责的 feature 目录，把上表右列的 M3 原件全部替换为左列组件，并按对应网页端页面调整布局（间距、分区、标题、空状态）。不新增设计系统组件；缺什么先回报。
- 不改业务逻辑、不改接口调用、不改字符串含义（可新增字符串）。
