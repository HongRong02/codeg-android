# S7 应用内更新与发版流水线

| 字段 | 值 |
|---|---|
| 批次 | SHELL1（纯套壳第一版） |
| 分支 | `task/S7-update`，基于 `shell` |
| 模式 | 开发任务，CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | S1–S8 全部并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`。网页端源码（只读参考）在 WSL `~/projects/codeg`（分支 `hongrong/quota-slots`），需要了解网页行为时去那里查，不要修改它。

## 目标
套壳版沿用原生版的自动更新：启动时检查 GitHub Release，有新版就提示下载安装；CI 发版流程在新结构下继续可用。

## 要求
1. **更新检查**：复用 `core/update/UpdateChecker.kt`、`UpdateDownloader.kt`（这两个文件归你，可以改）。旧版的界面和状态逻辑见 `git show native-final:app/src/main/java/app/codeg/android/core/update/UpdateBanner.kt` 和 `UpdateViewModel.kt`，参考它们写一个不依赖旧设计系统的版本，放在 `shell/update/`。
   - `onResume` 时检查，最多每 6 小时一次。有新版就在 `Overlay` 里显示原生对话框：版本号、更新说明（纯文本，可滚动）、"稍后"/"下载并安装"；下载时显示进度；下载完成后调起系统安装器（FileProvider authority `${applicationId}.files`，文件放在 `cacheDir/updates/`）。没有"安装未知应用"权限时，引导用户去设置页开启。
   - 点"稍后"后，同一版本 24 小时内不再提示。
   - debug 构建（`BuildConfig.DEBUG`）不检查更新（旧版同样如此，避免 debug 包提示升级成正式版）。
2. **手动检查入口**：网页里没有地方放，这一版先不做，在报告里说明。
3. **CI**：`.github/workflows/` 归你。`verify.yml` 和 `auto-release.yml` 要在新结构下正常运行（auto-release 的版本号逻辑不变，保证从 1.1.139 能覆盖升级）。`api-drift.yml`、`theme-drift.yml` 依赖已删除的原生代码和 `scripts/` 里的脚本：删掉这两个 workflow，以及只为它们服务的脚本（`scripts/` 归你，删除前确认没有其他 workflow 引用）。`release.yml`、`sync-upstream.yml` 检查是否还适用，不适用就删，在报告里写明理由。
4. **README**：把 `README.md` 改成套壳版的简短说明：这是什么、怎么用（填服务器地址、在网页里登录）、怎么构建、怎么发版。删掉旧的原生功能说明。

## 改动范围
`shell/update/**`、`core/update/**`、`.github/workflows/**`、`scripts/**`、`README.md`、`res/values*/strings_shell_update.xml`（不再用的 `strings_update.xml` 两份可以删除）、`.taskmaster/docs/shell/tests/S7.md`

## 验收
- CI（verify.yml）在 `task/S7-update` 上通过。
- 测试用例写在 `.taskmaster/docs/shell/tests/S7.md`，由协调方在真机上统一执行。
