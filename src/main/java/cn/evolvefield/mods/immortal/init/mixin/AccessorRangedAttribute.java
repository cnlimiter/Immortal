package cn.evolvefield.mods.immortal.init.mixin;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/30 16:24
 * Version: 1.0
 */
@Mixin(RangedAttribute.class)
public interface AccessorRangedAttribute {

    @Accessor("minValue")
    @Mutable
    void immortal$setMinValue(double minValue);

    @Accessor("maxValue")
    @Mutable
    void immortal$setMaxValue(double maxValue);
}
