# 批次 SHELL1：纯套壳第一版

> 本文件是本批次状态的唯一事实来源。原生版代码保存在 tag `native-final`（= UI1 的 sit）；UI1 批次停止，不发版。

```
批次 SHELL1：WebView 加载服务端网页，原生只做适配　集成分支：shell　状态：进行中
│
├─ L0 骨架（协调方）　✅　verify run 39476701724；合入后补 onReceivedSslError 钩子（S6 反馈）
│     MainActivity → ShellActivityHost → ShellApp → WebView；ShellFeature 插槽 ×8
│
├─ L1 功能插槽　[并行 ×8｜⟂ 按包划分]　门禁：shell CI 绿
│  ├─ S1 导航（返回键/外链/新窗口/scheme）　🔗　冲突域：shell/nav/**
│  ├─ S2 显示（insets/键盘/系统栏/深色）　　🔗　冲突域：shell/display/**、themes、colors、configChanges
│  ├─ S3 上传（文件/相册/拍照）　　　　　　 ⏳　冲突域：shell/upload/**
│  ├─ S4 下载（http/blob/data）　　　　　　 ⏳　冲突域：shell/download/**、Manifest 通知权限
│  ├─ S5 服务器（设置/多服务器/迁移/快捷方式）⏳　冲突域：shell/server/**、core/datastore、core/security、di、shortcuts.xml
│  ├─ S6 错误页与恢复　　　　　　　　　　　 ⏳　冲突域：shell/error/**
│  ├─ S7 更新与发版流水线、README　　　　　 ⏳　冲突域：shell/update/**、core/update、.github、scripts、README
│  └─ S8 WebView 设置/Cookie/剪贴板/权限　　🔗　冲突域：shell/web/**、strings.xml、font、raw、Manifest 媒体权限
│
├─ 真机第一轮（WebView 更新到 156 后）：显示/迁移/返回/深色/自动重连/管理页/剪贴板/上传/下载 ✅；键盘挤掉输入框 ❌、服务器挂掉白屏 ❌、原生界面样式简陋 ⚠️
│     未测：外链与特殊 scheme、release 包自动更新、局域网 http 剪贴板
├─ L2 第二版　[并行 ×4]　任务单 .taskmaster/docs/shell/v2/
│  ├─ V1 键盘遮挡输入框　🔄　冲突域：shell/display/**
│  ├─ V2 白屏无错误页 + WebView 版本检查　🔄　冲突域：shell/error/**
│  ├─ V3 服务器页样式　🔄　冲突域：shell/server/**
│  └─ V4 上传菜单/下载提示样式　🔄　冲突域：shell/upload/**、shell/download/**
├─ 集成：逐个合入 shell → shell CI → 真机统一测试（tests/S1…S8 合并）→ 第二版任务
└─ 发布：shell 合入 main（待用户确认）
```

## 运行记录

| 任务 | 状态 | codeg run ID | 会话 | 分支 | 备注 |
|---|---|---|---|---|---|
| S1 | 🔗 | fe45e6f9-791e-4ee3-8206-1a92865c875a | 会话 89 | task/S1-nav | |
| S2 | 🔗 | b54e3551-7de0-48cb-b06b-720eba20a2a6 | 会话 90 | task/S2-display | |
| S3 | 🔗 | 77f9c1f6-da0f-48ad-9c4e-e20e05a6c427 | 会话 91 | task/S3-upload | |
| S4 | 🔗 | acc2dfc6-eff5-44af-a959-6780628a3176 | 会话 92 | task/S4-download | |
| S5 | 🔗 | 3ae60324-a785-42c6-b0ae-4c7c87f82e33 | 会话 93 | task/S5-server | |
| S6 | 🔗 | a1df5a69-2753-43b6-9c19-6f808484ca69 | 会话 94 | task/S6-error | |
| S7 | 🔗 | faaa58f9-6c43-4bc3-a2e3-6e66705e5fb2 | 会话 95 | task/S7-update | |
| S8 | 🔗 | a791b454-ea41-461c-adf9-b611d67896fe | 会话 96 | task/S8-web | |

## 待用户决定
- 外网地址 rhdemo.online:8443 的 nginx 只反代 `/api/`、`/ws/`，没有反代网页本身；套壳通过外网使用前，需要在那台服务器的 nginx 上加 `/` 的反代（属于服务端运维改动，由用户确认后再做）。局域网 / 本机直连不受影响。
