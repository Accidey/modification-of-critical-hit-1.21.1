package com.xulai.criticalhit.data;

import com.xulai.criticalhit.config.CritConfig;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CritAvailabilityPack extends AbstractPackResources {

    private static final String CHANCE_ID = "criticalhit:crit_chance";
    private static final String EFFECT_ID = "criticalhit:crit_effect";

    private final Map<ResourceLocation, byte[]> tagFiles;
    private final byte[] packMeta;

    public CritAvailabilityPack(PackLocationInfo location, PackType type) {
        super(location);
        this.packMeta = ("{\"pack\":{\"pack_format\":"
                        + SharedConstants.getCurrentVersion().getPackVersion(type)
                        + ",\"description\":\"CriticalHit enchantment availability\"}}")
                .getBytes(StandardCharsets.UTF_8);
        this.tagFiles = buildTagFiles();
        tagFiles.forEach((file, bytes) -> com.xulai.criticalhit.CriticalHitMod.LOGGER.info(
                "[CriticalHit] availability {} -> {}", file.getPath(), new String(bytes, StandardCharsets.UTF_8)));
    }

    private static Map<ResourceLocation, byte[]> buildTagFiles() {
        Map<ResourceLocation, byte[]> map = new HashMap<>();
        map.put(tagPath("in_enchanting_table"),
                tagJson(CritConfig.isChanceInEnchantingTable(), CritConfig.isEffectInEnchantingTable()));
        map.put(tagPath("tradeable"),
                tagJson(CritConfig.isChanceInVillagerTrade(), CritConfig.isEffectInVillagerTrade()));
        map.put(tagPath("on_random_loot"),
                tagJson(CritConfig.isChanceInRandomLoot(), CritConfig.isEffectInRandomLoot()));
        return map;
    }

    private static ResourceLocation tagPath(String tag) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "tags/enchantment/" + tag + ".json");
    }

    private static byte[] tagJson(boolean chance, boolean effect) {
        StringBuilder json = new StringBuilder("{\"replace\": false, \"values\": [");
        boolean any = false;
        if (chance) {
            json.append('"').append(CHANCE_ID).append('"');
            any = true;
        }
        if (effect) {
            if (any) {
                json.append(',');
            }
            json.append('"').append(EFFECT_ID).append('"');
        }
        json.append("]}");
        return json.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... path) {
        if (path.length == 1 && PackResources.PACK_META.equals(path[0])) {
            return () -> new ByteArrayInputStream(packMeta);
        }
        return null;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        if (type != PackType.SERVER_DATA) {
            return null;
        }
        byte[] bytes = tagFiles.get(location);
        return bytes == null ? null : () -> new ByteArrayInputStream(bytes);
    }

    @Override
    public void listResources(PackType type, String namespace, String path, ResourceOutput output) {
        if (type != PackType.SERVER_DATA || !"minecraft".equals(namespace)) {
            return;
        }
        tagFiles.forEach((location, bytes) -> {
            if (location.getPath().startsWith(path)) {
                output.accept(location, () -> new ByteArrayInputStream(bytes));
            }
        });
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return type == PackType.SERVER_DATA ? Set.of("minecraft") : Set.of();
    }

    @Override
    public void close() {
    }
}
