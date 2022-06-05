package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.api.ImmortalApi;
import cn.evolvefield.mods.immortal.api.core.attribute.PlayerAttributes;
import cn.evolvefield.mods.immortal.init.config.CommonConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 11:19
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillHandler {
    /**
     * 添加经验事件
     *
     * @param event 事件
     */
    @SubscribeEvent
    public static void onExperienceProcessed(final PlayerXpEvent.XpChange event) {
        Player player = event.getPlayer();

        if (player.level.isClientSide) return;

        int amount = event.getAmount();
        //模组经验，即玩家经验
        int expMod = Math.round((float) amount * CommonConfig.COMMON.experienceSplit.get().floatValue() / 100F);
        //原版经验，即附魔经验
        int expVan = Math.round((float) amount * (100F - CommonConfig.COMMON.experienceSplit.get().floatValue()) / 100F);

        ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> {
            playerAttributes.add(player, PlayerAttributes.EXPERIENCE, expMod);

            float expCoeff = (float) playerAttributes.expCoeff(player);

            if (expCoeff > 0.95F) {
                playerAttributes.add(player, PlayerAttributes.LEVEL, 1);
                playerAttributes.forceSet(player, PlayerAttributes.EXPERIENCE, 0F);
            }
        });

        event.setAmount(expVan);
    }

    /**
     * 治疗事件
     *
     * @param event 事件
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onHeal(final net.minecraftforge.event.entity.living.LivingHealEvent event) {
        if (event.getEntityLiving() instanceof Player player) {

            if (player.level.isClientSide) return;

            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> {
                //event.setAmount(event.getAmount() * (1F + (float)playerAttributes.get(player, PlayerAttributes.HEALTH_REGEN_AMP)));
            });
        }
    }

    /**
     * 近程暴击事件（概率事件）
     *
     * @param event 事件
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCrit(final CriticalHitEvent event) {
        Player player = event.getPlayer();
        Random random = new Random();

        if (player.level.isClientSide) return;

        ImmortalApi.playerAttributes(player).ifPresent(attributes -> {
            event.setDamageModifier(1F + (float) attributes.get(player, PlayerAttributes.MELEE_CRIT_DAMAGE));

            if (random.nextInt(100) < (int) (100F * (float) attributes.get(player, PlayerAttributes.MELEE_CRIT_CHANCE))) {
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DENY);
            }
        });
    }

    /**
     * 生物受伤害事件
     *
     * @param event 事件
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingHurt(final net.minecraftforge.event.entity.living.LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            Random random = new Random();

            if (player.level.isClientSide) return;

            ImmortalApi.playerAttributes(player).ifPresent(playerAttributes -> {
//                if(event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE) || event.getSource().equals(DamageSource.HOT_FLOOR)) {
//                    event.setAmount(event.getAmount() * (1F - (float)playerAttributes.get(player, PlayerAttributes.FIRE_RESISTANCE)));
//                }
//
//                if(event.getSource().equals(DamageSource.LAVA)) {
//                    event.setAmount(event.getAmount() * (1F - (float)playerAttributes.get(player, PlayerAttributes.LAVA_RESISTANCE)));
//                }
//
//                if(event.getSource().isExplosion()) {
//                    event.setAmount(event.getAmount() * (1F - (float)playerAttributes.get(player, PlayerAttributes.EXPLOSION_RESISTANCE)));
//                }
//
//                if(event.getSource().isMagic()) {
//                    event.setAmount(event.getAmount() * (1F - (float)playerAttributes.get(player, PlayerAttributes.POISON_RESISTANCE)));
//                }
//
//                if(event.getSource().equals(DamageSource.WITHER)) {
//                    event.setAmount(event.getAmount() * (1F - (float)playerAttributes.get(player, PlayerAttributes.WITHER_RESISTANCE)));
//                }
//
//                if(event.getSource().equals(DamageSource.DROWN)) {
//                    event.setAmount(event.getAmount() * (1F - (float)playerAttributes.get(player, PlayerAttributes.DROWNING_RESISTANCE)));
//                }
//
//                if(event.getSource().equals(DamageSource.FALL)) {
//                    event.setAmount(event.getAmount() * (1F - (float)playerAttributes.get(player, PlayerAttributes.FALLING_RESISTANCE)));
//                }
//
//                if(event.getSource().isBypassArmor()) {
//                    event.setAmount(event.getAmount() * (1F - (float)playerAttributes.get(player, PlayerAttributes.DAMAGE_REDUCTION)));
//                }
//
//                if(event.getSource().isProjectile()) {
//                    if(random.nextInt(100) < (int)(100D * (float)playerAttributes.get(player, PlayerAttributes.EVASION))) {
//                        event.setCanceled(true);
//                    }
//                }
            });
        }

        if (event.getSource().getDirectEntity() instanceof Player player) {

            ImmortalApi.playerAttributes(player).ifPresent(var -> {
                player.heal(event.getAmount() * (float) var.get(player, PlayerAttributes.LIFESTEAL));
            });
        }
    }

    /**
     * 远程暴击伤害事件
     *
     * @param event 事件
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onProjectileImpact(final net.minecraftforge.event.entity.ProjectileImpactEvent event) {
        if (event.getProjectile().getOwner() instanceof Player player) {
            Random random = new Random();

            ImmortalApi.playerAttributes(player).ifPresent(var -> {
                boolean critical = random.nextInt(100) > (int) (100F * var.get(player, PlayerAttributes.RANGED_CRIT_CHANCE));
                double damage = ((AbstractArrow) event.getProjectile()).getBaseDamage() + var.get(player, PlayerAttributes.RANGED_DAMAGE);

                ((AbstractArrow) event.getProjectile()).setCritArrow(critical);
                ((AbstractArrow) event.getProjectile()).setBaseDamage(critical ? (damage * (1D + var.get(player, PlayerAttributes.RANGED_CRIT_DAMAGE))) : damage);
            });
        }
    }
}
