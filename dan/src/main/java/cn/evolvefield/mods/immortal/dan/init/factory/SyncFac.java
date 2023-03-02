package cn.evolvefield.mods.immortal.dan.init.factory;

import cn.evolvefield.mods.immortal.dan.init.handler.CaoHandler;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 20:36
 * Description:
 */
public class SyncFac {


    public static void init(){
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(
                CaoHandler.INSTANCE
        );
    }


}
