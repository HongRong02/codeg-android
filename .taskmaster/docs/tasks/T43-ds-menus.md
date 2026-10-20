# T43 设计系统：菜单与弹出层

> 先读 `.taskmaster/docs/CONVENTIONS.md` 和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：T43–T46 互相并行；T47–T50 依赖本任务
- 分支：`task/T43-ds-menus`

## 目标
第一阶段：在设计系统中提供与网页端一致的组件，供第二阶段各页面替换使用。

## 需求
按 `ui-alignment.md` 表中第一行，新建 `A/core/designsystem/component/Menus.kt`：`CodegDropdownMenu`（容器：`--popover` 背景、`--border` 1dp 边框、`rounded-lg`、阴影 `shadow-md`、内边距 p-1、最小宽 8rem）、`CodegMenuItem`（`rounded-md px-2 py-1.5 text-sm`，leading 图标 16dp、trailing 快捷键/箭头，`variant = Default/Destructive`，disabled 50% 透明，按下态 `--accent`）、`CodegMenuSeparator`（`-mx-1 my-1 h-px --border`）、`CodegMenuLabel`（`px-2 py-1.5 text-sm font-medium`）、`CodegMenuCheckboxItem`、`CodegContextMenu`（包住任意内容，长按弹出，同样样式）、`CodegPopover`、`CodegTooltip`（长按显示）。签名尽量贴近 M3 `DropdownMenu(expanded, onDismissRequest)` 以便第二阶段机械替换。
- 每个组件提供 `@Preview`（明/暗）；文件内提供 `internal fun T43Gallery()` 汇总所有变体。
- 不改任何 `A/feature/**` 文件。

## 改动范围
- `A/core/designsystem/component/Menus.kt`（新建）、`R/values/strings_ds_menus.xml` + zh（如需）、`app/src/test/.../designsystem/` 下单测
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 主题：`A/core/designsystem/theme/WebThemes.kt`（`WebTokens`）、`Color.kt`（`CodegColors`、`withOpacity`）、`CodegTheme.kt`、`CodegShapes`（按 code-index `core-designsystem.md` 查）
- 现有组件：`.taskmaster/docs/code-index/core-designsystem.md`

## 参考（只读）
- `WEB/components/ui/dropdown-menu.tsx`、`context-menu.tsx`、`popover.tsx`、`tooltip.tsx`
- `WEB/app/globals.css`（token 与圆角）

## 验收
- `./gradlew :app:check` 通过（CI verify.yml）；报告里附组件清单与签名。
