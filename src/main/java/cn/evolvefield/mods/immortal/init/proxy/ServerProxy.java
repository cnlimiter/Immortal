package cn.evolvefield.mods.immortal.init.proxy;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.init.data.LockJsonListener;
import cn.evolvefield.mods.immortal.util.FileUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
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
