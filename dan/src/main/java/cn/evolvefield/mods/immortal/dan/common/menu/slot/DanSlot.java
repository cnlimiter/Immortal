package cn.evolvefield.mods.immortal.dan.common.menu.slot;

import cn.evolvefield.mods.immortal.dan.common.menu.container.LuInContainer;
import cn.evolvefield.mods.immortal.dan.init.registry.ModRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 20:31
 * Description:
 */
public class DanSlot extends Slot {
    private final LuInContainer caoSlot;
    private final Player player;
    private int removeCount;

    public DanSlot(Player player, LuInContainer caoSlot,Container container, int i, int j, int k) {
        super(container, i, j, k);
        this.player = player;
        this.caoSlot = caoSlot;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }
        return super.remove(amount);
    }

    @Override
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void onSwapCraft(int numItemsCrafted) {
        this.removeCount += numItemsCrafted;
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        if (this.removeCount > 0) {
            stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        }
        if (this.container instanceof RecipeHolder) {
            ((RecipeHolder)((Object)this.container)).awardUsedRecipes(this.player);
        }
        this.removeCount = 0;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        NonNullList<ItemStack> nonNullList = player.level.getRecipeManager().getRemainingItemsFor(ModRecipe.LU, this.caoSlot, player.level);
        for (int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemStack = this.caoSlot.getItem(i);
            ItemStack itemStack2 = nonNullList.get(i);
            if (!itemStack.isEmpty()) {
                this.caoSlot.removeItem(i, 1);
                itemStack = this.caoSlot.getItem(i);
            }
            if (itemStack2.isEmpty()) continue;
            if (itemStack.isEmpty()) {
                this.caoSlot.setItem(i, itemStack2);
                continue;
            }
            if (ItemStack.isSame(itemStack, itemStack2) && ItemStack.tagMatches(itemStack, itemStack2)) {
                itemStack2.grow(itemStack.getCount());
                this.caoSlot.setItem(i, itemStack2);
                continue;
            }
            if (this.player.getInventory().add(itemStack2)) continue;
            this.player.drop(itemStack2, false);
        }
    }
}
