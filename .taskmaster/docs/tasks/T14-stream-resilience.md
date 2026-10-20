# T14 事件流断线重连与后台恢复

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：与阶段 1 并行（只改 ViewModel 流相关部分，T03 不改 ViewModel）
- 分支：`task/T14-stream-resilience`

## 目标
会话页事件流在弱网、切后台、token 失效时表现可预期：自动重连有上限和退避，回到前台立即恢复并补齐事件，token 失效时给出明确提示。

## 需求
- 审查现有重连逻辑：`handleStreamEnded` → `reconnectStream`，`MAX_STREAM_RECONNECTS` 上限。补齐指数退避（例如 1s/2s/4s/8s，封顶 30s），网络恢复时（`ConnectivityManager` 回调）立即重试并重置计数。
- 应用回到前台（`ProcessLifecycleOwner` 或页面 `LifecycleResumeEffect`）时，若流已断开，立即重连并用 replay 补齐（服务端支持 snapshot/replay 帧，见 `EventStream`）。
- `ApiError.isAuthFailure` 时停止重连，在页面显示“token 失效，请到服务器设置更新”，带跳转按钮。
- 重连中在 UI 上显示状态（复用 `NoticeBanner`，不新增复杂组件）。
- 为退避计算与状态转换写 JVM 单测（抽成纯函数）。

## 改动范围
- 修改：`A/feature/sessiondetail/SessionDetailViewModel.kt`（仅流相关函数）、`A/core/network/EventStream.kt`（如需暴露关闭原因）
- 修改：`A/feature/sessiondetail/SessionDetailScreen.kt`（仅 `NoticeBanner` 调用处与生命周期 effect）
- 新建：`A/feature/sessiondetail/StreamReconnectPolicy.kt`（纯逻辑）及对应单测
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `feature/sessiondetail/SessionDetailViewModel.kt`：`66 REASON_CONNECTION_GONE`、`69 MAX_STREAM_RECONNECTS`、`117 stream`、`120 streamGeneration`、`121 reconnectJob`、`137 streamReconnects`、`401 openStream`、`414 connectStream`、`831 handleStreamEnded`、`855 reconnectStream`、`1037 closeStream`、`1489 StreamAttachFailed`
- `core/network/EventStream.kt:39 EventStream`（31 行注释：重连由调用方负责）
- `core/network/ApiError.kt:42 isStaleConnection`、`:53 isAuthFailure`
- `core/data/ServerRepository.kt:77 eventStream`
- `feature/sessiondetail/SessionDetailScreen.kt:396 NoticeBanner`

## 参考（只读，按需打开）
- `WEB/components/chat/connection-status.tsx`、`composer-connection-status.tsx`（状态文案与样式）
- 服务端事件流：`SRV/` 下 websocket 相关（只在需要确认 replay 语义时查看）

## 验收
- 断网 10s 恢复、切后台 60s 返回、服务器重启三种场景下会话自动恢复且不丢事件（真机验证并在提交说明中记录）。
- token 失效时不无限重连，提示可跳转。
- 新增单测通过；CI 通过。
