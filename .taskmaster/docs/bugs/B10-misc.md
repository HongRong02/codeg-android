# B10 misc：底栏在顶部无法恢复；自动化空状态缺图标

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 2 轮修复 |
| 区域（冲突域） | misc：`A/feature/main/**`、`A/feature/automations/**` |
| 分支 / 基准 | `fix/B10-misc` / 最新 `sit` |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件。只读"定位"里列出的代码，只改"改动范围"里的文件。截图在 `/mnt/e/claude/apk/shots/`。

## 问题清单
### B10-1 底栏收起后，列表在顶部时无法恢复
- 来源：P-005 真机。往下滚动列表时底栏收起、往上滚动时恢复，这部分正常。但在设置首页滚动到下方后进入子页面再返回、或切换底栏标签后，列表处于顶部而底栏仍是收起状态；此时列表无法再往上滚，底栏恢复不了，只能先往下滚一点再往上滚。
- 归属原任务：T58
- 定位：`A/feature/main/MainShell.kt` 约 122–160 行的 `NestedScrollConnection` 底栏收起逻辑。
- 预期：以下任一情况底栏都恢复显示：切换底栏标签；从子页面返回到标签页；列表滚动到顶部（或内容不足一屏）；在顶部继续向下拉（overscroll）。保留"往下滚收起、往上滚恢复"的现有行为。

### B10-2 自动化空状态缺图标（原 B13）
- 来源：P-127，`shots/sw-自动化-dark.png`：自动化列表的空状态没有图标；工作任务（`shots/sw-工作任务-dark.png`）和搜索页（`shots/sw-搜索-dark.png`）的空状态都有图标容器。
- 归属原任务：T58
- 定位：`A/feature/automations/` 里调用 `EmptyState` 的地方。
- 预期：传入与网页端自动化页一致的 lucide 图标（在 `WEB/components/automations/` 里查实际使用的图标），风格与其他空状态一致。

## 改动范围
- `A/feature/main/**`、`A/feature/automations/**`。不改 `A/core/designsystem/**`（B15 正在并行修改）。

## 验收
- CI（verify.yml）在 `fix/B10-misc` 上通过。
- 复测用例（协调方在 sit 上执行）：P-005、P-006、P-127。
- 提交信息写 `Fixes B10-k (T58)`。
