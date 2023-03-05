package cn.evolvefield.mods.immortal.dan.common.menu;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 10:41
 * Description:
 */
public class LuMenu extends AbstractContainerMenu {
    private final ResultContainer resultSlots = new ResultContainer();
    protected LuMenu(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
