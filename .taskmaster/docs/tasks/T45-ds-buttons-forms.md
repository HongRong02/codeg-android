# T45 设计系统：按钮与表单控件

> 先读 `.taskmaster/docs/CONVENTIONS.md` 和 `.taskmaster/docs/ui-alignment.md`。只读本文件列出的代码位置。

- 优先级：high　依赖：无　可并行：T43–T46 互相并行；T47–T50 依赖本任务
- 分支：`task/T45-ds-buttons-forms`

## 目标
第一阶段：在设计系统中提供与网页端一致的组件，供第二阶段各页面替换使用。

## 需求
扩展 `Buttons.kt`：`CodegButton(onClick, variant, size, enabled, loading, leadingIcon, content)`，variant = Default(`--primary`)/Secondary(`--secondary`)/Outline(`border --input` 背景 `--background`，暗色 `--input/30`)/Ghost(按下 `--accent`)/Destructive(`--destructive`，暗色 `/60`)/Link；size = Default(h-9 px-4)/Sm(h-8 px-3)/Lg(h-10 px-6)/Icon(36dp 方)；圆角 `rounded-md`，字 `text-sm font-medium`。现有 `PrimaryButton`/`SecondaryButton` 等改为内部调用 `CodegButton`，签名不变。扩展 `TextFields.kt`：`CodegTextField` 补 `singleLine=false` 的 textarea 变体（min-h-16）、`isError`、`supportingText`；新增 `CodegField(label, description, error, content)`。新建 `Controls.kt`：`CodegSelect(label, value, options, onSelect)`（trigger 样式同 input + 右侧 chevron，弹出用 `CodegDropdownMenu` 风格的自带实现，不要依赖 T43 的文件）、`CodegCombobox`（可搜索）、`CodegRadioGroup`、`CodegCheckbox`（16dp `rounded-[4px]`，选中 `--primary`）、`CodegSwitch`（h-[1.15rem] w-8，选中 `--primary`，未选 `--input`）。
- 每个组件提供 `@Preview`（明/暗）；文件内提供 `internal fun T45Gallery()` 汇总所有变体。
- 不改任何 `A/feature/**` 文件。

## 改动范围
- `A/core/designsystem/component/Buttons.kt`、`TextFields.kt`（扩展，保持现有签名）、`Controls.kt`（新建）；`strings_ds_controls.xml` + zh
- 范围外文件一律不改；确需改动先回报。

## 代码定位
- 主题：`A/core/designsystem/theme/WebThemes.kt`（`WebTokens`）、`Color.kt`（`CodegColors`、`withOpacity`）、`CodegTheme.kt`、`CodegShapes`（按 code-index `core-designsystem.md` 查）
- 现有组件：`.taskmaster/docs/code-index/core-designsystem.md`

## 参考（只读）
- `WEB/components/ui/button.tsx`、`button-group.tsx`、`input.tsx`、`textarea.tsx`、`field.tsx`、`label.tsx`、`select.tsx`、`combobox.tsx`、`checkbox.tsx`、`switch.tsx`、`radio-group.tsx`
- `WEB/app/globals.css`（token 与圆角）

## 验收
- `./gradlew :app:check` 通过（CI verify.yml）；报告里附组件清单与签名。
