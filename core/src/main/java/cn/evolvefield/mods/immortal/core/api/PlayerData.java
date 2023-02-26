package cn.evolvefield.mods.immortal.core.api;

import dev.onyxstudios.cca.api.v3.component.Component;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 10:11
 * Description:
 */
public interface PlayerData extends Component {

    /**
     * @param attributeIn
     * @return the cached attribute modifier value, or 0 if it doesn't exist.
     */
    double get(final EntityAttributeSupplier attributeIn);

    /**
     * Sets the attribute modifier value for this attribute, and creates it if it doesn't exist.
     * @param attributeIn
     * @param valueIn
     */
    void set(final EntityAttributeSupplier attributeIn, final double valueIn);

    /**
     * Combination of {@link #get(net.minecraft.world.entity.ai.attributes.Attribute)} and {@link #set(net.minecraft.world.entity.ai.attributes.Attribute, double)}.
     * @param attributeIn
     * @param valueIn
     */
    void add(final EntityAttributeSupplier attributeIn, final double valueIn);

    /**
     * Removes the attribute modifier if it exists.
     * @param attributeIn
     */
    void remove(final EntityAttributeSupplier attributeIn);

    /**
     * Resets all data (including attribute modifiers) to their defaults - for modifiers this means removing them and deleting the cache.
     */
    void reset();

    /**
     * Adds skill points to the player.
     * @param pointsIn
     */
    void addSkillPoints(final int pointsIn);

    /**
     * Adds refund points to the player.
     * @param pointsIn
     * @return
     */
    int addRefundPoints(final int pointsIn);

    /**
     * @return Current skill points.
     */
    int skillPoints();

    /**
     * @return Current refund points.
     */
    int refundPoints();
}
