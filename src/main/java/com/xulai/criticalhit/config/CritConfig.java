package com.xulai.criticalhit.config;

import com.xulai.criticalhit.CriticalHitMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CritConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        DEFAULT_CHANCE = BUILDER
                .comment(
                        "默认暴击概率",
                        "Default crit chance",
                        "所有攻击的默认暴击概率，武器没有任何自定义暴击率与暴击附魔时同样生效。",
                        "Default crit chance for every attack, applies even when the weapon has no custom crit chance and no crit enchantment.",
                        "Default: 0.05 / 默认：0.05",
                        "Range: 0.0 ~ 1.0"
                )
                .defineInRange("default_crit_chance", 0.05, 0.0, 1.0);

        BUILDER.comment(" ");

        CHANCE_ENABLE = BUILDER
                .comment(
                        "启用概率暴击附魔",
                        "Enable the crit chance enchantment",
                        "是否启用【概率暴击】附魔；关闭后该附魔不再提供暴击率，也不再出现在任何获取渠道。",
                        "Whether the Crit Chance enchantment is enabled; when disabled it provides no crit chance and never appears in any source.",
                        "Default: true / 默认：true"
                )
                .define("crit_chance.enable", true);

        BUILDER.comment(" ");

        CHANCE_PER_LEVEL = BUILDER
                .comment(
                        "概率暴击每级暴击率",
                        "Crit chance per crit chance level",
                        "【概率暴击】附魔每级增加的暴击概率。",
                        "Crit chance added per level of the Crit Chance enchantment.",
                        "Default: 0.2 / 默认：0.2",
                        "Range: 0.0 ~ 1.0"
                )
                .defineInRange("crit_chance.chance_per_level", 0.2, 0.0, 1.0);

        BUILDER.comment(" ");

        CHANCE_MAX_LEVEL = BUILDER
                .comment(
                        "概率暴击附魔等级上限",
                        "Max crit chance enchantment level",
                        "【概率暴击】附魔的最高等级，影响附魔书、附魔台、村民交易与战利品中可出现的等级；不会超过附魔定义本身的 5 级。",
                        "Maximum level of the Crit Chance enchantment, affecting books, the enchanting table, villager trades and loot; never exceeds the definition's own cap of level 5.",
                        "修改后立刻生效，无需重启游戏。",
                        "Takes effect immediately, no restart required.",
                        "Default: 5 / 默认：5",
                        "Range: 1 ~ 255"
                )
                .defineInRange("crit_chance.max_level", 5, 1, 255);

        BUILDER.comment(" ");

        EFFECT_ENABLE = BUILDER
                .comment(
                        "启用暴击效果附魔",
                        "Enable the crit effect enchantment",
                        "是否启用【暴击效果】附魔；关闭后该附魔不再提供暴击伤害加成，也不再出现在任何获取渠道。",
                        "Whether the Crit Effect enchantment is enabled; when disabled it provides no crit damage bonus and never appears in any source.",
                        "Default: true / 默认：true"
                )
                .define("crit_effect.enable", true);

        BUILDER.comment(" ");

        EFFECT_INITIAL = BUILDER
                .comment(
                        "暴击基础伤害倍率",
                        "Base crit damage multiplier",
                        "暴击的基础伤害倍率，1.5 即暴击造成 150% 武器伤害，与原版暴击一致。",
                        "Base crit damage multiplier; 1.5 means crits deal 150% weapon damage, the vanilla value.",
                        "仅在武器没有自定义暴击伤害时作为基准，自定义武器的暴击伤害取自 custom_weapons。",
                        "Used only when the weapon has no custom crit damage; custom weapons take their value from custom_weapons.",
                        "物品提示里的暴击伤害百分比就是该倍率本身。",
                        "The crit damage percentage shown in the item tooltip is this multiplier.",
                        "Default: 1.5 / 默认：1.5",
                        "Range: 1.0 ~ 100.0"
                )
                .defineInRange("crit_effect.initial_damage", 1.5, 1.0, 100.0);

        BUILDER.comment(" ");

        EFFECT_PER_LEVEL = BUILDER
                .comment(
                        "暴击效果每级伤害倍率",
                        "Crit damage per crit effect level",
                        "【暴击效果】附魔每级在基础倍率上叠加的伤害倍率，0.2 即每级 +20% 武器伤害。",
                        "Crit damage multiplier added per level of the Crit Effect enchantment on top of the base value, 0.2 means +20% weapon damage per level.",
                        "默认数值下 I 级为 170%，V 级为 250%。",
                        "With the default values level I is 170% and level V is 250%.",
                        "Default: 0.2 / 默认：0.2",
                        "Range: 0.0 ~ 100.0"
                )
                .defineInRange("crit_effect.damage_per_level", 0.2, 0.0, 100.0);

        BUILDER.comment(" ");

        EFFECT_MAX_LEVEL = BUILDER
                .comment(
                        "暴击效果附魔等级上限",
                        "Max crit effect enchantment level",
                        "【暴击效果】附魔的最高等级，影响附魔书、附魔台、村民交易与战利品中可出现的等级；不会超过附魔定义本身的 5 级。",
                        "Maximum level of the Crit Effect enchantment, affecting books, the enchanting table, villager trades and loot; never exceeds the definition's own cap of level 5.",
                        "修改后立刻生效，无需重启游戏。",
                        "Takes effect immediately, no restart required.",
                        "Default: 5 / 默认：5",
                        "Range: 1 ~ 255"
                )
                .defineInRange("crit_effect.max_level", 5, 1, 255);

        BUILDER.comment(" ");

        CHANCE_IN_ENCHANTING_TABLE = BUILDER
                .comment(
                        "概率暴击附魔台渠道",
                        "Crit chance in enchanting table",
                        "【概率暴击】附魔是否可以出现在附魔台的候选里。",
                        "Whether the Crit Chance enchantment can show up in the enchanting table.",
                        "修改后需重进世界或执行 /reload 生效。",
                        "Requires rejoining the world or running /reload to take effect.",
                        "Default: true / 默认：true"
                )
                .define("availability.crit_chance.enchanting_table", true);

        BUILDER.comment(" ");

        CHANCE_IN_VILLAGER_TRADE = BUILDER
                .comment(
                        "概率暴击村民交易渠道",
                        "Crit chance in villager trades",
                        "【概率暴击】附魔是否可以出现在村民图书管理员的附魔书交易里。",
                        "Whether the Crit Chance enchantment can appear in librarian enchanted book trades.",
                        "修改后需重进世界或执行 /reload 生效。",
                        "Requires rejoining the world or running /reload to take effect.",
                        "Default: true / 默认：true"
                )
                .define("availability.crit_chance.villager_trade", true);

        BUILDER.comment(" ");

        CHANCE_IN_RANDOM_LOOT = BUILDER
                .comment(
                        "概率暴击战利品渠道",
                        "Crit chance in random loot",
                        "【概率暴击】附魔是否可以出现在奖励箱战利品与钓鱼获得的物品上。",
                        "Whether the Crit Chance enchantment can appear on loot chest items and fishing loot.",
                        "修改后需重进世界或执行 /reload 生效。",
                        "Requires rejoining the world or running /reload to take effect.",
                        "Default: true / 默认：true"
                )
                .define("availability.crit_chance.random_loot", true);

        BUILDER.comment(" ");

        EFFECT_IN_ENCHANTING_TABLE = BUILDER
                .comment(
                        "暴击效果附魔台渠道",
                        "Crit effect in enchanting table",
                        "【暴击效果】附魔是否可以出现在附魔台的候选里。",
                        "Whether the Crit Effect enchantment can show up in the enchanting table.",
                        "修改后需重进世界或执行 /reload 生效。",
                        "Requires rejoining the world or running /reload to take effect.",
                        "Default: true / 默认：true"
                )
                .define("availability.crit_effect.enchanting_table", true);

        BUILDER.comment(" ");

        EFFECT_IN_VILLAGER_TRADE = BUILDER
                .comment(
                        "暴击效果村民交易渠道",
                        "Crit effect in villager trades",
                        "【暴击效果】附魔是否可以出现在村民图书管理员的附魔书交易里。",
                        "Whether the Crit Effect enchantment can appear in librarian enchanted book trades.",
                        "修改后需重进世界或执行 /reload 生效。",
                        "Requires rejoining the world or running /reload to take effect.",
                        "Default: true / 默认：true"
                )
                .define("availability.crit_effect.villager_trade", true);

        BUILDER.comment(" ");

        EFFECT_IN_RANDOM_LOOT = BUILDER
                .comment(
                        "暴击效果战利品渠道",
                        "Crit effect in random loot",
                        "【暴击效果】附魔是否可以出现在奖励箱战利品与钓鱼获得的物品上。",
                        "Whether the Crit Effect enchantment can appear on loot chest items and fishing loot.",
                        "修改后需重进世界或执行 /reload 生效。",
                        "Requires rejoining the world or running /reload to take effect.",
                        "Default: true / 默认：true"
                )
                .define("availability.crit_effect.random_loot", true);

        BUILDER.comment(" ");

        DISABLE_VANILLA_JUMP_CRIT = BUILDER
                .comment(
                        "禁止原版跳跃暴击",
                        "Disable the vanilla jump crit",
                        "是否禁止原版的跳跃下落暴击；开启后只有自定义暴击率和附魔才能触发暴击。",
                        "Whether to disable the vanilla falling critical hit; when enabled only custom crit chance and enchantments can trigger crits.",
                        "Default: true / 默认：true"
                )
                .define("disable_vanilla_jump_crit", true);

        BUILDER.comment(" ");

        CUSTOM_WEAPONS = BUILDER
                .comment(
                        "自定义武器暴击列表",
                        "Custom weapon crit list",
                        "格式：物品ID,暴击率,暴击伤害倍率，例如 minecraft:diamond_sword,0.3,1.3。",
                        "Format: item_id,crit_chance,crit_damage, e.g. minecraft:diamond_sword,0.3,1.3.",
                        "暴击率取值 0.0 ~ 1.0，0.3 即 30%；暴击伤害倍率 = 1 + 加成，1.3 即暴击造成 130% 伤害。",
                        "crit_chance ranges from 0.0 to 1.0, 0.3 means 30%; crit_damage = 1 + bonus, 1.3 means crits deal 130% damage.",
                        "近战与远程均生效：弓、弩写入列表后，射出的箭矢与投掷物同样享受暴击率与暴击伤害加成。",
                        "Applies to melee and ranged alike: arrows and thrown projectiles fired with a listed bow or crossbow also benefit.",
                        "游戏内可用指令直接添加手持物品：/criticalhit chance <0~1> 与 /criticalhit damage <1~100>。",
                        "In game you can add the held item with /criticalhit chance <0~1> and /criticalhit damage <1~100>.",
                        "Default: [] / 默认：[]"
                )
                .defineListAllowEmpty(
                        "custom_weapons",
                        List.of(),
                        () -> "modid:item_id,0.3,1.5",
                        CritConfig::validateWeaponEntry);
    }

    public static final ModConfigSpec.DoubleValue DEFAULT_CHANCE;

    public static final ModConfigSpec.BooleanValue CHANCE_ENABLE;

    public static final ModConfigSpec.DoubleValue CHANCE_PER_LEVEL;

    public static final ModConfigSpec.IntValue CHANCE_MAX_LEVEL;

    public static final ModConfigSpec.BooleanValue EFFECT_ENABLE;

    public static final ModConfigSpec.DoubleValue EFFECT_INITIAL;

    public static final ModConfigSpec.DoubleValue EFFECT_PER_LEVEL;

    public static final ModConfigSpec.IntValue EFFECT_MAX_LEVEL;

    public static final ModConfigSpec.BooleanValue CHANCE_IN_ENCHANTING_TABLE;

    public static final ModConfigSpec.BooleanValue CHANCE_IN_VILLAGER_TRADE;

    public static final ModConfigSpec.BooleanValue CHANCE_IN_RANDOM_LOOT;

    public static final ModConfigSpec.BooleanValue EFFECT_IN_ENCHANTING_TABLE;

    public static final ModConfigSpec.BooleanValue EFFECT_IN_VILLAGER_TRADE;

    public static final ModConfigSpec.BooleanValue EFFECT_IN_RANDOM_LOOT;

    public static final ModConfigSpec.BooleanValue DISABLE_VANILLA_JUMP_CRIT;

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_WEAPONS;

    public static final ModConfigSpec SPEC = BUILDER.build();

    private static volatile Map<Item, WeaponCrit> customWeaponCache = Map.of();
    private static volatile double defaultChanceCache = 0.05;
    private static volatile boolean chanceEnableCache = true;
    private static volatile double chancePerLevelCache = 0.2;
    private static volatile int chanceMaxLevelCache = 5;
    private static volatile boolean effectEnableCache = true;
    private static volatile double effectInitialCache = 1.5;
    private static volatile double effectPerLevelCache = 0.2;
    private static volatile int effectMaxLevelCache = 5;
    private static volatile boolean disableVanillaJumpCritCache = true;
    private static volatile boolean chanceInTableCache = true;
    private static volatile boolean chanceInTradeCache = true;
    private static volatile boolean chanceInLootCache = true;
    private static volatile boolean effectInTableCache = true;
    private static volatile boolean effectInTradeCache = true;
    private static volatile boolean effectInLootCache = true;

    private static boolean validateWeaponEntry(Object obj) {
        if (!(obj instanceof String entry)) {
            return false;
        }
        String[] parts = entry.split(",");
        if (parts.length != 3) {
            return false;
        }
        if (ResourceLocation.tryParse(parts[0].trim()) == null) {
            return false;
        }
        try {
            double chance = Double.parseDouble(parts[1].trim());
            double damage = Double.parseDouble(parts[2].trim());
            return chance >= 0.0 && chance <= 1.0 && damage >= 1.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void onConfigEvent(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) {
            reload();
        }
    }

    public static void reload() {
        defaultChanceCache = DEFAULT_CHANCE.get();
        chanceEnableCache = CHANCE_ENABLE.get();
        chancePerLevelCache = CHANCE_PER_LEVEL.get();
        chanceMaxLevelCache = CHANCE_MAX_LEVEL.get();
        effectEnableCache = EFFECT_ENABLE.get();
        effectInitialCache = EFFECT_INITIAL.get();
        effectPerLevelCache = EFFECT_PER_LEVEL.get();
        effectMaxLevelCache = EFFECT_MAX_LEVEL.get();
        disableVanillaJumpCritCache = DISABLE_VANILLA_JUMP_CRIT.get();
        chanceInTableCache = CHANCE_IN_ENCHANTING_TABLE.get();
        chanceInTradeCache = CHANCE_IN_VILLAGER_TRADE.get();
        chanceInLootCache = CHANCE_IN_RANDOM_LOOT.get();
        effectInTableCache = EFFECT_IN_ENCHANTING_TABLE.get();
        effectInTradeCache = EFFECT_IN_VILLAGER_TRADE.get();
        effectInLootCache = EFFECT_IN_RANDOM_LOOT.get();

        Map<Item, WeaponCrit> map = new HashMap<>();
        for (String entry : CUSTOM_WEAPONS.get()) {
            String[] parts = entry.split(",");
            if (parts.length != 3) {
                CriticalHitMod.LOGGER.warn("[CriticalHit] Invalid weapon crit entry: {}", entry);
                continue;
            }
            ResourceLocation id = ResourceLocation.tryParse(parts[0].trim());
            if (id == null || !BuiltInRegistries.ITEM.containsKey(id)) {
                CriticalHitMod.LOGGER.warn("[CriticalHit] Unknown item id: {}", parts[0].trim());
                continue;
            }
            try {
                double chance = Double.parseDouble(parts[1].trim());
                double damage = Double.parseDouble(parts[2].trim());
                if (chance < 0.0 || chance > 1.0 || damage < 1.0) {
                    CriticalHitMod.LOGGER.warn("[CriticalHit] Out-of-range weapon crit entry: {}", entry);
                    continue;
                }
                map.put(BuiltInRegistries.ITEM.get(id), new WeaponCrit(chance, damage));
            } catch (NumberFormatException e) {
                CriticalHitMod.LOGGER.warn("[CriticalHit] Unparseable weapon crit entry: {}", entry);
            }
        }
        customWeaponCache = map;
        CriticalHitMod.LOGGER.info("[CriticalHit] Config loaded, custom crit weapons: {}", map.size());
    }

    public static boolean isChanceEnabled() {
        return chanceEnableCache;
    }

    public static double getDefaultChance() {
        return defaultChanceCache;
    }

    public static double getChancePerLevel() {
        return chancePerLevelCache;
    }

    public static int getChanceMaxLevel() {
        return chanceMaxLevelCache;
    }

    public static boolean isEffectEnabled() {
        return effectEnableCache;
    }

    public static double getEffectInitial() {
        return effectInitialCache;
    }

    public static double getEffectPerLevel() {
        return effectPerLevelCache;
    }

    public static int getEffectMaxLevel() {
        return effectMaxLevelCache;
    }

    public static void saveAndReload() {
        SPEC.save();
        reload();
    }

    public static boolean isVanillaJumpCritDisabled() {
        return disableVanillaJumpCritCache;
    }

    public static boolean isChanceInEnchantingTable() {
        return chanceEnableCache && chanceInTableCache;
    }

    public static boolean isChanceInVillagerTrade() {
        return chanceEnableCache && chanceInTradeCache;
    }

    public static boolean isChanceInRandomLoot() {
        return chanceEnableCache && chanceInLootCache;
    }

    public static boolean isEffectInEnchantingTable() {
        return effectEnableCache && effectInTableCache;
    }

    public static boolean isEffectInVillagerTrade() {
        return effectEnableCache && effectInTradeCache;
    }

    public static boolean isEffectInRandomLoot() {
        return effectEnableCache && effectInLootCache;
    }

    public static Map<Item, WeaponCrit> getCustomWeapons() {
        return customWeaponCache;
    }
}
