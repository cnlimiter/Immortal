package cn.evolvefield.mods.immortal.dan.common.menu;

import cn.evolvefield.mods.immortal.dan.common.menu.container.LuInContainer;
import cn.evolvefield.mods.immortal.dan.common.menu.slot.DanSlot;
import cn.evolvefield.mods.immortal.dan.common.recipe.LuRecipe;
import cn.evolvefield.mods.immortal.dan.init.registry.ModMenu;
import cn.evolvefield.mods.immortal.dan.init.registry.ModRecipe;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 10:41
 * Description:
 */
public class LuMenu extends AbstractContainerMenu {
    private final LuInContainer inSlots = new LuInContainer(this);
    private final ResultContainer resultSlots = new ResultContainer();
    private final ContainerLevelAccess access;
    private final Player player;

    public LuMenu(int i, Inventory inventory){
        this(i, inventory, ContainerLevelAccess.NULL);
    }
    protected LuMenu(int i, Inventory inventory,  ContainerLevelAccess containerLevelAccess) {
        super(ModMenu.LU_MENU, i);
        this.player = inventory.player;
        this.access = containerLevelAccess;
        int k;
        int j;
        this.addSlot(new DanSlot(inventory.player, this.inSlots, this.resultSlots, 0, 124, 35));
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 3; ++k) {
                this.addSlot(new Slot(this.inSlots, k + j * 3, 30 + k * 18, 17 + j * 18));
            }
        }


        //bag
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }

        //bar
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
        }
    }
    protected static void slotChangedCrafting(AbstractContainerMenu menu, Level level, Player player, LuInContainer container, ResultContainer result) {
        ItemStack itemStack2;
        LuRecipe luRecipe;
        if (level.isClientSide) {
            return;
        }
        ServerPlayer serverPlayer = (ServerPlayer)player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<LuRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(ModRecipe.LU, container, level);
        if (optional.isPresent() && result.setRecipeUsed(level, serverPlayer, luRecipe = optional.get()) && (itemStack2 = luRecipe.assemble(container)).isItemEnabled(level.enabledFeatures())) {
            itemStack = itemStack2;
        }
        result.setItem(0, itemStack);
        menu.setRemoteSlot(0, itemStack);
        serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemStack));

    }

    @Override
    public void slotsChanged(@NotNull Container container) {
        this.access.execute((level, blockPos) -> LuMenu.slotChangedCrafting(this, level, this.player, this.inSlots, this.resultSlots));
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.inSlots));
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index == 0) {
                this.access.execute((level, blockPos) -> itemStack2.getItem().onCraftedBy(itemStack2, level, player));
                if (!this.moveItemStackTo(itemStack2, 10, 44, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index >= 10 && index < 44 ? !this.moveItemStackTo(itemStack2, 1, 10, false) && (index < 37 ? !this.moveItemStackTo(itemStack2, 37, 44, false) : !this.moveItemStackTo(itemStack2, 10, 37, false)) : !this.moveItemStackTo(itemStack2, 10, 44, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, itemStack2);
            if (index == 0) {
                player.drop(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return LuMenu.stillValid(this.access, player, Blocks.CRAFTING_TABLE);
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }
}
