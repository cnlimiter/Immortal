package cn.evolvefield.mods.immortal.dan;

import cn.evolvefield.mods.immortal.dan.init.factory.SyncFac;
import net.fabricmc.api.ModInitializer;

public class Dan implements ModInitializer {

    @Override
    public void onInitialize() {
        SyncFac.init();
    }
}
