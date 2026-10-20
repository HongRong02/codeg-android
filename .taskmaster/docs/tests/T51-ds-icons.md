# T51 测试文档：设计系统 lucide 图标集

- 分支：`task/T51-ds-icons`　提交：本文件与代码所在提交（用 `git log -1 --format=%H -- .taskmaster/docs/tests/T51-ds-icons.md` 获取，最终报告列出 hash，避免文档自引用）。
- 依赖：无　测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 当前执行状态：Python 自测 4 项通过；325 图标离线再生成一致；1152 条生成 path 已用本机缓存 Compose `PathParser` 实际解析通过。未运行 Gradle、CI、JVM 单测、Preview 或真机；下列视觉用例均待协调方执行，尚无截图结果。

## 1. 测试范围

- 改动文件：
  - `scripts/gen_lucide_icons.py`：标准库生成器及 `--self-test`。
  - `scripts/lucide_icons.txt`：325 个排序、唯一的 kebab-case 图标名。
  - `app/src/main/java/app/codeg/android/core/designsystem/icon/LucideIcons.kt`：Lucide 命名空间与惰性 Gallery 注册表。
  - 同目录 `LucideIconsA_F.kt`、`LucideIconsG_L.kt`、`LucideIconsM_R.kt`、`LucideIconsS_Z.kt`：325 个图标，1152 个 path。
  - 同目录 `CodegIcon.kt`：包装组件、全量网格、明暗 Preview。
  - `app/src/test/java/app/codeg/android/core/designsystem/icon/LucideIconsTest.kt`。
  - `.taskmaster/docs/icon-map.md`：96 个 Material 引用形式（含样式、AutoMirrored）的映射与网页端定位。
  - `.taskmaster/docs/tests/T51-ds-icons.md`：本文。
- 新增公开 API：`object Lucide`；`val Lucide.Xxx: ImageVector`（按需 lazy 缓存）；`@Composable fun CodegIcon(icon: ImageVector, contentDescription: String?, modifier: Modifier = Modifier, size: Dp = 16.dp, tint: Color = LocalContentColor.current)`。
- 内部入口：`@Composable internal fun T51Gallery()`；同模块内供后续 Gallery 页面调用。
- 受影响页面/入口：无，仅组件；在 Gallery/@Preview 中验证。现有 Material 调用点均未修改。
- 明确不在范围内：调用点替换、业务逻辑、服务端、Gradle 依赖、导航、code-index、CI 配置、提交 SVG/tgz。

## 2. 编译检查

- 自评风险：
  - `LucideIconsA_F.kt:50` 等生成分组：依赖已有 Compose `ImageVector.Builder.addPath`、`PathParser.parsePathString().toNodes()`；已通过缓存 jar 的 `javap` 核对 API、实际解析所有 path，尚未验证 Kotlin 命名参数编译。
  - `LucideIcons.kt:48`：分组扩展属性与 Gallery 注册。其他包必须同时 import `Lucide` 和使用的扩展属性（或 `app.codeg.android.core.designsystem.icon.*`）；只 import object 不会导入扩展。
  - `LucideIconsM_R.kt:346`：`Lucide.Package` 首字母大写，不是 Kotlin 小写关键字 `package`。所有属性名已检查唯一且为合法标识符，`Grid2x2`、`Loader2`、`CheckCircle2` 等须保留准确大小写。
  - `LucideIconsA_F.kt:50`：最大分组 1252 行；每图标独立 lazy 初始化、每 path 一次解析；Gallery 注册分四个函数，降低单方法/类初始化体超限风险。未编译，仍需关注 Kotlin 后端 method too large / class too large。
  - `CodegIcon.kt:34`：`LocalContentColor.current` 在 Composable 默认参数中使用；`CodegIcon.kt:48` 网格使用现有 Foundation API，无新增实验性 API；`CodegIcon.kt:69`/`:77` Preview 显式传入现有 `ThemeMode.LIGHT/DARK`。
  - `LucideIconsTest.kt:21`：JVM 使用 Compose 图形值类型和 `VectorPath`，需由协调方验证测试运行环境；不使用 Android 绘图或设备接口。
- 预期协调方统一 `./gradlew :app:check` 关注：上述 Kotlin 编译、Compose lint、`LucideIconsTest` 两项；无新增依赖或资源。任务方不执行该命令。
- 可独立复现的脚本验证（不触发 CI/Gradle）：

```bash
python3 scripts/gen_lucide_icons.py --self-test
# 在 /tmp 执行 npm pack lucide-static@0.564.0 并 tar -xzf，SVG/tgz 仅保留 /tmp。
python3 scripts/gen_lucide_icons.py --svg-dir /tmp/t51-lucide/package/icons --web-root /mnt/e/claude/codeg/src
# 记录五个 LucideIcons*.kt 的 sha256，再执行离线命令，应逐文件一致。
python3 scripts/gen_lucide_icons.py --svg-dir /tmp/t51-lucide/package/icons
git diff --check
```

## 3. 单元测试

| 测试类::方法 | 覆盖点 |
|---|---|
| LucideIconsTest::allIconsHaveLucideViewportAndStroke | 遍历 325 图标，名字唯一；viewport/defaultSize 为 24；path 非空；无 fill、黑色 stroke、宽 2、Round cap/join；二次访问复用同一 ImageVector |
| LucideIconsTest::commonIconsPreserveUpstreamElementCounts | 24 个常用图标的 path 数等于固定版本 SVG 元素数量，含圆、多边形、矩形和复杂路径；包含 Package 名字可见性检查 |
| GeneratorTests::test_import_aliases（脚本 --self-test） | 多行 import、XIcon as Close、Calendar as CalendarIcon、type 排除、数字名字 |
| GeneratorTests::test_all_shapes（脚本 --self-test） | path、circle、ellipse、rect、line、polyline、polygon 转换结果 |
| GeneratorTests::test_rounded_rect_radii（脚本 --self-test） | rx/ry 单独提供、不同半径、半径上限、零圆角 |
| GeneratorTests::test_unsupported_element_fails（脚本 --self-test） | 不支持的元素明确报错，避免静默漏图 |

JVM 两项尚未执行；Python 四项已执行通过。额外临时 Java 检查使用缓存 Compose runtime jar 解析了全部 1152 path，临时检查器不作为项目依赖或提交内容。

## 4. 测试用例

前置条件：Gallery 无需服务器/数据；Android Studio 在统一编译后打开 `CodegIcon.kt` 的 `Lucide Light` / `Lucide Dark` Interactive Preview，或由协调方在同模块已有调试宿主挂载 `T51Gallery()`（需有有限高度，不能包在另一个纵向滚动容器中）。本任务不添加生产入口。真机由协调方统一操作，任务方不操作 adb。

网页端准备：打开下表对应页面/组件，使指定图标可见；网页与 App 同为明色或暗色。将网页 SVG 调至 16 CSS px、stroke-width=2、无 fill，与 Gallery 默认 16dp 比较；网页原位置若为 14px/20px，先截图记录原尺寸，再统一到 16px 评估几何。截图以逻辑尺寸对齐，允许平台抗锯齿的少量像素差，不允许形状、路径缺失、端点/接头样式差异。stroke=2 是 24 viewBox 单位，在 16dp 时约为 1.33dp，并非固定 2dp。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T51-01 | Lucide Light Preview / Gallery | 启用交互模式；从首项滚动到末项，逐行检查图标与名称并截图 | 325 个唯一名称；无空白/解析异常；图标随前景色着色、默认 16dp；名称可换行 | 明 |
| T51-02 | Lucide Dark Preview / Gallery | 与 T51-01 相同步骤，检查背景和前景 | 图标继承暗色前景，全部可辨；几何与明色一致 | 暗 |
| T51-03 | 同模块验证宿主 / CodegIcon | 分别显示 size=16/24/32.dp；在局部提供前景色，再显式传另一 tint；截图 | 默认 16dp；三种尺寸按比例缩放，视口不裁剪；默认继承 LocalContentColor、显式 tint 覆盖黑色 stroke | 明/暗 |
| T51-04 | 验证宿主 / 无障碍 | 同时显示带测试 contentDescription 的图标和 description=null 的装饰图标；开启 TalkBack 逐项聚焦 | 有描述图标朗读调用方提供文本；装饰图标不单独朗读；Gallery 通过名称 Text 识别 | 明/暗 |
| T51-05 | 生成脚本 | 执行上节自测及网页扫描生成，再从清单离线生成，比较 SHA-256；检查清单及映射覆盖 | 四项自测通过；两种生成结果一致；清单 325 项排序唯一；96 个 Material 引用形式均有映射；无 SVG/tgz 入库 | 不适用 |

以下每条都执行独立截图对照：在 Gallery 滚动定位指定名字 → 在对应网页位置显示该图标 → 各截一张明色图 → 切换两端暗色再次截图 → 按 16px/16dp 对齐比较轮廓、线宽、圆角端点与接头。每条执行后记录四张截图链接与通过/失败；当前全部待执行。

| 编号 | 入口/页面（网页端对照） | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T51-06 | MessageSquare；WEB/components/chat/conversation-context-bar.tsx:574 | 打开聊天模式选择，定位同名 Gallery 项并按上述流程截图 | 单闭合对话框与尾角一致；无 fill，圆角接头 | 明/暗 |
| T51-07 | RefreshCw；WEB/components/layout/aux-panel-git-changes-tab.tsx:1612 | 打开 Git 更多菜单刷新项，对照截图 | 四段路径、两侧箭头方向和弧线一致，端点为 Round | 明/暗 |
| T51-08 | Ellipsis；WEB/components/conversations/sidebar-section-header.tsx:245 | 打开会话分区标题，对照截图 | 三个等间距水平空心圆，线宽一致，无实心填充 | 明/暗 |
| T51-09 | EllipsisVertical；WEB/components/conversations/conversation-detail-header.tsx:309 | 显示会话详情更多入口，对照截图 | 三个等间距竖向空心圆；不与横向更多混淆 | 明/暗 |
| T51-10 | Search；WEB/components/ui/command.tsx:64 | 打开命令选择组件，对照截图 | 放大镜圆与斜柄位置、连接及圆端点一致 | 明/暗 |
| T51-11 | Plus；WEB/components/chat/composer/composer-add-menu.tsx:138 | 显示输入栏添加按钮，对照截图 | 横竖两笔居中，等长、等宽、Round cap | 明/暗 |
| T51-12 | X；WEB/components/ui/dialog.tsx:119 | 打开可关闭对话框，对照截图 | 两条交叉斜线位置一致，四端均为 Round | 明/暗 |
| T51-13 | Check；WEB/components/ui/dropdown-menu.tsx:129 | 打开勾选式菜单并选中一项，对照截图 | 勾号两笔比例与拐角一致；无额外填充 | 明/暗 |
| T51-14 | ChevronDown；WEB/components/ai-elements/tool.tsx:118 | 显示未展开工具标题，对照截图 | 向下折线一致，中心接头圆润，端点 Round | 明/暗 |
| T51-15 | ChevronRight；WEB/components/shared/server-file-browser-dialog.tsx:255 | 打开文件浏览目录层级，对照截图 | 向右折线与网页一致，非粗 Material 箭头 | 明/暗 |
| T51-16 | Folder；WEB/components/shared/folder-select.tsx:62 | 打开目录选择器，对照截图 | 文件夹标签、外框和圆角一致；内部透明 | 明/暗 |
| T51-17 | FolderOpen；WEB/components/conversations/sidebar-conversation-list.tsx:458 | 展示侧栏目录标题，对照截图 | 打开文件夹斜边、标签和路径衔接一致 | 明/暗 |
| T51-18 | Copy；WEB/components/ai-elements/mermaid-block.tsx:579 | 显示 Mermaid 复制按钮的未复制状态，对照截图 | 两层矩形错位及遮挡一致；圆角矩形不变成直角 | 明/暗 |
| T51-19 | Trash2；WEB/components/layout/workspace-folder-dialog.tsx:938 | 显示目录删除入口但不执行删除，对照截图 | 盖子、把手、桶体和两竖线完整；圆端点一致 | 明/暗 |
| T51-20 | Pencil；WEB/components/layout/workspace-folder-dialog.tsx:851 | 打开目录编辑入口，对照截图 | 笔尖、笔身、斜切线一致；无实心填充 | 明/暗 |
| T51-21 | ArrowLeft；WEB/components/layout/workspace-folder-dialog.tsx:475 | 打开目录选择内页返回入口，对照截图 | 左向箭头和横杆一致，Round cap/join，无自动翻转 | 明/暗 |
| T51-22 | ArrowUp；WEB/components/message/selection-action-bubble.tsx:473 | 选择消息文本显示操作浮层，对照截图 | 上箭头与竖杆比例、中心对齐一致 | 明/暗 |
| T51-23 | Square；WEB/components/chat/message-input.tsx:1812 | 会话生成中显示停止按钮，对照截图 | 圆角方框；内部透明；无 Material 实心方块 | 明/暗 |
| T51-24 | Circle；WEB/components/ai-elements/tool.tsx:59 | 展示工具输入流式状态，对照截图 | 圆形闭合平滑；两个弧段衔接不出现缺口 | 明/暗 |
| T51-25 | ShieldAlert；WEB/components/chat/permission-dialog.tsx:126 | 触发权限请求并保持待确认，对照截图 | 盾牌、感叹号竖线和圆点完整；不发生填充反转 | 明/暗 |
| T51-26 | Brain；WEB/components/ai-elements/reasoning.tsx:186 | 显示推理区标题，对照截图 | 八段复杂曲线均保留，无缺线、交点位移、尖角 | 明/暗 |
| T51-27 | Calendar；WEB/components/token-usage/token-usage-filters.tsx:400 | 显示日期范围筛选触发器，对照截图 | 外框圆角、两条挂钩、顶部分隔线一致 | 明/暗 |
| T51-28 | Loader2；WEB/components/shared/settings-section.tsx:285 | 显示设置区加载状态，暂停网页 CSS 动画后截图对照 | 单条开口弧形一致；Gallery 为静态向量，不负责旋转动画 | 明/暗 |
| T51-29 | FolderPlus；WEB/components/shared/directory-browser.tsx:701 | 打开目录浏览新建目录入口，对照截图 | 文件夹及加号完整，圆角与端点一致 | 明/暗 |

| T51-30 | KeyRound；WEB/components/chat/permission-dialog.tsx:289 | 显示权限记住选项，对照 Gallery 并按上述流程截图 | 钥匙齿与头部小圆点可辨；上游小圆 fill 被统一省略后，粗描边形成的圆点外观仍一致 | 明/暗 |
| T51-31 | Palette；WEB/components/settings/settings-shell.tsx:72 | 显示设置外观导航，对照 Gallery 并按上述流程截图 | 调色盘轮廓、四个半径 0.5 的小圆点完整；特别核对缩小到 16dp 后的抗锯齿与实心感 | 明/暗 |

对照网页端：各用例已列出具体文件/行；明暗主题、尺寸统一后对照形状。允许字体、平台抗锯齿和网页 CSS 动画差异；网页原位尺寸/透明度是调用方样式，不烘焙进 ImageVector。验收时把截图链接与设备密度/主题记在执行记录中。

## 5. 回归点

- 原会话列表、项目、设置、聊天输入栏仍使用 Material 图标：检查截图/操作保持原行为，本次没有调用点替换。
- 后续替换任务须按 `icon-map.md` 区分计划待办 CircleDashed、计划完成 CircleCheckBig、横向 Ellipsis、竖向 EllipsisVertical；检查实际页面语义，不能只按 Material 外观机械替换。
- 检查颜色继承：按钮、菜单、禁用容器传入的 LocalContentColor 在图标中生效；显式 tint 同样生效，无黑色残留。
- 检查 Gallery 快速滚动及重新进入：所有图标均能加载，名称与图形不串位；lazy 返回同一实例。
- 检查 RTL 页面方向图标：lucide 保持上游方向，不自动镜像；后续调用方需明确其导航语义。
- 检查仓库提交：无旧文件修改、SVG/tgz、依赖、code-index 或 tasks.json 变更。

## 6. 已知风险 / 未完成

- 编译、JVM 单测、Preview、真机及 26 个常用图标明暗截图对照未执行，需协调方统一验证。当前静态/API/路径解析检查不等同于 Android 编译通过。
- 一些 Material 图标在网页端没有同名或同功能 JSX：映射表明确标记语义补充；同一 Material 图标在不同页面可能需不同 lucide 图标（例如设置导航 MCP/模型服务/智能体），由后续替换任务按实际网页上下文确认。
- 上游 KeyRound、Palette、Tag、Tags 共 7 个小圆点显式带 `fill=currentColor`。按任务要求生成器统一使用 `fill=null`；这些圆点半径仅 0.5，stroke 宽 2，描边已覆盖圆心，但 16dp 抗锯齿差异需通过 T51-30/31 及 Tag/Tags 的 Gallery 检查确认。
- 本任务只提供基础向量；旋转动画、方向镜像、页面特定尺寸/透明度仍由调用方负责。
- 生成器对未知 SVG 元素、transform、非 24×24 viewport 明确失败；不支持任意第三方 SVG。输入限定任务指定 lucide-static@0.564.0。
- 标准库脚本无需 npm 运行时依赖；npm 仅用于在 /tmp 获取固定版本素材。生成文件包含包内完整 ISC 与所附 Feather MIT 许可说明。
