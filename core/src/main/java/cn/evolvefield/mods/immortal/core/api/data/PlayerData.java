package cn.evolvefield.mods.immortal.core.api.data;

import cn.evolvefield.mods.immortal.core.api.EntityAttributeSupplier;
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
     * Combination of {@link #get(EntityAttributeSupplier)}  and {@link #set(EntityAttributeSupplier, double)}.
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
     * Adds money points to the player.
     * @param pointsIn
     */
    void addMoneyPoints(final int pointsIn);


    /**
     * @return Current money points.
     */
    int moneyPoints();


}
