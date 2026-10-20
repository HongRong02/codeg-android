# B01 app：首次配置缺少 Toast 宿主、debug 版提示升级正式版

| 字段 | 值 |
|---|---|
| 批次 / 轮次 | UI1 / 第 1 轮修复 |
| 区域（冲突域） | app 根与更新：`A/app/**`、`A/core/update/**` |
| 分支 / 基准 | `fix/B01-app` / 最新 `sit` |
| 修复方式 | 派 Codex（CI 模式） |
| 状态 | 🔄 修复中 |

> 修复任务先读 `.taskmaster/docs/CONVENTIONS.md`（修复任务部分），再读本文件。只读"定位"里列出的代码，只改"改动范围"里的文件。

## 问题清单
### B01-1 首次配置流程没有 Toast 宿主（原 B01）
- 来源：P-003 真机。全新安装（没有服务器配置）→ 添加你的服务器 → 填写 → 测试连接
- 归属原任务：T58
- 现象：点"测试连接"后 3 秒内没有任何反馈。
- 定位：`A/app/CodegApp.kt` 的 `OnboardingRoot()` 不在 `CodegToasterHost` 里面，`ServerEditorScreen` 拿到的是 `LocalCodegToaster` 的默认静默实例；`MainShell.kt:201` 才有宿主。
- 预期：首次配置流程里的 Toast 正常显示。推荐把 `CodegToasterHost` 上移到 `CodegApp` 根部，包住 `OnboardingRoot` 和 `MainShell` 两个分支；这种情况下 `MainShell` 里那一层宿主会变成重复宿主——**`MainShell.kt` 不在本任务冲突域内**，所以不要删它，而是在修复说明里写明"`MainShell.kt:201` 的宿主可以在下一轮删除"，并确认两层宿主同时存在时不会重复显示（内层会覆盖 CompositionLocal，只要内层宿主存在就由内层显示）。如果只给 `OnboardingRoot` 单独包一层宿主更简单、也没有重复问题，可以选这种，写明理由。

- 修复说明：首次配置分支单独包裹 `CodegToasterHost`，让服务器编辑页使用有效的 Toast 实例；保留 MainShell 原宿主，两者位于互斥分支，不会重复显示。选择此方案避免嵌套宿主，无需后续删除 MainShell 宿主。复测 P-001、P-003、P-005：全新安装后测试连接的成功/失败提示，以及保存服务器后主界面提示。

### B01-2 debug 版提示升级到正式版（原 B02）
- 来源：真机，sit debug APK（`app.codeg.android.mine.debug`，versionName 1.0.1）
- 归属原任务：T41
- 现象：debug 版顶部显示"有新版本 1.1.144"。正式版包名是 `app.codeg.android.mine`，与 debug 版不同，点更新只会另装一个正式版，不会升级当前应用。
- 定位：`A/core/update/` 的更新检查。
- 预期：`BuildConfig.DEBUG` 为真（或 applicationId 不是 `app.codeg.android.mine`）时，不做自动更新检查、不显示横幅；"关于"里手动检查更新给出明确说明（例如"调试版本不支持应用内更新"，走字符串资源）。

- 修复说明：原更新仓库未区分构建类型和包名，直接比较正式发行版本。增加统一资格判断（非 DEBUG 且 applicationId 为 `app.codeg.android.mine`）；不支持的构建在网络和检查时间记录前返回，不显示横幅，也阻止下载。横幅组件在不支持构建上直接返回，保留 `UpdateUi.showBanner` 的纯状态推导，避免将构建配置耦合进状态单测。关于页手动检查复用现有 Toast 消息通路，显示中英文资源说明，离开页面时清理该消息。复测 P-137、P-111：debug 自动检查无请求/横幅、连续手动检查均显示说明；正式版自动/手动检查及下载安装保持正常。

## 改动范围
- `A/app/**`、`A/core/update/**`；字符串用新文件 `strings_fix_app.xml` + zh。不改 `A/feature/**`。

## 验收
- CI（verify.yml）在 `fix/B01-app` 上通过。
- 复测用例（协调方在 sit 上执行）：P-001、P-003、P-005、P-137、P-111。
- 提交信息写 `Fixes B01-k (Txx)`。
