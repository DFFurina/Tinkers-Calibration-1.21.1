# Tinkers Calibration 1.21.1

这是一个面向 **Minecraft 1.21.1** 与 **NeoForge** 的 Tinkers Calibration（匠魂校准）移植项目。

## 项目信息

- Mod ID：`tinkerscalibration`
- 游戏版本：`Minecraft 1.21.1`
- 模组加载器：`NeoForge`
- 当前模组版本：`1.2.0a`
- Java 版本：`JDK 21`
- 必需依赖：`Mantle 1.12.4` 和 `Tinkers' Construct 3.12.6` 或兼容版本
- 可选依赖：`Twilight Forest`、`ProjectE`

> 项目仍在移植与适配阶段，部分功能、兼容性或游戏内文档可能尚未完全实现。

## 安装

1. 安装与 Minecraft `1.21.1` 匹配的 NeoForge。
2. 将本模组和 Mantle 放入游戏实例的 `mods` 文件夹。
3. 额外安装可选依赖以畅玩该mod的功能。

## 开发构建

请先安装 JDK 21，然后在项目根目录执行：

```powershell
.\gradlew.bat build
```

构建产物位于 `build/libs/`。

## 上游项目

- Mantle：https://github.com/SlimeKnights/Mantle
- Tinkers' Construct：https://github.com/SlimeKnights/TinkersConstruct
- Mantle 1.21.1：https://github.com/zhuchuovo/TinkersConstruct-1.21.1
- Tinkers' Construct 1.21.1：https://github.com/zhuchuovo/TinkersConstruct-1.21.1
- Tinkers Calibration：https://github.com/Jamesdsj/TinkersCalibration
- Twilight Forest：https://github.com/TeamTwilight/twilightforest
- ProjectE：https://github.com/sinkillerj/ProjectE

## 许可证

本项目遵循仓库内 [LICENSE](LICENSE) 文件所列的 MIT 许可证。
