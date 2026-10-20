# 委派任务通用约定（每个任务开工前必读，仅此一页）

## 阅读范围
- 只读：本页 + 你的任务文档（开发任务 `.taskmaster/docs/tasks/Txx-*.md`，修复任务 `.taskmaster/docs/bugs/Bnn-*.md`）+ 任务文档“代码定位”里列出的文件/行。
- 不要通读 README、PRD、整个 CodegClient.kt 或其它 feature 目录。需要找别的符号时，先查 `.taskmaster/docs/code-index/<区域>.md`（按文件列出所有声明及行号），再只打开对应行附近。
- 服务端接口：查 `.taskmaster/docs/api-diff.md` 得到 handler 与请求结构体的 `文件:行`（路径相对 `E:\claude\codeg\src-tauri\src\web\`），只读那个结构体和 handler 函数。
- 行号以索引生成时为准，有偏移就用符号名 grep。索引由协调方在合并后统一生成，任务方不要运行生成脚本。

## 路径简写
- `A/` = `app/src/main/java/app/codeg/android/`
- `R/` = `app/src/main/res/`
- `WEB/` = `E:\claude\codeg\src\`（网页端，只读参考）
- `SRV/` = `E:\claude\codeg\src-tauri\src\web\`（服务端，只读，绝不修改）
- 在 WSL / codeg 里运行时，`E:\claude\...` 对应 `/mnt/e/claude/...`（例如 `WEB/` = `/mnt/e/claude/codeg/src/`）。

## 改动边界（并行防冲突）
- 只改任务文档“改动范围”里列出的文件；需要改范围外文件时停下来，在任务里记录原因（`task-master update-subtask` 或回报），不要自行扩大范围。
- 例外（默认在范围内，无需任务文档单独列出）：按下面规则新建的 `strings_<任务短名>.xml`（中英两份）、`CodegClient.kt` 中自己的 region、`core/model/wire/<任务短名>Requests.kt` 与 `core/model/<任务短名>.kt`、对应的 JVM 单测文件，以及自己的测试文档 `.taskmaster/docs/tests/Txx-<短名>.md`。界面文字一律走字符串资源，不要硬编码。
- 新增字符串：放到新文件 `R/values/strings_<任务短名>.xml` 和 `R/values-zh-rCN/strings_<任务短名>.xml`，文件名只能用小写字母、数字、下划线（如 `strings_git_stash.xml`），中英文必须同时加；不要往共享的 `strings.xml` 里加。
- 新增接口调用：在 `A/core/network/CodegClient.kt` 末尾（`// region Transport` 之前）新建 `// region <任务短名>` … `// endregion`，只在自己的 region 内追加方法；请求体放新文件 `A/core/model/wire/<任务短名>Requests.kt`，响应模型放新文件 `A/core/model/<任务短名>.kt`。
- 新增页面：新文件放 `A/feature/<目录>/`；导航入口只在 `A/feature/main/MainShell.kt` 追加一个 `composable(...)` 路由，不改其它路由。
- 服务端绝不修改；app 必须能连官方原版 codeg 0.38。服务端不支持某接口（返回 404 / unknown）时隐藏入口而不是报错。

## 风格
- 视觉对齐网页端：颜色只用主题 token（`A/core/designsystem/theme/`），半透明色用 `Color.withOpacity()`，不要对 `--input`/`--border` 用 `Color.copy(alpha=…)`；圆角用 `Shape.kt` 的 web 刻度；优先复用 `A/core/designsystem/component/` 里的共享组件。
- 代码风格跟周围代码一致（命名、注释密度、ViewModel + `StateFlow<Ui>` 模式）。
- 危险操作（删除、reset、rebase、强制操作）必须二次确认，确认框里写明目标（分支名/提交/文件）。

## 任务头部字段（关系图）
- 任务文档头部表格写明了批次、层级、硬依赖、软依赖、同层并行任务和**冲突域**。完整关系图见 `.taskmaster/docs/graphs/<批次>.md`（只需看自己那一行）。
- **冲突域**就是你的改动范围：同层其他任务正在并行改别的冲突域，越界会造成合并冲突。共享追加点（自己的 `strings_<短名>.xml`、`CodegClient.kt` 自己的 region、`MainShell.kt` 路由表追加一个 `composable`）除外。
- **软依赖**：同层任务提供的东西还没合入，按文档里的契约写占位，例如先用现有实现，并在代码里留 `// TODO(Txx): <约定的符号>`。不要自己实现对方的东西，也不要去读对方的分支。
- 硬依赖的代码已经在你的基准分支（`sit`）里，可以直接使用。

## 开发任务（默认：测试文档模式）
- **不要触发 CI、不要 `gh run watch`**，也不要本地 gradle。协调方会在你交付后编译，再统一合入 `sit` 测试。
- 改为写测试文档 `.taskmaster/docs/tests/Txx-<短名>.md`（按 `.taskmaster/docs/tests/TEMPLATE.md` 填写），和代码一起提交。写明测试范围、编译风险、单测清单、逐条可执行的测试用例（步骤 + 预期，明/暗）、回归点。
- 不要运行 `gen_code_index.py`、不要提交 `.taskmaster/docs/code-index/`（协调方合并后统一生成，避免并行冲突）。
- 因为没人替你实时编译，写代码时要格外注意：import 完整、只用项目里已有的依赖和 API（新依赖一律不加）、`@Composable` 调用位置正确、`when` 分支穷尽、`internal`/`private` 可见性正确、不要声明与已有符号同名的公开函数（先在 code-index 里搜）。拿不准的 API 先在 `~/.gradle` 缓存或项目现有代码里 grep 确认用法。
- 推送：`git push -u origin HEAD:task/Txx-<短名>`。
- 交付后如果协调方把编译日志或到货检查意见发回给你，在同一分支上修改并重新推送。

## 修复任务（bug 单：CI 模式）
- 任务文档是 `.taskmaster/docs/bugs/Bnn-<区域>.md`。基于最新 `sit`，分支 `fix/Bnn-<区域>`，只改 bug 单"改动范围"里的文件。
- 每个问题定位清楚再改，提交信息写 `Fixes Bnn-k (Txx)`。不要顺手重构，也不要修 bug 单以外的问题（发现了就写在报告里）。
- 验证用 CI：`git push -u origin HEAD:fix/Bnn-<区域>` → `gh workflow run verify.yml --ref fix/Bnn-<区域>` → `gh run list --workflow verify.yml --branch fix/Bnn-<区域> -L 1` 取 run id → `gh run watch <id> --exit-status`；失败用 `gh run view <id> --log-failed` 看日志，修复后重复，直到通过。
- 在 bug 单每个问题下补写"修复说明"（原因 + 改法 + 需要复测的用例）。

## 通用验证规则
- 本地没有 JDK/Android SDK，不要尝试本地 gradle。
- 有纯逻辑（解析、映射、状态推导）就在 `app/src/test/java/app/codeg/android/` 对应包下加 JVM 单测。
- 真机验证：只有一台手机，**不要自己操作 adb/手机**，真机测试由协调方在 `sit` 上统一执行。

## 提交
- 你运行在 codeg 为本任务新建的 git worktree 里（本地分支 `cli/<任务名>`，基于派发时主仓库的 HEAD，即 `sit`）；只在这个 worktree 里改动和提交，不要切换分支、不要动主仓库目录 `/mnt/e/claude/codeg-android`。
- 远程分支名：开发任务 `task/Txx-<短名>`，修复任务 `fix/Bnn-<区域>`。不要推 `main` 或 `sit`，不要开 PR，不要 merge。
- 提交信息用英文祈使句，正文写清改了什么、为什么。
- 不要修改 `.taskmaster/tasks/tasks.json` 和 `.taskmaster/docs/graphs/`（状态由协调方统一更新）。
- 结束时的最终回复包含：分支名、提交列表；开发任务写测试文档路径 + 自评编译风险点，修复任务写 CI run 链接与结果；以及未完成项或需要人工确认的问题。

## 集成与发布（协调方执行，任务方了解即可）
- 完整流程见 `.taskmaster/docs/WORKFLOW.md`。要点：任务分支到货先编译，通过后合入 `sit`；**合入 sit 之后发现的问题不回原分支**，而是写成 bug 单，基于最新 sit 开 `fix/*` 修复任务；全部测试通过后 `sit` 合入 `main` 发版。

## 提交信息
- 提交信息里不要加 `Co-Authored-By` 或任何 AI/工具署名行（包括 Claude、Codex、Gemini）。
