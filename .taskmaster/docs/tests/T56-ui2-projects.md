# T56 测试文档：项目与 Git 图标、Toast 与例外清理

- 分支：`task/T56-ui2-projects`（本地工作分支 `cli/T56-ui2-projects`，按派发约定不切分支）。提交：本文件与代码同一提交，准确 hash 见交付报告，或 `git log -1 --format=%H -- .taskmaster/docs/tests/T56-ui2-projects.md`。
- 依赖：T48、T51、T52、T53、T60 已在基准中；全局 Toast Host 由 T58 接入，协调合并时须包含该任务。
- 测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。下列运行用例全部待执行。
- 提交使用 `[skip ci]`，避免推送触发常规 push CI；不手工调度 workflow。
- 本次只执行静态检查；未触发 CI，未执行 Gradle、JVM 测试、adb、代码索引生成。静态检查不能证明编译或运行通过。

## 1. 测试范围

路径简写：下表 Kotlin 文件相对 `A/feature/projects/`。

### 图标替换

| 文件 | 替换前（Material 尾名） | 替换后（Lucide） |
|---|---|---|
| `BranchPickerSheet.kt` | Add, Check | Check, Plus |
| `CloneRepoSheet.kt` | CreateNewFolder | FolderPlus |
| `CommitSheet.kt` | CheckCircle | CircleCheckBig |
| `DirectoryBrowserSheet.kt` | KeyboardArrowRight, ArrowUpward, Folder, Home | ArrowUp, ChevronRight, Folder, Home |
| `FolderChangesView.kt` | Undo, Add, CheckCircle, DeleteOutline, Difference | CircleCheckBig, FileDiff, Plus, Trash2, Undo2 |
| `FolderCommitsView.kt` | CallSplit, Cloud, CloudDownload, CloudOff, CloudUpload, History, Sync | CloudCheck, CloudDownload, CloudOff, CloudUpload, GitFork, History, RefreshCw |
| `FolderFilesView.kt` | InsertDriveFile, ArrowUpward, Folder | ArrowUp, File, Folder |
| `GitCredentialSheet.kt` | Key | KeyRound |
| `GitStatusStrip.kt` | CheckCircle, Close, ErrorOutline, WarningAmber |  |
| `ProjectDetailScreen.kt` | ArrowBack, FolderOpen | ArrowLeft, FolderOpen |
| `ProjectListScreen.kt` | KeyboardArrowRight, Add, CloudDownload, CreateNewFolder, FolderOpen, WarningAmber | ChevronRight, CloudDownload, FolderOpen, FolderPlus, Plus |
| `git/GitActionsMenu.kt` | MoreVert | EllipsisVertical |

图标属性从 `LucideIcons*.kt` 验证存在；行内/按钮内显式 16dp。CodegIconButton 继续提供 48dp 外层触摸目标，分支新增按钮保留 48dp。历史已推送标志按网页实际使用的 CloudCheck 对齐。加载 Spinner 使用 16/24dp。

### Toast 替换

| 文件 | 替换前 | 替换后 / 触发用例 |
|---|---|---|
| BranchPickerSheet.kt | checkout/create 临时错误文字 | error；成功回调前 success（T56-05L/D） |
| CloneRepoSheet.kt | 克隆错误色块 | error；成功回调前 success（T56-03L/D） |
| CommitSheet.kt | commitError 色块 | error；提交成功且推送开始前 success（T56-07L/D） |
| GitCredentialSheet.kt | 验证错误 Banner | error；消费本地错误状态，允许相同错误重试再次提示（T56-09L/D） |
| GitStatusStrip.kt | 成功/警告/错误 banner | busy 时 loading，完成时 update 为 Success/Warning/Error；无 loading ID 时直接对应 Toast；投递后调用原 dismiss，退出清理 loading（T56-06、08L/D） |
| ProjectListScreen.kt | 已有内容下刷新错误横条 | error；首次加载错误仍用 InlineError（T56-01L/D） |
| git/BranchManageSheet.kt | completed 文字与错误 | success/error；列表为空时错误文字保留（T56-10L/D） |
| git/MergeOpsSheet.kt | completed 文字与错误 | success/error；未 ready 时加载错误保留（T56-12、13L/D） |
| git/StashSheet.kt | result 文字与错误 | success/error；无条目时错误保留（T56-11L/D） |
| git/GitMiscSheet.kt | success 文字与错误 | success/error；无 remote/worktree 时错误保留（T56-14L/D） |
| forge/ForgeSheet.kt | TODO 旁错误文字 | error（含本地化 displayMessage）；页面错误与 token hint 保留，模型没有区分加载与操作错误（T56-15、16L/D） |
| importsessions/ImportSessionsSheet.kt | TODO 旁导入反馈 | error；分类结果和逐条错误作为持续审计明细保留（T56-17L/D） |

新增 Toast 标题资源 `t56_operation_failed` / `t56_operation_completed`（en + zh-rCN）；描述沿用已有本地化消息或服务端原始错误，不改含义。Git banner 沿用已有消息来源。没有为导入分类汇总新增笼统 success，失败数/跳过数仍能持续查看。

网页端证据：`layout/stash-dialog.tsx:53,59`、`layout/commit-dialog.tsx:479,493`、`layout/branch-dropdown.tsx:274,313`、`layout/aux-panel-git-changes-tab.tsx:810,818,929,933,945,950,976,981`、`layout/aux-panel-git-log-tab.tsx:1878,1885,1940,1950`、`forge/forge-issue-detail-sheet.tsx:2510,2549,2560`、`import-sessions/import-sessions-window.tsx:437`。已只读 grep 核对，移动端沿用现有操作文案并提供本地化通用标题，未新增业务完成判定。

### T53 组件替换

| 文件 | 替换前 | 替换后 |
|---|---|---|
| ProjectListScreen.kt | 手工分组显隐、FolderBadge | CodegCollapsible、CodegFolderBadge（名称 + 原颜色） |
| git/BranchManageSheet.kt | 直接展开比较 diff | CodegCollapsible，结果变化默认展开 |
| git/StashSheet.kt | 直接展开 stash diff | CodegCollapsible，结果变化默认展开 |
| git/ConflictListScreen.kt | 手写冲突文件行选择 | CodegCommandList + CodegCommandItem；搜索与 busy 禁用 |
| FolderFilesView.kt | FileViewer 图片变体 | CodegImagePreviewDialog；调用方异步解码，沿用 4096 边长降采样；关闭后可重新打开，文本/不可预览内容仍用 FileViewer |

本范围没有独立手写搜索框或头像待替换：冲突搜索由 CodegCommandList 自带；Git 历史网页端此处没有 Avatar，因此不强加 CodegAvatar/CodegInputGroup。没有残留对应 TODO。

### 第二阶段例外清理

| 文件 | 替换前 | 替换后 |
|---|---|---|
| DirectoryBrowserSheet.kt、BranchPickerSheet.kt、GitStatusStrip.kt | CircularProgressIndicator | CodegSpinner |
| forge/ForgeSheet.kt、importsessions/ImportSessionsSheet.kt | 不确定加载用细 Skeleton | CodegProgress() |
| forge/ForgeSheet.kt、forge/ForgeDetail.kt | 输入仅回调 guard | enabled = !ui.busy；正文/评论 minLines=3 maxLines=8 |
| forge/ForgeDetail.kt | 危险确认无 busy | CodegConfirmDialog(busy=ui.busy) |
| git/BranchManageSheet.kt | CodegDialog 收集 force 后再最终确认 | CodegConfirmDialog(content/busy)，保留第二次最终确认与原 force 传参 |
| git/MergeOpsSheet.kt | CodegDialog 收集 hard 分支名后最终确认 | CodegConfirmDialog(content/confirmEnabled/busy)，保留 typed == branch 校验和第二步 |
| groups/GroupDialogs.kt | 手写按分组实体绑定的行选择 | CodegSelect<Pair<Int?, String>>，以分组 ID 为 key，显式未分组项可显示/选择，同名分组不混选 |
| ForgeDetail.kt、ForgeSheet.kt | CodegFilterChip 命名参数调用 | 已兼容 T60 唯一签名，无需改写 |

其他改动：新增 `R/values/strings_t56.xml`、`R/values-zh-rCN/strings_t56.xml`；本测试文档。
公开 API：没有新增或修改公开业务/组件 API；新增的 ProjectImagePreview 为文件私有 Composable。
受影响入口：项目列表/分组、目录与克隆、项目详情、文件/变更/提交历史、分支、凭据、Git 操作、Forge、导入会话。
范围外：设计系统、网络/模型接口、其他 feature、主仓库、Gradle 配置、索引、导航及全局 Toast Host。

### grep 与静态自查（本次已执行）

- `Icons\.`：0；Material icon import：0。
- `TODO\(T5[0-9]\)`：0。
- `OutlinedTextField(`：0；`CircularProgressIndicator(`：0；`LinearProgressIndicator(`：0。
- `CodegSkeleton(`：0；两个不确定条均用 CodegProgress()。
- CodegDialog 保留四处：分组命名、项目属性入口、分支凭据、Forge 新建；均为普通表单，不承载危险最终操作。
- `git diff --check` 通过；21 个变更 Kotlin 文件词法括号检查通过；23 个 Lucide 属性均存在；新增两份 XML 解析通过。这些检查不代替 Kotlin 编译。
- 业务 ViewModel、网络请求、Diff 解析与选择集合逻辑未修改；不新增 Gradle 依赖。

## 2. 编译检查

自评风险：中等，未编译。需统一 `:app:check` 重点关注：
- `FolderFilesView.kt:171`：produceState/ImageBitmap/BitmapPainter 的 Compose 推断与 Android 解码；Dispatchers.Default、取消与极大图片内存需真机验证。
- `groups/GroupDialogs.kt:66`：泛型 Pair<Int?, String> 与 nullable ID 的 key；未分组使用显式非空 Pair，避免 null selected 丢失标签。
- `GitStatusStrip.kt:35`：ToastId/ToastType 的可见性、LaunchedEffect 和 DisposableEffect；按已读取签名调用。
- `ProjectListScreen.kt:208`：header 闭包与折叠动画作用域；组内嵌套按钮点击不应触发父折叠。
- `git/BranchManageSheet.kt:73`：content 的 ColumnScope；两阶段确认 force 保持。
- `git/MergeOpsSheet.kt:57`：content/confirmEnabled/busy 命名参数，硬重置两步校验。
- `forge/ForgeDetail.kt:72`：enabled、minLines/maxLines 与 FilterChip 新签名；已有 ExperimentalLayoutApi 注解沿用。

预期 lint/单测关注：资源中英文一致性、未使用导入、Composable 调用作用域、泛型重载；原有项目/Git/Forge/导入模型单测需统一回归。未声明编译通过。

## 3. 单元测试

| 测试类::方法 | 覆盖点 |
|---|---|
| 无新增 JVM 测试 | 只修改 Compose 显示、现有反馈状态投递与参数接入；未改解析/映射/状态推导算法。图片采样沿用 FileViewer 既有算法，不新增镜像实现测试。 |

未运行 JVM 测试；协调方统一编译后执行已有测试。

## 4. 测试用例

前置条件：测试服 T13Test；可删除的测试仓库、分支、分组、remote/worktree、Forge issue/PR、可扫描会话；准备文本、图片、超大/损坏图片、二进制、冲突和需认证的远端。危险操作只针对测试数据。须合入 T58 Toast Host；缺 Host 时共享组件会静默丢弃 Toast，不应判为本任务用例通过。

所有用例均待执行；每组明/暗主题分别重新进入操作，不能复用另一主题的截图判定。允许手机 Sheet/原生键盘/触摸区域和桌面布局差异；颜色与组件 token 由设计系统提供。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T56-01L | 项目列表/分组 | 创建/重命名/删除测试分组；折叠展开，点标题内按钮；模拟刷新断网后重试；项目移动到同名另一分组及未分组 | 折叠不丢条目；嵌套按钮不误折叠；徽标名称/颜色正确；刷新仅 Toast error；初次失败保留 InlineError；ID 选择不混淆 | 明 |
| T56-01D | 项目列表/分组 | 创建/重命名/删除测试分组；折叠展开，点标题内按钮；模拟刷新断网后重试；项目移动到同名另一分组及未分组 | 折叠不丢条目；嵌套按钮不误折叠；徽标名称/颜色正确；刷新仅 Toast error；初次失败保留 InlineError；ID 选择不混淆 | 暗 |
| T56-02L | 目录浏览 | 打开目录，Home/上级/子目录，模拟慢网；选择当前目录，关闭再开 | 16dp 图标；24dp Spinner；路径/选择结果保持；触摸目标可达 | 明 |
| T56-02D | 目录浏览 | 打开目录，Home/上级/子目录，模拟慢网；选择当前目录，关闭再开 | 16dp 图标；24dp Spinner；路径/选择结果保持；触摸目标可达 | 暗 |
| T56-03L | 克隆 | 合法地址成功克隆；错误 URL/凭据失败，重复同一错误，再修正重试 | 成功 success、失败 error 各一次；相同错误重试可再次提示；busy 与目标目录规则保持 | 明 |
| T56-03D | 克隆 | 合法地址成功克隆；错误 URL/凭据失败，重复同一错误，再修正重试 | 成功 success、失败 error 各一次；相同错误重试可再次提示；busy 与目标目录规则保持 | 暗 |
| T56-04L | 详情/文件 | 切文件/变更/提交；预览文本、图片、损坏/超大图片、二进制；缩放拖动、关闭图片再打开、返回 | 图片独立预览支持缩放与关闭，重新打开可用；文本/不支持消息保留；损坏图片不崩溃 | 明 |
| T56-04D | 详情/文件 | 切文件/变更/提交；预览文本、图片、损坏/超大图片、二进制；缩放拖动、关闭图片再打开、返回 | 图片独立预览支持缩放与关闭，重新打开可用；文本/不支持消息保留；损坏图片不崩溃 | 暗 |
| T56-05L | 分支切换 | 切本地/远程分支、新建；成功后返回；制造创建/切换失败并重复 | 当前分支标记正确；成功 success 后关闭；失败 error 留在原表单可重试 | 明 |
| T56-05D | 分支切换 | 切本地/远程分支、新建；成功后返回；制造创建/切换失败并重复 | 当前分支标记正确；成功 success 后关闭；失败 error 留在原表单可重试 | 暗 |
| T56-06L | 变更/危险操作 | 看 diff；暂存、丢弃、删除分别先取消再确认；模拟失败 | 图标语义和16dp正确；危险框含路径；取消不执行；Git反馈按成功/失败 Toast；busy loading 结束更新 | 明 |
| T56-06D | 变更/危险操作 | 看 diff；暂存、丢弃、删除分别先取消再确认；模拟失败 | 图标语义和16dp正确；危险框含路径；取消不执行；Git反馈按成功/失败 Toast；busy loading 结束更新 | 暗 |
| T56-07L | 提交 | 勾选 tracked/untracked，消息为空/多行；分别提交、提交并推送，模拟提交失败 | 原选择和校验保持；提交成功 success，失败 error；推送只在提交后开始，后续推送失败单独反馈 | 明 |
| T56-07D | 提交 | 勾选 tracked/untracked，消息为空/多行；分别提交、提交并推送，模拟提交失败 | 原选择和校验保持；提交成功 success，失败 error；推送只在提交后开始，后续推送失败单独反馈 | 暗 |
| T56-08L | 历史/同步 | fetch/pull/push 成功与失败，远端不可用警告；慢网开始后切 tab/退出；打开历史、复制hash、看diff | loading/update 对应 Success/Error/Warning；无卡死loading，退出清理；CloudCheck/CloudOff正确；忙状态按钮不重复 | 明 |
| T56-08D | 历史/同步 | fetch/pull/push 成功与失败，远端不可用警告；慢网开始后切 tab/退出；打开历史、复制hash、看diff | loading/update 对应 Success/Error/Warning；无卡死loading，退出清理；CloudCheck/CloudOff正确；忙状态按钮不重复 | 暗 |
| T56-09L | Git凭据 | 无效GitHub token重复两次再有效；普通主机凭据；取消再触发 | 无效 token error 可重复；retry hint持续显示；凭据验证/保存/取消流程保持且不泄露密码 | 明 |
| T56-09D | Git凭据 | 无效GitHub token重复两次再有效；普通主机凭据；取消再触发 | 无效 token error 可重复；retry hint持续显示；凭据验证/保存/取消流程保持且不泄露密码 | 暗 |
| T56-10L | 分支管理 | 刷新，比较并折叠/展开；更新成功/失败；本地 force 删除两步确认、取消，远程删除 | 比较可折叠，内容不丢；success/error出现；两次确认含分支；force传递保持，busy拦截 | 明 |
| T56-10D | 分支管理 | 刷新，比较并折叠/展开；更新成功/失败；本地 force 删除两步确认、取消，远程删除 | 比较可折叠，内容不丢；success/error出现；两次确认含分支；force传递保持，busy拦截 | 暗 |
| T56-11L | Stash | save/apply/pop/drop/clear成功及失败；diff展开/折叠；确认取消 | success/error与操作结果对应；diff展开默认正确；取消不变；keep-index和message保持 | 明 |
| T56-11D | Stash | save/apply/pop/drop/clear成功及失败；diff展开/折叠；确认取消 | success/error与操作结果对应；diff展开默认正确；取消不变；keep-index和message保持 | 暗 |
| T56-12L | Merge/Rebase/Reset | 操作成功与失败；soft/mixed/hard；hard输入错/对分支名，先取消再两次确认 | 错误名不能继续；目标含分支→提交；成功success/失败error；无重复执行 | 明 |
| T56-12D | Merge/Rebase/Reset | 操作成功与失败；soft/mixed/hard；hard输入错/对分支名，先取消再两次确认 | 错误名不能继续；目标含分支→提交；成功success/失败error；无重复执行 | 暗 |
| T56-13L | 冲突 | 搜索冲突文件，清空与无匹配；切base/ours/theirs；解决/continue/abort，含失败 | CommandList可搜索且busy禁用；原版本/危险确认保持；完成success/失败error；仅无冲突可continue | 明 |
| T56-13D | 冲突 | 搜索冲突文件，清空与无匹配；切base/ours/theirs；解决/continue/abort，含失败 | CommandList可搜索且busy禁用；原版本/危险确认保持；完成success/失败error；仅无冲突可continue | 暗 |
| T56-14L | Remote/Worktree | 加载/刷新；增改删remote、fetch认证、增删worktree；模拟失败；删除取消 | 成功success/失败error（当前模型load亦会置success，保持已有语义）；目标准确，凭据重试保持；unknown能力隐藏 | 明 |
| T56-14D | Remote/Worktree | 加载/刷新；增改删remote、fetch认证、增删worktree；模拟失败；删除取消 | 成功success/失败error（当前模型load亦会置success，保持已有语义）；目标准确，凭据重试保持；unknown能力隐藏 | 暗 |
| T56-15L | Forge列表/新建 | 过滤分页刷新；新建长正文与标签；busy尝试修改，失败后重试 | Progress连续；禁用字段不可修改；正文3～8行可滚动；失败error，持续错误/token hint保留；成功仍执行原关闭刷新 | 明 |
| T56-15D | Forge列表/新建 | 过滤分页刷新；新建长正文与标签；busy尝试修改，失败后重试 | Progress连续；禁用字段不可修改；正文3～8行可滚动；失败error，持续错误/token hint保留；成功仍执行原关闭刷新 | 暗 |
| T56-16L | Forge详情 | 文件/评论分页，评论失败，关闭/重开、merge各method成功与失败；busy操作确认框 | 失败error；comment禁用且最多8行；确认目标明确、busy拦截；原成功状态刷新不变 | 明 |
| T56-16D | Forge详情 | 文件/评论分页，评论失败，关闭/重开、merge各method成功与失败；busy操作确认框 | 失败error；comment禁用且最多8行；确认目标明确、busy拦截；原成功状态刷新不变 | 暗 |
| T56-17L | 导入会话 | 扫描；全选/分组/单项；所选导入、全量先取消再确认；模拟网络失败及部分记录失败 | 网络失败error；分类汇总与逐条错误持续可读；只有new可选；busy Progress与关闭/选择保护保持 | 明 |
| T56-17D | 导入会话 | 扫描；全选/分组/单项；所选导入、全量先取消再确认；模拟网络失败及部分记录失败 | 网络失败error；分类汇总与逐条错误持续可读；只有new可选；busy Progress与关闭/选择保护保持 | 暗 |
| T56-18L | 兼容/全局 | 官方0.38/缺能力服务逐一进入；窄屏、长文字、键盘、TalkBack；中英切换，连续反馈 | 404/unknown入口沿用隐藏路径；不新增报错入口；没有越界布局；Toast标题本地化，关闭/返回无副作用 | 明 |
| T56-18D | 兼容/全局 | 官方0.38/缺能力服务逐一进入；窄屏、长文字、键盘、TalkBack；中英切换，连续反馈 | 404/unknown入口沿用隐藏路径；不新增报错入口；没有越界布局；Toast标题本地化，关闭/返回无副作用 | 暗 |

对照网页端（每组 L/D 同一参考，路径相对 WEB/components/）：

- T56-01L/D：`layout/folder-title-bar.tsx`。
- T56-02L/D：`layout/aux-panel-file-tree-tab.tsx`。
- T56-03L/D：`layout/folder-title-bar.tsx`。
- T56-04L/D：`layout/aux-panel-file-tree-tab.tsx`。
- T56-05L/D：`layout/branch-dropdown.tsx`。
- T56-06L/D：`layout/aux-panel-git-changes-tab.tsx`。
- T56-07L/D：`layout/commit-dialog.tsx`。
- T56-08L/D：`layout/aux-panel-git-log-tab.tsx`。
- T56-09L/D：`layout/branch-dropdown.tsx`。
- T56-10L/D：`layout/branch-dropdown.tsx`。
- T56-11L/D：`layout/stash-dialog.tsx`。
- T56-12L/D：`layout/aux-panel-git-log-tab.tsx`。
- T56-13L/D：`layout/conflict-dialog.tsx`。
- T56-14L/D：`layout/remote-manage-dialog.tsx`。
- T56-15L/D：`forge/forge-new-issue-dialog.tsx`。
- T56-16L/D：`forge/forge-issue-detail-sheet.tsx`。
- T56-17L/D：`import-sessions/import-sessions-window.tsx`。
- T56-18L/D：`layout/aux-panel-git-changes-tab.tsx`。

## 5. 回归点

- `.taskmaster/docs/tests/T48-ui-projects.md` 的 T48-01L/D～T48-18L/D 全部需复测；本表 T56-01～18 与其逐组对应。
- 重点：T48-01 同名分组/未分组 ID、嵌套按钮；T48-04 图片返回与提交历史图片预览；T48-05/07/09 重复相同错误 Toast；T48-06/08 Git banner消费后不重复与 loading 清理；T48-10/12 两步危险确认的原参数/校验。
- T48-11/13 diff折叠与冲突选择；T48-14认证/能力隐藏；T48-15/16禁用输入、长正文滚动；T48-17分类明细；T48-18明暗、键盘、小屏、TalkBack。

## 6. 已知风险 / 未完成

- 编译、JVM与真机验证未执行；协调方需执行第2、4、5节，不把静态检查当成功证据。
- Toast可见性依赖T58宿主；弹出Sheet/Dialog时覆盖顺序、短时间队列合并/离开页面后的loading清理需真机检查。
- 持久错误保留：BranchManage/Stash/GitMisc空数据与Merge未ready时、Forge共享加载/操作error；这些状态缺少独立加载错误通道，不改业务模型来区分，Toast同步通知，持续信息保留。GitMisc既有success包括load/refresh，本任务未改其完成定义。
- 冲突列表为空保留“已解决”；搜索无匹配使用共享组件默认本地化提示。
- CodegListRow没有enabled API；现有回调guard保留，无障碍仍可能宣布可点击。CodegCollapsible/CommandList内部触摸区域由共享组件决定，本任务没有越界改设计系统。
- 图片解码失败/内存压力、嵌套滚动与大数据折叠动画需真机验证。没有修改接口、替别的任务补共享组件或提交索引。
