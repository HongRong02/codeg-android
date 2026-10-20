# S5 服务器配置：首次设置、多服务器、从原生版迁移

| 字段 | 值 |
|---|---|
| 批次 | SHELL1（纯套壳第一版） |
| 分支 | `task/S5-server`，基于 `shell` |
| 模式 | 开发任务，CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | S1–S8 全部并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`。网页端源码（只读参考）在 WSL `~/projects/codeg`（分支 `hongrong/quota-slots`），需要了解网页行为时去那里查，不要修改它。

## 目标
用户能添加、切换多个 codeg 服务器；从旧原生版（1.1.x）升级上来时服务器列表保留，并且尽量免登录。

## 要求
1. 把骨架里的 SharedPreferences 占位实现换成正式实现，**保持契约**：`val current: StateFlow<String?>`（选中服务器的 origin，不带结尾斜杠；没有则为 null）、`@Composable fun SetupScreen(host: ShellHost)`、`@Composable fun ManagerScreen(host: ShellHost, onClose: () -> Unit)`。类名、包名和这三个成员的签名不能变（`ShellApp.kt` 在用）。`current` 在 `onCreate` 钩子返回前就要有值（同步读一次，或在 `onCreate` 里 `runBlocking` 读 DataStore 首个值），否则首次渲染会闪一下设置页。
2. **复用旧版存储**：旧原生版的服务器列表在 DataStore（`codeg_prefs`）里，读写类是 `core/datastore/ServerStore.kt` + `ServerProfile.kt`（Hilt 单例，DataStore 由 `di/StorageModule.kt` 提供）；令牌在 `core/security/KeystoreSecretStore.kt`，以 profile id 为键。直接复用这些类，升级上来的用户不用重新添加服务器。`ServerFeature` 不归 Hilt 管理，可以定义一个 `@EntryPoint`，用 `EntryPointAccessors.fromApplication(...)` 拿到 `ServerStore`/`SecretStore`。`core/datastore/`、`core/security/`、`di/` 归你。
3. **令牌迁移**：旧版保存了令牌，网页端的令牌在 `localStorage["codeg_token"]`。每个服务器只做一次：页面加载完成（`onPageFinished`，且页面 origin 等于该服务器）后，用 JS 检查 `localStorage.codeg_token`；为空就写入旧令牌并 `reload()` 一次，然后标记"已迁移"。之后令牌以网页为准，原生层不再管。令牌绝不写日志，也不发给其他 origin。
4. **SetupScreen**（首次启动）：输入服务器地址（没写协议时自动补 `http://`，只保留 origin，去掉路径和结尾斜杠），名称可选。保存前用 OkHttp 对 `<origin>/` 发 GET 探测：能连上就通过；连不上、超时、证书错误分别给出明确提示，但允许"仍然保存"。原生层不输入令牌，登录交给网页。
5. **ManagerScreen**：列表（名称、地址、当前选中标记），支持添加、编辑、删除（删除要二次确认；删的是当前服务器时切到另一个，没有了就回到 SetupScreen），点一项即切换并 `onClose()`。切换服务器就是 `current` 变成新值，骨架会重建 WebView。
6. **入口**：桌面图标长按出现"切换服务器"快捷方式。写 `res/xml/shortcuts.xml`（Manifest 已引用），intent 的 action 用 `app.codeg.android.action.MANAGE_SERVERS`，targetPackage 用 `${applicationId}` 对应的包（注意 debug 包名带 `.debug` 后缀，静态 shortcuts.xml 写不了变量的话改用 `ShortcutManagerCompat` 动态注册），targetClass `app.codeg.android.MainActivity`。在 `onIntent` 里识别这个 action，调用 `host.openServerManager()`。S6 的错误页上也有"切换服务器"按钮，它自己调用 `host.openServerManager()`。
7. 界面用 Compose + Material3（骨架根部已经包了 `ShellTheme`，由 S2 实现），风格简洁，接近网页端的 shadcn 中性风格：圆角输入框、主按钮。

## 改动范围
`shell/server/**`、`core/datastore/**`、`core/security/**`、`di/**`、`res/xml/shortcuts.xml`、`res/values*/strings_shell_server.xml`（不再用的 `strings_servers_onboarding.xml` 两份可以删除）、`.taskmaster/docs/shell/tests/S5.md`

## 验收
- CI（verify.yml）在 `task/S5-server` 上通过。
- 测试用例写在 `.taskmaster/docs/shell/tests/S5.md`，由协调方在真机上统一执行。
