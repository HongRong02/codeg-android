# T46 设计系统：标签页、徽章、列表行与反馈

> 先读 `.taskmaster/docs/CONVENTIONS.md` 和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：T43–T46 互相并行；T47–T50 依赖本任务
- 分支：`task/T46-ds-tabs-badges-rows`

## 目标
第一阶段：在设计系统中提供与网页端一致的组件，供第二阶段各页面替换使用。

## 需求
新建 `Tabs.kt`：`CodegTabs(selected, tabs, onSelect)`（TabsList `bg --muted rounded-lg p-[3px] h-9`，trigger `rounded-md text-sm font-medium`，选中 `--background` + `shadow-sm`，暗色选中 `--input/30` + `border --input`）。扩展 `Badges.kt`：`CodegBadge(text, variant = Default/Secondary/Destructive/Outline)`（`rounded-md px-2 py-0.5 text-xs font-medium`），`CodegFilterChip(selected, label, onClick)`（未选 outline、选中 secondary）。新建 `Feedback.kt`：`CodegSeparator`、`CodegSkeleton`（`--accent` 脉冲）、`CodegProgress`（`h-2 rounded-full bg --primary/20`，条 `--primary`）。新建 `ListRows.kt`：`CodegListRow`（参照网页侧栏会话卡片与 row-more-button：48–56dp 高、`rounded-md`、按下 `--accent`、选中 `--accent` 背景 + `--accent-foreground`、trailing 更多按钮）、`CodegSectionHeader(title, count, action, collapsed)`（参照 sidebar-section-header / sidebar-folder-group-header）、`CodegRowMoreButton`。
- 每个组件提供 `@Preview`（明/暗）；文件内提供 `internal fun T46Gallery()` 汇总所有变体。
- 不改任何 `A/feature/**` 文件。

## 改动范围
- `A/core/designsystem/component/Tabs.kt`、`Feedback.kt`、`ListRows.kt`（新建）、`Badges.kt`（扩展，保持现有签名）；`strings_ds_rows.xml` + zh
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 主题：`A/core/designsystem/theme/WebThemes.kt`（`WebTokens`）、`Color.kt`（`CodegColors`、`withOpacity`）、`CodegTheme.kt`、`CodegShapes`（按 code-index `core-designsystem.md` 查）
- 现有组件：`.taskmaster/docs/code-index/core-designsystem.md`

## 参考（只读）
- `WEB/components/ui/tabs.tsx`、`badge.tsx`、`separator.tsx`、`skeleton.tsx`、`progress.tsx`；`WEB/components/conversations/sidebar-conversation-card.tsx`、`sidebar-section-header.tsx`、`sidebar-folder-group-header.tsx`；`WEB/components/layout/row-more-button.tsx`
- `WEB/app/globals.css`（token 与圆角）

## 验收
- `./gradlew :app:check` 通过（CI verify.yml）；报告里附组件清单与签名。
