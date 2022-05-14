package cn.evolvefield.mods.immortal.api.client.hud;

import cn.evolvefield.mods.immortal.Static;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/30 12:07
 * Version: 1.0
 */
public abstract class AbstractBarOverlay implements IBarOverlay {
    public final ResourceLocation PLAYER_BAR = new ResourceLocation(Static.MOD_ID, "textures/gui/bar.png");

    public void renderAll(Player player, PoseStack poseStack) {
        this.renderBar(player, poseStack);
        this.renderText(player, poseStack);
        this.renderIcon(player, poseStack);
    }
}
