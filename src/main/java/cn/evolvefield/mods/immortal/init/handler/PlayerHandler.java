package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.api.ImmortalApi;
import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttribute;
import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttributes;
import cn.evolvefield.mods.immortal.api.core.attribute.PlayerAttributes;
import cn.evolvefield.mods.immortal.common.cap.AttributesCap;
import cn.evolvefield.mods.immortal.init.config.CommonConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 11:05
 * Version: 1.0
 */
@Mod.EventBusSubscriber
public class PlayerHandler {

    /**
     * 初始化，第一次进入世界
     *
     * @param player 玩家
     */
    private static void init(final Player player) {
        if (player == null) return;
        if (player.level.isClientSide) return;

        ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> {
            CompoundTag compoundTag = playerAttributes.write();

            if (!compoundTag.getBoolean("Initialised")) {
                getDefaultAttributes(player, playerAttributes);
                compoundTag.putBoolean("Initialised", true);
            }
        });
    }

    /**
     * 配置中的默认属性
     *
     * @param player           玩家
     * @param playerAttributes 属性集合
     */
    private static void getDefaultAttributes(Player player, IPlayerAttributes playerAttributes) {
        playerAttributes.add(player, PlayerAttributes.CONSTITUTION, CommonConfig.COMMON.constitution.get().doubleValue());
        playerAttributes.add(player, PlayerAttributes.STRENGTH, CommonConfig.COMMON.strength.get().doubleValue());
        playerAttributes.add(player, PlayerAttributes.DEXTERITY, CommonConfig.COMMON.dexterity.get().doubleValue());
        playerAttributes.add(player, PlayerAttributes.INTELLIGENCE, CommonConfig.COMMON.intelligence.get().doubleValue());
        playerAttributes.add(player, PlayerAttributes.LUCKINESS, CommonConfig.COMMON.luckiness.get().doubleValue());
        playerAttributes.add(player, PlayerAttributes.MENTALITY, CommonConfig.COMMON.mentality.get().doubleValue());
        playerAttributes.add(player, PlayerAttributes.MAX_HEALTH, (CommonConfig.COMMON.maxHealth.get() ? 20D : 20D - CommonConfig.COMMON.constitution.get().doubleValue()));
    }

    /**
     * 重置属性
     *
     * @param player   玩家
     * @param forceSet 强制设置为0
     */
    public static void reset(final Player player, final boolean forceSet) {
        if (player == null) return;
        if (player.level.isClientSide) return;

        ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> {
            if (forceSet) {
                for (IPlayerAttribute playerAttribute : PlayerAttributes.attributes()) {
                    playerAttributes.forceSet(player, playerAttribute, 0);
                }
            }

            getDefaultAttributes(player, playerAttributes);
        });
    }

    /**
     * 更新玩家属性
     *
     * @param player 玩家
     */
    public static void update(Player player) {
        if (player == null) return;
        if (player.level.isClientSide) return;

        ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> ((AttributesCap) playerAttributes).update(player));
    }

    /**
     * 将数据包从服务器发送到客户端，以使客户端与服务器同步。
     *
     * @param player Player instance.
     */
    public static void sync(Player player) {
        if (player == null) return;
        if (player.level.isClientSide) return;

        ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> ((AttributesCap) playerAttributes).send(player));
    }

    /**
     * 克隆事件，传送，重生时调用
     *
     * @param event 事件
     */
    @SubscribeEvent
    public static void onPlayerCloned(final PlayerEvent.Clone event) {
        Player player = event.getPlayer();
        Player original = event.getOriginal();

        if (player.level.isClientSide) return;

        //死亡时是否重置属性
        if (event.isWasDeath() && CommonConfig.COMMON.resetOnDeath.get()) {
            reset(player, false);
            update(player);
            sync(player);
            return;
        }

        try {
            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes ->
                    ImmortalApi.playerAttributes(original).ifPresent(attributes ->
                            playerAttributes.read(attributes.write())
                    )
            );
        } catch (Exception e) {
            e.fillInStackTrace();
        }

        update(player);
        sync(player);


        //防止传送时死亡血量不同步
        if (event.isWasDeath()) {
            player.heal(player.getMaxHealth());
        }

    }

    /**
     * 当玩家改变维度时触发事件。
     *
     * @param event 事件
     */
    @SubscribeEvent
    public static void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        update(event.getPlayer());
        sync(event.getPlayer());

    }

    /**
     * 玩家重生时触发的事件。
     *
     * @param event 事件
     */
    @SubscribeEvent
    public static void onPlayerRespawn(final PlayerEvent.PlayerRespawnEvent event) {
        update(event.getPlayer());
        sync(event.getPlayer());

    }

    /**
     * 玩家登录时触发的事件。
     *
     * @param event 事件
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();

        init(player);
        update(player);
        sync(player);

        if (player.level.isClientSide) return;

        ImmortalApi.playerAttributes(player).ifPresent(var -> {
            CompoundTag var1 = var.write();

            if (var1.contains("CurrentHealth")) {
                player.setHealth(var1.getFloat("CurrentHealth"));
            }
        });

    }

    /**
     * 玩家登出时触发的事件。
     *
     * @param event 事件
     */
    @SubscribeEvent
    public static void onPlayerLoggedOut(final PlayerEvent.PlayerLoggedOutEvent event) {
        Player player = event.getPlayer();

        if (player.level.isClientSide) return;

        ImmortalApi.playerAttributes(player).ifPresent(iPlayerAttributes -> {
            CompoundTag compoundTag = iPlayerAttributes.write();

            compoundTag.putFloat("CurrentHealth", player.getHealth());
            iPlayerAttributes.read(compoundTag);
        });
    }

    /**
     * 装备变更事件；当装备槽改变时触发。
     *
     * @param event 玩家装备变换事件
     */
    @SubscribeEvent
    public static void onPlayerEquippedItems(final LivingEquipmentChangeEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;

        ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> ((AttributesCap) playerAttributes).putEquipment(event.getSlot(), event.getFrom()));

    }

    /**
     * 玩家tick
     *
     * @param event 玩家刻时间
     */
    @SubscribeEvent
    public static void onPlayerTick(final TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (player.level.isClientSide) return;

        ImmortalApi.playerAttributes(player).ifPresent(attributes -> {
            player.heal((float) attributes.get(player, PlayerAttributes.HP_REGEN));

            AttributesCap attributesCapability = (AttributesCap) attributes;

            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemBySlot = player.getItemBySlot(equipmentSlot);
                ItemStack equipment = attributesCapability.getEquipment(equipmentSlot);

                if (!ItemStack.isSame(itemBySlot, equipment)) {
                    attributesCapability.putEquipment(equipmentSlot, itemBySlot);
                    sync(player);
                }
            }
        });
    }


    /**
     * 触发成就事件
     *
     * @param event 事件
     */
    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent event) {

    }


    /**
     * 玩家死亡事件
     *
     * @param event 事件
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityDrops(LivingDropsEvent event) {

    }

    @SubscribeEvent
    public static void onPlayerDeath(@NotNull LivingDeathEvent event) {

    }
}
