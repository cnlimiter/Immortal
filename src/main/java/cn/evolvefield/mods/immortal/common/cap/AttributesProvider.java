package cn.evolvefield.mods.immortal.common.cap;

import cn.evolvefield.mods.immortal.api.ImmortalApi;
import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttributes;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 10:14
 * Version: 1.0
 */
public class AttributesProvider implements NonNullSupplier<IPlayerAttributes>, ICapabilitySerializable<CompoundTag> {
    private final LazyOptional<IPlayerAttributes> optional;
    private final IPlayerAttributes data;

    public AttributesProvider() {
        this.data = new AttributesCap();
        this.optional = LazyOptional.of(() -> data);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ImmortalApi.PLAYER_ATTRIBUTES.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.data.write();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.data.read(nbt);
    }

    @NotNull
    @Override
    public IPlayerAttributes get() {
        return this.data;
    }
}
