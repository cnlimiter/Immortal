package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.client.screen.PlayerAttributesScreen;
import cn.evolvefield.mods.immortal.client.widges.TabButton;
import cn.evolvefield.mods.immortal.init.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/12 19:33
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ScreenHandler {
    @SubscribeEvent
    public static void onInitGui(ScreenEvent.InitScreenEvent event) {
        Screen screen = event.getScreen();

        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen ||
                screen instanceof PlayerAttributesScreen
        ) {
            boolean inventoryOpen = screen instanceof InventoryScreen;
            boolean creativeOpen = screen instanceof CreativeModeInventoryScreen;
            boolean attributeOpen = screen instanceof PlayerAttributesScreen;
            int x = (screen.width - (creativeOpen ? 195 : 176)) / 2 - 28;
            int y = (screen.height - (creativeOpen ? 136 : 166)) / 2;

            event.addListener(new TabButton(x, y + 7, TabButton.TabType.INVENTORY, inventoryOpen || creativeOpen));
            event.addListener(new TabButton(x, y + 36, TabButton.TabType.ATTRIBUTE, attributeOpen));

        }
    }


    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft minecraft = Minecraft.getInstance();

        if (ClientProxy.openKey.consumeClick() && minecraft.screen == null) {
            //minecraft.setScreen(new AbilityScreen());
        }
    }
}
