# T44 设计系统：对话框、抽屉、卡片

> 先读 `.taskmaster/docs/CONVENTIONS.md` 和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：T43–T46 互相并行；T47–T50 依赖本任务
- 分支：`task/T44-ds-dialogs-sheets`

## 目标
第一阶段：在设计系统中提供与网页端一致的组件，供第二阶段各页面替换使用。

## 需求
新建 `Dialogs.kt`：`CodegDialog(title, description, onDismiss, footer, content)`（`--background`、`rounded-xl`、`p-6`、`gap-4`、标题 `text-lg font-semibold`、描述 `text-sm --muted-foreground`、footer 右对齐按钮区、遮罩 `black/50`），`CodegConfirmDialog(title, message, target, confirmLabel, destructive, onConfirm, onDismiss)`（对应 alert-dialog，危险按钮用 destructive 色）。新建 `Sheets.kt`：`CodegSheet`（对应 drawer：顶部拖动条 `h-2 w-[100px] rounded-full --muted`、`rounded-t-xl`、标题/描述区、滚动内容、固定 footer、自带 `imePadding` 和导航栏 inset），`CodegFullScreenSheet`（全屏面板：顶栏关闭按钮 + 标题 + 操作区）。新建 `Cards.kt`：`CodegCard` / `CodegCardHeader(title, description, action)` / `CodegCardContent` / `CodegCardFooter`（`rounded-xl border py-6 shadow-sm`，内部 `px-6`），与现有 `Glass.kt` 的 `webSurface` 视觉一致（可让 webSurface 复用同一参数，但不改其签名）。
- 每个组件提供 `@Preview`（明/暗）；文件内提供 `internal fun T44Gallery()` 汇总所有变体。
- 不改任何 `A/feature/**` 文件。

## 改动范围
- `A/core/designsystem/component/Dialogs.kt`、`Sheets.kt`、`Cards.kt`（新建）；`Glass.kt`（只允许抽取共用常量）；`strings_ds_dialogs.xml` + zh
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 主题：`A/core/designsystem/theme/WebThemes.kt`（`WebTokens`）、`Color.kt`（`CodegColors`、`withOpacity`）、`CodegTheme.kt`、`CodegShapes`（按 code-index `core-designsystem.md` 查）
- 现有组件：`.taskmaster/docs/code-index/core-designsystem.md`

## 参考（只读）
- `WEB/components/ui/dialog.tsx`、`alert-dialog.tsx`、`drawer.tsx`、`card.tsx`
- `WEB/app/globals.css`（token 与圆角）

## 验收
- `./gradlew :app:check` 通过（CI verify.yml）；报告里附组件清单与签名。
