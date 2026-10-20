# T55 测试文档：会话列表与详情图标、Toast 和组件收尾

- 分支：`task/T55-ui2-sessions`（本地工作分支 `cli/T55-ui2-sessions`）；提交：本文件与实现同批提交，准确 hash 见交付报告，避免自引用。
- 依赖：T47、T51、T52、T53、T60 已在基准。测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 执行状态：仅完成静态检查。未运行 CI、本地 Gradle、JVM 测试、adb、索引生成；以下运行时用例全部待执行。

## 1. 测试范围

路径缩写：`F/` = `app/src/main/java/app/codeg/android/feature/`。

完整代码改动文件：

- `F/sessions/SessionListScreen.kt`
- `F/sessions/SessionRow.kt`
- `F/sessions/SectionHeader.kt`
- `F/sessions/tags/ConversationTagUi.kt`
- `F/sessiondetail/AgentOptionsSheet.kt`
- `F/sessiondetail/ComposeBar.kt`
- `F/sessiondetail/ComposeInsertSheet.kt`
- `F/sessiondetail/SessionActions.kt`
- `F/sessiondetail/SessionDetailScreen.kt`
- `F/sessiondetail/T55BranchChoice.kt`（新增内部展示映射）
- `F/sessiondetail/interactive/AskQuestionCard.kt`
- `F/sessiondetail/interactive/PermissionRequestCard.kt`
- `F/sessiondetail/interactive/PlanApprovalCard.kt`
- `F/sessiondetail/rendering/InlineImage.kt`
- `F/sessiondetail/rendering/LivePlanView.kt`
- `F/sessiondetail/rendering/ReasoningBlock.kt`
- `F/sessiondetail/rendering/ToolCallCard.kt`
- `F/sessiondetail/rendering/ToolValueFormat.kt`
- `F/sessiondetail/timeline/TimelineNodeBody.kt`

另新增：`app/src/main/res/values/strings_t55.xml`、`app/src/main/res/values-zh-rCN/strings_t55.xml`、`app/src/test/java/app/codeg/android/feature/sessiondetail/T55BranchChoiceTest.kt` 和本文件。

公开 API：无新增/变更。新增内部 `T55BranchChoice` / `t55BranchChoice`，仅映射分支列表展示身份、checkout target 和可选态；原 ViewModel、网络、checkout 调用保持不变。名称已在本区域 code-index 查重，无同名声明。

受影响入口：会话列表、标签面板、会话菜单、代理设置与分支选择、输入栏与插入搜索、三类交互审批、工具/思考折叠、时间线与图片预览。

范围外：设计系统、网络/业务接口、导航、Gradle 配置、code-index、状态图、服务端。

### 图标替换清单

以下均为 Material `Icons.*` → `Lucide.*`；行内/按钮 glyph 统一 16dp（共享组件自带图标由组件负责）。

| 文件（相对 F/） | 替换前 | 替换后 |
|---|---|---|
| sessions/SessionListScreen.kt | Dns/Add/Forum/PushPin/Folder/Inbox/ErrorOutline/Close/ExpandMore/Check | Server/Plus/MessageSquare/Pin/Folder/Inbox/CircleAlert/X/ChevronDown/Check |
| sessions/SessionRow.kt | PushPin | Pin |
| sessiondetail/ComposeBar.kt | Add/Image/Stop/ArrowUpward | Plus/Image/Square/ArrowUp；输入栏按钮显式 48dp |
| sessiondetail/SessionDetailScreen.kt | ArrowBack/Forum/ArrowDownward/Close | ArrowLeft/MessageSquare/ArrowDown/X |
| sessiondetail/SessionActions.kt | MoreVert | EllipsisVertical |
| sessiondetail/AgentOptionsSheet.kt | Folder/Lock/AltRoute/KeyboardArrowRight/ArrowBack/Add/Check | CodegFolderBadge/Lock/GitBranch/ChevronRight/ArrowLeft/Plus/Check；分支返回按钮 48dp |
| sessiondetail/interactive/AskQuestionCard.kt | Forum | MessageSquare |
| sessiondetail/interactive/PermissionRequestCard.kt | Assignment/GppMaybe | ClipboardList/ShieldAlert |
| sessiondetail/interactive/PlanApprovalCard.kt | Checklist | ListTodo |
| sessiondetail/rendering/LivePlanView.kt | Checklist/CheckCircle/RadioButtonUnchecked | ListTodo/CircleCheckBig/CircleDashed |
| sessiondetail/rendering/ReasoningBlock.kt | Psychology/KeyboardArrowRight | Brain；折叠箭头由共享组件负责 |
| sessiondetail/rendering/ToolCallCard.kt | ExpandLess/ExpandMore/CheckCircle/Adjust/RadioButtonUnchecked/ErrorOutline/Layers/Description/Edit/Search/Terminal/Language/Checklist/Groups/Build | 共享折叠箭头/CircleCheckBig/CircleDot/CircleDashed/CircleAlert/Layers/FileText/Pencil/Search/Terminal/Globe/ListTodo/Users/Wrench |
| sessiondetail/rendering/ToolValueFormat.kt | ErrorOutline | CircleAlert |
| sessiondetail/timeline/TimelineNodeBody.kt | Check/ContentCopy/ArrowUpward/Archive/WarningAmber | Check/Copy/ArrowUp/Archive/TriangleAlert |

网页端语义核对：`chat/conversation-context-bar.tsx`（MessageSquare/Folder）、`chat/message-input.tsx`（Square/ArrowUp）、`conversations/conversation-detail-header.tsx`（Pin/EllipsisVertical）、`chat/plan-approval-card.tsx`（ListTodo）、`chat/permission-dialog.tsx`（ShieldAlert）、`message/plan-card.tsx`（计划状态）；其余遵循 `icon-map.md`。

### Toast 替换清单

| 文件 | 替换前 | 替换后 |
|---|---|---|
| sessions/SessionListScreen.kt | 已加载列表中的刷新/操作错误 banner | LocalCodegToaster.error；显示后 dismissError，保留列表数据；首次加载失败仍为 InlineError |
| sessions/tags/ConversationTagUi.kt | 标签面板 ui.error Text | 与列表共用 error 状态，由列表唯一发送 Toast，避免重复 |
| sessiondetail/SessionDetailScreen.kt | ui.notice 临时 NoticeBanner | 断开完成 success；审批备注丢失 warning；其余 notice error；发送后 dismissNotice |
| sessiondetail/AgentOptionsSheet.kt | mode/config apply 的局部 note Text | apply 返回 null 时直接 error，沿用原失败资源；分支失败由详情统一发送 |

新增 Toast 标题走 en/zh 资源；description 保留既有 ViewModel/服务端错误内容，不改变原文含义。streamStatus（含服务器管理动作）、页面初始 error、加载/空状态继续在页面展示。没有增加服务端原本不发送的成功事件，也没有引入 loading Toast 覆盖原有操作锁。

网页端对照：`conversations/conversation-tag-picker.tsx:108` 和 `conversation-tag-list-editor.tsx:152` 使用 `toast.error(title, {description})`；`chat/session-config-stale-banner.tsx:79` 的应用成功反馈、`chat/conversation-context-bar.tsx` 的操作成功/失败反馈用于核对等级与触发时机。Android 使用现有状态事件，标题统一为操作失败/会话提示，具体原因放 description。

### T53 组件替换清单

| 文件 | 替换前 | 替换后 |
|---|---|---|
| sessions/SectionHeader.kt | 手写折叠触发器 | CodegInstantCollapsible + CodegSectionHeader；列表行继续作为 LazyColumn 兄弟项，保持虚拟化 |
| sessions/SessionRow.kt | AgentAvatar | 彩色品牌使用 CodegAvatar + 原品牌 painter；需 tint 的单色品牌保留 AgentAvatar，原因见第 6 节 |
| sessiondetail/AgentOptionsSheet.kt | AgentAvatar、只读项目 Folder 图标、分支搜索和手写结果行 | CodegAvatar（同上单色例外）、CodegFolderBadge、CodegCommandList（当前/占用/忙碌禁用） |
| sessiondetail/ComposeInsertSheet.kt | 手写插入列表 | CodegCommandList，快捷语/专家/命令可搜索；描述合入 label，原插入转换不变 |
| sessiondetail/rendering/ReasoningBlock.kt | clickable + AnimatedVisibility | CodegCollapsible；共享组件负责箭头与点击 |
| sessiondetail/rendering/ToolCallCard.kt | 单工具/工具组手写折叠 | CodegCollapsible，保留稳定 id 展开状态和正文 |
| sessiondetail/rendering/InlineImage.kt | 仅内联图片 | 点击打开 CodegImagePreviewDialog；base64 解码失败继续原占位 |
| sessiondetail/ComposeBar.kt | 多行输入与附件/action 布局 | 保留，CodegInputGroup 仅支持 singleLine=true，无法等价迁移（见第 6 节） |

### 第二阶段例外清理

| 文件 | 替换前 | 替换后 |
|---|---|---|
| sessions/tags/ConversationTagUi.kt | 仅回调 guard、删除确认忙碌状态不明显 | CodegTextField(enabled = !busy)、CodegConfirmDialog(busy = ui.tagsBusy) |
| sessions/tags/ConversationTagUi.kt | CodegFilterChip 旧位置参数 | 已符合 T60 唯一签名 selected/label/onClick，无 Deprecated，无需改调用 |
| sessiondetail/AgentOptionsSheet.kt | String path Select 再查实体 | CodegSelect<FolderDetail>：name 标签、path 描述、id key，直接返回实体 |
| sessiondetail/interactive/PlanApprovalCard.kt | 多行输入仅 guard | enabled = !submitting、minLines=2、maxLines=6 |
| sessiondetail/ComposeInsertSheet.kt、AgentOptionsSheet.kt、interactive 三卡、rendering/LivePlanView.kt、ToolCallCard.kt | CircularProgressIndicator | CodegSpinner，保留原加载位置 |
| 原危险确认 | 已是 CodegConfirmDialog | 保持；没有用普通 CodegDialog 模拟的危险确认。普通重命名/状态/标签表单仍用 CodegDialog |

### grep 自查

执行（目标目录均为 `F/sessions F/sessiondetail`）：

```bash
rg -n 'Icons\.' <目标目录>
rg -n 'TODO\(T5[0-9]\)' <目标目录>
rg -n '\b(OutlinedTextField|LinearProgressIndicator|CircularProgressIndicator|AlertDialog|DropdownMenu|DropdownMenuItem|ModalBottomSheet|FilterChip|HorizontalDivider|Button|TextButton|OutlinedButton)\s*\(' <目标目录>
git diff --check
```

结果：上述三组目标调用均为 0；diff 无空白错误。CodegDialog 保留 3 处（重命名、状态选择、标签表单），均非危险确认。BasicTextField 保留 1 处（多行 composer）。不将共享组件内部尚未迁移的 Material 图标算入本 feature 的 0 结果；设计系统不在改动范围。

额外临时只读检查：20 个新增/修改 Kotlin 文件（含测试）的括号配对、状态委托 import、所有使用的 Lucide 属性存在、两个新增资源 XML 可解析，全部通过。这些检查不等于 Kotlin 编译或 lint；未生成或提交 code-index。

## 2. 编译检查

自评风险：中等，未编译。

- `AgentOptionsSheet.kt:287`：泛型 CodegSelect 的 FolderDetail 推断及 key/optionDescription；已按 T60 实际签名核对。
- `AgentOptionsSheet.kt:416`、`T55BranchChoice.kt:12`：分支 command id 的 l-/r- 前缀和 target，已补 JVM 用例但未执行；确认原 checkout 目标保持。
- `ComposeInsertSheet.kt:106`：CodegCommandItem 构造参数、专家 id 与命令名回传、有限高度嵌套列表；需统一编译及真机滚动确认。
- `rendering/InlineImage.kt:44`：BitmapPainter、nullable bitmap smart cast、CodegPreviewImage/CodegImagePreviewDialog 参数及 Compose 状态 import。
- `rendering/ToolCallCard.kt:63`、`:239`、`rendering/ReasoningBlock.kt:38`：header/content Compose lambda 与命名 modifier；已静态核对括号，仍须 Kotlin 编译。
- `SessionDetailScreen.kt:109`、`sessions/SessionListScreen.kt:101`：LaunchedEffect 与消费回调，复核了 getValue/setValue 操作符 import。所有新增资源 en/zh key 一致。
- 无新增实验性 API 或依赖；原 ExperimentalMaterial3Api 注解保留。统一 `./gradlew :app:check` 关注 Compose 调用、泛型重载、资源/lint、T55BranchChoiceTest 和原会话测试。

## 3. 单元测试

以下新增 JVM 测试源代码，按测试文档模式未执行；不声明 red/green 或测试通过。

| 测试类::方法 | 覆盖点 |
|---|---|
| T55BranchChoiceTest::remoteBranchKeepsFullDisplayAndCheckoutTarget | origin/feature/topic 保留显示身份，checkout target 为 feature/topic |
| T55BranchChoiceTest::localAndRemoteCurrentBranchesAreDisabled | 当前本地与对应远程分支均不可选 |
| T55BranchChoiceTest::occupiedAndBusyBranchesAreDisabled | worktree 占用、会话运行或切换中禁止选择 |
| T55BranchChoiceTest::sameNameLocalAndRemoteKeepDistinctIdentity | 同名本地/远程 id 不冲突 |

## 4. 测试用例

前置条件：官方 codeg 0.38 / T13Test 测试服，多个服务器、同名不同路径项目、置顶/普通会话、各种品牌代理、可丢弃会话和标签、local/remote/占用分支、权限/问题/计划审批、含图片及工具输出的记录。每项均待协调方执行。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T55-01 | 会话列表 | 折叠展开各分区、滚动长列表、服务器菜单、会话菜单 | 单次切换、计数/选择正确；16dp Lucide、品牌头像/单色 tint 清楚，列表仍虚拟化 | 明、暗各一次 |
| T55-02 | 列表错误 | 首次断网加载；恢复加载后断网刷新；再执行失败置顶 | 首次保留 InlineError；已有数据保留；刷新/操作各一条 error Toast，消费后重组不重播，恢复后可刷新 | 明、暗各一次 |
| T55-03 | 标签面板 | 断网分别创建、编辑、分配、删除标签；恢复网络再次操作 | 每次失败仅一条 error Toast；失败面板不关闭、数据不丢；恢复后正常保存 | 明、暗各一次 |
| T55-04 | 标签忙碌 | 保存/删除时重复点、输入、返回；删除先取消再确认 | 输入呈禁用，删除确认 busy 防重复/关闭；目标名正确，取消不删 | 明、暗各一次 |
| T55-05 | 详情操作 | 断网发送、重命名、改状态、删除；模拟权限/问题/计划响应失败 | 每次 notice 一条 error Toast；草稿/待审批恢复机制不变；无重复发送 | 明、暗各一次 |
| T55-06 | 断开会话 | 支持断开的会话执行断开；重组/返回重入 | 一条 success Toast；成功目标正确，不重复播放；不支持时入口隐藏 | 明、暗各一次 |
| T55-07 | 审批备注 | 触发不支持传送备注的审批回退，提交有效备注 | 一条 warning Toast 提示备注丢失；审批仍按原协议提交 | 明、暗各一次 |
| T55-08 | 代理选项 | 选同名不同路径项目、切品牌代理；失败应用 mode/config 后重试 | 返回正确实体，路径可区分；品牌可辨；每个失败各一条 error Toast，选择回退、锁释放 | 明、暗各一次 |
| T55-09 | 分支选择 | 搜索本地/远程含 slash 分支；点当前/占用/可用项；切换中连点与 Enter；制造创建/切换失败 | id/checkout 目标不混；当前/占用/忙碌不可选；可用项原地切换或打开工作树；失败一条 error Toast | 明、暗各一次 |
| T55-10 | 插入面板 | 三个标签分别搜索名称/描述；点快捷语、专家、命令；草稿空/非空各测；同名不同描述 | 描述可辨，搜索正确；原追加、前缀替换、尾空格保持；选后关闭；长列表/无结果正常 | 明、暗各一次 |
| T55-11 | 工具/思考 | 展开单工具、工具组、思考；滚动流式更新；检查无正文工具 | 共享箭头不重复、一次切换；更新不重置稳定 id 状态；正文不丢，无正文不展开 | 明、暗各一次 |
| T55-12 | 图片 | 点有效内联图片；缩放、拖动、关闭；另测损坏 base64 | 打开共享预览，可返回；原 caption 保留；坏图显示占位且不崩溃 | 明、暗各一次 |
| T55-13 | 输入栏/时间线 | 多行输入、添加/移除附件、发送/停止、复制消息、跳到底部 | 多行与附件布局保持；发送/停止、附件移除、跳到底部与复制触摸目标至少 48dp；Lucide/Spinner 可辨，复制和滚动行为正常 | 明、暗各一次 |
| T55-14 | 三类交互卡片 | 单/多选、Other、跳过、允许/拒绝、计划请求修改并提交 | 答案不变且只发送一次；审批提交中输入禁用；Spinner 清晰，明暗对比正常 | 明、暗各一次 |
| T55-15 | 持续连接状态 | 断网重连/认证失败，点击服务器管理动作 | streamStatus 持续提示与可操作按钮保留，不误当临时 Toast 消费 | 明、暗各一次 |

网页端逐项对照：01–04 → `conversations/sidebar-conversation-list.tsx`、`sidebar-section-header.tsx`、`conversation-tag-picker.tsx`、`conversation-tag-list-editor.tsx`；05–07 → `conversations/conversation-detail-header.tsx`、`chat/plan-approval-card.tsx`；08–09 → `chat/conversation-context-bar.tsx`、`chat/model-option-list.tsx`；10、13 → `chat/message-input.tsx`；11 → `message/agent-tool-call.tsx`、`message/content-parts-renderer.tsx`；12 → `message/image-actions.tsx`（预览交互按共享组件）；14 → `chat/question-dialog.tsx`、`permission-dialog.tsx`、`plan-approval-card.tsx`；15 → `chat/session-failure-banner.tsx`、`session-config-stale-banner.tsx`。

允许差异：原生键盘/返回、48dp 触摸目标；CommandList 使用单行 label 合并描述，长内容可省略；代理单色标识保留原 tint 组件；输入栏因组件参数缺口保留现状。

## 5. 回归点

引用 `.taskmaster/docs/tests/T47-ui-sessions.md`，以下全部需复测：

- T47-01–04：列表折叠、服务器/行菜单、长按与更多入口一致。
- T47-05–06：标签 CRUD/分配/筛选、忙碌锁和删除目标。
- T47-07–08：重命名、状态、删除、fork/disconnect 支持性与详情信息。
- T47-09–10：同名项目身份、mode/config 乐观回退、分支 checkout 与返回语义。
- T47-11–12：三类插入转换、软键盘和列表高度。
- T47-13–18：问题/权限/计划协议参数、单次提交、长内容与禁用输入。
- T47-19–20：工具/思考展开、消息/附件、流式更新、断网恢复与草稿。

## 6. 已知风险 / 未完成

- 编译、JVM 测试、真机与所有明暗用例未执行，交协调方统一验证。Toast 显示依赖 T58 在主壳挂载 LocalCodegToaster/Host；本任务不改主壳。
- **设计系统缺口：ComposeBar 保留多行 BasicTextField 和附件/action 布局。** 当前 CodegInputGroup 在内部固定 singleLine=true，无多行/附件纵向 slot；本任务禁止扩展设计系统。需后续任务补参数后迁移，不能用单行替换破坏现有输入行为。
- **设计系统缺口：单色代理头像保留 AgentAvatar。** CodegAvatar 仅接收 painter，无 tint/ColorFilter；直接传模板品牌素材会丢失原 AgentIcon 主题 tint。彩色品牌已迁移 CodegAvatar，单色路径待组件能力补齐。
- 分区采用 CodegInstantCollapsible 负责触发器/语义，空 content；行仍在父 LazyColumn 中按相同 collapsed 状态渲染，避免将整个分区放入单项而丢失虚拟化。
- ui.notice 是无类型字符串：已识别断开 success 和备注丢失 warning，其余按 error；既有附件部分跳过/会话运行中提示也会进入 error。精确等级需要后续有类型事件契约，本任务不改业务状态接口。既有服务端/英文 notice description 不在本任务中重写。
- CodegCommandList 无 subtitle slot，插入描述合入 label；长描述会单行省略。分支当前/占用以文本说明，切换期间整体禁用；需真机确认长分支名、键盘和嵌套有限高度滚动。
- 无正文工具仍显示共享折叠箭头但不会展开；共享组件无隐藏箭头参数。本任务不越界修改组件。
