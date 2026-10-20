# S6 加载进度、错误页、断网恢复、渲染进程崩溃

| 字段 | 值 |
|---|---|
| 批次 | SHELL1（纯套壳第一版） |
| 分支 | `task/S6-error`，基于 `shell` |
| 模式 | 开发任务，CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | S1–S8 全部并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`。网页端源码（只读参考）在 WSL `~/projects/codeg`（分支 `hongrong/quota-slots`），需要了解网页行为时去那里查，不要修改它。

## 目标
网络不好或服务器挂掉时，App 给出清楚的原生提示和恢复手段，而不是显示 Chrome 的"网页无法打开"。

## 要求
1. **加载进度**：主页面加载时，在顶部（状态栏下方）显示一条 2dp 的细进度条（`onProgressChanged`），加载完淡出。网页内部的单页路由切换不显示。
2. **错误页**：只处理主框架（`request.isForMainFrame`）的错误：`onReceivedError`（连不上、DNS、超时、SSL）和 `onReceivedHttpError`（主文档返回 502/503/504 等 5xx）。在 `Overlay` 里盖一层原生错误页：图标、标题、可读的原因（中英文）、当前服务器地址，按钮"重试"（`host.reload()`）和"切换服务器"（`host.openServerManager()`）。子资源和 `/api/` 请求失败由网页自己处理，不弹错误页。成功加载后错误页消失。
3. **自动恢复**：显示错误页时注册 `ConnectivityManager.NetworkCallback`，网络恢复后自动重试一次；另外每 10 秒自动重试，最多 6 次，之后只保留手动重试。错误页消失时注销回调、停止计时。
4. **回到前台**：`onResume` 时，如果当前是错误页就立即重试一次。正常页面不要 reload（网页自己有 WebSocket 重连）。
5. **渲染进程崩溃**（`onRenderProcessGone`）：返回 true，调用 `host.recreateWebView()`，并 Toast"页面已重新加载"。
6. 错误页显示时按返回键：不拦截（返回 false）。
7. 界面用 Compose + Material3（骨架根部已经包了 `ShellTheme`），风格与网页端一致：居中、留白充足、按钮圆角。

## 改动范围
`shell/error/**`、`res/values*/strings_shell_error.xml`（不再用的 `strings_errors.xml` 两份可以删除）、`.taskmaster/docs/shell/tests/S6.md`

## 验收
- CI（verify.yml）在 `task/S6-error` 上通过。
- 测试用例写在 `.taskmaster/docs/shell/tests/S6.md`，由协调方在真机上统一执行。
