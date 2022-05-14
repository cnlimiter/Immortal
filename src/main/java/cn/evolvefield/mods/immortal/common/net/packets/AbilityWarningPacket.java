package cn.evolvefield.mods.immortal.common.net.packets;

import cn.evolvefield.mods.immortal.api.common.net.IPacket;
import cn.evolvefield.mods.immortal.init.handler.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/30 21:43
 * Version: 1.0
 */
public class AbilityWarningPacket extends IPacket {

    private final ResourceLocation resource;

    public AbilityWarningPacket(ResourceLocation resource) {
        this.resource = resource;
    }

    public AbilityWarningPacket(FriendlyByteBuf buffer) {
        resource = buffer.readResourceLocation();
    }

    public static void send(Player player, ResourceLocation resource) {
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new AbilityWarningPacket(resource));
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(resource);

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> ctx) {
        //ctx.get().enqueueWork(() -> Overlay.showWarning(resource));
        ctx.get().setPacketHandled(true);
    }
}
