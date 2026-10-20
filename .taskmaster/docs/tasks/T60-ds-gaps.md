# T60 设计系统：补齐第二阶段报告的接口缺口

> 先读 `.taskmaster/docs/CONVENTIONS.md`（**测试文档模式**）和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

| 字段 | 值 |
|---|---|
| 批次 / 层级 / 组 | UI1 / L3 / 组 D 设计系统收尾（关系图：`.taskmaster/docs/graphs/UI1.md`） |
| 硬依赖 ← | T43–T46、T47–T54（均已合入 sit） |
| 软依赖 ⇠ | 无 |
| 同层并行 | T59 ⟂（冲突域不相交） |
| 下游 | T55–T58（用本任务的新参数替换第二阶段留下的例外） |
| 冲突域 | `A/core/designsystem/component/` 下的 `TextFields.kt`、`Feedback.kt`、`Dialogs.kt`、`Controls.kt`、`Badges.kt`、`Buttons.kt`、`ListRows.kt` |
| 分支 / 基准 | `task/T60-ds-gaps` / 派发时的 `sit` |
| 测试方式 | 测试文档模式：写 `.taskmaster/docs/tests/T60-ds-gaps.md` |
| 优先级 | high（L4 关键路径） |

## 目标
T47–T50 因为共享组件缺少参数，保留了一批 M3 原件或改用了变通写法（详见 `.taskmaster/docs/tests/T47-ui-sessions.md`、`T48-ui-projects.md`、`T49-ui-settings.md`、`T50-ui-misc.md` 中的"例外/保留"小节）。本任务补齐这些缺口，**只追加带默认值的参数或新增重载，保证现有调用点源码兼容**。不改任何 feature 文件。

## 需求
1. `CodegTextField`：追加 `enabled: Boolean = true`、`readOnly: Boolean = false`、`keyboardOptions: KeyboardOptions? = null`（非空时覆盖 `keyboardType`）、`keyboardActions: KeyboardActions = KeyboardActions.Default`、`leading: (@Composable () -> Unit)? = null`、`trailing: (@Composable () -> Unit)? = null`、`minLines`/`maxLines`（textarea 变体已有的话复用）。disabled 样式按 `WEB/components/ui/input.tsx`（`disabled:opacity-50`、不可聚焦）。
2. `CodegProgress`：新增不确定进度重载 `CodegProgress(modifier: Modifier = Modifier)`（无 progress 参数，条带循环动画，颜色同确定进度）。
3. `Feedback.kt` 新增 `CodegSpinner(modifier, size: Dp = 16.dp)`，对应网页端 `Loader2 animate-spin`（图标用 `Lucide.Loader2`/`LoaderCircle`，以 T51 生成的名字为准），颜色默认 `LocalContentColor`。
4. `CodegConfirmDialog`：追加 `confirmEnabled: Boolean = true`、`busy: Boolean = false`（busy 时确认按钮显示 loading 并禁用、禁止点遮罩关闭）、`content: (@Composable ColumnScope.() -> Unit)? = null`（例如反馈输入、时间校验），放在描述与 footer 之间。
5. `CodegSelect` / `CodegCombobox`：新增泛型重载 `CodegSelect<T>(label: String, selected: T?, options: List<T>, optionLabel: (T) -> String, onSelect: (T) -> Unit, modifier, enabled, optionDescription: ((T) -> String?)? = null, key: ((T) -> Any)? = null)`，Combobox 同理。现有 String 版本改为委托泛型版本，签名不变。同名选项靠 `optionDescription` 区分（例如项目路径）。
6. `CodegFilterChip` 统一：保留 `Badges.kt` 中的版本并追加 `icon: ImageVector? = null`、`enabled: Boolean = true`；删除 `Buttons.kt` 中的旧版。删除前 `grep -rn "CodegFilterChip(" A/` 检查全部调用点：命名参数调用都能兼容；如果有按位置传 `label` 在前的调用，**不要改 feature 文件**，改为保留旧版并加 `@Deprecated(ReplaceWith(...))`，在测试文档里列出这些调用点交给 L4。
7. `CodegListRow`：追加 `titleMaxLines: Int = 1`、`subtitleMaxLines: Int = 1`（T47 问题卡片需要长选项换行）。
8. 本任务涉及的文件中，Material 图标一律改用 `Lucide.*`（按 `.taskmaster/docs/icon-map.md`）。
9. 更新各文件已有的 `TxxGallery()`，加入新变体；可测试的状态逻辑补 JVM 单测。

## 改动范围
- 只限"冲突域"中列出的 7 个文件；如需新字符串用 `strings_ds_gaps.xml` + zh；单测；测试文档。
- 不改 `A/feature/**`。范围外文件一律不改；不新增 Gradle 依赖。

## 代码定位
- 上述 7 个文件（全文可读）；`A/core/designsystem/icon/`（只查 `Lucide` 属性名）
- 调用点：`grep -rn "CodegTextField(\|CodegProgress(\|CodegConfirmDialog(\|CodegSelect(\|CodegCombobox(\|CodegFilterChip(\|CodegListRow(" A/feature` —— 只看不改，用于确认兼容性
- 缺口来源：`.taskmaster/docs/tests/T47–T50` 四份测试文档里的"例外/保留/编译风险"小节

## 参考（只读）
- `WEB/components/ui/input.tsx`、`textarea.tsx`、`progress.tsx`、`alert-dialog.tsx`、`select.tsx`、`combobox.tsx`、`badge.tsx`

## 验收
- 测试文档按模板写全：每个新参数/重载的新旧签名对照；兼容性检查结果（调用点数量、是否全部源码兼容）；给 L4 的迁移清单（第二阶段的哪些例外现在可以用哪个新参数替换，按 feature 目录分组，写明 文件:行）；Gallery 用例（明/暗）。
