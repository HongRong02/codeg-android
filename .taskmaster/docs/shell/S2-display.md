# S2 显示：边到边、键盘、系统栏、深色模式

| 字段 | 值 |
|---|---|
| 批次 | SHELL1（纯套壳第一版） |
| 分支 | `task/S2-display`，基于 `shell` |
| 模式 | 开发任务，CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | S1–S8 全部并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`。网页端源码（只读参考）在 WSL `~/projects/codeg`（分支 `hongrong/quota-slots`），需要了解网页行为时去那里查，不要修改它。

## 目标
网页内容不被状态栏、导航栏、键盘挡住；系统栏颜色和网页一致；深浅色跟随系统。

## 要求
1. **Insets**：通过 `webViewModifier` 给 WebView 加系统栏和 IME 内边距（骨架里的默认实现只是占位）。键盘弹出时，网页底部的聊天输入栏必须完整露在键盘上方，收起后恢复，过程中不跳动。确认 Android 11 上"边到边 + adjustResize"靠 Compose 的 `imePadding` 能生效。
2. **系统栏颜色**：状态栏和导航栏区域的背景色与网页一致，图标深浅按背景亮度切换。建议做法：在 `onPageFinished` 时，以及之后网页主题变化时（用注入的 MutationObserver 监听 `<html>` 的 class/style 变化，通过 JS 桥 `CodegShellDisplay` 回调），读取 `meta[name=theme-color]`，没有就读 `getComputedStyle(document.body).backgroundColor`，然后设置窗口背景和 `WindowInsetsControllerCompat` 的 `isAppearanceLightStatusBars`/`isAppearanceLightNavigationBars`。
3. **深色模式**：WebView 的 `prefers-color-scheme` 要跟随系统深浅色。注意：targetSdk ≥ 33 时，WebView 按 App 主题的 `isLightTheme` 决定 `prefers-color-scheme`，而现在的 `Theme.Codeg` 继承的是深色的 `Theme.Material.NoActionBar`。要改成白天用 Light 父主题，夜间（`values-night/themes.xml`）用深色父主题；同时关闭算法变暗（`settings.isAlgorithmicDarkeningAllowed = false`），让网页自己处理。
4. Manifest 里 activity 的 `configChanges` 包含 `uiMode`（避免切换深浅色时重建 WebView、丢失页面状态）。你要保证系统切换深浅色后，网页能感知到。不重建也能做到就保留；做不到就去掉 `uiMode`，并用 `WebView.saveState`/`restoreState` 保住当前页面。你只可以改 activity 的 `configChanges` 这一个属性。
5. **启动闪屏**：WebView 首次加载前，窗口和 WebView 背景用接近网页的颜色（浅色 #FFFFFF，深色 #0A0A0A，或缓存上一次读到的颜色），不能闪白或闪黑。
6. `ShellTheme`（`shell/display/ShellTheme.kt`）给原生页面（服务器设置、错误页、更新对话框）用，配色与网页端 shadcn 的中性色一致（背景、前景、muted、边框、主色），深浅两套。
7. 网页文字缩放固定为 100%（`settings.textZoom = 100`），不跟随系统字体大小，避免布局错乱。

## 改动范围
`shell/display/**`、`res/values/themes.xml`、`res/values-night/themes.xml`（新建）、`res/values*/colors.xml`、`res/values*/bools.xml`、`AndroidManifest.xml` 中 activity 的 `configChanges` 属性、`res/values*/strings_shell_display.xml`、`.taskmaster/docs/shell/tests/S2.md`

## 验收
- CI（verify.yml）在 `task/S2-display` 上通过。
- 测试用例写在 `.taskmaster/docs/shell/tests/S2.md`，由协调方在真机上统一执行。
