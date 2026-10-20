# UI1 测试结果

- 版本：sit `4ba52ca` 的 debug APK（`app.codeg.android.mine.debug`，与正式版 1.1.139 并存）
- 设备：vivo V1824A，Android 11；服务器：测试服 T13Test（`127.0.0.1:3082`，adb reverse）
- 计划：`PLAN-UI1.md`（T61 合并中）；合并前先记录探索性发现

| 用例 | 结果 | bug 单 | 截图 / 说明 |
|---|---|---|---|
| 首次配置 → 添加服务器 → 测试连接 | ❌ | B01 | 点击后 3 秒内无任何反馈；`OnboardingRoot` 不在 `CodegToasterHost` 内 |
| 首次配置 → 保存服务器 → 进入会话列表 | ✅ | | 连接成功，会话列表正常 |
| debug 版更新横幅 | ❌ | B02 | debug 版 versionName=1.0.1，更新检查未区分 debug，提示升级到正式版 1.1.144 |

## 待归档的 bug（测试结束后按区域写成 bug 单）
- B01 区域 app/main：首次配置流程缺少 Toast 宿主（`A/app/CodegApp.kt` 的 `OnboardingRoot`，或把宿主上移到 `CodegApp` 根部）。来源：T58。
- B02 区域 misc（core/update）：debug 版不应检查/提示正式版更新（或按 applicationId 区分）。来源：T41。

## 测试环境备注
- vivo 的安全键盘（令牌输入框）弹出时 `screencap` 得到 0 字节图片，截图前先点空白处收起键盘。
- 添加服务器页面按返回键会直接关闭整个页面、丢弃已填内容（是否需要确认待对照网页端）。
