package cn.evolvefield.mods.immortal.client.hud.bar;

import cn.evolvefield.mods.atomlib.client.hud.AbstractBarOverlay;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/30 12:08
 * Version: 1.0
 */
public class PlayerHp extends AbstractBarOverlay {
    private static int healthLat(final Player player) {
        if (player.hasEffect(MobEffects.WITHER)) return 32;
        if (player.hasEffect(MobEffects.POISON)) return 24;
        if (player.hasEffect(MobEffects.ABSORPTION)) return 16;
        return 8;
    }

    @Override
    public void renderBar(Player player, PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, this.PLAYER_BAR);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        minecraft.getProfiler().push("immortal_health");
        int xStart = minecraft.getWindow().getGuiScaledWidth() / 2 - 91;
        int yStart = minecraft.getWindow().getGuiScaledHeight() - 39;
        GuiComponent.blit(poseStack, xStart, yStart, 0.0F, 0.0F, 78, 8, 255, 255);
        int l = (int) (Math.min(player.getHealth() / player.getMaxHealth(), 1.0F) * 78);
        GuiComponent.blit(poseStack, xStart, yStart, 0.0F, healthLat(player), l, 8, 255, 255);
        minecraft.getProfiler().pop();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void renderText(Player player, PoseStack poseStack) {
        Minecraft minecraft = Minecraft.getInstance();
        int xStart = minecraft.getWindow().getGuiScaledWidth() / 2 - 91;
        int yStart = minecraft.getWindow().getGuiScaledHeight() - 39;
        String hp = String.valueOf((int) player.getHealth());
        GuiComponent.drawString(poseStack, Minecraft.getInstance().font, hp, xStart - 6 * hp.length(), yStart + 1, 65280);
    }

    @Override
    public void renderIcon(Player player, PoseStack poseStack) {

    }
}
