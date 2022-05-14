package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.common.net.packets.AbilityWarningPacket;
import cn.evolvefield.mods.immortal.common.net.packets.SyncPlayerAttributesPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/20 20:41
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PacketHandler {
    public static final String VERSION = "1.0";
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Static.MOD_ID, "network"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION)
        );
        INSTANCE.registerMessage(nextID(), SyncPlayerAttributesPacket.class, SyncPlayerAttributesPacket::toBytes, SyncPlayerAttributesPacket::new, SyncPlayerAttributesPacket::handle);
        INSTANCE.registerMessage(nextID(), AbilityWarningPacket.class, AbilityWarningPacket::toBytes, AbilityWarningPacket::new, AbilityWarningPacket::handle);
    }

}
