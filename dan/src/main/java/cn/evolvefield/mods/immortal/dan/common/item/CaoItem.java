package cn.evolvefield.mods.immortal.dan.common.item;

import cn.evolvefield.mods.immortal.dan.core.cao.Cao;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 20:01
 * Description:
 */
public class CaoItem extends Item{

    private final Cao cao;

    public CaoItem(Cao cao) {
        super(new Properties());
        this.cao = cao;
    }

    public void toNbt(CompoundTag tag) {
        cao.toNbt(tag);
    }
    public Cao fromNbt(CompoundTag tag) {
       return cao.fromNbt(tag);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
}
