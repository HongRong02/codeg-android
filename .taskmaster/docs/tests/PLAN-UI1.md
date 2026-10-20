# UI1 单机合并测试计划（T61）

状态：待协调方统一编译与真机执行；本文是执行计划，不是通过记录。仅依据 T47–T60 的 14 份测试文档，未读代码或网页源码。WEB = `/mnt/e/claude/codeg/src/`，下表路径沿用源测试文档，视觉对照时由协调方打开对应网页。

## 前置条件与数据

- 设备：一台 vivo V1824A / Android 11；协调方统一构建包含 T47–T60 及 T58 Toast Host 的 debug APK，另备 release APK 验证隔离；任务方不操作设备。
- 测试服 **T13Test**；另备官方 codeg 0.38 / 缺可选接口的服务用于兼容验证。受控失败或断网制造错误，不修改服务端。所有删除、卸载、reset、rebase 等仅操作预先准备的可恢复测试对象。
- 两台服务器（长名/有效无效 URL/token），一个无服务器的首次配置环境；同名不同 ID/路径项目、分组、代理、提供商/安装方案；置顶/运行中/普通会话，0/1/3 标签、长标题、全局/项目标签、单多选/Other/权限/计划审批、流式工具/思考/图片/附件数据。
- 可丢弃 Git 仓库：tracked/untracked/renamed/deleted 文件，文本/图片/二进制，本地/远程/当前/占用/带slash分支，认证远端，stash、merge/rebase 冲突、soft/mixed/hard reset 目标、remote/worktree；Forge Issue/PR/多标签/评论/各merge method；可导入 new/imported/deleted/unavailable/逐条错误会话。
- 设置数据：全部列明代理及不可用代理；测试 Pi 可安装卸载；同名提供商、MCP、专家、可写/只读/项目/全局技能与关联冲突、自定义库、字母中文/ZWJ/肤色emoji助手、Telegram/飞书/微信频道、扫码账号、两个webhook；工作任务四列、模板/继承设置、自动化及运行记录、日志/统计数据。
- Gallery 无需服务器；现有样例不足的 API 参数、缺宿主、TalkBack计数等由协调方在已有同模块验证宿主/Preview 展示，不要求本任务新增代码。准备两组命令、禁用项、100条及200字标签；横/纵/1px图，12000×8000来源图须先降采样再传Painter。

## 执行顺序、规则与记录

按表顺序就地完成“功能→明暗视觉→边界”，从首次引导/服务器→主框架→会话列表/详情→项目/Git→设置叶页面→工作任务→自动化→日志/动态/搜索→更新→Gallery各分组。每页仅切一次暗色拍照，再恢复功能主题；P2全入口矩阵随访页勾选，不要求最后再来回跳转。Git破坏性场景使用独立预置仓库快照；不要在一次reset后继续依赖已销毁数据。

功能默认只在明色执行一次；外观/语言切换、Gallery局部主题恢复属于其自身功能步骤。P1明暗视觉两主题分别真实截图，不以同一截图推断另一主题。中英文至少分别走一遍受影响页面/表单与全部Toast路径；此为语言覆盖，不重复危险写操作。视觉检查展开本页每个子页面及弹层，每页每主题至少一张，长页分屏截图。

所有页面就地核对顶栏返回/关闭/自定义标题与可用/禁用图标：按下accent、禁用不响应；限速加载、空数据/无匹配、断网恢复重试仅在页面实际存在的状态执行，加载mutedForeground、错误destructive边框文字、空态已有操作与分隔线不可重叠，默认/自定义重试文案正确。此规则承接T54逐文件用例，不是仅看截图。

所有危险动作一律先取消（含遮罩/系统返回）确认服务端未变，再确认检查目标（名称/ID/路径/分支→提交/数量）；busy连点不重复。所有Toast路径按表内枚举逐个触发，并连续重复相同结果至少两次：每次完成一条、消费后重组/重入不重播，不能因缺Host无提示而判通过。区分一次操作的loading→update与重复新增，持续错误/认证hint不得被消费。

记录格式：`P编号 / 子页面或数据变体 / APK hash / 服务版本 / 主题语言 / 结果(待测、通过、失败、阻塞、不适用及原因) / App与WEB截图链接 / 实际请求目标及次数 / 缺陷编号`。共用矩阵对每个适用页面分别记录，不以一次全局勾选代替页面覆盖。无对应状态的页面标不适用并说明，无法进入的可选功能标阻塞或能力隐藏结果，不能记作功能通过。

实际原始用例 **418** 条（按表中编号去重，非任务描述估算数）；合并后 **197** 条：**P0 84 / P1 110 / P2 3**。P1含26个逐图标明暗对照；一条合并用例可能含多个必须分别记录的动作/子页面，不代表只点一次。

## 合并用例

| 编号 | 优先级 | 入口 | 步骤 | 预期 | 主题 | 来源用例编号列表 |
|---|---|---|---|---|---|---|
| P-001 | P0 | 首次启动 / 服务器列表 | 无配置冷启动→添加并返回；准备两个长名服务器，切换、更多编辑；删除先取消再确认，最后删除末个测试服务器 | 首次引导完整可滚动，56dp 主图/16dp 功能图标；更多不误切换；确认显示服务器名，取消不写入；选择、持久化和最后一个删除后的入口正确 | 明（主题/语言功能按步骤切换） | T50-25、T50-26、T50-29、T50-30、T58-21、T58-22、T54-39 |
| P-002 | P1 | 首次启动 / 服务器列表 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/app-title-bar.tsx`、`WEB/components/layout/status-bar-computer.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-25、T50-26、T50-29、T50-30、T58-21、T58-22、T54-39 |
| P-003 | P0 | 服务器编辑 / 连接反馈 | 分别从首次配置及主框架新增/编辑；填名称、URL、token，测试有效/无效连接并连续重复；开键盘保存后重开 | token 遮挡；success 含版本，error 为原错误资源，每次完成仅一条 Toast；无临时 Text；重组不重播，保存持久化，首次配置也有可见宿主 | 明（主题/语言功能按步骤切换） | T50-27、T50-28、T58-23、T58-24、T54-38 |
| P-004 | P1 | 服务器编辑 / 连接反馈 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/status-bar-computer.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-27、T50-28、T58-23、T58-24、T54-38 |
| P-005 | P0 | 主框架 / 五个导航与 Toast 宿主 | 切五个导航和深链，系统返回；显示 Toast 后滚动折叠/恢复底栏，开关键盘，打开 Dialog/Sheet 并从弹层触发提示，再滑动关闭 | 返回栈、各页状态恢复保持；选中 Secondary/未选中 Ghost；Lucide 与≥48dp触摸目标；Toast 不改变页面高度，位于底栏/系统栏/键盘上且弹层触发可见；无静默实例、重复宿主 | 明（主题/语言功能按步骤切换） | T50-31、T50-32、T58-25、T58-26 |
| P-006 | P1 | 主框架 / 五个导航与 Toast 宿主 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/app-title-bar.tsx`、`WEB/components/layout/status-bar.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-31、T50-32、T58-25、T58-26 |
| P-007 | P0 | 会话列表 / 分区与菜单 | 滚动置顶/项目/未分组，折叠展开；切服务器、进入管理；正文开会话，长按/更多置顶取消、分配标签；点外部/返回关菜单 | 分区计数/折叠状态和选中项准确，副标题/标签/live 可辨；嵌套按钮不串触；单次触发且菜单锚点正确；品牌/单色头像、16dp Lucide；父 LazyColumn 保持虚拟化 | 明（主题/语言功能按步骤切换） | T47-01、T47-02、T47-03、T47-04、T55-01、T54-43 |
| P-008 | P1 | 会话列表 / 分区与菜单 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/conversations/sidebar-conversation-list.tsx`、`WEB/components/conversations/sidebar-section-header.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-01、T47-02、T47-03、T47-04、T55-01、T54-43 |
| P-009 | P0 | 会话列表 / 错误反馈 | 首次断网加载；恢复后再次断网刷新并置顶失败；重组、重入，再恢复网络刷新 | 首次保留 InlineError；已有数据保留；刷新、操作每次一条 error Toast，消费后不重播；FAB/重试/关闭可用 | 明（主题/语言功能按步骤切换） | T55-02 |
| P-010 | P1 | 会话列表 / 错误反馈 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/conversations/sidebar-conversation-list.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T55-02 |
| P-011 | P0 | 标签管理 / 分配筛选 | 新建/编辑：空名、错误颜色、有效值；分配两标签，切匹配全部/任一并清空；删除先取消后确认；每种写操作断网后恢复重试，busy 时输入/连点/关闭 | 全局/项目/已分配作用域正确，失效标签剔除；保存 footer 固定；确认显示标签名；busy 输入视觉禁用，确认不重复且不能关闭；失败每次一条 error，面板草稿保留 | 明（主题/语言功能按步骤切换） | T47-05、T47-06、T55-03、T55-04 |
| P-012 | P1 | 标签管理 / 分配筛选 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/conversations/conversation-tag-picker.tsx`、`WEB/components/conversations/conversation-tag-list-editor.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-05、T47-06、T55-03、T55-04 |
| P-013 | P0 | 会话详情 / 顶部动作 | 重命名空白/长名/取消/保存，切状态/置顶并查看元数据；删除先取消后确认；断网重试各动作；支持时 fork/断开，重组重入 | 空白不保存、取消无请求；字段/头像/时间正确；危险框准确会话名、成功回列表；失败 notice 每次一条 error；断开 success 一条；不支持隐藏，运行中锁定 | 明（主题/语言功能按步骤切换） | T47-07、T47-08、T55-05、T55-06、T54-41 |
| P-014 | P1 | 会话详情 / 顶部动作 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/conversations/conversation-detail-header.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-07、T47-08、T55-05、T55-06、T54-41 |
| P-015 | P0 | 草稿代理选项 / 项目与配置 | 切品牌代理，选同名不同路径项目；切 mode/config 并制造应用失败后重试，运行中尝试改选 | 原实体/路径正确；乐观选中失败回退、锁释放，每次失败 error 一条；品牌可辨，单色保留 tint；全屏选项可滚动 | 明（主题/语言功能按步骤切换） | T47-09、T55-08 |
| P-016 | P1 | 草稿代理选项 / 项目与配置 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/chat/conversation-context-bar.tsx`、`WEB/components/chat/model-option-list.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-09、T55-08 |
| P-017 | P0 | 已有会话 / 分支选择 | 核对项目只读；搜索 local/remote/带 slash 分支，点当前/占用/可用项；连点与 Enter；新建/切换失败重试；返回和关闭逐级测试 | ID/checkout 目标正确；当前、占用、busy 不可选；原地切换/工作树跳转保持；失败 error 一条；子页先回主选项再关闭 | 明（主题/语言功能按步骤切换） | T47-10、T55-09 |
| P-018 | P1 | 已有会话 / 分支选择 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/chat/conversation-context-bar.tsx`、`WEB/components/chat/model-option-list.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-10、T55-09 |
| P-019 | P0 | 输入栏 / 三类插入 | 快捷消息/专家/命令各搜索名称描述；空草稿、有文本分别选择同名不同描述项；无结果清空，键盘下滚到底，取消 | 搜索与描述可区分；追加、已知专家前缀替换（含 Codex/其他代理）、命令尾空格保持；选后关闭，取消不改草稿；有限高度，末项可达 | 明（主题/语言功能按步骤切换） | T47-11、T47-12、T55-10 |
| P-020 | P1 | 输入栏 / 三类插入 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/chat/composer/composer-add-menu.tsx`、`WEB/components/chat/message-input.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-11、T47-12、T55-10 |
| P-021 | P0 | 问题卡片 | 触发单选、多选、多问题；点标签/说明/复选框；Other 空/有效文本，空提交/完整提交/跳过；提交中连点及响应失败后恢复 | 每次只切一次，单选互斥并保留取消行为；全部回答才可提交；答案标签、Other 映射不变且只发送一次；失败恢复待答，Spinner 可见 | 明（主题/语言功能按步骤切换） | T47-13、T47-14、T55-14 |
| P-022 | P1 | 问题卡片 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/chat/question-dialog.tsx`、`WEB/components/message/ask-question-result-card.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-13、T47-14、T55-14 |
| P-023 | P0 | 权限卡片 | 触发命令/cwd、diff、计划、URL、原始 JSON 权限；逐次新请求测试拒绝/单次/持续允许；提交中连点并模拟响应失败 | 结构化内容/嵌套 Markdown 不丢，optionId 原样发送；一次请求一次响应，busy 阻止重复；失败提示可见且可恢复 | 明（主题/语言功能按步骤切换） | T47-15、T47-16、T55-14 |
| P-024 | P1 | 权限卡片 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/chat/permission-dialog.tsx`、`WEB/components/message/tool-call-block.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-15、T47-16、T55-14 |
| P-025 | P0 | 计划审批 / 备注回退 | 空/长计划分别请求修改，空白/有效备注；新请求分别批准、放弃；busy 输入/连点；触发不支持备注协议回退 | 空备注不提交，decision 和 trim 不变；busy 输入禁用，一次响应；备注丢失 warning 一条，审批仍按原协议发送；失败不丢待审批 | 明（主题/语言功能按步骤切换） | T47-17、T47-18、T55-07、T55-14 |
| P-026 | P1 | 计划审批 / 备注回退 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/chat/plan-approval-card.tsx`、`WEB/components/message/plan-mode-card.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-17、T47-18、T55-07、T55-14 |
| P-027 | P0 | 会话详情 / 工具思考与消息输入 | 展开工具/工具组/思考与无正文工具，流式更新；查看表格/图表/时间线；多行输入、附件增删、发送/停止、复制、跳底；发送及三类响应制造失败后重入 | 稳定 ID 展开态不重置，箭头不重复，无正文不展开；分隔 border；草稿/流式消息不丢，多行/附件保持；操作≥48dp；每次失败一条 error，无重复发送 | 明（主题/语言功能按步骤切换） | T47-19、T47-20、T55-11、T55-13、T54-42 |
| P-028 | P1 | 会话详情 / 工具思考与消息输入 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/message/agent-tool-call.tsx`、`WEB/components/message/content-parts-renderer.tsx`、`WEB/components/chat/message-input.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T47-19、T47-20、T55-11、T55-13、T54-42 |
| P-029 | P0 | 会话详情 / 图片预览 | 点有效内联图片，缩放平移，关闭重开；另测损坏 base64 | 共享预览可返回、caption 保留；坏图占位不崩溃；保持原消息滚动位置 | 明（主题/语言功能按步骤切换） | T55-12 |
| P-030 | P1 | 会话详情 / 图片预览 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/message/image-actions.tsx`、`WEB/components/ui/image-preview-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T55-12 |
| P-031 | P0 | 会话详情 / 持续连接状态 | 断网重连和鉴权失败，点击服务器管理动作，返回重入 | streamStatus 持续提示与动作保留，不被 Toast 消费；恢复后状态正确 | 明（主题/语言功能按步骤切换） | T55-15 |
| P-032 | P1 | 会话详情 / 持续连接状态 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/chat/session-failure-banner.tsx`、`WEB/components/chat/session-config-stale-banner.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T55-15 |
| P-033 | P0 | 项目列表 / 分组 | 创建重命名分组，编辑项目别名/颜色，移动同名不同 ID 分组及未分组；折叠展开、点嵌套按钮；删除先取消后确认；首次加载失败/有数据刷新失败后重试 | ID 不混、折叠不丢项目、嵌套按钮不串触；徽标正确；确认显示组名，busy 不重复；首次 InlineError、有数据刷新 error Toast 一条 | 明（主题/语言功能按步骤切换） | T48-01L、T48-01D、T56-01L、T56-01D、T54-32 |
| P-034 | P1 | 项目列表 / 分组 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/folder-title-bar.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-01L、T48-01D、T56-01L、T56-01D、T54-32 |
| P-035 | P0 | 目录浏览 | Home/子目录/上级；选当前目录，关闭重开，慢网加载 | 路径/选择结果不变；16dp 图标/24dp Spinner；顶部选择和关闭可达，无无界测量 | 明（主题/语言功能按步骤切换） | T48-02L、T48-02D、T56-02L、T56-02D、T54-25 |
| P-036 | P1 | 目录浏览 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/aux-panel-file-tree-tab.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-02L、T48-02D、T56-02L、T56-02D、T54-25 |
| P-037 | P0 | 克隆 | HTTPS URL、父目录、可选凭据克隆；错误 URL/认证失败重复两次再修正；busy 拖动/返回 | 路径推导/canClone 保持，密码遮挡；busy 不关闭不重复；成功 success、失败 error 每次一条，成功刷新 | 明（主题/语言功能按步骤切换） | T48-03L、T48-03D、T56-03L、T56-03D、T54-23 |
| P-038 | P1 | 克隆 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/folder-title-bar.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-03L、T48-03D、T56-03L、T56-03D、T54-23 |
| P-039 | P0 | 项目详情 / 文件 | 切文件/变更/提交，进出目录，预览文本/图片/二进制；图片缩放拖动关后重开，含历史图片 | tabs 内容正确、返回列表正确；共享图片预览可关闭重开，文本/不支持提示保留 | 明（主题/语言功能按步骤切换） | T48-04L、T48-04D、T56-04L、T56-04D、T54-28、T54-31 |
| P-040 | P1 | 项目详情 / 文件 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/aux-panel-file-tree-tab.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-04L、T48-04D、T56-04L、T56-04D、T54-28、T54-31 |
| P-041 | P0 | 项目分支切换 | 检查本地/远程/当前分支；切换、新建；失败重复后重试 | 当前标记且不执行、busy 不重复；成功 success 关闭刷新，失败 error 留表单 | 明（主题/语言功能按步骤切换） | T48-05L、T48-05D、T56-05L、T56-05D、T54-22 |
| P-042 | P1 | 项目分支切换 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/branch-dropdown.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-05L、T48-05D、T56-05L、T56-05D、T54-22 |
| P-043 | P0 | Git 变更 / 危险操作 | 看 diff 和 renamed 来源；长按暂存、丢弃 tracked、删除 untracked，每个确认先取消后确认；失败再重试 | 动作按状态显示；危险框完整文件路径；取消无请求，确认后刷新；loading 结束按成功/失败更新 Toast，无重复 | 明（主题/语言功能按步骤切换） | T48-06L、T48-06D、T56-06L、T56-06D、T54-26 |
| P-044 | P1 | Git 变更 / 危险操作 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/aux-panel-git-changes-tab.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-06L、T48-06D、T56-06L、T56-06D、T54-26 |
| P-045 | P0 | Git 提交 | 检查 tracked 默认选/untracked 不选，点行/checkbox、全选/清空；空/多行消息、无文件、busy；分别提交与提交并推送，失败重试 | 单击只切一次、分区计数正确；空消息/无选择/busy 禁提交；只提交选中文件；先成功提交才推送/认证；提交 success/error、后续推送失败独立反馈 | 明（主题/语言功能按步骤切换） | T48-07L、T48-07D、T56-07L、T56-07D、T54-24 |
| P-046 | P1 | Git 提交 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/commit-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-07L、T48-07D、T56-07L、T56-07D、T54-24 |
| P-047 | P0 | Git 历史 / 同步 / 状态条 | fetch/pull/push 成功失败及远端不可用；慢网切 tab/退出；历史复制 hash、查看有/无 diff、deleted 文件 | loading 原 ID 更新 success/error/warning 并退出清理；CloudCheck/CloudOff 与计数正确、busy 禁重复；作者时间增删正确，deleted 不开无效预览 | 明（主题/语言功能按步骤切换） | T48-08L、T48-08D、T56-08L、T56-08D、T54-27、T54-30、T54-34 |
| P-048 | P1 | Git 历史 / 同步 / 状态条 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/aux-panel-git-log-tab.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-08L、T48-08D、T56-08L、T56-08D、T54-27、T54-30、T54-34 |
| P-049 | P0 | Git 凭据 | 普通主机用户名密码及保存开关；GitHub 无效 token 重复两次再有效；生成链接；取消再触发 | 密码遮挡无泄漏，验证不重复；invalid token 每次 error，retry hint 持续；取消释放等待，新提示无旧值，生成链接正确 | 明（主题/语言功能按步骤切换） | T48-09L、T48-09D、T56-09L、T56-09D、T54-29 |
| P-050 | P1 | Git 凭据 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/branch-dropdown.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-09L、T48-09D、T56-09L、T56-09D、T54-29 |
| P-051 | P0 | Git 分支管理 | 刷新更新比较，折叠展开；认证失败重试；本地删除 force 勾选/不勾选分别走收集参数和最终确认；远程删除先取消再确认 | 本地远程/tracking 正确；最终确认显示分支，force 原值不变，busy 阻止；success/error 一次；不支持隐藏，比较内容保留 | 明（主题/语言功能按步骤切换） | T48-10L、T48-10D、T56-10L、T56-10D、T54-33 |
| P-052 | P1 | Git 分支管理 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/branch-dropdown.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-10L、T48-10D、T56-10L、T56-10D、T54-33 |
| P-053 | P0 | Git Stash | message、keep-index 保存；刷新/diff 折叠；apply/pop/drop/clear 每种成功失败，删除类先取消再确认 | 确认 ref 或清空数量明确，取消不变；参数保持，diff 不截断，默认展开正确；success/error 对应结果 | 明（主题/语言功能按步骤切换） | T48-11L、T48-11D、T56-11L、T56-11D、T54-36 |
| P-054 | P1 | Git Stash / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/stash-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-11L、T48-11D、T56-11L、T56-11D、T54-36 |
| P-055 | P0 | Git Merge / Rebase / Reset | merge/rebase 选目标；reset 输入提交并切 soft/mixed/hard，切操作检查目标清空；hard 错/对当前分支名，两步确认先取消再执行；各成功失败 | 排除当前分支；错误名不能继续；危险确认写当前分支→提交和模式；取消无副作用，busy 无重复；success/error 正确 | 明（主题/语言功能按步骤切换） | T48-12L、T48-12D、T56-12L、T56-12D |
| P-056 | P1 | Git Merge / Rebase / Reset / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/aux-panel-git-log-tab.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-12L、T48-12D、T56-12L、T56-12D |
| P-057 | P0 | Git 冲突 | 搜索文件、清空及无结果；切 base/ours/theirs；resolve 先取消后确认；未解决尝试 continue，全部解决后 continue；另造冲突 abort；失败重试 | 原版本正确，确认显示文件/分支；busy 搜索禁用；仅无冲突可 continue；空列表已解决，无匹配本地化提示；success/error 正确 | 明（主题/语言功能按步骤切换） | T48-13L、T48-13D、T56-13L、T56-13D |
| P-058 | P1 | Git 冲突 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/conflict-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-13L、T48-13D、T56-13L、T56-13D |
| P-059 | P0 | Git Remote / Worktree | 加载刷新；新增 remote、编辑 URL、fetch/认证；新增 worktree（base 可空）；删除分别先取消后确认，制造失败 | 名称/URL 校验保持；确认 remote 名、worktree 分支路径；成功刷新/凭据重试；success/error 对应（load/refresh 既有 success 语义保留）；unknown 隐藏 | 明（主题/语言功能按步骤切换） | T48-14L、T48-14D、T56-14L、T56-14D、T54-35 |
| P-060 | P1 | Git Remote / Worktree / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/remote-manage-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-14L、T48-14D、T56-14L、T56-14D、T54-35 |
| P-061 | P0 | Forge 列表 / 新建 | Issues/PRs、open/closed、分页刷新；标题/多行正文/多标签新建；busy 尝试编辑，失败后重试 | 筛选计数正确，空态保持；不确定 Progress；字段真禁用、正文3～8行；失败 error，持续错误/token hint 保留；成功关闭刷新 | 明（主题/语言功能按步骤切换） | T48-15L、T48-15D、T56-15L、T56-15D |
| P-062 | P1 | Forge 列表 / 新建 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/forge/forge-new-issue-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-15L、T48-15D、T56-15L、T56-15D |
| P-063 | P0 | Forge 详情 | 正文/labels/状态/patch/评论与分页；发评论、生成任务；关闭重开和 merge 各 method 先取消再确认，成功失败及 busy | 确认 #number title 和 method；草稿/不可合并/busy 约束保持；评论禁用最多8行；失败 error；原成功状态刷新，长内容可滚动 | 明（主题/语言功能按步骤切换） | T48-16L、T48-16D、T56-16L、T56-16D |
| P-064 | P1 | Forge 详情 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/forge/forge-issue-detail-sheet.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-16L、T48-16D、T56-16L、T56-16D |
| P-065 | P0 | 导入会话 | 扫描 new/imported/deleted/unavailable/错误记录；文件夹/agent 分组选择、行/checkbox/全选；导入所选及全量先取消后确认；网络/部分记录失败 | 仅 new 可选，一次切换；确认总数；busy Progress、不能改选择或关闭；网络 error Toast，分类计数和逐条错误持续，完成刷新 | 明（主题/语言功能按步骤切换） | T48-17L、T48-17D、T56-17L、T56-17D |
| P-066 | P1 | 导入会话 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/import-sessions/import-sessions-window.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T48-17L、T48-17D、T56-17L、T56-17D |
| P-067 | P0 | 设置 → 设置首页 | 打开全部叶页面再返回，切缺助手/MCP市场能力的服务器 | 分组/首尾行正确，16dp Lucide 语义正确；既有能力隐藏规则、返回保持 | 明（主题/语言功能按步骤切换） | T49-01、T49-02、T57-01、T57-02、T54-53 |
| P-068 | P1 | 设置 → 设置首页 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/settings-shell.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-01、T49-02、T57-01、T57-02、T54-53 |
| P-069 | P0 | 设置 → 外观 / 语言 | 跟随系统/浅深主题、逐个主题色；English/中文/跟随系统，Activity 重建重开 | 色样选择环、预览同步；主题语言持久化，资源和返回正确 | 明（主题/语言功能按步骤切换） | T49-03、T49-04、T49-05、T49-06、T57-03、T57-04 |
| P-070 | P1 | 设置 → 外观 / 语言 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/appearance-settings.tsx`、`WEB/app/settings/general/page.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-03、T49-04、T49-05、T49-06、T57-03、T57-04 |
| P-071 | P0 | 设置 → 常规 | 切实时反馈/提问/委派；启用后增减深度直到上下界再关闭 | 子项显隐、边界保持；Plus/Minus 16dp、按钮≥48dp | 明（主题/语言功能按步骤切换） | T49-07、T49-08、T57-05、T57-06 |
| P-072 | P1 | 设置 → 常规 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/general-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-07、T49-08、T57-05、T57-06 |
| P-073 | P0 | 设置 → 代理列表 / 通用详情 | 正文开详情与开关独立点击，检查不可用代理；Claude/Codex/Gemini/Cline/OpenCode/Hermes/Grok/Cursor 逐一切模式提供商模型保存；折叠普通/高级并输入非法 raw | 可用/版本和 live provider 关联保持；不可用禁用；折叠无重复箭头、标题48dp；各代理字段条件/草稿/持续校验和保存隔离保持 | 明（主题/语言功能按步骤切换） | T49-09、T49-10、T49-11、T49-12、T57-07、T57-08、T54-44、T54-45 |
| P-074 | P1 | 设置 → 代理列表 / 通用详情 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/acp-agent-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-09、T49-10、T49-11、T49-12、T57-07、T57-08、T54-44、T54-45 |
| P-075 | P0 | 设置 → 代理选择器 / Hermes | 同名不同 ID 提供商分别选保存重开，含 nullable Int/String/枚举；Hermes API key/OAuth/AWS 跨类别 | 只标记选中 ID，描述可辨且实体回写正确；分类标题、认证字段和校验保持 | 明（主题/语言功能按步骤切换） | T49-13、T49-14、T57-09、T57-10 |
| P-076 | P1 | 设置 → 代理选择器 / Hermes / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/acp-agent-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-13、T49-14、T57-09、T57-10 |
| P-077 | P0 | 设置 → Cursor 权限 / raw 配置 | 新增两规则，编辑删一条；展开 raw 多行编辑保存，刷新重开 | 删草稿不立即写服务端，raw/结构化补丁优先级和刷新语义保持 | 明（主题/语言功能按步骤切换） | T49-15、T49-16 |
| P-078 | P1 | 设置 → Cursor 权限 / raw 配置 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/cursor-config-panel.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-15、T49-16 |
| P-079 | P0 | 设置 → 代理服务器管理面板 | 分别打开 Antigravity、Command Code、DeepSeek、Kiro、Qoder、Trae、ZCode | 共享 FormSection，原只读说明、支持提示和按钮保持 | 明（主题/语言功能按步骤切换） | T49-17、T49-18 |
| P-080 | P1 | 设置 → 代理服务器管理面板 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/acp-agent-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-17、T49-18 |
| P-081 | P0 | 设置 → Kimi 配置 / 保存与模型 | 切登录/API、接口区域认证，raw 编辑；缺 URL/key 获取模型、非空/空响应/请求失败；API key缺模型保存，再合法成功及失败 | wire 值/字段显隐/忙态保持；保存 success/异常 error/模型必填 error；缺凭据 error、模型数量或空结果 success、请求失败 error 各一次；草稿保留 | 明（主题/语言功能按步骤切换） | T49-19、T49-20、T57-11、T57-12、T57-13、T57-14 |
| P-082 | P1 | 设置 → Kimi 配置 / 保存与模型 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/kimi-code-config-panel.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-19、T49-20、T57-11、T57-12、T57-13、T57-14 |
| P-083 | P0 | 设置 → Pi 运行时 / 配置 / 信任 | 默认/自定义运行时保存成功失败；缺 provider/model、缺 base URL 保存，再合法成功失败；信任切换失败，命令存在/不存在校验并重新检测 | runtimeSaved success/异常 error；两类配置前置校验 error、保存 success/error 可重复；信任失败 error；命令持续结果/环境变量和字段条件保持，原横幅消费后不重播 | 明（主题/语言功能按步骤切换） | T49-21、T49-22、T57-15、T57-16、T57-17、T57-18、T57-21、T57-22 |
| P-084 | P1 | 设置 → Pi 运行时 / 配置 / 信任 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/pi-config-panel.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-21、T49-22、T57-15、T57-16、T57-17、T57-18、T57-21、T57-22 |
| P-085 | P0 | 设置 → Pi 安装 / 卸载 | 安装成功/失败；卸载先取消再确认成功/失败，busy 连点 | 明确 Pi 目标；取消无请求，确认只执行一次；安装卸载 success/error，Spinner/禁用正常 | 明（主题/语言功能按步骤切换） | T49-23、T49-24、T57-19、T57-20 |
| P-086 | P1 | 设置 → Pi 安装 / 卸载 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/pi-config-panel.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-23、T49-24、T57-19、T57-20 |
| P-087 | P0 | 设置 → 快捷消息 | 空草稿校验，填标题多行保存重开编辑；更多/长按删除先取消再确认 | 空字段不能保存，键盘可达；两个删除入口一致，确认标题正确；取消保留，确认仅删目标 | 明（主题/语言功能按步骤切换） | T49-25、T49-26、T49-27、T49-28、T57-23、T57-24、T54-52 |
| P-088 | P1 | 设置 → 快捷消息 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/quick-messages-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-25、T49-26、T49-27、T49-28、T57-23、T57-24、T54-52 |
| P-089 | P0 | 设置 → 模型提供商 | 新建名称URL密钥代理和可选模型；编辑留空密钥；选同名不同ID分别编辑删除，先取消再确认 | 名称URL必填，留空密钥语义保持；原代理/ID 正确，目标名称可见且不误删 | 明（主题/语言功能按步骤切换） | T49-29、T49-30、T49-31、T49-32、T57-25、T57-26、T54-51 |
| P-090 | P1 | 设置 → 模型提供商 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/model-provider-settings.tsx`、`WEB/components/settings/add-model-provider-dialog.tsx`、`WEB/components/settings/edit-model-provider-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-29、T49-30、T49-31、T49-32、T57-25、T57-26、T54-51 |
| P-091 | P0 | 设置 → MCP 本地 | 新建 ID、JSON、多代理；无效JSON保存再修正；切代理，删除先取消再确认 | spec/apps 不变，错误保留；全屏滚动键盘正常，确认 ID，取消无请求 | 明（主题/语言功能按步骤切换） | T49-33、T49-34、T57-29、T57-30、T54-50 |
| P-092 | P1 | 设置 → MCP 本地 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/mcp-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-33、T49-34、T57-29、T57-30、T54-50 |
| P-093 | P0 | 设置 → MCP 市场 | 切来源搜索、详情返回分页；选同名不同 ID 安装方案；填必填/密钥/枚举/布尔/JSON、代理；安装先取消再确认，busy 尝试查询关闭 | 有限高度列表内外滚动正常；选项按 ID、参数类型不变；确认服务器名，busy 禁查询/关闭；成功刷新本地列表 | 明（主题/语言功能按步骤切换） | T49-35、T49-36、T49-37、T49-38、T57-31、T57-32、T54-56 |
| P-094 | P1 | 设置 → MCP 市场 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/mcp-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-35、T49-36、T49-37、T49-38、T57-31、T57-32、T54-56 |
| P-095 | P0 | 设置 → 专家 | 加载失败重试；开详情滚动Markdown；切关联代理，检查外链/真实目录/损坏状态 | 阻塞状态禁开关、持续错误可重试；关联取消与预览保持，Spinner 正确 | 明（主题/语言功能按步骤切换） | T49-39、T49-40、T57-33、T57-34、T54-49 |
| P-096 | P1 | 设置 → 专家 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/experts-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-39、T49-40、T57-33、T57-34、T54-49 |
| P-097 | P0 | 设置 → 技能 | 代理/项目/全局筛选；开目录/单文件/只读；增改可写技能；长按/更多删除先取消再确认 | 计数/FilterChip/作用域正确，目录文件图标区分；只读标记无删除，确认 ID | 明（主题/语言功能按步骤切换） | T49-41、T49-42、T57-35、T57-36、T54-54 |
| P-098 | P1 | 设置 → 技能 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/skills-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-41、T49-42、T57-35、T57-36、T54-54 |
| P-099 | P0 | 设置 → 自定义技能库 | 创建编辑复制，失败重试；删除先取消再确认；切导入来源、勾多项导入，逐代理关联，检查冲突/只读 | ID正文校验、复制保持；切来源清选择，部分失败计数保持；冲突不覆盖，确认 ID；有限列表滚动 | 明（主题/语言功能按步骤切换） | T49-43、T49-44、T49-45、T49-46、T57-37、T57-38、T54-55 |
| P-100 | P1 | 设置 → 自定义技能库 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/custom-skills-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-43、T49-44、T49-45、T49-46、T57-37、T57-38、T54-55 |
| P-101 | P0 | 设置 → 助手列表 / 编辑 | 字母/中文/普通emoji/ZWJ及肤色emoji头像；名称描述、代理模式选项指令创建编辑；probing/busy输入返回；排序边界；删除先取消再确认 | 完整emoji不截断、普通共享头像；长度校验、原快照、探测重试保持；真禁用输入/关闭，目标助手名，48dp按钮 | 明（主题/语言功能按步骤切换） | T49-47、T49-48、T57-27、T57-28、T54-46 |
| P-102 | P1 | 设置 → 助手列表 / 编辑 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/assistants-panel.tsx`、`WEB/components/settings/assistant-editor-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-47、T49-48、T57-27、T57-28、T54-46 |
| P-103 | P0 | 设置 → 频道列表 / 编辑 | 新增Telegram/飞书/微信，结构化/raw切换；编辑启用日报；删除先取消后确认，成功失败 | 字段校验/raw转换保持，开关不串触；三类头像；目标频道名，success/error正确，失败恢复列表，无旧Snackbar/错误Dialog | 明（主题/语言功能按步骤切换） | T49-49、T49-50、T57-39、T57-40、T54-47 |
| P-104 | P1 | 设置 → 频道列表 / 编辑 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/chat-channel-settings.tsx`、`WEB/components/settings/add-chat-channel-dialog.tsx`、`WEB/components/settings/edit-chat-channel-dialog.tsx`、`WEB/components/settings/channel-list-tab.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-49、T49-50、T57-39、T57-40、T54-47 |
| P-105 | P0 | 设置 → 频道详情 / 连接与令牌 | 查看详情各区；连接断开成功失败，观察真实状态；已连接发送测试成功失败；移除令牌先取消再确认成功失败 | 连接请求/断开/测试/令牌 success/error分别正确，资源反馈一次；真实状态由轮询更新；所属频道确认明确，取消无请求 | 明（主题/语言功能按步骤切换） | T49-51、T49-52、T57-41、T57-42、T57-43、T57-44 |
| P-106 | P1 | 设置 → 频道详情 / 连接与令牌 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/channel-list-tab.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-51、T49-52、T57-41、T57-42、T57-43、T57-44 |
| P-107 | P0 | 设置 → 微信二维码 / 预览 | 加载扫码/失败/过期刷新；点图缩放返回继续扫码；确认连接自动关闭，返回重开 | 白底居中，预览在Sheet上方；返回只关预览不重启轮询；过期自动关预览并可刷新，成功关整面板；关闭生命周期无残留 | 明（主题/语言功能按步骤切换） | T49-53、T49-54、T57-45、T57-46 |
| P-108 | P1 | 设置 → 微信二维码 / 预览 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/weixin-qrcode-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-53、T49-54、T57-45、T57-46 |
| P-109 | P0 | 设置 → 频道全局 / webhook | 改前缀回复语言、事件及webhook开关，添加两个webhook；移除先取消后确认 | 持久化/校验保持；确认具体 URL、不误删；Spinner/图标正常 | 明（主题/语言功能按步骤切换） | T49-55、T49-56、T57-47、T57-48、T54-48 |
| P-110 | P1 | 设置 → 频道全局 / webhook / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/channel-events-tab.tsx`、`WEB/components/settings/channel-other-tab.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-55、T49-56、T57-47、T57-48、T54-48 |
| P-111 | P0 | 设置 → 系统网络 / 更新 | 开关代理、修改URL保存；检查服务器更新，失败后重试 | 字段显隐/busy/持续错误和结果保持；共享Spinner、原接口不变 | 明（主题/语言功能按步骤切换） | T49-57、T49-58、T57-49、T57-50 |
| P-112 | P1 | 设置 → 系统网络 / 更新 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/system-network-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-57、T49-58、T57-49、T57-50 |
| P-113 | P0 | 设置 → 版本控制设置 | 检测Git，改路径测试后保存；查看空账户/默认账户 | GitBranch、Spinner/禁用正确；状态/版本/路径/测试保存结果与默认标记保持 | 明（主题/语言功能按步骤切换） | T49-59、T49-60、T57-51、T57-52 |
| P-114 | P1 | 设置 → 版本控制设置 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/version-control-settings.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-59、T49-60、T57-51、T57-52 |
| P-115 | P0 | 设置 → 关于 / 开源许可 | 查看应用/服务器版本与更新入口；滚动长许可、长按选择复制并返回 | Info/Shield、关于插图清楚，版本正确，分块复制/滚动/导航保持 | 明（主题/语言功能按步骤切换） | T49-61、T49-62、T57-53、T57-54 |
| P-116 | P1 | 设置 → 关于 / 开源许可 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/app/settings/page.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T49-61、T49-62、T57-53、T57-54 |
| P-117 | P0 | 工作任务看板 | 四列/显示已取消，详情事件文件diff与返回；待办上移下移、开始全部并刷新 | 列计数/过滤、状态排序仅作用当前项目且持久化；隐藏动作规则、忙态不伪造百分比 | 明（主题/语言功能按步骤切换） | T50-01、T50-02、T58-01、T58-02、T54-57 |
| P-118 | P1 | 工作任务看板 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/tasks/tasks-page.tsx`、`WEB/components/tasks/task-row.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-01、T50-02、T58-01、T58-02、T54-57 |
| P-119 | P0 | 工作任务创建 / 编辑 | 模板填充、同名项目和代理分别选；空字段校验、多行描述保存重开编辑 | 实体ID正确，附件和未知config保留；busy真禁用，键盘下可保存 | 明（主题/语言功能按步骤切换） | T50-03、T50-04、T58-03、T58-04 |
| P-120 | P1 | 工作任务创建 / 编辑 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/tasks/task-editor-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-03、T50-04、T58-03、T58-04 |
| P-121 | P0 | 工作任务动作 | 删除/取消/合并/清理先取消后确认；退回空/有效反馈，调度非法/合法时间，busy连点 | 任务标题和ID明确；非法禁确认，取消无请求；busy不重复，原字段与动作隐藏条件保持 | 明（主题/语言功能按步骤切换） | T50-05、T50-06、T58-05、T58-06 |
| P-122 | P1 | 工作任务动作 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/tasks/task-cancel-dialog.tsx`、`WEB/components/tasks/task-merge-dialog.tsx`、`WEB/components/tasks/task-schedule-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-05、T50-06、T58-05、T58-06 |
| P-123 | P0 | 工作任务模板 | 新建保存编辑模板；尝试改已有名称，复制名称；删除先取消后确认 | 已有名称只读可复制；配置填充/管理列表滚动保持；目标名正确，busy保护 | 明（主题/语言功能按步骤切换） | T50-07、T50-08、T58-07、T58-08、T54-58 |
| P-124 | P1 | 工作任务模板 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/tasks/task-editor-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-07、T50-08、T58-07、T58-08、T54-58 |
| P-125 | P0 | 工作任务设置 | 同名项目切换，继承/独立、布尔合并策略；非法JSON/数值再合法保存，重置先取消后确认 | 按ID选择，原校验说明/禁保存保留；重置明确当前项目，busy不能交互 | 明（主题/语言功能按步骤切换） | T50-09、T50-10、T58-09、T58-10 |
| P-126 | P1 | 工作任务设置 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/tasks/task-settings-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-09、T50-10、T58-09、T58-10 |
| P-127 | P0 | 自动化列表 / 详情 / 删除 | 全部/启用/禁用过滤，开关与详情，运行并刷新历史；删除自动化/取消run先取消后确认 | 开关不导航；配置提示词/状态badge/错误摘要时间保持；确认名称/run ID，busy不重复，历史正确 | 明（主题/语言功能按步骤切换） | T50-11、T50-12、T50-15、T50-16、T58-11、T58-12、T54-21 |
| P-128 | P1 | 自动化列表 / 详情 / 删除 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/automations/automations-page.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-11、T50-12、T50-15、T50-16、T58-11、T58-12、T54-21 |
| P-129 | P0 | 自动化编辑 | 手动/定时、cron非法合法与时区预览；同名项目代理，切代理后检查既有配置；键盘下保存 | 原ID/枚举、草稿和配置保持；无效计划不保存，busy字段禁用，固定footer可达、列表同步 | 明（主题/语言功能按步骤切换） | T50-13、T50-14、T58-13、T58-14 |
| P-130 | P1 | 自动化编辑 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/automations/automation-editor.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-13、T50-14、T58-13、T58-14 |
| P-131 | P0 | 日志 / 用量 | 最近/文件快速切换，级别关键字过滤、无匹配；开长日志返回刷新；7/30天/全部统计；缺部分/全部可选接口 | 查询不串页，文件元数据/截断提示和返回正确；不确定进度；会话token缓存日序列/格式正确，缺能力隐藏，空标签不崩溃 | 明（主题/语言功能按步骤切换） | T50-17、T50-18、T50-19、T50-20、T58-15、T58-16、T54-40 |
| P-132 | P1 | 日志 / 用量 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/settings/logs-settings.tsx`、`WEB/components/layout/status-bar-stats.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-17、T50-18、T50-19、T50-20、T58-15、T58-16、T54-40 |
| P-133 | P0 | 动态 | 运行中/最近折叠展开，点会话返回，下拉/顶部刷新，空态及错误重试 | 单一箭头、计数/状态正确，目标会话准确，空态可刷新 | 明（主题/语言功能按步骤切换） | T50-21、T50-22、T58-17、T58-18、T54-20 |
| P-134 | P1 | 动态 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/status-bar.tsx`、`WEB/components/layout/status-bar-alerts.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-21、T50-22、T58-17、T58-18、T54-20 |
| P-135 | P0 | 全局搜索 | IME Search输入关键字，点结果返回，清空、点历史、清历史；无结果/失败重试并重启 | 历史更新保存规则不变，持续错误留页面；搜索Spinner/16dp图标、清除≥48dp，原提交保持 | 明（主题/语言功能按步骤切换） | T50-23、T50-24、T58-19、T58-20、T54-37 |
| P-136 | P1 | 全局搜索 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/app-title-bar.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-23、T50-24、T58-19、T58-20、T54-37 |
| P-137 | P0 | 自动更新横幅 / 关于更新 | 手动检查已最新/失败/有新版，展开长说明取消/稍后/忽略版本；后台自动检查；下载未知/已知大小，下载/校验/安装启动失败；安装权限往返 | 已最新info/检查失败error一次，新版仍说明；自动检查不发手动Toast；取消不下载，进度不伪造；失败error；权限/FileProvider保持，安装器只启动一次 | 明（主题/语言功能按步骤切换） | T50-33、T50-34、T58-27、T58-28 |
| P-138 | P1 | 自动更新横幅 / 关于更新 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/status-bar-update.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T50-33、T50-34、T58-27、T58-28 |
| P-139 | P2 | 项目 / Git 兼容与边界 | 官方0.38/缺能力服务逐一打开项目与Git入口；空仓库/无变更；长分支/hash/路径、200目录、大批记录、损坏/超大图片；键盘、横屏、返回/遮罩 | 404/unknown沿用隐藏规则；空加载错误可恢复，无无界滚动崩溃；危险取消无请求；解码内存压力需记录；Toast中英本地化 | 明；布局风险明暗复查 | T48-18L、T48-18D、T56-18L、T56-18D |
| P-140 | P0 | Gallery 入口 / debug 与 release 隔离 | 协调方用debug APK冷/热启动 route=devgallery（无服务器亦测），逐级返回；普通/未知route和已有deep link；记录App主题，切Gallery局部主题重建退出；release冷/热传同参数 | debug直达分组、重入/返回正常；release不注册路由且不能进入；局部主题和选择恢复但不写App偏好；中英全分组资源正确，返回先关弹层 | 明（主题/语言功能按步骤切换） | T59-19、T59-20、T59-21、T59-22 |
| P-141 | P1 | Gallery 入口 / debug 与 release 隔离 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/button.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T59-19、T59-20、T59-21、T59-22 |
| P-142 | P0 | Gallery → T43 菜单 | 展开下拉/更多/长按上下文、popover、tooltip；选勾选项，尝试禁用，点外部/返回关闭 | 禁用/选中/分隔清楚，Check/EllipsisVertical/ChevronRight正确，回调一次，关闭不触发 | 明（主题/语言功能按步骤切换） | T59-01、T59-02 |
| P-143 | P1 | Gallery → T43 菜单 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/dropdown-menu.tsx`、`WEB/components/ui/context-menu.tsx`、`WEB/components/ui/popover.tsx`、`WEB/components/ui/tooltip.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T59-01、T59-02 |
| P-144 | P0 | Gallery → T44 对话框 / 抽屉 / 卡片 | 逐一打开T44Gallery/T44CardsGallery中dialog/confirm/destructive/sheet/full；disabled确认、busy确认尝试确认取消遮罩返回，查看content | 卡片所有变体可达，危险确认明确目标；disabled仅禁确认、busy显示Spinner且禁止关闭/重复；content位于描述与footer间；全屏X关闭/滚动正确 | 明（主题/语言功能按步骤切换） | T59-03、T59-04、T60-05 |
| P-145 | P1 | Gallery → T44 对话框 / 抽屉 / 卡片 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/dialog.tsx`、`WEB/components/ui/alert-dialog.tsx`、`WEB/components/ui/sheet.tsx`、`WEB/components/ui/card.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T59-03、T59-04、T60-05 |
| P-146 | P0 | Gallery → T45 按钮 / 表单 / 选择器 | 滚到底操作所有按钮/单复选开关；普通/禁用/只读输入；多行>4行、Done与两侧slot；同名实体按描述搜索清空，测试旧String选择；密码显隐 | 禁用.5且不能聚焦，只读可复制不可改；多行2～4可滚动、IME回调；slot/Spinner可见；选中唯一ID回传原实体，旧版保持；Eye/EyeOff和密码遮挡正确 | 明（主题/语言功能按步骤切换） | T59-05、T59-06、T60-01、T60-02、T60-03、T60-08 |
| P-147 | P1 | Gallery → T45 按钮 / 表单 / 选择器 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/button.tsx`、`WEB/components/ui/input.tsx`、`WEB/components/ui/textarea.tsx`、`WEB/components/ui/select.tsx`、`WEB/components/ui/combobox.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T59-05、T59-06、T60-01、T60-02、T60-03、T60-08 |
| P-148 | P0 | Gallery → T46 Tabs / Badge / Rows / Feedback | 分别打开T46Gallery/Tabs/Badges/Rows/Feedback五入口；切标签、普通/禁用图标chip，行more/长按；观察单行与3/2行、进度0/.5/1及无参数、16/24dp Spinner | 五入口全有，普通切一次禁用不触发且.5；默认单行省略/新行数保持；进度轨道与循环不溢出，Spinner继承内容色 | 明（主题/语言功能按步骤切换） | T59-07、T59-08、T60-04、T60-06、T60-07 |
| P-149 | P1 | Gallery → T46 Tabs / Badge / Rows / Feedback / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/tabs.tsx`、`WEB/components/ui/badge.tsx`、`WEB/components/ui/progress.tsx`、`WEB/components/conversations/sidebar-conversation-list.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T59-07、T59-08、T60-04、T60-06、T60-07 |
| P-150 | P0 | Gallery → T51 图标 / API 与无障碍 | 滚动325项到末项并快滚重入；同模块现有验证宿主观察16/24/32dp、继承前景/显式tint；TalkBack聚焦带描述与装饰图标 | 325唯一名图对应，无空白解析异常，默认16dp不裁切；颜色继承/覆盖无黑色残留；描述正确、装饰不重复朗读，实例稳定 | 明（主题/语言功能按步骤切换） | T51-01、T51-02、T51-03、T51-04、T59-09、T59-10 |
| P-151 | P1 | Gallery → T51 图标 / API 与无障碍 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/button.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T51-01、T51-02、T51-03、T51-04、T59-09、T59-10 |
| P-152 | P2 | Gallery → T51 生成一致性（协调方离线检查） | 协调方按T51文档第2节执行图标生成器四项自测、固定版本素材扫描与离线清单生成，比较SHA-256与映射清单（不是gen_code_index.py） | 325排序唯一、96种Material引用映射齐全，两次生成一致；未知SVG/transform/非24视口明确失败；ISC/Feather许可保留，无SVG/tgz或依赖入库；本任务不执行生成 | 明；布局风险明暗复查 | T51-05 |
| P-153 | P0 | Gallery → T52 Toast 全类型 / 生命周期 | success/error/info/warning各触发；loading等10秒后finish；action重试；1秒内4条；左右>48dp拖与短拖；loading→error(id)→重复dismiss/update；卡外计数按钮/TalkBack；缺宿主入口调用；进后台恢复、退出重入 | 普通约4秒退场，loading持续、同ID更新不增队列；最多3条最新在下、隐藏补位有完整阅读时长；action恰一次；长拖退出短拖复位；已关闭不复活；卡外透传/polite朗读/本地化描述；缺宿主无崩溃且日志无业务标题；协程随退出取消、无旧Toast重播 | 明（主题/语言功能按步骤切换） | T52-01、T52-02、T52-03、T52-04、T52-05、T52-06、T52-07、T52-08、T52-09、T52-10、T52-11、T59-11、T59-12 |
| P-154 | P1 | Gallery → T52 Toast 全类型 / 生命周期 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/app-toaster.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T52-01、T52-02、T52-03、T52-04、T52-05、T52-06、T52-07、T52-08、T52-09、T52-10、T52-11、T59-11、T59-12 |
| P-155 | P0 | Gallery → T53 折叠 | 普通组点两次，动画中反向点；即时组点击，TalkBack聚焦 | 受控回调每次一次，最终状态正确；即时无动画挂载卸载，箭头/朗读状态一致 | 明（主题/语言功能按步骤切换） | T53-01、T53-02、T59-13、T59-14 |
| P-156 | P1 | Gallery → T53 折叠 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/collapsible.tsx`、`WEB/components/ui/instant-collapsible.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T53-01、T53-02、T59-13、T59-14 |
| P-157 | P0 | Gallery → T53 Avatar | 对比24/32/40dp，普通Painter/null，空白/中文/emoji/超长RTL名称 | 圆形居中裁切、1dp边框、muted fallback首码点完整；空白无字，不撑大，TalkBack读完整名称 | 明（主题/语言功能按步骤切换） | T53-03、T53-04、T59-13、T59-14 |
| P-158 | P1 | Gallery → T53 Avatar / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/avatar.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T53-03、T53-04、T59-13、T59-14 |
| P-159 | P0 | Gallery → T53 InputGroup | 搜索聚焦失焦清除，量尺寸；error/disabled/readOnly，200字输入，RTL | 前后slot同框，3dp ring不改尺寸；error destructive、禁用不能改、只读可读；单行滚动/RTL排列正确 | 明（主题/语言功能按步骤切换） | T53-05、T53-06、T59-13、T59-14 |
| P-160 | P1 | Gallery → T53 InputGroup / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/input-group.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T53-05、T53-06、T59-13、T59-14 |
| P-161 | P0 | Gallery → T53 图片预览 | 三图initialIndex=1，双击/双指/平移和前后；空列表/-1/999及动态缩短；关闭/返回；降采样12000×8000来源图、纵图/1px，旋转/RTL | 首显2/3，双击1↔2、双指1～5，切图归1居中；首尾禁用；空可关闭、索引夹取/变更按initialIndex；Fit不变形、窄轴不拖至全空白、按钮常可见，RTL箭头排列对应 | 明（主题/语言功能按步骤切换） | T53-07、T53-08、T53-09、T59-13、T59-14 |
| P-162 | P1 | Gallery → T53 图片预览 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/image-preview-dialog.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T53-07、T53-08、T53-09、T59-13、T59-14 |
| P-163 | P0 | Gallery → T53 Command | 标签子串/大小写关键词/无匹配/清空；禁用可用点击，上下键循环和Enter；100条、空组/全禁用、200字/RTL | 空分组隐藏，清空恢复序；首可用高亮，键盘跳禁用且滚动可见；ID回调一次；无活动项安全，长文省略不溢出 | 明（主题/语言功能按步骤切换） | T53-10、T53-11、T53-12、T59-13、T59-14 |
| P-164 | P1 | Gallery → T53 Command / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/command.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T53-10、T53-11、T53-12、T59-13、T59-14 |
| P-165 | P0 | Gallery → T53 FolderBadge | Codeg/中文/空白/200字/emoji/RTL，覆盖web.secondary色，TalkBack | 14×14dp/4dp角/9sp首码点，空白无字、长名不膨胀；primary/foreground与覆盖色对比可读，读全名 | 明（主题/语言功能按步骤切换） | T53-13、T53-14、T59-13、T59-14 |
| P-166 | P1 | Gallery → T53 FolderBadge / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/folder-badge.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T53-13、T53-14、T59-13、T59-14 |
| P-167 | P0 | Gallery → T54 顶栏 / 状态 | 顶栏点/长按移出、Tab/Enter/空格/悬停；禁用按钮；长标题副标题/titleContent/分隔true/false及滚动；空态label与callback四组合；长loading、error重试显隐和自定义label | 顶栏48dp内容行、14sp medium；32/36dp视觉/48dp触摸、8dp角/16dp图标，accent与3dp半透明焦点ring；禁用整体.5无回调；省略/分隔滚动规则正确无M3抬升；空态仅label+callback都有才按钮；最终Lucide Loader2 16dp旋转，长文不挤出；错误destructive且重试一次，中英默认/自定义文案正确 | 明（主题/语言功能按步骤切换） | T54-01、T54-02、T54-03、T54-04、T54-05、T54-06、T54-07、T54-08、T59-15、T59-16 |
| P-168 | P1 | Gallery → T54 顶栏 / 状态 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/layout/app-title-bar.tsx`、`WEB/components/layout/folder-title-bar.tsx`、`WEB/components/ui/button.tsx`、`WEB/components/project-boot/shadcn/shadcn-preview.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T54-01、T54-02、T54-03、T54-04、T54-05、T54-06、T54-07、T54-08、T59-15、T59-16 |
| P-169 | P0 | Gallery → T60 分组复用 | 从T60分组逐一进入复用T44/T45/T46函数，核对说明和新增变体，再返回 | 不是独立旧样例副本；T60新增输入/确认/选择/反馈/行等可达，细项在上述分组执行一次 | 明（主题/语言功能按步骤切换） | T59-17、T59-18 |
| P-170 | P1 | Gallery → T60 分组复用 / 明暗视觉 | 在本页完成功能时固定最终数据，明色截图→切暗色同位置截图；本页弹层、菜单、忙态、空/错误实际存在的状态各留对照截图；不重复写操作 | 对照 `WEB/components/ui/alert-dialog.tsx`、`WEB/components/ui/input.tsx`、`WEB/components/ui/badge.tsx`；background/foreground/muted/border/danger、选中/禁用、图标语义尺寸、分隔和文字无丢失/遮挡；两主题均有真实截图 | 明/暗各截图 | T59-17、T59-18 |
| P-171 | P1 | Gallery → T51 / MessageSquare | 打开聊天模式选择，定位同名 Gallery 项并按上述流程截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/chat/conversation-context-bar.tsx:574`；单闭合对话框与尾角一致；无 fill，圆角接头；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-06 |
| P-172 | P1 | Gallery → T51 / RefreshCw | 打开 Git 更多菜单刷新项，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/layout/aux-panel-git-changes-tab.tsx:1612`；四段路径、两侧箭头方向和弧线一致，端点为 Round；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-07 |
| P-173 | P1 | Gallery → T51 / Ellipsis | 打开会话分区标题，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/conversations/sidebar-section-header.tsx:245`；三个等间距水平空心圆，线宽一致，无实心填充；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-08 |
| P-174 | P1 | Gallery → T51 / EllipsisVertical | 显示会话详情更多入口，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/conversations/conversation-detail-header.tsx:309`；三个等间距竖向空心圆；不与横向更多混淆；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-09 |
| P-175 | P1 | Gallery → T51 / Search | 打开命令选择组件，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/ui/command.tsx:64`；放大镜圆与斜柄位置、连接及圆端点一致；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-10 |
| P-176 | P1 | Gallery → T51 / Plus | 显示输入栏添加按钮，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/chat/composer/composer-add-menu.tsx:138`；横竖两笔居中，等长、等宽、Round cap；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-11 |
| P-177 | P1 | Gallery → T51 / X | 打开可关闭对话框，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/ui/dialog.tsx:119`；两条交叉斜线位置一致，四端均为 Round；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-12 |
| P-178 | P1 | Gallery → T51 / Check | 打开勾选式菜单并选中一项，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/ui/dropdown-menu.tsx:129`；勾号两笔比例与拐角一致；无额外填充；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-13 |
| P-179 | P1 | Gallery → T51 / ChevronDown | 显示未展开工具标题，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/ai-elements/tool.tsx:118`；向下折线一致，中心接头圆润，端点 Round；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-14 |
| P-180 | P1 | Gallery → T51 / ChevronRight | 打开文件浏览目录层级，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/shared/server-file-browser-dialog.tsx:255`；向右折线与网页一致，非粗 Material 箭头；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-15 |
| P-181 | P1 | Gallery → T51 / Folder | 打开目录选择器，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/shared/folder-select.tsx:62`；文件夹标签、外框和圆角一致；内部透明；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-16 |
| P-182 | P1 | Gallery → T51 / FolderOpen | 展示侧栏目录标题，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/conversations/sidebar-conversation-list.tsx:458`；打开文件夹斜边、标签和路径衔接一致；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-17 |
| P-183 | P1 | Gallery → T51 / Copy | 显示 Mermaid 复制按钮的未复制状态，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/ai-elements/mermaid-block.tsx:579`；两层矩形错位及遮挡一致；圆角矩形不变成直角；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-18 |
| P-184 | P1 | Gallery → T51 / Trash2 | 显示目录删除入口但不执行删除，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/layout/workspace-folder-dialog.tsx:938`；盖子、把手、桶体和两竖线完整；圆端点一致；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-19 |
| P-185 | P1 | Gallery → T51 / Pencil | 打开目录编辑入口，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/layout/workspace-folder-dialog.tsx:851`；笔尖、笔身、斜切线一致；无实心填充；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-20 |
| P-186 | P1 | Gallery → T51 / ArrowLeft | 打开目录选择内页返回入口，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/layout/workspace-folder-dialog.tsx:475`；左向箭头和横杆一致，Round cap/join，无自动翻转；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-21 |
| P-187 | P1 | Gallery → T51 / ArrowUp | 选择消息文本显示操作浮层，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/message/selection-action-bubble.tsx:473`；上箭头与竖杆比例、中心对齐一致；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-22 |
| P-188 | P1 | Gallery → T51 / Square | 会话生成中显示停止按钮，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/chat/message-input.tsx:1812`；圆角方框；内部透明；无 Material 实心方块；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-23 |
| P-189 | P1 | Gallery → T51 / Circle | 展示工具输入流式状态，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/ai-elements/tool.tsx:59`；圆形闭合平滑；两个弧段衔接不出现缺口；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-24 |
| P-190 | P1 | Gallery → T51 / ShieldAlert | 触发权限请求并保持待确认，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/chat/permission-dialog.tsx:126`；盾牌、感叹号竖线和圆点完整；不发生填充反转；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-25 |
| P-191 | P1 | Gallery → T51 / Brain | 显示推理区标题，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/ai-elements/reasoning.tsx:186`；八段复杂曲线均保留，无缺线、交点位移、尖角；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-26 |
| P-192 | P1 | Gallery → T51 / Calendar | 显示日期范围筛选触发器，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/token-usage/token-usage-filters.tsx:400`；外框圆角、两条挂钩、顶部分隔线一致；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-27 |
| P-193 | P1 | Gallery → T51 / Loader2 | 显示设置区加载状态，暂停网页 CSS 动画后截图对照；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/shared/settings-section.tsx:285`；单条开口弧形一致；Gallery 为静态向量，不负责旋转动画；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-28 |
| P-194 | P1 | Gallery → T51 / FolderPlus | 打开目录浏览新建目录入口，对照截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/shared/directory-browser.tsx:701`；文件夹及加号完整，圆角与端点一致；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-29 |
| P-195 | P1 | Gallery → T51 / KeyRound | 显示权限记住选项，对照 Gallery 并按上述流程截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/chat/permission-dialog.tsx:289`；钥匙齿与头部小圆点可辨；上游小圆 fill 被统一省略后，粗描边形成的圆点外观仍一致；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-30 |
| P-196 | P1 | Gallery → T51 / Palette | 显示设置外观导航，对照 Gallery 并按上述流程截图；两端各取明暗截图，统一16px/16dp、24 viewBox、stroke=2（约1.33dp），保留网页原尺寸截图 | 对照 `WEB/components/settings/settings-shell.tsx:72`；调色盘轮廓、四个半径 0.5 的小圆点完整；特别核对缩小到 16dp 后的抗锯齿与实心感；无fill，允许平台抗锯齿微差；各记录四张截图、密度/主题/结果 | 明/暗各截图 | T51-31 |
| P-197 | P2 | 全入口 / 边界与可访问性矩阵 | 按本文页面顺序就地测试：360dp小屏、横屏、200%字号、英文长文/RTL、IME、TalkBack及外接键盘；空列表/无匹配、限速/断网恢复；各页长标题/表单/错误、200字选项、三条长Toast；动态与目录大数据滚动记录帧/内存现象 | 各页逐项记录结果；无无限高度/崩溃/不可达footer；48dp触摸和禁用语义实测；Toast不遮关键动作，图片降采样；状态恢复、只读/ID/取消无副作用；已有持续错误不被Toast替代 | 明；布局风险明暗复查 | T47-01、T47-02、T47-03、T47-04、T47-05、T47-06、T47-07、T47-08、T47-09、T47-10、T47-11、T47-12、T47-13、T47-14、T47-15、T47-16、T47-17、T47-18、T47-19、T47-20、T48-01L、T48-01D、T48-02L、T48-02D、T48-03L、T48-03D、T48-04L、T48-04D、T48-05L、T48-05D、T48-06L、T48-06D、T48-07L、T48-07D、T48-08L、T48-08D、T48-09L、T48-09D、T48-10L、T48-10D、T48-11L、T48-11D、T48-12L、T48-12D、T48-13L、T48-13D、T48-14L、T48-14D、T48-15L、T48-15D、T48-16L、T48-16D、T48-17L、T48-17D、T48-18L、T48-18D、T49-01、T49-02、T49-03、T49-04、T49-05、T49-06、T49-07、T49-08、T49-09、T49-10、T49-11、T49-12、T49-13、T49-14、T49-15、T49-16、T49-17、T49-18、T49-19、T49-20、T49-21、T49-22、T49-23、T49-24、T49-25、T49-26、T49-27、T49-28、T49-29、T49-30、T49-31、T49-32、T49-33、T49-34、T49-35、T49-36、T49-37、T49-38、T49-39、T49-40、T49-41、T49-42、T49-43、T49-44、T49-45、T49-46、T49-47、T49-48、T49-49、T49-50、T49-51、T49-52、T49-53、T49-54、T49-55、T49-56、T49-57、T49-58、T49-59、T49-60、T49-61、T49-62、T50-01、T50-02、T50-03、T50-04、T50-05、T50-06、T50-07、T50-08、T50-09、T50-10、T50-11、T50-12、T50-13、T50-14、T50-15、T50-16、T50-17、T50-18、T50-19、T50-20、T50-21、T50-22、T50-23、T50-24、T50-25、T50-26、T50-27、T50-28、T50-29、T50-30、T50-31、T50-32、T50-33、T50-34、T51-01、T51-02、T51-03、T51-04、T51-05、T51-06、T51-07、T51-08、T51-09、T51-10、T51-11、T51-12、T51-13、T51-14、T51-15、T51-16、T51-17、T51-18、T51-19、T51-20、T51-21、T51-22、T51-23、T51-24、T51-25、T51-26、T51-27、T51-28、T51-29、T51-30、T51-31、T52-01、T52-02、T52-03、T52-04、T52-05、T52-06、T52-07、T52-08、T52-09、T52-10、T52-11、T53-01、T53-02、T53-03、T53-04、T53-05、T53-06、T53-07、T53-08、T53-09、T53-10、T53-11、T53-12、T53-13、T53-14、T54-01、T54-02、T54-03、T54-04、T54-05、T54-06、T54-07、T54-08、T54-20、T54-21、T54-22、T54-23、T54-24、T54-25、T54-26、T54-27、T54-28、T54-29、T54-30、T54-31、T54-32、T54-33、T54-34、T54-35、T54-36、T54-37、T54-38、T54-39、T54-40、T54-41、T54-42、T54-43、T54-44、T54-45、T54-46、T54-47、T54-48、T54-49、T54-50、T54-51、T54-52、T54-53、T54-54、T54-55、T54-56、T54-57、T54-58、T55-01、T55-02、T55-03、T55-04、T55-05、T55-06、T55-07、T55-08、T55-09、T55-10、T55-11、T55-12、T55-13、T55-14、T55-15、T56-01L、T56-01D、T56-02L、T56-02D、T56-03L、T56-03D、T56-04L、T56-04D、T56-05L、T56-05D、T56-06L、T56-06D、T56-07L、T56-07D、T56-08L、T56-08D、T56-09L、T56-09D、T56-10L、T56-10D、T56-11L、T56-11D、T56-12L、T56-12D、T56-13L、T56-13D、T56-14L、T56-14D、T56-15L、T56-15D、T56-16L、T56-16D、T56-17L、T56-17D、T56-18L、T56-18D、T57-01、T57-02、T57-03、T57-04、T57-05、T57-06、T57-07、T57-08、T57-09、T57-10、T57-11、T57-12、T57-13、T57-14、T57-15、T57-16、T57-17、T57-18、T57-19、T57-20、T57-21、T57-22、T57-23、T57-24、T57-25、T57-26、T57-27、T57-28、T57-29、T57-30、T57-31、T57-32、T57-33、T57-34、T57-35、T57-36、T57-37、T57-38、T57-39、T57-40、T57-41、T57-42、T57-43、T57-44、T57-45、T57-46、T57-47、T57-48、T57-49、T57-50、T57-51、T57-52、T57-53、T57-54、T58-01、T58-02、T58-03、T58-04、T58-05、T58-06、T58-07、T58-08、T58-09、T58-10、T58-11、T58-12、T58-13、T58-14、T58-15、T58-16、T58-17、T58-18、T58-19、T58-20、T58-21、T58-22、T58-23、T58-24、T58-25、T58-26、T58-27、T58-28、T59-01、T59-02、T59-03、T59-04、T59-05、T59-06、T59-07、T59-08、T59-09、T59-10、T59-11、T59-12、T59-13、T59-14、T59-15、T59-16、T59-17、T59-18、T59-19、T59-20、T59-21、T59-22、T60-01、T60-02、T60-03、T60-04、T60-05、T60-06、T60-07、T60-08 |

## 最终状态与允许差异

T47/T48/T49/T50 的业务行为及校验继续有效；其“保留Material图标、仅TODO Toast/头像/折叠/预览、无enabled API、原生加载指示器”的阶段性预期由 T55/T56/T57/T58/T59/T60 的 Lucide、Toast、共享预览/折叠、真禁用、不确定Progress/Spinner最终状态取代，不再按中间态判失败。T51–T54 的“尚未接入业务/等待图标替换”也仅是阶段说明，不再要求保持旧调用点。T60只补组件能力，不证明所有feature均已迁移。

Android允许Sheet/全屏Sheet、单列、系统返回/IME/inset、48dp触摸区；不照搬桌面hover/多栏。保留ComposeBar多行BasicTextField及附件布局、单色代理AgentAvatar tint、完整emoji助手自绘；CodegCommandList描述合入单行label可省略，无正文工具可显示箭头但不展开。图片预览多图/1～5倍为移动扩展，背景使用web.background；前后按钮翻页，无滑动翻页/下载复制要求。FolderBadge用首码点与token色，不复制网页hash算法。Toast手机16dp边距、三卡展开8dp间距、180ms进退场/48dp拖阈值，网页桌面hover折叠/暂停不要求复制；图标最终为Lucide。

## 回归与待人工确认风险

- 所有源文档都未声明编译、JVM或真机通过；本任务纯Markdown，未引入Kotlin/API/import/资源/依赖编译风险；原实现能否编译由协调方统一验证。
- T47/T48/T60：ListRow仍无enabled API，回调guard可能留下“可点击”无障碍语义；不能把T60新参数存在视作全部调用已修复。逐页验证busy与TalkBack；确认框busy固定Gallery态须重开宿主重置，业务异步结束需另测。
- T55/T56/T57/T58与T52：Toast依赖T58主壳宿主。独立Dialog/Sheet窗口可能挡住提示；首次配置可能缺祖先宿主。弹层内/引导内反馈必须可见，否则记缺陷。三条极长Toast在大字体小屏可能超高；后台不暂停计时，恢复阅读行为需确认。退出清理loading，持续错误与retry hint仍保留。
- T55：ui.notice无类型，除识别的断开success和备注warning外其余按error（含附件跳过/运行中提示），记录现状并交协调方确认分级；单色品牌tint、ComposeBar组件缺口保留，不能宣称全部组件已迁移。
- T56：BranchManage/Stash/GitMisc空数据及Merge未ready、Forge共享错误通道同时保留持续错误和Toast；GitMisc load/refresh亦可success是既有模型语义，不错误去重为无反馈。解码失败/图片内存、嵌套滚动、大列表折叠首次组合均待真机。
- T57：完整emoji头像自绘及普通头像DS默认色需协调方确认；CodegSelect内部触摸范围、VectorPainter主题对比度必须实测。
- T58：动态按整个分区组合会话行，长列表可能比旧Lazy逐行方案更贵；记录性能，不以“可滚动”代替无性能风险结论。MainShell五导航、深链、返回栈、rail/底栏滚动和IME恢复逐项覆盖；手动检查更新与后台检查分离。
- T51/T53/T59：图标保持上游方向，不一律RTL镜像；图片箭头按组件既有方向逻辑、菜单ChevronRight不自动镜像。图标语义须区别CircleDashed/CircleCheckBig、Ellipsis/EllipsisVertical。KeyRound/Palette/Tag/Tags小圆fill省略后的16dp抗锯齿需截图确认，Tag/Tags包含于325项网格；向量不负责动画，Loader由宿主旋转。图标生成器限定lucide-static@0.564.0，未知SVG明确失败；不把任意第三方SVG兼容列作通过条件。
- T53：大图内存由调用方降采样负责；Command 300dp上界/图片有界容器、controlled expanded/query、Painter和关闭回调状态需集成确认。颜色alpha token在多个主题下检查ring/边框。
- T54：默认顶栏分隔线可能与页面旧线叠加、加载由纵排转横排；小屏横屏200%字号/长标题错误文字和本地化需逐页留图。读屏不重复朗读装饰图标。
- T59/T60：debug入口Activity与MainShell双路径均按冷/热和返回验证；release隔离为P0。T60组复用原Gallery函数，新增变体要实际出现；报告级L4迁移候选没有代码核实，不在本任务扩展实现范围。

## 覆盖对照表

每个原始表格编号独立一行；功能与视觉分开映射。旧阶段仅替换展示/组件预期，原业务校验保留；不删除整条原用例。全入口P2矩阵为附加覆盖，不用于补齐漏映射。

| 原用例编号 | 合并后编号 | 处理说明 |
|---|---|---|
| T47-01 | P-007、P-008 | 由 P-007、P-008 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-02 | P-007、P-008 | 由 P-007、P-008 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-03 | P-007、P-008 | 由 P-007、P-008 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-04 | P-007、P-008 | 由 P-007、P-008 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-05 | P-011、P-012 | 由 P-011、P-012 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-06 | P-011、P-012 | 由 P-011、P-012 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-07 | P-013、P-014 | 由 P-013、P-014 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-08 | P-013、P-014 | 由 P-013、P-014 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-09 | P-015、P-016 | 由 P-015、P-016 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-10 | P-017、P-018 | 由 P-017、P-018 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-11 | P-019、P-020 | 由 P-019、P-020 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-12 | P-019、P-020 | 由 P-019、P-020 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-13 | P-021、P-022 | 由 P-021、P-022 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-14 | P-021、P-022 | 由 P-021、P-022 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-15 | P-023、P-024 | 由 P-023、P-024 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-16 | P-023、P-024 | 由 P-023、P-024 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-17 | P-025、P-026 | 由 P-025、P-026 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-18 | P-025、P-026 | 由 P-025、P-026 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-19 | P-027、P-028 | 由 P-027、P-028 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T47-20 | P-027、P-028 | 由 P-027、P-028 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-01L | P-033、P-034 | 由 P-033、P-034 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-01D | P-033、P-034 | 由 P-033、P-034 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-02L | P-035、P-036 | 由 P-035、P-036 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-02D | P-035、P-036 | 由 P-035、P-036 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-03L | P-037、P-038 | 由 P-037、P-038 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-03D | P-037、P-038 | 由 P-037、P-038 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-04L | P-039、P-040 | 由 P-039、P-040 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-04D | P-039、P-040 | 由 P-039、P-040 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-05L | P-041、P-042 | 由 P-041、P-042 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-05D | P-041、P-042 | 由 P-041、P-042 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-06L | P-043、P-044 | 由 P-043、P-044 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-06D | P-043、P-044 | 由 P-043、P-044 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-07L | P-045、P-046 | 由 P-045、P-046 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-07D | P-045、P-046 | 由 P-045、P-046 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-08L | P-047、P-048 | 由 P-047、P-048 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-08D | P-047、P-048 | 由 P-047、P-048 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-09L | P-049、P-050 | 由 P-049、P-050 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-09D | P-049、P-050 | 由 P-049、P-050 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-10L | P-051、P-052 | 由 P-051、P-052 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-10D | P-051、P-052 | 由 P-051、P-052 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-11L | P-053、P-054 | 由 P-053、P-054 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-11D | P-053、P-054 | 由 P-053、P-054 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-12L | P-055、P-056 | 由 P-055、P-056 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-12D | P-055、P-056 | 由 P-055、P-056 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-13L | P-057、P-058 | 由 P-057、P-058 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-13D | P-057、P-058 | 由 P-057、P-058 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-14L | P-059、P-060 | 由 P-059、P-060 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-14D | P-059、P-060 | 由 P-059、P-060 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-15L | P-061、P-062 | 由 P-061、P-062 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-15D | P-061、P-062 | 由 P-061、P-062 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-16L | P-063、P-064 | 由 P-063、P-064 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-16D | P-063、P-064 | 由 P-063、P-064 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-17L | P-065、P-066 | 由 P-065、P-066 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-17D | P-065、P-066 | 由 P-065、P-066 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-18L | P-139 | 由 P-139 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T48-18D | P-139 | 由 P-139 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-01 | P-067、P-068 | 由 P-067、P-068 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-02 | P-067、P-068 | 由 P-067、P-068 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-03 | P-069、P-070 | 由 P-069、P-070 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-04 | P-069、P-070 | 由 P-069、P-070 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-05 | P-069、P-070 | 由 P-069、P-070 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-06 | P-069、P-070 | 由 P-069、P-070 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-07 | P-071、P-072 | 由 P-071、P-072 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-08 | P-071、P-072 | 由 P-071、P-072 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-09 | P-073、P-074 | 由 P-073、P-074 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-10 | P-073、P-074 | 由 P-073、P-074 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-11 | P-073、P-074 | 由 P-073、P-074 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-12 | P-073、P-074 | 由 P-073、P-074 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-13 | P-075、P-076 | 由 P-075、P-076 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-14 | P-075、P-076 | 由 P-075、P-076 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-15 | P-077、P-078 | 由 P-077、P-078 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-16 | P-077、P-078 | 由 P-077、P-078 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-17 | P-079、P-080 | 由 P-079、P-080 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-18 | P-079、P-080 | 由 P-079、P-080 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-19 | P-081、P-082 | 由 P-081、P-082 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-20 | P-081、P-082 | 由 P-081、P-082 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-21 | P-083、P-084 | 由 P-083、P-084 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-22 | P-083、P-084 | 由 P-083、P-084 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-23 | P-085、P-086 | 由 P-085、P-086 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-24 | P-085、P-086 | 由 P-085、P-086 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-25 | P-087、P-088 | 由 P-087、P-088 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-26 | P-087、P-088 | 由 P-087、P-088 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-27 | P-087、P-088 | 由 P-087、P-088 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-28 | P-087、P-088 | 由 P-087、P-088 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-29 | P-089、P-090 | 由 P-089、P-090 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-30 | P-089、P-090 | 由 P-089、P-090 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-31 | P-089、P-090 | 由 P-089、P-090 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-32 | P-089、P-090 | 由 P-089、P-090 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-33 | P-091、P-092 | 由 P-091、P-092 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-34 | P-091、P-092 | 由 P-091、P-092 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-35 | P-093、P-094 | 由 P-093、P-094 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-36 | P-093、P-094 | 由 P-093、P-094 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-37 | P-093、P-094 | 由 P-093、P-094 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-38 | P-093、P-094 | 由 P-093、P-094 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-39 | P-095、P-096 | 由 P-095、P-096 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-40 | P-095、P-096 | 由 P-095、P-096 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-41 | P-097、P-098 | 由 P-097、P-098 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-42 | P-097、P-098 | 由 P-097、P-098 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-43 | P-099、P-100 | 由 P-099、P-100 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-44 | P-099、P-100 | 由 P-099、P-100 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-45 | P-099、P-100 | 由 P-099、P-100 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-46 | P-099、P-100 | 由 P-099、P-100 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-47 | P-101、P-102 | 由 P-101、P-102 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-48 | P-101、P-102 | 由 P-101、P-102 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-49 | P-103、P-104 | 由 P-103、P-104 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-50 | P-103、P-104 | 由 P-103、P-104 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-51 | P-105、P-106 | 由 P-105、P-106 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-52 | P-105、P-106 | 由 P-105、P-106 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-53 | P-107、P-108 | 由 P-107、P-108 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-54 | P-107、P-108 | 由 P-107、P-108 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-55 | P-109、P-110 | 由 P-109、P-110 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-56 | P-109、P-110 | 由 P-109、P-110 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-57 | P-111、P-112 | 由 P-111、P-112 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-58 | P-111、P-112 | 由 P-111、P-112 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-59 | P-113、P-114 | 由 P-113、P-114 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-60 | P-113、P-114 | 由 P-113、P-114 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-61 | P-115、P-116 | 由 P-115、P-116 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T49-62 | P-115、P-116 | 由 P-115、P-116 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-01 | P-117、P-118 | 由 P-117、P-118 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-02 | P-117、P-118 | 由 P-117、P-118 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-03 | P-119、P-120 | 由 P-119、P-120 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-04 | P-119、P-120 | 由 P-119、P-120 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-05 | P-121、P-122 | 由 P-121、P-122 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-06 | P-121、P-122 | 由 P-121、P-122 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-07 | P-123、P-124 | 由 P-123、P-124 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-08 | P-123、P-124 | 由 P-123、P-124 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-09 | P-125、P-126 | 由 P-125、P-126 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-10 | P-125、P-126 | 由 P-125、P-126 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-11 | P-127、P-128 | 由 P-127、P-128 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-12 | P-127、P-128 | 由 P-127、P-128 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-13 | P-129、P-130 | 由 P-129、P-130 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-14 | P-129、P-130 | 由 P-129、P-130 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-15 | P-127、P-128 | 由 P-127、P-128 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-16 | P-127、P-128 | 由 P-127、P-128 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-17 | P-131、P-132 | 由 P-131、P-132 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-18 | P-131、P-132 | 由 P-131、P-132 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-19 | P-131、P-132 | 由 P-131、P-132 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-20 | P-131、P-132 | 由 P-131、P-132 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-21 | P-133、P-134 | 由 P-133、P-134 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-22 | P-133、P-134 | 由 P-133、P-134 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-23 | P-135、P-136 | 由 P-135、P-136 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-24 | P-135、P-136 | 由 P-135、P-136 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-25 | P-001、P-002 | 由 P-001、P-002 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-26 | P-001、P-002 | 由 P-001、P-002 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-27 | P-003、P-004 | 由 P-003、P-004 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-28 | P-003、P-004 | 由 P-003、P-004 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-29 | P-001、P-002 | 由 P-001、P-002 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-30 | P-001、P-002 | 由 P-001、P-002 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-31 | P-005、P-006 | 由 P-005、P-006 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-32 | P-005、P-006 | 由 P-005、P-006 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-33 | P-137、P-138 | 由 P-137、P-138 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T50-34 | P-137、P-138 | 由 P-137、P-138 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T51-01 | P-150、P-151 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-02 | P-150、P-151 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-03 | P-150、P-151 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-04 | P-150、P-151 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-05 | P-152 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-06 | P-171 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-07 | P-172 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-08 | P-173 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-09 | P-174 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-10 | P-175 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-11 | P-176 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-12 | P-177 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-13 | P-178 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-14 | P-179 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-15 | P-180 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-16 | P-181 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-17 | P-182 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-18 | P-183 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-19 | P-184 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-20 | P-185 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-21 | P-186 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-22 | P-187 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-23 | P-188 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-24 | P-189 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-25 | P-190 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-26 | P-191 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-27 | P-192 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-28 | P-193 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-29 | P-194 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-30 | P-195 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T51-31 | P-196 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T52-01 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-02 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-03 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-04 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-05 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-06 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-07 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-08 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-09 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-10 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T52-11 | P-153、P-154 | 由 P-153、P-154 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-01 | P-155、P-156 | 由 P-155、P-156 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-02 | P-155、P-156 | 由 P-155、P-156 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-03 | P-157、P-158 | 由 P-157、P-158 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-04 | P-157、P-158 | 由 P-157、P-158 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-05 | P-159、P-160 | 由 P-159、P-160 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-06 | P-159、P-160 | 由 P-159、P-160 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-07 | P-161、P-162 | 由 P-161、P-162 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-08 | P-161、P-162 | 由 P-161、P-162 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-09 | P-161、P-162 | 由 P-161、P-162 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-10 | P-163、P-164 | 由 P-163、P-164 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-11 | P-163、P-164 | 由 P-163、P-164 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-12 | P-163、P-164 | 由 P-163、P-164 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-13 | P-165、P-166 | 由 P-165、P-166 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T53-14 | P-165、P-166 | 由 P-165、P-166 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-01 | P-167、P-168 | 由 P-167、P-168 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-02 | P-167、P-168 | 由 P-167、P-168 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-03 | P-167、P-168 | 由 P-167、P-168 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-04 | P-167、P-168 | 由 P-167、P-168 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-05 | P-167、P-168 | 由 P-167、P-168 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-06 | P-167、P-168 | 由 P-167、P-168 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-07 | P-167、P-168 | 由 P-167、P-168 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-08 | P-167、P-168 | 由 P-167、P-168 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-20 | P-133、P-134 | 由 P-133、P-134 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-21 | P-127、P-128 | 由 P-127、P-128 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-22 | P-041、P-042 | 由 P-041、P-042 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-23 | P-037、P-038 | 由 P-037、P-038 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-24 | P-045、P-046 | 由 P-045、P-046 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-25 | P-035、P-036 | 由 P-035、P-036 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-26 | P-043、P-044 | 由 P-043、P-044 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-27 | P-047、P-048 | 由 P-047、P-048 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-28 | P-039、P-040 | 由 P-039、P-040 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-29 | P-049、P-050 | 由 P-049、P-050 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-30 | P-047、P-048 | 由 P-047、P-048 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-31 | P-039、P-040 | 由 P-039、P-040 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-32 | P-033、P-034 | 由 P-033、P-034 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-33 | P-051、P-052 | 由 P-051、P-052 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-34 | P-047、P-048 | 由 P-047、P-048 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-35 | P-059、P-060 | 由 P-059、P-060 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-36 | P-053、P-054 | 由 P-053、P-054 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-37 | P-135、P-136 | 由 P-135、P-136 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-38 | P-003、P-004 | 由 P-003、P-004 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-39 | P-001、P-002 | 由 P-001、P-002 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-40 | P-131、P-132 | 由 P-131、P-132 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-41 | P-013、P-014 | 由 P-013、P-014 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-42 | P-027、P-028 | 由 P-027、P-028 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-43 | P-007、P-008 | 由 P-007、P-008 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-44 | P-073、P-074 | 由 P-073、P-074 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-45 | P-073、P-074 | 由 P-073、P-074 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-46 | P-101、P-102 | 由 P-101、P-102 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-47 | P-103、P-104 | 由 P-103、P-104 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-48 | P-109、P-110 | 由 P-109、P-110 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-49 | P-095、P-096 | 由 P-095、P-096 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-50 | P-091、P-092 | 由 P-091、P-092 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-51 | P-089、P-090 | 由 P-089、P-090 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-52 | P-087、P-088 | 由 P-087、P-088 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-53 | P-067、P-068 | 由 P-067、P-068 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-54 | P-097、P-098 | 由 P-097、P-098 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-55 | P-099、P-100 | 由 P-099、P-100 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-56 | P-093、P-094 | 由 P-093、P-094 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-57 | P-117、P-118 | 由 P-117、P-118 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T54-58 | P-123、P-124 | 由 P-123、P-124 取代阶段性展示预期：采用第三阶段Lucide/Toast/共享组件最终状态，保留原业务、校验与边界 |
| T55-01 | P-007、P-008 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-02 | P-009、P-010 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-03 | P-011、P-012 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-04 | P-011、P-012 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-05 | P-013、P-014 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-06 | P-013、P-014 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-07 | P-025、P-026 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-08 | P-015、P-016 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-09 | P-017、P-018 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-10 | P-019、P-020 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-11 | P-027、P-028 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-12 | P-029、P-030 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-13 | P-027、P-028 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-14 | P-021、P-022、P-023、P-024、P-025、P-026 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T55-15 | P-031、P-032 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-01L | P-033、P-034 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-01D | P-033、P-034 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-02L | P-035、P-036 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-02D | P-035、P-036 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-03L | P-037、P-038 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-03D | P-037、P-038 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-04L | P-039、P-040 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-04D | P-039、P-040 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-05L | P-041、P-042 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-05D | P-041、P-042 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-06L | P-043、P-044 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-06D | P-043、P-044 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-07L | P-045、P-046 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-07D | P-045、P-046 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-08L | P-047、P-048 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-08D | P-047、P-048 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-09L | P-049、P-050 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-09D | P-049、P-050 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-10L | P-051、P-052 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-10D | P-051、P-052 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-11L | P-053、P-054 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-11D | P-053、P-054 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-12L | P-055、P-056 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-12D | P-055、P-056 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-13L | P-057、P-058 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-13D | P-057、P-058 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-14L | P-059、P-060 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-14D | P-059、P-060 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-15L | P-061、P-062 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-15D | P-061、P-062 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-16L | P-063、P-064 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-16D | P-063、P-064 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-17L | P-065、P-066 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-17D | P-065、P-066 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-18L | P-139 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T56-18D | P-139 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-01 | P-067、P-068 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-02 | P-067、P-068 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-03 | P-069、P-070 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-04 | P-069、P-070 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-05 | P-071、P-072 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-06 | P-071、P-072 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-07 | P-073、P-074 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-08 | P-073、P-074 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-09 | P-075、P-076 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-10 | P-075、P-076 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-11 | P-081、P-082 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-12 | P-081、P-082 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-13 | P-081、P-082 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-14 | P-081、P-082 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-15 | P-083、P-084 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-16 | P-083、P-084 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-17 | P-083、P-084 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-18 | P-083、P-084 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-19 | P-085、P-086 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-20 | P-085、P-086 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-21 | P-083、P-084 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-22 | P-083、P-084 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-23 | P-087、P-088 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-24 | P-087、P-088 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-25 | P-089、P-090 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-26 | P-089、P-090 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-27 | P-101、P-102 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-28 | P-101、P-102 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-29 | P-091、P-092 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-30 | P-091、P-092 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-31 | P-093、P-094 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-32 | P-093、P-094 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-33 | P-095、P-096 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-34 | P-095、P-096 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-35 | P-097、P-098 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-36 | P-097、P-098 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-37 | P-099、P-100 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-38 | P-099、P-100 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-39 | P-103、P-104 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-40 | P-103、P-104 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-41 | P-105、P-106 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-42 | P-105、P-106 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-43 | P-105、P-106 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-44 | P-105、P-106 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-45 | P-107、P-108 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-46 | P-107、P-108 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-47 | P-109、P-110 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-48 | P-109、P-110 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-49 | P-111、P-112 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-50 | P-111、P-112 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-51 | P-113、P-114 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-52 | P-113、P-114 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-53 | P-115、P-116 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T57-54 | P-115、P-116 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-01 | P-117、P-118 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-02 | P-117、P-118 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-03 | P-119、P-120 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-04 | P-119、P-120 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-05 | P-121、P-122 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-06 | P-121、P-122 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-07 | P-123、P-124 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-08 | P-123、P-124 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-09 | P-125、P-126 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-10 | P-125、P-126 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-11 | P-127、P-128 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-12 | P-127、P-128 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-13 | P-129、P-130 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-14 | P-129、P-130 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-15 | P-131、P-132 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-16 | P-131、P-132 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-17 | P-133、P-134 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-18 | P-133、P-134 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-19 | P-135、P-136 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-20 | P-135、P-136 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-21 | P-001、P-002 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-22 | P-001、P-002 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-23 | P-003、P-004 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-24 | P-003、P-004 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-25 | P-005、P-006 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-26 | P-005、P-006 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-27 | P-137、P-138 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T58-28 | P-137、P-138 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-01 | P-142、P-143 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-02 | P-142、P-143 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-03 | P-144、P-145 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-04 | P-144、P-145 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-05 | P-146、P-147 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-06 | P-146、P-147 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-07 | P-148、P-149 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-08 | P-148、P-149 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-09 | P-150、P-151 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-10 | P-150、P-151 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-11 | P-153、P-154 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-12 | P-153、P-154 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-13 | P-155、P-156、P-157、P-158、P-159、P-160、P-161、P-162、P-163、P-164、P-165、P-166 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-14 | P-155、P-156、P-157、P-158、P-159、P-160、P-161、P-162、P-163、P-164、P-165、P-166 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-15 | P-167、P-168 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-16 | P-167、P-168 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-17 | P-169、P-170 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-18 | P-169、P-170 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-19 | P-140、P-141 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-20 | P-140、P-141 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-21 | P-140、P-141 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T59-22 | P-140、P-141 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T60-01 | P-146、P-147 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T60-02 | P-146、P-147 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T60-03 | P-146、P-147 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T60-04 | P-148、P-149 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T60-05 | P-144、P-145 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T60-06 | P-148、P-149 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T60-07 | P-148、P-149 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
| T60-08 | P-146、P-147 | 合并同页功能及明暗视觉，原业务/边界继续覆盖 |
