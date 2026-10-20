# B18 main + settings：设置子页面切换时底栏不恢复（B16 第二轮）

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 3 轮修复（B16 复测未通过） |
| 区域（冲突域） | `A/feature/main/**`、`A/feature/settings/**` |
| 分支 / 基准 | `fix/B18-main-settings` / 最新 `sit` |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件和 `.taskmaster/docs/bugs/B16-main.md`（上一轮的修复说明）。只读"定位"里列出的代码，只改"改动范围"里的文件。

## 问题清单
### B18-1 设置子页面的切换不经过导航，底栏状态不重置
- 来源：P-005 真机复测（sit 合入 B16-main 之后）
- 归属原任务：T58、T57
- 复现：设置标签 → 列表往下滚，底栏收起 → 点"助手"进入子页面（**子页面里底栏也仍是收起的**）→ 返回设置首页，底栏仍收起。
- 根因（协调方已确认）：设置的子页面（智能体、助手、专家、技能……）都是 `SettingsScreen` 内部用状态切换的（例如 `SettingsScreen.kt:142` 附近的 `agentDetail`），在 `MainShell` 看来始终是 `HomeTab.SETTINGS.route` 这一个导航目的地（`MainShell.kt` 约 305 行），所以 B16 的 `LaunchedEffect(backStackEntry)` 不会触发。
- 预期修法：让设置页在内部切换子页面（进入和返回）时通知主框架恢复底栏。例如在 `MainShell` 提供一个 `CompositionLocal`（或给 `SettingsScreen` 加一个带默认值的回调参数 `onPageChanged: () -> Unit = {}`），`SettingsScreen` 在内部页面状态变化时调用它，`MainShell` 收到后把底栏设为可见。不要把设置的内部页面改成导航路由（改动太大）。其他标签页如果也有类似的内部页面切换，一并检查，列在修复说明里。
- 保留 B10、B16 已修好的行为：滚动收起/恢复、滚回顶部、顶部下拉、点击标签恢复、导航条目变化恢复。

- 修复说明：设置首页、各设置子页面和智能体详情共用同一导航条目，B16 的导航监听无法感知内部切换。为 SettingsScreen 追加带默认空实现的 onPageChanged 回调，用 LaunchedEffect(leaf, agentDetail) 在内部页面状态变化时通知 MainShell 恢复底栏；覆盖进入、系统返回、顶部返回和智能体详情关闭，不改变原有设置逻辑及滚动/导航恢复逻辑。检查 MainShell 中其他标签页的入口：会话、文件夹、活动和搜索的对外子页面入口均调用 nav.navigate，继续由 B16 的导航监听恢复；受本任务只读定位文件限制，未深入这些标签内部实现，无法排除其内部还有状态切页。需复测 P-005 全部场景，重点为设置列表下滚收起后进入助手/专家/技能并以系统键和顶部按钮返回，以及智能体列表进入详情和关闭；同时复测 P-006、P-067，确认滚动收起/恢复、滚回顶部、顶部下拉、点击标签和导航条目变化恢复仍正常。

## 改动范围
- `A/feature/main/**`、`A/feature/settings/**`。只为传递"页面已切换"信号做最小改动，不改设置页的其他逻辑。

## 验收
- CI（verify.yml）在 `fix/B18-main-settings` 上通过。
- 复测用例（协调方在 sit 上执行）：P-005（全部场景，重点：设置列表下滚收起 → 进入助手 → 返回）、P-006、P-067。
- 提交信息写 `Fixes B18-1 (T58)`。
