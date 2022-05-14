package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttributes;
import cn.evolvefield.mods.immortal.common.cap.AttributesProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 11:03
 * Version: 1.0
 */
@Mod.EventBusSubscriber
public class CapHandler {

    @SubscribeEvent
    public static void registerCap(RegisterCapabilitiesEvent event) {
        event.register(IPlayerAttributes.class);//属性
    }

    @SubscribeEvent
    public static void attachCap(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(Static.MOD_ID, "im_player_attributes"), new AttributesProvider());
        }
    }
}
