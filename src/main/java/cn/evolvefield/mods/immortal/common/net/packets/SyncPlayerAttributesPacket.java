package cn.evolvefield.mods.immortal.common.net.packets;

import cn.evolvefield.mods.immortal.Immortal;
import cn.evolvefield.mods.immortal.api.ImmortalApi;
import cn.evolvefield.mods.immortal.api.common.net.IPacket;
import cn.evolvefield.mods.immortal.common.cap.AttributesCap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 10:44
 * Version: 1.0
 */
public class SyncPlayerAttributesPacket extends IPacket {

    private CompoundTag tag;
    private double offset, scale;

    public SyncPlayerAttributesPacket() {
    }

    public SyncPlayerAttributesPacket(FriendlyByteBuf buf) {
        this.tag = buf.readNbt();
        this.offset = buf.readDouble();
        this.scale = buf.readDouble();
    }


    public SyncPlayerAttributesPacket(CompoundTag tag, double offset, double scale) {
        this.tag = tag;
        this.offset = offset;
        this.scale = scale;
    }


    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeNbt(tag);
        buffer.writeDouble(offset);
        buffer.writeDouble(scale);

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                Immortal.PROXY.player().ifPresent(player -> {
                    if (tag != null) {
                        ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> ((AttributesCap) playerAttributes).receive(tag, offset, scale));
                    }
                });
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
