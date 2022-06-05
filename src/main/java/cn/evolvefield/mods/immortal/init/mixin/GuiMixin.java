package cn.evolvefield.mods.immortal.init.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/5/15 8:15
 * Version: 1.0
 */
@Mixin(Gui.class)
public abstract class GuiMixin extends GuiComponent {
    @Final
    @Shadow
    protected Minecraft minecraft;
    @Final
    @Shadow protected final Random random = new Random();
    @Shadow
    private Player getCameraPlayer() {return null;}
    @Shadow
    protected void renderTextureOverlay(ResourceLocation p_168709_, float p_168710_) {}
    @Shadow
    protected int screenWidth;
    @Shadow
    protected int screenHeight;
    @Shadow protected int tickCount;
}
