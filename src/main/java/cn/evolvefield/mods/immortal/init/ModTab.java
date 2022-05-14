package cn.evolvefield.mods.immortal.init;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.init.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/31 10:36
 * Version: 1.0
 */
public class ModTab extends CreativeModeTab {
    public static CreativeModeTab TAB = new ModTab();

    public ModTab() {
        super(Static.MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.SKILL_FRAGMENT);
    }
}
