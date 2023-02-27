package cn.evolvefield.mods.immortal.core.init.factory;

import cn.evolvefield.mods.immortal.core.Core;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/28 0:17
 * Description:
 */
public class ScreenFactory implements ExtendedScreenHandlerFactory {
    private final int pageId;

    public ScreenFactory(final int pageId) {
        this.pageId = pageId;
    }

    public static ExtendedScreenHandlerType<Handler> type() {
        return new ExtendedScreenHandlerType<>((syncId, inv, buf) -> new ScreenFactory.Handler(syncId, inv, buf.readInt()));
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
        return new Handler(syncId, inventory, this.pageId);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("im_core.gui.page.attributes.title");
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeInt(this.pageId);
    }

    public static class Handler extends AbstractContainerMenu {
        public final int pageId;

        public Handler(int syncId, Inventory inventory, int pageId) {
            super(Core.IM_SCREEN, syncId);
            this.pageId = pageId;
        }


        @Override
        public @NotNull ItemStack quickMoveStack(Player player, int index) {
            return this.slots.get(index).getItem();
        }

        @Override
        public boolean stillValid(Player player) {
            return true;
        }
    }
}
