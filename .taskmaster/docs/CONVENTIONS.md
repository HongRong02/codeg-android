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

## 测试文档模式（T51 起，以及任务文档注明“测试文档模式”的任务）
- **不要触发 CI、不要 `gh run watch`**，也不要本地 gradle。所有任务完成后由协调方统一合并、编译、测试。
- 改为写测试文档 `.taskmaster/docs/tests/Txx-<短名>.md`（按 `.taskmaster/docs/tests/TEMPLATE.md` 填写），和代码一起提交。写明测试范围、编译风险、单测清单、逐条可执行的测试用例（步骤 + 预期，明/暗）、回归点。
- 不要运行 `gen_code_index.py`、不要提交 `.taskmaster/docs/code-index/`（协调方合并后统一生成，避免并行冲突）。
- 因为没人替你编译，写代码时要格外注意：import 完整、只用项目里已有的依赖和 API（新依赖一律不加）、`@Composable` 调用位置正确、`when` 分支穷尽、`internal`/`private` 可见性正确。拿不准的 API 先在 `~/.gradle` 缓存或项目现有代码里 grep 确认用法。
- 最终回复中把“CI 结果”换成“测试文档路径 + 自评编译风险点”。

## 验证（CI 模式，T50 及以前未注明测试文档模式的任务）
- 本地没有 JDK/Android SDK，不要尝试本地 gradle。CI 只在 push main / PR 时自动运行，任务分支要手动触发：
  `git push -u origin HEAD:task/Txx-<短名>` → `gh workflow run verify.yml --ref task/Txx-<短名>` → `gh run list --workflow verify.yml --branch task/Txx-<短名> -L 1` 取 run id → `gh run watch <id> --exit-status`；失败用 `gh run view <id> --log-failed` 看日志，修复后重复，直到通过。
- 有纯逻辑（解析、映射、状态推导）就在 `app/src/test/java/app/codeg/android/` 对应包下加 JVM 单测。
- 真机验证：只有一台手机，多个任务并行，**不要自己操作 adb/手机**。任务文档里写的“真机验证”由协调方在 CI 产出 APK 后统一做；你在最终回复里列出需要验证的具体步骤与预期结果。

## 提交
- 你运行在 codeg 为本任务新建的 git worktree 里（本地分支 `cli/Txx-<短名>`，基于派发时主仓库的 HEAD：测试文档模式的任务基于 `sit`，其余基于 `main`）；只在这个 worktree 里改动和提交，不要切换分支、不要动主仓库目录 `/mnt/e/claude/codeg-android`。
- 远程分支名统一为 `task/Txx-<短名>`（见上面的 push 命令）。不要推 main，不要开 PR，不要 merge。
- 提交信息用英文祈使句，正文写清改了什么、为什么。
- 不要修改 `.taskmaster/tasks/tasks.json`（任务状态由协调方统一更新）。
- 结束时的最终回复包含：分支名、提交列表、CI 结果（run 链接与是否通过）、未完成项或需要人工确认的问题。

## 集成分支 sit（协调方执行，任务方了解即可）
- 所有任务分支统一 `--no-ff` 合入 `sit`，在 `sit` 上跑 verify CI 并统一真机测试；任务分支不再单独跑 CI。
- 发现问题：协调方先定位问题范围（哪个任务、哪些文件），回到**原任务分支**修改（原任务方返工或协调方修），再重新合入 `sit` 复测；不要直接在 `sit` 上改业务代码（合并冲突解决和 code-index 重新生成除外）。
- 全部通过后，协调方把 `sit` 合入 `main`（push main 即发版）。

## 提交信息
- 提交信息里不要加 `Co-Authored-By` 或任何 AI/工具署名行（包括 Claude、Codex、Gemini）。
