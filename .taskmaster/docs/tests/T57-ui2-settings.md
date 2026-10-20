# T57 测试文档：设置图标、Toast 与例外清理

- 分支：`task/T57-ui2-settings`；本文与代码同一提交，hash 以 `git log -1 --format=%H -- .taskmaster/docs/tests/T57-ui2-settings.md` 及交付报告为准（避免自引用）。
- 依赖：T49、T51、T52、T53、T60，均已合入基准。Toast 的全局 Host 由 T58 挂载，集成测试须包含该接入。
- 测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。以下运行用例全部待协调方执行；本任务仅完成静态检查，未运行 Gradle、CI、adb 或真机。

## 1. 测试范围

路径前缀 `A/ = app/src/main/java/app/codeg/android/`。改动文件（均位于 `A/feature/settings/`）：

- `AgentConfigKimiView.kt`
- `AgentConfigPiView.kt`
- `AgentDetailScreen.kt`
- `AgentsScreen.kt`
- `AssistantsScreen.kt`
- `ChatChannelsScreen.kt`
- `ChatChannelsViewModel.kt`
- `ChatGlobalSettingsScreen.kt`
- `ExpertsScreen.kt`
- `GeneralScreen.kt`
- `McpScreen.kt`
- `ModelProvidersScreen.kt`
- `QuickMessagesScreen.kt`
- `SettingsChoice.kt`
- `SettingsScreen.kt`
- `SkillsScreen.kt`
- `SystemScreen.kt`
- `VersionControlScreen.kt`
- `customskills/CustomSkillsScreen.kt`
- `mcpmarket/McpMarketSheet.kt`

新增文件：`app/src/main/res/values/strings_ui2_settings.xml`、`app/src/main/res/values-zh-rCN/strings_ui2_settings.xml` 及本测试文档。没有修改已有共享 strings.xml。

- 公开 API：`ChatChannelsUiState` 追加默认参数 `toastIsError: Boolean = false`，仅用于选择 Toast 样式；无新公开函数，无接口/依赖更改。
- 入口：设置首页与所有设置叶页面、代理配置、助手、自定义技能、MCP 市场、频道详情及二维码面板。
- 范围外：core/designsystem、其他 feature、导航/Toast Host、服务端、代码索引、CI/设备。原请求顺序、校验、取消和二维码轮询逻辑保留。

### 1.1 图标替换清单

下表按文件列出原 Material 图标和迁移后使用的 Lucide 属性；按钮/行内图标 16dp，触摸控件与 glyph 分开设定。状态插图/关于页图标保留原来的较大尺寸，非行内图标。

| 文件 | 替换前（Icons.*） | 替换后（Lucide.*） |
|---|---|---|
| `AgentConfigKimiView.kt` | CheckCircle, ErrorOutline, Verified, WarningAmber | BadgeCheck, TriangleAlert |
| `AgentConfigPiView.kt` | CheckCircle, ErrorOutline, Refresh | CheckCircle, CircleAlert, RefreshCw |
| `AgentDetailScreen.kt` | Delete, ExpandMore, Refresh, Warning | RefreshCw, Trash2, TriangleAlert |
| `AgentsScreen.kt` | Person | User |
| `AssistantsScreen.kt` | ArrowDownward, ArrowUpward, Delete, Edit | ArrowDown, ArrowUp, Pencil, Trash2 |
| `ChatChannelsScreen.kt` | Add, Bolt, Chat, Close, Forum, KeyboardArrowRight, NotificationsActive, QrCode, Refresh, Send, Tune | BellRing, ChevronRight, MessageSquare, Plus, QrCode, RefreshCw, Send, SlidersHorizontal, X, Zap |
| `ChatGlobalSettingsScreen.kt` | Add, Delete | Plus, Trash2 |
| `ExpertsScreen.kt` | AutoAwesome, Close, School | GraduationCap, Sparkles, X |
| `GeneralScreen.kt` | Add, Remove | Minus, Plus |
| `McpScreen.kt` | Add, Check, Memory | Check, Cpu, Plus |
| `ModelProvidersScreen.kt` | Add, Memory | Cpu, Plus |
| `QuickMessagesScreen.kt` | Add, Chat | MessageSquare, Plus |
| `SettingsScreen.kt` | ArrowBack, Bolt, Brush, Chat, Difference, Extension, Info, KeyboardArrowRight, Language, Memory, Person, Policy, Schedule, School, Settings, Tune | ArrowLeft, BookOpenText, Bot, ChevronRight, Clock, GitBranch, Globe, GraduationCap, Info, MessageSquareText, Palette, PlugZap, SendHorizontal, Server, Settings, Shield, SlidersHorizontal, User, Zap |
| `SkillsScreen.kt` | Add, Close, Description, Folder | FileText, Folder, Plus, X |

网页端核对：`WEB/components/settings/settings-shell.tsx` 导航实际使用 Palette、Bot、Server、PlugZap、BookOpenText、GitBranch、MessageSquareText、SendHorizontal，对应首页按这些语义迁移；其他按 icon-map.md 映射。语言/许可/关于等移动端入口采用 Globe/Shield/Info。

### 1.2 Toast 替换清单

| 文件 | 替换前 | 替换后 |
|---|---|---|
| AgentConfigKimiView.kt | TODO(T52)、KimiResultBanner 与 4s 延时 | LocalCodegToaster success/error；消费后清空 banner，重复相同操作仍可提示 |
| AgentConfigPiView.kt | TODO(T52)、PiResultBanner 与 4s 延时 | success/error；配置、运行时、安装/卸载、信任错误，保留持久命令校验结果 |
| ChatChannelsScreen.kt | 列表 Snackbar；详情 toast 仅延时清空；操作错误 Dialog | 列表 success/error，详情 success/error；每个消费后调用原 dismiss 方法 |
| ChatChannelsViewModel.kt | 删除结果没有类型；连接/断开硬编码英文 | 新增 toastIsError 显示元数据；连接请求与断开反馈走新中英资源 |

文案：Kimi/Pi 继续使用现有字符串资源及已有异常展示文字；频道测试/删除/令牌反馈继续使用现有资源，新增连接/断开资源两项中英对称。不把加载错误、刷新失败、字段校验、空状态、二维码状态改为 Toast。异步进度继续用按钮禁用和 Spinner，不另加 loading Toast，避免重复反馈。

网页端对照：kimi-code-config-panel.tsx（saved / saveFailed / fixErrorsFirst）、pi-config-panel.tsx（runtimeSaved / savePiFailed / binaryInstalled / binaryUninstalled / savePiTrustFailed）、channel-list-tab.tsx（connect/disconnect/test/delete success/error），均位于 WEB/components/settings/。

### 1.3 T53 组件替换清单

| 文件 | 替换前 | 替换后 |
|---|---|---|
| AgentDetailScreen.kt | ExpandableGroup / AdvancedSection 手写 Row、旋转、AnimatedVisibility | CodegCollapsible；标题触摸高度 ≥48dp，原草稿和校验保留 |
| AssistantsScreen.kt | 手绘字母头像 | CodegAvatar(40dp)；emoji 的例外见下文 |
| ChatChannelsScreen.kt | 手绘频道图标头像；仅内联 QR 图片 | CodegAvatar + 带主题 tint / 内边距的 VectorPainter；图片点击进入 CodegImagePreviewDialog/BitmapPainter |
| mcpmarket/McpMarketSheet.kt | 普通查询 TextField | CodegInputGroup + 16dp Search；搜索按钮和回调保留 |

CodegInstantCollapsible / CodegCommandList / CodegFolderBadge：本范围没有对应 TODO 或等价的命令面板/目录徽标待迁移；保留技能行的目录/文件类型图标，不把它误作文件夹名称徽标。

### 1.4 T49 / T60 例外清理

| 文件 | 替换前 | 替换后 |
|---|---|---|
| AssistantsScreen.kt | AssistantField 仅 onChange guard | CodegTextField(enabled)，保留回调 guard |
| SettingsChoice.kt | 按名称查回 ID；重复名手写菜单 | 泛型 CodegSelect<Pair<String,String>>，key 绑定 ID，描述显示 ID |
| AgentDetailScreen.kt | SelectField 先转选项索引再选回实体 | 泛型 CodegSelect 直接保留原 Pair<T,String>，包括可空 T |
| mcpmarket/McpMarketSheet.kt | 用显示标签作当前值 | MarketSelect 分开 label / 实际 ID，源/安装选项/枚举按 ID 选中 |
| AgentConfigKimiView.kt / AgentConfigPiView.kt / AgentDetailScreen.kt / AssistantsScreen.kt / ChatChannelsScreen.kt / ChatGlobalSettingsScreen.kt / ExpertsScreen.kt / SkillsScreen.kt / SystemScreen.kt / VersionControlScreen.kt / customskills/CustomSkillsScreen.kt | CircularProgressIndicator | CodegSpinner（按钮/行内 15/16dp，原 18dp 按钮统一 16dp） |

保留与原因：
- 助手 emoji 自定义头像保留完整 glyph 的 Text + 圆背景。T53 CodegAvatar fallback 仅提取首个 Unicode code point，没有完整 emoji/内容 slot，会截断肤色/ZWJ 序列；因此不强行迁移。普通字母头像使用 DS 默认样式，不再单独按 avatar.color 着色。需要协调方确认是否由后续 DS 扩展完整头像内容/颜色参数。
- 二维码白底/黑图保留扫码对比度；二维码专用 sheet 继续拥有状态、刷新与轮询生命周期，共享图片 Dialog 只负责放大图片，关闭预览不重启轮询。
- Hermes 的 API key/OAuth/AWS 分类菜单按 T60 清单允许保留；通用同名选择器已迁移。
- 外观主题圆色样、持续加载骨架、复合列表/编辑表单、只读信息、技能目录类型图标保持专用表现；无不确定 LinearProgressIndicator 残留需要替换。
- CodegDialog 剩余为快捷消息编辑、提供商编辑、MCP 安装确认，均非危险删除。危险操作已有 CodegConfirmDialog，不新增模拟危险确认。CodegFilterChip 现有调用均命名参数，T60 无 Deprecated，不需重写。

### 1.5 grep / 静态自查

2026-10-20 在本 worktree 执行，下表为最终静态结果；不是编译或运行通过声明。

```sh
rg -n 'Icons\.|TODO\(T5[0-9]\)' app/src/main/java/app/codeg/android/feature/settings
rg -n '\b(OutlinedTextField|CircularProgressIndicator|LinearProgressIndicator|SnackbarHost|Snackbar)\s*\(' app/src/main/java/app/codeg/android/feature/settings
git diff --check
```

- Icons.：0；TODO(T5x)：0。
- OutlinedTextField / CircularProgressIndicator / LinearProgressIndicator / Snackbar(Host)：调用各 0。旧图标及上述控件 import 无残留。
- 只读代码复核发现的频道头像 tint/尺寸、二维码过期预览遮挡两项已修复并复核；未进行编译。
- 额外核对 Lucide 属性名全部存在；重复 import 为 0；State 委托 getValue/setValue import 保留。
- 新增 XML 可解析，中英键集合相同（2 项）；git diff --check 通过；改动路径均在授权冲突域/自有资源文件/本测试文档。

## 2. 编译检查

自评风险：中等，未编译。协调方统一 `./gradlew :app:check` 时关注：

- `SettingsChoice.kt:9`：泛型 Pair 推断、key 和描述回调；重复名按 ID 绑定，未知值显示原始 ID。
- `AgentDetailScreen.kt:862`：泛型 SelectField 包含可空 T 的 Pair，onSelect 回传原 T；907/938 的 CodegCollapsible header/content 是 Composable lambda，ColumnScope 在内部 Column 中恢复。
- `ChatChannelsScreen.kt:124`：rememberVectorPainter 的 ImageVector 支持；540 的 BitmapPainter / CodegPreviewImage / 图片 Dialog；Painter 包装通过 ColorFilter.tint 和 inset 保持主题笔画/半尺寸图标，需编译确认 DrawScope/Painter 扩展解析。
- `mcpmarket/McpMarketSheet.kt:41`：provider 是 String?，传入前已 orEmpty；安装 option ID 与源 ID 保持区分。
- `AgentConfigKimiView.kt:189`、`AgentConfigPiView.kt:403`、`ChatChannelsScreen.kt:226/415`：LaunchedEffect 消费后清空临时消息；无普通回调中调用 Composable，检查关闭页面/连续结果的事件时序。
- `ChatChannelsViewModel.kt:261`：UI data class 新增默认字段，不改变请求结构。连接成功提示表示请求被接受，真实连接状态仍由原刷新/轮询更新。
- `AssistantsScreen.kt:183`：TextField enabled 新参数；所有 Spinner 采用 T60 已有 size/modifier API，无新增依赖或实验性 API。
- lint：未使用旧 import 不影响 Kotlin 解析但仍需统一 lint；重点关注窄屏、TalkBack、字号、Dialog 安全区域、48dp 目标和头像笔画对比度。

## 3. 单元测试

本任务没有新增解析、映射算法或业务逻辑；仅显示组件替换、原回调透传和反馈元数据，未新增镜像 UI 实现的 JVM 测试。没有执行单元测试。

| 测试类::方法 | 覆盖点 |
|---|---|
| T60ChoiceTest::duplicateLabelsKeepIdentity / searchMatchesLabelAndDescriptionIgnoringCase | 复用泛型选择器同名 ID 与描述搜索契约 |
| T49 文档第 3 节所列 AgentsViewModelTest / CustomSkillPolicyTest / CustomSkillWireTest / McpMarketParametersTest | 原配置保存、关联策略、市场参数序列化回归，方法保持不变 |

## 4. 测试用例

前置条件：协调方包含依赖及 T58 Host 接入后统一构建；服务器 T13Test 或支持对应功能的原版 codeg；准备可恢复的测试代理、同名不同 ID 提供商/安装选项、助手（字母、中文、普通 emoji、ZWJ/肤色 emoji）、频道和可扫码微信账号。错误场景通过测试服务器受控失败/断网触发，卸载/删除只用测试对象。

所有用例**待执行**。每条分别以明色和暗色执行，表中拆为两个编号；中英文均执行 Toast 路径。每次操作观察只出现一次 Toast，连续重复同一成功/失败至少两次仍能触发；关闭面板后重开不重播旧提示。每条“入口”均从设置首页开始。

| 编号 | 入口/页面（网页端对照） | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T57-01 | 设置首页（settings-shell.tsx） | 依次打开全部叶页面，再返回；检查图标与首尾行 | 导航 Lucide 语义一致，16dp；空状态大图标保留，跳转不变 | 明 |
| T57-02 | 设置首页（settings-shell.tsx） | 依次打开全部叶页面，再返回；检查图标与首尾行 | 导航 Lucide 语义一致，16dp；空状态大图标保留，跳转不变 | 暗 |
| T57-03 | 外观、语言（appearance-settings.tsx / general/page.tsx） | 切主题/主题色/中英语言，重开首页 | 色样和资源持久化，图标颜色与返回正常 | 明 |
| T57-04 | 外观、语言（appearance-settings.tsx / general/page.tsx） | 切主题/主题色/中英语言，重开首页 | 色样和资源持久化，图标颜色与返回正常 | 暗 |
| T57-05 | 常规（general-settings.tsx） | 切委派后连续点深度加减 | 16dp Plus/Minus，48dp 按钮，边界和开关行为不变 | 明 |
| T57-06 | 常规（general-settings.tsx） | 切委派后连续点深度加减 | 16dp Plus/Minus，48dp 按钮，边界和开关行为不变 | 暗 |
| T57-07 | 代理列表及通用详情（acp-agent-settings.tsx） | 各代理开详情；展开/收起普通配置与高级配置；输入非法原始配置 | 共享折叠无重复箭头，草稿/持续校验保留，48dp 标题可点 | 明 |
| T57-08 | 代理列表及通用详情（acp-agent-settings.tsx） | 各代理开详情；展开/收起普通配置与高级配置；输入非法原始配置 | 共享折叠无重复箭头，草稿/持续校验保留，48dp 标题可点 | 暗 |
| T57-09 | 代理选择器（acp-agent-settings.tsx） | 准备同名不同 ID 提供商；分别选中保存；重开；检查 Hermes 三类菜单及可空选项 | 同名按 ID 区分，描述可见，原实体回传；分类菜单保留 | 明 |
| T57-10 | 代理选择器（acp-agent-settings.tsx） | 准备同名不同 ID 提供商；分别选中保存；重开；检查 Hermes 三类菜单及可空选项 | 同名按 ID 区分，描述可见，原实体回传；分类菜单保留 | 暗 |
| T57-11 | Kimi 保存（kimi-code-config-panel.tsx） | 修改合法配置保存；服务器返回失败；API key 缺模型再保存 | 保存 success、异常 error、模型必填 error 均一次；字段内容保留 | 明 |
| T57-12 | Kimi 保存（kimi-code-config-panel.tsx） | 修改合法配置保存；服务器返回失败；API key 缺模型再保存 | 保存 success、异常 error、模型必填 error 均一次；字段内容保留 | 暗 |
| T57-13 | Kimi 模型（kimi-code-config-panel.tsx） | 缺 URL/key 获取模型；准备非空/空模型响应；再让请求失败 | 缺凭据 error、模型数量/空结果 success、请求失败 error；Spinner 和按钮忙态正常 | 明 |
| T57-14 | Kimi 模型（kimi-code-config-panel.tsx） | 缺 URL/key 获取模型；准备非空/空模型响应；再让请求失败 | 缺凭据 error、模型数量/空结果 success、请求失败 error；Spinner 和按钮忙态正常 | 暗 |
| T57-15 | Pi 运行时（pi-config-panel.tsx） | 修改运行时保存成功，再模拟失败 | runtimeSaved success / 异常 error；结果消费后原横幅不再出现 | 明 |
| T57-16 | Pi 运行时（pi-config-panel.tsx） | 修改运行时保存成功，再模拟失败 | runtimeSaved success / 异常 error；结果消费后原横幅不再出现 | 暗 |
| T57-17 | Pi 配置（pi-config-panel.tsx） | 缺 provider/model 保存；缺 base URL 保存；再用合法配置成功/失败 | 两条前置校验 error、保存 success/error 均可重复触发 | 明 |
| T57-18 | Pi 配置（pi-config-panel.tsx） | 缺 provider/model 保存；缺 base URL 保存；再用合法配置成功/失败 | 两条前置校验 error、保存 success/error 均可重复触发 | 暗 |
| T57-19 | Pi 安装/卸载（pi-config-panel.tsx） | 仅用测试 Pi：安装成功/失败；卸载先取消再确认成功/失败 | 取消不请求；目标明确；安装/卸载各 success/error；禁用与 Spinner 正常 | 明 |
| T57-20 | Pi 安装/卸载（pi-config-panel.tsx） | 仅用测试 Pi：安装成功/失败；卸载先取消再确认成功/失败 | 取消不请求；目标明确；安装/卸载各 success/error；禁用与 Spinner 正常 | 暗 |
| T57-21 | Pi 信任/命令校验（pi-config-panel.tsx） | 信任切换触发失败；校验不存在和存在命令；重新检测 | 信任失败 error；命令校验持续结果保留，检测 16dp RefreshCw | 明 |
| T57-22 | Pi 信任/命令校验（pi-config-panel.tsx） | 信任切换触发失败；校验不存在和存在命令；重新检测 | 信任失败 error；命令校验持续结果保留，检测 16dp RefreshCw | 暗 |
| T57-23 | 快捷消息（quick-messages-settings.tsx） | 新增、编辑、删除先取消再确认；触发空列表 | Plus/Trash2/MessageSquare 统一；取消无写操作，原表单和确认正常 | 明 |
| T57-24 | 快捷消息（quick-messages-settings.tsx） | 新增、编辑、删除先取消再确认；触发空列表 | Plus/Trash2/MessageSquare 统一；取消无写操作，原表单和确认正常 | 暗 |
| T57-25 | 提供商（model-provider-settings.tsx） | 新增、编辑、删除测试提供商；检查空状态 | Server 导航和共享确认正常，原表单及接口行为保持 | 明 |
| T57-26 | 提供商（model-provider-settings.tsx） | 新增、编辑、删除测试提供商；检查空状态 | Server 导航和共享确认正常，原表单及接口行为保持 | 暗 |
| T57-27 | 助手（assistants-panel.tsx） | 检查四类头像；创建编辑并在 probing/busy 时输入；排序及删除取消/确认 | 字母共享头像；完整 emoji 无截断；禁用输入有视觉态且不可改，48dp 图标按钮可操作 | 明 |
| T57-28 | 助手（assistants-panel.tsx） | 检查四类头像；创建编辑并在 probing/busy 时输入；排序及删除取消/确认 | 字母共享头像；完整 emoji 无截断；禁用输入有视觉态且不可改，48dp 图标按钮可操作 | 暗 |
| T57-29 | MCP（mcp-settings.tsx） | 新增、切代理、删除测试 MCP；检查空列表 | PlugZap 导航、16dp 图标、原代理选择和确认正常 | 明 |
| T57-30 | MCP（mcp-settings.tsx） | 新增、切代理、删除测试 MCP；检查空列表 | PlugZap 导航、16dp 图标、原代理选择和确认正常 | 暗 |
| T57-31 | MCP 市场（mcp-settings.tsx 市场面板） | 选来源搜索、分页；选同名不同 ID 安装方案；改枚举/布尔；取消与确认安装 | InputGroup/Search 可用；当前值按 ID 显示；参数/密钥不改变类型，忙时禁用查询 | 明 |
| T57-32 | MCP 市场（mcp-settings.tsx 市场面板） | 选来源搜索、分页；选同名不同 ID 安装方案；改枚举/布尔；取消与确认安装 | InputGroup/Search 可用；当前值按 ID 显示；参数/密钥不改变类型，忙时禁用查询 | 暗 |
| T57-33 | 专家（experts-settings.tsx） | 加载失败重试、空态、详情启用/停用、保存 | Lucide 状态和 Spinner；持续错误仍可重试；原专家行为不变 | 明 |
| T57-34 | 专家（experts-settings.tsx） | 加载失败重试、空态、详情启用/停用、保存 | Lucide 状态和 Spinner；持续错误仍可重试；原专家行为不变 | 暗 |
| T57-35 | 技能（skills-settings.tsx） | 切代理筛选；打开目录/单文件；增改删测试技能；只读技能复查 | 目录与文件图标区别，FilterChip 正常；共享确认及只读限制保持 | 明 |
| T57-36 | 技能（skills-settings.tsx） | 切代理筛选；打开目录/单文件；增改删测试技能；只读技能复查 | 目录与文件图标区别，FilterChip 正常；共享确认及只读限制保持 | 暗 |
| T57-37 | 自定义技能（custom-skills-settings.tsx） | 创建编辑、复制、导入；检查关联/冲突/只读 | Spinner 替换，原参数/校验/导入和关联策略保持 | 明 |
| T57-38 | 自定义技能（custom-skills-settings.tsx） | 创建编辑、复制、导入；检查关联/冲突/只读 | Spinner 替换，原参数/校验/导入和关联策略保持 | 暗 |
| T57-39 | 频道列表（channel-list-tab.tsx） | 查看三种频道头像；删除测试频道成功与失败 | success/error 正确区分；失败恢复原列表；无 Snackbar 或错误 Dialog | 明 |
| T57-40 | 频道列表（channel-list-tab.tsx） | 查看三种频道头像；删除测试频道成功与失败 | success/error 正确区分；失败恢复原列表；无 Snackbar 或错误 Dialog | 暗 |
| T57-41 | 频道详情连接/断开（channel-list-tab.tsx） | 连接成功后观察真实状态；断开；分别触发请求失败 | 连接请求 success、断开 success、失败 error；真实状态由原轮询更新，无重复提示 | 明 |
| T57-42 | 频道详情连接/断开（channel-list-tab.tsx） | 连接成功后观察真实状态；断开；分别触发请求失败 | 连接请求 success、断开 success、失败 error；真实状态由原轮询更新，无重复提示 | 暗 |
| T57-43 | 频道测试/令牌（channel-list-tab.tsx） | 已连接频道发送测试成功/失败；移除令牌先取消再确认成功/失败 | 测试 success/error，令牌 success/error；取消无请求，反馈走资源 | 明 |
| T57-44 | 频道测试/令牌（channel-list-tab.tsx） | 已连接频道发送测试成功/失败；移除令牌先取消再确认成功/失败 | 测试 success/error，令牌 success/error；取消无请求，反馈走资源 | 暗 |
| T57-45 | 微信二维码（weixin-qrcode-dialog.tsx） | 加载后点图预览；缩放、返回；继续扫码；测试过期/失败/刷新与确认自动关闭 | 共享预览在 sheet 之上；关闭仅关预览，轮询未重启；确认仍关闭整面板，过期自动关闭预览并显示刷新入口；刷新恢复 | 明 |
| T57-46 | 微信二维码（weixin-qrcode-dialog.tsx） | 加载后点图预览；缩放、返回；继续扫码；测试过期/失败/刷新与确认自动关闭 | 共享预览在 sheet 之上；关闭仅关预览，轮询未重启；确认仍关闭整面板，过期自动关闭预览并显示刷新入口；刷新恢复 | 暗 |
| T57-47 | 频道全局（channel-events-tab.tsx / channel-other-tab.tsx） | 改前缀、事件、语言、webhook，移除先取消再确认 | 16dp 图标/Spinner，原校验和目标确认保持 | 明 |
| T57-48 | 频道全局（channel-events-tab.tsx / channel-other-tab.tsx） | 改前缀、事件、语言、webhook，移除先取消再确认 | 16dp 图标/Spinner，原校验和目标确认保持 | 暗 |
| T57-49 | 系统（system-network-settings.tsx） | 保存 URL、检查更新，制造请求失败后重试 | 共享 Spinner，按钮禁用和持续错误可读，无新接口 | 明 |
| T57-50 | 系统（system-network-settings.tsx） | 保存 URL、检查更新，制造请求失败后重试 | 共享 Spinner，按钮禁用和持续错误可读，无新接口 | 暗 |
| T57-51 | 版本控制（version-control-settings.tsx） | 检测 Git、测试/保存路径，观察空账户和默认账户 | GitBranch 导航，Spinner/禁用/状态/账户显示保持 | 明 |
| T57-52 | 版本控制（version-control-settings.tsx） | 检测 Git、测试/保存路径，观察空账户和默认账户 | GitBranch 导航，Spinner/禁用/状态/账户显示保持 | 暗 |
| T57-53 | 关于、许可（settings/page.tsx，移动端专有） | 查看版本并滚动复制许可正文 | Info/Shield 入口及关于插图清晰，复制/滚动/返回不变 | 明 |
| T57-54 | 关于、许可（settings/page.tsx，移动端专有） | 查看版本并滚动复制许可正文 | Info/Shield 入口及关于插图清晰，复制/滚动/返回不变 | 暗 |

对照网页端路径均位于 `WEB/components/settings/`（表内 *page.tsx 位于 WEB/app/settings/）。允许移动端全屏 sheet、触摸目标扩大及原生键盘差异；二维码白底和完整 emoji 为明确保留。额外在最小屏/横屏/最大字体/TalkBack 下复查表单、选择器、折叠、图片预览、Toast 和键盘遮挡；明暗主题都需要实际测量对比度，未在本任务运行。

## 5. 回归点

需复测 T49 的全部 T49-01～62：首页所有入口受图标迁移影响；其中重点：

- T49-11～18：泛型 SelectField / SettingsChoice、Hermes 分类菜单、高级配置草稿及 Cursor 刷新。
- T49-19/20：Kimi 全部临时结果 → Toast；T49-21～24：Pi 配置、安装卸载、信任错误 Toast。
- T49-35～38：市场查询与按 ID 选项；T49-47/48：头像和真正的输入 disabled。
- T49-49～54：频道删除/操作反馈、二维码预览、自动轮询/过期刷新/关闭生命周期。
- T49-39～46、55～60：Spinner 与禁用/错误状态、技能只读/导入、webhook 危险确认。
- 不支持能力的服务器继续按原逻辑隐藏入口；未更改接口或能力探测。

## 6. 已知风险 / 未完成

- 未编译、未跑 JVM/CI、未做真机；所有运行用例待协调方统一执行。静态检查不能替代这些结果。
- 全局 Toast Host 依赖 T58 接入；本范围没有越界挂载 Host。
- 完整 emoji 自定义头像缺少 DS 内容能力，保留原绘制；普通头像色彩改为 DS 默认。需协调方确认此例外；VectorPainter 已显式按主题着色，仍需实测对比度，不能声称完全像素一致。
- CodegSelect 内部行高/触摸目标由设计系统负责（本任务无权修改）；本范围图标按钮/折叠标题已扩大或保留 ≥48dp。协调方真机需确认共享控件的实际触摸扩展区域。
