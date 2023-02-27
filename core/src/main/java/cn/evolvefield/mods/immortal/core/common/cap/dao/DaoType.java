package cn.evolvefield.mods.immortal.core.common.cap.dao;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/2/26 1:52
 * Description:
 */
public enum DaoType {
    JIN(0, "dao.jin"),
    MU(1, "dao.mu"),
    SHUI(2, "dao.shui"),
    HUO(3, "dao.huo"),
    TU(4, "dao.tu"),
    SHEN(5, "dao.shen"),
    TI(6, "dao.ti"),
    JIAN(7, "dao.jian"),
    QI(8, "dao.qi"),
    ZHEN(9, "dao.zhen"),
    DAN(10, "dao.dan"),
    LIAN_QI(11, "dao.lian_qi")
    ;
    public final int index;
    public final String displayName;

    DaoType(int index, String displayName) {
        this.index = index;
        this.displayName = displayName;
    }

    public static DaoType of(int index){
        return DaoType.values()[index];
    }


}
