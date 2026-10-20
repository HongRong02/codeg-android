# T28 服务端中断的回合给出明确提示

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：无　可并行：与 T27、T29 并行（T27 也改 `SessionDetailViewModel.kt`，各改不同函数，合并冲突小）
- 分支：`task/T28-interrupted-turn`

## 目标
agent 输出中服务端重启（或连接被服务端回收）时，app 重连失败后走 `reconcileAfterTurn` 拉回 transcript，界面像正常结束，只是回答残缺。需要明确告诉用户这一轮被中断了。

## 需求
- 区分“正常结束”（收到 turn 完成事件 / `finalizeTurn`）与“未收到完成事件就靠 reconcile 收尾”的情况；后者在该轮回答末尾显示与网页端一致的中断提示（复用 `timeline/TimelineNodeBody.kt` 的 `InlineTurnError` 或同风格组件），文案如“回合被中断（与服务端的连接已断开）”。
- 若 reconcile 拉回的 transcript 显示该轮实际已完成，则不显示提示。
- 纯逻辑（判断是否中断）抽成可单测的函数并写 JVM 单测。

## 改动范围
- 修改：`A/feature/sessiondetail/SessionDetailViewModel.kt`（`reconcileAfterTurn`、`handleStreamEnded`、`finalizeTurn` 附近）
- 修改：`A/feature/sessiondetail/timeline/TimelineNode.kt` / `TimelineNodeBody.kt`（仅在需要承载中断标记时）
- 新建：`R/values*/strings_interrupted_turn.xml`、对应单测
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `feature/sessiondetail/SessionDetailViewModel.kt`：`830 finalizeTurn`、`858 failLive`、`869 handleStreamEnded`、`952 reconcileAfterTurn`（行号见 code-index，可能偏移）；`REASON_CONNECTION_GONE`
- `feature/sessiondetail/StreamReconnectPolicy.kt`：`StreamRecovery.RECONCILE`
- `feature/sessiondetail/timeline/TimelineNodeBody.kt`：`InlineTurnError`

## 参考（只读，按需打开）
- `WEB/components/chat/session-failure-banner.tsx`、`WEB/components/message/`（中断/失败回合的展示）
- `docs/smoke/real-server-2026-10-20.md` 的 F2

## 验收
- 输出中重启服务端：回合末尾出现中断提示；正常完成的回合不出现。
- CI 通过；协调方真机复测。
