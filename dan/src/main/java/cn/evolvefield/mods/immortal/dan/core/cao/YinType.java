package cn.evolvefield.mods.immortal.dan.core.cao;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:27
 * Description:
 */
public enum YinType {


    DEFAULT(0, prefix("default")),
    PING(1, prefix("ping")),
    HAN(2, prefix("han")),
    RE(3, prefix("re"));


    public final int index;
    public final String displayName;

    YinType(int index, String displayName) {
        this.index = index;
        this.displayName = displayName;
    }

    private static String prefix(String name){
        return "type.cao.yin." + name;
    }
    public static YinType of(int index){
        return YinType.values()[index];
    }

}
