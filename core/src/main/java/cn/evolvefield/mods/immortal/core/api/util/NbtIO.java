package cn.evolvefield.mods.immortal.core.api.util;

import net.minecraft.nbt.CompoundTag;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 20:04
 * Description:
 */
public interface NbtIO {

    void toNbt(CompoundTag tag);

    void fromNbt(CompoundTag tag);
}
