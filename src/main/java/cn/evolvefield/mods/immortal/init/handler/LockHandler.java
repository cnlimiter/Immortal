package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.api.ImmortalApi;
import cn.evolvefield.mods.immortal.api.core.attribute.PlayerAttributes;
import cn.evolvefield.mods.immortal.init.data.LockJsonListener;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/12 19:20
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LockHandler {
    @SubscribeEvent
    public static void onPlayerEquippedItems(final LivingEquipmentChangeEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;
        //禁止未达到能力条件前使用
        if (!player.isCreative() && event.getSlot().getType() == EquipmentSlot.Type.ARMOR) {
            ItemStack item = event.getTo();

            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> LockJsonListener.getRequirements().forEach((requirement) -> {

                if (requirement.type.equalsIgnoreCase("equipment")
                        && Objects.requireNonNull(item.getItem().getRegistryName()).toString().equals(requirement.name)
                        && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) > requirement.level
                ) {
                    player.drop(item.copy(), false);
                    item.setCount(0);
                }


            }));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemStack();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();


        if (!player.isCreative())
            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> LockJsonListener.getRequirements().forEach((requirement) -> {

                if (requirement.type.equalsIgnoreCase("item")
                        && Objects.requireNonNull(item.getItem().getRegistryName()).toString().equals(requirement.name)
                        && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) < requirement.level
                ) {
                    event.setCanceled(true);
                } else if (requirement.type.equalsIgnoreCase("block")
                        && Objects.requireNonNull(block.getRegistryName()).toString().equals(requirement.name)
                        && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) < requirement.level
                ) {
                    event.setCanceled(true);

                }


            }));


    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemStack();
        Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
        if (!player.isCreative())
            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> LockJsonListener.getRequirements().forEach((requirement) -> {

                if (requirement.type.equalsIgnoreCase("item")
                        && Objects.requireNonNull(item.getItem().getRegistryName()).toString().equals(requirement.name)
                        && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) < requirement.level
                ) {
                    event.setCanceled(true);
                } else if (requirement.type.equalsIgnoreCase("block")
                        && Objects.requireNonNull(block.getRegistryName()).toString().equals(requirement.name)
                        && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) < requirement.level
                ) {
                    event.setCanceled(true);

                }


            }));

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemStack();

        if (!player.isCreative())
            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> LockJsonListener.getRequirements().forEach((requirement) -> {

                if (requirement.type.equalsIgnoreCase("item")
                        && Objects.requireNonNull(item.getItem().getRegistryName()).toString().equals(requirement.name)
                        && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) < requirement.level
                ) {
                    event.setCanceled(true);
                }

            }));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        Entity entity = event.getTarget();
        ItemStack item = event.getItemStack();

        if (!player.isCreative())
            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> LockJsonListener.getRequirements().forEach((requirement) -> {

                if (requirement.type.equalsIgnoreCase("item")
                        && Objects.requireNonNull(item.getItem().getRegistryName()).toString().equals(requirement.name)
                        && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) < requirement.level
                ) {
                    event.setCanceled(true);
                } else if (requirement.type.equalsIgnoreCase("interact")
                        && Objects.requireNonNull(entity.getName()).getString().equals(requirement.name)
                        && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) < requirement.level
                ) {
                    event.setCanceled(true);

                }


            }));
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getPlayer();

        if (player != null) {
            ItemStack item = player.getMainHandItem();

            if (!player.isCreative())
                ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> LockJsonListener.getRequirements().forEach((requirement) -> {

                    if (requirement.type.equalsIgnoreCase("item")
                            && Objects.requireNonNull(item.getItem().getRegistryName()).toString().equals(requirement.name)
                            && playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) < requirement.level
                    ) {
                        event.setCanceled(true);
                    }

                }));
        }
    }

    @SubscribeEvent
    public static void onTooltipDisplay(ItemTooltipEvent event) {
        if (event.getPlayer() != null) {
            Player player = event.getPlayer();
            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> LockJsonListener.getRequirements().forEach((requirement) -> {

                if (requirement.type.equalsIgnoreCase("item")
                        && Objects.requireNonNull(event.getItemStack().getItem().getRegistryName()).toString().equals(requirement.name)

                ) {
                    String[] des = requirement.attribute.split(":");

                    List<Component> tooltips = event.getToolTip();
                    tooltips.add(TextComponent.EMPTY);
                    tooltips.add(new TranslatableComponent("tooltip.requirements").append(":").withStyle(ChatFormatting.GRAY));

                    ChatFormatting colour = playerAttributes.get(player, PlayerAttributes.fromRegistryName(requirement.attribute)) >= requirement.level ? ChatFormatting.GREEN : ChatFormatting.RED;
                    tooltips.add(new TranslatableComponent("tooltip." + des[0] + "." + des[1]).append(" " + requirement.level).withStyle(colour));

                }


            }));


        }
    }

}
