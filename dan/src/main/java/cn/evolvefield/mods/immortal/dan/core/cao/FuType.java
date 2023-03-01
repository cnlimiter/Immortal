package cn.evolvefield.mods.immortal.dan.core.cao;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:27
 * Description:
 */
public enum FuType {


    DAO_YUN(0, prefix("dao_yun")),
    DING_SHA(1, prefix("ding_sha")),
    DUAN_TI(2, prefix("duan_ti")),
    GU_YUAN(3, prefix("gu_yuan")),
    HUN_YUAN(4, prefix("hun_yuan")),
    JIAN_YI(5, prefix("jina_yi")),
    JIE_DU(6, prefix("jie_du")),
    JV_LING(7, prefix("jv_ling")),
    KAI_QIAO(8, prefix("kai_qiao")),
    KAI_WU(9, prefix("kai_wu")),
    LIAN_QI(10, prefix("lian_qi")),
    NING_SHEN(11, prefix("ning_shen")),
    NING_YE(12, prefix("ning_ye")),
    PEI_YUAN(13, prefix("pei_yuan")),
    QIANG_JIN(14, prefix("qiang_jin")),
    QIANG_MU(15, prefix("qiang_mu")),
    QIANG_SHUI(16, prefix("qiang_shui")),
    QIANG_HUO(17, prefix("qiang_huo")),
    QIANG_TU(18, prefix("qiang_tu")),
    QING_XIN(19, prefix("qing_xin")),
    SHEN_XING(20, prefix("shen_xing")),
    TIAO_HE(21, prefix("tiao_he")),
    XI_SUI(22, prefix("xi_sui")),
    YANG_HUN(23, prefix("yang_hun")),
    YANG_QI(24, prefix("yang_qi")),
    YI_SHOU(25, prefix("yi_shou")),
    NING_YING(26, prefix("ning_ying")),
    HUA_SHEN(27, prefix("hua_shen")),
    YI_RONG(28, prefix("yi_rong")),

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

}
