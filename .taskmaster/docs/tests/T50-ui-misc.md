# T50 测试文档：工作任务、自动化、日志、动态、搜索、服务器与主框架 UI 对齐

- 分支：`task/T50-ui-misc`　提交：代码与本文同一提交，以 `git log -1 --format=%H -- .taskmaster/docs/tests/T50-ui-misc.md` 查询（避免自引用 hash）。
- 依赖：T43–T46（基于 sit）　测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 执行状态：仅完成静态复核；本文真机用例、Gradle、lint、JVM 单测均未执行。未触发 CI、未操作 adb、未生成或提交 code-index。

## 1. 测试范围

路径前缀 `A/` 为 `app/src/main/java/app/codeg/android/`。下表逐项列出全部代码改动文件。

| 改动文件 | 替换前 → 替换后 |
|---|---|
| A/feature/worktasks/WorkTaskScreen.kt | TextButton、Checkbox、TabRow/Tab、列表标题/状态/分隔线 → CodegButton、CodegCheckbox、CodegTabs、CodegListRow/CodegBadge/CodegSectionHeader/CodegSeparator |
| A/feature/worktasks/WorkTaskControls.kt | TextButton、菜单、AlertDialog、反馈/调度输入 → CodegButton、CodegDropdownMenu/CodegMenuItem、CodegDialog、CodegTextField；保留任务标题与 ID 确认文案 |
| A/feature/worktasks/WorkTaskTemplates.kt | 管理菜单、模板列表、AlertDialog、按钮 → CodegDropdownMenu/CodegMenuItem、CodegListRow、CodegDialog、CodegButton；点模板行进入编辑 |
| A/feature/worktasks/WorkTaskSettingsSheet.kt | AlertDialog、菜单、Switch、按钮、分区标题 → CodegDialog、CodegDropdownMenu/CodegMenuItem、CodegSwitch、CodegButton、CodegSectionHeader |
| A/feature/automations/AutomationScreen.kt | AlertDialog、TextButton、Switch、CodegSegmented、手写列表/卡片/标题、运行状态/分隔线 → CodegDialog、CodegButton、CodegSwitch、CodegTabs、CodegListRow/CodegCard/CodegSectionHeader、CodegBadge/CodegSeparator |
| A/feature/automations/AutomationEditorSheet.kt | ModalBottomSheet、表单、Switch、按钮与菜单 → CodegSheet（固定保存 footer）、CodegTextField、CodegSwitch、CodegButton、CodegDropdownMenu/CodegMenuItem |
| A/feature/serverlogs/ServerLogsScreen.kt | 顶部筛选、FilterChip、查询输入、文件行、分区标题 → CodegTabs、CodegFilterChip、CodegTextField、CodegListRow、CodegSectionHeader |
| A/feature/activity/ActivityScreen.kt | CollapsibleSectionHeader → CodegSectionHeader；保留原图标、计数、折叠状态与点击回调 |
| A/feature/search/SearchScreen.kt | 最近搜索标题、清空按钮、历史行、分隔线 → CodegSectionHeader、CodegButton、CodegListRow、CodegSeparator |
| A/feature/server/ServerListScreen.kt | AlertDialog、手写服务器行、更多按钮与菜单 → CodegConfirmDialog（目标名称、危险样式）、CodegListRow、CodegRowMoreButton、CodegDropdownMenu/CodegMenuItem |
| A/feature/server/ServerEditorScreen.kt | 手写卡片/分区标题/分隔线 → CodegCard/CodegCardContent、CodegSectionHeader、CodegSeparator；沿用已有 CodegTextField/SecretField/PrimaryButton |
| A/feature/onboarding/OnboardingScreen.kt | 手写功能面板 → CodegCard/CodegCardContent；沿用 PrimaryButton |
| A/feature/main/MainShell.kt | NavigationRailItem/NavigationBarItem 与分隔线 → CodegListRow、CodegButton（选中 Secondary、其余 Ghost）、CodegSeparator；保留图标与路由回调 |
| A/core/update/UpdateBanner.kt | TextButton、AlertDialog、确定下载进度 → CodegButton、CodegDialog、CodegProgress |

- 文档文件：`.taskmaster/docs/tests/T50-ui-misc.md`。
- 新增/变更的公开 API：无；未改 ViewModel、网络、模型、状态推导或业务接口；无新增依赖/字符串。
- 受影响入口：工作任务及其管理弹窗、自动化及编辑抽屉、日志与统计、动态、全局搜索、服务器列表/编辑、首次启动、主导航、应用更新。
- 明确不在范围内：共享 designsystem、其它 feature、服务端、图标库迁移、Toast 接线、代码索引、任务状态、CI 配置。

### grep 自查与例外

在上述九个目录执行以下静态搜索（只匹配完整名称的调用，避免将 CodegButton 误计为 Button）：

```bash
rg -n '\b(DropdownMenu|DropdownMenuItem|AlertDialog|ModalBottomSheet|TextButton|Button|OutlinedButton|OutlinedTextField|Checkbox|Switch|FilterChip|TabRow|SegmentedTabs|HorizontalDivider|LinearProgressIndicator)\s*\(' app/src/main/java/app/codeg/android/feature/{worktasks,automations,serverlogs,activity,search,server,onboarding,main} app/src/main/java/app/codeg/android/core/update
```

结果：DropdownMenu、DropdownMenuItem、AlertDialog、ModalBottomSheet、TextButton、Button、OutlinedButton、Checkbox、Switch、FilterChip、TabRow、SegmentedTabs、HorizontalDivider 调用均为 **0**。剩余 **7 个 OutlinedTextField 调用位置、6 个不确定 LinearProgressIndicator 调用位置**，有以下接口缺口，已向协调方回报，未扩大改动范围：

| 保留位置 | 数量 | 原因 |
|---|---:|---|
| WorkTaskControls.kt：任务标题、描述 | 2 | CodegTextField 没有 enabled；保留 busy 禁用行为 |
| WorkTaskTemplates.kt：名称、标题、描述 | 3 | 同上；已有模板名称还必须只读 |
| WorkTaskSettingsSheet.kt：动态设置文本字段 | 1 | 同上；保留 loading/busy 禁用、校验和错误说明 |
| SearchScreen.kt：全局搜索框 | 1 | 共享字段缺少 keyboardActions/IME、leading/trailing slot；保留搜索提交、清空、进行中指示，等待 T53 输入组 |
| WorkTaskScreen.kt、WorkTaskTemplates.kt、WorkTaskSettingsSheet.kt、AutomationEditorSheet.kt、ServerLogsScreen.kt、UpdateBanner.kt | 各 1 | CodegProgress 只有确定 progress 参数，不能用虚构百分比替代不确定进度；已知下载百分比已替换 |

危险确认例外：工作任务动作/模板删除/设置重置、自动化删除/取消运行使用 CodegDialog + 保留 enabled 条件的 footer，目标仍显示在文案中。CodegConfirmDialog 没有 enabled，也没有输入区；强行替换会丢失忙碌禁用和反馈/时间校验。服务器删除不依赖这些能力，已用 CodegConfirmDialog。工作任务纯危险动作确认的 footer 后续还需统一为确认组件的危险样式。

选择器例外：项目、模板、代理等沿用按实体绑定回调的菜单，并已替换菜单原件；CodegSelect 仅提供字符串值，无法区分同名项目/代理，未改成按名称查找 ID。设置的合并策略菜单也已迁移为设计系统菜单，但尚未换 CodegSelect。统计数值行、详情键值行、事件时间线保留其展示结构；它们不是可点击的 sidebar 列表项。

### TODO(T5x) 清单

- `SearchScreen.kt`：`TODO(T53): CodegInputGroup`，全局搜索输入组。
- `ActivityScreen.kt`：`TODO(T53): CodegCollapsible`，分区内容折叠。
- `ServerEditorScreen.kt`：`TODO(T52): CodegToaster`，测试连接反馈。
- `UpdateBanner.kt`：两处 `TODO(T52): CodegToaster`，下载失败、手动检查更新反馈。
- 图标与共享组件内部的 Toast/折叠实现留给第三阶段，本任务未新增图标依赖。

## 2. 编译检查

自评风险：**中等，未编译**。已按共享组件当前签名核对参数；仍需统一编译和真机确认。

| 文件:定位 | 编译/运行关注点 |
|---|---|
| MainShell.kt:373、388 | RowScope.weight、selected/role 语义扩展、系统导航栏 inset；底栏窄屏英文可能省略，宽屏 rail 现为 160dp |
| ActivityScreen.kt:141 | RowScope 中调用 CodegSectionHeader，保留 LazyItemScope.animateItem；确认 Compose DSL 接收者解析和折叠动画 |
| AutomationEditorSheet.kt:56 | CodegSheet 的 ColumnScope footer 捕获编辑状态与保存条件；键盘弹出后 footer 不遮挡；多行字段 singleLine=false |
| WorkTaskControls.kt:66、111；WorkTaskTemplates.kt:48；WorkTaskSettingsSheet.kt:76 | CodegDialog 的 ColumnScope 内容和 RowScope footer；已删除内层无限制 verticalScroll，避免与共享弹窗重复滚动 |
| ServerEditorScreen.kt:54 | CodegCard 外层滚动和输入键盘 inset，确认长表单可滚到保存按钮 |
| ServerLogsScreen.kt:59 | 动态 tabs（包括全部接口不支持的空列表）和 UI 页码映射；筛选芯片显式传 enabled，规避两个 CodegFilterChip 重载歧义 |
| UpdateBanner.kt:83、111 | 确定进度使用 Float，保留 null 的不确定进度；更新说明使用共享对话框滚动 |

- 静态检查：`git diff --check`；14 个变更 Kotlin 文件的括号平衡检查；完整名称原件搜索；检查改动文件边界。以上不等价于 Kotlin 编译。
- 协调方 `./gradlew :app:check` 关注：Compose 编译、资源引用、芯片重载解析、DSL receiver、现有工作任务/自动化/搜索/日志/更新单测与 lint。原有 ExperimentalMaterial3Api/ExperimentalLayoutApi 注解保留；未新增实验性 API 或依赖。

## 3. 单元测试

| 测试类::方法 | 覆盖点 |
|---|---|
| 无新增 | 本次为 UI 组件替换；未新增纯逻辑，也未更改现有解析/映射/状态推导。未执行 JVM 测试。 |
| 协调方执行现有 suite（不指定未核实的方法名） | 工作任务列归属/动作/配置保留/设置校验，自动化草稿与 cron，日志过滤，搜索历史及更新状态回归 |

## 4. 测试用例

前置条件：T43–T46 与本任务合入 sit 后生成 APK；由协调方使用 vivo V1824A，Android 11。准备官方原版 codeg 0.38 或兼容测试服 T13Test，至少两个项目（含重名项目）、多状态工作任务、模板、手动/定时自动化及运行历史、日志文件和 token 数据；另准备不支持可选接口的服务器。危险操作仅使用可删除测试数据。每条用例独立执行，先设置该行主题；英文界面补测长文案。无需本任务方操作手机。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T50-01 | 工作任务 → 看板 | 打开四个列标签；勾选/取消显示已取消；进入长标题任务再返回；待办任务上移/下移 | 标签显示列名与计数，筛选正确；行标题与状态可辨；排序只作用于对应项目，刷新后保持；详情返回不退出页面 | 明 |
| T50-02 | 工作任务 → 看板 | 打开四个列标签；勾选/取消显示已取消；进入长标题任务再返回；待办任务上移/下移 | 标签显示列名与计数，筛选正确；行标题与状态可辨；排序只作用于对应项目，刷新后保持；详情返回不退出页面 | 暗 |
| T50-03 | 工作任务 → 创建/编辑 | 从创建选择模板、项目、代理；填写标题和多行描述；保存；再次打开修改；分别选择两个同名项目 | 选择的是点击的实体；模板填充正确；附件/未知配置不丢；空字段不能保存，busy 时仍锁定原字段；弹窗与键盘可滚动 | 明 |
| T50-04 | 工作任务 → 创建/编辑 | 从创建选择模板、项目、代理；填写标题和多行描述；保存；再次打开修改；分别选择两个同名项目 | 选择的是点击的实体；模板填充正确；附件/未知配置不丢；空字段不能保存，busy 时仍锁定原字段；弹窗与键盘可滚动 | 暗 |
| T50-05 | 工作任务 → 动作确认 | 对测试任务依次打开删除/取消/合并/清理确认并先取消；退回填写空/有效反馈；调度填写非法/合法时间后确认 | 显示任务标题和 ID；取消不发动作；非法输入不能确认；有效确认仅执行对应任务动作，既有隐藏动作规则生效 | 明 |
| T50-06 | 工作任务 → 动作确认 | 对测试任务依次打开删除/取消/合并/清理确认并先取消；退回填写空/有效反馈；调度填写非法/合法时间后确认 | 显示任务标题和 ID；取消不发动作；非法输入不能确认；有效确认仅执行对应任务动作，既有隐藏动作规则生效 | 暗 |
| T50-07 | 工作任务 → 更多 → 模板 | 新建模板并保存；点模板行编辑；验证名称锁定；删除先取消后确认；长列表滚至底部 | 设计系统菜单定位正确；编辑仍填充原配置；删除确认显示模板名；保存/删除受 busy 限制；无无限高度崩溃 | 明 |
| T50-08 | 工作任务 → 更多 → 模板 | 新建模板并保存；点模板行编辑；验证名称锁定；删除先取消后确认；长列表滚至底部 | 设计系统菜单定位正确；编辑仍填充原配置；删除确认显示模板名；保存/删除受 busy 限制；无无限高度崩溃 | 暗 |
| T50-09 | 工作任务 → 更多 → 设置 | 切换项目；查看继承/独立设置；切换布尔与合并策略；填非法 JSON/数值；保存合法值；重置先取消后确认 | 分区清楚，错误说明与保存禁用保留；重置目标为当前项目且取消无操作；加载时字段禁用；长表单滚动正常 | 明 |
| T50-10 | 工作任务 → 更多 → 设置 | 切换项目；查看继承/独立设置；切换布尔与合并策略；填非法 JSON/数值；保存合法值；重置先取消后确认 | 分区清楚，错误说明与保存禁用保留；重置目标为当前项目且取消无操作；加载时字段禁用；长表单滚动正常 | 暗 |
| T50-11 | 自动化 → 列表/详情 | 依次选全部/启用/禁用；点击行；切换开关；查看配置、提示词、运行历史并刷新 | 分段与列表行一致；开关只切换启用状态，不误触行导航；代理图标/状态点保留；状态 badge 与错误、摘要、时间对应 | 明 |
| T50-12 | 自动化 → 列表/详情 | 依次选全部/启用/禁用；点击行；切换开关；查看配置、提示词、运行历史并刷新 | 分段与列表行一致；开关只切换启用状态，不误触行导航；代理图标/状态点保留；状态 badge 与错误、摘要、时间对应 | 暗 |
| T50-13 | 自动化 → 创建/编辑 | 填写名称/多行提示词、项目和代理；切换手动/定时；输入非法 cron、合法 cron 与时区；弹出键盘后保存 | 抽屉固定 footer 可见；cron 预览状态与保存条件保留；无效计划不能保存；键盘不覆盖末项；保存后列表同步 | 明 |
| T50-14 | 自动化 → 创建/编辑 | 填写名称/多行提示词、项目和代理；切换手动/定时；输入非法 cron、合法 cron 与时区；弹出键盘后保存 | 抽屉固定 footer 可见；cron 预览状态与保存条件保留；无效计划不能保存；键盘不覆盖末项；保存后列表同步 | 暗 |
| T50-15 | 自动化 → 删除/取消运行 | 选择测试自动化删除，先取消再确认；运行后取消测试 run，先取消再确认；观察 busy | 确认显示自动化名、取消运行显示 run ID 与名称；取消不发送动作；确认按钮 busy 禁用，历史刷新正确 | 明 |
| T50-16 | 自动化 → 删除/取消运行 | 选择测试自动化删除，先取消再确认；运行后取消测试 run，先取消再确认；观察 busy | 确认显示自动化名、取消运行显示 run ID 与名称；取消不发送动作；确认按钮 busy 禁用，历史刷新正确 | 暗 |
| T50-17 | 日志 → 最近/文件 | 切换标签；选级别；搜索关键字/不存在内容；打开长日志文件并返回；刷新 | 标签与筛选一致；文件行显示名称及元数据；过滤/空态正确；文件返回先回列表；加载显示不确定进度 | 明 |
| T50-18 | 日志 → 最近/文件 | 切换标签；选级别；搜索关键字/不存在内容；打开长日志文件并返回；刷新 | 标签与筛选一致；文件行显示名称及元数据；过滤/空态正确；文件返回先回列表；加载显示不确定进度 | 暗 |
| T50-19 | 日志 → 用量 | 依次选 7 天/30 天/全部；查看会话、token、缓存、日序列；使用缺少部分/全部可选接口的服务器 | 统计/格式正确；范围筛选有效；不支持的入口隐藏；空标签列表不崩溃；不存在接口不出现可操作入口 | 明 |
| T50-20 | 日志 → 用量 | 依次选 7 天/30 天/全部；查看会话、token、缓存、日序列；使用缺少部分/全部可选接口的服务器 | 统计/格式正确；范围筛选有效；不支持的入口隐藏；空标签列表不崩溃；不存在接口不出现可操作入口 | 暗 |
| T50-21 | 动态 | 展开/折叠运行中与最近分区；点会话；返回；下拉与顶栏刷新；验证空态/错误重试 | 分区图标、计数和展开状态正确；会话目标正确；空态仍支持刷新，错误重试可恢复 | 明 |
| T50-22 | 动态 | 展开/折叠运行中与最近分区；点会话；返回；下拉与顶栏刷新；验证空态/错误重试 | 分区图标、计数和展开状态正确；会话目标正确；空态仍支持刷新，错误重试可恢复 | 暗 |
| T50-23 | 搜索 | 输入关键字；按 IME 搜索；点结果；返回清空；点历史词；清空历史；输入无结果/触发错误后重试 | IME 提交与历史记录保留；清空按钮可用；历史行与清空按钮风格一致；会话/空态/错误状态正确 | 明 |
| T50-24 | 搜索 | 输入关键字；按 IME 搜索；点结果；返回清空；点历史词；清空历史；输入无结果/触发错误后重试 | IME 提交与历史记录保留；清空按钮可用；历史行与清空按钮风格一致；会话/空态/错误状态正确 | 暗 |
| T50-25 | 服务器 → 列表 | 准备两个长名称服务器；切换；点更多编辑；删除先取消后确认；删除最后一个 | 选中行、URL 和菜单锚点正确；删除确认明确服务器名且为危险样式；更多不误切换；删除后选择/首次启动由现有状态处理 | 明 |
| T50-26 | 服务器 → 列表 | 准备两个长名称服务器；切换；点更多编辑；删除先取消后确认；删除最后一个 | 选中行、URL 和菜单锚点正确；删除确认明确服务器名且为危险样式；更多不误切换；删除后选择/首次启动由现有状态处理 | 暗 |
| T50-27 | 服务器 → 新建/编辑 | 填写名称/URL/token；测试有效和无效连接；滚动到底保存；返回列表；重开编辑 | 表单分区和卡片正确，token 仍隐藏；测试反馈与加载状态保留；键盘不挡保存；持久化数据正确 | 明 |
| T50-28 | 服务器 → 新建/编辑 | 填写名称/URL/token；测试有效和无效连接；滚动到底保存；返回列表；重开编辑 | 表单分区和卡片正确，token 仍隐藏；测试反馈与加载状态保留；键盘不挡保存；持久化数据正确 | 暗 |
| T50-29 | 首次启动 | 在无服务器配置的测试环境打开 App；查看功能面板；点添加服务器并返回 | 功能面板按卡片布局；图标和文案保留；按钮进入新增服务器页；滚动可看到完整说明 | 明 |
| T50-30 | 首次启动 | 在无服务器配置的测试环境打开 App；查看功能面板；点添加服务器并返回 | 功能面板按卡片布局；图标和文案保留；按钮进入新增服务器页；滚动可看到完整说明 | 暗 |
| T50-31 | 主框架 → 五个导航入口 | 逐个点击底栏入口并返回；旋转到宽屏查看 rail；重复切换；使用系统返回；检查英文和字体放大 | 选中 Secondary、未选中 Ghost；图标保留；路由与各页状态恢复和返回栈保留；系统栏不遮挡；宽屏行可点击 | 明 |
| T50-32 | 主框架 → 五个导航入口 | 逐个点击底栏入口并返回；旋转到宽屏查看 rail；重复切换；使用系统返回；检查英文和字体放大 | 选中 Secondary、未选中 Ghost；图标保留；路由与各页状态恢复和返回栈保留；系统栏不遮挡；宽屏行可点击 | 暗 |
| T50-33 | 更新横幅/关于 → 更新 | 检查更新；展开长说明并关闭/稍后；再次下载；观察未知/已知大小进度；模拟失败；在测试设备确认安装权限流程 | 按钮/说明对话框一致；未知大小不伪造进度，已知大小进度正常；取消说明不下载；消息、权限、FileProvider 安装流程保留 | 明 |
| T50-34 | 更新横幅/关于 → 更新 | 检查更新；展开长说明并关闭/稍后；再次下载；观察未知/已知大小进度；模拟失败；在测试设备确认安装权限流程 | 按钮/说明对话框一致；未知大小不伪造进度，已知大小进度正常；取消说明不下载；消息、权限、FileProvider 安装流程保留 | 暗 |

对照网页端（以上每对明/暗用例使用相同参考）：

- T50-01/02：`WEB/components/tasks/tasks-page.tsx、task-row.tsx`。
- T50-03/04：`WEB/components/tasks/task-editor-dialog.tsx`。
- T50-05/06：`WEB/components/tasks/task-cancel-dialog.tsx、task-merge-dialog.tsx、task-schedule-dialog.tsx`。
- T50-07/08：`WEB/components/tasks/task-editor-dialog.tsx（模板菜单）`。
- T50-09/10：`WEB/components/tasks/task-settings-dialog.tsx`。
- T50-11/12：`WEB/components/automations/automations-page.tsx`。
- T50-13/14：`WEB/components/automations/automation-editor.tsx`。
- T50-15/16：`WEB/components/automations/automations-page.tsx`。
- T50-17/18：`WEB/components/settings/logs-settings.tsx`。
- T50-19/20：`WEB/components/settings/logs-settings.tsx、layout/status-bar-stats.tsx`。
- T50-21/22：`WEB/components/layout/status-bar.tsx、status-bar-alerts.tsx（移动端为独立页面）`。
- T50-23/24：`WEB/components/layout/app-title-bar.tsx（移动端保留独立搜索页与 IME）`。
- T50-25/26：`WEB/components/layout/app-title-bar.tsx、status-bar-computer.tsx（移动端连接配置页）`。
- T50-27/28：`WEB/components/layout/status-bar-computer.tsx（移动端服务器设置）`。
- T50-29/30：`WEB/components/layout/app-title-bar.tsx（仅对齐 chrome/按钮；无对应网页 onboarding）`。
- T50-31/32：`WEB/components/layout/app-title-bar.tsx、status-bar.tsx`。
- T50-33/34：`WEB/components/layout/status-bar-update.tsx`。

允许差异：Android 保留单列详情、返回键、触摸菜单、系统栏/IME inset；不复制网页桌面多栏和 hover 行为；未替换 Material 图标。共享控件缺口及其保留原件按第 1 节处理。尤其在 360dp 宽度/英文/字体放大时检查标签省略，不能导致操作不可达。

## 5. 回归点

- 工作任务：空/错误/不支持状态、隐藏动作、四列过滤、显示已取消、开始全部、项目内排序、详情事件/文件/diff、返回行为；创建/编辑必须保留原附件及未知 config；模板新增/编辑/删除；设置继承/校验/保存/重置逐项检查。
- 自动化：过滤/启用/禁用、列表详情、手动运行、刷新、编辑草稿、切换代理的既有配置处理、cron/时区预览、删除和取消运行；404/unknown 时保留原入口隐藏条件。
- 日志：级别、关键字、文件选择与返回、文件截断提示、用量时间范围/空态/部分接口不支持；快速切换标签不能让文件查询串到别的页面。
- 动态/搜索：分组折叠、刷新、会话导航、查询提交/重试/最近历史/清空；重启后历史按原规则保存。
- 服务器/引导：新增/编辑/选择/删除、鉴权测试、令牌隐藏、最后一个服务器删除后的入口、首次启动添加服务器。
- 主框架：五个 HomeTab、深层路由返回、选中态、滚动时底栏显隐、窄/宽屏切换、系统导航栏/横竖屏/大字体与英文长标题。
- 更新：检查/忽略版本、长说明、下载百分比、未知总长度、失败提示、安装权限往返、安装器只启动一次。此任务不调整更新状态机或下载器。
- 危险操作统一验证：取消/点击外部/系统返回不能发送请求；确认文字明确目标；连续点击、busy 与错误恢复不重复发起请求。

## 6. 已知风险 / 未完成

- 共享组件缺少 enabled、IME/图标 slot、不确定进度、实体 ID 选择能力；保留的 13 个原件调用位置与确认/选择器例外已在第 1 节列全，需协调方扩展共享 API 后继续收口。未修改 designsystem。
- 工作任务危险确认目前仍用保留校验/禁用的 CodegDialog，尚未全部统一 CodegConfirmDialog；自动化/模板/重置的确认已用危险按钮并保留目标，仍待共享确认组件支持 enabled。
- T52/T53 留项见第 1 节，未接 Toast/新折叠组件。全局搜索输入等待 T53；本次保留原 IME 与清除行为。
- 主导航交互和弹窗/抽屉布局有变化，未实际编译或截图对比；字体放大、长文案、触摸目标、TalkBack 选中语义与小屏可达性须优先人工验证。确认组件内部样式继续由共享组件任务负责。
- 所有真机步骤与编译检查待协调方统一执行；静态检查不能证明构建通过。
