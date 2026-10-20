# T48 测试文档：项目与 Git 界面对齐

- 分支：`task/T48-ui-projects`　提交：与本文件同一提交（最终 hash 见交付报告；可用 `git log -1 --format=%H -- .taskmaster/docs/tests/T48-ui-projects.md` 查询）。
- 依赖：T43–T46 已合入 sit；T52/T53/T54 后续处理对应组件。测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 执行状态：仅完成代码静态检查；下列编译、单测、真机用例均未运行。不触发 CI、不运行本地 Gradle/adb/索引生成。

## 1. 测试范围

路径简写：下表文件均相对 `app/src/main/java/app/codeg/android/feature/projects/`。

| 改动文件 | 替换前 | 替换后 |
|---|---|---|
| `BranchPickerSheet.kt` | ModalBottomSheet、FilledIconButton、ListItem、手写标题 | CodegSheet、CodegButton(Secondary/Icon)、CodegListRow、CodegSectionHeader |
| `CloneRepoSheet.kt` | 自绘全屏 Dialog/滚动/footer | CodegSheet 统一滚动与固定 footer；保留 CodegTextField/SecretField/PrimaryButton |
| `CommitSheet.kt` | 自绘 Dialog、TextButton、手写文件选择行/分区 | CodegSheet、CodegButton(Ghost)、CodegListRow/CodegCheckbox、CodegSectionHeader |
| `DirectoryBrowserSheet.kt` | 自绘全屏 Dialog、目录行 | CodegFullScreenSheet、CodegListRow、顶部固定 CodegButton |
| `FolderChangesView.kt` | ModalBottomSheet、AlertDialog、TextButton、ListItem、操作行 | CodegSheet、CodegConfirmDialog(destructive)、CodegListRow |
| `FolderCommitsView.kt` | 手写同步按钮、ListItem、TextButton、手写卡片/文件行/摘要 | CodegButton(Outline/Ghost)、CodegListRow、CodegCard/Content、CodegSectionHeader、CodegBadge |
| `FolderFilesView.kt` | ListItem、预览手写标题 | CodegListRow、CodegSectionHeader |
| `GitChangeVisuals.kt` | 手写变更状态字母徽章 | CodegBadge(Default/Secondary/Destructive) |
| `GitCredentialSheet.kt` | 自绘 Dialog、TextButton、Checkbox | CodegSheet、CodegButton(Ghost)、CodegCheckbox；保留 SecretField |
| `GitStatusStrip.kt` | 手写圆角、临时反馈 | CodegShapes.small；保留反馈并标记 TODO(T52) |
| `ProjectDetailScreen.kt` | 本地 SegmentedTabs/CodegSegmented、手写圆角 | CodegTabs、CodegShapes/PillShape |
| `ProjectListScreen.kt` | DropdownMenu/Item、AlertDialog、TextButton、手写行/分组标题/卡片/徽章 | CodegDropdownMenu/MenuItem、CodegConfirmDialog、CodegButton、CodegListRow/SectionHeader/Card/Badge |
| `forge/ForgeDetail.kt` | TextButton、FilterChip、OutlinedTextField、HorizontalDivider、AlertDialog、文本状态 | CodegButton、CodegFilterChip、CodegTextField、CodegSeparator、CodegConfirmDialog、CodegBadge/SectionHeader |
| `forge/ForgeSheet.kt` | Dialog/AlertDialog、TextButton、OutlinedTextField、FilterChip、HorizontalDivider、LinearProgressIndicator、手写行 | CodegFullScreenSheet/Dialog、CodegButton/TextField/FilterChip/Tabs/Separator/Skeleton/ListRow/Badge |
| `git/BranchManageSheet.kt` | ModalBottomSheet、AlertDialog、TextButton、OutlinedTextField、Checkbox、手写标题/行 | CodegSheet/Dialog/ConfirmDialog、CodegButton/TextField/SecretField/Checkbox/SectionHeader/ListRow |
| `git/ConflictListScreen.kt` | FilterChip、TextButton、AlertDialog、文件列表 | CodegTabs、CodegButton、CodegConfirmDialog、CodegListRow/SectionHeader |
| `git/GitActionsMenu.kt` | DropdownMenu/Item | CodegDropdownMenu/CodegMenuItem |
| `git/GitMiscSheet.kt` | ModalBottomSheet、AlertDialog、Button/TextButton、OutlinedTextField、HorizontalDivider、LinearProgressIndicator、手写行/标题 | CodegSheet/ConfirmDialog/Button/SecretField/Separator、LoadingView、CodegListRow/SectionHeader |
| `git/MergeOpsSheet.kt` | ModalBottomSheet、AlertDialog、Button/TextButton、OutlinedTextField、FilterChip、手写分支选择、LinearProgressIndicator | CodegSheet/Dialog/ConfirmDialog/Button/TextField/Tabs/RadioGroup/Select、LoadingView |
| `git/StashSheet.kt` | ModalBottomSheet、AlertDialog、TextButton、Switch、HorizontalDivider、LinearProgressIndicator、手写 stash 行 | CodegSheet/ConfirmDialog/Button/Switch/Separator、LoadingView、CodegListRow |
| `groups/GroupDialogs.kt` | AlertDialog、TextButton、OutlinedTextField、手写选项 | CodegDialog、CodegButton、CodegTextField、CodegListRow |
| `importsessions/ImportSessionsSheet.kt` | Dialog/AlertDialog、Button/TextButton、Checkbox、LinearProgressIndicator、手写分组/会话行/状态 | CodegFullScreenSheet/ConfirmDialog、CodegButton/Checkbox/Skeleton/SectionHeader/ListRow/Badge |

另新增本测试文档。无新增依赖、资源字符串、公开 API 或网络模型；业务 ViewModel、接口和纯逻辑文件未改。已有 PrimaryButton、CodegTopBar、CodegIconButton、EmptyState/LoadingView/InlineError 保留调用。受影响入口即第 4 节各页面。

明确不在范围内：设计系统内部实现、Material 图标迁移、接 Toast、服务端、导航和其它 feature、code-index；不改共享 strings.xml。

### grep 自查

```bash
rg -n '\b(DropdownMenu|DropdownMenuItem|AlertDialog|ModalBottomSheet|TextButton|Button|OutlinedButton|OutlinedTextField|Checkbox|Switch|FilterChip|SegmentedTabs|TabRow|HorizontalDivider|LinearProgressIndicator|ListItem|CodegSegmented)\s*\(' app/src/main/java/app/codeg/android/feature/projects
```

结果：0 命中（rg exit 1，符合无匹配预期），无目标 M3 原件例外；对应 M3 import 也无残留。`git diff --check` 通过。圆角统一使用 CodegShapes/PillShape；透明色使用 withOpacity。

允许保留：Text/Icon/Scaffold、PullToRefreshBox、CircularProgressIndicator 等不在本次替换映射中的基础控件。不定进度：Git 管理使用 LoadingView；Forge/导入使用 CodegSkeleton，因 CodegProgress 仅支持已知数值，未伪造进度。

## 2. 编译检查

未执行编译；下列是自评关注点，不代表编译失败。

- `CommitSheet.kt:115`：CodegSheet footer 的 Row/Column scope、PrimaryButton/CodegButton 参数；多行输入和 checkbox/行嵌套点击。
- `BranchPickerSheet.kt:70`：LazyColumn 外仍有 heightIn(max = 520.dp) 有限约束；共享 Sheet 自带滚动，检查小屏双滚动。
- `DirectoryBrowserSheet.kt:67`：共享全屏 Sheet 自带滚动，目录改 Column；顶部 actions 与长标题布局及大目录性能。
- `git/MergeOpsSheet.kt:13`：FlowRow 实验 API opt-in；CodegSelect/RadioGroup/Tabs 参数，hard 验证到最终确认状态切换。
- `git/BranchManageSheet.kt:18`：FlowRow opt-in、force 到最终确认状态；SecretField 与 CodegDialog footer scope。
- `forge/ForgeSheet.kt:17`：FlowRow opt-in；CodegFilterChip 显式 enabled 选择已有重载；无新增依赖。
- `forge/ForgeDetail.kt:72`：CodegTextField 只有 singleLine，无 enabled/maxLines；busy 使用回调 guard；moreFiles/moreComments 显式 onClick。
- `importsessions/ImportSessionsSheet.kt:19`：CodegListRow/Checkbox 嵌套及状态 badge 分支；大数据 Column 不再惰性组合，需真机性能验证。
- `ProjectListScreen.kt:77`：属性委托 getValue/setValue 与 collectAsStateWithLifecycle 导入已复核；删除分组 onConfirm 保留 busy guard。

- 统一 `./gradlew :app:check` 时关注 Compose 参数/作用域、实验 API、Kotlin operator imports、可见性、资源引用和既有 JVM 单测。本任务不执行该命令；不增加依赖或改公开签名。

## 3. 单元测试

| 测试类::方法 | 覆盖点 |
|---|---|
| 无新增/修改 JVM 单测 | 本次仅 UI 组件迁移，不新增解析、映射或状态推导函数；已有业务逻辑保持原实现。统一集成时运行现有 app JVM 单测，界面行为按下表真机验证。 |

## 4. 测试用例

前置条件：协调方合并 T43–T46 与本分支后编译安装；连接测试服 T13Test，另备官方原版 codeg 0.38/缺能力服务；准备可丢弃的 Git 测试仓库、本地/远程分支、tracked/untracked/renamed/deleted 文件、远端凭据、冲突、stash、worktree、Forge Issue/PR/评论/多标签、可导入各状态会话。所有危险操作仅针对测试数据。

每组分别独立执行明/暗主题：切换主题后重新进入页面，检查 background/foreground、muted、border、danger、选中/禁用态和长文本；不可仅在同一截图上推断另一主题通过。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T48-01L | 项目列表与分组 | 打开项目列表→更多菜单→创建分组→重命名→折叠/展开→长按项目编辑别名、颜色、移动分组；取消一次分组删除，再确认删除测试分组。 | 菜单/卡片/分区/行样式统一；所有颜色选项、未分组入口可达；取消不修改；删除框写明组名，busy 不执行删除。 | 明 |
| T48-01D | 项目列表与分组 | 打开项目列表→更多菜单→创建分组→重命名→折叠/展开→长按项目编辑别名、颜色、移动分组；取消一次分组删除，再确认删除测试分组。 | 菜单/卡片/分区/行样式统一；所有颜色选项、未分组入口可达；取消不修改；删除框写明组名，busy 不执行删除。 | 暗 |
| T48-02L | 目录浏览 | 项目列表→打开目录；点击 Home、子目录、上级；选择当前目录，再重新打开并关闭；使用长路径和 200 个目录。 | 目录路径和行准确；顶部选择/Home/关闭可操作；选择仍打开原目录；无无限高度崩溃，检查长标题及滚动性能。 | 明 |
| T48-02D | 目录浏览 | 项目列表→打开目录；点击 Home、子目录、上级；选择当前目录，再重新打开并关闭；使用长路径和 200 个目录。 | 目录路径和行准确；顶部选择/Home/关闭可操作；选择仍打开原目录；无无限高度崩溃，检查长标题及滚动性能。 | 暗 |
| T48-03L | 克隆 | 项目列表→克隆；输入 HTTPS URL、浏览父目录、填写可选凭据→克隆；另测错误 URL/认证失败→修正重试；克隆中拖动/返回。 | 目标路径推导不变；密码遮挡；固定按钮按 canClone 禁用；克隆中关闭回调受保护；成功刷新，失败可见并能重试。 | 明 |
| T48-03D | 克隆 | 项目列表→克隆；输入 HTTPS URL、浏览父目录、填写可选凭据→克隆；另测错误 URL/认证失败→修正重试；克隆中拖动/返回。 | 目标路径推导不变；密码遮挡；固定按钮按 canClone 禁用；克隆中关闭回调受保护；成功刷新，失败可见并能重试。 | 暗 |
| T48-04L | 项目详情与文件 | 打开项目→依次切换文件/变更/提交；文件页进入/返回目录，打开文本、图片、二进制；返回项目列表。 | 三项 tabs 选中态清晰且内容对应；文件导航/预览与不支持提示保持；主题下标题/路径/徽章可读。 | 明 |
| T48-04D | 项目详情与文件 | 打开项目→依次切换文件/变更/提交；文件页进入/返回目录，打开文本、图片、二进制；返回项目列表。 | 三项 tabs 选中态清晰且内容对应；文件导航/预览与不支持提示保持；主题下标题/路径/徽章可读。 | 暗 |
| T48-05L | 分支切换 | 项目详情→分支徽章/更多→切换分支；检查本地/远程与当前分支；切换另一测试分支，创建新测试分支；模拟失败。 | 当前分支有选中标记且点击不触发；busy 不重复执行；成功关闭并刷新；失败留提示；长列表可滚动。 | 明 |
| T48-05D | 分支切换 | 项目详情→分支徽章/更多→切换分支；检查本地/远程与当前分支；切换另一测试分支，创建新测试分支；模拟失败。 | 当前分支有选中标记且点击不触发；busy 不重复执行；成功关闭并刷新；失败留提示；长列表可滚动。 | 暗 |
| T48-06L | 变更操作 | 变更页→点击文件看 diff→返回→长按→暂存；对已修改文件选择丢弃，对未跟踪文件选择删除；每项先取消再确认。 | 文件行、重命名来源、字母 badge 正确；只有对应动作可见；危险框包含完整文件路径；取消无操作，确认后状态刷新。 | 明 |
| T48-06D | 变更操作 | 变更页→点击文件看 diff→返回→长按→暂存；对已修改文件选择丢弃，对未跟踪文件选择删除；每项先取消再确认。 | 文件行、重命名来源、字母 badge 正确；只有对应动作可见；危险框包含完整文件路径；取消无操作，确认后状态刷新。 | 暗 |
| T48-07L | 提交 | 变更页→提交；检查 tracked 默认选中/untracked 默认不选；单行与复选框切换、全选/清空；输入多行 message→提交；另测提交并推送和提交失败。 | 选择不重复切换；分区计数正确；空消息/无选择/busy 禁用提交；键盘下 footer 可达；只提交选择文件；提交后才开始推送和认证。 | 明 |
| T48-07D | 提交 | 变更页→提交；检查 tracked 默认选中/untracked 默认不选；单行与复选框切换、全选/清空；输入多行 message→提交；另测提交并推送和提交失败。 | 选择不重复切换；分区计数正确；空消息/无选择/busy 禁用提交；键盘下 footer 可达；只提交选择文件；提交后才开始推送和认证。 | 暗 |
| T48-08L | 提交历史与同步 | 提交页→fetch/pull/push；打开历史详情→复制 hash→点变更文件→返回；包含已删除文件和有/无 diff 的提交。 | 同步按钮 busy 禁用及计数保持；作者/时间/hash/增删可读；详情卡片/diff/预览正确，删除文件不打开不可用预览。 | 明 |
| T48-08D | 提交历史与同步 | 提交页→fetch/pull/push；打开历史详情→复制 hash→点变更文件→返回；包含已删除文件和有/无 diff 的提交。 | 同步按钮 busy 禁用及计数保持；作者/时间/hash/增删可读；详情卡片/diff/预览正确，删除文件不打开不可用预览。 | 暗 |
| T48-09L | Git 凭据 | 触发需认证的 fetch/push；普通主机输入用户名/密码并切换保存；GitHub 输入无效再有效 token，点击生成链接；取消后再触发。 | SecretField 默认遮挡；生成链接仍正确；无效 token 提示保留；验证中不重复提交；取消释放等待操作，新提示不复用旧值。 | 明 |
| T48-09D | Git 凭据 | 触发需认证的 fetch/push；普通主机输入用户名/密码并切换保存；GitHub 输入无效再有效 token，点击生成链接；取消后再触发。 | SecretField 默认遮挡；生成链接仍正确；无效 token 提示保留；验证中不重复提交；取消释放等待操作，新提示不复用旧值。 | 暗 |
| T48-10L | 分支管理 | Git 菜单→分支管理→刷新/更新/比较；删除本地测试分支（分别勾选和不勾 force），删除远程测试分支；测试认证重试。 | 本地/远程分区及 tracking 正确；本地先收集 force，再最终确认；最终框显示分支名；取消不删，确认传递原 force；unsupported 操作隐藏。 | 明 |
| T48-10D | 分支管理 | Git 菜单→分支管理→刷新/更新/比较；删除本地测试分支（分别勾选和不勾 force），删除远程测试分支；测试认证重试。 | 本地/远程分区及 tracking 正确；本地先收集 force，再最终确认；最终框显示分支名；取消不删，确认传递原 force；unsupported 操作隐藏。 | 暗 |
| T48-11L | Stash | Git 菜单→Stash；输入 message、切 keep-index→保存→刷新→显示 diff；分别 apply/pop/drop，对 clear 先取消再确认。 | switch/行/按钮统一；确认框显示 ref 或全部数量；取消不变，确认执行原动作并刷新；展开 diff 不截断。 | 明 |
| T48-11D | Stash | Git 菜单→Stash；输入 message、切 keep-index→保存→刷新→显示 diff；分别 apply/pop/drop，对 clear 先取消再确认。 | switch/行/按钮统一；确认框显示 ref 或全部数量；取消不变，确认执行原动作并刷新；展开 diff 不截断。 | 暗 |
| T48-12L | Merge/Rebase/Reset | Git 菜单→操作；分别选 merge/rebase 目标，reset 输入提交目标并切 soft/mixed/hard；hard 输入错误/正确当前分支名；取消及确认。 | 切操作清空目标；分支列表排除当前分支；hard 错误名不能继续，正确后进入最终危险确认，写明当前分支→目标提交和模式。 | 明 |
| T48-12D | Merge/Rebase/Reset | Git 菜单→操作；分别选 merge/rebase 目标，reset 输入提交目标并切 soft/mixed/hard；hard 输入错误/正确当前分支名；取消及确认。 | 切操作清空目标；分支列表排除当前分支；hard 错误名不能继续，正确后进入最终危险确认，写明当前分支→目标提交和模式。 | 暗 |
| T48-13L | 冲突 | 准备 merge/rebase 冲突→打开操作页→选择文件与 base/ours/theirs；选择 ours/theirs 后先取消再确认；解决后 continue；另建冲突测试 abort。 | 分段与文件选中正确；确认框明确文件或分支；未解决时 continue 禁用；取消不修改；确认执行原解决/继续/中止动作。 | 明 |
| T48-13D | 冲突 | 准备 merge/rebase 冲突→打开操作页→选择文件与 base/ours/theirs；选择 ours/theirs 后先取消再确认；解决后 continue；另建冲突测试 abort。 | 分段与文件选中正确；确认框明确文件或分支；未解决时 continue 禁用；取消不修改；确认执行原解决/继续/中止动作。 | 暗 |
| T48-14L | Remote/Worktree | Git 菜单→远程/worktree；新增 remote、编辑 URL、fetch/认证；删除先取消再确认；新增 worktree（base 可空）、删除测试 worktree。 | 表单/标题/行正确；名称与 URL 校验保持；删除 remote 显示名称，worktree 显示分支和路径；成功回调刷新；404/unknown 功能隐藏。 | 明 |
| T48-14D | Remote/Worktree | Git 菜单→远程/worktree；新增 remote、编辑 URL、fetch/认证；删除先取消再确认；新增 worktree（base 可空）、删除测试 worktree。 | 表单/标题/行正确；名称与 URL 校验保持；删除 remote 显示名称，worktree 显示分支和路径；成功回调刷新；404/unknown 功能隐藏。 | 暗 |
| T48-15L | Forge 列表与新建 | Git 菜单→Forge；切 Issues/PRs、open/closed、分页/刷新；新建 Issue 输入标题/多行正文/多标签→创建；另测失败。 | tabs 与筛选区分清楚；窄屏按钮换行；空状态、计数、分页保持；busy 输入数据不变且不能重复创建；成功/失败显示原结果。 | 明 |
| T48-15D | Forge 列表与新建 | Git 菜单→Forge；切 Issues/PRs、open/closed、分页/刷新；新建 Issue 输入标题/多行正文/多标签→创建；另测失败。 | tabs 与筛选区分清楚；窄屏按钮换行；空状态、计数、分页保持；busy 输入数据不变且不能重复创建；成功/失败显示原结果。 | 暗 |
| T48-16L | Forge 详情 | 打开 Issue/PR→看正文/labels/状态/文件 patch/评论；更多文件/评论、发送评论、生成任务；关闭/重开与各合并 method 先取消再确认。 | 所有内容与操作保留；目标 #number title 和 method 正确；取消无操作；确认执行原 API；不可合并/草稿/busy 约束保持；长内容可滚动。 | 明 |
| T48-16D | Forge 详情 | 打开 Issue/PR→看正文/labels/状态/文件 patch/评论；更多文件/评论、发送评论、生成任务；关闭/重开与各合并 method 先取消再确认。 | 所有内容与操作保留；目标 #number title 和 method 正确；取消无操作；确认执行原 API；不可合并/草稿/busy 约束保持；长内容可滚动。 | 暗 |
| T48-17L | 导入会话 | 项目列表→导入→扫描；按文件夹/agent 分组选择，单行/checkbox/全选；分别导入所选、全量导入（先取消再确认）；扫描含 new/imported/deleted/unavailable 与错误数据。 | 会话状态 badge 正确，非 new 不可选；busy 不能变选择或关闭；全量确认显示数量；结果分类计数/错误展示保持；导入回调刷新。 | 明 |
| T48-17D | 导入会话 | 项目列表→导入→扫描；按文件夹/agent 分组选择，单行/checkbox/全选；分别导入所选、全量导入（先取消再确认）；扫描含 new/imported/deleted/unavailable 与错误数据。 | 会话状态 badge 正确，非 new 不可选；busy 不能变选择或关闭；全量确认显示数量；结果分类计数/错误展示保持；导入回调刷新。 | 暗 |
| T48-18L | 兼容与异常 | 以官方 codeg 0.38 和缺能力服务器分别打开以上入口；模拟网络失败/空仓库/无变更；每个弹层弹出键盘、横向窄屏、返回与遮罩关闭。 | 不支持接口保持原隐藏路径；空/加载/错误可见且能重试；按钮触达/焦点/关闭行为正确，无嵌套滚动崩溃；不误执行危险操作。 | 明 |
| T48-18D | 兼容与异常 | 以官方 codeg 0.38 和缺能力服务器分别打开以上入口；模拟网络失败/空仓库/无变更；每个弹层弹出键盘、横向窄屏、返回与遮罩关闭。 | 不支持接口保持原隐藏路径；空/加载/错误可见且能重试；按钮触达/焦点/关闭行为正确，无嵌套滚动崩溃；不误执行危险操作。 | 暗 |

对照网页端（均在 `WEB/components/`，每组 L/D 使用相同参考）：

- T48-01L/D：`layout/folder-title-bar.tsx`。
- T48-02L/D：`layout/aux-panel-file-tree-tab.tsx`。
- T48-03L/D：`layout/folder-title-bar.tsx`。
- T48-04L/D：`layout/aux-panel-file-tree-tab.tsx`。
- T48-05L/D：`layout/branch-dropdown.tsx`。
- T48-06L/D：`layout/aux-panel-git-changes-tab.tsx`。
- T48-07L/D：`layout/commit-dialog.tsx`。
- T48-08L/D：`layout/aux-panel-git-log-tab.tsx`。
- T48-09L/D：`layout/branch-dropdown.tsx`。
- T48-10L/D：`layout/branch-dropdown.tsx`。
- T48-11L/D：`layout/stash-dialog.tsx`。
- T48-12L/D：`layout/commit-dialog.tsx`。
- T48-13L/D：`layout/conflict-dialog.tsx`。
- T48-14L/D：`layout/remote-manage-dialog.tsx`。
- T48-15L/D：`forge/`。
- T48-16L/D：`forge/`。
- T48-17L/D：`import-sessions/`。
- T48-18L/D：`layout/aux-panel-*.tsx`。

允许差异：手机使用 Sheet/全屏 Sheet 代替桌面侧栏/对话框，保留触摸目标和纵向布局；沿用现有 Material 图标；目录选择置顶保持可达；强制删除和 hard reset 的附加表单先用 CodegDialog，再进入 CodegConfirmDialog；组件 token/选中态/分区语义按共享设计系统。

## 5. 回归点

- 项目：刷新/重试、创建/重命名/删除分组、折叠状态、项目别名/颜色/归组/打开、克隆与目录路径选择（T48-01～04）。
- 文件与历史：目录上级/返回、文本/图片/不支持预览、diff/复制提交 hash/已删除文件屏蔽（T48-04、06、08）。
- Git：切换/创建/更新/比较/删除分支；tracked 默认选择、提交文件集合、提交成功后才 push；fetch/pull/push/busy/认证取消与重试（T48-05～10）。
- Stash：keep-index、message、apply/pop/drop/clear 与确认取消；合并/rebase/reset 模式、hard 分支名校验、冲突版本/resolve/continue/abort；remote URL、worktree 新增/删除与认证（T48-11～14）。
- Forge：过滤/分页/新建/labels/详情/patch/评论/任务/关闭/重开/合并方法及可用条件（T48-15～16）。
- 导入：扫描、分组/单项/全选、仅 new 可选、所选/全量导入、结果错误和回调（T48-17）。
- 全域：官方 0.38/未知能力仍按既有路径隐藏入口，危险操作取消无副作用，主题/键盘/长文本/小屏与大数据滚动（T48-18）。

## 6. 已知风险 / 未完成

- 未编译、未执行 JVM 单测、未真机验证；由协调方统一执行以上用例。本任务未触发 CI 或生成 code-index。
- CodegTextField/CodegListRow 缺 enabled 视觉 API：原 busy 数据保护通过回调 guard 保持；行可能仍暴露可点击无障碍动作，需后续共享组件演进，不在本任务改设计系统。
- 全屏 Sheet 自带滚动，目录/Forge/导入内部改普通 Column 避免无限高度嵌套；大量记录的首次组合与滚动、长分支/hash 挤压以及键盘下 footer 必须真机评估。
- 无需新增字符串；原资源含义保持。暂存的 Toast/折叠/图片等仅留标记，未接第三阶段功能。

### 留给 T52/T53 的 TODO 清单

- `BranchPickerSheet.kt:72`：TODO(T52): CodegToaster for checkout/create failures.
- `CloneRepoSheet.kt:95`：TODO(T52): CodegToaster for operation errors.
- `CommitSheet.kt:178`：TODO(T52): CodegToaster for commit failures.
- `FolderFilesView.kt:145`：TODO(T53): CodegImagePreviewDialog for image content.
- `GitCredentialSheet.kt:159`：TODO(T52): CodegToaster for operation errors.
- `GitStatusStrip.kt:57`：TODO(T52): CodegToaster for transient Git operation feedback.
- `ProjectListScreen.kt:207`：TODO(T52): CodegToaster for refresh errors.
- `ProjectListScreen.kt:252`：TODO(T53): CodegCollapsible for project groups.
- `ProjectListScreen.kt:362`：TODO(T53): CodegFolderBadge.
- `forge/ForgeSheet.kt:25`：TODO(T52): CodegToaster for operation feedback.
- `git/BranchManageSheet.kt:28`：TODO(T52): CodegToaster for operation feedback.
- `git/BranchManageSheet.kt:52`：TODO(T53): CodegCollapsible for branch comparison.
- `git/ConflictListScreen.kt:27`：TODO(T53): CodegCommandList for conflict file selection.
- `git/GitMiscSheet.kt:39`：TODO(T52): CodegToaster for operation feedback.
- `git/MergeOpsSheet.kt:27`：TODO(T52): CodegToaster for operation feedback.
- `git/StashSheet.kt:33`：TODO(T52): CodegToaster for operation feedback.
- `git/StashSheet.kt:46`：TODO(T53): CodegCollapsible for stash diff.
- `importsessions/ImportSessionsSheet.kt:24`：TODO(T52): CodegToaster for import result feedback.
