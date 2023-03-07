package cn.evolvefield.mods.immortal.dan.client;

import cn.evolvefield.mods.immortal.dan.client.screen.LuScreen;
import cn.evolvefield.mods.immortal.dan.init.registry.ModMenu;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/1 13:09
 * Description:
 */
public class DanClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenu.LU_MENU, LuScreen::new);
    }
}
