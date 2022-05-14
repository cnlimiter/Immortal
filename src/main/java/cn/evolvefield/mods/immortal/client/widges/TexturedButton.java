package cn.evolvefield.mods.immortal.client.widges;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/29 9:24
 * Version: 1.0
 */
public class TexturedButton extends AbstractButton {
    private final ResourceLocation tex;
    private Screen parentScreen;
    private BiConsumer<Screen, Integer> pressFunction;
    private int textureLat, textureLon, additionalData;

    public TexturedButton(final Screen screen, int x, int y, int w, int h, int u, int v, final int data, final BiConsumer<Screen, Integer> process, final ResourceLocation texture) {
        super(screen.width + x, screen.height + y, w, h, new TextComponent(""));
        this.parentScreen = screen;
        this.textureLat = u;
        this.textureLon = v;
        this.additionalData = data;
        this.pressFunction = process;
        this.tex = texture;
    }

    @Override
    public void onPress() {
        this.pressFunction.accept(this.parentScreen, this.additionalData);
    }

    public int getAdditionalData() {
        return this.additionalData;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderTexture(0, tex);

        RenderSystem.disableDepthTest();

        int var1 = this.textureLon;

        if (this.active) {
            if (isHovered) {
                var1 += this.height;
            }
        } else {
            var1 += (2 * this.height);
        }

        this.blit(pPoseStack, this.x, this.y, this.textureLat, var1, this.width, this.height);

        RenderSystem.enableDepthTest();
    }

}
