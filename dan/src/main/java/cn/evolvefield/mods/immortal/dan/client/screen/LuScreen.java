package cn.evolvefield.mods.immortal.dan.client.screen;

import cn.evolvefield.mods.immortal.dan.common.menu.LuMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/8 2:03
 * Description:
 */
public class LuScreen extends AbstractContainerScreen<LuMenu> {
    public LuScreen(LuMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {

    }
}
