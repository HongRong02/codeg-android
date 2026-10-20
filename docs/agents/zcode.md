# ZCode 设置调研（T24）

## 已读证据
- WEB/components/settings/acp-agent-settings.tsx：面板、env 构造函数；ZCode 为 importantEnvKeysByAgent 与分发分支。
- SRV/handlers/acp.rs：acp_list_agents、AcpUpdateAgentEnvParams / acp_update_agent_env、AcpUpdateAgentConfigParams / acp_update_agent_config，以及下述专用 handler（如有）。路径按 CONVENTIONS 换算。

## 配置结构
importantEnvKeysByAgent(zcode) 返回空字段列表，并跳过通用配置编辑器。官方运行时安装并登录后，ACP bridge 复用现有配置。

## 读写接口
读取：acp_list_agents 返回 agent 的 env、config_json、config_file_path。
通用环境写入：acp_update_agent_env，camelCase 请求 {agentType, enabled, env, modelProviderId}。env 是完整 map，不能只发送面板字段。
原生配置写入：acp_update_agent_config，包含 agentType、configJson 等可选字段，handler 委派核心函数，本身不保证未知字段保留。
已读 handler 范围未提供专用配置读写结构；不能通过配置存在性推断已登录。

## 安全编辑结论
没有确认可安全编辑的原生字段，不假设具有通用 config.json 或通用 API key 字段。

本次按需求交付先只读展示阶段，只展示已知字段和凭据存在性，不显示密钥、token 或原始 JSON。存在凭据不代表认证成功，缺失值不代表登录失败。未调用专用接口，旧服务端没有新增 404 / unknown 操作入口。

未开放保存：当前没有测试服务端和配置文件往返证据；未超出允许的 handler 范围追读服务端核心实现。网页端结构已确认，磁盘写回安全仍待核验。后续写入必须重新读取完整最新 agent，保留 enabled、modelProviderId 与未知 env / JSON 字段，只合并用户明确修改的白名单字段，保存后重读核对。对无操作往返和单字段修改对比原文件，确认未知字段与其它文件无意外变化；整份文档替换还需防范并发写入。

## 验证与边界
- Section 使用现有 detail 快照，直接随 agent 更新，不新增配置写入方法。顶部已有启用开关仍沿用原行为。
- 真机：打开本 agent，核对服务端字段、缺失值提示、中英切换、长地址换行、横屏、大字体和深浅主题。凭据仅显示存在性；服务端更新后返回再打开，确认数据刷新。
- 文件安全：只浏览（不操作顶部开关）前后对比原生文件，预期逐字节相同。本次无保存入口，写入 round-trip 验收留待开放编辑时执行。
