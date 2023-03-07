package cn.evolvefield.mods.immortal.dan;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 20:34
 * Description:
 */
public class Constant {
    public static final String ID = "im_dan";

    public static Logger LOGGER = LoggerFactory.getLogger("Dan");

    public static ResourceLocation id(String path) {
        return new ResourceLocation(ID, path);
    }


}
