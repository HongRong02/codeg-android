# T53 测试文档：折叠、头像、输入组、图片预览、命令列表、文件夹徽章

- 分支：`task/T53-ds-misc`　提交：包含本文档的代码提交，可用 `git log -1 --oneline -- .taskmaster/docs/tests/T53-ds-misc.md` 查询（避免在提交内容里自引用 hash）。
- 依赖：无；图标暂用 Material，均留有 `TODO(T51): Lucide.Xxx`。测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 静态结果：六组 Gallery、12 个 Preview、中英 XML 键与格式占位符、组件字符串引用和颜色约束检查通过；代码审查发现的长图平移边界已修正并复查。
- 执行状态：本任务只做静态审查及资源/范围检查；未运行 Gradle、CI、JVM 单测或真机测试。下列用例全部待协调方执行。

## 1. 测试范围

改动文件（`A/` 为 `app/src/main/java/app/codeg/android/`）：

- `A/core/designsystem/component/Collapsible.kt`
- `A/core/designsystem/component/Avatar.kt`
- `A/core/designsystem/component/InputGroup.kt`
- `A/core/designsystem/component/ImagePreview.kt`
- `A/core/designsystem/component/Command.kt`
- `A/core/designsystem/component/FolderBadge.kt`
- `app/src/main/res/values/strings_ds_misc.xml`
- `app/src/main/res/values-zh-rCN/strings_ds_misc.xml`
- `app/src/test/java/app/codeg/android/core/designsystem/component/DsMiscTest.kt`
- `.taskmaster/docs/tests/T53-ds-misc.md`

新增公开 API（签名中 slot 均为 `@Composable`）：

- `CodegCollapsible(expanded: Boolean, onExpandedChange: (Boolean) -> Unit, header: () -> Unit, modifier: Modifier = Modifier, content: () -> Unit)`。
- `CodegInstantCollapsible(...)`：同上，立即挂载/卸载内容，箭头立即切换。
- `CodegAvatar(fallbackText: String, modifier: Modifier = Modifier, painter: Painter? = null, size: Dp = 32.dp)`。
- `CodegInputGroup(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, leading: (() -> Unit)? = null, trailing: (() -> Unit)? = null, placeholder: String = "", enabled: Boolean = true, readOnly: Boolean = false, isError: Boolean = false, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, keyboardActions: KeyboardActions = KeyboardActions.Default)`。
- `CodegPreviewImage(painter: Painter, description: String)`；`CodegImagePreviewDialog(images: List<CodegPreviewImage>, initialIndex: Int = 0, onDismiss: () -> Unit)`。
- `CodegCommandItem(id: String, label: String, keywords: List<String> = emptyList(), enabled: Boolean = true)`；`CodegCommandGroup(heading: String, items: List<CodegCommandItem>)`。
- `CodegCommandList(query: String, onQueryChange: (String) -> Unit, groups: List<CodegCommandGroup>, onSelect: (CodegCommandItem) -> Unit, modifier: Modifier = Modifier, empty: String = 本地化无结果文案, placeholder: String = 本地化搜索文案)`。
- `CodegFolderBadge(name: String, modifier: Modifier = Modifier, color: Color? = null)`；覆盖颜色应传 `web` token，并与 `primaryForeground` 保持对比度。
- 六文件各有 `internal @Composable fun T53XxxGallery()`，以及明、暗 `@Preview`。

受影响页面/入口：无，仅组件；在 Gallery/@Preview 中验证。协调方统一构建时将 Gallery 接入内部预览宿主；`ImagePreviewGallery` 内联渲染内容便于 Preview，关闭/返回键用例需宿主以 `show` 状态调用公开 Dialog，`onDismiss` 将 `show` 设为 false。

明确不在范围内：业务页面接线、图片 URL 下载/解码、依赖配置、T45 共享输入/按钮、T51 图标替换、服务端、code-index。

## 2. 编译检查

以下路径均相对 `A/core/designsystem/component/`，行号以本提交源码为准：

- `Collapsible.kt:3`：`AnimatedVisibility`、`animateFloatAsState` 和 `Modifier.weight`；无实验性 API，需统一编译确认 Compose 版本与 import。
- `Avatar.kt:45`：Unicode 首码点使用 Java `Character.toChars` 和 `Locale.ROOT`；JVM 测试要覆盖代理对，空值不访问首字符。
- `InputGroup.kt:56`：`drawBehind`/`Stroke` 画外侧 3dp ring，不改变测量尺寸；`BasicTextField` 使用已有 Compose foundation API，验证键盘选项与 decorationBox 重载解析。
- `ImagePreview.kt:61`：`DialogProperties` 全屏参数、`safeDrawingPadding`、双 `pointerInput` 与 `graphicsLayer`；无新增依赖或显式实验 API，需确认项目 Compose 版本可编译。双击/双指事件协调必须真机验证。
- `Command.kt:61`：`stringResource` 作为 Composable 默认参数；`LaunchedEffect` 调用 `LazyListState.animateScrollToItem`；键盘事件扩展 import 与泛型 `List<String?>` 需统一编译确认。
- `FolderBadge.kt:22`：默认参数 nullable Color，背景取 `web.primary`，无新增依赖。
- 单测与被测 `internal` 函数在同模块，依赖 Kotlin 测试 friend paths；JUnit4 的 `assertEquals(Float, Float, Float)` 已显式传 delta。

预期统一 `./gradlew :app:check` 重点：六文件 Compose 编译、资源生成、中英字符串占位符一致、Material 图标解析、`DsMiscTest` 八个方法。可能出现 Material 左右图标的弃用提示，T51 替换时处理。未宣称编译或测试通过。

## 3. 单元测试

| 测试类::方法 | 覆盖点 |
|---|---|
| DsMiscTest::filtersLabelsAndKeywordsWithoutCase | 标签/关键词、不区分大小写、查询首尾空白 |
| DsMiscTest::removesEmptyGroupsAndPreservesOrder | 无匹配、空输入、空列表、空分组、顺序保留 |
| DsMiscTest::keepsDisabledItemsVisible | 禁用项参与过滤但保持禁用属性 |
| DsMiscTest::clampsScaleIncludingNonFiniteValues | 1–5 倍边界、正常值、NaN、Infinity |
| DsMiscTest::clampsIndexForEmptyAndChangedLists | 空列表、负索引、超过末尾 |
| DsMiscTest::clampsPanAndResetsAtOriginalScale | 原始尺寸归零、横纵通用平移上下界、非有限值 |
| DsMiscTest::fitsPortraitAndLandscapeBeforeClampingPan | Fit 后横图/纵图真实尺寸、窄轴不允许平移、无固有尺寸 Painter 回退 |
| DsMiscTest::initialsHandleBlankWhitespaceAndUnicode | 空白、首字母、中文、emoji 代理对 |

## 4. 测试用例

前置条件：无需服务器；宿主以 `CodegTheme` 包裹对应 Gallery/组件。分别设 LIGHT/DARK，测试中文、英文；RTL 使用 `CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl)`，不修改系统。准备普通横图、纵图、1px 图以及由调用方降采样后的 12000×8000 来源图；用 `BitmapPainter` 传入，不把原尺寸大图强行全部解码进内存。Command 准备两个分组、一个禁用项、关键词、100 条数据及 200 字长标签；回调显示计数和最近选中 id。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T53-01 | CollapsibleGallery | 点击普通组标题两次，动画中快速反向点击 | 展开/收起回调各一次；内容及 chevron 随受控状态变化，最终状态正确 | 明 |
| T53-02 | CollapsibleGallery | 点击即时组；将 header 换为 200 字及 RTL 文字；TalkBack 聚焦 | 内容立即挂载/卸载；箭头立即切换；标题不挤出箭头；读出展开/折叠状态 | 暗 |
| T53-03 | AvatarGallery | 比较 24/32/40dp，传普通图 Painter 与 null | 图像圆形居中裁剪；fallback muted 背景与首字符；边框 1dp | 明 |
| T53-04 | AvatarGallery | 依次传空白、中文、emoji 和超长 RTL 名称 | 空白无字符；中文/emoji 完整首码点；超长名称不扩大圆；TalkBack 读完整名称 | 暗 |
| T53-05 | InputGroupGallery | 搜索输入、聚焦/失焦、点内嵌清除；记录组件尺寸 | leading/trailing 在一个边框内；输入回调正确；清除为空；3dp ring 出现不改变尺寸 | 明 |
| T53-06 | InputGroupGallery | 检查 error、disabled、readOnly；200 字输入；开启 RTL | error 使用 destructive；disabled 不可编辑；readOnly 可读不可改；长文单行滚动；前后缀随 RTL 排列 | 暗 |
| T53-07 | ImagePreviewDialog | 打开三张图 initialIndex=1；双击、双指、平移；点前后按钮 | 首显 2/3；双击 1↔2 倍；双指限制 1–5 倍；平移有限；切图归 1 倍居中；首尾按钮禁用 | 明 |
| T53-08 | ImagePreviewDialog | 空列表、initialIndex=-1/999；打开后缩短列表；关闭按钮和系统返回键分别操作 | 空列表提示可关闭、无索引；索引安全夹取；数据变化重新按 initialIndex 定位；关闭回调有效，无越界崩溃 | 暗 |
| T53-09 | ImagePreviewDialog | 传降采样大图、纵图/1px 图；旋转屏幕/改变窗口尺寸；重复缩放并开启 RTL | Fit 显示不扭曲；窄轴不得拖离视口形成全空白；内容裁剪于视口，工具按钮始终可见；边界安全；RTL 上一张/下一张图标方向与排列对应 | 明/暗 |
| T53-10 | CommandGallery | 搜标签子串和混合大小写关键词，输入无结果；清空 | 仅匹配项出现，空分组消失；空结果文本居中；清空恢复顺序；第一可用项 accent 高亮 | 明 |
| T53-11 | CommandGallery | 点击禁用项/可用项；搜索框聚焦后键盘上下/Enter；100 条列表滚动 | 禁用项不回调；可用项传正确 id；上下跳过禁用项且循环，当前高亮滚动可见；Enter 只调用一次 | 暗 |
| T53-12 | CommandGallery | groups=emptyList/仅空组/仅禁用项；200 字标题与条目；RTL | 空组显示空提示；仅禁用项可见但无活动项；长文省略，无横向溢出；RTL 对齐正常 | 明/暗 |
| T53-13 | FolderBadgeGallery | 默认 name=Codeg，再传 name=中文，检查尺寸与读屏 | 14×14dp、4dp 圆角、9sp 首码点；primary/primaryForeground；读出完整名称 | 明 |
| T53-14 | FolderBadgeGallery | 空白、200 字、emoji、RTL 名称；color=web.secondary（用对应对比度主题） | 空白为无字色块；其余仅首码点；不随长文字膨胀；覆盖色生效，验证前景对比度 | 暗 |

对照网页端（编号一一对应）：

- 01–02：`collapsible.tsx`/`instant-collapsible.tsx`；网页根节点无固定 padding，Android 由 header/content slot 控制；即时版本按任务要求不做动画。
- 03–04：`avatar.tsx`；24/32/40、圆形、muted；允许无 CSS 混色边框，使用 token 实线；图片加载失败由调用方传 null。
- 05–06：`input-group.tsx`；36dp 最小高度、26dp 圆角、input/30、1dp 边框与 3dp ring；移动端触控附件按钮可扩大触控范围，大字体时允许增高；不提供网页块级 textarea 变体。
- 07–09：`image-preview-dialog.tsx`；原网页只有单图，本任务扩展多图索引和手势。按颜色约束使用 web.background 全屏底色，取代网页黑色透明 overlay；保留安全区和上下 64dp 工具区，非严格 90vh/90vw。切换通过前后按钮，不提供滑动翻页；无下载/复制功能要求。
- 10–12：`command.tsx`；搜索 40dp、图标 16dp，列表最高 300dp、标题 12sp，行 14sp、8/6dp padding、6dp 圆角、accent 高亮；过滤采用子串/关键词，不复现 cmdk 模糊排序。
- 13–14：`folder-badge.tsx`；实现 sm 14dp/9sp/4dp；按本任务 name/color API 使用首码点和 token 色，不读取范围外网页 hash 配色算法或新增 folderId API。
- 移动端不要求 hover；读屏、键盘焦点与触摸以 Compose 原生行为为准。

## 5. 回归点

- 确认未改 feature、TextFields/Buttons/Controls、主题文件和依赖；已有页面不应出现新入口或行为变化。
- 合并 T51 后逐一替换 TODO 的 Search/X/ChevronDown/ChevronLeft/ChevronRight，复查大小与读屏名称。
- 在多个已有主题下重复 05/07/10，特别检查半透明 input token 未被覆盖 alpha，错误/焦点 ring 对比度可辨。
- Gallery 嵌入滚动页面时 Command 有 300dp 上界；全屏 ImagePreviewGallery 应置于有界容器，不放进无界竖向滚动宿主。
- 真正业务接线时验证 Painter 解码/降采样、回调关闭状态及受控 expanded/query 不被宿主丢弃。

## 6. 已知风险 / 未完成

- 编译、JVM 单测、明暗 Preview 渲染及真机手势尚未执行，由协调方统一验证；本任务没有编译成功结论。
- 超大图片内存管理由提供 Painter 的调用方负责，组件只限制显示尺寸与缩放/平移，不承诺未经采样原图的内存安全。
- 暂用 Material 图标，T51 后替换；无业务入口接线。除上述集成验证外无计划内遗漏。
