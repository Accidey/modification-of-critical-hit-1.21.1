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

    public static final ModConfigSpec.DoubleValue DEFAULT_CHANCE = BUILDER
            .comment(
                    "所有攻击的默认暴击概率，武器没有任何自定义暴击率与暴击附魔时同样生效（0.05 = 5%）",
                    "Default crit chance for every attack, applies even when the weapon has no custom crit chance and no crit enchantment (0.05 = 5%)")
            .defineInRange("default_crit_chance", 0.05, 0.0, 1.0);

    public static final ModConfigSpec.BooleanValue CHANCE_ENABLE = BUILDER
            .comment(
                    "是否启用【概率暴击】附魔（修改后需重启游戏生效）",
                    "Enable the Crit Chance enchantment (requires restart to take effect)")
            .define("crit_chance.enable", true);

    public static final ModConfigSpec.DoubleValue CHANCE_PER_LEVEL = BUILDER
            .comment(
                    "",
                    "概率暴击附魔每级增加的暴击率（0.2 = 20%）",
                    "Crit chance added per level of the Crit Chance enchantment (0.2 = 20%)")
            .defineInRange("crit_chance.chance_per_level", 0.2, 0.0, 1.0);

    public static final ModConfigSpec.IntValue CHANCE_MAX_LEVEL = BUILDER
            .comment(
                    "",
                    "概率暴击附魔的生效等级上限（附魔本身共 5 级，调低可限制生效等级）",
                    "Max effective level of the Crit Chance enchantment (the enchantment itself has 5 levels; lower this to cap the effect)")
            .defineInRange("crit_chance.max_level", 5, 1, 255);

    public static final ModConfigSpec.BooleanValue EFFECT_ENABLE = BUILDER
            .comment(
                    "",
                    "是否启用【暴击效果】附魔（修改后需重启游戏生效）",
                    "Enable the Crit Effect enchantment (requires restart to take effect)")
            .define("crit_effect.enable", true);

    public static final ModConfigSpec.DoubleValue EFFECT_INITIAL = BUILDER
            .comment(
                    "",
                    "暴击的基础伤害倍率（1.5 = 暴击造成 150% 武器伤害，即原版暴击倍率）",
                    "暴击效果附魔每级在其基础上叠加 damage_per_level（默认 1.5 + 每级 0.2，满级 5 级时暴击造成 250% 武器伤害）",
                    "物品提示里的暴击伤害百分比就是该倍率本身（默认 150%）",
                    "仅在武器没有自定义暴击伤害时作为基准；自定义武器的暴击伤害取自 custom_weapons",
                    "Base crit damage multiplier (1.5 = crits deal 150% weapon damage, the vanilla value)",
                    "Each level of the Crit Effect enchantment stacks damage_per_level on top (default 1.5 + 0.2/level, so level 5 crits deal 250% weapon damage)",
                    "The crit damage percentage shown in the item tooltip is this multiplier (150% by default)",
                    "Used only when the weapon has no custom crit damage; custom weapons take their value from custom_weapons")
            .defineInRange("crit_effect.initial_damage", 1.5, 1.0, 100.0);

    public static final ModConfigSpec.DoubleValue EFFECT_PER_LEVEL = BUILDER
            .comment(
                    "",
                    "暴击效果附魔每级增加的暴击伤害倍率（0.2 = 每级 +20% 武器伤害，满级 5 级共 +100%）",
                    "Crit damage multiplier added per level of the Crit Effect enchantment (0.2 = +20% weapon damage per level, +100% total at level 5)")
            .defineInRange("crit_effect.damage_per_level", 0.2, 0.0, 100.0);

    public static final ModConfigSpec.IntValue EFFECT_MAX_LEVEL = BUILDER
            .comment(
                    "",
                    "暴击效果附魔的生效等级上限（附魔本身共 5 级，调低可限制生效等级）",
                    "Max effective level of the Crit Effect enchantment (the enchantment itself has 5 levels; lower this to cap the effect)")
            .defineInRange("crit_effect.max_level", 5, 1, 255);

    public static final ModConfigSpec.BooleanValue CHANCE_IN_ENCHANTING_TABLE = BUILDER
            .comment(
                    "",
                    "【概率暴击】附魔是否可以出现在附魔台的候选里（修改后需重进世界或执行 /reload 生效）",
                    "Whether the Crit Chance enchantment can show up in the enchanting table (requires rejoining the world or /reload)")
            .define("availability.crit_chance.enchanting_table", true);

    public static final ModConfigSpec.BooleanValue CHANCE_IN_VILLAGER_TRADE = BUILDER
            .comment(
                    "",
                    "【概率暴击】附魔是否可以出现在村民（图书管理员）的附魔书交易里（修改后需重进世界或执行 /reload 生效）",
                    "Whether the Crit Chance enchantment can appear in villager enchanted book trades (requires rejoining the world or /reload)")
            .define("availability.crit_chance.villager_trade", true);

    public static final ModConfigSpec.BooleanValue CHANCE_IN_RANDOM_LOOT = BUILDER
            .comment(
                    "",
                    "【概率暴击】附魔是否可以出现在奖励箱战利品与钓鱼获得的物品上（修改后需重进世界或执行 /reload 生效）",
                    "Whether the Crit Chance enchantment can appear on loot chest items and fishing loot (requires rejoining the world or /reload)")
            .define("availability.crit_chance.random_loot", true);

    public static final ModConfigSpec.BooleanValue EFFECT_IN_ENCHANTING_TABLE = BUILDER
            .comment(
                    "",
                    "【暴击效果】附魔是否可以出现在附魔台的候选里（修改后需重进世界或执行 /reload 生效）",
                    "Whether the Crit Effect enchantment can show up in the enchanting table (requires rejoining the world or /reload)")
            .define("availability.crit_effect.enchanting_table", true);

    public static final ModConfigSpec.BooleanValue EFFECT_IN_VILLAGER_TRADE = BUILDER
            .comment(
                    "",
                    "【暴击效果】附魔是否可以出现在村民（图书管理员）的附魔书交易里（修改后需重进世界或执行 /reload 生效）",
                    "Whether the Crit Effect enchantment can appear in villager enchanted book trades (requires rejoining the world or /reload)")
            .define("availability.crit_effect.villager_trade", true);

    public static final ModConfigSpec.BooleanValue EFFECT_IN_RANDOM_LOOT = BUILDER
            .comment(
                    "",
                    "【暴击效果】附魔是否可以出现在奖励箱战利品与钓鱼获得的物品上（修改后需重进世界或执行 /reload 生效）",
                    "Whether the Crit Effect enchantment can appear on loot chest items and fishing loot (requires rejoining the world or /reload)")
            .define("availability.crit_effect.random_loot", true);

    public static final ModConfigSpec.BooleanValue DISABLE_VANILLA_JUMP_CRIT = BUILDER
            .comment(
                    "",
                    "是否禁止原版的跳跃下落暴击（true = 只有自定义暴击率和附魔才能触发暴击）",
                    "Whether to disable the vanilla falling/jump critical hit (true = crits can only be triggered by custom crit chance and enchantments)")
            .define("disable_vanilla_jump_crit", true);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> CUSTOM_WEAPONS = BUILDER
            .comment(
                    "",
                    "自定义武器暴击列表，格式：物品ID,暴击率,暴击伤害倍率",
                    "暴击率取值 0.0~1.0（0.3 = 30%）；暴击伤害倍率 = 1 + 加成，例如 1.3 表示暴击造成130%伤害（即+30%）",
                    "示例：minecraft:diamond_sword,0.3,1.3 表示钻石剑拥有30%暴击率，暴击伤害+30%",
                    "近战与远程均生效：弓、弩等远程武器写在列表里后，射出的箭矢、投掷物同样享受暴击率与暴击伤害加成",
                    "游戏内可用指令直接添加手持物品：/criticalhit chance <0~1> 与 /criticalhit damage <1~100>",
                    "Custom weapon crit list, format: item_id,crit_chance,crit_damage",
                    "crit_chance ranges from 0.0 to 1.0 (0.3 = 30%); crit_damage = 1 + bonus, e.g. 1.3 means crits deal 130% damage (+30%)",
                    "Example: minecraft:diamond_sword,0.3,1.3 gives the diamond sword 30% crit chance and +30% crit damage",
                    "Applies to both melee and ranged: arrows and thrown projectiles fired with a listed bow/crossbow also benefit from crit chance and crit damage",
                    "In game you can add the held item with: /criticalhit chance <0~1> and /criticalhit damage <1~100>")
            .defineListAllowEmpty(
                    "custom_weapons",
                    List.of(),
                    () -> "modid:item_id,0.3,1.5",
                    CritConfig::validateWeaponEntry);

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
