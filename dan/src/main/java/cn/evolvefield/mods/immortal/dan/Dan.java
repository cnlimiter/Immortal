package cn.evolvefield.mods.immortal.dan;

import cn.evolvefield.mods.immortal.dan.init.factory.MultiFac;
import cn.evolvefield.mods.immortal.dan.init.factory.SyncFac;
import cn.evolvefield.mods.immortal.dan.init.registry.ModMenu;
import cn.evolvefield.mods.immortal.dan.init.registry.ModRecipe;
import net.fabricmc.api.ModInitializer;

public class Dan implements ModInitializer {

    @Override
    public void onInitialize() {
        MultiFac.init();
        SyncFac.init();
        ModRecipe.init();
        ModMenu.init();
    }
}
