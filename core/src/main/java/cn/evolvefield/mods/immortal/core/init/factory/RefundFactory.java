package cn.evolvefield.mods.immortal.core.init.factory;

import cn.evolvefield.mods.immortal.core.api.CoreApi;
import cn.evolvefield.mods.immortal.core.api.data.PlayerData;
import com.github.clevernucleus.dataattributes.api.DataAttributesAPI;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 16:35
 * Description:
 */
public class RefundFactory {

    public static final RefundFactory STORE = new RefundFactory();

    private RefundFactory() {}

    public void forEach(Consumer<BiFunction<PlayerData, Player, Double>> registry) {
        registry.accept((data, player) -> DataAttributesAPI.ifPresent(player, CoreApi.AGE, 18D, value -> data.get(CoreApi.AGE)));
}
}
