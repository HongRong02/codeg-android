# T52 设计系统：Toast（对齐 sonner）

> 先读 `.taskmaster/docs/CONVENTIONS.md`（**测试文档模式**）和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：T43–T46、T51、T53、T54 互相并行；T47–T50 依赖本任务
- 分支：`task/T52-ds-toasts`　测试方式：**测试文档模式**（不跑 CI）

## 目标
网页端用 `sonner`（`WEB/components/ui/app-toaster.tsx`，约 130 处调用）做操作反馈；App 目前只有零星 Snackbar 和页面内文字提示。提供与网页端一致的全局 Toast。

## 需求
新建 `A/core/designsystem/component/Toasts.kt`：
- `class CodegToaster`：`success(title, description = null, action = null)`、`error(...)`、`info(...)`、`warning(...)`、`loading(title): ToastId`、`update(id, type, title, description)`（loading → success/error，对应网页端 `toast.promise` / 带 id 更新）、`dismiss(id)`。`action = ToastAction(label, onClick)`。
- 状态逻辑（队列、最多同时显示 3 条、默认时长按 `app-toaster.tsx` 与 sonner 默认值、loading 不自动消失、相同 id 更新而非新增）放在纯 Kotlin 类里，**写 JVM 单测**。
- `val LocalCodegToaster = staticCompositionLocalOf<CodegToaster>`：默认值为不显示的空实现（记录日志），保证第二阶段各页面在宿主挂上之前调用也不崩。
- `@Composable fun CodegToasterHost(toaster: CodegToaster = remember { CodegToaster() }, content: @Composable () -> Unit)`：提供 Local，并在内容之上叠加 toast 层（位置、偏移、堆叠、进出动画、滑动关闭按 `app-toaster.tsx` 配置与 sonner 样式：`--popover` 背景、`--border` 边框、`rounded-lg`、`shadow-lg`、`text-sm`，类型图标颜色与网页端一致；需要避开系统导航栏与输入法 insets）。宿主挂到 `MainShell` 由 T50 做，本任务不改 feature。
- 文字走 `strings_ds_toasts.xml` + zh；`@Preview`（明/暗，各类型）；`internal fun T52Gallery()`（按钮触发各类型 toast）。
- 图标：T51 并行开发中，本任务先用 Material 图标并在代码里留 `// TODO(T51): Lucide.CircleCheck` 之类注释（写明网页端用的 lucide 名），合并后由协调方替换。

## 改动范围
- `A/core/designsystem/component/Toasts.kt`（新建）、`strings_ds_toasts.xml` + zh、`app/src/test/.../designsystem/` 下单测、测试文档 `.taskmaster/docs/tests/T52-ds-toasts.md`
- 范围外文件一律不改；不新增 Gradle 依赖。

## 代码定位
- 主题：`A/core/designsystem/theme/WebThemes.kt`（`WebTokens`）、`Color.kt`（`withOpacity`）、`CodegShapes`（按 code-index `core-designsystem.md` 查）

## 参考（只读）
- `WEB/components/ui/app-toaster.tsx`（及其 test）、网页端任意 `toast.` 调用处（了解常见用法：成功提示、错误 + 描述、loading→结果）
- `WEB/app/globals.css`

## 验收
- 测试文档按模板写全：单测清单；用例覆盖四种类型、loading→success、action 按钮、超过 3 条堆叠、滑动关闭、横竖屏/输入法弹出时位置、明/暗；对照网页端的允许差异。
