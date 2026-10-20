# T52 测试文档：设计系统 Toast（sonner）

- 分支：`task/T52-ds-toasts`　提交：本文件与代码同一提交，以 `git log -1 --format=%H -- .taskmaster/docs/tests/T52-ds-toasts.md` 查询。
- 依赖：无；业务宿主接入由协调方安排，T51 合入后替换图标。测试方式：统一合并后编译 + 真机（vivo V1824A，Android 11）。

## 1. 测试范围
- 改动文件：
  - `app/src/main/java/app/codeg/android/core/designsystem/component/Toasts.kt`
  - `app/src/main/res/values/strings_ds_toasts.xml`
  - `app/src/main/res/values-zh-rCN/strings_ds_toasts.xml`
  - `app/src/test/java/app/codeg/android/core/designsystem/ToastsTest.kt`
  - `.taskmaster/docs/tests/T52-ds-toasts.md`
- 新增公开 API：`ToastId(value: Long)`、`ToastType`（Success/Error/Info/Warning/Loading）、`ToastAction(label, onClick)`；`CodegToaster()`，`success/error/info/warning(title, description = null, action = null): ToastId`，`loading(title): ToastId`，`update(id, type, title, description = null)`，`dismiss(id)`；`LocalCodegToaster`；`CodegToasterHost(toaster = remember { CodegToaster() }, content)`。
- 内部接口：纯 Kotlin `ToastQueue`、`ToastEntry`；`T52Gallery()`。
- 受影响页面/入口：无，仅组件；在 Gallery/@Preview 中验证。Gallery 自带宿主。
- 明确不在范围内：feature 接入、MainShell、服务端、Gradle 依赖、code-index、CI、手机操作。

## 2. 编译检查
- 自评编译风险点：
  - `Toasts.kt:3` 起：Compose animation、Material 基础图标、pointerInput/graphicsLayer、WindowInsets 的 import；未新增依赖，合并编译须确认现有 Compose 版本支持这些 API。
  - `Toasts.kt:41` 起：`ToastId` 使用 Kotlin value class；状态类无 Android/Compose 调用，位于同一文件但可由 JVM 单测直接实例化。
  - `Toasts.kt:88` 起：公开 CodegToaster 不暴露 internal ToastEntry；StateFlow 仅内部可见，写操作用同步锁序列化。
  - `Toasts.kt:123` 起：默认参数 remember、AnimatedVisibility、WindowInsets.safeDrawing/imePadding 均在 composable 内使用，无实验性 API 的主动引入；需以合并构建确认是否需要版本特定的 OptIn。
  - `Toasts.kt:174` 起：图标 when 穷尽五种类型；主题使用 web token；圆角 10dp 对应 globals.css 的 rounded-lg（当前 CodegShapes 无该档，不修改共享主题）。
  - `Toasts.kt:264` 起：Preview 使用既有 ThemeMode.LIGHT/DARK 和 CodegTheme；资源中英键必须一致。
- 预期 `./gradlew :app:check` 中需要关注的 lint / 单测：ToastsTest 六个方法；资源引用、Compose 编译器、Material 图标解析、无障碍内容描述、长函数/通配 import 等项目 lint 规则。
- 本任务未执行 Gradle、单测、CI 或真机；仅执行差异格式、XML 解析、资源键一致性和改动范围静态检查。以上不构成编译通过声明。

## 3. 单元测试
| 测试类::方法 | 覆盖点 |
|---|---|
| ToastsTest::newestThreeAreVisibleAndHiddenToastKeepsItsBudget | 最多三条、新消息优先、隐藏消息恢复后有完整 4 秒时长 |
| ToastsTest::loadingNeverExpiresAndUpdateReusesIdAndRestartsDuration | loading 无限、同 id 更新成功及描述、更新后重新计时、不新增 |
| ToastsTest::dismissAndUnknownUpdatesAreSafe | 删除、重复删除、已删除 id 更新无副作用 |
| ToastsTest::allTypesAndActionArePreserved | 五种类型、描述及 action 保留，回调可调用 |
| ToastsTest::updatingErrorClearsDescriptionAndPreservesAction | loading→error、清空旧描述、保留 action、错误默认时长 |
| ToastsTest::negativeTimeDoesNotExtendLifetime | 非正时间不影响到期 |

单测已编写，未执行。action 的 UI 点击与关闭行为由下述真机用例覆盖。

## 4. 测试用例
前置条件：无需服务器；协调方在组件预览入口展示 T52Gallery，必要时在其外层宿主内容放置输入框、计数按钮以验证 insets 和透传。测试中英文；除特别标注，每项明/暗主题各执行一次。所有反馈内容由测试代码/字符串资源提供。

| 编号 | 入口/页面 | 步骤 | 预期结果 | 明/暗 |
|---|---|---|---|---|
| T52-01 | Gallery | 分别点击成功、错误、提示、警告，逐条等候 | 标题/类型图标正确；错误显示描述；约 4 秒退出；popover 背景、border 边框、14sp 文本可读 | 明/暗 |
| T52-02 | Gallery | 点击加载中，等待 10 秒，再点完成加载 | 加载期间不消失；原卡片变为成功，数量不变；更新后约 4 秒消失 | 明/暗 |
| T52-03 | Gallery | 点提示，点击 Toast 内重试 | 原提示关闭；回调恰好触发一次并出现成功；点击 action 不触发拖动关闭 | 明/暗 |
| T52-04 | Gallery | 1 秒内连续点击四种类型 | 最新三条可见，最新在最下；最早隐藏且保留队列；到期后最早补位并具有完整阅读时长 | 明/暗 |
| T52-05 | Gallery | 生成 Toast，左右各拖动超过 48dp，再重试短拖动 | 长拖动卡片跟随并退出；短拖松手复位；后续点击和滑动仍有效 | 明/暗 |
| T52-06 | Gallery + 输入框 | 生成 loading；竖屏、横屏互换；弹出/收起输入法 | Toast 位于可用区域右下，手机边距 16dp；避开导航栏、刘海和键盘；无重复叠加 insets；无横向越界 | 明/暗 |
| T52-07 | 明暗 Preview | 打开 ToastsLightPreview/ToastsDarkPreview，查看五种类型 | 五种卡片均展示；spinner、图标和描述可读；圆角 10dp、阴影与边框清楚 | 明/暗 |
| T52-08 | 未挂宿主的组件测试入口 | 通过 LocalCodegToaster.current 调四种通知、loading/update/dismiss | 无界面、无崩溃；新增调用记录宿主缺失日志且不含业务标题；loading 返回的空 id 可安全更新/关闭 | 明/暗 |
| T52-09 | Gallery | 提示出现期间点击卡片外内容按钮，启用 TalkBack 后再次生成提示 | 卡片外事件透传；标题/描述由 polite live region 宣读；类型图标有本地化描述；action 可聚焦执行 | 明/暗 |
| T52-10 | 测试宿主 | loading→error(id, 描述)，随后 dismiss(id)；重复 dismiss 并 update 同 id | 同卡片更新错误及描述；关闭后不复活，无重复消息或崩溃 | 明/暗 |
| T52-11 | 测试宿主 | 使用多行长标题、长描述、较长中英文 action；再增至三条 | 内容换行且按钮可点；在目标手机常规字体下不越界；记录大字体/小屏总高度结果 | 明/暗 |

对照网页端：全部用例对照 `WEB/components/ui/app-toaster.tsx` 与 sonner 默认交互。AppToaster 未指定 richColors，因此各类型图标使用正常前景色，不自行引入彩色状态色。默认时长 4000ms、loading 无限、最多三条、右下角与网页一致。手机使用 16dp 安全区外偏移（桌面 sonner 默认 32px），三条采用展开卡片排列与 8dp 间距，便于触控；未复制桌面 hover 折叠堆叠、鼠标悬停暂停和 native webview occlusion lease。进退场采用 180ms 淡入淡出加竖向位移；拖动为横向 48dp 阈值，短拖直接复位。Material 图标暂代 Lucide，协调方合并 T51 后按 TODO 替换。上述原生交互差异由协调方视觉验收。

## 5. 回归点
- 宿主内容布局填满可用区域，空队列不拦截点击；接入方仅挂一个宿主，共享同一个 toaster，避免重复计时。
- 同 id 更新不重新排队；连续插入/删除后最多三条有效 Toast（180ms 退场期间可暂留退出卡片）。
- Gallery 明暗/中英资源切换、返回页面时宿主 coroutine 自动取消，无全局协程泄漏。
- 保持现有 Snackbar/页面提示行为；本任务未替换业务调用点。

## 6. 已知风险 / 未完成
- 尚未统一编译和执行 JVM 单测，无法断言编译通过。
- 宿主尚未接入业务；T51 图标替换由协调方完成。
- 极端长文本/大字体下三条卡片总高度可能超过可用高度，需要 T52-11 真机确认；当前未截断正文。
- Host 为内容上方的 Compose 层；独立 Dialog/Popup 窗口可能盖住它，接入时须验证对话框内反馈的窗口层级。
- 计时随宿主协程推进；未实现应用后台/用户触摸时暂停计时，后台恢复的阅读行为需集成评估。
