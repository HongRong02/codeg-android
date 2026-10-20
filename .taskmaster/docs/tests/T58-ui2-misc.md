# T58 测试文档：杂项页面图标、Toast 与组件例外清理

- 分支：`task/T58-ui2-misc`（本地工作分支 `cli/T58-ui2-misc`，不切换分支）。提交：代码与本文同一提交，用 `git log -1 --format=%H -- .taskmaster/docs/tests/T58-ui2-misc.md` 查询。
- 依赖：T50、T51、T52、T53、T60，已在派发基准中。测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 执行状态：仅执行静态检查；Gradle、lint、JVM 测试、真机用例均未执行。未触发 CI、未操作 adb、未运行索引生成器，未改 code-index。

## 1. 测试范围

`A/` = `app/src/main/java/app/codeg/android/`。下列清单覆盖全部 14 个改动 Kotlin 文件，另有本文；未新增公开 API、依赖或字符串，Toast 复用现有中英文资源。ViewModel、网络、模型和主框架路由表未改。

### 图标替换（文件 → 替换前 → 替换后）

| 文件（相对 A/） | Material → Lucide |
|---|---|
| feature/worktasks/WorkTaskScreen.kt | ArrowBack / Refresh / Schedule → ArrowLeft / RefreshCw / Clock |
| feature/worktasks/WorkTaskTemplates.kt | MoreVert → EllipsisVertical |
| feature/automations/AutomationScreen.kt | ArrowBack / Refresh / KeyboardArrowRight / Schedule / PlayArrow / Layers / Folder / CalendarToday / History → ArrowLeft / RefreshCw / ChevronRight / Clock / Play / Layers / Folder / Calendar / History |
| feature/serverlogs/ServerLogsScreen.kt | ArrowBack / Refresh → ArrowLeft / RefreshCw |
| feature/activity/ActivityScreen.kt | Refresh / GraphicEq / Bedtime / History → RefreshCw / AudioLines / Moon / History |
| feature/search/SearchScreen.kt | Search / Close / ErrorOutline / History → Search / X / CircleAlert / History |
| feature/server/ServerListScreen.kt | ArrowBack / Add / Dns / Check / Edit / Delete → ArrowLeft / Plus / Server / Check / Pencil / Trash2 |
| feature/server/ServerEditorScreen.kt | ArrowBack → ArrowLeft |
| feature/onboarding/OnboardingScreen.kt | Code / Visibility / Bolt / PlayArrow → Code / Eye / Zap / Play |
| feature/main/MainShell.kt | Forum / FolderOpen / Bolt / Search / Settings（Outlined 与 Rounded）→ MessageSquare / FolderOpen / Zap / Search / Settings |

直接绘制统一用 `CodegIcon`，默认/行内 16dp；首次启动品牌主图保留 56dp（不是行内图标）。图标按钮继续由 `CodegIconButton` 提供触摸目标，真机需确认 ≥48dp。空态图标大小由共享组件控制。Lucide 图标不自动镜像，RTL 方向需人工确认。

网页端已 grep 对照：`components/automations/automations-page.tsx` 的 Play、Clock、Pencil、Plus、MoreHorizontal 及 size-4；`automation-editor.tsx` 的 ArrowLeft；`components/tasks/tasks-page.tsx`、`task-actions.ts`、`task-settings-dialog.tsx`；`components/layout/status-bar-tasks.tsx` 的 Clock。其余按 icon-map.md 的语义映射；Activity AudioLines、首次启动品牌图没有本任务参考目录中的逐项对应页面，不宣称像素一致。

### Toast 替换

| 文件 | 替换前 → 替换后 |
|---|---|
| feature/server/ServerEditorScreen.kt | 测试连接成功/失败的临时 Text + TODO(T52) → `LaunchedEffect(test)` 调用 success / error；沿用 server_connection_ok / failed 资源 |
| core/update/UpdateBanner.kt：UpdateBanner | 下载失败 Text + TODO(T52) → 按 state.message 发 error；安装启动失败也发 error，保留原安装流程 |
| core/update/UpdateBanner.kt：UpdateAbout | 手动检查反馈 Text + TODO(T52) → 本次手动检查结束后 info（已最新）/ error（失败），成功发现新版仍打开说明 |
| feature/main/MainShell.kt | 无全局宿主 → CodegToasterHost 包住 Scaffold、底栏及所有路由父级；按底栏实测高度预留 Toast 空间，内容仍按完整高度测量；宿主自身处理 safeDrawing 和 IME inset |

网页端参考 `components/tasks/use-task-dialogs.tsx`、`components/automations/automations-page.tsx` 的失败 toast.error、`components/settings/logs-settings.tsx` 的 error/info 与 `components/ui/app-toaster.tsx`。Android 连接测试/APK 安装没有本任务参考目录中的同操作 Web 实现，沿用既有操作文案与完成时机。未把持续加载失败/空态转换成 Toast；不添加业务成功事件，不推测 ViewModel 完成状态。

### T53 组件

| 文件 | 替换前 → 替换后 |
|---|---|
| feature/search/SearchScreen.kt | 手写 OutlinedTextField 搜索组 + TODO(T53) → CodegInputGroup；保留 IME Search、提交回调、清空按钮；加载用 CodegSpinner |
| feature/activity/ActivityScreen.kt | 手写展开条件 + TODO(T53) → 每个分区的 CodegInstantCollapsible；沿用 collapsed 状态、计数、会话点击与刷新，分区内会话使用 runtime key |

本范围没有其他 T53 TODO，不为凑组件清单新增头像、图片预览、文件夹徽章或命令菜单。

### T50 / T60 例外清理

| 文件 | 替换前 → 替换后 |
|---|---|
| feature/worktasks/WorkTaskControls.kt | 标题/描述 OutlinedTextField → CodegTextField(enabled、多行)；任务动作 CodegDialog → CodegConfirmDialog(content、confirmEnabled、busy)，保留反馈和时间校验；实体回调菜单 → 泛型 CodegSelect，项目按 ID 定位选中项、代理按 wire 定位，回调不按名称反查 |
| feature/worktasks/WorkTaskTemplates.kt | 3 个 OutlinedTextField → CodegTextField（已有名称 readOnly，busy 禁用）；不确定 LinearProgressIndicator → CodegProgress()；删除 CodegDialog → CodegConfirmDialog(busy) |
| feature/worktasks/WorkTaskSettingsSheet.kt | 动态输入 → CodegTextField(enabled、supportingText)；加载 → CodegProgress()；项目/合并策略菜单 → 泛型 CodegSelect（ID/wire key）；重置 → CodegConfirmDialog(busy) |
| feature/worktasks/WorkTaskScreen.kt | 不确定加载 → CodegProgress() |
| feature/automations/AutomationScreen.kt | 两处 CircularProgressIndicator → CodegSpinner(size=24dp)；删除/取消运行 → CodegConfirmDialog(busy) |
| feature/automations/AutomationEditorSheet.kt | 不确定加载 → CodegProgress()；项目/代理菜单 → 泛型 CodegSelect，绑定 ID/枚举；文本输入补 enabled=!ui.busy |
| feature/serverlogs/ServerLogsScreen.kt | 不确定加载 → CodegProgress()；现有 CodegFilterChip 已符合 T60 新签名，无需再改 |
| feature/search/SearchScreen.kt | 搜索字段与加载指示由上述 InputGroup / Spinner 接替 |
| core/update/UpdateBanner.kt | 未知下载进度 → CodegProgress()，已知 progress 重载保留 |

### grep 自查

目录限定为 `A/feature/{worktasks,automations,serverlogs,activity,search,server,onboarding,main}` 和 `A/core/update`：

- `rg -n 'Icons\.'`：0。
- `rg -n 'TODO\(T5[0-9]\)'`：0。
- 原件完整调用 `\b(OutlinedTextField|LinearProgressIndicator|CircularProgressIndicator)\s*\(`：分别 0 / 0 / 0；原 7 个输入、6 个不确定进度例外全部清除。
- `CodegDialog` 仍有 4 处：工作任务编辑、模板管理、设置管理、更新说明；它们是编辑/管理/说明窗口，危险确认均已迁移。
- PullToRefreshBox 保留系统刷新行为；未替换共享组件内部实现，也未修改 designsystem。
- 静态校验：`git diff --check`；14 个 Kotlin 文件去除注释/字符串后的括号匹配；26 个 Lucide 属性存在性；NavHost 路由区域逐字比较未变。以上均不等同 Kotlin 编译。

## 2. 编译检查

自评编译风险：**中等，未编译**。

| 文件:行 / 符号 | 需统一编译或真机确认 |
|---|---|
| MainShell.kt:198（bottomBarHeight） | onSizeChanged、Density 的 Dp/px 转换、自定义 layout 的约束与完整高度测量；底栏折叠、横竖屏、键盘与导航手势 inset 组合重点复测 |
| ActivityScreen.kt:139（CodegInstantCollapsible） | LazyItemScope.animateItem 和 runtime key 作用域；分区内不再逐行懒组合，超长动态列表性能需真机检查 |
| WorkTaskControls.kt:88（Choice） | 泛型 Pair<String, () -> Unit> 选择器推断与回调捕获；同名项目/代理选中身份，未知代理无选中项 |
| WorkTaskSettingsSheet.kt:80、102 | 泛型 Folder / Pair 选择器 key 类型；翻译文案在组合期间求值，无 Composable lambda 越界 |
| AutomationEditorSheet.kt:87 | Pair<T, String> 泛型推断，ID/枚举回传保持 |
| ServerEditorScreen.kt:45 | TestState sealed 分支有 else；Toast 文案在组合期读取资源，effect 只分发，不在 effect 中调用 stringResource |
| UpdateBanner.kt:67、109 | nullable resource ID 与 effect 触发时机；同一错误连续重试、手动检查结束与发现新版时序需回归 |
| 各图标文件 | icon 包通配 import 提供 Lucide 扩展属性和 CodegIcon；未新增实验性 API，原有 OptIn 保留 |

协调方 `./gradlew :app:check` 关注：Compose 编译、泛型重载、布局约束、现有工作任务/自动化/日志/搜索/更新单测、资源与未使用 import lint。不得据静态检查宣称编译通过。

## 3. 单元测试

| 测试类::方法 | 覆盖点 |
|---|---|
| 无新增 | 本次仅 UI 组件与反馈接线，未新增解析、映射或状态推导算法；不写镜像组件实现的单测 |
| 现有相关 suite（协调方执行） | 工作任务配置/动作/设置校验、自动化草稿/cron、搜索历史、日志过滤、更新流程；本任务未运行 |

## 4. 测试用例

前置条件：依赖合入后统一产出 APK；由协调方使用 vivo V1824A / Android 11 与 T13Test 或兼容服务器执行。准备两个重名项目、重名显示代理、模板、可删除任务/自动化、运行历史和日志；准备有效/无效连接、已最新/有新版/检查失败/下载失败/安装失败场景。每条独立执行，长英文与字体放大补测。所有用例当前均待执行。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T58-01 | 工作任务看板 | 切换四列、筛选已取消、进入任务返回、刷新 | Lucide 图标为16dp行内尺寸，状态与排序保持；忙碌进度不伪造百分比 | 明 |
| T58-02 | 工作任务看板 | 切换四列、筛选已取消、进入任务返回、刷新 | Lucide 图标为16dp行内尺寸，状态与排序保持；忙碌进度不伪造百分比 | 暗 |
| T58-03 | 工作任务创建/编辑 | 选择模板；依次选择两个同名项目/代理并保存；编辑多行描述 | 选中和回传对应实体，模板配置不丢；忙碌字段禁用，键盘可滚动至保存 | 明 |
| T58-04 | 工作任务创建/编辑 | 选择模板；依次选择两个同名项目/代理并保存；编辑多行描述 | 选中和回传对应实体，模板配置不丢；忙碌字段禁用，键盘可滚动至保存 | 暗 |
| T58-05 | 工作任务动作 | 删除/取消/清理先取消后确认；退回填空和有效反馈；调度填非法和合法时间 | 目标标题和ID清楚；危险样式正确；非法输入禁确认，busy不能重复确认；请求内容保持 | 明 |
| T58-06 | 工作任务动作 | 删除/取消/清理先取消后确认；退回填空和有效反馈；调度填非法和合法时间 | 目标标题和ID清楚；危险样式正确；非法输入禁确认，busy不能重复确认；请求内容保持 | 暗 |
| T58-07 | 工作任务模板 | 新建、保存、编辑模板；尝试改已有名称；删除先取消后确认 | 名称只读可复制，忙碌禁用；目标名称正确，管理列表与滚动保持 | 明 |
| T58-08 | 工作任务模板 | 新建、保存、编辑模板；尝试改已有名称；删除先取消后确认 | 名称只读可复制，忙碌禁用；目标名称正确，管理列表与滚动保持 | 暗 |
| T58-09 | 工作任务设置 | 切换重名项目；改合并策略、非法JSON/数值；保存后重置 | ID选择准确；原校验和错误说明保留；重置确认目标正确，busy禁止交互 | 明 |
| T58-10 | 工作任务设置 | 切换重名项目；改合并策略、非法JSON/数值；保存后重置 | ID选择准确；原校验和错误说明保留；重置确认目标正确，busy禁止交互 | 暗 |
| T58-11 | 自动化列表/详情 | 筛选、切换开关、打开详情、刷新运行历史；删除/取消运行先取消再确认 | 行内图标16dp；Spinner和危险确认可见；开关、运行目标、返回保持 | 明 |
| T58-12 | 自动化列表/详情 | 筛选、切换开关、打开详情、刷新运行历史；删除/取消运行先取消再确认 | 行内图标16dp；Spinner和危险确认可见；开关、运行目标、返回保持 | 暗 |
| T58-13 | 自动化编辑 | 选择重名项目与代理；编辑提示词；填写非法/合法cron，保存 | 选择回传原ID/枚举；忙碌禁用输入；预览进度与原保存条件保持 | 明 |
| T58-14 | 自动化编辑 | 选择重名项目与代理；编辑提示词；填写非法/合法cron，保存 | 选择回传原ID/枚举；忙碌禁用输入；预览进度与原保存条件保持 | 暗 |
| T58-15 | 服务器日志/统计 | 最近/文件切换；筛选、搜索、打开长日志并返回；切换统计范围；连接不支持可选接口的服务器 | Lucide导航与不确定进度正常；FilterChip可用；统计正确，不支持入口仍隐藏 | 明 |
| T58-16 | 服务器日志/统计 | 最近/文件切换；筛选、搜索、打开长日志并返回；切换统计范围；连接不支持可选接口的服务器 | Lucide导航与不确定进度正常；FilterChip可用；统计正确，不支持入口仍隐藏 | 暗 |
| T58-17 | 动态 | 展开/折叠各分区；点击会话返回；下拉和顶部刷新；准备大量会话后滚动 | 分区计数/图标正确，单一折叠箭头；会话目标正确，空态和错误重试保持；记录长列表性能 | 明 |
| T58-18 | 动态 | 展开/折叠各分区；点击会话返回；下拉和顶部刷新；准备大量会话后滚动 | 分区计数/图标正确，单一折叠箭头；会话目标正确，空态和错误重试保持；记录长列表性能 | 暗 |
| T58-19 | 全局搜索 | 输入词并按IME Search；清空；点历史词与结果；触发无结果/加载错误 | InputGroup图标16dp、搜索中Spinner；原提交与历史保持；清空触摸目标≥48dp，持续错误仍在页面 | 明 |
| T58-20 | 全局搜索 | 输入词并按IME Search；清空；点历史词与结果；触发无结果/加载错误 | InputGroup图标16dp、搜索中Spinner；原提交与历史保持；清空触摸目标≥48dp，持续错误仍在页面 | 暗 |
| T58-21 | 服务器列表/首次启动 | 切换服务器、编辑、删除先取消后确认；无服务器进入首次启动再添加 | Lucide图标、菜单、选中状态正确；首次启动主图56dp、功能行16dp；路由保持 | 明 |
| T58-22 | 服务器列表/首次启动 | 切换服务器、编辑、删除先取消后确认；无服务器进入首次启动再添加 | Lucide图标、菜单、选中状态正确；首次启动主图56dp、功能行16dp；路由保持 | 暗 |
| T58-23 | 服务器编辑/连接Toast | 有效URL和token测试成功；改无效参数测试失败；连续重试；开键盘 | success显示版本，error显示原错误资源；每次完成只发一次，重组不重复；不再显示临时Text，保存保持 | 明 |
| T58-24 | 服务器编辑/连接Toast | 有效URL和token测试成功；改无效参数测试失败；连续重试；开键盘 | success显示版本，error显示原错误资源；每次完成只发一次，重组不重复；不再显示临时Text，保存保持 | 暗 |
| T58-25 | 主框架/Toast宿主 | 逐个切换五个导航；显示Toast后折叠/恢复底栏；开关键盘、旋转宽屏；打开弹层；滑动关闭Toast | 路由恢复保持；Toast在底栏/系统栏/键盘之上，页面高度不变；弹层能读到同一Toaster，无静默实例提示；导航点击目标≥48dp | 明 |
| T58-26 | 主框架/Toast宿主 | 逐个切换五个导航；显示Toast后折叠/恢复底栏；开关键盘、旋转宽屏；打开弹层；滑动关闭Toast | 路由恢复保持；Toast在底栏/系统栏/键盘之上，页面高度不变；弹层能读到同一Toaster，无静默实例提示；导航点击目标≥48dp | 暗 |
| T58-27 | 更新/关于Toast | 手动检查已最新与检查失败；再检查有新版并取消说明；下载未知/已知大小文件；模拟下载或校验失败、安装启动失败 | 已最新info、检查失败error各一次；新版仍开说明；不确定/确定Progress正确；下载/安装失败error；自动检查不发手动提示，原权限流程保持 | 明 |
| T58-28 | 更新/关于Toast | 手动检查已最新与检查失败；再检查有新版并取消说明；下载未知/已知大小文件；模拟下载或校验失败、安装启动失败 | 已最新info、检查失败error各一次；新版仍开说明；不确定/确定Progress正确；下载/安装失败error；自动检查不发手动提示，原权限流程保持 | 暗 |

对照网页端（路径均相对 WEB/components/）：

- T58-01 / 02：`tasks/tasks-page.tsx、task-card.tsx`。
- T58-03 / 04：`tasks/task-editor-dialog.tsx`。
- T58-05 / 06：`tasks/task-cancel-dialog.tsx、task-schedule-dialog.tsx`。
- T58-07 / 08：`tasks/task-editor-dialog.tsx`。
- T58-09 / 10：`tasks/task-settings-dialog.tsx`。
- T58-11 / 12：`automations/automations-page.tsx`。
- T58-13 / 14：`automations/automation-editor.tsx`。
- T58-15 / 16：`settings/logs-settings.tsx`。
- T58-17 / 18：`layout/status-bar-tasks.tsx（活动状态语义；非同版布局）`。
- T58-19 / 20：`tasks/tasks-page.tsx（搜索语义；允许全局范围不同）`。
- T58-21 / 22：`layout/app-title-bar.tsx（壳层语义；Android专属配置入口）`。
- T58-23 / 24：`ui/app-toaster.tsx（Toast样式；连接测试是Android操作）`。
- T58-25 / 26：`ui/app-toaster.tsx、layout/app-title-bar.tsx`。
- T58-27 / 28：`layout/status-bar-update.tsx、ui/app-toaster.tsx（APK安装为Android流程）`。

允许差异：原生键盘、触摸目标、移动导航和 APK 安装流程；没有同操作 Web 页的入口仅比较图标语义与共享 Toast 风格，不宣称完全一致。

## 5. 回归点

- 全量复测 T50-01～34：T50-01～10 对应工作任务/模板/设置，T50-11～16 自动化，T50-17～20 日志/统计，T50-21～22 动态，T50-23～24 搜索，T50-25～30 服务器/首次启动，T50-31～32 主框架，T50-33～34 更新。
- T50 的“图标保留”预期更新为 Lucide；“临时消息”更新为 Toast；业务回调、可选接口隐藏、配置保留、busy禁用与输入校验继续保持。
- 重点：同名项目/代理不混选；反馈/调度确认不绕过校验；搜索IME触发历史更新；手动检查与后台检查分离；Toast不因重组重复，重复重试仍反馈。
- MainShell 的 NavHost 逐字未改；仍需测试深链、返回栈、宽屏 rail、滚动驱动底栏和输入法状态恢复。

## 6. 已知风险 / 未完成

- 编译、lint、JVM及真机测试均待协调方执行；没有构建通过或视觉完全对齐的结论。
- 动态分区内容由 CodegInstantCollapsible 管理，一次组合一个分区的会话行；超长列表较原逐行Lazy布局可能增加组合成本，需用 T58-17/18 记录性能。
- MainShell 宿主无法改变系统 Dialog / Sheet 的窗口层级；弹层内触发Toast可能在窗口后显示，需 T58-25/26 真机确认，若要求跨原生窗口置顶需协调设计系统方案，未越界修改共享组件。
- MainShell之外的首次配置入口是否另有父级宿主，本任务未越界读取应用根入口；首次添加服务器连接反馈需 T58-23/24 及 T50-29/30 联动验证，若使用静默实例需协调方在应用根入口接入宿主。
- 图标方向、48dp实际触摸范围、IME/底栏组合、重复失败重试的effect时序均需人工确认。未修改冲突域外代码，也未替其他任务补组件。
