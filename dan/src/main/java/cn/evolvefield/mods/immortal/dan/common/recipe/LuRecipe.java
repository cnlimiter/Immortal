package cn.evolvefield.mods.immortal.dan.common.recipe;

import cn.evolvefield.mods.immortal.dan.common.menu.container.LuInContainer;
import cn.evolvefield.mods.immortal.dan.common.recipe.io.FuInput;
import cn.evolvefield.mods.immortal.dan.common.recipe.io.ItemOutput;
import cn.evolvefield.mods.immortal.dan.common.recipe.io.YinInput;
import cn.evolvefield.mods.immortal.dan.common.recipe.io.ZhuInput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 10:45
 * Description:
 */
public class LuRecipe implements Recipe<LuInContainer> {
    final ResourceLocation id;
    final LuRecipeType type;

    public List<ZhuInput> zhu1;
    public List<ZhuInput> zhu2;
    public List<FuInput> fu1;
    public List<FuInput> fu2;
    public List<YinInput> yin;
    //public List<ItemInput> fire;
    public List<ItemOutput> out;

    public LuRecipe(ResourceLocation id, LuRecipeType type){
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(LuInContainer container, Level level) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull ItemStack assemble(LuInContainer container) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        for (ItemOutput o : out) {
            return new ItemStack(o.item, o.amount);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return type;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return type;
    }






}
