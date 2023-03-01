package cn.evolvefield.mods.immortal.dan.core.cao;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:27
 * Description:
 */
public enum YinType {


    PING(0, prefix("ping")),
    HAN(1, prefix("han")),
    RE(2, prefix("re"));


    public final int index;
    public final String displayName;

    YinType(int index, String displayName) {
        this.index = index;
        this.displayName = displayName;
    }

    private static String prefix(String name){
        return "type.cao.yin." + name;
    }

}
