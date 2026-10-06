# Tinkers Calibration 1.21.1

这是一个面向 **Minecraft 1.21.1** 与 **NeoForge** 的 Tinkers Calibration（匠魂校准）移植项目。
模组在匠魂的基础上新增大量工具 / 盔甲强化、材料、状态效果与矿石世界生成，并对部分原有内容做了「校准」式调整。

## 项目信息

- Mod ID：`tinkerscalibration`
- 游戏版本：`Minecraft 1.21.1`
- 模组加载器：`NeoForge`（`21.1.x`）
- 当前模组版本：`1.2.5a`
- Java 版本：`JDK 21`
- 作者：`James(dddddsj)`、`BeeNotSting`
- 许可证：`MIT`
- 必需依赖：`Mantle 1.12.5` 和 `Tinkers' Construct 3.12.6` 或兼容版本
- 可选依赖：`Twilight Forest`、`ProjectE`

> 项目仍在移植与适配阶段，部分功能、兼容性或游戏内文档可能尚未完全实现。

## 内容一览

| 内容 | 数量 | 说明 |
| --- | --- | --- |
| 工具强化 | 138 | 普通强化、能力强化与无槽强化 |
| 盔甲强化 | 56 | 多数工具强化都提供对应的盔甲版本 |
| 状态效果 | 9 | 嗜血、嫉妒、冲刺、狮心、沉重、涡流、失衡等 |
| 材料定义 | 75 | 新增材料与对匠魂原有材料的校准（含等价交换、暮色森林材料） |
| 配方 | 747 | 工匠站 / 工匠砧、熔化、合金、部件与铸造配方 |
| 矿石世界生成 | 11 种 | 刚玉、钛、尖晶石、电气石、振动水晶等（配置 + 放置共 22 个文件） |
| 语言文件 | 2 | `en_us` 与 `zh_cn` |

### 主要特性

- **强化体系**：工具与盔甲强化统一走匠魂 3 的 `Modifier` 钩子体系（`InventoryTickModifierHook`、`ModifierHooks` 等），
  数值与行为沿用 1.20.1 版；能力强化与无槽强化分别位于 `recipe/tools/modifiers/ability`、`recipe/tools/modifiers/slotless`。
- **自定义挖掘等级**：新增暗物质（Dark Matter）与红物质（Red Matter）挖掘等级，配合 `ProjectE` 使用。
- **状态效果**：为部分强化注册了独立状态效果（嗜血、嫉妒、冲刺、失衡、涡流、沉重、狮心等）。
- **HUD**：新增「超限护盾」与「远程蓄力」两个 HUD 模块。
- **可选模组联动**：暮色森林（`carminite` 等材料）与等价交换（暗物质 / 红物质）的配方使用 `neoforge:mod_loaded` 条件，
  对应模组缺失时自动跳过，不会导致加载失败。

## 安装

1. 安装与 Minecraft `1.21.1` 匹配的 NeoForge。
2. 将本模组和 Mantle 放入游戏实例的 `mods` 文件夹。
3. 额外安装可选依赖以畅玩该mod的功能。

## 开发构建

请先安装 JDK 21，并在 `libs/` 中放入依赖 jar（`TinkersConstruct-1.21.1-3.12.6.jar`、`Mantle-1.21.1-1.12.5.jar`），
然后在项目根目录执行：

```powershell
.\gradlew.bat build
```

构建产物位于 `build/libs/`。

## 数据驱动

材料、强化、配方、标签与矿石世界生成全部位于 `src/main/resources/data/tinkerscalibration/`（共 1100+ 个数据文件），
可被数据包覆盖或扩展；配方通过 `neoforge:mod_loaded` 与标签条件实现可选依赖兼容。

## 上游项目

- Mantle：https://github.com/SlimeKnights/Mantle
- Tinkers' Construct：https://github.com/SlimeKnights/TinkersConstruct
- Mantle 1.21.1：https://github.com/zhuchuovo/TinkersConstruct-1.21.1
- Tinkers' Construct 1.21.1：https://github.com/zhuchuovo/TinkersConstruct-1.21.1
- Tinkers Calibration：https://github.com/Jamesdsj/TinkersCalibration
- Twilight Forest：https://github.com/TeamTwilight/twilightforest
- ProjectE：https://github.com/sinkillerj/ProjectE

## 问题反馈

- 本仓库：https://github.com/DFFurina/Tinkers-Calibration-1.21.1
- 问题反馈：https://github.com/DFFurina/Tinkers-Calibration-1.21.1/issues

本项目的创作过程中使用了 AI 帮助，若发现 bug 或平衡性问题，欢迎提交 issue。谢谢！

## 更新日志

见 [CHANGELOG.md](CHANGELOG.md)。

## 许可证

本项目遵循仓库内 [LICENSE](LICENSE) 文件所列的 MIT 许可证。
