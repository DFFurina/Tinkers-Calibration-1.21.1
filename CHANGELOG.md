# 更新日志 / Changelog

本文件记录 1.21.1（NeoForge）分支的更新内容；标题中的版本号与 `gradle.properties` 中的 `mod_version` 一致，
完整格式为 `1.21.1-<版本>`。1.20.1 及更早版本的更新记录请见上游仓库
[TinkersCalibration](https://github.com/Jamesdsj/TinkersCalibration)。

## 1.2.5a（NeoForge 1.21.1）

### 🔧 兼容性修复
- **修复与 More Resilient Tinkers（`more_resilient_tinkers`）共存时，自动修复特性会不断消耗工具最高耐久的问题**：
  该模组在 `ToolDamageUtil.repair` 的头部注入逻辑，每次修复武器或盔甲都会累加「最高耐久的 3~8% 加 10~25 点固定值」
  的永久耐久损失（记在持久化数据 `more_resilient_tinkers:max_durability_lost` 上，并在重建属性时扣减最高耐久）。
  该机制本意是让工作台修复有所代价，但「修复苔藓」每 5 tick 就会自动修复一次，于是最高耐久被持续扣光。
  现在自动修复会先记下修复前的累计值、修复后还原并重建属性，只抵消本次自动修复产生的损耗；
  工作台修复与使用磨损造成的损失保持不变，未安装该模组时行为完全不变。
- **同类问题一并处理**：除「修复苔藓」外，「雨后春笋」（含盔甲版本）、「冰冷」、「月之力量」的自动修复
  也统一走新增的 `library/ToolRepairHelper`，不再被上述磨损机制反复扣除最高耐久。

## 1.2.4a（NeoForge 1.21.1）

### 🔧 严重修复
- **修复在创造模式物品栏搜索物品时游戏崩溃的问题**：崩溃堆栈为 `Cannot invoke "LivingEntity.getEffect(...)" because "entity" is null`（`VibratingModifier` 第 58 行）。
  搜索界面渲染工具提示时是**没有玩家实体**的（`player == null`），而「振动」强化的提示逻辑在判空前就去读取玩家身上的状态效果，
  而匠魂的 `TinkerEffect.getAmplifier(entity, effect)` 内部不做空检查，于是直接空指针崩溃。
  现已改为**先判空再取加成**，并在加成计算函数内部做空实体保护（无实体时返回 0，不显示该提示）。
- **同类问题一并修复**：同为“读取实体状态效果计算加成”的强化在相同路径上也会崩溃，现已全部加固：
  「嫉妒（Envy）」「嗜血（Bloodthirsty）」「无懈可击（Impregnable）」的工具提示，以及「无懈可击」在无实体持有时
  计算耐久减免的路径（`onDamageTool` 的 `holder` 参数本身允许为 null）。

### 🧹 代码清理
- 删除移植过程中遗留的空判断与注释代码（`VibratingModifier` 中已无作用的 `if (arrow != null) {}`、
  `GlobalTravellerModifier` 末尾整段注释掉的旧版 `addTooltip` 实现）及无用的重复 import。

## 1.2.3a（NeoForge 1.21.1）

### 🔧 修复
- **修复「苔藓修复」强化耐久不实时显示的问题**：修复后重新写入数据组件，使客户端能实时同步修复状态（此前只有重新加载物品才更新）
- **修复弓箭蓄力准星框与实际蓄力不一致的问题**：HUD 改用实际蓄力时间（拉弓时写入的 drawtime），不再使用旧版公式，准星不再提前显示满蓄力；松手时机与实际威力一致，箭矢弹道随之恢复正常

## 1.2.2a（NeoForge 1.21.1）

### 🔧 严重修复
- **修复所有工具无法挖掘方块的问题**：晶洞模块（冰岛晶石/月光石）的注册类未被类加载触发，导致 12 个晶体方块未注册，`minecraft:mineable/pickaxe` 标签加载失败，进而使全部工具的挖掘方块表为空
- **修复手持弓箭蓄力时快捷栏变色的问题**：蓄力 HUD 修改渲染混合模式后未恢复，污染了后续 GUI 层渲染

### 🧹 跨模组依赖清理（不再强依赖其他模组）
- 为约 76 个引用 blue_skies / upgradednetherite / mna / twilightforest / projecte 的配方添加 `neoforge:mod_loaded` 条件，对应模组缺失时自动跳过，不再报错
- 删除 15 个引用不存在物品的孤儿配方/战利品表（sunstone、moonstone、prehnite、moonsteel、ichor 弹弓等）
- `tconstruct:wood_variants/planks|logs` 标签中 blue_skies 条目改为可选（`required: false`），blue_skies 缺失时不再导致标签加载失败
- 修正战利品表与配方中 `wheat_rod` 拼写错误为 `hard_wheat_rod`

### 💧 流体修复
- 修正熔化配方引用未注册流体的问题：`moltenwither` → `moltenwitherium`、`refinedquartz` → `moltenrefinedquartz`
- 全量核对 54 个流体，无其他缺失引用

### 🌐 翻译补全
- 补充硝石/滑石的矿石与矿物块中文名称（硝石矿石、深层硝石矿石、硝石块、滑石矿石、深层滑石矿石、滑石块）
- 补充强化「月光之力」（Moon Power）的中文名称、风味与描述
- 英文语言文件同步补全

## 1.2.1a（NeoForge 1.21.1）

### 🔧 修复
- 修复材料覆盖问题（mining_tier 等）
- 修复苔藓修复等强化在 1.21.1 中失效的问题（`max_level` 字段迁移）

## 1.2.0a（NeoForge 1.21.1）

### ✨ 新增
- 首个 1.21.1 NeoForge 移植版本：将 Tinkers Calibration 从 Minecraft 1.20.1（Forge）迁移至 NeoForge 1.21.1，
  强化（工具 / 盔甲）改写为匠魂 3.12.6 的 `Modifier` 钩子体系，配方、标签、材料与晶洞世界生成改为数据驱动，
  并同步整理中英文语言文件。
