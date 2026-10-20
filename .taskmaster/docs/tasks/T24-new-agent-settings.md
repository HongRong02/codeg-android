# T24 7 种新 agent 的设置页（先调研，未确认结构前只读）

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：T10　可并行：可按 agent 拆成子任务并行
- 分支：`task/T24-new-agent-settings`

## 目标
为 DeepSeek、Qoder、Antigravity、Kiro、ZCode、TRAE、Command Code 提供设置页。前提是逐个确认服务端配置结构，避免写坏配置。

## 需求
- 第一步（必须）：逐个 agent 阅读网页端配置面板与服务端读写配置的代码，在 `docs/agents/<agent>.md` 写下配置结构、读写接口、哪些字段可安全编辑。
- 第二步：对结构已确认的 agent 实现设置页：先只读展示；写入仅限确认安全的字段，保存前做 round-trip（读 → 改 → 写回原样未知字段）。
- 当前这些 agent 在 `AgentDetailScreen` 走 `ServerManagedSection`（只提示去网页端），实现后替换为各自的 Section。

## 改动范围
- 新建：`docs/agents/*.md`、`A/feature/settings/AgentConfig<Agent>View.kt`（每个 agent 一个文件，参照 `AgentConfigKimiView.kt`）
- 修改：`A/feature/settings/AgentDetailScreen.kt`（仅分发到新 Section 的 `when` 分支）、`AgentsViewModel.kt`（仅新增各 agent 的读写方法）
- 新建：`R/values*/strings_agent_<agent>.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- app：`feature/settings/AgentDetailScreen.kt:142`（新 agent 分发到 ServerManagedSection 的 when 分支）、`:503 ServerManagedSection`、`:99 AgentDetailContent`；`AgentConfigKimiView.kt:85`（参考实现）；`AgentsViewModel.kt:47`；`core/model/AgentType.kt`；`core/model/AgentConfig.kt`
- 服务端：`SRV/handlers/acp.rs`（`acp_load_deepseek_model_catalog`、`acp_qoder_auth_status`、`acp_antigravity_*` 等，行号见 `api-diff.md`）

## 参考（只读，按需打开）
- `WEB/components/settings/deepseek-config-panel.tsx`、`qoder-config-panel.tsx`、`antigravity-config-panel.tsx`、`kiro-config-panel.tsx`、`trae-config-panel.tsx`、`command-code-config-panel.tsx`（ZCode 在 `acp-agent-settings.tsx` 内搜索）

## 验收
- 每个 agent 都有调研文档；已实现的设置页在测试服务端上 round-trip 后配置文件无意外变化（对比前后文件）。
- CI 通过。
