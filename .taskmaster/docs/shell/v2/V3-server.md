# V3 服务器设置页和管理页样式对齐网页端（第一版 S5）

| 字段 | 值 |
|---|---|
| 批次 | SHELL1 第二版（真机测试后的修复） |
| 分支 | `task/V3-server`，基于最新 `shell` |
| 模式 | CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | V1–V4 并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`，再读你在第一版对应的任务单（括号里注明），了解上下文。截图在 `/mnt/e/claude/apk/shots/`。真机环境：vivo V1824A，Android 11，系统 WebView 已更新到 156；测试服 `http://127.0.0.1:3082`（手机通过 `adb reverse` 访问电脑上的端口）。网页源码只读参考：WSL `~/projects/codeg`。

## 问题
功能都正常，但样式简陋，和网页端的 shadcn 风格差距大：`shots/v2-mgr.png`。
- 管理页：标题直接顶在页面上，"服务器设置"按钮语义不对（应为"添加服务器"），"关闭"是一个孤立的文字按钮，卡片是灰色大块，编辑/删除是两个散落的文字。

## 要求
- 参考网页端设置页的样式（`~/projects/codeg/src/components/settings/` 和 `src/components/ui/card.tsx`、`button.tsx`、`input.tsx`），用 Compose 还原：
  - 顶栏：左侧返回/关闭图标按钮，中间或左侧标题"服务器"；右侧"添加"图标按钮（或底部主按钮"添加服务器"，二选一）。
  - 列表：每个服务器一张卡片，`rounded-2xl`、1dp 边框（border 色）、白底（深色下 card 色），内含名称（前景色、中等字重）、地址（muted 色、小号）、当前服务器显示一个小徽章"当前"（`rounded-4xl` 的 secondary badge）；右侧一个"更多"图标按钮，点开菜单含"编辑"、"删除"（删除为 destructive 色）。点卡片本身即切换。
  - 设置页（首次）和编辑表单：标签 + 输入框（`rounded-4xl`、1dp 边框、聚焦时 ring），主按钮 `rounded-4xl` 实心（primary），次按钮 outline；错误提示用 destructive 色小字放在输入框下方。
  - 删除确认对话框：标题、说明（包含服务器名）、"取消"(outline) 和 "删除"(destructive)，`rounded-4xl` 弹窗。
- 颜色一律用 `ShellTheme`（`shell/display/ShellTheme.kt`，S2 已按 shadcn 中性色配好）里的 colorScheme，不要写死颜色。深浅两套都要截图自查（在报告里说明你是怎么验证的，例如 Compose Preview）。
- 文案：中英文都要有。

## 改动范围
`shell/server/**`、`res/values*/strings_shell_server.xml`、`.taskmaster/docs/shell/tests/S5.md`（补充用例）

## 验收
- CI（verify.yml）在 `task/V3-server` 上通过。
- 补充的真机用例写进对应的测试文件，由协调方统一执行。
