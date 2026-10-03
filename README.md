# Critical Hit Reform · 暴击改革

Critical Hit Reform is a **NeoForge** mod for Minecraft that introduces **critical hit chance** and **critical hit damage** to weapons, bringing a game-style critical hit system into the game. This project is a **NeoForge port of *Modification of Critical Hit* (Fabric) by [WCBBEX](https://modrinth.com/user/WCBBEX)**, ported to Minecraft 1.21.1 by **Accidey**, with additional enhancements and refinements.

---

## English

### Overview

Critical Hit Reform adds two dedicated enchantments and a fully configurable critical hit system:

- **Probability Critical Hit** — increases the chance of landing a critical hit.
- **Critical Effect** — increases the damage dealt by critical hits.

Every attack has a default critical hit chance, which can be further increased by weapon-specific settings and enchantments. Critical hits deal multiplied weapon damage, and the system applies consistently to both melee attacks and projectiles.

**This version is ported by [Accidey] to Minecraft 1.21.1 on the NeoForge platform**, based on WCBBEX's original Fabric mod *Modification of Critical Hit*.

### Features

| Feature | Description |
| --- | --- |
| Default crit chance | Every attack has a base crit chance (5% by default), even without any enchantment or weapon configuration. |
| Two enchantments | *Probability Critical Hit* and *Critical Effect*, each with up to 5 levels. |
| Per-weapon configuration | Assign custom crit chance and crit damage to specific items via the config file or in-game commands. |
| Melee & ranged support | Bows, crossbows and other ranged weapons benefit as well; arrows and thrown projectiles inherit the crit stats of the weapon that launched them. |
| Item tooltips | Relevant weapons display their total crit chance and, when applicable, their crit damage percentage. |
| Acquisition control | Each enchantment can be individually enabled or disabled for the enchanting table, villager trades, and loot/fishing. |
| Disable vanilla jump crit | Optionally disable the vanilla falling/jump critical hit so that critical hits only occur through this mod's systems. |
| Bilingual | Fully localized in English and Simplified Chinese. |

### Mechanics

#### Critical Hit Chance

The total critical hit chance is calculated as follows:

```
total crit chance = default_crit_chance
                 + custom weapon crit chance (if configured)
                 + Probability Critical Hit level × chance_per_level
```

The result is capped at **100%**. By default, `default_crit_chance` is **5%**, and each level of *Probability Critical Hit* adds **20%**.

#### Critical Hit Damage

The critical hit damage multiplier is calculated as follows:

```
crit damage multiplier = (custom weapon crit damage | crit_effect.initial_damage)
                      + Critical Effect level × damage_per_level
```

If a weapon has a configured custom crit damage, that value is used as the base; otherwise the default base is **1.5** (150% of weapon damage). Each level of *Critical Effect* adds **0.2** (20%), so a level V critical hit deals **250%** of weapon damage.

| Critical Effect level | Crit damage |
| --- | --- |
| I | 170% |
| II | 190% |
| III | 210% |
| IV | 230% |
| V | 250% |

#### Enchantments

| Enchantment | Effect | Max level |
| --- | --- | --- |
| Probability Critical Hit | +20% crit chance per level | 5 |
| Critical Effect | +20% crit damage per level (base 150%) | 5 |

#### Supported Items

The enchantments apply to items in the `#criticalhit:crit_enchantable` tag, which includes vanilla `#minecraft:enchantable/weapon`, `#minecraft:enchantable/bow`, and `#minecraft:enchantable/crossbow`.

#### Item Tooltips

- The **crit chance line** is shown on weapons in the `crit_enchantable` tag, weapons with custom configuration, and weapons carrying a crit enchantment. The value is the total crit chance (including the default 5%) and is displayed without a `+` prefix.
- The **crit damage line** is shown only when a crit damage source exists (a *Critical Effect* enchantment above level 0, or a configured custom crit damage). It is displayed as a percentage (e.g., 150%, 250%).

### Configuration

The configuration file is located at `config/criticalhit-common.toml`. Key options include:

| Option | Description |
| --- | --- |
| `default_crit_chance` | Default crit chance for all attacks (0.05 = 5%). |
| `crit_chance.enable` | Enable or disable the *Probability Critical Hit* enchantment. |
| `crit_chance.chance_per_level` | Crit chance added per enchantment level (0.2 = 20%). |
| `crit_chance.max_level` | Maximum effective level of the enchantment. |
| `crit_effect.enable` | Enable or disable the *Critical Effect* enchantment. |
| `crit_effect.initial_damage` | Base crit damage multiplier (1.5 = 150%). |
| `crit_effect.damage_per_level` | Crit damage added per enchantment level (0.2 = +20%). |
| `crit_effect.max_level` | Maximum effective level of the enchantment. |
| `availability.*` | Control where each enchantment can be obtained (enchanting table, villager trades, loot/fishing). |
| `disable_vanilla_jump_crit` | Disable the vanilla falling/jump critical hit. |
| `custom_weapons` | Per-weapon crit settings, format: `item_id,crit_chance,crit_damage`. |

Some options (such as `crit_chance.enable`) require a game restart to take effect; changes to `availability.*` take effect after rejoining the world or running `/reload`.

Example `custom_weapons` entry:

```
minecraft:diamond_sword,0.3,1.3
```

This gives the diamond sword a 30% crit chance and +30% crit damage (critical hits deal 130% damage).

### Commands

| Command | Description |
| --- | --- |
| `/criticalhit chance <0~1>` | Set the held weapon's crit chance and write it to the configuration. |
| `/criticalhit damage <1~100>` | Set the held weapon's crit damage and write it to the configuration. |

Both commands require permission level 2 (operator).

### Requirements

- Minecraft **1.21.1**
- NeoForge **21.1.x**
- Java **21**

### Credits

**Original author:** [WCBBEX](https://modrinth.com/user/WCBBEX) — creator of *Modification of Critical Hit* (Fabric, Minecraft 1.19.3–1.20.4, MIT License).

**Port author:** Accidey — ported the mod to NeoForge for Minecraft 1.21.1 and extended it with additional features.

This mod is a **NeoForge port of [Modification of Critical Hit](https://modrinth.com/mod/modification-of-critical-hit)**. The original project is released under the **MIT License** (Copyright © 2024 WCBBEX), which permits use, copying, modification, merging, publication, distribution, sublicensing, and sale, provided that the original copyright notice and permission notice are retained. We sincerely thank the original author for creating and sharing this mod.

- Modrinth: https://modrinth.com/mod/modification-of-critical-hit
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/modification-of-critical-hit

---

## 中文

### 模组简介

暴击改革（Critical Hit Reform）是一款基于 **NeoForge** 的 Minecraft 模组，为武器引入**暴击率**与**暴击伤害**属性，让《我的世界》也能拥有类游戏化的暴击机制。本模组由 **Accidey** 移植自 **WCBBEX** 制作的 *Modification of Critical Hit*（Fabric 版），并在此基础上进行了功能增强与完善。

**本版本由 Accidey 移植至 Minecraft 1.21.1 的 NeoForge 平台**，原作是 WCBBEX 的 Fabric 模组 *Modification of Critical Hit*。

### 功能特性

| 功能 | 说明 |
| --- | --- |
| 默认暴击率 | 所有攻击都拥有基础暴击率（默认 5%），即使没有附魔或武器配置也同样生效。 |
| 两种附魔 | **概率暴击**与**暴击效果**，各拥有最高 5 级。 |
| 自定义武器 | 可通过配置文件或游戏内指令，为指定物品单独设置暴击率与暴击伤害。 |
| 近战与远程通用 | 弓、弩等远程武器同样生效；射出的箭矢与投掷物继承发射武器的暴击属性。 |
| 物品提示 | 相关武器会显示总暴击率；存在暴击伤害来源时还会显示暴击伤害百分比。 |
| 获取渠道控制 | 可分别控制每种附魔是否出现在附魔台、村民交易以及战利品/钓鱼中。 |
| 禁用原版跳跃暴击 | 可选项，用于禁用原版的跳跃下落暴击，使暴击仅通过本模组机制触发。 |
| 双语支持 | 完整支持英文与简体中文。 |

### 机制说明

#### 暴击率

总暴击率按以下方式计算：

```
总暴击率 = 默认暴击率
        + 自定义武器暴击率（若已配置）
        + 概率暴击附魔等级 × 每级暴击率
```

计算结果上限为 **100%**。默认情况下，`default_crit_chance` 为 **5%**，*概率暴击*附魔每级增加 **20%**。

#### 暴击伤害

暴击伤害倍率按以下方式计算：

```
暴击伤害倍率 = （自定义武器暴击伤害 或 crit_effect.initial_damage）
           + 暴击效果附魔等级 × 每级伤害加成
```

若武器已配置自定义暴击伤害，则以该值为基础；否则默认基础值为 **1.5**（即 150% 武器伤害）。*暴击效果*附魔每级增加 **0.2**（20%），满级 V 级时暴击造成 **250%** 武器伤害。

| 暴击效果等级 | 暴击伤害 |
| --- | --- |
| I | 170% |
| II | 190% |
| III | 210% |
| IV | 230% |
| V | 250% |

#### 附魔

| 附魔 | 效果 | 最高等级 |
| --- | --- | --- |
| 概率暴击 | 每级 +20% 暴击率 | 5 |
| 暴击效果 | 每级 +20% 暴击伤害（基础 150%） | 5 |

#### 适用物品

附魔适用于 `#criticalhit:crit_enchantable` 标签中的物品，包含原版 `#minecraft:enchantable/weapon`、`#minecraft:enchantable/bow` 与 `#minecraft:enchantable/crossbow`。

#### 物品提示

- **暴击率行**显示在属于 `crit_enchantable` 标签的武器、已配置自定义暴击的武器以及带有暴击附魔的武器上。数值为总暴击率（包含默认 5%），不带 `+` 前缀。
- **暴击伤害行**仅在存在暴击伤害来源时显示（*暴击效果*附魔等级大于 0，或已配置自定义暴击伤害），以百分比形式呈现（如 150%、250%）。

### 配置文件

配置文件位于 `config/criticalhit-common.toml`。主要选项如下：

| 选项 | 说明 |
| --- | --- |
| `default_crit_chance` | 所有攻击的默认暴击率（0.05 = 5%）。 |
| `crit_chance.enable` | 是否启用「概率暴击」附魔。 |
| `crit_chance.chance_per_level` | 附魔每级增加的暴击率（0.2 = 20%）。 |
| `crit_chance.max_level` | 附魔的生效等级上限。 |
| `crit_effect.enable` | 是否启用「暴击效果」附魔。 |
| `crit_effect.initial_damage` | 暴击基础伤害倍率（1.5 = 150%）。 |
| `crit_effect.damage_per_level` | 附魔每级增加的暴击伤害（0.2 = +20%）。 |
| `crit_effect.max_level` | 附魔的生效等级上限。 |
| `availability.*` | 控制每种附魔的获取渠道（附魔台、村民交易、战利品/钓鱼）。 |
| `disable_vanilla_jump_crit` | 是否禁用原版跳跃下落暴击。 |
| `custom_weapons` | 自定义武器暴击列表，格式：`物品ID,暴击率,暴击伤害`。 |

部分选项（如 `crit_chance.enable`）修改后需重启游戏生效；`availability.*` 相关改动在重进世界或执行 `/reload` 后生效。

`custom_weapons` 配置示例：

```
minecraft:diamond_sword,0.3,1.3
```

该条目表示钻石剑拥有 30% 暴击率，暴击伤害 +30%（暴击造成 130% 伤害）。

### 游戏内指令

| 指令 | 说明 |
| --- | --- |
| `/criticalhit chance <0~1>` | 将手持物品的暴击率写入配置。 |
| `/criticalhit damage <1~100>` | 将手持物品的暴击伤害写入配置。 |

两条指令均需要权限等级 2（管理员）。

### 运行环境

- Minecraft **1.21.1**
- NeoForge **21.1.x**
- Java **21**

### 致谢

**原作者：** [WCBBEX](https://modrinth.com/user/WCBBEX) —— *Modification of Critical Hit* 的作者（Fabric 平台，支持 Minecraft 1.19.3–1.20.4，MIT 许可证）。

**移植作者：** Accidey —— 将本模组移植至 NeoForge / Minecraft 1.21.1，并扩展了附加功能。

本模组是 [Modification of Critical Hit](https://modrinth.com/mod/modification-of-critical-hit) 的 **NeoForge 移植版**。原项目以 **MIT 许可证**发布（Copyright © 2024 WCBBEX），允许使用、复制、修改、合并、发布、分发、再许可与销售，但需保留原版权声明与许可声明。在此衷心感谢原作者创作并分享了这一模组。

- Modrinth：https://modrinth.com/mod/modification-of-critical-hit
- CurseForge：https://www.curseforge.com/minecraft/mc-mods/modification-of-critical-hit