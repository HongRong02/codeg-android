# T50 界面对齐：工作任务、自动化、日志、动态、搜索、服务器、主框架

> 先读 `.taskmaster/docs/CONVENTIONS.md` 和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：T43、T44、T45、T46 全部合并后才能开始　可并行：T47–T50 互相并行
- 分支：`task/T50-ui-misc`

## 目标
第二阶段：把本范围内页面的 M3 原件替换为设计系统组件，并按网页端调整布局，使菜单、对话框、面板、按钮、表单、标签页、列表行与网页版一致。

## 需求
- 按 `ui-alignment.md` 映射表替换范围内所有 `DropdownMenu`/`DropdownMenuItem`、`AlertDialog`、`ModalBottomSheet`、`TextButton`/`Button`/`OutlinedButton`、`OutlinedTextField`、`Checkbox`、`Switch`、`FilterChip`、手写分段/选择器/列表行/分区标题。
- 布局对照网页端对应页面：对照 `WEB/components/tasks/`、`WEB/components/automations/`、`WEB/components/settings/logs-settings.tsx`、`WEB/components/layout/status-bar*.tsx`、`app-title-bar.tsx`；底部导航与顶栏按网页端菜单样式统一（溢出菜单统一用 CodegDropdownMenu）。
- 危险操作统一 `CodegConfirmDialog`（写明目标）。
- 不改业务逻辑与接口；可新增字符串（en + zh-rCN）。
- 完成后 `grep` 自查范围内不再出现上述 M3 原件（列在报告里，例外需说明原因）。

## 改动范围
- `A/feature/worktasks/**`、`automations/**`、`serverlogs/**`、`activity/**`、`search/**`、`server/**`、`onboarding/**`、`main/**`、`A/core/update/**`
- 对应 `R/values*/strings_*.xml`（只追加）
- 不改 `A/core/designsystem/**`；缺组件先回报。

## 验收
- CI 通过；报告附 替换清单 + 真机检查步骤（明/暗各一）。
