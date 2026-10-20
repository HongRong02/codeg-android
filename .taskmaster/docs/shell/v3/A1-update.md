# A1 更新改为从当前服务器下载，并提供网页可调用的"检查更新"

| 字段 | 值 |
|---|---|
| 批次 | SHELL1 第三版 |
| 分支 | `task/A1-update`，基于最新 `shell` |
| 模式 | CI 模式（见 `shell/CONVENTIONS.md`） |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md` 和第一版任务单 `.taskmaster/docs/shell/S7-update.md`（现有更新逻辑在 `shell/update/`、`core/update/`）。

## 背景
国内网络下手机经常访问不了 GitHub，旧版检查更新失败，用户收不到新版本。现在用户的 codeg 服务器会分发 APK（另一个任务在服务端实现），接口如下（**公开，不需要令牌**）：
- `GET <server>/api/app_release/android/latest` → `{"version_code":180,"version_name":"1.1.180","notes":"...","apk_file":"codeg-android-1.1.180.apk","sha256":"<hex>","size_bytes":45506347,"published_at":"...","apk_url":"/api/app_release/android/apk"}`；服务器没有发布时返回 404。
- `GET <server>/api/app_release/android/apk` → APK 文件。

`<server>` 就是当前选中的服务器 origin（`host.currentServerUrl`）。

## 要求
1. **更新来源**：先请求当前服务器的 `latest`；成功则以它为准（`version_code` 与 `BuildConfig.VERSION_CODE` 比较），下载 `<server><apk_url>`；返回 404、网络错误或超时（10 秒）时，退回现有的 GitHub 检查逻辑。下载完成后**必须校验 SHA-256**（服务器来源时用 `sha256` 字段；GitHub 来源时如果 Release 有 `.sha256` 资源也校验），不一致就删除文件并提示失败，不安装。
2. **网页可调用的检查更新**：通过 `addJavascriptInterface` 暴露 `CodegShellUpdate`，只在页面 origin 等于当前服务器时响应：
   - `check()`：手动检查。忽略"稍后"的 24 小时静默和 6 小时节流；有新版本就显示现有的更新对话框；已是最新时显示原生提示"已是最新版本（1.1.xxx）"；检查失败时提示原因。
   - `versionName()`：返回当前 `BuildConfig.VERSION_NAME`（网页用来显示）。
   - 注入时机照现有其他 JS 桥的做法（S4/S8 已有先例），所有回调切回主线程。
3. **debug 构建**：自动检查仍然关闭；手动 `check()` 允许执行并显示结果，但对话框里标注"当前为调试版，安装的是正式版（另一个应用）"。
4. 自动检查（`onResume`，6 小时节流）同样使用新的来源顺序。
5. 用户可见文案中英文都要有。

## 改动范围
`shell/update/**`、`core/update/**`、`res/values*/strings_shell_update.xml`、`.taskmaster/docs/shell/tests/S7.md`（补充用例：服务器有新版、服务器 404 回退 GitHub、SHA-256 不一致、网页调用 check() 已是最新/有新版、非当前服务器 origin 调用被拒绝）

## 验收
- CI（verify.yml）在 `task/A1-update` 上通过。
- 单元测试覆盖：`latest` JSON 解析、来源回退顺序、SHA-256 校验、版本比较。
