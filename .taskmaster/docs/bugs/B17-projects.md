# B17 projects：一次 Git 操作弹出两条 Toast

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 3 轮修复 |
| 区域（冲突域） | projects：`A/feature/projects/**` |
| 分支 / 基准 | `fix/B17-projects` / 最新 `sit` |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件。只读"定位"里列出的代码，只改"改动范围"里的文件。截图在 `/mnt/e/claude/apk/shots/`。

## 问题清单
### B17-1 提交成功后同时出现"操作完成"和"已提交 1 个文件"两条 Toast
- 来源：P-045 真机。项目 sandbox → 更改 → 提交更改 → 填写提交信息 → 提交 1 个文件。提交成功（服务端 git log 已有新提交），但屏幕底部同时出现两条成功 Toast：`shots/git-commit-toast.png`。
- 归属原任务：T56
- 定位：`CommitSheet.kt:69` 的 `doneTitle = stringResource(R.string.t56_operation_completed)`；同样的写法还在 `BranchPickerSheet.kt:49`、`CloneRepoSheet.kt:50`、`git/BranchManageSheet.kt:28`、`git/GitMiscSheet.kt:37`、`git/MergeOpsSheet.kt:25`、`git/StashSheet.kt:25`。很可能是面板自己弹一条通用 Toast，而 ViewModel 或页面又弹一条具体结果的 Toast。
- 预期：参照网页端（`WEB/components/layout/commit-dialog.tsx` 等对应组件里的 `toast.` 调用），**一次操作完成只出现一条 Toast**，用具体的结果文案（例如"已提交 1 个文件"）。loading → 结果用 `loading` + `update` 同一个 id，不要新增第二条。

### B17-2 检查其余 6 个面板
- 预期：上面列出的其余 6 个面板逐个检查，凡是同一次操作出现两条 Toast 的都改成一条；在修复说明里列出每个面板的处理结果（文件 → 修改前的 Toast → 修改后的 Toast）。

## 改动范围
- `A/feature/projects/**`；字符串只追加。

## 验收
- CI（verify.yml）在 `fix/B17-projects` 上通过。
- 复测用例（协调方在 sit 上执行）：P-045（提交）、P-041（切换分支）、P-051（分支管理）、P-053（暂存）、P-055（合并/变基/重置，只测取消）、P-037（克隆）。
- 提交信息写 `Fixes B17-k (T56)`。
