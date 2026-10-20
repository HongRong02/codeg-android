# 批次 UI1：界面对齐网页端（shadcn radix-maia）

> 格式与状态图例见 `WORKFLOW.md` 第 8 节。本文件是本批次任务状态的唯一事实来源。

```
批次 UI1：菜单/对话框/面板/按钮/表单/图标/Toast 与网页端一致　集成分支：sit　状态：进行中
│
├─ L1 设计系统基础组件　[并行 ×4｜⟂ 互不依赖]　门禁：sit CI 绿 ✔（run 39417266720）
│  ├─ T43 菜单/弹出层/提示　🔗　← 无　冲突域：component/Menus.kt
│  ├─ T44 对话框/抽屉/卡片　🔗　← 无　冲突域：component/Dialogs.kt、Sheets.kt、Cards.kt
│  ├─ T45 按钮/表单控件　　🔗　← 无　冲突域：component/Buttons.kt、TextFields.kt、Controls.kt
│  └─ T46 标签页/徽章/列表行　🔗　← 无　冲突域：component/Tabs.kt、Badges.kt、Feedback.kt、ListRows.kt
│
├─ L2 页面替换 + 补充组件　[并行 ×8]　门禁：L1 全部 🔗 ✔；L2 门禁 sit CI 绿 ✔（run 39420681087）
│  ├─ 组 A 页面替换（M3 → L1 组件）　⟂ 组内互不依赖（按 feature 目录划分）
│  │  ├─ T47 会话列表/详情　🔗　← 硬：L1　冲突域：feature/sessions/**、feature/sessiondetail/**
│  │  ├─ T48 项目与 Git　　 🔗　← 硬：L1　冲突域：feature/projects/**
│  │  ├─ T49 设置　　　　　 🔗　← 硬：L1　冲突域：feature/settings/**
│  │  └─ T50 其余页面/主框架 🔗　← 硬：L1　冲突域：feature/{worktasks,automations,serverlogs,activity,search,server,onboarding,main}/**、core/update/**
│  │     （组 A 与组 B 的约定：组 A 不换图标、不接 Toast，在需要处留 TODO(T52)/TODO(T53)）
│  └─ 组 B 补充组件　⟂ 组内互不依赖
│     ├─ T51 lucide 图标　🔗　← 硬：无　冲突域：designsystem/icon/**、scripts/gen_lucide_icons.py、scripts/lucide_icons.txt、docs/icon-map.md
│     ├─ T52 Toast　　　　🔗　← 硬：无　⇠ 软：T51（契约：先用 Material 图标，留 TODO(T51): Lucide.Xxx）　冲突域：component/Toasts.kt
│     ├─ T53 杂项组件　　 🔗　← 硬：无　⇠ 软：T51（同上）　冲突域：component/{Collapsible,Avatar,InputGroup,ImagePreview,Command,FolderBadge}.kt
│     └─ T54 顶栏/状态视图 🔗　← 硬：无　⇠ 软：T51（同上）　冲突域：component/TopBar.kt、StateViews.kt（签名不变）
│
├─ L3 设计系统收尾　[并行 ×2｜⟂]　门禁：L2 全部 🔗（T60 需要看到 T47–T50 的真实调用点）
│  ├─ T59 组件预览页(debug) + DS 内部图标→lucide　🔄　← 硬：T43–T46、T51–T54
│  │     冲突域：feature/devgallery/**、MainActivity.kt(debug 入口)、component/{Menus,Sheets,Cards,Tabs,Toasts,Collapsible,Avatar,
│  │             InputGroup,ImagePreview,Command,FolderBadge,TopBar,StateViews}.kt(只改图标)；共享追加点：MainShell 路由表 +1
│  └─ T60 补齐 DS 接口缺口　🔄　← 硬：T43–T54（L2 报告的缺口：TextField enabled/IME/插槽、不确定进度、Spinner、
│        ConfirmDialog busy/content、泛型 Select、FilterChip 统一、ListRow 多行）
│        冲突域：component/{TextFields,Feedback,Dialogs,Controls,Badges,Buttons,ListRows}.kt
│
├─ L4 页面接入：图标 / Toast / T53 组件 / 例外清理　[并行 ×4｜⟂ 按 feature 目录]　门禁：T60 🔗（T59 不阻塞，可同时在跑）
│  ├─ T55 会话　📝　← 硬：T47、T51、T52、T53、T60　冲突域：同 T47
│  ├─ T56 项目　📝　← 硬：T48、T51、T52、T53、T60　冲突域：同 T48
│  ├─ T57 设置　📝　← 硬：T49、T51、T52、T53、T60　冲突域：同 T49
│  └─ T58 其余 + 挂 CodegToasterHost　📝　← 硬：T50、T51、T52、T53、T60　冲突域：同 T50（MainShell 路由表不动）
│
│  关键路径：L2 组 A（T47–T50）→ T60 → T55–T58
│
├─ SIT 集成测试（批次末尾统一测）
│  ├─ S1 编译门禁：每层合入后 sit CI 绿（协调方）
│  ├─ S2 真机测试：按 tests/T43…T59 逐条执行 → tests/RESULTS-UI1.md（明/暗）
│  └─ S3 修复轮次：bugs/Bnn-<区域>.md，按区域并行 fix/Bnn-*（CI 模式）→ 回到 S2 复测
│
└─ 发布：全量回归 → sit 合入 main → 真机自动更新验证
```

## 运行记录

| 任务 | 状态 | codeg run ID | 会话 | 分支 | 备注 |
|---|---|---|---|---|---|
| T43 | 🔗 | 9d9d1563-d31a-4f19-bd98-f76518013018 | 58 | task/T43-ds-menus | CI 模式，分支 CI 通过 |
| T44 | 🔗 | a766b6d2-d0d2-453f-8f58-4abf01a8b46d | 59 | task/T44-ds-dialogs-sheets | 同上 |
| T45 | 🔗 | 6f0f25d1-977b-4a80-b07a-b4bb2f43e366 | 60 | task/T45-ds-buttons-forms | 同上 |
| T46 | 🔗 | 668b57c0-60ab-47bb-a295-f53033ba149f | 61 | task/T46-ds-tabs-badges-rows | 同上；`CodegFilterChip` 新增重载，与 Buttons.kt 旧版共存 |
| T47 | 🔗 | 18abffee-714c-4350-8acd-275088e228a5 | 62 | task/T47-ui-sessions | 测试文档模式 |
| T48 | 🔗 | 881aa9a4-28bb-48ef-afeb-20b7dd40efb0 | 63 | task/T48-ui-projects | 到货编译漏 `Column` import，协调方在原分支修复（3aec76d） |
| T49 | 🔗 | 7d1c94ed-a2b6-47a7-a9cc-53583f36a0a2 | 64 | task/T49-ui-settings | 同上 |
| T50 | 🔗 | 645929a2-439e-4d19-9f48-112779cd18f2 | 65 | task/T50-ui-misc | 同上 |
| T51 | 🔗 | bb215266-1dbe-4a0e-920c-02b7d85b0371 | 66 | task/T51-ds-icons | 同上 |
| T52 | 🔗 | f072fcae-f347-4364-a502-9ad5161833f4 | 67 | task/T52-ds-toasts | 同上 |
| T53 | 🔗 | 788c0647-a1cd-414c-a4c8-51819f9c721e | 68 | task/T53-ds-misc | 同上 |
| T54 | 🔗 | 77981623-a22f-4ca7-99d2-5f8961749644 | 69 | task/T54-ds-topbar-states | 同上 |
| T59 | 🔄 | dd63bb97-f84a-44ec-adc6-ac120bdcac5d | 70 | task/T59-ds-gallery | 测试文档模式 |
| T60 | 🔄 | a6f5dc95-75fd-42bd-88f5-5e7599115b8e | 71 | task/T60-ds-gaps | 同上 |
| T55–T58 | 📝 | | | | T60 合入 sit 后派发 |

## 修复记录

| bug 单 | 区域 | 轮次 | 来源 | 状态 |
|---|---|---|---|---|

## 复盘
- L1：T43–T46 用的是旧的 CI 模式，11 次 verify，平均 4–5 分钟一次。
- L2：T47–T50 报告了 DS 缺口（TextField 无 enabled/IME、无不确定进度、ConfirmDialog 无 busy、Select 只支持字符串），在 L3 增加 T60。教训：L1 组件的需求应当先扫描各页面的真实用法（参数清单）再定签名。
- L1：T45 和 T46 都声明了 `CodegFilterChip`，是重载、编译通过。教训：冲突域应该按"符号"而不只是按文件来划分，第二阶段要统一成一个版本。
