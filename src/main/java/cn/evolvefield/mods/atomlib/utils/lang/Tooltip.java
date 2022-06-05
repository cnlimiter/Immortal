package cn.evolvefield.mods.atomlib.utils.lang;

import net.minecraft.ChatFormatting;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/2 12:45
 * Version: 1.0
 */
public class Tooltip extends Localizable {
    public Tooltip(String key) {
        super(key, ChatFormatting.GRAY);
    }

    public Tooltip(String key, ChatFormatting defaultColor) {
        super(key, defaultColor);
    }
}
