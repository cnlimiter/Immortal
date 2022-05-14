package cn.evolvefield.mods.immortal.api.core.attribute;

import cn.evolvefield.mods.immortal.api.util.Limit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.UUID;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 9:27
 * Version: 1.0
 */
public interface IPlayerAttribute {
    /**
     * @return 属性数据行为；也可以用来区分战利品属性和数据属性
     */
    Type type();

    /**
     * @return 属性的uuid
     */
    UUID uuid();

    /**
     * @return 对属性的限制，以供附属使用
     */
    Limit limit();

    /**
     * @return 应以 (YourMod.MODID, "name_of_attribute") 形式注册. 例子: {@link PlayerAttributes#EXPERIENCE} is (Static.MOD_ID, "experience").
     */
    ResourceLocation registryName();

    /**
     * @return 基于 {@link #type()}.
     */
    Attribute get();

    /**
     * 这些是用于定义属性的类型。有关详细信息，请参阅文档。
     */
    enum Type {
        GAME, DATA, ALL, ITEM;
    }
}
