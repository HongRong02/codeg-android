# T41 APK 自动更新

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：与 T36–T40 并行
- 分支：`task/T41-apk-auto-update`

## 目标
main 每次合并后由 CI 自动发布一个签名 APK 到 GitHub Release；App 检测到新版本时提示，一键下载并调起系统安装器覆盖升级。

## 决定（已确认，不要改）
- 签名：继续用仓库里公开的 dev 密钥（`keystore/dev.p12`，即没有 `keystore.properties` 时的默认 release 签名），与用户手机上已装版本一致，可直接覆盖升级。
- 仓库公开：App 通过 `https://api.github.com/repos/HongRong02/codeg-android/releases` 匿名读取，不带任何令牌。
- 不动现有 `release.yml`（语义版本 + 私有密钥那套，保留不用）。

## 需求
### CI（新建 `.github/workflows/auto-release.yml`）
- 触发：push 到 `main`（忽略只改 `docs/**`、`.taskmaster/**`、`**/*.md` 的提交）+ `workflow_dispatch`。
- checkout 用 `fetch-depth: 0`；`versionCode = git rev-list --count HEAD`，`versionName = 1.1.<versionCode>`。
- 构建：`./gradlew :app:assembleRelease -PcodegVersionCode=<n> -PcodegVersionName=<name>`（不写 keystore.properties，走 dev 签名）。用 apksigner 校验签名。
- 发布：tag `build-<versionCode>`，标题 `Codeg Android <versionName>`，正文为上一个 `build-*` 以来的提交标题列表；资源 `codeg-android-<versionName>.apk` 与其 `.sha256`；`gh release create ... --latest`。
- 只保留最近 10 个 `build-*` release（删 release 和 tag），避免堆积。
- `permissions: contents: write`；action 版本照 `verify.yml` 用 commit SHA 固定。
- 同一时间只跑一个（`concurrency: auto-release`，不取消进行中的）。

### Gradle（`app/build.gradle.kts`）
- `versionCode` / `versionName` 读 Gradle 属性 `codegVersionCode` / `codegVersionName`，缺省保持现值（2 / "1.0.1"），本地和 verify.yml 构建不受影响。

### App
- 检查：新建 `A/core/update/UpdateChecker.kt`：用现有 Ktor client（或单独 OkHttp 引擎实例，不带 codeg 鉴权头）请求 `releases?per_page=20`，在非 draft、非 prerelease、tag 匹配 `build-(\d+)` 的里取最大编号；若大于 `BuildConfig.VERSION_CODE` 且有 `.apk` 资源，返回 `{versionName, versionCode, notes, apkUrl, sha256Url, size}`。网络失败静默返回 null。
- 时机：App 启动后（每 6 小时最多一次，时间戳存 DataStore）自动检查；设置 → 关于 页加"检查更新"按钮（手动，显示"已是最新"/错误）。
- 提示：有新版本时在主界面顶部显示横幅（复用/参照会话页 `NoticeBanner` 样式，不要改它的 private 实现，可在 `core/update/` 新写一个），内容"有新版本 x.y.z"+"更新"按钮+关闭；点开显示更新说明对话框。关闭后同一版本本次启动内不再提示。
- 下载：用 `DownloadManager` 或 OkHttp 流式下载到 `cacheDir/updates/`，显示进度；下载后校验 sha256（有 .sha256 资源时），不一致报错并删除。
- 安装：`FileProvider`（新增 `res/xml/update_paths.xml`，authority `${applicationId}.updates`）+ `Intent.ACTION_VIEW`（`application/vnd.android.package-archive`，`FLAG_GRANT_READ_URI_PERMISSION`）。Manifest 加 `REQUEST_INSTALL_PACKAGES`。若 `packageManager.canRequestPackageInstalls()` 为 false，先引导到 `Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES`（带包名），返回后继续。
- 关于页显示当前版本 `versionName (versionCode)`。

## 改动范围
- 新建：`.github/workflows/auto-release.yml`
- 修改：`app/build.gradle.kts`（只改 versionCode/versionName 两行的取值方式）
- 修改：`app/src/main/AndroidManifest.xml`（权限 + provider）
- 新建：`app/src/main/res/xml/update_paths.xml`
- 新建：`A/core/update/` 下文件（UpdateChecker、UpdateDownloader、UpdateViewModel、UpdateBanner 等）
- 修改：`A/feature/main/MainShell.kt`（只在顶部挂横幅一处）
- 修改：`A/feature/settings/SettingsScreen.kt`（只改 `AboutScreen`，约 279 行）
- 新建：`R/values/strings_update.xml`、`R/values-zh-rCN/strings_update.xml`
- 单测：版本比较、tag 解析、release JSON 解析（用 ktor-client-mock）
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- app：`A/feature/main/MainShell.kt:113 MainShell`；`A/feature/settings/SettingsScreen.kt:279 AboutScreen`；`A/feature/sessiondetail/SessionDetailScreen.kt:407 NoticeBanner`（只参考样式）；`A/MainActivity.kt:21`；DataStore 用法按 `.taskmaster/docs/code-index/core-datastore.md` 查。
- 构建：`app/build.gradle.kts:41-42`（版本）、`:53-69`（签名，不改）；`.github/workflows/verify.yml`（action SHA 与步骤写法）、`release.yml`（apksigner 校验写法可参考）。

## 验收
- 推到 `task/T41-apk-auto-update` 后跑 verify.yml 通过；auto-release.yml 用 `workflow_dispatch --ref task/T41-apk-auto-update` 试跑一次，能产出 `build-<n>` release（试跑后把这个测试 release 删掉，或在报告里写明 tag 由协调方处理）。
- 真机步骤写在报告里：装低版本 → 启动出现横幅 → 更新 → 系统安装器 → 覆盖安装后数据仍在。
- CI 通过。
