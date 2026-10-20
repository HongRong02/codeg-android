# 任务方约定（开工前必读，仅此一页）

## 阅读范围
- 只读：本页 + 你的任务文档（开发任务 `.taskmaster/docs/tasks/Txx-*.md`，修复任务 `.taskmaster/docs/bugs/Bnn-*.md`）+ 任务文档"代码定位"里列出的文件/行。不要通读 README、PRD、整个 `CodegClient.kt` 或其他 feature 目录。
- 找其他符号：先查 `.taskmaster/docs/code-index/<区域>.md`（按文件列出声明及行号），只打开对应行附近；行号有偏移就按符号名 grep。
- 服务端接口：查 `.taskmaster/docs/api-diff.md` 得到 handler 和请求结构体的位置，只读那个结构体和函数。

## 路径简写
- `A/` = `app/src/main/java/app/codeg/android/`，`R/` = `app/src/main/res/`
- `WEB/` = `/mnt/e/claude/codeg/src/`（网页端，只读参考），`SRV/` = `/mnt/e/claude/codeg/src-tauri/src/web/`（服务端，只读，绝不修改）

## 改动边界
- 任务文档头部写了**冲突域**，这就是你的改动范围。同层其他任务正在并行改别的冲突域，越界会造成合并冲突。需要改范围外的文件时，停下来写进报告，不要自行扩大范围。
- 默认在范围内的（共享追加点）：
  - 新字符串：自己新建 `R/values/strings_<短名>.xml` 和 `R/values-zh-rCN/strings_<短名>.xml`（文件名只用小写字母、数字、下划线，中英文同时加），不要往共享的 `strings.xml` 里加。界面文字一律走字符串资源。
  - 新接口：在 `A/core/network/CodegClient.kt` 末尾（`// region Transport` 之前）新建自己的 `// region <短名>` … `// endregion`；请求体放 `A/core/model/wire/<短名>Requests.kt`，响应模型放 `A/core/model/<短名>.kt`。
  - 新页面：放 `A/feature/<目录>/`，在 `A/feature/main/MainShell.kt` 路由表追加一个 `composable(...)`，不改其他路由。
  - 自己的 JVM 单测，以及自己的测试文档。
- **软依赖**：同层任务提供的东西还没合入时，按任务文档里的契约写占位（先用现有实现，并留 `// TODO(Txx): <约定的符号>`）。不要替对方实现，也不要读对方的分支。硬依赖的代码已经在你的基准分支 `sit` 里，可以直接用。
- 服务端不支持某个接口（返回 404 或 unknown）时，隐藏入口而不是报错。
- 不新增 Gradle 依赖。

## 风格
- 视觉对齐网页端：颜色只用 `CodegTheme.colors.web.*` 等主题 token；半透明色用 `Color.withOpacity()`，不要对 `--input`/`--border` 用 `Color.copy(alpha=…)`；圆角用 `CodegShapes` 的 web 刻度；优先复用 `A/core/designsystem/` 的共享组件，图标用 `Lucide.*`。
- 代码风格跟周围代码一致（命名、注释密度、ViewModel + `StateFlow<Ui>` 模式）。
- 危险操作（删除、reset、rebase、强制操作）必须二次确认，确认框写明目标（分支名、提交、文件）。

## 开发任务（测试文档模式）
- **不要触发 CI、不要本地 gradle**（本地也没有 JDK/SDK），不要运行 `gen_code_index.py`、不要提交 `.taskmaster/docs/code-index/`。协调方会在你交付后编译。
- 写测试文档 `.taskmaster/docs/tests/Txx-<短名>.md`（按同目录的 `TEMPLATE.md`），和代码一起提交。
- 没人实时替你编译，所以要格外注意：import 完整、`@Composable` 调用位置正确、`when` 分支穷尽、可见性正确、调用标了实验性 API 的组件（如 `CodegTopBar`）时加 `@OptIn(ExperimentalMaterial3Api::class)`、不声明和已有符号同名的公开函数（先在 code-index 里搜）、只用项目里已有的 API（拿不准就 grep 现有用法或 `~/.gradle` 缓存）。
- 有纯逻辑（解析、映射、状态推导）就在 `app/src/test/java/app/codeg/android/` 对应包下加 JVM 单测。
- 推送：`git push -u origin HEAD:task/Txx-<短名>`。协调方如果把编译日志或检查意见发回给你，在同一分支上修改并重新推送。

## 修复任务（CI 模式）
- 按 bug 单修，分支 `fix/Bnn-<区域>`，只改 bug 单"改动范围"里的文件；不顺手重构，不修 bug 单以外的问题（发现了写进报告）。提交信息写 `Fixes Bnn-k (Txx)`。
- 验证：`git push -u origin HEAD:fix/Bnn-<区域>` → `gh workflow run verify.yml --ref fix/Bnn-<区域>` → `gh run list --workflow verify.yml --branch fix/Bnn-<区域> -L 1` 取 run id → `gh run watch <id> --exit-status`；失败就 `gh run view <id> --log-failed`，修好再跑，直到通过。
- 在 bug 单每个问题下补写"修复说明"（原因、改法、需要复测的用例）。

## 提交与报告
- 你在 codeg 为本任务新建的 worktree 里（基于 `sit`）。只在这个 worktree 里改动和提交，不要切换分支，不要动主仓库 `/mnt/e/claude/codeg-android`，不要推 `main`/`sit`，不要开 PR，不要 merge。
- 不要修改 `.taskmaster/docs/graphs/`（状态由协调方维护）。
- 不要操作 adb 或手机，真机测试由协调方统一做。
- 提交信息用英文祈使句，正文写清改了什么、为什么；**不要加 `Co-Authored-By` 或任何 AI/工具署名**。
- 最终回复：分支名、提交列表；开发任务写测试文档路径和自评编译风险点，修复任务写 CI run 链接和结果；以及未完成项和需要人工确认的问题。
