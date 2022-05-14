package cn.evolvefield.mods.immortal.client.screen;

import cn.evolvefield.mods.immortal.Static;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/12 19:31
 * Version: 1.0
 */
public class AbilityScreen extends Screen {
    public static final ResourceLocation RESOURCES = new ResourceLocation(Static.MOD_ID, "textures/gui/abilities.png");

    public AbilityScreen() {
        super(new TranslatableComponent("container.abilities"));
    }

    @Override
    protected void init() {
        int left = (width - 162) / 2;
        int top = (height - 128) / 2;

        for (int i = 0; i < 12; i++) {
            int x = left + i % 2 * 83;
            int y = top + i / 2 * 36;

            //addRenderableWidget(new AbilityButton(x, y, AbilityModel.Ability.values()[i]));
        }
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderTexture(0, RESOURCES);

        int left = (width - 176) / 2;
        int top = (height - 166) / 2;

        renderBackground(stack);

        blit(stack, left, top, 0, 0, 176, 166);
        font.draw(stack, title, (width - font.width(title)) / 2f, top + 6, 0x3F3F3F);

        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
