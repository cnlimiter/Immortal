package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttribute;
import cn.evolvefield.mods.immortal.api.core.attribute.PlayerAttributes;
import cn.evolvefield.mods.immortal.api.util.AttributeUtil;
import cn.evolvefield.mods.immortal.init.mixin.AccessorRangedAttribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/30 16:35
 * Version: 1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeHandler {

    @SubscribeEvent
    public static void init(FMLLoadCompleteEvent event) {

        //取消原版最大生命值的上限
        final Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(ResourceLocation.tryParse("minecraft:generic.max_health"));
        if (attribute != null) {
            final AccessorRangedAttribute accessor = (AccessorRangedAttribute) attribute;
            accessor.immortal$setMaxValue(Double.MAX_VALUE);
        }


        //
        event.enqueueWork(() -> {
            //耐力 + 最大生命值 和 击退抗性
            PlayerAttributes.registerAdder(PlayerAttributes.CONSTITUTION.registryName(), (player, playerAttributes, amount) -> {
                playerAttributes.add(player, PlayerAttributes.MAX_HEALTH, amount);
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.KNOCKBACK_RESISTANCE, amount * 0.01D, 1.0D);
            });
            //力量 + 近战伤害 和 护甲
            PlayerAttributes.registerAdder(PlayerAttributes.STRENGTH.registryName(), (player, playerAttributes, amount) -> {
                playerAttributes.add(player, PlayerAttributes.MELEE_DAMAGE, amount * 0.25D);
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.ARMOR, amount * 0.25D, 100D);
            });
            //敏捷 + 攻击速度 和 移动速度 和 近战暴击伤害
            PlayerAttributes.registerAdder(PlayerAttributes.DEXTERITY.registryName(), (player, playerAttributes, amount) -> {
                playerAttributes.add(player, PlayerAttributes.RANGED_DAMAGE, amount * 0.25D);
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.ATTACK_SPEED, amount * 0.1D, 8.0D);
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.MOVEMENT_SPEED, amount * 0.004D, 1.0D);
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.MELEE_CRIT_DAMAGE, amount * 0.05D, 10.0D);
            });
            //智力 + 远程暴击伤害 和 生命偷取
            PlayerAttributes.registerAdder(PlayerAttributes.INTELLIGENCE.registryName(), (player, playerAttributes, amount) -> {
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.RANGED_CRIT_DAMAGE, amount * 0.05D, 10.0D);
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.LIFESTEAL, amount * 0.02D, 10.0D);
            });
            //幸运 + 远程暴击概率 和 进程暴击概率
            PlayerAttributes.registerAdder(PlayerAttributes.LUCKINESS.registryName(), (player, playerAttributes, amount) -> {
                playerAttributes.add(player, PlayerAttributes.LUCK, amount);
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.MELEE_CRIT_CHANCE, amount * 0.02D, 1.0D);
                AttributeUtil.add(playerAttributes, player, PlayerAttributes.RANGED_CRIT_CHANCE, amount * 0.02D, 1.0D);
            });
            //最大生命值
            PlayerAttributes.registerAdder(PlayerAttributes.MAX_HEALTH.registryName(), (player, playerAttributes, amount) -> {
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            });

            PlayerAttributes.registerModifier(PlayerAttributes.CONSTITUTION.registryName(), (player, playerAttributes, amount) -> {
                playerAttributes.apply(player, PlayerAttributes.MAX_HEALTH, amount);
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.KNOCKBACK_RESISTANCE, amount, 0.01D, 1.0D);
            });
            PlayerAttributes.registerModifier(PlayerAttributes.STRENGTH.registryName(), (player, playerAttributes, amount) -> {
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.MELEE_DAMAGE, amount, 0.25D);
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.ARMOR, amount, 0.25D, 100D);
            });
            PlayerAttributes.registerModifier(PlayerAttributes.DEXTERITY.registryName(), (player, playerAttributes, amount) -> {
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.RANGED_DAMAGE, amount, 0.25D);
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.ATTACK_SPEED, amount, 0.1D, 8.0D);
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.MOVEMENT_SPEED, amount, 0.004D, 1.0D);
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.MELEE_CRIT_DAMAGE, amount, 0.05D, 10.0D);
            });
            PlayerAttributes.registerModifier(PlayerAttributes.INTELLIGENCE.registryName(), (player, playerAttributes, amount) -> {
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.RANGED_CRIT_DAMAGE, amount, 0.05D, 10.0D);
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.LIFESTEAL, amount, 0.02D, 10.0D);
            });
            PlayerAttributes.registerModifier(PlayerAttributes.LUCKINESS.registryName(), (player, playerAttributes, amount) -> {
                playerAttributes.apply(player, PlayerAttributes.LUCK, amount);
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.MELEE_CRIT_CHANCE, amount, 0.02D, 1.0D);
                AttributeUtil.apply(playerAttributes, player, PlayerAttributes.RANGED_CRIT_CHANCE, amount, 0.02D, 1.0D);
            });
            PlayerAttributes.registerModifier(PlayerAttributes.MAX_HEALTH.registryName(), (player, playerAttributes, amount) -> {
                if (player.getHealth() > player.getMaxHealth()) {
                    player.setHealth(player.getMaxHealth());
                }
            });
        });

    }

    @SubscribeEvent
    public static void registerAttributes(final RegistryEvent.Register<Attribute> par0) {
        for (IPlayerAttribute playerAttribute : PlayerAttributes.attributes()) {
            if (playerAttribute.type() == IPlayerAttribute.Type.ALL || playerAttribute.type() == IPlayerAttribute.Type.DATA) {
                par0.getRegistry().register(playerAttribute.get());
            }
        }
    }
}
