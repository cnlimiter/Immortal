package cn.evolvefield.mods.immortal.core.init.factory;

import cn.evolvefield.mods.immortal.core.Constant;
import cn.evolvefield.mods.immortal.core.api.CoreApi;
import cn.evolvefield.mods.immortal.core.api.EntityAttributeSupplier;
import cn.evolvefield.mods.immortal.core.api.data.PlayerData;
import cn.evolvefield.mods.immortal.core.api.net.PlayerPacketType;
import com.github.clevernucleus.dataattributes.api.DataAttributesAPI;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 17:11
 * Description:
 */
public class NetWorkFactory {

    public static final ResourceLocation MODIFY = new ResourceLocation(Constant.MODID, "modify");

    public static void modifyAttributes(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        CompoundTag tag = buf.readNbt();

        server.execute(() -> {
            if(player != null && tag != null) {
                PlayerData data = CoreApi.PLAYER_DATA.get(player);
                ListTag list = tag.getList("Data", Tag.TAG_COMPOUND);
                PlayerPacketType playerPacketType = PlayerPacketType.fromId(tag.getByte("Type"));

                if(playerPacketType.test(server, player, data)) {
                    for(int i = 0; i < list.size(); i++) {
                        CompoundTag entry = list.getCompound(i);
                        ResourceLocation identifier = new ResourceLocation(entry.getString("Key"));
                        EntityAttributeSupplier attribute = EntityAttributeSupplier.of(identifier);
                        DataAttributesAPI.ifPresent(player, attribute, (Object)null, amount -> {
                            double value = entry.getDouble("Value");
                            data.add(attribute, value);
                            return null;
                        });
                    }
                }
            }
        });
    }


    public static void switchScreen(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        int pageId = buf.readInt();

        server.execute(() -> {
            if(player != null) {
                if(pageId < 0) {
                    player.closeContainer();
                } else {
                    player.openMenu(new ScreenFactory(pageId));

                }
            }
        });
    }
}
