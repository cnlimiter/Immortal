package cn.evolvefield.mods.immortal.dan.core.cao;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:27
 * Description:
 */
public enum ZhuType {

    DEFAULT(0, prefix("default")),
    HUO_XUE(1, prefix("huo_xue")),
    SHENG_XI(2, prefix("sheng_xi")),
    JING_XUE(3, prefix("jing_xue")),
    JV_YUAN(4, prefix("jv_yuan")),
    LIAN_MO(5, prefix("lian_mo")),
    QU_YAO(6, prefix("qu_yao")),
    YOU_YAO(7, prefix("you_yao")),
    YU_QI(8, prefix("yu_qi")),
    ZHEN_QI(9, prefix("zhen_qi"));


    public final int index;
    public final String displayName;

    ZhuType(int index, String displayName) {
        this.index = index;
        this.displayName = displayName;
    }

    private static String prefix(String name){
        return "type.cao.zhu." + name;
    }

    public static ZhuType of(int index){
        return ZhuType.values()[index];
    }

}
