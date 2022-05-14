package cn.evolvefield.mods.immortal.client.widges;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.client.screen.PlayerAttributesScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.TextComponent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/12 19:30
 * Version: 1.0
 */
public class TabButton extends AbstractButton {
    private final boolean selected;
    private final TabType type;

    public TabButton(int x, int y, TabType type, boolean selected) {
        super(x, y, 31, 28, TextComponent.EMPTY);

        this.type = type;
        this.selected = selected;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        active = !(minecraft.screen instanceof InventoryScreen) || !((InventoryScreen) minecraft.screen).getRecipeBookComponent().isVisible();

        if (active) {
            RenderSystem.setShaderTexture(0, Static.RESOURCES);

            blit(stack, x, y, selected ? 31 : 0, 166, width, height);
            blit(stack, x + (selected ? 8 : 10), y + 6, 240, 128 + type.iconIndex * 16, 16, 16);
        }
    }

    @Override
    public void onPress() {
        Minecraft minecraft = Minecraft.getInstance();

        switch (type) {
            case INVENTORY -> minecraft.setScreen(new InventoryScreen(minecraft.player));
            case ATTRIBUTE -> minecraft.setScreen(new PlayerAttributesScreen());
            //case SKILLS -> minecraft.setScreen(new AbilityScreen());
        }
    }

    @Override
    public void updateNarration(NarrationElementOutput output) {

    }

    public enum TabType {
        INVENTORY(0),
        ATTRIBUTE(1),
        SKILLS(2);

        public final int iconIndex;

        TabType(int index) {
            iconIndex = index;
        }
    }
}
