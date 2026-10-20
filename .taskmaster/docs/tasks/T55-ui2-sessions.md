# T55 界面对齐（第三阶段）：会话列表与会话详情——图标、Toast 与例外清理

> 先读 `.taskmaster/docs/CONVENTIONS.md`（**测试文档模式**）和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

| 字段 | 值 |
|---|---|
| 批次 / 层级 / 组 | UI1 / L4 / 组 E 页面接入（关系图：`.taskmaster/docs/graphs/UI1.md`） |
| 硬依赖 ← | T47（同目录第二阶段）、T51 图标、T52 Toast、T53 杂项组件、T60 缺口补齐（均已合入 sit） |
| 软依赖 ⇠ | 无 |
| 同层并行 | T55–T58 ⟂（按 feature 目录划分）；T59 可能仍在运行，冲突域不相交 |
| 下游 | 批次末真机测试 |
| 冲突域 | `A/feature/sessions/**`、`A/feature/sessiondetail/**`；对应 `R/values*/strings_*.xml`（只追加新文件） |
| 分支 / 基准 | `task/T55-ui2-sessions` / 派发时的 `sit` |
| 测试方式 | 测试文档模式：写 `.taskmaster/docs/tests/T55-ui2-sessions.md` |
| 优先级 | high |

## 目标
第二阶段（T47）已经把本范围的 M3 原件换成了设计系统组件，但按约定没有换图标、没有接 Toast，并留下了 `TODO(T52)`/`TODO(T53)` 和一批"例外"。本任务把这些全部收尾，使本范围的页面与网页端完全一致。

## 需求
1. **图标**：把本范围内所有 `Icons.*`（Material）换成 `Lucide.*`，对照 `.taskmaster/docs/icon-map.md`。同一语义优先用网页端对应页面实际使用的图标（在参考目录里 grep 确认）。尺寸跟网页端一致（行内图标 `size-4` = 16dp，按钮内图标同理），但触摸目标保持 ≥ 48dp。完成后本范围 `grep -rn "Icons\." ` 应为 0，例外要写明原因。
2. **Toast**：处理全部 `TODO(T52)`，用 `LocalCodegToaster.current` 的 `success`/`error`/`info`/`warning`/`loading`+`update`。时机和文案参照网页端同一操作的 `toast.` 调用（在参考目录里 grep `toast`）。只替换**临时**反馈（操作成功/失败提示）；页面上持续存在的错误状态（加载失败、空状态）保留 `InlineError`/`EmptyState`。toast 文案走字符串资源。
3. **T53 组件**：处理全部 `TODO(T53)`，换成 `CodegCollapsible`/`CodegInstantCollapsible`、`CodegInputGroup`、`CodegImagePreviewDialog`、`CodegCommandList`、`CodegAvatar`、`CodegFolderBadge`。
4. **第二阶段例外清理**：按 `.taskmaster/docs/tests/T47-ui-sessions.md` 的"例外/保留"小节，以及 `.taskmaster/docs/tests/T60-ds-gaps.md` 的"给 L4 的迁移清单"中属于本范围的条目，用 T60 新增的参数和重载替换：`OutlinedTextField` → `CodegTextField(enabled/keyboardActions/leading/trailing)`；不确定的 `LinearProgressIndicator` → `CodegProgress()`；`CircularProgressIndicator` → `CodegSpinner`（下拉刷新等系统控件除外）；用 `CodegDialog` 模拟的危险确认 → `CodegConfirmDialog(busy/confirmEnabled/content)`；按实体绑定的手写选择菜单 → `CodegSelect<T>`/`CodegCombobox<T>`；`CodegFilterChip` 调用统一到新签名（如果 T60 标了 `@Deprecated`）。
5. 不改业务逻辑与接口；可新增字符串（en + zh-rCN）。

## 改动范围
- 冲突域中列出的目录；对应 `R/values*/strings_*.xml`（只追加新文件）；测试文档 `.taskmaster/docs/tests/T55-ui2-sessions.md`
- 不改 `A/core/designsystem/**`；缺组件或参数先回报，不要自己加。

## 代码定位
- 本范围的 TODO：`grep -rn "TODO(T5[0-9])" <冲突域目录>`
- 本范围的 Material 图标：`grep -rn "Icons\." <冲突域目录>`
- 组件 API：`.taskmaster/docs/code-index/core-designsystem.md`（按符号查行号）；图标属性名：`A/core/designsystem/icon/LucideIcons*.kt`（只 grep 名字）
- 例外清单：`.taskmaster/docs/tests/T47-ui-sessions.md`、`.taskmaster/docs/tests/T60-ds-gaps.md`

## 参考（只读）
- `WEB/components/conversations/`、`WEB/components/message/`、`WEB/components/chat/`
- `WEB/components/ui/app-toaster.tsx`

## 验收
- 测试文档按模板写全：
  - 替换清单，分图标 / Toast / T53 组件 / 例外清理四部分（文件 → 替换前 → 替换后）；
  - grep 自查结果（`Icons.`、`TODO(T5x)`、例外列出的 M3 原件剩余数量及原因）；
  - 逐页面测试用例（入口 → 步骤 → 预期，明/暗各一，注明对照的网页端页面），每个 toast 至少一条触发用例；
  - 回归点：引用 `.taskmaster/docs/tests/T47-ui-sessions.md` 中的用例编号，说明哪些需要复测；
  - 自评编译风险。
