# T39 MCP 市场：搜索与安装

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：无　可并行：与 T36–T38、T40 并行
- 分支：`task/T39-mcp-marketplace`

## 目标
在设置 → MCP 页里从市场搜索并安装 MCP 服务。

## 需求
- 入口：MCP 页顶部加"市场"按钮，打开全屏 Sheet。
- 市场源 `mcp_list_marketplaces`（多个时用下拉切换）；搜索 `mcp_search_marketplace`（关键词，分页按服务端参数）。
- 详情 `mcp_get_marketplace_server_detail`：名称、描述、所需参数/环境变量。
- 安装 `mcp_install_from_marketplace`：按详情给出的参数生成表单（密钥类字段用现有 `SecretField`），确认后安装，成功后刷新 MCP 列表。
- 不做：`mcp_set_server_apps`。

## 改动范围
- 修改：`A/feature/settings/McpScreen.kt`（只加入口按钮与 Sheet 显示）、`McpViewModel.kt`（只追加刷新调用所需的最少改动）
- 新建：`A/feature/settings/mcpmarket/` 下文件
- 修改：`A/core/network/CodegClient.kt`（新增 `// region mcp-market`，放在 `// region Transport` 之前）
- 新建：`A/core/model/wire/McpMarketRequests.kt`、`A/core/model/McpMarket.kt`
- 新建：`R/values/strings_mcp_market.xml`、`R/values-zh-rCN/strings_mcp_market.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/mcp.rs`）：list_marketplaces `:76`；search `:81` · `SearchMarketplaceParams :18`；detail `:90` · `GetMarketplaceServerDetailParams :26`；install `:99` · `InstallFromMarketplaceParams :33`。模型 grep `Marketplace`。
- app：`A/feature/settings/McpScreen.kt:74 McpContent`、`:133 McpEditor`；`core/designsystem/component/SecretField`（按 code-index 查）。

## 参考（只读，按需打开）
- `WEB/components/settings/mcp-settings.tsx`（市场部分）

## 验收
- 测试服务端上：搜索一个 MCP（如 filesystem）→ 看详情 → 安装 → MCP 列表出现；再在 MCP 页删除它。
- CI 通过。
