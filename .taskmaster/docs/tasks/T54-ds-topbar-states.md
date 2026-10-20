# T54 设计系统：顶栏与空/加载/错误状态对齐

> 先读 `.taskmaster/docs/CONVENTIONS.md`（**测试文档模式**）和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：T43–T46、T51–T53 互相并行；T47–T50 依赖本任务
- 分支：`task/T54-ds-topbar-states`　测试方式：**测试文档模式**（不跑 CI）

## 目标
`TopBar.kt`（`CodegTopBar`、`CodegIconButton`）和 `StateViews.kt`（`EmptyState`、`LoadingView`、`InlineError`）被几乎所有页面使用，但还没按网页端对齐。本任务只改这两个文件的内部实现与新增参数，**保持现有签名源码兼容**（只允许追加带默认值的参数），让所有页面自动获得新样式。

## 需求
- `CodegTopBar`：对照 `WEB/components/layout/app-title-bar.tsx`、`folder-title-bar.tsx`：高度、标题字号字重（`text-sm font-medium` 级别）、返回/操作按钮为 ghost icon 按钮（`size-8`/`size-9`、`rounded-md`、按下 `--accent`）、底部 `--border` 分隔线、背景 `--background`；可追加 `subtitle`、`showDivider` 等带默认值参数。
- `CodegIconButton`：对齐 `button.tsx` 的 ghost + icon 尺寸（按下 `--accent`、disabled 50%、焦点 ring）；保持签名。
- `EmptyState`：对照网页端空状态（在 WEB 中 grep `empty`/`Empty` 找会话列表、项目列表、任务列表的空状态写法）：图标容器、标题 `text-sm font-medium`、描述 `text-muted-foreground text-xs/sm`、可选操作按钮。
- `LoadingView`：对照网页端 loading（spinner `Loader2 animate-spin` + 文字 `--muted-foreground`）。
- `InlineError`：对照网页端错误提示（`--destructive` 文字/边框、可选重试按钮）。
- 新增 `@Preview`（明/暗）与 `internal fun T54Gallery()`。
- 图标：T51 并行开发中，先用 Material 图标并留 `// TODO(T51): Lucide.Xxx` 注释（写明网页端用的 lucide 名）。
- 不改任何调用点（`A/feature/**`）。

## 改动范围
- `A/core/designsystem/component/TopBar.kt`、`StateViews.kt`；如需新字符串用 `strings_ds_states.xml` + zh；测试文档 `.taskmaster/docs/tests/T54-ds-topbar-states.md`
- 范围外文件一律不改；不新增 Gradle 依赖。

## 代码定位
- `A/core/designsystem/component/TopBar.kt`、`StateViews.kt`（全文可读，文件小）
- 调用点数量：`grep -rn "CodegTopBar\|EmptyState(\|LoadingView(\|InlineError(\|CodegIconButton(" A/feature` —— 只看不改，用于确认签名兼容和列回归页面
- 主题：`A/core/designsystem/theme/WebThemes.kt`、`Color.kt`、`CodegShapes`

## 参考（只读）
- `WEB/components/layout/app-title-bar.tsx`、`folder-title-bar.tsx`、`WEB/components/ui/button.tsx`
- `WEB/app/globals.css`

## 验收
- 测试文档按模板写全：因为影响全局，回归点必须列出所有调用页面（按 feature 分组），每个页面一条检查用例（明/暗）；说明签名兼容性（列出新旧签名）。
