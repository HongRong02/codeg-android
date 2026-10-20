# S8 WebView 设置、Cookie、剪贴板、权限、调试

| 字段 | 值 |
|---|---|
| 批次 | SHELL1（纯套壳第一版） |
| 分支 | `task/S8-web`，基于 `shell` |
| 模式 | 开发任务，CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | S1–S8 全部并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`。网页端源码（只读参考）在 WSL `~/projects/codeg`（分支 `hongrong/quota-slots`），需要了解网页行为时去那里查，不要修改它。

## 目标
把 WebView 的基础能力配置齐全，让网页端的各项功能在 App 里都能正常工作。

## 要求
1. **WebView 设置**（`onWebViewCreated`）：JavaScript、DOM storage、`databaseEnabled`、`mediaPlaybackRequiresUserGesture=false`、`setSupportMultipleWindows(true)` + `javaScriptCanOpenWindowsAutomatically=true`（`onCreateWindow` 由 S1 处理）、允许混合内容（`MIXED_CONTENT_COMPATIBILITY_MODE`，服务器可能是局域网 http）、`allowFileAccess=false`、缓存模式 `LOAD_DEFAULT`。UA 在默认值后追加 ` CodegAndroidShell/<versionName>`。
2. **Cookie 与存储持久化**：`CookieManager.setAcceptCookie(true)`、`setAcceptThirdPartyCookies(webView, true)`；`onPause` 时 `CookieManager.flush()`。确认 App 被杀后 localStorage 仍然保留（令牌存在里面）。
3. **剪贴板**：局域网 `http://` 不是安全上下文，`navigator.clipboard` 不可用，网页的"复制"按钮会失效。在页面开始加载时（`onPageStarted` + `onPageFinished` 双保险）注入 polyfill：当 `navigator.clipboard?.writeText` 不存在或调用失败时，改走 `CodegShellClipboard.writeText(text)` 桥接（原生写入 `ClipboardManager`）；`readText` 同理。去 `~/projects/codeg/src` 搜 `clipboard`，确认网页用到的 API 都覆盖了（包括用 `ClipboardItem` 写图片的场景，做不到的写进报告）。https 下原生 API 能用时不要覆盖。
4. **权限请求**（`onPermissionRequest`）：网页如果申请麦克风（语音输入）或摄像头，只对当前服务器 origin 授权：先申请 Android 运行时权限（`RECORD_AUDIO`/`CAMERA`），用户同意后 `request.grant`，否则 `deny`。去网页源码确认是否真的用到：用到才在 Manifest 里加对应的 `uses-permission`（只允许加这两条）；用不到就不声明，直接 deny，并在报告里说明。
5. **调试**：debug 构建开启 `WebView.setWebContentsDebuggingEnabled(true)`。
6. 地理位置等网页用不到的权限一律拒绝。
7. **字符串清理**：`res/values/strings.xml` 和 `res/values-zh-rCN/strings.xml` 里是旧原生版留下的 700 多条字符串，只保留 `app_name`，其余删除。`strings_servers_onboarding.xml`、`strings_errors.xml`、`strings_update.xml` 分别由 S5/S6/S7 处理，你不要动。同时删除 `res/font/`、`res/raw/` 里已经没人引用的资源（删除前先 grep 确认；第三方许可证相关文件如果引用了 `inter_ofl.txt` 就保留）。

## 改动范围
`shell/web/**`、`res/values/strings.xml`、`res/values-zh-rCN/strings.xml`、`res/font/**`、`res/raw/**`、`AndroidManifest.xml`（仅限第 4 条的 `uses-permission`）、`res/values*/strings_shell_web.xml`、`.taskmaster/docs/shell/tests/S8.md`

## 验收
- CI（verify.yml）在 `task/S8-web` 上通过。
- 测试用例写在 `.taskmaster/docs/shell/tests/S8.md`，由协调方在真机上统一执行。
