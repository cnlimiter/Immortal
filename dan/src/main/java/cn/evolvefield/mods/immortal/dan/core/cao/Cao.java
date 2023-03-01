package cn.evolvefield.mods.immortal.dan.core.cao;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:25
 * Description:
 */
public class Cao {


    private final String id;
    private final ZhuType zhu;
    private final FuType fu;
    private final YinType yin;
    private final int value;
    public Cao(String id, ZhuType zhu, FuType fu, YinType yin, int value){
        this.id = id;
        this.zhu = zhu;
        this.fu = fu;
        this.yin = yin;
        this.value = value;
    }

    public String getNameKey(){
        return "cao." + id;
    }

    public ZhuType getZhu() {
        return zhu;
    }

    public FuType getFu() {
        return fu;
    }

    public YinType getYin() {
        return yin;
    }

    public int getValue() {
        return value;
    }
}
