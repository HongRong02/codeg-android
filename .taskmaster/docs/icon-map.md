# T51 Material → lucide 映射

来源：App 全目录仅扫描 `Icons.*` 引用；网页端仅检查 components 下 lucide import 与对应 JSX。保留全部样式/AutoMirrored 引用形式，不代表立即替换调用点。lucide-static 固定 0.564.0；同形旧别名仍作为独立公开属性供网页端命名使用。

| Material 名 | lucide 名 | 网页端出处（文件） | 备注 |
|---|---|---|---|
| Icons.AutoMirrored.Filled.KeyboardArrowRight | `ChevronRight` | `WEB/components/shared/server-file-browser-dialog.tsx:255` | 目录层级/进入子目录；折叠处也使用 ChevronRight；lucide 不自动 RTL 镜像 |
| Icons.AutoMirrored.Rounded.AltRoute | `GitBranch` | `WEB/components/layout/aux-panel-git-log-tab.tsx:795` | 网页端实际 JSX 用法；替换时按具体上下文复核；lucide 不自动 RTL 镜像，调用方按方向语义决定 |
| Icons.AutoMirrored.Rounded.ArrowBack | `ArrowLeft` | `WEB/components/automations/automation-editor.tsx:340` | 网页端实际 JSX 用法；替换时按具体上下文复核；lucide 不自动 RTL 镜像，调用方按方向语义决定 |
| Icons.AutoMirrored.Rounded.Assignment | `ClipboardList` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认；lucide 不自动 RTL 镜像，调用方按方向语义决定 |
| Icons.AutoMirrored.Rounded.CallSplit | `GitFork` | `WEB/components/layout/branch-dropdown.tsx:661` | 网页端实际 JSX 用法；替换时按具体上下文复核；lucide 不自动 RTL 镜像，调用方按方向语义决定 |
| Icons.AutoMirrored.Rounded.Chat | `MessageSquare` | `WEB/components/chat/conversation-context-bar.tsx:574` | 会话/聊天模式；快捷消息设置导航实际使用 MessageSquareText（settings-shell.tsx:112）；lucide 不自动 RTL 镜像 |
| Icons.AutoMirrored.Rounded.InsertDriveFile | `File` | `WEB/components/ai-elements/file-tree.tsx:490` | 网页端实际 JSX 用法；替换时按具体上下文复核；lucide 不自动 RTL 镜像，调用方按方向语义决定 |
| Icons.AutoMirrored.Rounded.KeyboardArrowRight | `ChevronRight` | `WEB/components/shared/server-file-browser-dialog.tsx:255` | 目录层级/进入子目录；折叠处也使用 ChevronRight；lucide 不自动 RTL 镜像 |
| Icons.AutoMirrored.Rounded.Send | `Send` | `WEB/components/chat/feedback-dialog.tsx:106` | 网页端实际 JSX 用法；替换时按具体上下文复核；lucide 不自动 RTL 镜像，调用方按方向语义决定 |
| Icons.AutoMirrored.Rounded.Undo | `Undo2` | `WEB/components/layout/aux-panel-git-changes-tab.tsx:1603` | Git 回滚工作区，与网页端同一操作；lucide 不自动 RTL 镜像 |
| Icons.Default.Check | `Check` | `WEB/components/ai-elements/mermaid-block.tsx:579` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Default.Close | `X` | `WEB/components/ai-elements/mermaid-block.tsx:740` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Default.MoreVert | `EllipsisVertical` | `WEB/components/conversations/conversation-detail-header.tsx:309` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Outlined.Bolt | `Zap` | `WEB/components/automations/automations-page.tsx:462` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Outlined.FolderOpen | `FolderOpen` | `WEB/components/conversations/sidebar-conversation-list.tsx:458` | 侧栏目录；浏览文件时沿用 FolderOpen |
| Icons.Outlined.Forum | `MessageSquare` | `WEB/components/chat/conversation-context-bar.tsx:574` | 会话/聊天模式；快捷消息设置导航实际使用 MessageSquareText（settings-shell.tsx:112） |
| Icons.Outlined.Search | `Search` | `WEB/components/chat/composer/composer-add-menu.tsx:239` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Outlined.Settings | `Settings` | `WEB/components/conversations/sidebar-conversation-list.tsx:3523` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Add | `Plus` | `WEB/components/ai-elements/commit.tsx:405` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Adjust | `CircleDot` | `WEB/components/message/plan-card.tsx:87` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Archive | `Archive` | `WEB/components/layout/aux-panel-git-changes-tab.tsx:1569` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.ArrowDownward | `ArrowDown` | `WEB/components/ai-elements/message-thread.tsx:361` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.ArrowUpward | `ArrowUp` | `WEB/components/message/selection-action-bubble.tsx:473` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.AutoAwesome | `Sparkles` | `WEB/components/canvas/add-node-menu.tsx:337` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Bedtime | `Moon` | `WEB/components/settings/appearance-settings.tsx:134` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Bolt | `Zap` | `WEB/components/automations/automations-page.tsx:462` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Brush | `Palette` | `WEB/components/settings/settings-shell.tsx:72` | 外观设置入口使用 Palette，非笔刷 |
| Icons.Rounded.Build | `Wrench` | `WEB/components/ai-elements/tool.tsx:110` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.CalendarToday | `Calendar` | `WEB/components/token-usage/token-usage-filters.tsx:400` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Check | `Check` | `WEB/components/ai-elements/mermaid-block.tsx:579` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.CheckCircle | `CircleCheckBig` | `WEB/components/message/plan-card.tsx:81` | 计划已完成：网页端 CheckCircle2Icon 为 CircleCheckBig 同形别名；其他成功状态按页面确认 |
| Icons.Rounded.Checklist | `ListTodo` | `WEB/components/chat/agent-plan-overlay.tsx:149` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.ChevronRight | `ChevronRight` | `WEB/components/shared/server-file-browser-dialog.tsx:255` | 目录层级/进入子目录；折叠处也使用 ChevronRight |
| Icons.Rounded.Close | `X` | `WEB/components/ai-elements/mermaid-block.tsx:740` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Cloud | `Cloud` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认 |
| Icons.Rounded.CloudDownload | `CloudDownload` | `WEB/components/layout/aux-panel-git-changes-tab.tsx:1546` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.CloudOff | `CloudOff` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认 |
| Icons.Rounded.CloudUpload | `CloudUpload` | `WEB/components/layout/aux-panel-git-changes-tab.tsx:1564` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Code | `Code` | `WEB/components/canvas/nodes/file-node.tsx:179` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.ContentCopy | `Copy` | `WEB/components/ai-elements/mermaid-block.tsx:579` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.CreateNewFolder | `FolderPlus` | `WEB/components/import-sessions/import-sessions-window.tsx:672` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Delete | `Trash2` | `WEB/components/ai-elements/terminal.tsx:265` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.DeleteOutline | `Trash2` | `WEB/components/ai-elements/terminal.tsx:265` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Description | `FileText` | `WEB/components/canvas/add-node-menu.tsx:450` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Difference | `FileDiff` | `WEB/components/message/reply-artifacts.tsx:252` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Dns | `Server` | `WEB/components/settings/model-provider-settings.tsx:141` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Edit | `Pencil` | `WEB/components/automations/automations-page.tsx:856` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.ErrorOutline | `CircleAlert` | `WEB/components/chat/feedback-notes-display.tsx:77` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.ExpandLess | `ChevronUp` | `WEB/components/browser/browser-find-bar.tsx:146` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.ExpandMore | `ChevronDown` | `WEB/components/ai-elements/tool.tsx:118` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Extension | `PlugZap` | `WEB/components/settings/settings-shell.tsx:87` | 设置 MCP 导航使用 PlugZap；通用扩展插件若非 MCP 按页面确认 |
| Icons.Rounded.Folder | `Folder` | `WEB/components/shared/folder-select.tsx:62` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.FolderOpen | `FolderOpen` | `WEB/components/conversations/sidebar-conversation-list.tsx:458` | 侧栏目录；浏览文件时沿用 FolderOpen |
| Icons.Rounded.Forum | `MessageSquare` | `WEB/components/chat/conversation-context-bar.tsx:574` | 会话/聊天模式；快捷消息设置导航实际使用 MessageSquareText（settings-shell.tsx:112） |
| Icons.Rounded.GppMaybe | `ShieldAlert` | `WEB/components/chat/permission-dialog.tsx:126` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.GraphicEq | `AudioLines` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认 |
| Icons.Rounded.Groups | `Users` | `WEB/components/message/content-parts-renderer.tsx:922` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.History | `History` | `WEB/components/settings/backup-settings.tsx:858` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Home | `Home` | `WEB/components/shared/server-file-browser-dialog.tsx:291` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Image | `Image` | `WEB/components/settings/workspace-background-section.tsx:100` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Inbox | `Inbox` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认 |
| Icons.Rounded.Info | `Info` | `WEB/components/conversations/conversation-detail-header.tsx:337` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Key | `KeyRound` | `WEB/components/chat/permission-dialog.tsx:289` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.KeyboardArrowDown | `ChevronDown` | `WEB/components/ai-elements/tool.tsx:118` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Language | `Globe` | `WEB/components/automations/automation-editor.tsx:630` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Layers | `Layers` | `WEB/components/canvas/add-node-menu.tsx:189` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Lock | `Lock` | `WEB/components/chat/composer/composer-add-menu.tsx:338` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Memory | `Cpu` | `WEB/components/message/collab-agent-card.tsx:173` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.MoreHoriz | `Ellipsis` | `WEB/components/conversations/sidebar-section-header.tsx:245` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.MoreVert | `EllipsisVertical` | `WEB/components/conversations/conversation-detail-header.tsx:309` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.NotificationsActive | `BellRing` | `WEB/components/settings/desktop-notification-settings.tsx:244` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Person | `User` | `WEB/components/layout/aux-panel-git-log-tab.tsx:1048` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.PhoneAndroid | `Smartphone` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认 |
| Icons.Rounded.PlayArrow | `Play` | `WEB/components/automations/automations-page.tsx:840` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Policy | `Shield` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认 |
| Icons.Rounded.Psychology | `Brain` | `WEB/components/ai-elements/reasoning.tsx:186` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.PushPin | `Pin` | `WEB/components/conversations/conversation-detail-header.tsx:329` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.QrCode | `QrCode` | `WEB/components/chat/remote-control-strip.tsx:111` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.RadioButtonUnchecked | `CircleDashed` | `WEB/components/message/plan-card.tsx:91` | 计划待办：网页端 CircleDashedIcon；选中状态使用 CircleDot（同文件:87） |
| Icons.Rounded.Refresh | `RefreshCw` | `WEB/components/layout/aux-panel-git-changes-tab.tsx:1612` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Remove | `Minus` | `WEB/components/ai-elements/commit.tsx:434` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Schedule | `Clock` | `WEB/components/automations/automations-page.tsx:1239` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.School | `GraduationCap` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认 |
| Icons.Rounded.Search | `Search` | `WEB/components/chat/composer/composer-add-menu.tsx:239` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Settings | `Settings` | `WEB/components/conversations/sidebar-conversation-list.tsx:3523` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Stop | `Square` | `WEB/components/chat/message-input.tsx:1812` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Sync | `RefreshCw` | `WEB/components/layout/aux-panel-git-changes-tab.tsx:1612` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Terminal | `Terminal` | `WEB/components/ai-elements/terminal.tsx:140` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Translate | `Languages` | `WEB/components/settings/system-network-settings.tsx:807` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Tune | `SlidersHorizontal` | `WEB/components/automations/automations-page.tsx:1246` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.UnfoldMore | `ChevronsUpDown` | `WEB/components/canvas/canvas-dock.tsx:382` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Verified | `BadgeCheck` | — | 网页端未找到同名 JSX；按 Material 语义补充，待替换页面确认 |
| Icons.Rounded.Visibility | `Eye` | `WEB/components/browser/browser-agent-access.tsx:160` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.VisibilityOff | `EyeOff` | `WEB/components/forge/forge-auto-settings.tsx:515` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.Warning | `TriangleAlert` | `WEB/components/browser/browser-agent-access.tsx:468` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
| Icons.Rounded.WarningAmber | `TriangleAlert` | `WEB/components/browser/browser-agent-access.tsx:468` | 网页端实际 JSX 用法；替换时按具体上下文复核 |
