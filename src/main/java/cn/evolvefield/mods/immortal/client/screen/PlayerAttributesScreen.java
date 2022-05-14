package cn.evolvefield.mods.immortal.client.screen;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.api.ImmortalApi;
import cn.evolvefield.mods.immortal.api.core.attribute.PlayerAttributes;
import cn.evolvefield.mods.immortal.client.widges.DynamicTextComponent;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/29 8:57
 * Version: 1.0
 */
public class PlayerAttributesScreen extends Screen {

    public static final ResourceLocation GUI = new ResourceLocation(Static.MOD_ID, "textures/gui/background.png");
    private final int left = (width - 176) / 2;
    ;
    private final int top = (height - 166) / 2;
    private final List<DynamicTextComponent> dynamicTextComponents = new ArrayList<>();


    private final DynamicTextComponent level = new DynamicTextComponent(left + 12, top + 12, (player, playerAttributes) -> {
        TranslatableComponent component = new TranslatableComponent(Static.MOD_ID + ".attribute.level", (int) playerAttributes.get(player, PlayerAttributes.LEVEL), (int) (100 * playerAttributes.expCoeff(player)), "%");

        return component.getString();
    }, (player, playerAttributes) -> {
        List<Component> componentList = new ArrayList<>();
        componentList.add(new TextComponent(ChatFormatting.GRAY + I18n.get(Static.MOD_ID + ".attribute.level.alt")));
        ImmortalApi.getTooltips(PlayerAttributes.LEVEL.registryName()).forEach(function -> componentList.add(new TextComponent(function.apply(player, playerAttributes))));
        return componentList;
    });


    public PlayerAttributesScreen() {
        super(new TranslatableComponent("container.player_attributes"));
        this.dynamicTextComponents.add(level);

    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderTexture(0, GUI);

        renderBackground(pPoseStack);
        blit(pPoseStack, left, top, 0, 0, 176, 166);
        font.draw(pPoseStack, title, (width - font.width(title)) / 2f, top + 6, 0x3F3F3F);

        this.dynamicTextComponents.forEach(dynamicTextComponent -> dynamicTextComponent.draw(pPoseStack, this.font, this.minecraft.player));
        this.dynamicTextComponents.forEach(dynamicTextComponent -> dynamicTextComponent.drawAlt(this, pPoseStack, this.font, this.minecraft.player, this.width, this.height, pMouseX, pMouseY));

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);

    }

    @Override
    protected void init() {
        int left = (width - 162) / 2;
        int top = (height - 128) / 2;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
