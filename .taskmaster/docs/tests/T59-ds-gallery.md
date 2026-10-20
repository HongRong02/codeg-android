# T59 测试文档：debug 组件预览页与设计系统 lucide 图标

- 分支：`task/T59-ds-gallery`　提交：与本文同次提交，准确 hash 见交付报告（避免文档自引用提交 hash）。
- 依赖：T43–T46、T51–T54 已合入；T60 通过现有 Gallery 自动包含。测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。
- 执行状态：仅完成静态检查；下列构建、安装、adb 与真机步骤均待协调方执行。未触发 CI，未运行本地 Gradle。

## 1. 测试范围

改动文件（`A/` = `app/src/main/java/app/codeg/android/`）：

- `A/MainActivity.kt`
- `A/feature/main/MainShell.kt`
- `A/feature/devgallery/DevGalleryScreen.kt`
- `A/core/designsystem/component/Menus.kt`
- `A/core/designsystem/component/Sheets.kt`
- `A/core/designsystem/component/Toasts.kt`
- `A/core/designsystem/component/Collapsible.kt`
- `A/core/designsystem/component/InputGroup.kt`
- `A/core/designsystem/component/ImagePreview.kt`
- `A/core/designsystem/component/Command.kt`
- `A/core/designsystem/component/StateViews.kt`
- `app/src/main/res/values/strings_dev_gallery.xml`
- `app/src/main/res/values-zh-rCN/strings_dev_gallery.xml`
- `.taskmaster/docs/tests/T59-ds-gallery.md`

新增/变更公开 API：`@Composable fun DevGalleryScreen(onBack: () -> Unit)`；其余组件签名不变。MainShell 仅在 debug 注册 `devgallery` 路由。

入口：Activity 冷启动或 `onNewIntent` 收到 `route=devgallery` 时，仅 debug 直接显示 Gallery，独立于服务器配置/引导页；退出后恢复 CodegApp。采用直接承载方式，无需跨范围修改 CodegApp 传参。MainShell 另有同名 debug 路由供内部导航使用。

明确不在范围内：T60 的七个组件文件（只调用其已有 Gallery）、图标生成文件、全局主题偏好、服务端、Gradle、code-index、graphs；Cards/Tabs/Avatar/FolderBadge/TopBar 检索无待替换图标，保持不变。

## 2. 编译检查

自评编译风险点（行号以本提交为准）：

- `A/MainActivity.kt:24`：Activity 使用 Compose mutableStateOf；debug 判断同时覆盖 onCreate、onNewIntent 与渲染分支，新增 DevGalleryScreen import。
- `A/feature/main/MainShell.kt:270`：仅追加 debug 条件内路由，使用全限定类名，不改变现有路由。
- `A/feature/devgallery/DevGalleryScreen.kt:23`：internal Gallery 均在同一 app 模块可见；CodegTheme 使用实际 themeMode 参数（LIGHT/DARK），未新增 darkTheme 参数。
- `A/feature/devgallery/DevGalleryScreen.kt:56`：T44/T45/T54 自带滚动、T51 网格、T52 Toast Host 均获得有限高度，需真机确认无无限高度异常；其余样例由页面滚动。
- `A/core/designsystem/component/Toasts.kt:203`：加载图标新增 rememberInfiniteTransition/animateFloat 与 getValue 委托（runtime.*），保留 18dp 与旋转；确认 Compose animation API 编译及 lint。
- `A/core/designsystem/component/StateViews.kt:90`：Loader2 使用相同动画 API，显式引入 getValue，保留 16dp；其余调用方传入的图标语义不变。

静态检查结果：`git diff --check` 通过；检索到的 18 个 Gallery 声明均有调用；13 个允许组件文件无 `Icons.*`、无 `TODO(T51)`；所有新 Lucide 属性可在现有生成文件中找到；两份 XML 可解析、键集合一致，页面字符串引用齐全。静态检查不代表编译通过。

协调方运行 `./gradlew :app:check` 时关注：Kotlin import/Composable 可见性、资源生成、动画 API、现有设计系统单测。页面只调用现有组件，未新增需要 OptIn 的组件 API；TopBar/StateViews 原有 OptIn 保留。

## 3. 单元测试

| 测试类::方法 | 覆盖点 |
|---|---|
| 无新增 | 本次为 UI 编排、debug 门控和图标替换，无新增解析、映射或独立状态推导算法；交互以以下真机用例验证。现有组件单测由协调方统一执行。 |

## 4. 测试用例

前置条件：无需服务器，debug APK；分别以中文和英文检查标题、返回与主题按钮无资源缺失。每次在分组列表选择任务，再选择列出的 Gallery 函数；系统返回和顶部返回均应逐级回到函数列表、分组列表、正常 App。

入口命令（仅协调方执行）：

```bash
adb shell am start -n app.codeg.android.mine/app.codeg.android.MainActivity --es route devgallery
```

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T59-01 | T43 菜单 | 切换为明色；打开 T43Gallery；展开下拉/更多菜单、长按上下文菜单，打开 popover、长按 tooltip | 菜单/禁用项/勾选/分隔线可见，弹层可关闭，Check、EllipsisVertical、ChevronRight 显示正确 | 明 |
| T59-02 | T43 菜单 | 切换为暗色；打开 T43Gallery；展开下拉/更多菜单、长按上下文菜单，打开 popover、长按 tooltip | 菜单/禁用项/勾选/分隔线可见，弹层可关闭，Check、EllipsisVertical、ChevronRight 显示正确 | 暗 |
| T59-03 | T44 对话框/抽屉/卡片 | 切换为明色；分别打开 T44Gallery、T44CardsGallery；触发 dialog/confirm/destructive/sheet/full | 所有卡片与弹层可见；确认与取消正常，完整抽屉关闭图标为 X，内容可滚动 | 明 |
| T59-04 | T44 对话框/抽屉/卡片 | 切换为暗色；分别打开 T44Gallery、T44CardsGallery；触发 dialog/confirm/destructive/sheet/full | 所有卡片与弹层可见；确认与取消正常，完整抽屉关闭图标为 X，内容可滚动 | 暗 |
| T59-05 | T45 按钮/表单 | 切换为明色；打开 T45Gallery，滚动到底；操作按钮、文本输入、选项、复选与切换控件 | 全部现有变体可达，输入和选择生效，无滚动测量异常 | 明 |
| T59-06 | T45 按钮/表单 | 切换为暗色；打开 T45Gallery，滚动到底；操作按钮、文本输入、选项、复选与切换控件 | 全部现有变体可达，输入和选择生效，无滚动测量异常 | 暗 |
| T59-07 | T46 标签/徽章/列表行/反馈 | 切换为明色；逐一打开 T46Gallery、T46TabsGallery、T46BadgesGallery、T46RowsGallery、T46FeedbackGallery | 全部五个函数入口存在，标签与行操作正常，反馈状态可见 | 明 |
| T59-08 | T46 标签/徽章/列表行/反馈 | 切换为暗色；逐一打开 T46Gallery、T46TabsGallery、T46BadgesGallery、T46RowsGallery、T46FeedbackGallery | 全部五个函数入口存在，标签与行操作正常，反馈状态可见 | 暗 |
| T59-09 | T51 图标 | 切换为明色；打开 T51Gallery，滚动网格到底 | 图标与属性名成对显示，无无限高度异常或缺失图标 | 明 |
| T59-10 | T51 图标 | 切换为暗色；打开 T51Gallery，滚动网格到底 | 图标与属性名成对显示，无无限高度异常或缺失图标 | 暗 |
| T59-11 | T52 Toast | 切换为明色；打开 T52Gallery；触发 success/error/info/action/warning/loading/finish；尝试滑动关闭 | Toast 可见且有正确 lucide 图标；加载旋转，finish 更新成功，action 可触发；无裁切遮挡 | 明 |
| T59-12 | T52 Toast | 切换为暗色；打开 T52Gallery；触发 success/error/info/action/warning/loading/finish；尝试滑动关闭 | Toast 可见且有正确 lucide 图标；加载旋转，finish 更新成功，action 可触发；无裁切遮挡 | 暗 |
| T59-13 | T53 杂项 | 切换为明色；逐一打开 T53CollapsibleGallery、T53AvatarGallery、T53InputGroupGallery、T53ImagePreviewGallery、T53CommandGallery、T53FolderBadgeGallery；测试折叠、清空、图片前后切换/缩放、命令搜索 | 六个入口均可达；ChevronDown、Search、X、ChevronLeft/Right 正确，图片边界按钮禁用正常 | 明 |
| T59-14 | T53 杂项 | 切换为暗色；逐一打开 T53CollapsibleGallery、T53AvatarGallery、T53InputGroupGallery、T53ImagePreviewGallery、T53CommandGallery、T53FolderBadgeGallery；测试折叠、清空、图片前后切换/缩放、命令搜索 | 六个入口均可达；ChevronDown、Search、X、ChevronLeft/Right 正确，图片边界按钮禁用正常 | 暗 |
| T59-15 | T54 顶栏/状态 | 切换为明色；打开 T54Gallery 并滚动，观察普通/禁用顶栏按钮、空态、加载与错误态 | 顶栏/状态全可见；Info、X、旋转 Loader2 正确，16dp 加载图标不改变行布局 | 明 |
| T59-16 | T54 顶栏/状态 | 切换为暗色；打开 T54Gallery 并滚动，观察普通/禁用顶栏按钮、空态、加载与错误态 | 顶栏/状态全可见；Info、X、旋转 Loader2 正确，16dp 加载图标不改变行布局 | 暗 |
| T59-17 | T60 缺口补齐 | 切换为明色；打开 T60 分组，再打开现有 T44Gallery、T45Gallery 与 T46Gallery | 显示复用现有 Gallery 的说明，三份样例可用；T60 扩展这些函数时自动包含其内容 | 明 |
| T59-18 | T60 缺口补齐 | 切换为暗色；打开 T60 分组，再打开现有 T44Gallery、T45Gallery 与 T46Gallery | 显示复用现有 Gallery 的说明，三份样例可用；T60 扩展这些函数时自动包含其内容 | 暗 |
| T59-19 | debug 启动与返回 | 无服务器配置时冷启动执行入口命令；逐级返回至 App；已运行时再次执行命令 | 直接进入分组列表；返回正常 App；onNewIntent 重入可用 | 明/暗 |
| T59-20 | 局部主题与恢复 | 先记录 App 主题；进入 Gallery 切换，打开样例，旋转屏幕，再退出 | Gallery 明暗切换与选择在重建后恢复；退出 App 主题偏好未改动 | 明/暗 |
| T59-21 | release 隔离 | 协调方使用 release APK（按其 applicationId 调整包名）执行同一 --es route devgallery 命令，分别冷启动与热启动 | 不进入 Gallery，正常首页或无服务器时正常引导页；release 不注册 devgallery 路由 | 明/暗 |
| T59-22 | 语言与系统返回 | 中英文分别进入所有分组；在弹窗/抽屉/图片预览中按返回，再逐级退出 Gallery | 标题与操作说明正确，无缺失资源；返回先关闭弹层，再退出所在层级 | 明/暗 |

对照网页端：本页无对应业务页面。T43 对照 dropdown/context-menu/popover/tooltip；T44 对照 dialog/sheet/card；T45 对照 button/input/select 等；T46 对照 tabs/badge/sidebar row/feedback；T51 为 lucide-static 图标；T52 对照 sonner；T53 对照对应杂项组件；T54 对照 header/状态布局。允许 Android 触摸反馈、系统键盘与系统栏差异。T60 对照其合并后的组件变体复测，本任务不修改其组件文件。

### 图标替换清单

尺寸沿用原有 Modifier/容器：输入组 16dp/14dp、折叠与命令搜索 16dp、Toast 常规 20dp/加载 18dp、LoadingView 16dp；未显式指定尺寸的原有 Icon 保持默认尺寸。

| 文件 | 旧图标 | 新图标 |
|---|---|---|
| Menus.kt | Icons.Default.Check | Lucide.Check |
| Menus.kt | Icons.Default.MoreVert | Lucide.EllipsisVertical |
| Menus.kt | Icons.AutoMirrored.Filled.KeyboardArrowRight | Lucide.ChevronRight |
| Sheets.kt | Icons.Default.Close | Lucide.X |
| Toasts.kt | Icons.Default.CheckCircle | Lucide.CircleCheck |
| Toasts.kt | Icons.Default.Warning | Lucide.TriangleAlert / CircleX |
| Toasts.kt | Icons.Default.Info | Lucide.Info |
| Collapsible.kt | Icons.Default.ExpandMore | Lucide.ChevronDown |
| InputGroup.kt | Icons.Default.Search | Lucide.Search |
| InputGroup.kt | Icons.Default.Close | Lucide.X |
| ImagePreview.kt | Icons.Default.Close | Lucide.X |
| ImagePreview.kt | Icons.Default.ChevronLeft | Lucide.ChevronLeft |
| ImagePreview.kt | Icons.Default.ChevronRight | Lucide.ChevronRight |
| Command.kt | Icons.Default.Search | Lucide.Search |
| StateViews.kt | Icons.Rounded.Close | Lucide.X |
| StateViews.kt | Icons.Rounded.Info | Lucide.Info |
| Toasts.kt | CircularProgressIndicator / TODO LoaderCircle | 旋转 Lucide.LoaderCircle（18dp） |
| StateViews.kt | CircularProgressIndicator / TODO Loader2 | 旋转 Lucide.Loader2（16dp） |
| StateViews.kt | 调用方传入的 ImageVector，TODO Inbox/CircleAlert | 仍由调用方决定语义，仅移除已不适用的迁移 TODO |
| Cards.kt / Tabs.kt / Avatar.kt / FolderBadge.kt / TopBar.kt | 无 Icons.* 或 T51 图标 TODO | 无改动 |

## 5. 回归点

- 普通启动不带 route、未知 route 均走 CodegApp；已有 deep link 分发保持不变，热启动时验证正常页面切换。
- 返回后原有 App 导航与服务器选择正常；Gallery 不写主题偏好，无网络依赖。
- 现有业务页使用的菜单勾选、全屏抽屉关闭、输入组清空、命令搜索、图片前后箭头与状态图标，检查颜色、尺寸、点击区域及 RTL（图片箭头保留原旋转逻辑；菜单 ChevronRight 不自动 RTL 镜像）。
- 键盘弹起、横屏、小屏滚动、Toast 宿主叠层和退出时动画销毁，真机确认无崩溃和遮挡。

## 6. 已知风险 / 未完成

- 未编译、未执行单测、未操作 adb/手机，无法宣称构建或真机测试通过；由协调方统一完成。
- T60 分组复用 T44Gallery、T45Gallery、T46Gallery，覆盖对话框、表单、徽章、反馈与列表行的现有聚合入口；T60 在这些函数中的变化自动包含，需合并后复测新增变体。
- debug 入口直接由 Activity 承载，另注册 MainShell 路由；没有为了传递启动参数修改范围外的 App 根组件。需按入口用例检查冷启动、热启动与返回行为。
