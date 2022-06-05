package cn.evolvefield.mods.immortal.init.proxy;

import net.minecraft.world.entity.player.Player;

import java.util.Optional;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 10:11
 * Version: 1.0
 */
public class ServerProxy implements IProxy {






    @Override
    public Optional<Player> player() {
        return Optional.empty();
    }

    @Override
    public void init() {

    }
}
