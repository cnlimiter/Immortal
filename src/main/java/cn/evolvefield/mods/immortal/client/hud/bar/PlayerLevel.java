package cn.evolvefield.mods.immortal.client.hud.bar;

import cn.evolvefield.mods.immortal.api.ImmortalApi;
import cn.evolvefield.mods.atomlib.client.hud.AbstractBarOverlay;
import cn.evolvefield.mods.immortal.api.core.attribute.PlayerAttributes;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.player.Player;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/30 15:20
 * Version: 1.0
 */
public class PlayerLevel extends AbstractBarOverlay {
    @Override
    public void renderBar(Player player, PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        if (player == null) return;
        Window var1 = minecraft.getWindow();
        int posX = var1.getGuiScaledWidth();
        int posY = var1.getGuiScaledHeight();
        RenderSystem.setShaderTexture(0, this.PLAYER_BAR);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        minecraft.getProfiler().push("immortal_level");
        GuiComponent.blit(poseStack, (posX / 2) - 91, posY - 27, 0, 0, 58, 155, 5, 255, 255);

        ImmortalApi.playerAttributes(player).ifPresent(attributes -> {
            int exp = 0, var3 = 166;

            exp = (int) (155F * attributes.expCoeff(player));
            var3 = 48;


            GuiComponent.blit(poseStack, (posX / 2) - 91, posY - 27, 0, 0, var3, exp, 5, 255, 255);
        });
        minecraft.getProfiler().pop();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

    }

    @Override
    public void renderText(Player player, PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        ImmortalApi.playerAttributes(player).ifPresent(attributes -> {
            int level = 0, color = 0, yPos = minecraft.getWindow().getGuiScaledHeight() - 36;
            level = (int) attributes.get(player, PlayerAttributes.LEVEL);
            color = 16759296;
            if (level < 0) return;
            String formatLevel = "" + level;
            int xPos = (minecraft.getWindow().getGuiScaledWidth() - font.width(formatLevel)) / 2;

            font.draw(poseStack, formatLevel, (float) (xPos + 1), (float) yPos, 0);
            font.draw(poseStack, formatLevel, (float) (xPos - 1), (float) yPos, 0);
            font.draw(poseStack, formatLevel, (float) xPos, (float) (yPos + 1), 0);
            font.draw(poseStack, formatLevel, (float) xPos, (float) (yPos - 1), 0);
            font.draw(poseStack, formatLevel, (float) xPos, (float) yPos, color);

        });
    }

    @Override
    public void renderIcon(Player player, PoseStack poseStack) {

    }
}
