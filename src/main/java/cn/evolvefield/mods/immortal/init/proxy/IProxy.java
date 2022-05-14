package cn.evolvefield.mods.immortal.init.proxy;

import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 10:11
 * Version: 1.0
 */
public interface IProxy {

    /**
     * @return 带有空条件的可选选项是服务器
     */
    Optional<Player> player();

    default void init() {
    }
}
