package cn.evolvefield.mods.immortal.dan.common.block;

import cn.evolvefield.mods.immortal.dan.common.menu.LuMenu;
import cn.evolvefield.mods.multiblocklib.api.Multiblock;
import cn.evolvefield.mods.multiblocklib.api.pattern.MatchResult;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/4 18:10
 * Description:
 */
public class LuBlock extends Multiblock implements MenuProvider {

    public LuBlock(Level world, MatchResult match) {
        super(world, match);
    }

    @Override
    public InteractionResult onUse(Level world, BlockPos clickPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(this);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("DanLu");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player) {
        return new LuMenu(i, inventory);
    }
}
