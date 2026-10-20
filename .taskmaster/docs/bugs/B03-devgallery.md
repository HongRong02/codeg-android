# B03 devgallery：打开 Gallery 分组即崩溃（嵌套垂直滚动）

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 1 轮修复（阻塞组件用例 P-140–P-169，提前修） |
| 区域（冲突域） | devgallery：`A/feature/devgallery/**` |
| 分支 / 基准 | `fix/B03-devgallery` / 最新 `sit` |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件。只读"定位"里列出的代码，只改"改动范围"里的文件。

## 问题清单
### B03-1 打开 T43Gallery 崩溃
- 来源：真机探索测试（P-142 前置），sit `4ba52ca` debug APK
- 归属原任务：T59
- 复现步骤：1. `adb shell am start -n app.codeg.android.mine.debug/app.codeg.android.MainActivity --es route devgallery` 2. 点"T43 · 菜单" 3. 点"T43Gallery"
- 预期结果：显示 T43Gallery 的全部组件
- 实际结果：应用崩溃。`java.lang.IllegalStateException: Vertically scrollable component was measured with an infinity maximum height constraints, which is disallowed. One of the common reasons is nesting layouts like LazyColumn and Column(Modifier.verticalScroll())...`（堆栈全是 Compose 内部帧）
- 定位：`DevGalleryScreen.kt:60`、`:63` 用 `verticalScroll` 包住 Gallery 内容；Gallery 内部的组件（如菜单内容、`Controls.kt`、`Dialogs.kt`、`StateViews.kt`、`icon/CodegIcon.kt` 的 Gallery 中都含有 LazyColumn / verticalScroll / LazyVerticalGrid）再次请求无限高度。

### B03-2 全部分组都要能打开
- 预期：预览页里列出的**每一个** Gallery 函数（`DevGalleryScreen.kt:104` 起的 when 分支）都能打开、滚动查看、返回，不崩溃。
- 修法要求：不要改 `A/core/designsystem/**` 里的 Gallery 函数；在预览页这一侧解决，例如：Gallery 渲染区域给定有限高度（`fillMaxSize()` 的 Box，不再外包 `verticalScroll`），并对不自带滚动的 Gallery 单独包一层滚动；或者在 when 分支里逐个决定是否包滚动。选定方案后在修复说明里写清楚。
- 自查：逐个检查每个 Gallery 函数内部是否含 `LazyColumn`/`LazyRow`/`LazyVerticalGrid`/`verticalScroll`，在修复说明里列出每个分组的处理方式。

## 改动范围
- `A/feature/devgallery/**`；如需新字符串用 `strings_dev_gallery.xml`（已存在，只追加）。

## 验收
- CI（verify.yml）在 `fix/B03-devgallery` 上通过。
- 复测用例（协调方在 sit 上执行）：P-140、P-142、P-144、P-146、P-148、P-150、P-153、P-155、P-157、P-159、P-161、P-163、P-165、P-167、P-169。
- 提交信息写 `Fixes B03-k (T59)`。
