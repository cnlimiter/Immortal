package cn.evolvefield.mods.immortal;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 10:00
 * Version: 1.0
 */
public class Static {
    public static final String MOD_ID = "immortal";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final ResourceLocation RESOURCES = new ResourceLocation(Static.MOD_ID, "textures/gui/abilities.png");


    public static Path CONFIG_FOLDER ;
}
