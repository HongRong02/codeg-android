# S4 下载：http、blob、data 链接

| 字段 | 值 |
|---|---|
| 批次 | SHELL1（纯套壳第一版） |
| 分支 | `task/S4-download`，基于 `shell` |
| 模式 | 开发任务，CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | S1–S8 全部并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`。网页端源码（只读参考）在 WSL `~/projects/codeg`（分支 `hongrong/quota-slots`），需要了解网页行为时去那里查，不要修改它。

## 目标
网页里所有"下载/导出"（导出 PNG、下载文件、导出日志等）在 App 里都能保存到手机的"下载"目录。

## 要求
1. 在 `onWebViewCreated` 里设置 `webView.setDownloadListener`：
   - `http(s)` 链接：用 OkHttp 流式下载，带上 WebView 的 Cookie（`CookieManager.getCookie(url)`）和 User-Agent。网页的 API 用 `Authorization: Bearer <localStorage.codeg_token>` 鉴权而不是 Cookie：同 origin 的下载如果返回 401，就用 JS 取出令牌，加上 `Authorization` 头重试。令牌只能发给当前服务器 origin，绝不发给其他主机，也不写日志。
   - `blob:` 链接：WebView 不能直接下载。注入 JS：`fetch(blobUrl)` → `FileReader` 转 base64 → 通过 `addJavascriptInterface`（名字 `CodegShellDownload`）回传给原生写文件。还要处理网页用 `<a download href=blob:...>` 点击的情况（注入脚本拦截 click，或者在 DownloadListener 里收到 blob URL）。去 `~/projects/codeg/src` 搜 `download`、`createObjectURL`、`toBlob`，列出网页所有的导出方式，确保都覆盖，并写进测试用例。
   - `data:` 链接：直接解码保存。
2. 文件名：优先用 `Content-Disposition` / `<a download>` 的名字，其次用 `URLUtil.guessFileName`；重名时自动加 `(1)`。
3. 保存位置：`MediaStore.Downloads`（Android 10 以上不需要存储权限）。
4. 反馈：开始下载时 Toast"正在下载 xxx"；完成后提示"已保存到下载：xxx"，并能一键打开（系统通知或原生提示二选一，写明理由；如果用通知，需要的 `POST_NOTIFICATIONS` 权限申请也由你处理，允许你在 Manifest 里只加这一条 `uses-permission`）；失败时提示原因。
5. 大文件不能整个读进内存：http 流式写入；blob 的 base64 过大时分块传。

## 改动范围
`shell/download/**`、`res/values*/strings_shell_download.xml`、`AndroidManifest.xml`（仅限第 4 条的权限）、`.taskmaster/docs/shell/tests/S4.md`

## 验收
- CI（verify.yml）在 `task/S4-download` 上通过。
- 测试用例写在 `.taskmaster/docs/shell/tests/S4.md`，由协调方在真机上统一执行。
