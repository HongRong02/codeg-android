# T16 文件查看增强：图片预览、历史版本、大文件

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T05　可并行：与 T17–T20 并行（T17–T20 只依赖 T05）
- 分支：`task/T16-file-viewer`

## 目标
在现有项目文件浏览（`FolderFilesView`，已支持文本预览）基础上，增加图片预览、查看某提交版本的文件，并抽出可复用的 `FileViewer` 组件供 git 相关任务使用。

## 需求
- 抽出 `FileViewer(content: FileViewerContent)` 组件：文本（行号 + 语法高亮复用 `CodeBlock`）、图片（base64 解码显示，可缩放）、二进制/过大文件（显示大小与提示）。
- 图片：用 `read_file_base64` 读取工作区图片。
- 历史版本：用 `git_show_file`（文本）/ `git_show_file_base64`（图片）读取指定提交的文件；在提交详情（`FolderCommitsView` 的 `CommitDetail`）中点击文件可查看该版本。
- `FolderFilesView` 改为使用新组件，保留现有 4000 行截断逻辑。

## 改动范围
- 新建：`A/core/designsystem/component/FileViewer.kt`
- 修改：`A/feature/projects/FolderFilesView.kt`、`FolderCommitsView.kt`（仅 `CommitDetail` 中的文件点击）
- 修改：`A/core/network/CodegClient.kt`（新增 `// region file-viewer`）
- 新建：`A/core/model/wire/FileViewerRequests.kt`、`R/values*/strings_file_viewer.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端：`SRV/handlers/files.rs:115 read_file_base64` · `ReadFileBase64Params :35`；`SRV/handlers/git.rs:115 git_show_file` · `GitShowFileParams :109`；`:131 git_show_file_base64` · `GitShowFileBase64Params :124`
- app：`core/network/CodegClient.kt:329 readFilePreview`（已有文本读取写法）、`:345 gitShowDiff`；`core/model/Git.kt:30 FilePreviewContent`
- `feature/projects/FolderFilesView.kt`：`55 FolderFilesView`、`128 FilePreview`、`158 FileContent`、`181 formatBytes`
- `feature/projects/FolderCommitsView.kt:239 CommitDetail`
- `core/designsystem/component/CodeBlock.kt:52 CodeBlock`；`feature/sessiondetail/rendering/InlineImage.kt:27 InlineImage`（base64 图片显示写法）

## 参考（只读，按需打开）
- `WEB/components/files/file-viewer-drawer.tsx`、`image-preview.tsx`、`file-path-breadcrumb.tsx`

## 验收
- 文本、图片、超大文件三种情况显示正确；提交详情中能查看历史版本。
- CI 通过。
