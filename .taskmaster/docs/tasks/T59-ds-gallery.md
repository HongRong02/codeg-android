# T59 组件预览页（debug）＋ 设计系统内部图标换成 lucide

> 先读 `.taskmaster/docs/CONVENTIONS.md`（**测试文档模式**）和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

| 字段 | 值 |
|---|---|
| 批次 / 层级 / 组 | UI1 / L3 / 组 D 设计系统收尾（关系图：`.taskmaster/docs/graphs/UI1.md`） |
| 硬依赖 ← | T43–T46、T51–T54（均已合入 sit） |
| 软依赖 ⇠ | 无（T60 的 Gallery 变化在 T60 自己的文件里，本页面按函数名调用即可自动包含） |
| 同层并行 | T60 ⟂ |
| 下游 | 批次末真机测试：L1–L3 组件类用例都在本页面上执行 |
| 冲突域 | `A/feature/devgallery/**`（新建）；`A/MainActivity.kt`（只加 debug 入口）；共享追加点：`A/feature/main/MainShell.kt` 路由表 +1；`A/core/designsystem/component/` 下的 `Menus.kt`、`Sheets.kt`、`Cards.kt`、`Tabs.kt`、`Toasts.kt`、`Collapsible.kt`、`Avatar.kt`、`InputGroup.kt`、`ImagePreview.kt`、`Command.kt`、`FolderBadge.kt`、`TopBar.kt`、`StateViews.kt`（**只做图标替换**） |
| 分支 / 基准 | `task/T59-ds-gallery` / 派发时的 `sit` |
| 测试方式 | 测试文档模式：写 `.taskmaster/docs/tests/T59-ds-gallery.md` |
| 优先级 | high（批次末测试的前置） |

## 目标
L1–L3 的组件只有 `@Preview` 和 `internal fun TxxGallery()`，App 里没有入口，测试文档里的组件用例在真机上没法执行。本任务提供一个**只在 debug 版可见**的组件预览页，并把设计系统内部遗留的 Material 图标换成 lucide。

## 需求
1. 新建 `A/feature/devgallery/DevGalleryScreen.kt`：顶部 `CodegTopBar`（返回）+ 明/暗切换（只影响本页，用局部 `CodegTheme(darkTheme = …)` 包裹）+ 分组列表。分组按任务排列：T43 菜单、T44 对话框/抽屉/卡片、T45 按钮/表单、T46 标签/徽章/列表行/反馈、T51 图标、T52 Toast、T53 杂项、T54 顶栏/状态、T60 缺口补齐。点进分组后渲染对应的 `TxxGallery()` 函数；一个任务有多个 Gallery 函数时（例如 `T46BadgesGallery`）全部列出。用 `grep -rn "internal fun T[0-9]*.*Gallery" A/core/designsystem` 找出全部函数。
2. Toast 分组要用 `CodegToasterHost` 包住，保证在本页可以触发 toast。
3. 入口（只在 debug）：
   - 在 `MainShell.kt` 路由表追加一个 `composable("devgallery")`，不改其他路由；
   - 在 `MainActivity.kt` 处理 debug 启动参数：`adb shell am start -n app.codeg.android.mine/app.codeg.android.MainActivity --es route devgallery` 时直接导航到该页面。用 `BuildConfig.DEBUG` 判断，release 版完全不响应。
   - 如果 MainActivity 到 MainShell 之间没有现成的传路由方式，用最小改动加一个可选参数，带默认值。
4. 设计系统内部图标：冲突域中列出的 13 个组件文件，把 `Icons.*` 和 `// TODO(T51): Lucide.Xxx` 全部换成 `Lucide.Xxx`（以 `.taskmaster/docs/icon-map.md` 和 T51 生成的属性名为准），尺寸保持原有视觉（网页端 `size-4` = 16dp）。**这些文件只改图标相关的行**。
5. 文字走 `strings_dev_gallery.xml` + zh（分组名用任务标题，可以只有英文 + 中文两份）。

## 改动范围
- 只限"冲突域"；不改 T60 冲突域里的 7 个文件（`TextFields.kt`、`Feedback.kt`、`Dialogs.kt`、`Controls.kt`、`Badges.kt`、`Buttons.kt`、`ListRows.kt`），也不改 `designsystem/icon/**`。
- 范围外文件一律不改；不新增 Gradle 依赖。

## 代码定位
- `A/MainActivity.kt`、`A/feature/main/MainShell.kt`（只看路由表和 NavHost 部分，按 code-index `feature-main.md` 定位）
- Gallery 函数：`grep -rn "internal fun T[0-9]*.*Gallery" A/core/designsystem`
- 图标：`.taskmaster/docs/icon-map.md`、`A/core/designsystem/icon/LucideIcons*.kt`（只查属性名）

## 参考（只读）
- 无网页端对应页面；样式用现有的设计系统组件即可。

## 验收
- 测试文档按模板写全：入口命令；每个分组一条用例（明/暗各一）；release 版不可进入的检查方法（例如用 release APK 执行同一条 am start 命令，预期停在首页）；图标替换清单（文件 → 旧图标 → 新图标）。
