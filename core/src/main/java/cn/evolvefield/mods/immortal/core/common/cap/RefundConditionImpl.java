package cn.evolvefield.mods.immortal.core.common.cap;

import cn.evolvefield.mods.immortal.core.api.PlayerData;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 11:04
 * Description:
 */
public class RefundConditionImpl {
    private static final List<BiFunction<PlayerData, Player, Double>> REGISTRY = new ArrayList<>();

    public static void add(final BiFunction<PlayerData, Player, Double> refundCondition) {
        REGISTRY.add(refundCondition);
    }

    public static Collection<BiFunction<PlayerData, Player, Double>> get() {
        return REGISTRY;
    }
}
