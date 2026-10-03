package com.xulai.criticalhit;

import com.mojang.logging.LogUtils;
import com.xulai.criticalhit.config.CritConfig;
import com.xulai.criticalhit.data.CritAvailabilityPackSource;
import com.xulai.criticalhit.handler.CritAttackHandler;
import com.xulai.criticalhit.handler.ProjectileCritHandler;
import com.xulai.criticalhit.command.CritCommands;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(CriticalHitMod.MODID)
public class CriticalHitMod {
    public static final String MODID = "criticalhit";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CriticalHitMod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CritConfig.SPEC);
        modEventBus.addListener(CritConfig::onConfigEvent);
        modEventBus.addListener(CritAvailabilityPackSource::onAddPackFinders);
        NeoForge.EVENT_BUS.register(CritAttackHandler.class);
        NeoForge.EVENT_BUS.register(ProjectileCritHandler.class);
        NeoForge.EVENT_BUS.register(CritCommands.class);
    }
}
