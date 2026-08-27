# 更新日志 / Changelog

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

---

## 1.2.1a（NeoForge 1.21.1）

- 修复材料覆盖问题（mining_tier 等）
- 修复苔藓修复等强化在 1.21.1 中失效的问题（`max_level` 字段迁移）
