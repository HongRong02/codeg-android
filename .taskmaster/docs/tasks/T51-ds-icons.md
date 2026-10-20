# T51 设计系统：lucide 图标集

> 先读 `.taskmaster/docs/CONVENTIONS.md`（**测试文档模式**）和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：T43–T46、T52–T54 互相并行；T47–T50 依赖本任务
- 分支：`task/T51-ds-icons`　测试方式：**测试文档模式**（不跑 CI）

## 目标
网页端全部图标来自 `lucide-react@^0.564.0`，App 用的是 Material Icons（约 75 种、57 个文件）。提供与网页端完全一致的 lucide 图标（Compose `ImageVector`），供第二阶段替换。

## 需求
1. 生成脚本 `scripts/gen_lucide_icons.py`（纯 Python 3 标准库）：
   - 输入：`lucide-static@0.564.0` 的 `icons/*.svg`（在 WSL 用 `npm pack lucide-static@0.564.0` 下载到 `/tmp` 解压；**不要**把 svg 或 tgz 提交进仓库）。
   - 图标清单 = 网页端用到的全部 lucide 图标（扫描 `WEB/**/*.tsx` 中 `from "lucide-react"` 的 import，含多行 import、`XxxIcon` 别名写法统一成 lucide 名）∪ 第 3 条映射表需要的图标。清单写到 `scripts/lucide_icons.txt`（每行一个 kebab-case 名，排序）并提交。
   - 把 svg 的 `path`、`circle`、`rect`（含 rx/ry）、`line`、`polyline`、`polygon`、`ellipse` 全部转换为 path data；viewport 24×24，stroke 宽 2，`StrokeCap.Round`、`StrokeJoin.Round`，fill 为空，stroke 用 `SolidColor(Color.Black)`（`Icon` 的 tint 会覆盖）。
   - 输出 `A/core/designsystem/icon/LucideIcons.kt`（文件头注明“由脚本生成，勿手改”及 lucide 的 ISC 许可证声明）：`object Lucide { val MessageSquare: ImageVector by lazy { ... } ... }`，属性名为 PascalCase（与 lucide-react 导出名一致，去掉 `Icon` 后缀）。单个文件过大（> 3000 行）时按首字母拆成 `LucideIconsA_F.kt` 等多个文件，用 `val Lucide.Xxx` 扩展属性或拆多个 object 均可，但对外调用方式统一为 `Lucide.Xxx`。
2. `A/core/designsystem/icon/CodegIcon.kt`：`@Composable fun CodegIcon(icon: ImageVector, contentDescription: String?, modifier: Modifier = Modifier, size: Dp = 16.dp, tint: Color = LocalContentColor.current)`（网页端默认 `size-4`），并提供 `@Preview`（明/暗）与 `internal fun T51Gallery()`：网格展示全部图标及名字。
3. 映射表 `.taskmaster/docs/icon-map.md`：App 现有每个 Material 图标 → 对应 lucide 名。**优先取网页端同一语义处实际用的图标**（在 WEB 里 grep 相同功能的组件确认，例如会话用的 `MessageSquare`、刷新 `RefreshCw`、更多 `Ellipsis`/`EllipsisVertical` 等，以网页端实际为准），表格列：Material 名 | lucide 名 | 网页端出处（文件）| 备注。App 现有图标清单用 `grep -rhoE "Icons\.[A-Za-z.]+\.[A-Za-z0-9]+" A/` 自行统计（含 `AutoMirrored`）。
4. 不改任何现有调用点（替换由 T47–T50 做）。

## 改动范围
- 新建：`scripts/gen_lucide_icons.py`、`scripts/lucide_icons.txt`、`A/core/designsystem/icon/LucideIcons*.kt`、`A/core/designsystem/icon/CodegIcon.kt`、`.taskmaster/docs/icon-map.md`
- 测试文档：`.taskmaster/docs/tests/T51-ds-icons.md`；单测：`app/src/test/.../designsystem/icon/`（如：抽查若干图标 `ImageVector` 的 viewport 与 path 数量）
- 范围外文件一律不改；不新增 Gradle 依赖。

## 代码定位
- 现有组件索引：`.taskmaster/docs/code-index/core-designsystem.md`
- 主题：`A/core/designsystem/theme/CodegTheme.kt`

## 参考（只读）
- `WEB/components/**`（lucide 用法）、`WEB/components/ui/button.tsx`（图标默认尺寸 `size-4`）

## 验收
- 测试文档按模板写全：图标数量、抽查清单（至少 20 个常用图标与网页端截图对照：形状、线宽、圆角端点）、明/暗 Gallery 检查步骤；自评编译风险（生成文件的方法体大小、名字冲突如 `Lucide.Package`/Kotlin 关键字）。
