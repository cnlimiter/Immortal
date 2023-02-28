package cn.evolvefield.mods.immortal.core.api;

import cn.evolvefield.mods.immortal.core.api.data.DaoData;
import cn.evolvefield.mods.immortal.core.api.data.PlayerData;
import cn.evolvefield.mods.immortal.core.init.config.CoreConfig;
import cn.evolvefield.mods.immortal.core.init.config.ImConfig;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

import static cn.evolvefield.mods.immortal.core.Constant.MODID;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 10:03
 * Description:
 */
public final class CoreApi {

    /** Round value to integer property attached to some attributes. */
    public static final String INTEGER_PROPERTY = "integer";
    /** Multiply value by property and append % symbol. */
    public static final String PERCENTAGE_PROPERTY = "percentage";
    /** Multiply value by property. */
    public static final String MULTIPLIER_PROPERTY = "multiplier";
    /** The UUID PlayerEx modifiers use. */
    public static final UUID IM_MODIFIER_ID = UUID.fromString("0f320cdd-8b2e-47a6-917e-adca8f899495");
    public static final ComponentKey<PlayerData> PLAYER_DATA = ComponentRegistry.getOrCreate(new ResourceLocation(MODID, "player_data"), PlayerData.class);
    public static final ComponentKey<DaoData> DAO_DATA = ComponentRegistry.getOrCreate(new ResourceLocation(MODID, "dao_data"), DaoData.class);


    //基础属性
    public static final EntityAttributeSupplier AGE = define("age");
    public static final EntityAttributeSupplier SHOU_YUAN = define("shou_yuan");
    public static final EntityAttributeSupplier ZI_ZHI = define("zi_zhi");
    public static final EntityAttributeSupplier SHEN_SHI = define("shen_shi");
    public static final EntityAttributeSupplier WU_XING = define("wu_xing");
    public static final EntityAttributeSupplier DUN_SU = define("dun_su");

    //附加属性
    public static final EntityAttributeSupplier XIU_SU = define("xiu_su");
    public static final EntityAttributeSupplier XIN_JING = define("xin_jing");
    public static final EntityAttributeSupplier DAN_DU = define("dan_du");
    public static final EntityAttributeSupplier LING_GAN = define("ling_gan");

    //灵根
    public static final EntityAttributeSupplier JIN = define("jin");
    public static final EntityAttributeSupplier MU = define("mu");
    public static final EntityAttributeSupplier SHUI = define("shui");
    public static final EntityAttributeSupplier HUO = define("huo");
    public static final EntityAttributeSupplier TU = define("tu");
    //道境
    public static final EntityAttributeSupplier WU_DAO_DIAN = define("dao_point");
    //灵石
    public static final EntityAttributeSupplier LING_SHI = define("money");



    private static EntityAttributeSupplier define(final String path) {
        return EntityAttributeSupplier.of(new ResourceLocation(MODID, path));
    }


    public static ImConfig getConfig() {
        return AutoConfig.getConfigHolder(CoreConfig.class).get();
    }

}
