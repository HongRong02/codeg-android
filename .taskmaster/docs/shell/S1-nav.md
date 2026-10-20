# S1 导航：返回键、外链、新窗口、特殊链接

| 字段 | 值 |
|---|---|
| 批次 | SHELL1（纯套壳第一版） |
| 分支 | `task/S1-nav`，基于 `shell` |
| 模式 | 开发任务，CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | S1–S8 全部并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`。网页端源码（只读参考）在 WSL `~/projects/codeg`（分支 `hongrong/quota-slots`），需要了解网页行为时去那里查，不要修改它。

## 目标
让返回键和链接行为像一个原生 App，而不是浏览器。

## 要求
1. **返回键**（`onBack`）按顺序处理：
   - 网页里有打开的弹层（Radix 的 Dialog/Sheet/Drawer/DropdownMenu/Popover 等，通常带 `data-state="open"`，`role="dialog"`/`role="menu"`），先关掉它：用 `evaluateJavascript` 向 `document.activeElement ?? document.body` 派发 `Escape` 的 `keydown` 事件，再确认弹层是否已关闭。网页端移动布局的侧边栏抽屉也要能关掉。去 `~/projects/codeg/src/components/layout/` 和 `src/components/ui/` 查实际的 DOM 特征，不要猜。
   - 否则 `webView.canGoBack()` → `goBack()`。
   - 已在根页面：第一次提示"再按一次退出"（2 秒内有效），第二次退出。
   - `evaluateJavascript` 是异步的：`onBack` 先同步返回 true，再在回调里决定是 goBack 还是退出（`host.activity.moveTaskToBack(true)` 或 `finish()`，二选一并写明理由）。
2. **外链**（`shouldOverrideUrlLoading`）：与 `host.currentServerUrl` 不同 origin 的 http/https 链接，用系统浏览器打开（`Intent.ACTION_VIEW`）并返回 true；同 origin 的留在 WebView 里。
3. **特殊 scheme**：`mailto:`、`tel:`、`market:`，以及 `intent:`（用 `Intent.parseUri` 解析，找不到应用时用 `browser_fallback_url`）交给系统；`javascript:`、`file:`、`content:` 一律拦截。找不到能处理的应用时不能崩溃，给一个 Toast。
4. **新窗口**（`onCreateWindow`，即 `target=_blank` / `window.open`）：取出目标 URL（用一个临时 WebView 接住它的第一次导航），同 origin 的在当前 WebView 打开，外部的用系统浏览器打开。`setSupportMultipleWindows(true)` 由 S8 开启，你只负责处理回调。
5. 服务端重定向（例如未登录时跳到 `/login`）不能被当成外链。

## 改动范围
`shell/nav/**`、`res/values*/strings_shell_nav.xml`、`.taskmaster/docs/shell/tests/S1.md`

## 验收
- CI（verify.yml）在 `task/S1-nav` 上通过。
- 测试用例写在 `.taskmaster/docs/shell/tests/S1.md`，由协调方在真机上统一执行。
