# T60 测试文档：设计系统接口补齐

- 分支：`task/T60-ds-gaps`　提交：本文件随实现提交；准确 hash 见最终交付报告（避免自引用）。
- 依赖：T43–T46、T47–T54 已合入基准；测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。

## 1. 测试范围
- 改动文件：`A/core/designsystem/component/` 下 TextFields.kt、Feedback.kt、Dialogs.kt、Controls.kt、Badges.kt、Buttons.kt、ListRows.kt；新增 T60ChoiceTest.kt 和本测试文档。
- 受影响入口：仅共享组件与现有 T44Gallery、T45Gallery、T46Gallery / 明暗 Preview；没有修改 feature。
- 范围外：业务逻辑、网络接口、导航、依赖、code-index、CI、Gradle、设备操作。

### 新旧公开签名对照
以下 `…` 表示保留原参数、顺序与默认值。

| API | 旧 | 新 |
|---|---|---|
| CodegTextField | `(value, onValueChange, label, modifier, placeholder, keyboardType, mono, singleLine, isError, supportingText)` | 原签名末尾追加 `enabled: Boolean = true, readOnly: Boolean = false, keyboardOptions: KeyboardOptions? = null, keyboardActions: KeyboardActions = KeyboardActions.Default, leading: (@Composable () -> Unit)? = null, trailing: (@Composable () -> Unit)? = null, minLines: Int = 1, maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE` |
| CodegProgress | `(progress: Float, modifier: Modifier = Modifier)` | 原签名保留；新增 `(modifier: Modifier = Modifier)` 不确定动画 |
| CodegSpinner | 无 | `(modifier: Modifier = Modifier, size: Dp = 16.dp)`；LocalContentColor、Lucide.Loader2 |
| CodegConfirmDialog | `(title, message, target, confirmLabel, destructive = false, onConfirm, onDismiss)` | 原参数末尾追加 `confirmEnabled: Boolean = true, busy: Boolean = false, content: (@Composable ColumnScope.() -> Unit)? = null` |
| CodegSelect / CodegCombobox | `(label: String, value: String, options: List<String>, onSelect: (String) -> Unit, modifier = Modifier, enabled = true)` | 原签名保留并委托泛型；新增 `<T>(label: String, selected: T?, options: List<T>, optionLabel: (T) -> String, onSelect: (T) -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true, optionDescription: ((T) -> String?)? = null, key: ((T) -> Any)? = null)` |
| CodegFilterChip | Badges `(selected, label, onClick, modifier = Modifier)`；Buttons `(label, selected, onClick, modifier, icon, enabled)` | 保留 Badges 签名并追加 `icon: ImageVector? = null, enabled: Boolean = true`；删除 Buttons 旧版本 |
| CodegListRow | `(title, onClick, modifier, subtitle, selected, leading, trailing, onLongClick, onMoreClick)` | 原参数末尾追加 `titleMaxLines: Int = 1, subtitleMaxLines: Int = 1` |

### 调用兼容性检查

静态扫描 feature 下目标调用（不含声明 / Preview）：

- CodegTextField：101 处。
- CodegProgress：0 处。
- CodegConfirmDialog：22 处。
- CodegSelect：3 处。
- CodegCombobox：0 处。
- CodegFilterChip：12 处。
- CodegListRow：49 处。

所有现有调用保持源码兼容；TextField/ConfirmDialog/ListRow 均末尾默认追加；String 选择器签名不变；Progress 通过有无 progress 区分。全 A/ 扫描 FilterChip：feature 的位置参数调用只有 sessions/tags/ConversationTagUi.kt:53，顺序为 selected、label、onClick；其余为命名参数，因此不保留旧重载，无需 Deprecated。删除后避免原命名参数调用的重载歧义。未更改任何 feature 调用点。

### L4 迁移清单
路径相对 `A/feature/`；沿用第二阶段报告定位，未迁移的 M3 行号由 L4 按组件名复核。

| feature 分组 | 文件:行 / 定位 | 新能力 |
|---|---|---|
| sessions | tags/ConversationTagUi.kt:53（筛选）、TagForm / 删除确认（T47 报告） | 统一 FilterChip；输入 enabled、确认 busy |
| sessiondetail | AgentOptionsSheet.kt:289 | 泛型 Select 绑定 FolderDetail，optionLabel 展示名称、optionDescription 展示路径、key 用稳定 ID/path |
| sessiondetail | interactive/PlanApprovalCard.kt:104 | TextField enabled = !submitting，minLines/maxLines |
| sessiondetail | interactive/AskQuestionCard.kt（T47 报告长选项） | 如需行布局可用 titleMaxLines / subtitleMaxLines；保留原多选/说明点击逻辑 |
| projects | forge/ForgeDetail.kt:72；forge/ForgeSheet.kt:88、89 | enabled 禁用输入，maxLines 限制长正文；确认框 busy |
| projects | forge/ForgeSheet.kt；importsessions/ImportSessionsSheet.kt（T48 不确定加载例外） | 可按加载表现将 Skeleton 换为无参数 Progress；并非强制去除骨架屏 |
| settings | AssistantsScreen.kt:179 | enabled 替换仅回调 guard 的视觉例外 |
| settings | SettingsChoice.kt:21；Hermes 提供商重复显示名菜单（T49 报告） | 泛型选择器绑定实体 ID；分类菜单仍可保留 |
| worktasks | WorkTaskControls.kt:66、111（T50 报告） | ConfirmDialog content 反馈/时间校验，confirmEnabled、busy；标题/描述 TextField enabled |
| worktasks | WorkTaskTemplates.kt:48；WorkTaskSettingsSheet.kt:76（T50 报告） | ConfirmDialog content/confirmEnabled/busy；输入 enabled；已有名称 readOnly |
| search | SearchScreen.kt 全局输入（T50 报告） | keyboardOptions/keyboardActions + leading/trailing；可用 Spinner 展示加载 |
| automations | AutomationEditorSheet.kt:67、70；AutomationScreen.kt 删除/取消确认（T50 报告） | 输入 enabled；确认 busy/confirmEnabled；无参数 Progress |
| serverlogs | ServerLogsScreen.kt:59（T50 报告） | 无参数 Progress；FilterChip 统一后无需通过 enabled 规避重载 |

T50 其余不确定进度候选：WorkTaskScreen.kt、WorkTaskTemplates.kt、WorkTaskSettingsSheet.kt；core/update/UpdateBanner.kt:83、111 的未知下载进度（后者在本任务允许的代码阅读范围外，仅引用 T50 报告）。已知百分比继续使用 progress 重载。以上未提供确切源码行的项是报告级候选，L4 需按符号定位，不代表已经迁移。

## 2. 编译检查

自评风险：中等，未编译。
- TextFields.kt:45：KeyboardActions/KeyboardOptions 导入，M3 leadingIcon/trailingIcon、minLines/maxLines 参数；singleLine=true 时沿用单行，调用方应提供合法行数。
- Controls.kt:43：泛型重载通过额外 optionLabel 区分；nullable selected 和 key 的推断，需编译确认。Lucide 扩展属性使用 icon 包导入。
- Feedback.kt:66：BoxWithConstraints 的 Dp offset、无限动画和不确定进度语义；确认项目 Compose 版本兼容。
- Dialogs.kt:85：可空 ColumnScope content 通过 invoke(this) 调用；busy 拦截遮罩、返回、取消，确认按钮显示 Spinner。
- Badges.kt:279 / Buttons.kt：删除重载后命名调用统一解析；新增图标继承相同 foreground。
- ListRows.kt:43：行数透传到 Text，新增参数在末尾。
- 预期统一 `:app:check` 关注 Kotlin 泛型重载解析、Compose scope、Lucide import、现有 normalizedProgress 测试和 T60ChoiceTest。
- 已执行：`git diff --check`、全范围 Material 图标残留扫描、调用点静态检查；无编译/运行通过声明。

## 3. 单元测试

| 测试类::方法 | 覆盖点 |
|---|---|
| T60ChoiceTest::duplicateLabelsKeepIdentity | 同名不同 ID 不混选；相同 ID 新快照仍选中；null 未选择；默认实体相等 |
| T60ChoiceTest::searchMatchesLabelAndDescriptionIgnoringCase | 名称/路径忽略大小写搜索；无匹配、null 描述、空查询 |

未执行 JVM 测试，按要求交协调方统一运行。其余变更为 Compose 参数透传与显示，不新增镜像实现的测试。

## 4. 测试用例
前置条件：无需服务器；统一编译后打开已有 Gallery 或 Preview；所有用例待执行。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T60-01 | T45Gallery | 输入普通/禁用/只读字段；尝试聚焦与修改 | 禁用 opacity .5 且不能聚焦；只读可复制但不能修改 | 明、暗各一次 |
| T60-02 | T45Gallery | 多行输入超过四行；触发 Done；观察两侧 slot | min=2 max=4，内容可滚动；IME 回调可触发；图标和 Spinner 正常 | 明、暗各一次 |
| T60-03 | T45Gallery | 选两个同名实体，按描述搜索，清空查询；再测试旧 String 版本 | 分别回传原实体；只标记一个 ID；描述可区分；旧版行为保持 | 明、暗各一次 |
| T60-04 | T46FeedbackGallery | 观察 0/.5/1 与无参数进度；观察 16/24dp Spinner | 确定轨道保持；不确定条带连续循环不溢出；Spinner 随内容色旋转 | 明、暗各一次 |
| T60-05 | T44Gallery | 打开 disabled、busy 确认；点确认、取消、遮罩、返回；观察内容 | disabled 仅禁止确认；busy loading 并禁止关闭和重复确认；content 在描述和 footer 之间且目标清晰 | 明、暗各一次 |
| T60-06 | T46BadgesGallery | 切换普通芯片，点禁用图标芯片 | 普通状态切换一次；图标正常；禁用不触发且 .5 透明度 | 明、暗各一次 |
| T60-07 | T46RowsGallery | 对照旧单行与长标题/副标题行 | 默认省略不变；新增变体最多 3/2 行；more/长按保持 | 明、暗各一次 |
| T60-08 | SecretField Preview / Gallery | 切换密码可见性 | Lucide Eye/EyeOff 切换，原密码遮挡和说明保持 | 明、暗各一次 |

对照网页端：input.tsx / textarea.tsx（输入禁用）、progress.tsx（颜色/轨道）、alert-dialog.tsx（内容/危险确认）、select.tsx / combobox.tsx（选择/描述）、badge.tsx（图标/标签）。Spinner 对照 Loader2 animate-spin。允许原生键盘、触摸目标与平台弹层差异；不确定条带是移动端新增表现。

## 5. 回归点
- 全部旧 String Select/Combobox、默认 TextField / ConfirmDialog / ListRow 调用显示与回调保持。
- 确认框默认可取消，目标始终显示；busy 结束后恢复关闭。
- 泛型选项 key 需稳定唯一；重复名称不能用名称作 key。
- 检查明暗色、TalkBack 进度语义、窄屏长描述、输入法和弹窗滚动。

## 6. 已知风险 / 未完成
- 未触发 CI，未运行本地 Gradle / 设备测试；统一编译与上述真机用例待协调方执行。
- L4 feature 迁移按范围保留；迁移清单中报告级候选需补确切源码行。
- ListRow enabled 不在本任务需求内，T47/T48 对应例外仍存在。
- busy Gallery 为固定展示态，需通过重新打开 Preview 或 Gallery 宿主重置；业务异步状态需 L4 真机验证。
