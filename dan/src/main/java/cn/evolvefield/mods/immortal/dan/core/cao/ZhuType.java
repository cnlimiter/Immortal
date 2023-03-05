package cn.evolvefield.mods.immortal.dan.core.cao;

import cn.evolvefield.mods.immortal.core.common.cap.dao.DaoType;
import net.minecraft.world.item.ItemStack;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:27
 * Description:
 */
public enum ZhuType {


    HUO_XUE(0, prefix("huo_xue")),
    SHENG_XI(1, prefix("sheng_xi")),
    JING_XUE(2, prefix("jing_xue")),
    JV_YUAN(3, prefix("jv_yuan")),
    LIAN_MO(4, prefix("lian_mo")),
    QU_YAO(5, prefix("qu_yao")),
    YOU_YAO(6, prefix("you_yao")),
    YU_QI(7, prefix("yu_qi")),
    ZHEN_QI(8, prefix("zhen_qi"));


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
