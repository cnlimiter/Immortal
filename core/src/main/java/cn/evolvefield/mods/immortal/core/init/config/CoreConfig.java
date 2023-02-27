package cn.evolvefield.mods.immortal.core.init.config;

import cn.evolvefield.mods.immortal.core.Constant;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/28 0:29
 * Description:
 */

@Config(name = Constant.MODID)
public class CoreConfig implements ImConfig, ConfigData {

    public static enum Tooltip { DEFAULT, VANILLA, IM; }


}
