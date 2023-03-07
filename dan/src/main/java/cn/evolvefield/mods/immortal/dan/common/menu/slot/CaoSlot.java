package cn.evolvefield.mods.immortal.dan.common.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 20:00
 * Description:
 */
public class CaoSlot extends Slot {
    public CaoSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.getOrCreateTag().contains("zhu")
                || stack.getOrCreateTag().contains("fu")
                || stack.getOrCreateTag().contains("yin")
                ;
    }
}
