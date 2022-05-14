package cn.evolvefield.mods.immortal.init.registry;

import cn.evolvefield.mods.immortal.init.ModTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/12 19:41
 * Version: 1.0
 */
public class ModItems {
    public static Item SKILL_FRAGMENT;

    @SubscribeEvent
    public static void registryItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(
                SKILL_FRAGMENT = new Item(new Item.Properties().tab(ModTab.TAB)).setRegistryName("skill_fragment")
        );
    }
}
