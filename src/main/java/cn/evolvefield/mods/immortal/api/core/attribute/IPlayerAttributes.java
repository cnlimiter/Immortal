package cn.evolvefield.mods.immortal.api.core.attribute;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.util.TriConsumer;

public interface IPlayerAttributes {
    /**
     * @param player 玩家.
     * @return 一个 0 到 1 的系数，再乘以经验数，告诉我们离下一个级别有多近。
     */
    double expCoeff(Player player);

    /**
     * @param player          玩家.
     * @param playerAttribute IPlayerAttribute 属性实例.
     * @return 属性的数值
     */
    double get(Player player, IPlayerAttribute playerAttribute);

    /**
     * 将输入值添加到属性中。
     * 当数值被运算完，将数据注册并将运算玩的值添加到属性 - see: {@link PlayerAttributes#registerAdder(ResourceLocation, TriConsumer)}
     *
     * @param player          玩家
     * @param playerAttribute Input IPlayerAttribute instance.
     * @param amount          增加的数值（可以是负数）
     */
    void add(Player player, IPlayerAttribute playerAttribute, double amount);

    /**
     * 与 {@link #add(Player, IPlayerAttribute, double)} 类似, 但直接将 IPlayerAttribute 的值设置为输入值并添加到属性，并且不添加任何数据.
     *
     * @param Player          Player instance.
     * @param playerAttribute Input IPlayerAttribute instance.
     * @param amount          设置数值
     */
    void forceSet(Player Player, IPlayerAttribute playerAttribute, double amount);

    /**
     * 将输入的 AttributeModifier 应用于输入的 IPlayerAttribute。直到AttributeModifier被移除前，保持激活状态， {@link #removeModifier(Player, IPlayerAttribute, AttributeModifier)}.
     * 如果输入 AttributeModifier 已经存在并且已经应用，这不会覆盖或重新应用当前活动的。
     * 如果输入的 AttributeModifier 的值改变了；必须删除修饰符然后重新应用 - 属性修饰符不是动态的。
     * AttributeModifier 的区别基于它们的 UUID，但也要使名字也具有唯一性。
     *
     * @param Player            玩家
     * @param playerAttribute   IPlayerAttribute 实例.
     * @param attributeModifier AttributeModifier input.
     * @return 能力实例 (pass-through).
     */
    IPlayerAttributes applyModifier(Player Player, IPlayerAttribute playerAttribute, AttributeModifier attributeModifier);

    /**
     * 如果输入的 AttributeModifier存在，则删除。
     *
     * @param Player            玩家.
     * @param playerAttribute   IPlayerAttribute 实例.
     * @param attributeModifier AttributeModifier input.
     * @return 能力实例 (pass-through).
     */
    IPlayerAttributes removeModifier(Player Player, IPlayerAttribute playerAttribute, AttributeModifier attributeModifier);

    /**
     * @return 往cap中写入数据
     */
    CompoundTag write();

    /**
     * 获取cap中的数据
     *
     * @param tag Input data.
     */
    void read(CompoundTag tag);
}
