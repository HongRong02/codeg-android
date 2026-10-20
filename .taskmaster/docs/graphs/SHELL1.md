# 批次 SHELL1：纯套壳第一版

> 本文件是本批次状态的唯一事实来源。原生版代码保存在 tag `native-final`（= UI1 的 sit）；UI1 批次停止，不发版。

```
批次 SHELL1：WebView 加载服务端网页，原生只做适配　集成分支：shell　状态：进行中
│
├─ L0 骨架（协调方）　✅　verify run 39476701724
│     MainActivity → ShellActivityHost → ShellApp → WebView；ShellFeature 插槽 ×8
│
├─ L1 功能插槽　[并行 ×8｜⟂ 按包划分]　门禁：shell CI 绿
│  ├─ S1 导航（返回键/外链/新窗口/scheme）　⏳　冲突域：shell/nav/**
│  ├─ S2 显示（insets/键盘/系统栏/深色）　　⏳　冲突域：shell/display/**、themes、colors、configChanges
│  ├─ S3 上传（文件/相册/拍照）　　　　　　 ⏳　冲突域：shell/upload/**
│  ├─ S4 下载（http/blob/data）　　　　　　 ⏳　冲突域：shell/download/**、Manifest 通知权限
│  ├─ S5 服务器（设置/多服务器/迁移/快捷方式）⏳　冲突域：shell/server/**、core/datastore、core/security、di、shortcuts.xml
│  ├─ S6 错误页与恢复　　　　　　　　　　　 ⏳　冲突域：shell/error/**
│  ├─ S7 更新与发版流水线、README　　　　　 ⏳　冲突域：shell/update/**、core/update、.github、scripts、README
│  └─ S8 WebView 设置/Cookie/剪贴板/权限　　⏳　冲突域：shell/web/**、strings.xml、font、raw、Manifest 媒体权限
│
├─ 集成：逐个合入 shell → shell CI → 真机统一测试（tests/S1…S8 合并）→ 第二版任务
└─ 发布：shell 合入 main（待用户确认）
```

## 运行记录

| 任务 | 状态 | codeg run ID | 分支 | 备注 |
|---|---|---|---|---|

## 待用户决定
- 外网地址 rhdemo.online:8443 的 nginx 只反代 `/api/`、`/ws/`，没有反代网页本身；套壳通过外网使用前，需要在那台服务器的 nginx 上加 `/` 的反代（属于服务端运维改动，由用户确认后再做）。局域网 / 本机直连不受影响。
