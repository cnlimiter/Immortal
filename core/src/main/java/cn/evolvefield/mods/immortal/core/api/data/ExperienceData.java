package cn.evolvefield.mods.immortal.core.api.data;

import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 10:13
 * Description:修为
 */
public interface ExperienceData extends ServerTickingComponent {
    boolean updateExperienceNegationFactor(final int amount);
    void resetExperienceNegationFactor();
}
