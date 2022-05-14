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
     * Adds par0 and par1 together with diminishing returns as they approach par2.
     *
     * @param par0 double Current Value (Input 1).
     * @param par1 double Adding/Subtracting Value (Input 2).
     * @param par2 double Limit.
     * @return double Diminishing returns output.
     */
    public static double dim(final double par0, final double par1, final double par2) {
        if (par2 <= 0D) return 0D;
        if (par0 >= 0D && par1 >= 0D) return par2 * (1.0D - ((1.0D - (par0 / par2)) * (1.0D - (par1 / par2))));
        if (par0 >= 0D && par1 <= 0D) return par2 * (1.0D - ((1.0D - (par0 / par2)) / (1.0D - ((-par1) / par2))));
        if (par0 <= 0D && par1 >= 0D) return par2 * (1.0D - ((1.0D - (par1 / par2)) / (1.0D - ((-par0) / par2))));

        return 0D;
    }

    /**
     * Adds the input IPlayerAttribute's current value with the input value, using {@link #dim(double, double, double)} and the input limit.
     *
     * @param playerAttributes Capability instance.
     * @param player           Player instance.
     * @param playerAttribute  IPlayerAttribute to add to.
     * @param par3             Amount to add (can be negative to subtract).
     * @param par4             Limit.
     */
    public static void add(IPlayerAttributes playerAttributes, Player player, IPlayerAttribute playerAttribute, double par3, double par4) {
        double var0 = playerAttributes.get(player, playerAttribute);
        double var1 = dim(var0, par3, par4) - var0;

        playerAttributes.add(player, playerAttribute, var1);
    }

    /**
     * Applies or removes the input attribute modifier, but uses {@link #dim(double, double, double)} for ADDITION functions. Similar to {@link #add(IPlayerAttributes, Player, IPlayerAttribute, double, double)}.
     *
     * @param par0 {@link IPlayerAttributes#applyModifier(Player, IPlayerAttribute, AttributeModifier)} or {@link IPlayerAttributes#removeModifier(Player, IPlayerAttribute, AttributeModifier)}.
     * @param par1 Player instance.
     * @param par2 IPlayerAttribute.
     * @param par3 AttributeModifier.
     * @param par4 AttributeModifier value multiplier.
     * @param par5 Diminishing value limit (in case of modifier ADDITION function).
     */
    public static void apply(TriFunction<Player, IPlayerAttribute, AttributeModifier, IPlayerAttributes> par0, Player par1, IPlayerAttribute par2, AttributeModifier par3, double par4, double par5) {
        double var0 = par3.getAmount() * par4;

        if (par3.getOperation() == AttributeModifier.Operation.ADDITION) {
            double var1 = par0.apply(null, null, null).get(par1, par2);
            var0 = dim(var1, par3.getAmount() * par4, par5) - var1;
        }

        AttributeModifier var1 = new AttributeModifier(par3.getId(), par3.getName(), var0, par3.getOperation());

        par0.apply(par1, par2, var1);
    }

    /**
     * Applies or removes the input attribute modifier, but multiplies the modifier value with the input multiplier.
     *
     * @param par0 {@link IPlayerAttributes#applyModifier(Player, IPlayerAttribute, AttributeModifier)} or {@link IPlayerAttributes#removeModifier(Player, IPlayerAttribute, AttributeModifier)}.
     * @param par1 Player instance.
     * @param par2 IPlayerAttribute.
     * @param par3 AttributeModifier.
     * @param par4 AttributeModifier value multiplier.
     */
    public static void apply(TriFunction<Player, IPlayerAttribute, AttributeModifier, IPlayerAttributes> par0, Player par1, IPlayerAttribute par2, AttributeModifier par3, double par4) {
        AttributeModifier var0 = new AttributeModifier(par3.getId(), par3.getName(), par3.getAmount() * par4, par3.getOperation());

        par0.apply(par1, par2, var0);
    }
}
