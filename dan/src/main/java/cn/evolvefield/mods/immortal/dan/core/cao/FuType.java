package cn.evolvefield.mods.immortal.dan.core.cao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:27
 * Description:
 */
public enum FuType {

    DEFAULT(0, prefix("default")),
    DAO_YUN(1, prefix("dao_yun")),
    DING_SHA(2, prefix("ding_sha")),
    DUAN_TI(3, prefix("duan_ti")),
    GU_YUAN(4, prefix("gu_yuan")),
    HUN_YUAN(5, prefix("hun_yuan")),
    JIAN_YI(6, prefix("jina_yi")),
    JIE_DU(7, prefix("jie_du")),
    JV_LING(8, prefix("jv_ling")),
    KAI_QIAO(9, prefix("kai_qiao")),
    KAI_WU(10, prefix("kai_wu")),
    LIAN_QI(11, prefix("lian_qi")),
    NING_SHEN(12, prefix("ning_shen")),
    NING_YE(13, prefix("ning_ye")),
    PEI_YUAN(14, prefix("pei_yuan")),
    QIANG_JIN(15, prefix("qiang_jin")),
    QIANG_MU(16, prefix("qiang_mu")),
    QIANG_SHUI(17, prefix("qiang_shui")),
    QIANG_HUO(18, prefix("qiang_huo")),
    QIANG_TU(19, prefix("qiang_tu")),
    QING_XIN(20, prefix("qing_xin")),
    SHEN_XING(21, prefix("shen_xing")),
    TIAO_HE(22, prefix("tiao_he")),
    XI_SUI(23, prefix("xi_sui")),
    YANG_HUN(24, prefix("yang_hun")),
    YANG_QI(25, prefix("yang_qi")),
    YI_SHOU(26, prefix("yi_shou")),
    NING_YING(27, prefix("ning_ying")),
    HUA_SHEN(28, prefix("hua_shen")),
    YI_RONG(29, prefix("yi_rong")),

    ;


    public final int index;
    public final String displayName;

    FuType(int index, String displayName) {
        this.index = index;
        this.displayName = displayName;
    }

    private static String prefix(String name){
        return "type.cao.fu." + name;
    }
    public static FuType of(int index){
        return FuType.values()[index];
    }


}
