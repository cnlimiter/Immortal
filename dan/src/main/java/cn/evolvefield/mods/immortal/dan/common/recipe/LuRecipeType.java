package cn.evolvefield.mods.immortal.dan.common.recipe;

import cn.evolvefield.mods.immortal.dan.common.recipe.io.FuInput;
import cn.evolvefield.mods.immortal.dan.common.recipe.io.ItemOutput;
import cn.evolvefield.mods.immortal.dan.common.recipe.io.YinInput;
import cn.evolvefield.mods.immortal.dan.common.recipe.io.ZhuInput;
import cn.evolvefield.mods.immortal.dan.core.cao.FuType;
import cn.evolvefield.mods.immortal.dan.core.cao.YinType;
import cn.evolvefield.mods.immortal.dan.core.cao.ZhuType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 15:33
 * Description:
 */
public record LuRecipeType(ResourceLocation id) implements RecipeType<LuRecipe>, RecipeSerializer<LuRecipe> {

    private Collection<LuRecipe> getManagerRecipes(Level world) {
        return world.getRecipeManager().getAllRecipesFor(this);
    }

    public Collection<LuRecipe> getRecipes(Level world) {
        return getManagerRecipes(world);
    }

    @Nullable
    public LuRecipe getRecipe(Level world, ResourceLocation id) {
        return getRecipes(world).stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public @NotNull LuRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject jsonObject) {
        LuRecipe recipe = new LuRecipe(id, this);

        recipe.zhu1 = readArray(jsonObject, "zhu1_input", LuRecipeType::readZhuInput);
        recipe.zhu2 = readArray(jsonObject, "zhu2_input", LuRecipeType::readZhuInput);
        recipe.fu1 = readArray(jsonObject, "fu1_input", LuRecipeType::readFuInput);
        recipe.fu2 = readArray(jsonObject, "fu2_input", LuRecipeType::readFuInput);
        recipe.yin = readArray(jsonObject, "yin_input", LuRecipeType::readYinInput);

        recipe.out = readArray(jsonObject, "out", LuRecipeType::readItemOutput);


        return recipe;
    }

    @Override
    public @NotNull LuRecipe fromNetwork(@NotNull ResourceLocation id, FriendlyByteBuf buf) {
        LuRecipe recipe = new LuRecipe(id, this);

        recipe.zhu1 = readList(buf, b -> new ZhuInput(ZhuType.of(b.readVarInt()), b.readInt()));
        recipe.zhu2 = readList(buf, b -> new ZhuInput(ZhuType.of(b.readVarInt()), b.readInt()));
        recipe.fu1 = readList(buf, b -> new FuInput(FuType.of(b.readVarInt()), b.readInt()));
        recipe.fu2 = readList(buf, b -> new FuInput(FuType.of(b.readVarInt()), b.readInt()));
        recipe.yin = readList(buf, b -> new YinInput(YinType.of(b.readVarInt()), b.readInt()));

        recipe.out = readList(buf, b -> new ItemOutput(BuiltInRegistries.ITEM.byId(b.readVarInt()), b.readVarInt()));


        return recipe;
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, LuRecipe recipe) {
        writeList(buf, recipe.zhu1, (b, i) -> {
            buf.writeVarInt(i.zhuType.index);
            buf.writeInt(i.value);
        });
        writeList(buf, recipe.zhu2, (b, i) -> {
            buf.writeVarInt(i.zhuType.index);
            buf.writeInt(i.value);
        });
        writeList(buf, recipe.fu1, (b, i) -> {
            buf.writeVarInt(i.fuType.index);
            buf.writeInt(i.value);
        });
        writeList(buf, recipe.fu2, (b, i) -> {
            buf.writeVarInt(i.fuType.index);
            buf.writeInt(i.value);
        });
        writeList(buf, recipe.yin, (b, i) -> {
            buf.writeVarInt(i.yinType.index);
            buf.writeInt(i.value);
        });
        writeList(buf, recipe.out, (b, i) -> {
            buf.writeVarInt(Item.getId(i.item));
            buf.writeVarInt(i.amount);
        });
    }


    private static ZhuInput readZhuInput(JsonObject json) {
        int value = 1;
        if (json.has("value")) {
            value = readNonNegativeInt(json, "value");
        }

        var zhu = readNonNegativeInt(json, "zhu_type");
        return new ZhuInput(ZhuType.of(zhu), value);
    }

    private static FuInput readFuInput(JsonObject json) {
        int value = 1;
        if (json.has("value")) {
            value = readNonNegativeInt(json, "value");
        }

        var fu = readNonNegativeInt(json, "fu_type");
        return new FuInput(FuType.of(fu), value);
    }

    private static YinInput readYinInput(JsonObject json) {
        int value = 1;
        if (json.has("value")) {
            value = readNonNegativeInt(json, "value");
        }

        var yin = readNonNegativeInt(json, "yin_type");
        return new YinInput(YinType.of(yin), value);
    }

    private static ItemOutput readItemOutput(JsonObject json) {
        ResourceLocation id = readIdentifier(json, "item");
        Item item = BuiltInRegistries.ITEM.getOptional(id).orElseThrow(() -> {
            throw new IllegalArgumentException("Item " + id + " does not exist.");
        });
        int amount = 1;
        if (json.has("amount")) {
            amount = readPositiveInt(json, "amount");
        }
        return new ItemOutput(item, amount);
    }


    private static <T> List<T> readArray(JsonObject json, String element, Function<JsonObject, T> reader) {
        if (!GsonHelper.isArrayNode(json, element)) {
            JsonElement backupObject = json.get(element);
            if (backupObject != null && backupObject.isJsonObject()) {
                return Collections.singletonList(reader.apply(backupObject.getAsJsonObject()));
            } else {
                return Collections.emptyList();
            }
        } else {
            JsonArray array = GsonHelper.getAsJsonArray(json, element);
            JsonObject[] objects = new JsonObject[array.size()];
            for (int i = 0; i < objects.length; i++) {
                objects[i] = array.get(i).getAsJsonObject();
            }
            return Arrays.stream(objects).map(reader).collect(Collectors.toList());
        }
    }

    private static ResourceLocation readIdentifier(JsonObject json, String element) {
        return new ResourceLocation(GsonHelper.getAsString(json, element));
    }

    private static int readPositiveInt(JsonObject json, String element) {
        int x = GsonHelper.getAsInt(json, element);
        if (x <= 0)
            throw new IllegalArgumentException(element + " should be a positive integer.");
        return x;
    }

    private static int readNonNegativeInt(JsonObject json, String element) {
        int x = GsonHelper.getAsInt(json, element);
        if (x < 0)
            throw new IllegalArgumentException(element + " should be a positive integer.");
        return x;
    }

    private static <T> List<T> readList(FriendlyByteBuf buf, Function<FriendlyByteBuf, T> reader) {
        List<T> l = new ArrayList<>();
        int size = buf.readVarInt();
        for (int i = 0; i < size; ++i) {
            l.add(reader.apply(buf));
        }
        return l;
    }

    private static <T> void writeList(FriendlyByteBuf buf, List<T> list, BiConsumer<FriendlyByteBuf, T> writer) {
        buf.writeVarInt(list.size());
        for (T t : list) {
            writer.accept(buf, t);
        }
    }
}
