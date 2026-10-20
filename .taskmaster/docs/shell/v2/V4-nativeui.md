# V4 上传菜单和下载提示样式对齐网页端（第一版 S3、S4）

| 字段 | 值 |
|---|---|
| 批次 | SHELL1 第二版（真机测试后的修复） |
| 分支 | `task/V4-nativeui`，基于最新 `shell` |
| 模式 | CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | V1–V4 并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`，再读你在第一版对应的任务单（括号里注明），了解上下文。截图在 `/mnt/e/claude/apk/shots/`。真机环境：vivo V1824A，Android 11，系统 WebView 已更新到 156；测试服 `http://127.0.0.1:3082`（手机通过 `adb reverse` 访问电脑上的端口）。网页源码只读参考：WSL `~/projects/codeg`。

## 问题
功能正常，但原生弹出的界面风格不一致：
- 上传底部菜单：标题和选项都居中、没有图标，像系统默认样式：`shots/v2-upload.png`。
- 下载完成提示：用的是旧式平台 AlertDialog（青色文字按钮）：`shots/v2-download.png`。

## 要求
- 上传菜单：参考网页端 `~/projects/codeg/src/components/ui/dropdown-menu.tsx` / `sheet.tsx` 的样式。标题左对齐（小号 muted），每一项左对齐，带 16dp 图标（相册、相机、文件；用 Material Icons Extended 里最接近 lucide 的 `Outlined` 图标），行高 48dp、`rounded-xl` 按压态；底部安全区留白。
- 下载提示：改成网页 sonner 风格的底部 Toast：圆角卡片（`rounded-2xl`、1dp 边框、阴影），左侧成功/失败图标，标题"已保存到下载"，副标题文件名，右侧"打开"按钮；4 秒后自动消失，可以横滑关闭。失败时用错误样式。实现放在 `shell/download/`，通过 `Overlay` 画出来。"正在下载"的提示同样用这个 Toast（loading 状态）替换系统 Toast。
- 颜色一律用 `ShellTheme` 的 colorScheme。

## 改动范围
`shell/upload/**`、`shell/download/**`、`res/values*/strings_shell_upload.xml`、`res/values*/strings_shell_download.xml`、`.taskmaster/docs/shell/tests/S3.md`、`.taskmaster/docs/shell/tests/S4.md`（补充用例）

## 验收
- CI（verify.yml）在 `task/V4-nativeui` 上通过。
- 补充的真机用例写进对应的测试文件，由协调方统一执行。
