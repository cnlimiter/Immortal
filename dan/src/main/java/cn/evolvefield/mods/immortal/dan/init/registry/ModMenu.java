package cn.evolvefield.mods.immortal.dan.init.registry;

import cn.evolvefield.mods.immortal.dan.Constant;
import cn.evolvefield.mods.immortal.dan.common.menu.LuMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/6 0:38
 * Description:
 */
public class ModMenu {
    public static final MenuType<LuMenu> LU_MENU = new MenuType<>(LuMenu::new);

    public static void init(){
        Registry.register(BuiltInRegistries.MENU, Constant.id("lu"), LU_MENU);
    }


}
