package cn.evolvefield.mods.immortal.dan.init.registry;

import cn.evolvefield.mods.immortal.dan.common.recipe.LuRecipeType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 11:15
 * Description:
 */
public class ModRecipe {
    public static LuRecipeType LU;
    public static void init(){
        LU = create("lu");
    }
    private static final List<LuRecipeType> recipeTypes = new ArrayList<>();
    public static List<LuRecipeType> getRecipeTypes() {
        return Collections.unmodifiableList(recipeTypes);
    }

    public static LuRecipeType create(String name) {
        return create(name, LuRecipeType::new);
    }

    private static LuRecipeType create(String name, Function<ResourceLocation, LuRecipeType> ctor) {
        LuRecipeType type = ctor.apply(new ResourceLocation(name));
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, type.id(), type);
        Registry.register(BuiltInRegistries.RECIPE_TYPE, type.id(), type);
        recipeTypes.add(type);
        return type;
    }

}
