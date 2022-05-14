package cn.evolvefield.mods.immortal.client.widges;//package nova.committee.immortal.client.widges;
//
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.components.AbstractButton;
//import net.minecraft.client.gui.narration.NarrationElementOutput;
//import net.minecraft.network.chat.TextComponent;
//import net.minecraft.network.chat.TranslatableComponent;
//import net.minecraft.world.ContainerHelper;
//import nova.committee.immortal.client.screen.AbilityScreen;
//import nova.committee.immortal.init.registry.ModItems;
//
///**
// * Description:
// * Author: cnlimiter
// * Date: 2022/4/12 19:28
// * Version: 1.0
// */
//public class AbilityButton extends AbstractButton
//{
//    private final AbilityModel.Ability ability;
//
//    public AbilityButton(int x, int y, AbilityModel.Ability ability)
//    {
//        super(x, y, 79, 32, TextComponent.EMPTY);
//
//        this.ability = ability;
//    }
//
//    @Override
//    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks)
//    {
//        Minecraft minecraft = Minecraft.getInstance();
//        RenderSystem.setShaderTexture(0, AbilityScreen.RESOURCES);
//
//        int level = AbilityModel.get().getAbilityLevel(ability);
//        int maxLevel = AbilityCap.getMaxLevel();
//
//        int u = ((int) Math.ceil((double) level * 4 / maxLevel) - 1) * 16 + 176;
//        int v = ability.index * 16 + 128;
//
//        blit(stack, x, y, 176, (level == maxLevel ? 64 : 0) + (isMouseOver(mouseX, mouseY) ? 32 : 0), width, height);
//        blit(stack, x + 6, y + 8, u, v, 16, 16);
//
//        minecraft.font.draw(stack, new TranslatableComponent(ability.displayName), x + 25, y + 7, 0xFFFFFF);
//        minecraft.font.draw(stack, level + "/" + maxLevel, x + 25, y + 18, 0xBEBEBE);
//
//        if (isMouseOver(mouseX, mouseY) && level < maxLevel)
//        {
//            int cost = AbilityCap.getStartCost() + (level - 1) * AbilityCap.getCostIncrease();
//            int available = AbilityCap.getUseSkillFragments() ? ContainerHelper.clearOrCountMatchingItems(minecraft.player.getInventory(), itemStack -> itemStack.getItem() == ModItems.SKILL_FRAGMENT, 0, true) : minecraft.player.experienceLevel;
//            int colour = available >= cost ? 0x7EFC20 : 0xFC5454;
//            String text = Integer.toString(cost);
//
//            minecraft.font.drawShadow(stack, text, x + 73 - minecraft.font.width(text), y + 18, colour);
//        }
//    }
//
//    @Override
//    public void onPress()
//    {
//        RequestLevelUp.send(ability);
//    }
//
//    @Override
//    public void updateNarration(NarrationElementOutput output)
//    {
//
//    }
//}
