# V2 服务器挂掉时白屏，没有错误页；增加 WebView 版本检查（第一版 S6）

| 字段 | 值 |
|---|---|
| 批次 | SHELL1 第二版（真机测试后的修复） |
| 分支 | `task/V2-error`，基于最新 `shell` |
| 模式 | CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | V1–V4 并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`，再读你在第一版对应的任务单（括号里注明），了解上下文。截图在 `/mnt/e/claude/apk/shots/`。真机环境：vivo V1824A，Android 11，系统 WebView 已更新到 156；测试服 `http://127.0.0.1:3082`（手机通过 `adb reverse` 访问电脑上的端口）。网页源码只读参考：WSL `~/projects/codeg`。

## 问题 1：白屏
- 复现：手机通过 `adb reverse tcp:3082 tcp:3082` 访问电脑上的测试服；停掉测试服，强制停止 App 后重新打开。
- 实际：整屏白色，没有进度条，也没有原生错误页：`shots/v2-blank.png`。测试服恢复后约 10 秒内页面自动出现（说明自动重试在工作，但错误页没有显示）。
- 注意：经过 `adb reverse` 时，连接会先被 adb 接受，然后立刻关闭，WebView 收到的可能是 `ERR_EMPTY_RESPONSE`/`ERR_CONNECTION_CLOSED`，也可能是不带正文的 502。真实环境下的反向代理（nginx）在后端挂掉时返回 502/504 页面。这几种情况都要显示错误页。
- 请用 debug 包的 WebView 调试（`chrome://inspect`，或在报告里写出你推断的回调路径）定位：是回调没触发、被判定成非主框架，还是 Overlay 触发了但没画出来（比如被 WebView 盖住、或在 WebView 重建后状态丢失）。修好后写清根因。
- 加载中（还没有任何内容时）要能看到 S6 的进度条或一个居中的加载指示，不能是纯白屏。

## 问题 2：WebView 版本过低时给出提示（新增）
- 背景：这台手机原来的系统 WebView 是 83，网页端（Next.js）需要较新的内核，在 83 上直接报 JS 语法错误，页面一直停在加载状态，用户不知道原因。
- 要求：启动时读取 `WebView.getCurrentWebViewPackage()` 的版本号，主版本低于 **111** 时，在 `Overlay` 里显示原生提示页：说明原因、当前版本和要求版本，并提供两个按钮："打开应用商店"（`market://details?id=com.google.android.webview`，没有能处理的应用时打开 `https://play.google.com/store/apps/details?id=com.google.android.webview`）和"仍然继续"（关闭提示，本次启动不再显示）。
- 放在 `shell/error/` 包里实现。

## 改动范围
`shell/error/**`、`res/values*/strings_shell_error.xml`、`.taskmaster/docs/shell/tests/S6.md`（补充用例）

## 验收
- CI（verify.yml）在 `task/V2-error` 上通过。
- 补充的真机用例写进对应的测试文件，由协调方统一执行。
