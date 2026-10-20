# T40 自定义技能管理

> 先读 `.taskmaster/docs/CONVENTIONS.md`（通用约定、路径简写、防冲突规则）。只读本文件列出的代码位置。

- 优先级：low　依赖：无　可并行：与 T36–T39 并行
- 分支：`task/T40-custom-skills`

## 目标
与网页端"自定义技能"一致：在 codeg 中央库里创建、编辑、复制、删除技能，并关联到各 agent。

## 需求
- 入口：设置 → 技能 页顶部加"自定义技能"按钮，打开全屏页/Sheet。
- 列表 `custom_list` + `custom_list_all_install_statuses`（每个技能在各 agent 的关联状态）。
- 查看/编辑 `custom_read_skill` / `custom_save_skill`（等宽编辑 SKILL.md 内容）；新建 `custom_create_skill`；复制 `custom_duplicate_skill`；删除 `custom_delete_skills`（确认）。
- 关联：每个技能一组 agent 开关，提交用 `custom_apply_links`。
- 从 agent 导入 `custom_import_from_agent`（选 agent 后列出可导入项）；`custom_import_skill`（按路径/URL 导入）可选。

## 改动范围
- 修改：`A/feature/settings/SkillsScreen.kt`（只加入口按钮与页面显示）
- 新建：`A/feature/settings/customskills/` 下文件
- 修改：`A/core/network/CodegClient.kt`（新增 `// region custom-skills`，放在 `// region Transport` 之前）
- 新建：`A/core/model/wire/CustomSkillRequests.kt`、`A/core/model/CustomSkill.kt`
- 新建：`R/values/strings_custom_skills.xml`、`R/values-zh-rCN/strings_custom_skills.xml`
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 服务端（`SRV/handlers/custom_skills.rs`）：list `:57`；list_all_install_statuses `:64`；apply_links `:72` · `CustomApplyLinksParams :18`；read `:81` · `CustomIdParams :12`；create `:90` / save `:99` · `CustomContentParams :24`；duplicate `:108` · `CustomDuplicateParams :31`；import `:117` · `CustomImportParams :38`；import_from_agent `:126` · `CustomImportFromAgentParams :46`；delete `:136` · `CustomDeleteParams :53`。
- app：`A/feature/settings/SkillsScreen.kt:90 SkillsContent`、`:287 SkillEditorDialog`（编辑器写法）。

## 参考（只读，按需打开）
- `WEB/components/settings/custom-skills-settings.tsx`、`skill-agent-matrix.tsx`

## 验收
- 测试服务端上：新建技能 → 编辑保存 → 关联到 Codex → 复制 → 删除两个。
- CI 通过。
