package cn.evolvefield.mods.immortal.dan.common.recipe.io;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 16:11
 * Description:
 */
public class ItemOutput {
    public final Item item;
    public final int amount;

    public ItemOutput(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public ItemStack getStack() {
        return new ItemStack(item, amount);
    }
}
