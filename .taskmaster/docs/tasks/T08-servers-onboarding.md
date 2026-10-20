# T08 打磨：服务器列表/编辑与引导页

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：medium　依赖：T01　可并行：T02–T11 互相并行
- 分支：`task/T08-servers-onboarding`

## 目标
对照网页端逐项打磨服务器列表/编辑与引导页，并把顶栏换成 `CodegTopBar`。

## 需求
- 顶栏替换为 `CodegTopBar` / `CodegIconButton`。
- 对照网页端参考检查：列表行高、内边距、字号层级、次要文字颜色（`--muted-foreground`）、卡片（`Modifier.webSurface`）、分隔线、徽章、空状态/加载/错误态。
- 只做视觉与布局调整，不改业务逻辑、不改 ViewModel 的对外状态结构。

## 改动范围
- 修改：`A/feature/server/ServerListScreen.kt`、`ServerEditorScreen.kt`、`A/feature/onboarding/OnboardingScreen.kt`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- `feature/server/ServerListScreen.kt`：`52 ServerListScreen`（顶栏 67 行）、`139 ServerRow`
- `feature/server/ServerEditorScreen.kt`：`35 ServerEditorScreen`（顶栏 43 行）、`120 SectionHeader`
- `feature/onboarding/OnboardingScreen.kt`：`33 OnboardingScreen`、`91 FeatureRow`

## 参考（只读，按需打开）
- `WEB/app/login/`（登录页布局与表单样式）、`WEB/components/ui/card.tsx`、`field.tsx`、`input.tsx`

## 验收
- 与网页端参考逐项对照：间距、字号、颜色 token、圆角、分隔线、空状态/加载/错误态一致或有意识的移动端适配（在提交说明里列出差异及理由）。
- 明暗两种模式、默认 neutral 主题下截图无异常；不引入新的硬编码颜色。
- 本页面的顶栏已换成 `CodegTopBar`（T01）。
- CI `verify.yml` 通过。
