# T49 测试文档：设置界面对齐

- 分支：`task/T49-ui-settings`；代码与本文同一提交，提交号以 `git log -1 --format=%H -- .taskmaster/docs/tests/T49-ui-settings.md` 为准，最终交付报告列出短 hash。
- 依赖：T43–T46（已合入 sit）；T52/T53 留标记，待第三阶段接入。
- 测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。本文用例均为待执行；本任务未执行 Gradle、CI、adb 或真机测试。

## 1. 测试范围

代码路径前缀 `A/ = app/src/main/java/app/codeg/android/`。以下均在 `A/feature/settings/`，替换清单同时为改动文件清单：

| 文件 | 替换前 | 替换后 |
|---|---|---|
| `SettingsScreen.kt` | ListItem、手写分组/卡片、CodegSegmented | CodegListRow、CodegSectionHeader、CodegCard、CodegTabs |
| `GeneralScreen.kt` | ListItem、Switch、分区标题、FilledTonalIconButton | CodegListRow、CodegSwitch、CodegCardHeader、CodegButton(Secondary/Icon) |
| `LanguageScreen.kt` | ListItem、RadioButton、手写卡片 | CodegCard/Header/Content、CodegRadioGroup |
| `AgentsScreen.kt` | 手写代理列表行、Switch | CodegListRow、CodegSwitch |
| `AgentDetailScreen.kt` | ExposedDropdownMenu、OutlinedTextField、Switch、FilledTonalButton、手写表单分区 | SettingsChoice/CodegSelect、分类 CodegDropdownMenu/MenuLabel、CodegTextField、CodegSwitch、CodegButton、CodegCard/Header/Content |
| `AgentConfigKimiView.kt` | OutlinedTextField、OutlinedButton、TextButton、DropdownMenu、HorizontalDivider | CodegTextField、CodegButton、CodegDropdownMenu/MenuItem、CodegSeparator；选择器经 SelectField 复用 |
| `AgentConfigPiView.kt` | CodegSegmented、Switch、OutlinedButton、HorizontalDivider、直接卸载 | CodegTabs、CodegSwitch、CodegButton、CodegSeparator、CodegConfirmDialog(Pi) |
| `AssistantsScreen.kt` | TextButton、AlertDialog、Switch、OutlinedTextField、手写选择器、Dialog、进度条 | CodegButton、CodegConfirmDialog、CodegSwitch、CodegTextField、SettingsChoice、CodegFullScreenSheet、CodegSkeleton |
| `QuickMessagesScreen.kt` | 手写列表行、DropdownMenu、AlertDialog、TextButton、FAB、直接删除 | CodegListRow、CodegDropdownMenu/MenuItem、CodegDialog、CodegButton、CodegConfirmDialog(消息标题) |
| `ModelProvidersScreen.kt` | 手写列表行、ExposedDropdownMenu、AlertDialog、TextButton、FAB、直接删除 | CodegListRow、SettingsChoice、CodegDialog、CodegButton、CodegConfirmDialog(提供商名) |
| `McpScreen.kt` | 手写列表行、DropdownMenu、TextButton、FAB、手写全屏 Dialog、直接删除 | CodegListRow、CodegDropdownMenu/MenuItem、CodegButton、CodegFullScreenSheet、CodegConfirmDialog(服务器 ID) |
| `mcpmarket/McpMarketSheet.kt` | 手写全屏 Dialog、AlertDialog、TextButton、手写选择器/市场行 | CodegFullScreenSheet、CodegDialog、CodegButton、SettingsChoice/CodegSelect、CodegListRow |
| `ExpertsScreen.kt` | 手写专家行、Dialog、Switch、HorizontalDivider | CodegListRow、CodegFullScreenSheet、CodegSwitch、CodegSeparator、CodegCard |
| `SkillsScreen.kt` | FilterChip、AlertDialog、TextButton、FAB、Dialog、手写技能行/标题 | CodegFilterChip、CodegConfirmDialog、CodegButton、CodegFullScreenSheet、CodegListRow、CodegBadge(Outline)、CodegSectionHeader |
| `customskills/CustomSkillsScreen.kt` | TextButton、AlertDialog、Switch、Checkbox、RadioButton、Dialog、进度条 | CodegButton、CodegConfirmDialog、CodegSwitch、CodegCheckbox、CodegRadioGroup、CodegFullScreenSheet、CodegSkeleton、CodegCard |
| `ChatChannelsScreen.kt` | AlertDialog、Switch、TextButton、FilledTonalButton、FAB、Dialog、手写频道行/分区 | CodegConfirmDialog(频道名/令牌所属频道)、CodegSwitch、CodegButton、CodegFullScreenSheet、CodegListRow、CodegCardHeader、CodegBadge |
| `ChatGlobalSettingsScreen.kt` | Switch、FilledTonalButton、FilledIconButton、Dialog、手写标题、直接移除 webhook | CodegSwitch、CodegButton、CodegFullScreenSheet、CodegCard/Header/Content、CodegConfirmDialog(webhook URL) |
| `SystemScreen.kt` | Switch、Button、手写卡片 | CodegSwitch、CodegButton、CodegCard |
| `VersionControlScreen.kt` | Button、FilledTonalButton、手写卡片 | CodegButton(Default/Secondary)、CodegCard、CodegBadge(Secondary) |
| `SettingsChoice.kt（新增）` | 各页独立按钮加菜单 | 内部适配器：普通选项 CodegSelect；重复显示名用 CodegField + CodegDropdownMenu/MenuItem，维持独立 ID |

新增资源：`app/src/main/res/values/strings_ui_settings.xml`、`app/src/main/res/values-zh-rCN/strings_ui_settings.xml`；新增目标删除说明，中英文键一致。新增测试文档：`.taskmaster/docs/tests/T49-ui-settings.md`。

- 新增公开 API：无。`SettingsChoice` 为 feature 内部组件；原 `FormSection`、泛型 `SelectField` 对调用方签名不变。未新增依赖。
- 页面入口：设置首页全部现有叶页面，代理详情各配置分区，助手/技能/自定义技能/MCP 市场/聊天频道各编辑面板。
- 不在范围内：ViewModel、请求和服务端行为、导航注册、设计系统实现、图标迁移、Toast 接线、code-index。开源许可正文/关于页无待替换控件，保留现有呈现与选择复制。
- 表单保持原回调及校验；危险操作在确认后才调用原回调。规则草稿行删除仍只改本地未保存草稿，不属于持久化删除。

### grep 自查

执行（命中应为零，`rg` 返回 1 表示没有匹配）：

```sh
rg -n '\b(DropdownMenu|DropdownMenuItem|ExposedDropdownMenuBox|ExposedDropdownMenu|AlertDialog|ModalBottomSheet|TextButton|Button|OutlinedButton|FilledTonalButton|OutlinedTextField|Checkbox|Switch|FilterChip|ListItem|RadioButton|HorizontalDivider|LinearProgressIndicator|SegmentedTabs|CodegSegmented|TabRow)\s*\(' app/src/main/java/app/codeg/android/feature/settings
```

结果：零命中。另做静态参数核对：218 处共享组件调用未发现未知命名参数；两份 XML 可解析、import 无重复、`git diff --check` 通过。这些检查不代表 Kotlin 编译或运行通过。

保留说明：

- `IconButton`/`CodegIconButton`、Text、Icon、Scaffold 和圆形加载指示器不在本轮指定替换清单内；图标按第三阶段处理。
- 外观的主题色圆形色样为专用颜色选择器，保留色样与选中边框；不把主题色变成普通文本选项。
- 助手头像、二维码、折叠、搜索框见 TODO；二维码白底/黑图是扫码对比要求，维持原有图片颜色。
- 助手卡片的排序/编辑/删除工具区、自定义技能每个代理的关联状态行、只读账户/版本信息、频道详细配置表单行是复合内容，使用共享卡片和控件保留各字段及操作。
- Hermes 提供商保留 API key/OAuth/AWS 分类菜单，用 CodegField + CodegDropdownMenu/MenuLabel；重复显示名选择器也保留基于 ID 的共享菜单，避免按显示名回写错误选项。

### TODO(T5x) 清单

- `AgentConfigKimiView.kt:196`：TODO(T52): CodegToaster。
- `AgentConfigPiView.kt:406`：TODO(T52): CodegToaster。
- `AgentDetailScreen.kt:905`：TODO(T53): CodegCollapsible。
- `AgentDetailScreen.kt:930`：TODO(T53): CodegCollapsible。
- `AssistantsScreen.kt:62`：TODO(T53): CodegAvatar。
- `ChatChannelsScreen.kt:124`：TODO(T53): CodegAvatar。
- `ChatChannelsScreen.kt:416`：TODO(T52): CodegToaster。
- `ChatChannelsScreen.kt:468`：TODO(T53): CodegImagePreviewDialog; retain QR polling lifecycle.。
- `mcpmarket/McpMarketSheet.kt:42`：TODO(T53): CodegInputGroup。

## 2. 编译检查

自评编译风险：中等。修改面较大，未编译；调用均使用现有 T43–T46 API，未引入新依赖。协调方执行 `./gradlew :app:check` 时关注：

- `SettingsChoice.kt:17`：CodegSelect 的 String 标签与业务 ID 映射；重复显示名回退菜单保持 ID；internal 跨子包访问同模块有效。
- `AgentDetailScreen.kt:863`：泛型 T（含可空 Int）到选项索引的映射及回调类型推断；heightIn、Box 和菜单组件 import 完整。
- `AssistantsScreen.kt:178`：CodegTextField 不提供 enabled 参数，使用原 enabled 条件保护 onChange；需真机确认忙碌期间内容不变。
- `customskills/CustomSkillsScreen.kt:76`：共享面板滚动中 LazyColumn 使用屏高 65% 的有限最大高度，避免无限高度异常；长列表内外滚动及键盘需实测。
- `mcpmarket/McpMarketSheet.kt:33`：LocalConfiguration、Dp 乘 Float 与 heightIn 使用现有 Compose API；市场列表同样有界。
- `ChatChannelsScreen.kt:505`：二维码区域为屏高 75% 的有限高度，保留 weight 居中；小屏/横屏仍需验证扫码内容及说明可滚动到达。
- `AgentConfigPiView.kt:408`：卸载确认的 AgentType.PI 与已用枚举一致，确认时再次检查 piOp。
- `ChatGlobalSettingsScreen.kt:134`：Pair<Int,String> 目标快照与 getOrNull 防越界；确认期间列表变化不应误删其它 URL。

- 全屏面板内容是 ColumnScope；动作区是 RowScope。迁移后的编辑内容不再自带 verticalScroll/纵向 weight，避免重复滚动；未在普通回调中调用 Composable。
- 保留原有 `@OptIn`，新增控件不需增加实验性 API。字符串新文件名与资源名满足 Android 要求；中英键对称。
- 重点 lint：未使用 import/变量（不应阻断编译，统一检查仍需关注）、无障碍标签、内容描述、窄屏内容溢出。项目原有 Material 图标和状态色不在本轮替换。

## 3. 单元测试

本轮仅替换 UI 组件与确认交互，不修改纯业务逻辑，未新增 JVM 单测；以下为协调方应运行的现有回归测试，均未在本任务执行：

| 测试类::方法 | 覆盖点 |
|---|---|
| AgentsViewModelTest::`toggle uses the live row even when its provider id is null` / `toggle keeps a live provider link` / `toggle falls back to the captured snapshot when no live row exists` | 启停代理保持实时状态及 provider 关联 |
| AgentsViewModelTest::`grok dropdown save patches the fresh config, preserving edits since the panel opened` / `grok api-key-only save writes nothing to config` / `grok raw edit is sent verbatim and ignores the fresh base` | Grok 选择器与原始配置保存路径 |
| AgentsViewModelTest::`cursor sends the raw cli-config only when the user edited it` / `a raw cursor edit suppresses the structured patch so it can't overwrite it` / `cursor structured patch omits an unset sandbox and drops blank rules` | Cursor 原始/结构化配置及权限规则 |
| CustomSkillPolicyTest::`foreign links and real directories cannot be overwritten` / `import excludes project read only and already stored skills` | 关联禁用态和导入筛选 |
| CustomSkillWireTest::`link requests use the shared expert ID and camel case agent key` / `duplicate request preserves source and new ID` / `camel case statuses preserve the owning skill and agent` / `partial import results distinguish skipped and failed entries` | 自定义技能关联、复制和导入结果 |
| McpMarketParametersTest::preservesTypesAndSecrets / rejectsInvalidRequiredAndTypedValues / usesDefaultsAndOmitsBlankOptionalValues | 市场参数类型、必填、密钥、默认值 |

## 4. 测试用例

前置条件：协调方在 sit 合并 T43–T46 与 T49 后生成 APK；连接官方原版 codeg 0.38 或提供对应能力的 T13Test，准备可丢弃数据：两条快捷消息、两个提供商（含同名）、两个 MCP、本地/只读技能、自定义技能与不同关联状态、专家、助手、Telegram/飞书/微信频道、两个 webhook。代理机器准备已安装与不可用代理。删除/卸载使用可恢复测试目标。

每一行均独立从入口执行；先设置对应明/暗主题，再执行步骤。中英文至少各完整走一遍；检查文本、焦点/点击、边框、菜单、禁用态、键盘、滚动和返回。服务器写操作之后重开页面确认持久化；仅取消操作不应发生写请求。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T49-01 | 设置 → 设置首页 | 设为明色；依次打开各分组叶页面，再按系统返回；用不支持助手/MCP 市场的服务器复查入口 | 分组卡片/共享列表行一致；进入/返回正确；已有能力隐藏规则保持 | 明 |
| T49-02 | 设置 → 设置首页 | 设为暗色；依次打开各分组叶页面，再按系统返回；用不支持助手/MCP 市场的服务器复查入口 | 分组卡片/共享列表行一致；进入/返回正确；已有能力隐藏规则保持 | 暗 |
| T49-03 | 设置 → 外观 | 设为明色；切换跟随系统/浅色/深色，逐个点主题色，重开页面 | CodegTabs 选中态与持久化一致；色样选中环可见；预览颜色同步 | 明 |
| T49-04 | 设置 → 外观 | 设为暗色；切换跟随系统/浅色/深色，逐个点主题色，重开页面 | CodegTabs 选中态与持久化一致；色样选中环可见；预览颜色同步 | 暗 |
| T49-05 | 设置 → 语言 | 设为明色；从当前语言切到 English，再切中文与跟随系统 | 单选组正确；Activity 重建后资源和选择保持；返回可用 | 明 |
| T49-06 | 设置 → 语言 | 设为暗色；从当前语言切到 English，再切中文与跟随系统 | 单选组正确；Activity 重建后资源和选择保持；返回可用 | 暗 |
| T49-07 | 设置 → 常规 | 设为明色；切换实时反馈/提问/委派；启用委派后增减深度，再关闭 | 左标签右开关/步进控件；子项显隐、深度限制与原行为一致 | 明 |
| T49-08 | 设置 → 常规 | 设为暗色；切换实时反馈/提问/委派；启用委派后增减深度，再关闭 | 左标签右开关/步进控件；子项显隐、深度限制与原行为一致 | 暗 |
| T49-09 | 设置 → 代理列表 | 设为明色；点击代理正文打开详情；点击可用代理开关；观察不可用代理 | 正文与开关不串触；可用态/版本描述保留；不可用开关禁用 | 明 |
| T49-10 | 设置 → 代理列表 | 设为暗色；点击代理正文打开详情；点击可用代理开关；观察不可用代理 | 正文与开关不串触；可用态/版本描述保留；不可用开关禁用 | 暗 |
| T49-11 | 设置 → 代理详情/通用配置 | 设为明色；逐一打开 Claude/Codex/Gemini/Cline/OpenCode/Hermes/Grok/Cursor；切换模式、提供商、模型，修改一项保存后重开 | 分组卡片、输入/选择/开关统一；原字段/条件分支保留；保存只作用于所选代理 | 明 |
| T49-12 | 设置 → 代理详情/通用配置 | 设为暗色；逐一打开 Claude/Codex/Gemini/Cline/OpenCode/Hermes/Grok/Cursor；切换模式、提供商、模型，修改一项保存后重开 | 分组卡片、输入/选择/开关统一；原字段/条件分支保留；保存只作用于所选代理 | 暗 |
| T49-13 | 设置 → 代理详情/Hermes | 设为明色；打开提供商菜单；跨 API key/OAuth/AWS 分类选项，保存 | 分类标题存在；标签与 ID 对应；各认证类型字段及原校验保持 | 明 |
| T49-14 | 设置 → 代理详情/Hermes | 设为暗色；打开提供商菜单；跨 API key/OAuth/AWS 分类选项，保存 | 分类标题存在；标签与 ID 对应；各认证类型字段及原校验保持 | 暗 |
| T49-15 | 设置 → 代理详情/Cursor 权限与原始配置 | 设为明色；新增两条规则、编辑/删一条；展开原始配置输入多行后保存 | 规则草稿可编辑；删除草稿不立即写服务端；原始配置、结构化补丁优先级保持 | 明 |
| T49-16 | 设置 → 代理详情/Cursor 权限与原始配置 | 设为暗色；新增两条规则、编辑/删一条；展开原始配置输入多行后保存 | 规则草稿可编辑；删除草稿不立即写服务端；原始配置、结构化补丁优先级保持 | 暗 |
| T49-17 | 设置 → 代理详情/服务器管理面板 | 设为明色；分别打开 Antigravity、Command Code、DeepSeek、Kiro、Qoder、Trae、ZCode 配置 | 各子面板经共享 FormSection 得到一致卡片；原有只读说明/支持提示和按钮保持 | 明 |
| T49-18 | 设置 → 代理详情/服务器管理面板 | 设为暗色；分别打开 Antigravity、Command Code、DeepSeek、Kiro、Qoder、Trae、ZCode 配置 | 各子面板经共享 FormSection 得到一致卡片；原有只读说明/支持提示和按钮保持 | 暗 |
| T49-19 | 设置 → 代理详情/Kimi | 设为明色；切换登录/API 模式、接口/区域/认证；取模型并选择；展开 raw 保存 | 选择器不改 wire 值；取模型/保存禁用状态保持；多行编辑、结果提示可见 | 明 |
| T49-20 | 设置 → 代理详情/Kimi | 设为暗色；切换登录/API 模式、接口/区域/认证；取模型并选择；展开 raw 保存 | 选择器不改 wire 值；取模型/保存禁用状态保持；多行编辑、结果提示可见 | 暗 |
| T49-21 | 设置 → 代理详情/Pi | 设为明色；切换默认/自定义运行时；验证命令、选择 provider/thinking、保存；切信任工作区 | tabs、开关、选择器及按钮一致；字段显隐与校验、信任写入保持 | 明 |
| T49-22 | 设置 → 代理详情/Pi | 设为暗色；切换默认/自定义运行时；验证命令、选择 provider/thinking、保存；切信任工作区 | tabs、开关、选择器及按钮一致；字段显隐与校验、信任写入保持 | 暗 |
| T49-23 | 设置 → 代理详情/Pi 卸载 | 设为明色；已安装测试 Pi 上点卸载，先取消，再重开确认并执行 | 确认框明确 Pi 目标；取消不卸载；确认只触发一次原卸载并显示结果 | 明 |
| T49-24 | 设置 → 代理详情/Pi 卸载 | 设为暗色；已安装测试 Pi 上点卸载，先取消，再重开确认并执行 | 确认框明确 Pi 目标；取消不卸载；确认只触发一次原卸载并显示结果 | 暗 |
| T49-25 | 设置 → 快捷消息 | 设为明色；创建空白草稿检查保存禁用；填写标题和多行内容保存，点正文编辑 | 表单/菜单样式统一；保存条件保持；键盘不挡操作，重开数据正确 | 明 |
| T49-26 | 设置 → 快捷消息 | 设为暗色；创建空白草稿检查保存禁用；填写标题和多行内容保存，点正文编辑 | 表单/菜单样式统一；保存条件保持；键盘不挡操作，重开数据正确 | 暗 |
| T49-27 | 设置 → 快捷消息/删除 | 设为明色；点更多或长按目标，删除先取消，再确认 | 更多与长按等价；确认展示消息标题；取消保留，确认删除目标 | 明 |
| T49-28 | 设置 → 快捷消息/删除 | 设为暗色；点更多或长按目标，删除先取消，再确认 | 更多与长按等价；确认展示消息标题；取消保留，确认删除目标 | 暗 |
| T49-29 | 设置 → 模型提供商 | 设为明色；新建名称/URL/密钥/代理/可选模型；编辑时留空密钥，换代理后保存 | 选择显示名映射到原代理；名称/URL 必填；留空密钥保持原语义 | 明 |
| T49-30 | 设置 → 模型提供商 | 设为暗色；新建名称/URL/密钥/代理/可选模型；编辑时留空密钥，换代理后保存 | 选择显示名映射到原代理；名称/URL 必填；留空密钥保持原语义 | 暗 |
| T49-31 | 设置 → 模型提供商/删除与同名 | 设为明色；选两条同名提供商分别编辑、删除先取消再确认 | 回调仍区分 ID；确认展示名称；取消不改，确认删除所选 ID | 明 |
| T49-32 | 设置 → 模型提供商/删除与同名 | 设为暗色；选两条同名提供商分别编辑、删除先取消再确认 | 回调仍区分 ID；确认展示名称；取消不改，确认删除所选 ID | 暗 |
| T49-33 | 设置 → MCP 本地 | 设为明色；新建 ID 与 JSON，选择多个代理；输入无效 JSON保存，再纠正；删除先取消再确认 | 全屏表单滚动和键盘正常；错误保留；成功保存原 spec/apps；确认展示 ID | 明 |
| T49-34 | 设置 → MCP 本地 | 设为暗色；新建 ID 与 JSON，选择多个代理；输入无效 JSON保存，再纠正；删除先取消再确认 | 全屏表单滚动和键盘正常；错误保留；成功保存原 spec/apps；确认展示 ID | 暗 |
| T49-35 | 设置 → MCP 市场/列表 | 设为明色；切换源、输入搜索、打开详情、返回、加载更多；超过一屏列表内外滚动 | 选择器/列表行可用；搜索/分页保持；有限列表高度且不抛无限高度异常 | 明 |
| T49-36 | 设置 → MCP 市场/列表 | 设为暗色；切换源、输入搜索、打开详情、返回、加载更多；超过一屏列表内外滚动 | 选择器/列表行可用；搜索/分页保持；有限列表高度且不抛无限高度异常 | 暗 |
| T49-37 | 设置 → MCP 市场/安装 | 设为明色；切安装方式；填必填/密钥/枚举/布尔/JSON；选代理；安装先取消再确认 | 参数类型校验保持；安装确认显示服务器名；忙碌禁用/阻止关闭；成功刷新本地列表 | 明 |
| T49-38 | 设置 → MCP 市场/安装 | 设为暗色；切安装方式；填必填/密钥/枚举/布尔/JSON；选代理；安装先取消再确认 | 参数类型校验保持；安装确认显示服务器名；忙碌禁用/阻止关闭；成功刷新本地列表 | 暗 |
| T49-39 | 设置 → 专家 | 设为明色；打开专家，滚动说明/Markdown；切换可关联代理；观察外链/真实目录/损坏状态 | 共享行和全屏面板；阻塞状态开关禁用；原关联/取消关联与内容预览保持 | 明 |
| T49-40 | 设置 → 专家 | 设为暗色；打开专家，滚动说明/Markdown；切换可关联代理；观察外链/真实目录/损坏状态 | 共享行和全屏面板；阻塞状态开关禁用；原关联/取消关联与内容预览保持 | 暗 |
| T49-41 | 设置 → 技能 | 设为明色；切代理、打开项目/全局/只读技能；新增与修改可写技能；长按/更多删除先取消再确认 | 筛选与标题计数正确；只读标记且无删除入口；确认展示 ID；作用域/布局保持 | 明 |
| T49-42 | 设置 → 技能 | 设为暗色；切代理、打开项目/全局/只读技能；新增与修改可写技能；长按/更多删除先取消再确认 | 筛选与标题计数正确；只读标记且无删除入口；确认展示 ID；作用域/布局保持 | 暗 |
| T49-43 | 设置 → 自定义技能库/编辑 | 设为明色；打开库，创建/编辑/复制；触发加载失败后重试；删除先取消再确认 | 共享卡片、有限列表、编辑面板正常；ID/正文校验、重试、复制语义保持；确认展示 ID | 明 |
| T49-44 | 设置 → 自定义技能库/编辑 | 设为暗色；打开库，创建/编辑/复制；触发加载失败后重试；删除先取消再确认 | 共享卡片、有限列表、编辑面板正常；ID/正文校验、重试、复制语义保持；确认展示 ID | 暗 |
| T49-45 | 设置 → 自定义技能库/导入及关联 | 设为明色；切导入来源代理，勾多项导入；切换每代理关联；观察冲突状态 | 单选与复选状态正确；切来源清空旧选择；计数/部分失败显示保持；冲突不可覆盖 | 明 |
| T49-46 | 设置 → 自定义技能库/导入及关联 | 设为暗色；切导入来源代理，勾多项导入；切换每代理关联；观察冲突状态 | 单选与复选状态正确；切来源清空旧选择；计数/部分失败显示保持；冲突不可覆盖 | 暗 |
| T49-47 | 设置 → 助手列表/编辑 | 设为明色；新建名称、描述、头像、代理/模式/选项/指令；保存；排序并编辑；保存期间尝试输入/返回；删除先取消再确认 | 原长度校验、快照保留和探测重试保持；忙碌时草稿不变/关闭被阻止；确认展示助手名 | 明 |
| T49-48 | 设置 → 助手列表/编辑 | 设为暗色；新建名称、描述、头像、代理/模式/选项/指令；保存；排序并编辑；保存期间尝试输入/返回；删除先取消再确认 | 原长度校验、快照保留和探测重试保持；忙碌时草稿不变/关闭被阻止；确认展示助手名 | 暗 |
| T49-49 | 设置 → 聊天频道/列表编辑 | 设为明色；逐一新增 Telegram/飞书/微信；结构化/raw 切换；编辑并开关启用/日报；删除先取消再确认 | 共享行/面板/按钮；原字段、校验及 raw 转换保持；确认展示频道名；开关不串触 | 明 |
| T49-50 | 设置 → 聊天频道/列表编辑 | 设为暗色；逐一新增 Telegram/飞书/微信；结构化/raw 切换；编辑并开关启用/日报；删除先取消再确认 | 共享行/面板/按钮；原字段、校验及 raw 转换保持；确认展示频道名；开关不串触 | 暗 |
| T49-51 | 设置 → 聊天频道/详情与令牌 | 设为明色；打开详情各区；重连/测试（有能力时）；移除令牌先取消再确认 | 状态/配置/消息保留；确认展示所属频道；取消保留，确认调用原移除接口；错误对话框可关闭 | 明 |
| T49-52 | 设置 → 聊天频道/详情与令牌 | 设为暗色；打开详情各区；重连/测试（有能力时）；移除令牌先取消再确认 | 状态/配置/消息保留；确认展示所属频道；取消保留，确认调用原移除接口；错误对话框可关闭 | 暗 |
| T49-53 | 设置 → 聊天频道/微信扫码 | 设为明色；打开 QR，在加载/扫码/过期/重试/连接成功状态分别观察；返回后重开 | 二维码白底清晰、有限区域居中；小屏可滚动说明；原轮询/过期/关闭生命周期保持 | 明 |
| T49-54 | 设置 → 聊天频道/微信扫码 | 设为暗色；打开 QR，在加载/扫码/过期/重试/连接成功状态分别观察；返回后重开 | 二维码白底清晰、有限区域居中；小屏可滚动说明；原轮询/过期/关闭生命周期保持 | 暗 |
| T49-55 | 设置 → 聊天频道/全局设置 | 设为明色；改前缀与回复语言；切事件、webhook 开关；添加 webhook；移除先取消再确认 | 左标签右开关；原设置持久化；移除框展示 URL；取消不改，确认不误删其它 URL | 明 |
| T49-56 | 设置 → 聊天频道/全局设置 | 设为暗色；改前缀与回复语言；切事件、webhook 开关；添加 webhook；移除先取消再确认 | 左标签右开关；原设置持久化；移除框展示 URL；取消不改，确认不误删其它 URL | 暗 |
| T49-57 | 设置 → 系统 | 设为明色；启用/关闭代理、改 URL 并保存；检查更新；模拟请求失败后重试 | 共享卡片/按钮/开关；原字段显隐、busy/结果/错误均保留 | 明 |
| T49-58 | 设置 → 系统 | 设为暗色；启用/关闭代理、改 URL 并保存；检查更新；模拟请求失败后重试 | 共享卡片/按钮/开关；原字段显隐、busy/结果/错误均保留 | 暗 |
| T49-59 | 设置 → 版本控制 | 设为明色；检查 Git 状态；改路径先测试再保存；查看空账户/默认账户 | 按钮主次与禁用态正确；状态、路径和版本可读；保存/测试结果及默认标记保持 | 明 |
| T49-60 | 设置 → 版本控制 | 设为暗色；检查 Git 状态；改路径先测试再保存；查看空账户/默认账户 | 按钮主次与禁用态正确；状态、路径和版本可读；保存/测试结果及默认标记保持 | 暗 |
| T49-61 | 设置 → 关于与开源许可 | 设为明色；从首页打开关于查看版本，再打开许可滚动并长按复制正文 | 入口/返回、应用与服务器版本、更新入口以及许可分块选择复制不受影响 | 明 |
| T49-62 | 设置 → 关于与开源许可 | 设为暗色；从首页打开关于查看版本，再打开许可滚动并长按复制正文 | 入口/返回、应用与服务器版本、更新入口以及许可分块选择复制不受影响 | 暗 |

对照网页端（路径均相对 `WEB/`；文件名以下均精确指向目录）：

- T49-01/02：app/settings/*/page.tsx；components/settings/settings-shell.tsx。
- T49-03/04：app/settings/appearance/page.tsx；components/settings/appearance-settings.tsx。
- T49-05/06：app/settings/general/page.tsx；本地 App 语言为移动端差异。
- T49-07/08：app/settings/general/page.tsx；components/settings/general-settings.tsx。
- T49-09/10：app/settings/agents/page.tsx；components/settings/acp-agent-settings.tsx。
- T49-11/12：app/settings/agents/page.tsx；components/settings/acp-agent-settings.tsx 及 cursor-config-panel.tsx。
- T49-13/14：app/settings/agents/page.tsx；components/settings/acp-agent-settings.tsx。
- T49-15/16：app/settings/agents/page.tsx；components/settings/cursor-config-panel.tsx。
- T49-17/18：app/settings/agents/page.tsx；components/settings/对应 *-config-panel.tsx。
- T49-19/20：app/settings/agents/page.tsx；components/settings/kimi-code-config-panel.tsx。
- T49-21/22：app/settings/agents/page.tsx；components/settings/pi-config-panel.tsx。
- T49-23/24：app/settings/agents/page.tsx；components/settings/pi-config-panel.tsx。
- T49-25/26：app/settings/quick-messages/page.tsx；components/settings/quick-messages-settings.tsx。
- T49-27/28：app/settings/quick-messages/page.tsx；components/settings/quick-messages-settings.tsx。
- T49-29/30：app/settings/model-providers/page.tsx；components/settings/add-model-provider-dialog.tsx、components/settings/edit-model-provider-dialog.tsx。
- T49-31/32：app/settings/model-providers/page.tsx；components/settings/model-provider-settings.tsx。
- T49-33/34：app/settings/mcp/page.tsx；components/settings/mcp-settings.tsx。
- T49-35/36：app/settings/mcp/page.tsx；components/settings/mcp-settings.tsx market 面板。
- T49-37/38：app/settings/mcp/page.tsx；components/settings/mcp-settings.tsx 安装对话框。
- T49-39/40：app/settings/experts/page.tsx；components/settings/experts-settings.tsx。
- T49-41/42：app/settings/skills/page.tsx；components/settings/skills-settings.tsx。
- T49-43/44：app/settings/skills/page.tsx；components/settings/custom-skills-settings.tsx。
- T49-45/46：app/settings/skills/page.tsx；components/settings/custom-skills-settings.tsx。
- T49-47/48：app/settings/agents/page.tsx；components/settings/assistants-panel.tsx；components/settings/assistant-editor-dialog.tsx。
- T49-49/50：app/settings/chat-channels/page.tsx；components/settings/chat-channel-settings.tsx；components/settings/add-chat-channel-dialog.tsx、components/settings/edit-chat-channel-dialog.tsx。
- T49-51/52：app/settings/chat-channels/page.tsx；components/settings/channel-list-tab.tsx。
- T49-53/54：app/settings/chat-channels/page.tsx；components/settings/weixin-qrcode-dialog.tsx。
- T49-55/56：app/settings/chat-channels/page.tsx；components/settings/channel-events-tab.tsx；components/settings/channel-other-tab.tsx。
- T49-57/58：app/settings/system/page.tsx；components/settings/system-network-settings.tsx。
- T49-59/60：app/settings/version-control/page.tsx；components/settings/version-control-settings.tsx。
- T49-61/62：app/settings/page.tsx；许可/应用版本为移动端页面。

允许差异：窄屏使用单列/全屏编辑面板，桌面双列不照搬；较长列表保留惰性滚动；应用语言/许可为移动端能力；既有 Material 图标和 Toast/头像/折叠在第三阶段处理。不允许差异：业务选项 ID、请求字段、禁用条件、危险操作目标不明确、文字含义改变。

## 5. 回归点

- 每次取消删除/卸载/令牌移除/webhook 移除均验证服务端数据不变；确认只针对展示目标；从更多与长按进入结果一致。
- 代理启停保持 live row 的 provider 关联；所有泛型选项（枚举、String、nullable Int）以及重复标签选项选择后回写正确 ID。
- 各代理结构化/raw 配置保存、Kimi 获取模型/认证字段显隐、Pi 运行时/信任环境变量不变；未改网络调用。
- 提供商空密钥编辑行为、快捷消息空字段校验、MCP JSON 错误与 apps、技能只读/作用域、专家冲突保护、自定义技能部分导入结果保持。
- 助手长度限制/原快照/代理配置探测/排序边界/忙碌保护均验证；异步编辑错误不丢失草稿。
- 频道类型切换、raw 转换、连接测试、密钥、日报、QR 轮询，前缀/回复语言/事件/webhook 设置逐项验证。
- 设置能力探测隐藏（官方 0.38 不支持时）、代理/市场不可用与重试、空数据/刷新错误/长名称/超长表单；明暗+中英文无文字消失或横向溢出。
- 所有编辑面板：输入法显示时可滚到最后字段与操作按钮，返回/关闭路径正确；嵌套惰性列表内外滑动无异常；旋转及小屏 QR 可见。
- 外观和语言持久化、关于/许可入口、Git 路径测试保存、网络代理保存和更新检查均保持原功能。

## 6. 已知风险 / 未完成

- 未运行编译、lint、JVM 单测或真机；上述用例均待协调方统一执行。本报告不宣称 APK 可编译或真机验收通过。
- TODO(T52)/TODO(T53) 为任务要求的阶段性交接，见完整清单。未替换图标、未接 Toast、未改设计系统或服务端。
- CodegTextField 当前没有 enabled/readOnly 参数：助手保存期间通过回调保护阻止改值，但外观仍为输入框，焦点和软键盘仍可能出现；交互需统一真机确认。
- CodegFullScreenSheet 自带滚动；市场/自定义技能列表显式限制 65% 屏高以保持惰性布局。小屏、横屏、长文本与输入法组合为主要视觉风险。
- CodegListRow 标题/副标题各为单行省略，长描述需进入详情查看；与原部分列表两行预览存在允许的紧凑展示差异。
- 无需新增设计系统组件；无范围外改动。禁止事项均未执行：CI、gh run watch、本地 Gradle、gen_code_index.py、code-index 提交、adb、PR/merge。
