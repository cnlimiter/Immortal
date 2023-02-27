package cn.evolvefield.mods.immortal.core.api.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/28 0:33
 * Description:
 */
public abstract class PageLayer extends AbstractContainerScreen<AbstractContainerMenu> {
    protected final AbstractContainerScreen<?> parent;

    public PageLayer(AbstractContainerScreen<?> parent, AbstractContainerMenu menu, Inventory inv, Component text) {
        super(menu, inv, text);

        this.parent = parent;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float delta) {}


    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {

    }


    @FunctionalInterface
    public interface Builder {

        /**
         *
         * @param parent
         * @param menu
         * @param inv
         * @param text
         * @return
         */
        PageLayer build(AbstractContainerScreen<?> parent, AbstractContainerMenu menu, Inventory inv, Component text);
    }
}
