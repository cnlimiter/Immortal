package cn.evolvefield.mods.immortal.api.core.attribute;

import cn.evolvefield.mods.immortal.api.util.Limit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Description:玩家属性实体类
 * Author: cnlimiter
 * Date: 2022/3/28 9:33
 * Version: 1.0
 */
public class PlayerAttribute implements IPlayerAttribute {
    private final IPlayerAttribute.Type type;
    private final Supplier<Attribute> attribute;
    private final ResourceLocation registryName;
    private final Limit limit;
    private final UUID uuid;

    public PlayerAttribute(final ResourceLocation registryName, final UUID uuid, final Limit limit, final IPlayerAttribute.Type type, final Supplier<Attribute> supplier) {
        this.registryName = registryName;
        this.uuid = uuid;
        this.limit = limit;
        this.type = type;
        this.attribute = supplier;
    }

    @Override
    public Type type() {
        return this.type;
    }

    @Override
    public UUID uuid() {
        return this.uuid;
    }

    @Override
    public Limit limit() {
        return this.limit;
    }

    @Override
    public ResourceLocation registryName() {
        return this.registryName;
    }

    @Override
    public Attribute get() {
        return this.attribute.get();
    }

    @Override
    public boolean equals(Object par0) {
        if (par0 == null) return false;
        if (par0 == this) return true;
        if (!(par0 instanceof IPlayerAttribute)) return false;

        IPlayerAttribute var0 = (IPlayerAttribute) par0;

        return toString().equals(var0.toString());
    }

    @Override
    public String toString() {
        return this.registryName.toString();
    }
}
