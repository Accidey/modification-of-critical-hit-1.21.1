package com.xulai.criticalhit.data;

import com.xulai.criticalhit.CriticalHitMod;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.Optional;

public class CritAvailabilityPackSource {

    private static final String PACK_ID = "mod/criticalhit:availability";

    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }
        PackLocationInfo info = new PackLocationInfo(
                PACK_ID,
                Component.literal("CriticalHit Enchantment Availability"),
                PackSource.BUILT_IN,
                Optional.empty());
        Pack.ResourcesSupplier supplier = new Pack.ResourcesSupplier() {
            @Override
            public PackResources openPrimary(PackLocationInfo location) {
                return new CritAvailabilityPack(location, PackType.SERVER_DATA);
            }

            @Override
            public PackResources openFull(PackLocationInfo location, Pack.Metadata metadata) {
                return new CritAvailabilityPack(location, PackType.SERVER_DATA);
            }
        };
        event.addRepositorySource(packConsumer -> {
            Pack pack = Pack.readMetaAndCreate(info, supplier, PackType.SERVER_DATA,
                    new PackSelectionConfig(true, Pack.Position.BOTTOM, false));
            if (pack != null) {
                packConsumer.accept(pack);
            } else {
                CriticalHitMod.LOGGER.warn("[CriticalHit] Failed to create the enchantment availability data pack");
            }
        });
    }
}
