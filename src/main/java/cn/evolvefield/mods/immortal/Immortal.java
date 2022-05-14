package cn.evolvefield.mods.immortal;

import cn.evolvefield.mods.immortal.init.config.CommonConfig;
import cn.evolvefield.mods.immortal.init.data.LockJsonListener;
import cn.evolvefield.mods.immortal.init.proxy.ClientProxy;
import cn.evolvefield.mods.immortal.init.proxy.IProxy;
import cn.evolvefield.mods.immortal.init.proxy.ServerProxy;
import cn.evolvefield.mods.immortal.util.FileUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Static.MOD_ID)
public class Immortal {

    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Immortal() {
        CommonConfig.register();

        PROXY.init();

        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::onSetUpEvent);


        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(this::onServerStarted);
    }


    public void onSetUpEvent(FMLCommonSetupEvent event) {

        Static.CONFIG_FOLDER = FileUtil.checkFolder(FMLPaths.CONFIGDIR.get().resolve("immortal"));

    }

    public void onServerStarted(ServerStartedEvent event){
        LockJsonListener.init();
    }




}
