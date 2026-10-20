# T53 设计系统：折叠、头像、输入组、图片预览、命令列表、文件夹徽章

> 先读 `.taskmaster/docs/CONVENTIONS.md`（**测试文档模式**）和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：T43–T46、T51、T52、T54 互相并行；T47–T50 依赖本任务
- 分支：`task/T53-ds-misc`　测试方式：**测试文档模式**（不跑 CI）

## 目标
补齐网页端 `components/ui/` 中映射表未覆盖、但第二阶段页面会用到的组件。

## 需求
每个组件数值按对应 `.tsx` 的 Tailwind class 换算（1 单位 = 4dp），颜色只用 `CodegTheme.colors.web.*`：
- `Collapsible.kt`：`CodegCollapsible(expanded, onExpandedChange, header, content)` 与 `CodegInstantCollapsible`（对应 `instant-collapsible.tsx`：无动画立即展开），带 chevron 旋转。用于设置分组、工具卡片详情。
- `Avatar.kt`：`CodegAvatar(imageUrl/painter?, fallbackText, size)`（`avatar.tsx`：圆形、`--muted` 背景、fallback 首字母）。
- `InputGroup.kt`：`CodegInputGroup(value, onValueChange, leading, trailing, placeholder, ...)`（`input-group.tsx`：输入框内嵌前后缀图标/按钮/文字，整体一个边框与 focus ring）。用于搜索框、带清除按钮的输入。
- `ImagePreview.kt`：`CodegImagePreviewDialog(images, initialIndex, onDismiss)`（`image-preview-dialog.tsx`：全屏、双指缩放、双击放大、左右切换、关闭按钮、索引指示）。图片加载沿用项目里已有的方式（先 grep 现有附件/图片显示代码，用同样的库；没有就只接受 `ImageBitmap`/`Painter`）。
- `Command.kt`：`CodegCommandList(query, onQueryChange, groups, onSelect, empty)`（`command.tsx`：顶部搜索输入、分组标题、条目高亮、空结果文案）。用于搜索页、选择器的可搜索列表。
- `FolderBadge.kt`：`CodegFolderBadge(name, color?)`（`folder-badge.tsx`）。
- 每个组件 `@Preview`（明/暗）；可测的状态逻辑（如 Command 过滤/分组、ImagePreview 缩放边界）写 JVM 单测；每个文件提供 `internal fun T53XxxGallery()`。
- 图标：T51 并行开发中，先用 Material 图标并留 `// TODO(T51): Lucide.Xxx` 注释（写明网页端用的 lucide 名）。

## 改动范围
- `A/core/designsystem/component/Collapsible.kt`、`Avatar.kt`、`InputGroup.kt`、`ImagePreview.kt`、`Command.kt`、`FolderBadge.kt`（均新建）；`strings_ds_misc.xml` + zh；单测；测试文档 `.taskmaster/docs/tests/T53-ds-misc.md`
- 不改 `TextFields.kt`/`Buttons.kt`/`Controls.kt`（T45 负责）；需要按钮/输入框样式时在自己文件里内联实现或等合并后由协调方统一。
- 范围外文件一律不改；不新增 Gradle 依赖。

## 代码定位
- 主题：`A/core/designsystem/theme/WebThemes.kt`、`Color.kt`、`CodegShapes`（按 code-index `core-designsystem.md` 查）
- 图片加载：在 code-index 里搜 `Image`/`Attachment`/`coil` 定位现有用法

## 参考（只读）
- `WEB/components/ui/collapsible.tsx`、`instant-collapsible.tsx`、`avatar.tsx`、`input-group.tsx`、`image-preview-dialog.tsx`、`command.tsx`、`folder-badge.tsx`
- `WEB/app/globals.css`

## 验收
- 测试文档按模板写全：每个组件至少 2 条用例（含明/暗）+ 边界（空列表、超长文字、RTL、超大图片）；对照网页端的允许差异（如移动端无 hover）。
