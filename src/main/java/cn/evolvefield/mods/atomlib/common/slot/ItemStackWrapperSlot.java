package cn.evolvefield.mods.atomlib.common.slot;

import cn.evolvefield.mods.atomlib.utils.item.ItemStackWrapper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/2 18:12
 * Version: 1.0
 */
public class ItemStackWrapperSlot extends SlotItemHandler {
    private final ItemStackWrapper inventory;
    private final int index;

    public ItemStackWrapperSlot(ItemStackWrapper inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
        this.inventory = inventory;
        this.index = index;
    }

    public boolean mayPickup(Player player) {
        return !this.inventory.extractItemSuper(this.index, 1, true).isEmpty();
    }

    public @NotNull ItemStack remove(int amount) {
        return this.inventory.extractItemSuper(this.index, amount, false);
    }

}
