# T47 测试文档：会话列表与会话详情界面对齐

- 分支：`task/T47-ui-sessions`　提交：与本文件同批的 `Align session screens with shared design components` 提交（哈希见分支日志及任务最终报告，避免文档自引用哈希）。
- 依赖：T43–T46 已合入 sit。测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 执行状态：仅完成静态检查；以下运行时用例均待协调方执行。未运行 Gradle、CI、adb、索引生成脚本。

## 1. 测试范围

以下路径相对 `app/src/main/java/app/codeg/android/feature/`，也是完整代码改动清单：

| 文件 | 替换前 | 替换后 |
|---|---|---|
| sessions/SessionListScreen.kt | DropdownMenu / DropdownMenuItem / TextButton / HorizontalDivider | CodegDropdownMenu / CodegMenuItem / CodegButton(Ghost) / CodegMenuSeparator |
| sessions/SessionRow.kt | 手写会话行、长按菜单 | CodegListRow（含更多按钮、长按）、CodegDropdownMenu / CodegMenuItem；项目名移至副标题 |
| sessions/SectionHeader.kt | 手写折叠标题、计数 | CodegSectionHeader，保留分区图标与折叠回调 |
| sessions/tags/ConversationTagUi.kt | FilterChip / Checkbox / Button / TextButton / ModalBottomSheet / AlertDialog / OutlinedTextField / 手写管理行 | CodegFilterChip / CodegCheckbox / CodegButton / CodegSheet（固定保存 footer）/ CodegDialog / CodegConfirmDialog / CodegTextField / CodegListRow |
| sessiondetail/SessionActions.kt | M3 菜单、对话框、底部面板、单选、文本框、按钮、分隔线 | CodegDropdownMenu / CodegMenuItem / CodegMenuSeparator / CodegDialog / CodegConfirmDialog / CodegSheet / CodegRadioGroup / CodegTextField / CodegButton / CodegSeparator |
| sessiondetail/AgentOptionsSheet.kt | ModalBottomSheet、文件夹菜单选择器、模式/配置/分支行、分组标题、TextButton、HorizontalDivider、手写面板 | CodegFullScreenSheet / CodegSelect / CodegListRow / CodegSectionHeader / CodegButton / CodegSeparator / CodegCard |
| sessiondetail/ComposeInsertSheet.kt | ModalBottomSheet / CodegSegmented / 手写插入行 | CodegSheet / CodegTabs / CodegListRow |
| sessiondetail/SessionDetailScreen.kt | 提示条 TextButton | CodegButton(Ghost)，增加 T52 注释 |
| sessiondetail/interactive/AskQuestionCard.kt | 手写卡片、问题类型标签、选项行、Checkbox、RadioButton、BasicTextField、Button / TextButton | CodegCard / CodegBadge / 带可换行标签的 CodegCheckbox / CodegRadioGroup / CodegTextField / CodegButton |
| sessiondetail/interactive/PermissionRequestCard.kt | 手写卡片、类型标签、Button / OutlinedButton | CodegCard / CodegBadge / CodegButton(Default / Outline) |
| sessiondetail/interactive/PlanApprovalCard.kt | 手写卡片、BasicTextField、Button / OutlinedButton / TextButton | CodegCard / CodegTextField / CodegButton(Default / Outline / Ghost) |
| sessiondetail/rendering/ToolValueFormat.kt | HorizontalDivider | CodegSeparator |
| sessiondetail/timeline/TimelineNodeBody.kt | HorizontalDivider | CodegSeparator |
| sessiondetail/ComposeBar.kt | 自定义输入组 | 仅增加 T53 注释 |
| sessiondetail/rendering/ToolCallCard.kt | 自定义工具折叠卡片 | 仅增加 T53 注释 |
| sessiondetail/rendering/ReasoningBlock.kt | 自定义思考折叠 | 仅增加 T53 注释 |
| sessiondetail/rendering/InlineImage.kt | 内联图片 | 仅增加 T53 注释 |

另新增本测试文档。无新增依赖、资源或公开 API；现有页面签名、ViewModel、接口及答案映射保持原样。私有 InsertRow 删除失去用途的 mono 参数。

受影响入口：会话列表、列表服务器菜单、会话行更多菜单、标签管理/分配、详情顶部菜单、代理选项/分支页、输入栏插入面板、问题/权限/计划审批卡片、工具输出和时间线分隔线。

范围外：设计系统内部实现、图标替换、Toast 接入、服务端、导航与网络业务。用户自定义颜色标签继续使用 ConversationTagChip；只读元数据行保留双列排版；实时工具/思考折叠等待 T53；浮动新会话按钮、图标按钮、圆形加载指示器保留原样。

### grep 自查

执行：

```bash
rg -n '\b(DropdownMenu|DropdownMenuItem|AlertDialog|ModalBottomSheet|TextButton|Button|OutlinedButton|OutlinedTextField|Checkbox|Switch|FilterChip|HorizontalDivider|SegmentedTabs|CodegSegmented|TabRow|LinearProgressIndicator)\s*\(' app/src/main/java/app/codeg/android/feature/sessions app/src/main/java/app/codeg/android/feature/sessiondetail
rg -n '^import androidx\.compose\.material3\.(DropdownMenu|DropdownMenuItem|AlertDialog|ModalBottomSheet|TextButton|Button|OutlinedButton|OutlinedTextField|Checkbox|Switch|FilterChip|HorizontalDivider|TabRow|LinearProgressIndicator)$' app/src/main/java/app/codeg/android/feature/sessions app/src/main/java/app/codeg/android/feature/sessiondetail
git diff --check
```

结果：目标调用及显式 import 均无匹配；diff 无空白错误。额外用临时脚本检查修改 Kotlin 文件的括号配对和状态委托 import，通过；这不是 Kotlin 编译或 lint。`ConversationTagUi.kt` 原有 material3 通配 import 不代表仍调用目标 M3 原件。纯词搜索会匹配 ComposeBar 的网页 Button 注释、ViewModel 的 Switch 英文注释，不属于组件调用。

## 2. 编译检查

自评风险：中等（未编译，改动跨多个 Compose 页面，无新增依赖）。重点位置：

- `sessiondetail/AgentOptionsSheet.kt:126`：CodegFullScreenSheet 的 ColumnScope 与 BackHandler；分支页 LazyColumn 仍有 600dp 上限，主内容改由共享面板滚动，避免双重无界滚动。需确认 Dialog 返回与关闭按钮均先回到主选项页。
- `sessiondetail/AgentOptionsSheet.kt:289`：CodegSelect 用文件夹 path 映射回原 FolderDetail，要求项目路径唯一；检查同名不同路径项目能选对。
- `sessiondetail/ComposeInsertSheet.kt:79`、`:88`：共享 Sheet / Tabs 命名参数及 LazyColumn 有界测量；已按现有组件签名核对。
- `sessions/tags/ConversationTagUi.kt:53`：项目存在两种 CodegFilterChip 重载，采用 Boolean 首参的位置参数明确选择 T46 版本。
- `sessions/tags/ConversationTagUi.kt` 的 TagForm 和删除确认：CodegTextField / CodegConfirmDialog 没有 enabled 参数，使用回调忙碌锁阻止编辑、重复请求和关闭；控件视觉仍可呈启用态，需实测忙碌期间点击。
- `sessiondetail/SessionActions.kt:137`：CodegConfirmDialog 的 destructive 和目标名参数；空标题用现有未命名资源兜底。
- `sessiondetail/interactive/AskQuestionCard.kt`：共享 Checkbox 的非空 onCheckedChange 和说明文字点击，需确认一次触摸只切换一次；长选项使用可换行标签，避免共享 ListRow 的单行省略；状态委托 getValue/setValue import 已保留。
- `sessiondetail/interactive/PlanApprovalCard.kt`：多行 CodegTextField 没有 enabled 参数，提交后通过 onValueChange 锁住反馈；未改变提交 decision 与 trim 逻辑。
- 现有 ExperimentalMaterial3Api 注解有部分继续保留；没有新增实验性 API 或可见性依赖。

协调方统一执行 `./gradlew :app:check` 时关注 Kotlin 编译（Compose 接收者、命名参数、重载、委托 import）、资源/lint、原有会话相关 JVM 单测。不要将本文静态核对当作构建通过。

## 3. 单元测试

本次没有新增纯逻辑，无新增/修改 JVM 单测，也未运行单测。保留 ComposeInsert 草稿转换、问题 buildAnswer、状态映射和分支切换算法。

| 测试类::方法 | 覆盖点 |
|---|---|
| 无新增 | 组件迁移使用下述真机用例；协调方统一运行现有测试，不虚报未读取或未执行的测试方法 |

## 4. 测试用例

前置条件：连接官方原版 codeg 0.38；可使用 T13Test 测试服补充权限、问题和计划审批数据。准备两个服务器、多个项目（同名不同路径）、置顶/普通/进行中会话、超长标题、0/1/3 个标签、全局与项目标签、空与长工具输出、local/remote/worktree 占用分支。删除仅使用可丢弃数据。每行独立执行，未执行。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T47-01 | 会话列表 | 切明色；滚动置顶/项目/未分组区域；折叠再展开；点会话 | 分区标题计数正确，保留折叠状态；选中行高亮，项目副标题、标签、时间或 live 状态可辨；打开原会话 | 明 |
| T47-02 | 会话列表 | 切暗色；重复 01，包含长标题和 3 标签会话 | 同 01；深色背景、文本和边框有对比，长文本不挤出更多按钮 | 暗 |
| T47-03 | 列表菜单 | 点服务器标题，切另一服务器；再点管理；长按会话和点更多分别执行置顶/取消、分配标签 | 菜单定位在锚点附近，点击后关闭；服务器与会话目标正确，两种打开方式一致 | 明 |
| T47-04 | 列表菜单 | 暗色重复 03；点外部、系统返回关闭菜单 | 同 03；暗色选中提示可见，关闭不触发动作 | 暗 |
| T47-05 | 标签管理/分配 | 管理中新建；空名/错误颜色尝试保存；改为有效值保存；编辑；分配两标签保存；筛选并切匹配全部、清空 | 无效表单不能保存；CRUD/分配和 AND/OR 结果正确；保存按钮固定在 footer；长列表可滚动 | 明 |
| T47-06 | 标签管理/分配 | 暗色重复 05；删除标签先取消再确认；请求中重复点击保存/删除、输入并尝试关闭 | 确认框显示标签名和危险按钮；取消不删，确认只请求一次；忙碌锁有效；暗色控件清晰 | 暗 |
| T47-07 | 详情顶部菜单 | 重命名：空白名、长名、取消、保存；切换每种状态；打开详情面板并滚动 | 空白不保存，取消不提交；状态回调正确；详情字段、头像、时间与原数据一致 | 明 |
| T47-08 | 详情顶部菜单 | 暗色重复 07；删除可丢弃会话，先取消后确认；检查 fork/disconnect 可用性 | 删除确认显示准确会话名，确认后回列表；不支持的入口隐藏，忙碌时相应动作禁用；暗色危险态可辨 | 暗 |
| T47-09 | 草稿代理选项 | 打开全屏选项；切代理；选同名不同路径项目；选择模式/配置；制造应用失败 | 项目映射正确；模式/配置乐观选中、加载和失败回退保持；不能在运行中改选；主内容可滚动 | 明 |
| T47-10 | 已有会话代理选项 | 暗色打开；核对项目只读；进入分支搜索 local/remote/占用分支；返回键与关闭按钮分别测试；新建、切换分支 | 原有工作树跳转/原地切换正确；占用/当前分支不能误切；子页返回主选项，再返回关闭；暗色选中/禁用可辨 | 暗 |
| T47-11 | 输入栏插入面板 | 打开插入面板，依次切快捷消息/专家/命令；分别选一项（草稿为空、有文本各测一次） | 标签页选中背景正确；空/加载/长列表正常；插入位置、专家前缀和命令尾空格与原逻辑一致；选完关闭 | 明 |
| T47-12 | 输入栏插入面板 | 暗色重复 11；打开软键盘后切页、滚动到末项、取消面板 | 三标签清晰，无无限高度异常，末项可点；取消不改草稿、键盘不遮挡关键动作 | 暗 |
| T47-13 | 问题卡片 | 触发单选、多选、多问题；点文字行和复选框各一次；选 Other 输入；尝试空提交再完整提交 | 一次点击只切一次；单选互斥且保留原取消选择行为；全部回答后可提交；答案标签/Other 文本正确，只提交一次 | 明 |
| T47-14 | 问题卡片 | 暗色重复 13，另触发新问题后点跳过；检查长描述与软键盘 | 跳过正常；类型 badge、选中态与文本可读；长内容滚动不遮按钮 | 暗 |
| T47-15 | 权限卡片 | 触发命令/cwd、diff、计划、URL、原始 JSON 权限；分别拒绝、单次/持续允许 | 结构化内容不丢失；Outline/Default 层级清晰；原 optionId 原样发送；发送中禁止重复 | 明 |
| T47-16 | 权限卡片 | 暗色重复 15；长内容滚到末尾，检查嵌套 diff/Markdown | 内容、类型 badge、按钮与加载状态可读，无裁切或误触 | 暗 |
| T47-17 | 原生计划审批 | 测空计划、长计划；请求修改并输入空白/有效备注后发送；分别新触发后批准、放弃 | 空备注不提交；decision/备注 trim 不变；三种响应各只发送一次；提交后输入不变 | 明 |
| T47-18 | 原生计划审批 | 暗色重复 17，打开键盘并滚动计划 | 卡片/输入/按钮对比正确，内容与动作可到达，加载/禁用无重复响应 | 暗 |
| T47-19 | 详情时间线/工具/输入栏 | 展开工具和思考；查看图表/表格输出分隔线与时间线；附件预览；发送、停止、重连 | 分隔线使用主题 border；现有工具折叠、图片和输入行为不变；错误条动作仍可用 | 明 |
| T47-20 | 详情时间线/工具/输入栏 | 暗色重复 19；断网重连、返回会话再进入 | 暗色分隔可辨、状态正常恢复；草稿、消息/流式输出无新增丢失 | 暗 |

对照网页端（WEB 为 `/mnt/e/claude/codeg/src/`）：

- 01–04：`components/conversations/sidebar*.tsx`（含 `sidebar-section-header.tsx`）、`components/layout/sidebar.tsx` 的分区、紧凑行、选中和菜单。
- 05–06：`components/conversations/sidebar-tag-filter.tsx` 的筛选、标签和管理动作。
- 07–10：网页会话侧栏菜单及 `components/chat/` 的会话/代理选项、工作区和分支控制。
- 11–12：`components/chat/composer/composer-add-menu.tsx` 和 `rich-composer.tsx` 的插入入口。
- 13–14：`components/message/ask-question-result-card.tsx` 的问题层级、标签和答案展示；交互流程由 Android 原请求卡片保持。
- 15–16、19–20：`components/message/tool-call-block.tsx`、`tool-group-part.tsx` 的工具/输出层级和 `components/chat/` 的权限控件。
- 17–18：`components/message/plan-mode-card.tsx`、`plan-card.tsx` 的计划层级及聊天审批动作。
- 允许差异：Android 保留 48dp 触摸行、现有 Material 图标、原生键盘和滚动；代理选项使用共享全屏面板以保留子页返回语义；项目路径在选择器中直接展示以区分同名项目。Toast、头像、输入组、图片预览及折叠的统一组件迁移留给后续任务。

## 5. 回归点

- 会话列表加载、空态、下拉刷新失败后的重试/关闭、多服务器切换、分组计数、折叠状态和新会话 FAB。
- 会话行点击、长按、更多按钮、置顶顺序、标签展示与项目副标题。
- 标签作用域（全局/根项目/已分配）、保存时剔除失效标签、CRUD、颜色校验、筛选匹配全部/任一、清除筛选。
- 详情重命名、状态、置顶、元数据、删除回列表；fork/disconnect 的版本兼容与运行中禁用。
- 草稿代理/目录选择、已有目录只读、配置请求并行锁与失败回退、分支搜索/新建/占用防护、工作树会话跳转。
- 快捷消息追加、已知专家前缀替换、Codex 与其它代理前缀、斜杠命令插入；未修改 ComposeInsert 逻辑。
- 问题答案映射、跳过、Other 空值校验；权限 optionId；计划三种 decision 和备注；重复提交锁。
- 流式输出、消息渲染、工具/思考展开、附件、发送/停止/重连；明暗主题、键盘、旋转、长文本和小屏滚动。

## 6. 已知风险 / 未完成

- 编译、lint、JVM 和真机均待协调方统一执行；本任务未触发 CI，没有构建通过结论。
- 共享 ListRow 无 enabled 参数，模式/分支用回调保护和透明度；共享确认框无 busy 参数、文本框无 enabled 参数，标签/计划用回调锁，视觉禁用态存在差异。
- 卡片改用共享 CodegCard 的 padding/间距；小屏长权限/多问题卡片需优先检查布局。只读元数据表和自定义颜色标签不机械改成可点击列表行/固定色 badge。
- TODO 清单（以下是计划内后续阶段，不是未完成的 T47 M3 替换）：

| 文件 | TODO |
|---|---|
| sessions/SessionListScreen.kt | T52 CodegToaster 列表错误 |
| sessions/tags/ConversationTagUi.kt | T52 CodegToaster 标签错误 |
| sessiondetail/SessionDetailScreen.kt | T52 CodegToaster 会话提示 |
| sessiondetail/AgentOptionsSheet.kt | T52 CodegToaster 应用失败；T53 CodegAvatar 代理；T53 CodegCommandList 分支搜索 |
| sessions/SectionHeader.kt | T53 CodegCollapsible 分区主体 |
| sessions/SessionRow.kt | T53 CodegAvatar 会话头像 |
| sessiondetail/ComposeBar.kt | T53 CodegInputGroup |
| sessiondetail/ComposeInsertSheet.kt | T53 CodegCommandList 专家/命令 |
| sessiondetail/rendering/ToolCallCard.kt | T53 CodegCollapsible 工具 |
| sessiondetail/rendering/ReasoningBlock.kt | T53 CodegCollapsible 思考 |
| sessiondetail/rendering/InlineImage.kt | T53 CodegImagePreviewDialog |

独立只读审查：发现多选问题使用 CodegListRow 会截断长选项，已改为 CodegCheckbox(label) + 可换行说明文字；说明点击仍触发原 toggle 回调。其它未发现明确编译问题，不代表编译通过。
