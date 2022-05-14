package cn.evolvefield.mods.immortal.client.widges;

import cn.evolvefield.mods.immortal.api.ImmortalApi;
import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttributes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/29 9:31
 * Version: 1.0
 */
public class DynamicTextComponent {
    private final float scale = 0.7F;
    private int posX, posY;
    private BiFunction<Player, IPlayerAttributes, String> titleText;
    private BiFunction<Player, IPlayerAttributes, List<Component>> hoverText;

    public DynamicTextComponent(int x, int y, BiFunction<Player, IPlayerAttributes, String> titleText, BiFunction<Player, IPlayerAttributes, List<Component>> hoverText) {
        this.posX = x;
        this.posY = y;
        this.titleText = titleText;
        this.hoverText = hoverText;
    }

    private boolean isHovered(int x, int y, int width, int height, int fontWidth, int fontHeight) {
        return (x >= width && y >= height && x < width + fontWidth && y < height + fontHeight);
    }

    public void draw(PoseStack poseStack, Font font, Player player) {
        ImmortalApi.playerAttributes(player).ifPresent(var -> {
            poseStack.pushPose();
            poseStack.scale(this.scale, this.scale, this.scale);

            font.draw(poseStack, this.titleText.apply(player, var), this.posX, this.posY, 4210752);

            poseStack.popPose();
        });
    }

    public void drawAlt(Screen screen, PoseStack poseStack, Font font, Player player, int width, int height, int mouseX, int mouseY) {
        int width1 = (int) ((float) (width - 176) / 2.0F);
        int height1 = (int) ((float) (height - 166) / 2.0F);

        ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> {
            if (isHovered(mouseX, mouseY, width1 + (int) (this.posX * this.scale), height1 + (int) (this.posY * this.scale), (int) (font.width(this.titleText.apply(player, playerAttributes)) * this.scale), 7)) {
                screen.renderComponentTooltip(poseStack, this.hoverText.apply(player, playerAttributes), mouseX, mouseY, font);
            }
        });
    }
}
