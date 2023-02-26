package cn.evolvefield.mods.immortal.core.common.cap.dao;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/2/26 1:56
 * Description:
 */
public enum DaoStat {
    CHU(0, 1000,"dao.stat.chu"),
    XIAO(0, 8000,"dao.stat.xiao"),
    TONG(0, 30000,"dao.stat.tong"),
    ZHEN(0, 70000,"dao.stat.zhen"),
    DAO(0, 150000,"dao.stat.dao");

    public final int index;
    public final long value;
    public final String displayName;

    DaoStat(int index, long value,String displayName) {
        this.index = index;
        this.value = value;
        this.displayName = displayName;
    }
}
