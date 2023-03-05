package cn.evolvefield.mods.immortal.dan.common.recipe.slot;

import cn.evolvefield.mods.immortal.dan.core.cao.ZhuType;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 15:52
 * Description:
 */
public class ZhuIngredient implements Predicate<ZhuType> {
    @Override
    public boolean test(ZhuType type) {
        if (stack == null) {
            return false;
        }
        if (this.isEmpty()) {
            return stack.isEmpty();
        }
        for (ItemStack itemStack : this.getItems()) {
            if (!itemStack.is(stack.getItem())) continue;
            return true;
        }
        return false;
    }
}
