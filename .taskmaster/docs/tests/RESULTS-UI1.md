# UI1 测试结果

- 版本：sit `4ba52ca` 的 debug APK（`app.codeg.android.mine.debug`，与正式版 1.1.139 并存）
- 设备：vivo V1824A，Android 11；服务器：测试服 T13Test（`127.0.0.1:3082`，adb reverse）
- 计划：`PLAN-UI1.md`（T61 合并中）；合并前先记录探索性发现

| 用例 | 结果 | bug 单 | 截图 / 说明 |
|---|---|---|---|
| 首次配置 → 添加服务器 → 测试连接 | ❌ | B01 | 点击后 3 秒内无任何反馈；`OnboardingRoot` 不在 `CodegToasterHost` 内 |
| 首次配置 → 保存服务器 → 进入会话列表 | ✅ | | 连接成功，会话列表正常 |
| debug 版更新横幅 | ❌ | B02 | debug 版 versionName=1.0.1，更新检查未区分 debug，提示升级到正式版 1.1.144 |
| P-005 主框架五个导航（功能） | ✅ | | 五个标签往返切换无崩溃；选中项 Secondary、其余 Ghost；底栏图标均为 lucide。Toast 层位置待 B01/B03 修复后用 Gallery 和设置页复测 |
| P-006 主框架明色视觉 | ⚠️ | B04 | `shots/p006-*-light.png`；设置首页、文件夹页的卡片上下多出大块空白（1.1.139 无此问题，见 `shots/rel-*.png`）。暗色待测 |
| P-007 会话列表（视觉初查） | ⚠️ | | `shots/p005-sessions-light.png`；分区、计数、更多按钮与网页侧栏一致；「T13 probe」行的运行状态圆点落在 urgent 徽章下方，未与时间列对齐（待对照网页端） |
| P-008 会话列表暗色视觉 | ✅ | | `shots/p008-sessions-dark.png`；primary FAB 暗色下为浅底深字，与网页端一致 |
| P-006 设置首页暗色视觉 | ❌ | B04 | `shots/p006-settings-dark.png`：每个列表行自带实心背景（`--background`），在卡片（`--card`）里显示成一块块深色方块 |
| P-013 会话详情顶部菜单 | ⚠️ | B06 | `shots/p013-menu-light.png`：popover 样式、分隔线、destructive 删除项 ✅；菜单右边缘紧贴屏幕，无边距 |
| P-013 删除会话确认（只取消） | ⚠️ | B05 | `shots/p013-delete-confirm-light.png`：显示目标会话名 ✅，取消后会话保留 ✅；"取消"与"删除"未垂直对齐，取消按钮应为 outline 样式（网页端 alert-dialog） |
| P-014/P-027 会话详情明色视觉 | ✅ | | `shots/p013-detail-light.png`：工具卡片、错误输出、消息操作与网页端一致；禁用的发送按钮为大号灰色圆形（网页端为小号图标按钮，待对照） |
| P-140/P-142 Gallery 入口 / T43 | ❌→✅ | B03 | 首轮：点开 T43Gallery 崩溃（嵌套垂直滚动）。B03 修复后（sit `e721536`）复测：全部 21 个 Gallery（含 T60 复用的 3 个）逐个打开、截图、返回，0 次崩溃，截图 `shots/gal-*-light.png` |
| P-142 T43 菜单（明色视觉） | ✅ | | `shots/gal-T43Gallery-light.png`：标签、快捷键、子菜单箭头、复选项、禁用、destructive 均正确 |
| P-146 T45 按钮（明色视觉） | ✅ | | `shots/gal-T45Gallery-light.png`：6 种样式、4 种尺寸、加载、禁用正确 |

## 第 1 轮修复复测（sit `96d8ea3` 之后，含 B03、B04-designsystem、B01-app、B09-build）
| 用例 | 结果 | 对应 bug | 说明 |
|---|---|---|---|
| P-003 首次配置 → 测试连接 | ✅ | B01-1 | Toast "已连接 · codeg 0.38.0" 显示在屏幕底部 |
| P-137 debug 版自动更新横幅 | ✅ | B01-2 | 保存服务器进入主界面后无"有新版本"横幅 |
| P-137 关于 → 检查更新（debug） | ✅ | B01-2 | Toast "此版本不支持应用内更新（调试版或非正式包名）。"，`shots/p137-debug-toast.png`；sonner 风格（popover 底、边框、info 图标） |
| P-006 设置首页明色 | ✅ | B04-1 | `shots/r1-settings-light.png`：卡片紧凑，与 1.1.139 一致 |
| P-006 设置首页暗色 | ✅ | B04-1 | `shots/r1-settings-dark.png`：行透明，不再出现深色方块 |
| P-070 外观 → 主题分段标签暗色 | ✅ | B04-5 | `shots/r1-settings-dark.png`（外观页）：选中项只有一层底色加边框 |
| P-070 外观 → 预览"主按钮" | ⚠️ | | 仍是胶囊形，网页端按钮为 `rounded-md`（第 2 轮处理） |
| P-039 项目详情暗色 | ✅ | B04-4、B04-5 | `shots/r1-project-dark.png`：项目头像显示文件夹图标；"文件/更改/提交"标签选中项只有一层 |
| P-033 文件夹页暗色 | ✅ | B04-1 | `shots/r1-folders-dark.png`：卡片紧凑 |
| P-047 Git 操作菜单暗色 | ✅ | B04-3 | `shots/r1-gitmenu-dark.png`：菜单与屏幕右边缘留有边距 |
| P-033 删除分组确认（只取消） | ✅ | B04-2 | `shots/r1-confirm-dark.png`：显示目标"Mobile"；按钮垂直对齐，取消为 outline，删除为暗色 destructive；取消后分组保留 |
| P-113/P-117/P-127/P-109/P-131/P-115/P-133/P-135 页面扫查（暗色） | ✅ | | 版本控制、工作任务、自动化、聊天频道、服务端日志、开源许可、动态、搜索全部能打开，0 次崩溃，`shots/sw-*-dark.png` |
| P-117 工作任务看板分栏 | ⚠️ | B11 | `shots/sw-工作任务-dark.png`：4 个分栏标签挤在一行，"进行中 (…"被截断 |
| P-131/P-135 日志搜索框、全局搜索框 | ❌ | B12 | `shots/sw-服务端日志与用量-dark.png`、`shots/sw-搜索-dark.png`：输入组是胶囊形，网页端 `Input` 为 `rounded-md` |
| P-127 自动化空状态 | ⚠️ | B13 | `shots/sw-自动化-dark.png`：空状态没有图标，与工作任务、搜索页的空状态风格不一致 |
| P-005 底栏滚动收起/恢复 | ❌ | B10 | 往下滚收起、往上滚恢复 ✅；但列表已在顶部而底栏处于收起状态时（滚动后进子页面再返回、切换标签）无法恢复，只能先往下滚再往上滚 |

## 待归档的 bug（测试结束后按区域写成 bug 单）
- ~~B01 区域 app/main~~ 已由 `fix/B01-app` 修复并复测通过；：首次配置流程缺少 Toast 宿主（`A/app/CodegApp.kt` 的 `OnboardingRoot`，或把宿主上移到 `CodegApp` 根部）。来源：T58。
- B02 区域 misc（core/update）：debug 版不应检查/提示正式版更新（或按 applicationId 区分）。来源：T41。
- B04 区域 designsystem（`ListRows.kt`，可能还有 `Cards.kt`）：`CodegListRow` 默认带实心背景，放进卡片后暗色下显示为深色方块；行应透明，按下/选中才显示 `--accent`。另外列表型卡片上下多余空白。网页端设置首页对应的是左侧紧凑导航列表（`localhost:3082/settings`），不是带内边距的卡片。修法二选一：`CodegCard` 增加无内边距的列表用法，或者在调用处去掉。来源：T44/T49/T57、T48/T56。

- B10 区域 main（`MainShell.kt` 底栏收起逻辑）：列表回到顶部或返回/切换页面时，底栏应自动恢复显示。来源：T58。
- ~~B04/B05/B06/B07/B08~~ 已由 `fix/B04-designsystem` 修复并复测通过；
- B05 区域 designsystem（`Dialogs.kt`）：确认框 footer 两个按钮未垂直对齐；取消按钮应为 outline。来源：T44。
- B06 区域 designsystem（`Menus.kt`）：下拉菜单贴屏幕边缘，缺少碰撞边距（网页端 radix `collisionPadding`）。来源：T43。

## 已有问题（非 UI1 回归，记录备查）
- 文件夹页：未分组的项目显示在 "Mobile" 分组卡片内（1.1.139 同样如此）。

## 测试环境备注
- 底栏在列表滚动时会自动收起（T58 的设计行为），自动化点击底栏前要先确认底栏可见，否则点击会落到下面的列表上。
- 添加服务器页面只要有令牌输入框就禁止截屏（截图为 0 字节），属于有意的安全设置。
- 每次 CI 构建的 debug APK 签名不同（各 runner 临时生成调试密钥），无法覆盖安装，只能卸载重装并重新添加测试服。已开 `fix/B09-build`：debug 版改用公开 dev 密钥签名。
- vivo 的安全键盘（令牌输入框）弹出时 `screencap` 得到 0 字节图片，截图前先点空白处收起键盘。
- 添加服务器页面按返回键会直接关闭整个页面、丢弃已填内容（是否需要确认待对照网页端）。
