# T27 错误提示本地化与认证失败去重

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：无　可并行：与 T28、T29 并行
- 分支：`task/T27-error-messages`

## 目标
`Throwable.displayMessage()` 的文案全部硬编码英文；token 失效时会话页同时显示 T14 的“Token 已失效”横幅和一条英文 “Invalid or missing token” 通知。改为本地化文案，并在认证失败已由横幅提示时不再重复弹通用通知。

## 需求
- `displayMessage()` 改为可本地化：新增 `fun Throwable.displayMessage(context: Context): String`（或 `Resources`），文案放 `strings_errors.xml`（中英）；保留无参版本时让它只作为兜底并标 `@Deprecated`，逐步替换调用点。`ApiError.Server` 的 `serverMessage`、`Transport.detail` 原样显示（来自服务端/系统）。
- 会话页：当 `streamStatus` 已是 token 失效提示（`StreamNotice.openServers == true`）时，`ApiError.Unauthorized` 不再写入 `ui.notice`。
- 其它调用点（`grep -rn "displayMessage()" app/src/main/java`）一并改成带 Context 的版本；ViewModel 里用已注入的 `@ApplicationContext`。

## 改动范围
- 修改：`A/core/network/ErrorMessages.kt`
- 修改：所有 `displayMessage()` 调用点所在文件（只改调用那一行及必要的 import / context 获取）
- 修改：`A/feature/sessiondetail/SessionDetailViewModel.kt`（`Unauthorized` 去重）
- 新建：`R/values/strings_errors.xml`、`R/values-zh-rCN/strings_errors.xml`、`ErrorMessages` 的 JVM 单测（如可行）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `core/network/ErrorMessages.kt:4 displayMessage`；`core/network/ApiError.kt`（各子类）
- `feature/sessiondetail/SessionDetailViewModel.kt`：`_streamStatus`/`streamStatus`、`stopForStreamAuth`、`discardOptimistic(... notice = e.displayMessage())`（约 338 行）、702/712 行的 `notice = e.displayMessage()`
- `feature/sessiondetail/StreamReconnectPolicy.kt`：`StreamNotice`

## 参考（只读，按需打开）
- `docs/smoke/real-server-2026-10-20.md` 的 F1

## 验收
- 中文系统下各类错误提示为中文；token 失效时会话页只出现一条提示（带“服务器设置”按钮）。
- CI 通过。
