package cn.evolvefield.mods.immortal.init.proxy;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Optional;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 10:11
 * Version: 1.0
 */
public class ClientProxy implements IProxy {


    public static final KeyMapping openKey = new KeyMapping("key.abilities", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, 71, "Immortal");


    public void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //Register entity rendering handlers
        //event.registerLayerDefinition(PodModel.LAYER_LOCATION, PodModel::createBodyLayer);
    }


    public void onClientSetUpEvent(FMLClientSetupEvent event) {
        //ModMenus.onClientSetup();
        //ModEntities.onClientSetup();
        ClientRegistry.registerKeyBinding(openKey);
    }

    @Override
    public Optional<Player> player() {
        return Optional.ofNullable(Minecraft.getInstance().player);
    }

    @Override
    public void init() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::registerLayer);
        modBus.addListener(this::onClientSetUpEvent);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

    }
}
