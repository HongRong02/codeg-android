# T25 服务端 API 差异检查接入 CI

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：无　可并行：与所有任务并行
- 分支：`task/T25-api-diff-ci`

## 目标
`scripts/api_diff.py` 已能对比服务端路由与 app 调用。把它接入 GitHub Actions：定期拉官方 codeg 最新 release，若出现“app 调用但服务端已删除”的接口或新增接口，自动开 issue。

## 需求
- 新建 workflow（每周一次 + 手动触发）：checkout 本仓库 + `spacering-net/codeg` 最新 tag（只需 `src-tauri/src/web/`，可用 sparse checkout）。
- 运行 `python scripts/api_diff.py <router.rs 路径>`；扩展脚本：支持 `--baseline <file>` 对比上次的“服务端接口集合”，输出新增/删除的服务端接口。
- 基线文件 `scripts/api_baseline.txt` 提交到仓库（当前 0.38 的路由名列表）。
- broken > 0 或有新增/删除时，用 `gh issue create`（GITHUB_TOKEN）开 issue，正文附 diff 表；已有同标题 open issue 时更新评论而非重复开。

## 改动范围
- 修改：`scripts/api_diff.py`
- 新建：`scripts/api_baseline.txt`、`.github/workflows/api-drift.yml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `scripts/api_diff.py`（全文，约 90 行）
- 参考已有 workflow 写法：`.github/workflows/sync-upstream.yml`（91–120 行：开 PR / 开 issue 的 gh 用法）

## 参考（只读，按需打开）
- 无

## 验收
- 手动触发 workflow 成功运行；人为删改基线一行后能开出 issue。
- 不需要任何个人 token（只用 GITHUB_TOKEN）。
