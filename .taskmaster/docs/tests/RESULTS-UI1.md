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

## 待归档的 bug（测试结束后按区域写成 bug 单）
- B01 区域 app/main：首次配置流程缺少 Toast 宿主（`A/app/CodegApp.kt` 的 `OnboardingRoot`，或把宿主上移到 `CodegApp` 根部）。来源：T58。
- B02 区域 misc（core/update）：debug 版不应检查/提示正式版更新（或按 applicationId 区分）。来源：T41。
- B04 区域 designsystem（`ListRows.kt`，可能还有 `Cards.kt`）：`CodegListRow` 默认带实心背景，放进卡片后暗色下显示为深色方块；行应透明，按下/选中才显示 `--accent`。另外列表型卡片上下多余空白。网页端设置首页对应的是左侧紧凑导航列表（`localhost:3082/settings`），不是带内边距的卡片。修法二选一：`CodegCard` 增加无内边距的列表用法，或者在调用处去掉。来源：T44/T49/T57、T48/T56。

- B05 区域 designsystem（`Dialogs.kt`）：确认框 footer 两个按钮未垂直对齐；取消按钮应为 outline。来源：T44。
- B06 区域 designsystem（`Menus.kt`）：下拉菜单贴屏幕边缘，缺少碰撞边距（网页端 radix `collisionPadding`）。来源：T43。

## 已有问题（非 UI1 回归，记录备查）
- 文件夹页：未分组的项目显示在 "Mobile" 分组卡片内（1.1.139 同样如此）。

## 测试环境备注
- 每次 CI 构建的 debug APK 签名不同（各 runner 临时生成调试密钥），无法覆盖安装，只能卸载重装并重新添加测试服。已开 `fix/B09-build`：debug 版改用公开 dev 密钥签名。
- vivo 的安全键盘（令牌输入框）弹出时 `screencap` 得到 0 字节图片，截图前先点空白处收起键盘。
- 添加服务器页面按返回键会直接关闭整个页面、丢弃已填内容（是否需要确认待对照网页端）。
