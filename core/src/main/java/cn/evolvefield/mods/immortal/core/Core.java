package cn.evolvefield.mods.immortal.core;

import cn.evolvefield.mods.immortal.core.init.factory.NetWorkFactory;
import cn.evolvefield.mods.immortal.core.init.factory.ScreenFactory;
import cn.evolvefield.mods.immortal.core.init.handler.CmdHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/25 1:06
 * Description:
 */
public class Core implements ModInitializer {
    public static final MenuType<ScreenFactory.Handler> IM_SCREEN = Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Constant.MODID, "im_screen"), ScreenFactory.type());


    @Override
    public void onInitialize() {
        CmdHandler.init();
        ServerPlayNetworking.registerGlobalReceiver(NetWorkFactory.MODIFY, NetWorkFactory::modifyAttributes);

    }
}
