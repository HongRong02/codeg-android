# S3 上传：文件和图片选择、拍照

| 字段 | 值 |
|---|---|
| 批次 | SHELL1（纯套壳第一版） |
| 分支 | `task/S3-upload`，基于 `shell` |
| 模式 | 开发任务，CI 模式（见 `shell/CONVENTIONS.md`） |
| 同层并行 | S1–S8 全部并行，互不依赖 |

> 先读 `.taskmaster/docs/shell/CONVENTIONS.md`。网页端源码（只读参考）在 WSL `~/projects/codeg`（分支 `hongrong/quota-slots`），需要了解网页行为时去那里查，不要修改它。

## 目标
网页里所有 `<input type=file>`（聊天附件、图片、导入等）在 App 里都能选文件、拍照。

## 要求
1. 实现 `onShowFileChooser`：按 `FileChooserParams` 的 `acceptTypes`、`mode`（单选/多选）、`isCaptureEnabled` 决定打开方式。
   - 图片/视频类型：优先用系统照片选择器（`PickVisualMedia`/`PickMultipleVisualMedia`），同时提供"拍照"入口。
   - 其他类型：用 `OpenDocument`/`OpenMultipleDocuments`，把 accept 转成 MIME 过滤（`.png` 这类扩展名转成 MIME，不认识的用 `*/*`）。
   - 需要让用户在"相册 / 拍照 / 文件"之间选择时，在 `Overlay` 里画一个原生底部菜单（Compose Material3，骨架根部已包 `ShellTheme`）。
2. Activity result launcher 必须在 `onCreate` 钩子里注册（`host.activity.registerForActivityResult`）。
3. **每次回调必须且只能调用一次 `callback.onReceiveValue`**，取消时传 null；否则网页下次点上传会没反应。用户取消、关掉底部菜单、Activity 重建、权限被拒都要覆盖。
4. 拍照：图片存到 `cacheDir/camera/`，通过 FileProvider（authority `${applicationId}.files`）交给相机（`TakePicture`）。不要声明 CAMERA 权限（Manifest 里没有声明时，`TakePicture` 不需要它）。
5. 返回的 content:// Uri 要能被 WebView 读取。
6. 拖拽、粘贴图片属于网页自己的逻辑，不用管。

## 改动范围
`shell/upload/**`、`res/values*/strings_shell_upload.xml`、`.taskmaster/docs/shell/tests/S3.md`

## 验收
- CI（verify.yml）在 `task/S3-upload` 上通过。
- 测试用例写在 `.taskmaster/docs/shell/tests/S3.md`，由协调方在真机上统一执行。
