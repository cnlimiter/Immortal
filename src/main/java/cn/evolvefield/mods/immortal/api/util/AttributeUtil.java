package cn.evolvefield.mods.immortal.api.util;

import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttribute;
import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/29 15:52
 * Version: 1.0
 */
public class AttributeUtil {
    /**
     * Adds currentValue and value together with diminishing returns as they approach limit.
     *
     * @param currentValue double Current Value (Input 1).
     * @param value double Adding/Subtracting Value (Input 2).
     * @param limit double Limit.
     * @return double Diminishing returns output.
     */
    public static double dim(final double currentValue, final double value, final double limit) {
        if (limit <= 0D) return 0D;
        if (currentValue >= 0D && value >= 0D) return limit * (1.0D - ((1.0D - (currentValue / limit)) * (1.0D - (value / limit))));
        if (currentValue >= 0D && value <= 0D) return limit * (1.0D - ((1.0D - (currentValue / limit)) / (1.0D - ((-value) / limit))));
        if (currentValue <= 0D && value >= 0D) return limit * (1.0D - ((1.0D - (value / limit)) / (1.0D - ((-currentValue) / limit))));

        return 0D;
    }

    /**
     * Adds the input IPlayerAttribute's current value with the input value, using {@link #dim(double, double, double)} and the input limit.
     *
     * @param playerAttributes Capability instance.
     * @param player           Player instance.
     * @param playerAttribute  IPlayerAttribute to add to.
     * @param value             Amount to add (can be negative to subtract).
     * @param limit             Limit.
     */
    public static void add(IPlayerAttributes playerAttributes, Player player, IPlayerAttribute playerAttribute, double value, double limit) {
        double var0 = playerAttributes.get(player, playerAttribute);
        double var1 = dim(var0, value, limit) - var0;

        playerAttributes.add(player, playerAttribute, var1);
    }

    /**
     * Applies or removes the input attribute modifier, but uses {@link #dim(double, double, double)} for ADDITION functions. Similar to {@link #add(IPlayerAttributes, Player, IPlayerAttribute, double, double)}.
     *
     * @param attributesTriFunction {@link IPlayerAttributes#applyModifier(Player, IPlayerAttribute, AttributeModifier)} or {@link IPlayerAttributes#removeModifier(Player, IPlayerAttribute, AttributeModifier)}.
     * @param player Player instance.
     * @param playerAttribute IPlayerAttribute.
     * @param modifier AttributeModifier.
     * @param multiplier AttributeModifier value multiplier.
     * @param limit Diminishing value limit (in case of modifier ADDITION function).
     */
    public static void apply(TriFunction<Player, IPlayerAttribute, AttributeModifier, IPlayerAttributes> attributesTriFunction, Player player, IPlayerAttribute playerAttribute, AttributeModifier modifier, double multiplier, double limit) {
        double var0 = modifier.getAmount() * multiplier;

        if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
            double var1 = attributesTriFunction.apply(null, null, null).get(player, playerAttribute);
            var0 = dim(var1, modifier.getAmount() * multiplier, limit) - var1;
        }

        AttributeModifier var1 = new AttributeModifier(modifier.getId(), modifier.getName(), var0, modifier.getOperation());

        attributesTriFunction.apply(player, playerAttribute, var1);
    }

    /**
     * Applies or removes the input attribute modifier, but multiplies the modifier value with the input multiplier.
     *
     * @param attributesTriFunction {@link IPlayerAttributes#applyModifier(Player, IPlayerAttribute, AttributeModifier)} or {@link IPlayerAttributes#removeModifier(Player, IPlayerAttribute, AttributeModifier)}.
     * @param player Player instance.
     * @param playerAttribute IPlayerAttribute.
     * @param modifier AttributeModifier.
     * @param multiplier AttributeModifier value multiplier.
     */
    public static void apply(TriFunction<Player, IPlayerAttribute, AttributeModifier, IPlayerAttributes> attributesTriFunction, Player player, IPlayerAttribute playerAttribute, AttributeModifier modifier, double multiplier) {
        AttributeModifier var0 = new AttributeModifier(modifier.getId(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation());

        attributesTriFunction.apply(player, playerAttribute, var0);
    }
}
