package cn.evolvefield.mods.immortal.api.core.lock;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/30 21:41
 * Version: 1.0
 */
public class Requirement {
    public final String name;
    public final String type;
    public final String attribute;
    public final double level;

    public Requirement(String name, String type, String attribute, double level) {
        this.name = name;
        this.type = type;
        this.attribute = attribute;
        this.level = level;
    }

    @Override
    public String toString() {
        return name + "," + type + ":" + attribute + ":" + level;
    }
}
