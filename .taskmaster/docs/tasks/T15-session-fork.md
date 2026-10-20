# T15 会话 fork / 断开 / 预检

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T03、T14
- 分支：`task/T15-session-fork`

## 目标
接入服务端 `acp_fork`、`acp_disconnect`、`acp_preflight`：在会话菜单中支持从当前会话 fork 新会话、手动断开 agent 连接；新建会话前做预检并展示问题。

## 需求
- 先读三个请求结构体与 handler，确认参数与返回值（见“代码定位”），再看网页端如何调用。
- fork：会话操作菜单新增“Fork 会话”，成功后跳转到新会话。
- disconnect：菜单新增“断开连接”，断开后页面回到未连接态（下次发送会重新 connect）。
- preflight：新建/续聊连接前调用；有问题时用 `NoticeBanner` 展示服务端返回的提示，不阻断可继续的情况。
- 服务端不支持（老版本）时隐藏菜单项。

## 改动范围
- 修改：`A/core/network/CodegClient.kt`（新增 `// region session-fork`）
- 新建：`A/core/model/wire/SessionForkRequests.kt`、`A/core/model/SessionFork.kt`
- 修改：`A/feature/sessiondetail/SessionActions.kt`、`SessionDetailViewModel.kt`（新增方法，不改流逻辑）
- 新建：`R/values/strings_session_fork.xml`、`R/values-zh-rCN/strings_session_fork.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端：`SRV/handlers/acp.rs:459 acp_fork` · `AcpForkParams :344`；`:126 acp_disconnect` · `AcpDisconnectParams :122`；`:203 acp_preflight` · `AcpPreflightParams :198`
- app：`core/network/CodegClient.kt:217 connect`、`:232 findConnection`（参考已有 acp 调用写法）；`feature/sessiondetail/SessionActions.kt:56 SessionActionsMenu`；`SessionDetailViewModel.kt:352 connectFresh`、`:362 connectWithPreferences`、`:386 resolveConnection`

## 参考（只读，按需打开）
- `WEB/components/conversations/conversation-detail-panel.tsx`（搜 `acpFork`/`fork`）
- `WEB/lib/api.ts`（搜 `acp_fork`、`acp_disconnect`、`acp_preflight` 的参数）

## 验收
- 三个功能在本地 0.38 测试服务端上真机验证通过。
- CI 通过。
