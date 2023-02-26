package cn.evolvefield.mods.immortal.core.api;

import com.github.clevernucleus.dataattributes.api.DataAttributesAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 10:04
 * Description:
 */
public class EntityAttributeSupplier implements Supplier<Attribute> {
    private final ResourceLocation id;

    private EntityAttributeSupplier(final ResourceLocation id) { this.id = id; }

    /**
     * @param registryKey EntityAttribute registry key.
     * @return
     */
    public static EntityAttributeSupplier of(final ResourceLocation registryKey) {
        return new EntityAttributeSupplier(registryKey);
    }

    /**
     * @return The registry key.
     */
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public Attribute get() {
        return DataAttributesAPI.getAttribute(this.id).get();
    }
}
