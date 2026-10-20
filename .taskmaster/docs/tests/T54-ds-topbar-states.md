# T54 测试文档：顶栏与空/加载/错误状态对齐

- 分支：`task/T54-ds-topbar-states`　提交：本文件与组件代码同一提交；合并时用 `git log -1 task/T54-ds-topbar-states` 取得提交 hash。
- 依赖：无；T51 图标迁移后续处理。测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 执行状态：仅完成静态核对，以下 Preview、编译及真机用例均待协调方执行。

## 1. 测试范围
- 改动文件：`A/core/designsystem/component/TopBar.kt`、`StateViews.kt`、`R/values/strings_ds_states.xml`、`R/values-zh-rCN/strings_ds_states.xml`、本测试文档。
- 新增/变更公开 API：完整新旧签名见下；新增 `@Composable internal fun T54Gallery()` 及私有明暗 Preview。
- 受影响页面/入口：共享组件自动影响下方按 feature 分组的全部 39 个调用文件；不改调用点。
- 明确不在范围内：业务逻辑、网络接口、导航、依赖、T51 图标实现、code-index、任务状态。

### 签名兼容性
以下均为 `@Composable`；参数名称、顺序、类型全部保留，仅 InlineError 末尾追加默认参数。

```kotlin
// 旧；新签名相同，仅 showDivider 默认 false → true，默认显示网页分隔线。
fun CodegTopBar(title: String, modifier: Modifier = Modifier,
    subtitle: String? = null, navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    showDivider: Boolean = false, titleContent: (@Composable () -> Unit)? = null)
// 新：以上 showDivider: Boolean = true；其余完全一致。

// 旧 = 新：保留末尾 content，现有 trailing lambda 继续成立。
fun CodegIconButton(onClick: () -> Unit, modifier: Modifier = Modifier,
    enabled: Boolean = true, visualSize: Dp = 36.dp, content: @Composable () -> Unit)
fun EmptyState(icon: ImageVector, title: String, message: String,
    modifier: Modifier = Modifier, actionLabel: String? = null, onAction: (() -> Unit)? = null)
fun LoadingView(message: String, modifier: Modifier = Modifier)

// 旧
fun InlineError(icon: ImageVector, title: String, message: String,
    onRetry: () -> Unit, modifier: Modifier = Modifier, retryLabel: String = "Retry")
// 新
fun InlineError(icon: ImageVector, title: String, message: String,
    onRetry: () -> Unit, modifier: Modifier = Modifier,
    retryLabel: String = stringResource(R.string.ds_states_retry), showRetry: Boolean = true)
```

`onRetry` 类型及必填规则不变；隐藏重试用 `showRetry = false`。原有显式 retryLabel 不被覆盖；原有位置参数和命名参数兼容。源码兼容不代表二进制兼容，集成时重新编译所有调用方。

## 2. 编译检查
- `TopBar.kt:103` 起：新增 foundation `clickable`、`Role.Button`、`alpha`、`border`，沿用已有 InteractionSource 的 focused/hovered/pressed 收集；无新依赖。
- `TopBar.kt:45`：沿用已有 `ExperimentalMaterial3Api` opt-in、`TopAppBarScrollBehavior` 与 `expandedHeight`，未引入新实验 API。
- `StateViews.kt:85`：RowScope 内的 `Modifier.weight(1f, fill = false)`，检查小屏长加载文案布局。
- `StateViews.kt:97`：Composable 默认参数调用 `stringResource`，调用方本身均为 Composable；检查 R 资源生成及两种语言。
- `StateViews.kt:124`：internal Gallery；Preview 使用现有 `CodegTheme(themeMode = ThemeMode.LIGHT/DARK)`，没有跨模块内部符号访问；Gallery 显式沿用 ExperimentalMaterial3Api opt-in。
- 预期统一 `./gradlew :app:check`：关注 Compose 编译、资源合并、Preview tooling、旧调用的命名参数/位置参数/trailing lambda；lint 检查无硬编码界面字符串、图标描述与触摸目标。
- 已执行：`git diff --check`；XML 解析及中英文 key 一致性；静态检查调用文件清单与范围。未执行 Gradle、CI、ADB、索引生成。

## 3. 单元测试
| 测试类::方法 | 覆盖点 |
|---|---|
| 无新增 JVM 单测 | 仅 Compose 样式和资源，没有新增纯逻辑；不以无意义的常量断言替代 UI 验证。交互与主题由下列用例覆盖；已有 JVM 单测由协调方统一运行。 |

## 4. 测试用例
前置条件：Gallery/Preview 无需服务器；页面回归使用官方 codeg 0.38 测试实例，准备有数据及空数据账号、可访问项目与 Git 仓库。加载态可通过限速网络观察；错误态通过临时断网并刷新触发，恢复网络后重试。不得改服务端制造错误。每条标记“明/暗”的用例须分别在强制明色、强制暗色执行并记录结果。页面不存在某状态时只验证该页面实际调用的组件。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T54-01 | T54Gallery / 两个 Preview | 分别渲染 Light/Dark；滚动查看全部变体 | 背景为 background；顶栏 48dp 内容行（系统栏 inset 另计）、标题 14sp medium；空态标题14sp、描述12sp；无截图裁切 | 明/暗 |
| T54-02 | Gallery 顶栏按钮 | 点击、长按后移出；连接键盘用 Tab 聚焦、Enter/空格激活；指针悬停 | 视觉32/36dp、8dp圆角、16dp图标，48dp触摸区；按下/悬停 accent，焦点 ring 3dp且50%透明；释放复原；按钮语义和描述可读 | 明/暗 |
| T54-03 | Gallery 禁用按钮 | 尝试触摸及键盘聚焦/激活；对照启用按钮 | 整个内容50%透明（含显式 tint 图标）；不能触发回调，无启用态反馈 | 明/暗 |
| T54-04 | 顶栏参数 | 在 Preview 临时传长标题、长副标题、titleContent；分别 showDivider true/false，并传已有滚动行为滚动 | 标题/副标题单行省略；自定义标题可交互；默认分隔线可见；false无滚动时隐藏，滚动重叠时显示；背景无M3抬升变色 | 明/暗 |
| T54-05 | Gallery 空态 | 对照有/无操作按钮；临时只传 actionLabel 或只传 onAction；小屏和200%字号 | 图标容器 muted；只有 label+callback 同时提供才出现按钮；标题/描述换行居中不溢出，按钮回调一次 | 明/暗 |
| T54-06 | Gallery 加载 | 观察动画；临时传长加载文案 | 16dp原生持续旋转指示器+mutedForeground文案；长文案换行且不挤出spinner | 明/暗 |
| T54-07 | Gallery 错误 | 对照 showRetry true/false；接入计数回调点击重试；传长异常信息 | destructive文字和边框；重试出现/隐藏正确；点击一次回调一次；长文案不越界 | 明/暗 |
| T54-08 | 中英文资源 | 切换中文/英文，省略 retryLabel，再传自定义 label | 默认“重试”/“Retry”随语言切换；自定义 label 保留；Gallery 文字无缺失资源 | 明/暗 |
| T54-20 | `activity/ActivityScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-21 | `automations/AutomationScreen.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-22 | `projects/BranchPickerSheet.kt`（CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口 | 标题、背景、分隔线正确，返回/关闭保持原行为；无重叠/裁切 | 明/暗 |
| T54-23 | `projects/CloneRepoSheet.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-24 | `projects/CommitSheet.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-25 | `projects/DirectoryBrowserSheet.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-26 | `projects/FolderChangesView.kt`（EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-27 | `projects/FolderCommitsView.kt`（EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-28 | `projects/FolderFilesView.kt`（EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-29 | `projects/GitCredentialSheet.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-30 | `projects/GitStatusStrip.kt`（CodegIconButton） | 进入该页面或所属页面的对应弹层/菜单；点击可用图标；忙碌或边界条件下尝试禁用图标 | 按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-31 | `projects/ProjectDetailScreen.kt`（CodegIconButton, CodegTopBar, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-32 | `projects/ProjectListScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-33 | `projects/git/BranchManageSheet.kt`（CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口 | 标题、背景、分隔线正确，返回/关闭保持原行为；无重叠/裁切 | 明/暗 |
| T54-34 | `projects/git/GitActionsMenu.kt`（CodegIconButton） | 进入该页面或所属页面的对应弹层/菜单；点击可用图标；忙碌或边界条件下尝试禁用图标 | 按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-35 | `projects/git/GitMiscSheet.kt`（CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口 | 标题、背景、分隔线正确，返回/关闭保持原行为；无重叠/裁切 | 明/暗 |
| T54-36 | `projects/git/StashSheet.kt`（CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口 | 标题、背景、分隔线正确，返回/关闭保持原行为；无重叠/裁切 | 明/暗 |
| T54-37 | `search/SearchScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-38 | `server/ServerEditorScreen.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-39 | `server/ServerListScreen.kt`（CodegIconButton, CodegTopBar, EmptyState） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；使用空数据或无匹配筛选 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；空态图标、标题、描述及已有操作正确；无重叠/裁切 | 明/暗 |
| T54-40 | `serverlogs/ServerLogsScreen.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-41 | `sessiondetail/SessionActions.kt`（CodegIconButton） | 进入该页面或所属页面的对应弹层/菜单；点击可用图标；忙碌或边界条件下尝试禁用图标 | 按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-42 | `sessiondetail/SessionDetailScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-43 | `sessions/SessionListScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-44 | `settings/AgentDetailScreen.kt`（LoadingView） | 进入该页面或所属页面的对应弹层/菜单；限速后重新加载 | 加载指示与文字为 mutedForeground；无重叠/裁切 | 明/暗 |
| T54-45 | `settings/AgentsScreen.kt`（EmptyState, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；限速后重新加载；使用空数据或无匹配筛选 | 加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；无重叠/裁切 | 明/暗 |
| T54-46 | `settings/AssistantsScreen.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-47 | `settings/ChatChannelsScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-48 | `settings/ChatGlobalSettingsScreen.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-49 | `settings/ExpertsScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-50 | `settings/McpScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；无重叠/裁切 | 明/暗 |
| T54-51 | `settings/ModelProvidersScreen.kt`（EmptyState, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；限速后重新加载；使用空数据或无匹配筛选 | 加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；无重叠/裁切 | 明/暗 |
| T54-52 | `settings/QuickMessagesScreen.kt`（EmptyState, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；限速后重新加载；使用空数据或无匹配筛选 | 加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；无重叠/裁切 | 明/暗 |
| T54-53 | `settings/SettingsScreen.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-54 | `settings/SkillsScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-55 | `settings/customskills/CustomSkillsScreen.kt`（CodegIconButton, CodegTopBar） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |
| T54-56 | `settings/mcpmarket/McpMarketSheet.kt`（CodegIconButton, CodegTopBar, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；无重叠/裁切 | 明/暗 |
| T54-57 | `worktasks/WorkTaskScreen.kt`（CodegIconButton, CodegTopBar, EmptyState, InlineError, LoadingView） | 进入该页面或所属页面的对应弹层/菜单；检查标题、返回/关闭及操作入口；点击可用图标；忙碌或边界条件下尝试禁用图标；限速后重新加载；使用空数据或无匹配筛选；断网刷新，恢复后点重试 | 标题、背景、分隔线正确，返回/关闭保持原行为；按下 accent，动作正常，禁用不响应；加载指示与文字为 mutedForeground；空态图标、标题、描述及已有操作正确；错误 destructive 边框/文字，重试恢复内容；无重叠/裁切 | 明/暗 |
| T54-58 | `worktasks/WorkTaskTemplates.kt`（CodegIconButton） | 进入该页面或所属页面的对应弹层/菜单；点击可用图标；忙碌或边界条件下尝试禁用图标 | 按下 accent，动作正常，禁用不响应；无重叠/裁切 | 明/暗 |

对照网页端：T54-01/02/03/04 对照 `components/layout/app-title-bar.tsx`、`folder-title-bar.tsx` 和 `components/ui/button.tsx`；T54-05 对照 `components/tasks/tasks-page.tsx:676`、`components/conversations/sidebar-conversation-list.tsx` 的 chats-empty/folders-empty 提示（会话及项目/文件夹列表）、`components/import-sessions/import-sessions-window.tsx:670`；T54-06 对照 `components/project-boot/shadcn/shadcn-preview.tsx:47` 的 Loader2；T54-07 对照 destructive token/button；T54-08 对照本地化资源。页面回归各行按所列组件映射以上参考。

允许差异：Android 保留48dp触摸目标/顶栏内容高度与系统栏 inset，网页标题栏当前为32/40/44px；任务明确指定 background、accent、rounded-md，优先于网页当前 muted/70 和 rounded-4xl；原生旋转指示器替代 Loader2，Material 图标待T51迁移；不搬运桌面窗口控件。空态统一使用图标容器，而网页侧栏小提示可仅有文字。

## 5. 回归点
以下按 feature 分组列出本次 grep 命中的所有调用文件（各文件逐页用例见上表）。组件片段也单独列入，以覆盖宿主页中的菜单、模板、状态条。
- **activity**：`activity/ActivityScreen.kt`。
- **automations**：`automations/AutomationScreen.kt`。
- **projects**：`projects/BranchPickerSheet.kt`、`projects/CloneRepoSheet.kt`、`projects/CommitSheet.kt`、`projects/DirectoryBrowserSheet.kt`、`projects/FolderChangesView.kt`、`projects/FolderCommitsView.kt`、`projects/FolderFilesView.kt`、`projects/GitCredentialSheet.kt`、`projects/GitStatusStrip.kt`、`projects/ProjectDetailScreen.kt`、`projects/ProjectListScreen.kt`、`projects/git/BranchManageSheet.kt`、`projects/git/GitActionsMenu.kt`、`projects/git/GitMiscSheet.kt`、`projects/git/StashSheet.kt`。
- **search**：`search/SearchScreen.kt`。
- **server**：`server/ServerEditorScreen.kt`、`server/ServerListScreen.kt`。
- **serverlogs**：`serverlogs/ServerLogsScreen.kt`。
- **sessiondetail**：`sessiondetail/SessionActions.kt`、`sessiondetail/SessionDetailScreen.kt`。
- **sessions**：`sessions/SessionListScreen.kt`。
- **settings**：`settings/AgentDetailScreen.kt`、`settings/AgentsScreen.kt`、`settings/AssistantsScreen.kt`、`settings/ChatChannelsScreen.kt`、`settings/ChatGlobalSettingsScreen.kt`、`settings/ExpertsScreen.kt`、`settings/McpScreen.kt`、`settings/ModelProvidersScreen.kt`、`settings/QuickMessagesScreen.kt`、`settings/SettingsScreen.kt`、`settings/SkillsScreen.kt`、`settings/customskills/CustomSkillsScreen.kt`、`settings/mcpmarket/McpMarketSheet.kt`。
- **worktasks**：`worktasks/WorkTaskScreen.kt`、`worktasks/WorkTaskTemplates.kt`。

- 导航和自定义标题：服务器切换器、返回、关闭、菜单开关仍各触发一次，不被透明触摸区遮挡。
- 顶栏默认分隔线：检查原页面已有分隔线是否视觉叠加；有显式 false 的调用仍按原滚动规则处理。
- TalkBack：按钮语义、调用方图标描述、禁用状态；装饰状态图标不重复朗读标题。
- 小屏、横屏、200%字号：长标题省略、错误长文案换行、按钮可达、Gallery 可滚动。
- 切换主题及语言后重新进入各页面，确认内容、滚动与重试回调不丢失。

## 6. 已知风险 / 未完成
- 未编译、未运行单测或真机；以上用例为待执行计划，不是通过记录。由协调方统一编译并执行。
- T51 仍并行：保留 Material 图标和 TODO(T51) Lucide 名称；不改调用方图标。
- 共享组件覆盖范围广，默认开启分隔线、加载布局由纵排改横排，需重点检查弹层中已有分隔线、窄栏与大字号布局。
- 不新增 Gradle 依赖；不运行 CI、gh run watch、本地 Gradle、gen_code_index.py，不提交 code-index。
