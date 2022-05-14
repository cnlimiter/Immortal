package cn.evolvefield.mods.immortal.api;

import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttributes;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiFunction;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 10:11
 * Version: 1.0
 */
public class ImmortalApi {

    /**
     * 属性.
     */
    public static final Capability<IPlayerAttributes> PLAYER_ATTRIBUTES = CapabilityManager.get(new CapabilityToken<IPlayerAttributes>() {
    });
    private static final Multimap<ResourceLocation, BiFunction<Player, IPlayerAttributes, String>> TOOLTIPS = ArrayListMultimap.create();

    /**
     * @param player 玩家.
     * @return 玩家属性实例.
     */
    public static LazyOptional<IPlayerAttributes> playerAttributes(Player player) {
        return player.getCapability(PLAYER_ATTRIBUTES, null);
    }


    public static void addTooltip(final ResourceLocation location, BiFunction<Player, IPlayerAttributes, String> attributesStringBiFunction) {
        TOOLTIPS.put(location, attributesStringBiFunction);
    }


    public static Multimap<ResourceLocation, BiFunction<Player, IPlayerAttributes, String>> tooltips() {
        return ImmutableListMultimap.copyOf(TOOLTIPS);
    }

    public static Collection<BiFunction<Player, IPlayerAttributes, String>> getTooltips(final ResourceLocation location) {
        return ImmutableList.copyOf(TOOLTIPS.asMap().getOrDefault(location, new ArrayList<>()));
    }

}
