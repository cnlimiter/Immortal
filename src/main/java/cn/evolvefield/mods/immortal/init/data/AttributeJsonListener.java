package cn.evolvefield.mods.immortal.init.data;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttribute;
import cn.evolvefield.mods.immortal.api.core.attribute.PlayerAttributes;
import cn.evolvefield.mods.immortal.api.util.Limit;
import cn.evolvefield.mods.atomlib.utils.JsonUtils;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/5/8 21:47
 * Version: 1.0
 */
public class AttributeJsonListener extends SimpleJsonResourceReloadListener {

    public static final AttributeJsonListener INSTANCE = new AttributeJsonListener();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private Map<String, IPlayerAttribute> attributeHashMap = new HashMap<>();


    public AttributeJsonListener() {
        super(GSON, "attributes");
    }

    public static IPlayerAttribute create(ResourceLocation id, JsonObject json) throws JsonSyntaxException {
        Limit limit;
        UUID uuid;
        double defaultValue = JsonUtils.getDoubleOr("value", json, 0);
        double min = JsonUtils.getDoubleOr("min", json, 0);
        double max = JsonUtils.getDoubleOr("max", json, Double.MAX_VALUE);


        return PlayerAttributes.registerAttribute(new ResourceLocation(Static.MOD_ID, id.getPath()), UUID.randomUUID(), Limit.none(), IPlayerAttribute.Type.ALL,
                () -> new RangedAttribute(PlayerAttributes.newRegistryName(id.getPath()), defaultValue, min, max));

    }


    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> dataMap, @NotNull ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        pProfiler.push("AttributeJsonListener");

        Map<String, IPlayerAttribute> data = new HashMap<>();
        if (!dataMap.isEmpty()) {
            for (Map.Entry<ResourceLocation, JsonElement> entry : dataMap.entrySet()) {
                ResourceLocation id = entry.getKey();

                ResourceLocation simpleId = id.getPath().contains("/") ? new ResourceLocation(id.getNamespace(), id.getPath().substring(id.getPath().lastIndexOf("/") + 1)) : id;
                IPlayerAttribute attribute = create(simpleId, entry.getValue().getAsJsonObject());

                data.remove(simpleId.toString());
                data.put(simpleId.toString(), attribute);

                Static.LOGGER.debug("Adding to attributes data " + simpleId);
            }
            setData(data);
        }


        pProfiler.popPush("AttributeJsonListener");
    }

    public IPlayerAttribute getData(String id) {
        return attributeHashMap.get(id);
    }

    public Map<String, IPlayerAttribute> getData() {
        return attributeHashMap;
    }

    public void setData(Map<String, IPlayerAttribute> data) {
        attributeHashMap = data;
        if (ModList.get().isLoaded("patchouli")) {
        }
    }
}
