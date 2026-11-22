package com.xulai.criticalhit.data;

import com.xulai.criticalhit.config.CritConfig;
import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceMetadata;
import org.jspecify.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CritAvailabilityPack implements PackResources {

    private static final String CHANCE_ID = "criticalhit:crit_chance";
    private static final String EFFECT_ID = "criticalhit:crit_effect";

    private final PackLocationInfo location;
    private final Map<Identifier, byte[]> tagFiles;
    private final byte[] packMeta;

    public CritAvailabilityPack(PackLocationInfo location, PackType type) {
        this.location = location;
        this.packMeta = buildMetadata(type);
        this.tagFiles = buildTagFiles();
    }

    private static byte[] buildMetadata(PackType type) {
        PackFormat format = SharedConstants.getCurrentVersion().packVersion(type);
        String json = "{\"pack\":{\"min_format\":" + format.major()
                + ",\"max_format\":" + format.major()
                + ",\"description\":\"CriticalHit enchantment availability\"}}";
        return json.getBytes(StandardCharsets.UTF_8);
    }

    private static Map<Identifier, byte[]> buildTagFiles() {
        Map<Identifier, byte[]> map = new HashMap<>();
        map.put(tagPath("in_enchanting_table"),
                tagJson(CritConfig.isChanceInEnchantingTable(), CritConfig.isEffectInEnchantingTable()));
        map.put(tagPath("tradeable"),
                tagJson(CritConfig.isChanceInVillagerTrade(), CritConfig.isEffectInVillagerTrade()));
        map.put(tagPath("on_random_loot"),
                tagJson(CritConfig.isChanceInRandomLoot(), CritConfig.isEffectInRandomLoot()));
        return map;
    }

    private static Identifier tagPath(String tag) {
        return Identifier.fromNamespaceAndPath("minecraft", "tags/enchantment/" + tag + ".json");
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

    @Override
    public PackLocationInfo location() {
        return location;
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
    public IoSupplier<InputStream> getResource(PackType type, Identifier target) {
        if (type != PackType.SERVER_DATA) {
            return null;
        }
        byte[] bytes = tagFiles.get(target);
        return bytes == null ? null : () -> new ByteArrayInputStream(bytes);
    }

    @Override
    public void listResources(PackType type, String namespace, String path, ResourceOutput output) {
        if (type != PackType.SERVER_DATA || !"minecraft".equals(namespace)) {
            return;
        }
        tagFiles.forEach((target, bytes) -> {
            if (target.getPath().startsWith(path)) {
                output.accept(target, () -> new ByteArrayInputStream(bytes));
            }
        });
    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return type == PackType.SERVER_DATA ? Set.of("minecraft") : Set.of();
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionType<T> metadataSerializer) throws IOException {
        try (InputStream stream = new ByteArrayInputStream(packMeta)) {
            return ResourceMetadata.fromJsonStream(stream).getSection(metadataSerializer).orElse(null);
        }
    }

    @Override
    public void close() {
    }
}
