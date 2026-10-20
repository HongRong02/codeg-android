# 委派任务通用约定（每个任务开工前必读，仅此一页）

## 阅读范围
- 只读：本页 + 你的任务文档 `.taskmaster/docs/tasks/Txx-*.md` + 任务文档“代码定位”里列出的文件/行。
- 不要通读 README、PRD、整个 CodegClient.kt 或其它 feature 目录。需要找别的符号时，先查 `.taskmaster/docs/code-index/<区域>.md`（按文件列出所有声明及行号），再只打开对应行附近。
- 服务端接口：查 `.taskmaster/docs/api-diff.md` 得到 handler 与请求结构体的 `文件:行`（路径相对 `E:\claude\codeg\src-tauri\src\web\`），只读那个结构体和 handler 函数。
- 行号以索引生成时为准，有偏移就用符号名 grep。改完代码后运行 `python -X utf8 scripts/gen_code_index.py` 更新索引（索引文件一并提交）。

## 路径简写
- `A/` = `app/src/main/java/app/codeg/android/`
- `R/` = `app/src/main/res/`
- `WEB/` = `E:\claude\codeg\src\`（网页端，只读参考）
- `SRV/` = `E:\claude\codeg\src-tauri\src\web\`（服务端，只读，绝不修改）
- 在 WSL / codeg 里运行时，`E:\claude\...` 对应 `/mnt/e/claude/...`（例如 `WEB/` = `/mnt/e/claude/codeg/src/`）。

## 改动边界（并行防冲突）
- 只改任务文档“改动范围”里列出的文件；需要改范围外文件时停下来，在任务里记录原因（`task-master update-subtask` 或回报），不要自行扩大范围。
- 例外（默认在范围内，无需任务文档单独列出）：按下面规则新建的 `strings_<任务短名>.xml`（中英两份）、`CodegClient.kt` 中自己的 region、`core/model/wire/<任务短名>Requests.kt` 与 `core/model/<任务短名>.kt`、对应的 JVM 单测文件，以及重新生成的 `.taskmaster/docs/code-index/`。界面文字一律走字符串资源，不要硬编码。
- 新增字符串：放到新文件 `R/values/strings_<任务短名>.xml` 和 `R/values-zh-rCN/strings_<任务短名>.xml`，文件名只能用小写字母、数字、下划线（如 `strings_git_stash.xml`），中英文必须同时加；不要往共享的 `strings.xml` 里加。
- 新增接口调用：在 `A/core/network/CodegClient.kt` 末尾（`// region Transport` 之前）新建 `// region <任务短名>` … `// endregion`，只在自己的 region 内追加方法；请求体放新文件 `A/core/model/wire/<任务短名>Requests.kt`，响应模型放新文件 `A/core/model/<任务短名>.kt`。
- 新增页面：新文件放 `A/feature/<目录>/`；导航入口只在 `A/feature/main/MainShell.kt` 追加一个 `composable(...)` 路由，不改其它路由。
- 服务端绝不修改；app 必须能连官方原版 codeg 0.38。服务端不支持某接口（返回 404 / unknown）时隐藏入口而不是报错。

## 风格
- 视觉对齐网页端：颜色只用主题 token（`A/core/designsystem/theme/`），半透明色用 `Color.withOpacity()`，不要对 `--input`/`--border` 用 `Color.copy(alpha=…)`；圆角用 `Shape.kt` 的 web 刻度；优先复用 `A/core/designsystem/component/` 里的共享组件。
- 代码风格跟周围代码一致（命名、注释密度、ViewModel + `StateFlow<Ui>` 模式）。
- 危险操作（删除、reset、rebase、强制操作）必须二次确认，确认框里写明目标（分支名/提交/文件）。

## 验证
- 本地没有 JDK/Android SDK，不要尝试本地 gradle。CI 只在 push main / PR 时自动运行，任务分支要手动触发：
  `git push -u origin HEAD:task/Txx-<短名>` → `gh workflow run verify.yml --ref task/Txx-<短名>` → `gh run list --workflow verify.yml --branch task/Txx-<短名> -L 1` 取 run id → `gh run watch <id> --exit-status`；失败用 `gh run view <id> --log-failed` 看日志，修复后重复，直到通过。
- 有纯逻辑（解析、映射、状态推导）就在 `app/src/test/java/app/codeg/android/` 对应包下加 JVM 单测。
- 真机验证：只有一台手机，多个任务并行，**不要自己操作 adb/手机**。任务文档里写的“真机验证”由协调方在 CI 产出 APK 后统一做；你在最终回复里列出需要验证的具体步骤与预期结果。

## 提交
- 你运行在 codeg 为本任务新建的 git worktree 里（本地分支 `cli/Txx-<短名>`，基于 `main`）；只在这个 worktree 里改动和提交，不要切换分支、不要动主仓库目录 `/mnt/e/claude/codeg-android`。
- 远程分支名统一为 `task/Txx-<短名>`（见上面的 push 命令）。不要推 main，不要开 PR，不要 merge。
- 提交信息用英文祈使句，正文写清改了什么、为什么。
- 不要修改 `.taskmaster/tasks/tasks.json`（任务状态由协调方统一更新）。
- 结束时的最终回复包含：分支名、提交列表、CI 结果（run 链接与是否通过）、未完成项或需要人工确认的问题。
