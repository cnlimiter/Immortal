package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.client.hud.bar.PlayerHp;
import cn.evolvefield.mods.immortal.client.hud.bar.PlayerLevel;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/29 21:42
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderHandler {

    public static void initOverLay(){
        OverlayRegistry.registerOverlayTop("", (gui, poseStack, partialTick, width, height) -> {
            gui.setupOverlayRenderState(true, false);
            //(new PlayerHp()).renderAll(gui);
        });
    }

    @SubscribeEvent
    public static void onGameOverlayRenderPre(RenderGameOverlayEvent.PreLayer event) {
        var mc = Minecraft.getInstance();
        if (mc.player != null && mc.gameMode != null) {

            if (event.getOverlay() == ForgeIngameGui.PLAYER_HEALTH_ELEMENT) {
                event.setCanceled(true);
                (new PlayerHp()).renderAll((Player) mc.getCameraEntity(), event.getMatrixStack());
            }
            if (event.getOverlay() == ForgeIngameGui.EXPERIENCE_BAR_ELEMENT) {
                event.setCanceled(true);
                (new PlayerLevel()).renderAll((Player) mc.getCameraEntity(), event.getMatrixStack());
            }
        }

    }


    private void fillRect(BufferBuilder builder, int x, int y, int width, int height, int r, int g, int b) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        builder.vertex(x, y, 0.0D).color(r, g, b, 255).endVertex();
        builder.vertex(x, y + height, 0.0D).color(r, g, b, 255).endVertex();
        builder.vertex(x + width, y + height, 0.0D).color(r, g, b, 255).endVertex();
        builder.vertex(x + width, y, 0.0D).color(r, g, b, 255).endVertex();
        builder.end();
        BufferUploader.end(builder);
    }
}
