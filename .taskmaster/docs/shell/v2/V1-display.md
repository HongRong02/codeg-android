# V1 键盘弹出后输入框被挤出可见区域（第一版 S2）

| 字段 | 值 |
|---|---|
| 批次 | SHELL1 第二版（真机测试后的修复） |
| 分支 | `task/V1-display`，基于最新 `shell` |
| 模式 | CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | V1–V4 并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`，再读你在第一版对应的任务单（括号里注明），了解上下文。截图在 `/mnt/e/claude/apk/shots/`。真机环境：vivo V1824A，Android 11，系统 WebView 已更新到 156；测试服 `http://127.0.0.1:3082`（手机通过 `adb reverse` 访问电脑上的端口）。网页源码只读参考：WSL `~/projects/codeg`。

## 问题
- 复现：打开 App（新建会话页），点底部"发送消息…"输入框。
- 实际：键盘弹出后 WebView 高度缩小，但网页没有把获得焦点的输入框滚动到可见区域，输入框完全看不到，只露出网页底部的状态栏（"4 个会话 / 添加命令"）：`shots/v2-ime.png`。
- 预期：和手机浏览器一致，输入框始终露在键盘上方，可以看到正在输入的文字。会话详情页的输入栏同样要验证。

## 线索
- 现在的做法是 Compose `imePadding` 缩小 WebView。WebView 布局尺寸变化时，Chromium 不会像系统键盘改变"可视视口"那样自动把焦点元素滚进视野。
- 可选方案（选一种，写明理由）：
  1. IME 动画结束、WebView 尺寸变化后，注入 JS：`document.activeElement` 是可编辑元素时，调用 `scrollIntoView({block: 'nearest'})`；同时检查网页是否用了 `100vh`/`100dvh` 布局，导致内容区本身就比视口高。
  2. 不缩小 WebView，改为保持全屏，把 IME 高度通过 CSS 变量或 `visualViewport` 交给网页处理（需要确认网页端是否已经适配 `interactive-widget=resizes-content`，可以查 `~/projects/codeg/src/app/layout.tsx` 的 viewport 设置）。
- 键盘收起后布局要恢复，过程中不能有明显跳动。

## 改动范围
`shell/display/**`、`res/values*/strings_shell_display.xml`、`.taskmaster/docs/shell/tests/S2.md`（补充用例）

## 验收
- CI（verify.yml）在 `task/V1-display` 上通过。
- 补充的真机用例写进对应的测试文件，由协调方统一执行。
