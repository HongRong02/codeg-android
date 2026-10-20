# T48 界面对齐：项目与 Git

> 先读 `.taskmaster/docs/CONVENTIONS.md`（**测试文档模式**）和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：T43–T46（已合入 `sit`，本任务基于 `sit`）　可并行：T47–T54 互相并行
- 分支：`task/T48-ui-projects`　测试方式：**测试文档模式**（不跑 CI）

## 目标
第二阶段：把本范围内页面的 M3 原件替换为设计系统组件，并按网页端调整布局，使菜单、对话框、面板、按钮、表单、标签页、列表行与网页版一致。

## 需求
- 按 `ui-alignment.md` 映射表替换范围内所有 `DropdownMenu`/`DropdownMenuItem`、`AlertDialog`、`ModalBottomSheet`、`TextButton`/`Button`/`OutlinedButton`、`OutlinedTextField`、`Checkbox`、`Switch`、`FilterChip`、手写分段/选择器/列表行/分区标题。
- 布局对照网页端对应页面：对照 `WEB/components/layout/folder-title-bar.tsx`、`aux-panel-*.tsx`、`branch-dropdown.tsx`、`commit-dialog.tsx`、`stash-dialog.tsx`、`remote-manage-dialog.tsx`、`conflict-dialog.tsx`、`WEB/components/forge/`、`WEB/components/import-sessions/`。
- 危险操作统一 `CodegConfirmDialog`（写明目标）。
- 不改业务逻辑与接口；可新增字符串（en + zh-rCN）。
- 本阶段**不替换图标、不接 Toast**（第三阶段做）；需要 T52/T53 组件（Toast、折叠、输入组、图片预览、命令列表、头像）的位置留 `// TODO(T52)` / `// TODO(T53): <组件名>` 注释。`CodegTopBar`/`EmptyState` 等照常使用（T54 只改其内部样式）。
- 完成后 `grep` 自查范围内不再出现上述 M3 原件（列在报告里，例外需说明原因）。

## 改动范围
- `A/feature/projects/**`（含 git/、forge/、groups/、importsessions/）
- 对应 `R/values*/strings_*.xml`（只追加）
- 测试文档 `.taskmaster/docs/tests/T48-ui-projects.md`
- 不改 `A/core/designsystem/**`；缺组件先回报。

## 验收
- 测试文档按模板写全：替换清单（文件 → 替换前/后组件）、grep 自查结果、留下的 TODO(T5x) 清单、逐页面测试用例（入口 → 步骤 → 预期，明/暗各一，并注明对照的网页端页面）、回归点（业务功能逐项）、自评编译风险。
