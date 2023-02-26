package cn.evolvefield.mods.immortal.core;

import cn.evolvefield.mods.immortal.core.init.factory.NetWorkFactory;
import cn.evolvefield.mods.immortal.core.init.handler.CmdHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/25 1:06
 * Description:
 */
public class Core implements ModInitializer {
    @Override
    public void onInitialize() {
        CmdHandler.init();
        ServerPlayNetworking.registerGlobalReceiver(NetWorkFactory.MODIFY, NetWorkFactory::modifyAttributes);

    }
}
