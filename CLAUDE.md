# codeg-android（协调方须知）

codeg 服务端的原生安卓客户端（Kotlin + Compose），用户自己的 fork。所有适配只在 App 里做，**不改 codeg 服务端**，App 必须能连官方原版 codeg。

## 文档
- 流程：`.taskmaster/docs/WORKFLOW.md`（分支模型、分层派发、sit 集成、bug 修复、发布）
- 任务方约定：`.taskmaster/docs/CONVENTIONS.md`；任务 `tasks/`、测试 `tests/`、bug 单 `bugs/`、关系图 `graphs/`（**当前状态以关系图为准**）
- 符号索引 `.taskmaster/docs/code-index/`（`python -X utf8 scripts/gen_code_index.py`）；接口差异 `scripts/api_diff.py` → `api-diff.md`

## 发布
- push `main`（非文档改动）= 发版：`auto-release.yml` 签名并发布 Release `build-<提交数>`，版本名 `1.1.<n>`，App 自动更新。所以 main 只接收 `sit` 的整批合入。
- `verify.yml` 只能手动触发：`gh workflow run verify.yml --ref <分支>`。

## 常用命令（Windows Git Bash）
- gh：`"/c/Program Files/GitHub CLI/gh.exe"`；以 `.` 开头的 git 路径参数前加 `MSYS_NO_PATHCONV=1`。
- 派发 Codex：循环写进脚本文件，用 `MSYS_NO_PATHCONV=1 wsl.exe -e bash -l /mnt/c/<脚本>` 运行（内联 `bash -lc` 会被安全检查拦）。脚本内容：
  `codeg-cli run -a codex -w /mnt/e/claude/codeg-android --worktree="$t" --permissions allow --detach --no-stdin "$P" </dev/null`
  （worktree 基于主仓库当前 HEAD，所以派发前主仓库必须 checkout `sit`；不加 `--no-stdin` 会卡住）
- 收结果：`codeg-cli result <RUN> --wait 3h -q </dev/null`（后台运行）。
- 合并、提交只在 Windows 端做，不在 WSL 里提交。Codex 的 worktree（`E:\claude\codeg-android-cli-*`）的 `.git` 指向 WSL 路径，Windows 端用不了；要改任务分支就 `git worktree add /e/claude/wt-fix origin/task/Txx`，改完 `git push origin HEAD:task/Txx`，再 `git worktree remove`。
- 合并冲突：code-index 取 ours，合完重新生成；`CodegClient.kt` 的 `// region` 冲突两边都保留，中间补一个 `    // endregion`。
- Python 在 Windows 上写文件要用 `newline='\n'`，否则会把 LF 文件改成 CRLF。

## 真机（vivo V1824A，Android 11，包名 `app.codeg.android.mine`）
- adb：`E:\Agent\.tools\platform-tools\adb.exe`。安装用 `E:\claude\apk\install.sh <apk>`；操作一律用 `E:\claude\apk\safe.sh`（start/tap/long/swipe/text/key/back/ui/shot），它会先确认前台是 codeg。读屏用 `ui.sh`。
- 键盘弹出时不要滑动（vivo 输入法会当成滑行输入）。
- 测试服：WSL `~/codeg-t13`，端口 3082，令牌在 `~/codeg-t13/test-token.txt`，手机配置名 `T13Test`，先 `adb reverse tcp:3082 tcp:3082`。它和真实 WSL 共用用户目录，装 MCP、关联技能会改到真实的 agent 配置。
- 用户的真实 codeg（WSL 3080，外网 https://rhdemo.online:8443）的令牌**绝不填进 App**。
