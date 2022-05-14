package cn.evolvefield.mods.immortal.api.core.attribute;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.api.util.Limit;
import cn.evolvefield.mods.immortal.api.util.TriFunction;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 9:45
 * Version: 1.0
 */
public class PlayerAttributes {

    private static final List<IPlayerAttribute> ATTRIBUTES = new ArrayList<IPlayerAttribute>();
    //模组定义
    //玩家个人经验，区别于原版附魔经验
    public static final IPlayerAttribute EXPERIENCE = registerAttribute(new ResourceLocation(Static.MOD_ID, "experience"),
            UUID.randomUUID(), Limit.none(), IPlayerAttribute.Type.DATA, () -> (new RangedAttribute(newRegistryName("experience"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //玩家等级
    public static final IPlayerAttribute LEVEL = registerAttribute(new ResourceLocation(Static.MOD_ID, "level"),
            UUID.randomUUID(), Limit.none(), IPlayerAttribute.Type.DATA, () -> (new RangedAttribute(newRegistryName("level"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //玩家技能点
    public static final IPlayerAttribute SKILL_POINTS = registerAttribute(new ResourceLocation(Static.MOD_ID, "skill_points"),
            UUID.randomUUID(), Limit.none(), IPlayerAttribute.Type.DATA, () -> (new RangedAttribute(newRegistryName("skill_points"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //生命恢复
    public static final IPlayerAttribute HP_REGEN = registerAttribute(new ResourceLocation(Static.MOD_ID, "health_regen"),
            UUID.randomUUID(), Limit.hold(0.0005D, 0.0005D, 0.005D, 0.5D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("health_regen"),
                    0D, ((-1D) * Double.MAX_VALUE), Double.MAX_VALUE)).setSyncable(true));
    //魔力值
    public static final IPlayerAttribute MP = registerAttribute(new ResourceLocation(Static.MOD_ID, "magic_point"),
            UUID.randomUUID(), Limit.hold(0.0005D, 0.0005D, 0.005D, 0.5D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("magic_point"),
                    200D, ((-1D) * Double.MAX_VALUE), Double.MAX_VALUE)).setSyncable(true));
    //魔力回复
    public static final IPlayerAttribute MP_REGEN = registerAttribute(new ResourceLocation(Static.MOD_ID, "magic_point_regen"),
            UUID.randomUUID(), Limit.hold(0.0005D, 0.0005D, 0.005D, 0.5D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("magic_point_regen"),
                    5D, ((-1D) * Double.MAX_VALUE), Double.MAX_VALUE)).setSyncable(true));
    //力量
    public static final IPlayerAttribute STRENGTH = registerAttribute(new ResourceLocation(Static.MOD_ID, "strength"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.8D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("strength"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //敏捷
    public static final IPlayerAttribute DEXTERITY = registerAttribute(new ResourceLocation(Static.MOD_ID, "dexterity"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.8D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("dexterity"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //精力
    public static final IPlayerAttribute MENTALITY = registerAttribute(new ResourceLocation(Static.MOD_ID, "mentality"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.7D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("mentality"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //智力
    public static final IPlayerAttribute INTELLIGENCE = registerAttribute(new ResourceLocation(Static.MOD_ID, "intelligence"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.7D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("intelligence"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //耐力
    public static final IPlayerAttribute CONSTITUTION = registerAttribute(new ResourceLocation(Static.MOD_ID, "constitution"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.7D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("constitution"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //幸运
    public static final IPlayerAttribute LUCKINESS = registerAttribute(new ResourceLocation(Static.MOD_ID, "luckiness"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.7D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("luckiness"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //    近战暴击伤害
    public static final IPlayerAttribute MELEE_CRIT_DAMAGE = registerAttribute(new ResourceLocation(Static.MOD_ID, "melee_crit_damage"),
            UUID.randomUUID(), Limit.hold(0.05D, 0.05D, 0.3D, 0.4D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("melee_crit_damage"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //    近战暴击几率
    public static final IPlayerAttribute MELEE_CRIT_CHANCE = registerAttribute(new ResourceLocation(Static.MOD_ID, "melee_crit_chance"),
            UUID.randomUUID(), Limit.hold(0.01D, 0.01D, 0.25D, 0.3D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("melee_crit_chance"),
                    0D, 0D, 1D)).setSyncable(true));
    //    远程伤害
    public static final IPlayerAttribute RANGED_DAMAGE = registerAttribute(new ResourceLocation(Static.MOD_ID, "ranged_damage"),
            UUID.randomUUID(), Limit.hold(0.25D, 0.25D, 4D, 0.5D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("ranged_damage"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //    远程暴击伤害
    public static final IPlayerAttribute RANGED_CRIT_DAMAGE = registerAttribute(new ResourceLocation(Static.MOD_ID, "ranged_crit_damage"),
            UUID.randomUUID(), Limit.hold(0.05D, 0.05D, 0.3D, 0.4D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("ranged_crit_damage"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //    远程暴击几率
    public static final IPlayerAttribute RANGED_CRIT_CHANCE = registerAttribute(new ResourceLocation(Static.MOD_ID, "ranged_crit_chance"),
            UUID.randomUUID(), Limit.hold(0.01D, 0.01D, 0.25D, 0.3D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("ranged_crit_chance"),
                    0D, 0D, 1D)).setSyncable(true));
    //    生命偷取
    public static final IPlayerAttribute LIFESTEAL = registerAttribute(new ResourceLocation(Static.MOD_ID, "lifesteal"),
            UUID.randomUUID(), Limit.hold(0.01D, 0.01D, 0.25D, 0.25D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("lifesteal"),
                    0D, 0D, Double.MAX_VALUE)).setSyncable(true));
    //    减伤百分比
    public static final IPlayerAttribute DAMAGE_REDUCTION = registerAttribute(new ResourceLocation(Static.MOD_ID, "damage_reduction"),
            UUID.randomUUID(), Limit.hold(0.01D, 0.01D, 0.25D, 0.3D), IPlayerAttribute.Type.ALL, () -> (new RangedAttribute(newRegistryName("damage_reduction"),
                    0D, ((-1D) * Double.MAX_VALUE), 1D)).setSyncable(true));
    //forge提供
    //重力
    public static final IPlayerAttribute GRAVITY = registerAttribute(new ResourceLocation("forge", "gravity"),
            UUID.randomUUID(), Limit.hold(0.002D, 0.002D, 0.06D, 0.05D), IPlayerAttribute.Type.GAME, ForgeMod.ENTITY_GRAVITY);
    //
    public static final IPlayerAttribute REACH_DISTANCE = registerAttribute(new ResourceLocation("forge", "reach_distance"),
            UUID.randomUUID(), Limit.hold(0.5D, 0.5D, 2D, 0.2D), IPlayerAttribute.Type.GAME, ForgeMod.REACH_DISTANCE);
    //游泳速度
    public static final IPlayerAttribute SWIM_SPEED = registerAttribute(new ResourceLocation("forge", "swim_speed"),
            UUID.randomUUID(), Limit.hold(0.1D, 0.1D, 1D, 0.35D), IPlayerAttribute.Type.GAME, ForgeMod.SWIM_SPEED);
    //原版
    //生命值上限
    public static final IPlayerAttribute MAX_HEALTH = registerAttribute(new ResourceLocation("max_health"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.65D), IPlayerAttribute.Type.GAME, () -> Attributes.MAX_HEALTH);
    //盔甲值
    public static final IPlayerAttribute ARMOR = registerAttribute(new ResourceLocation("armor"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.6D), IPlayerAttribute.Type.GAME, () -> Attributes.ARMOR);
    //盔甲韧性
    public static final IPlayerAttribute ARMOR_TOUGHNESS = registerAttribute(new ResourceLocation("armor_toughness"),
            UUID.randomUUID(), Limit.hold(0.5D, 0.5D, 6D, 0.45D), IPlayerAttribute.Type.GAME, () -> Attributes.ARMOR_TOUGHNESS);
    //击退抗性
    public static final IPlayerAttribute KNOCKBACK_RESISTANCE = registerAttribute(new ResourceLocation("knockback_resistance"),
            UUID.randomUUID(), Limit.hold(0.01D, 0.01D, 0.25D, 0.5D), IPlayerAttribute.Type.GAME, () -> Attributes.KNOCKBACK_RESISTANCE);
    //移动速度
    public static final IPlayerAttribute MOVEMENT_SPEED = registerAttribute(new ResourceLocation("movement_speed"),
            UUID.randomUUID(), Limit.hold(0.004D, 0.004D, 0.1D, 0.35D), IPlayerAttribute.Type.GAME, () -> Attributes.MOVEMENT_SPEED);
    //近战伤害
    public static final IPlayerAttribute MELEE_DAMAGE = registerAttribute(new ResourceLocation("melee_damage"),
            UUID.randomUUID(), Limit.hold(0.25D, 0.25D, 4D, 0.5D), IPlayerAttribute.Type.GAME, () -> Attributes.ATTACK_DAMAGE);
    //攻击速度
    public static final IPlayerAttribute ATTACK_SPEED = registerAttribute(new ResourceLocation("attack_speed"),
            UUID.randomUUID(), Limit.hold(0.25D, 0.25D, 2D, 0.6D), IPlayerAttribute.Type.GAME, () -> Attributes.ATTACK_SPEED);
    //幸运
    public static final IPlayerAttribute LUCK = registerAttribute(new ResourceLocation("luck"),
            UUID.randomUUID(), Limit.hold(1D, 1D, 10D, 0.5D), IPlayerAttribute.Type.GAME, () -> Attributes.LUCK);
    private static final Multimap<ResourceLocation, TriConsumer<Player, IPlayerAttributes, Double>> ADD_CONSUMERS = ArrayListMultimap.create();
    private static final Multimap<ResourceLocation, TriConsumer<Player, TriFunction<Player, IPlayerAttribute, AttributeModifier, IPlayerAttributes>, AttributeModifier>> MOD_CONSUMERS = ArrayListMultimap.create();

    /**
     * 本地化属性名
     *
     * @param type 属性
     * @return 属性名
     */
    public static String newRegistryName(String type) {
        return "attribute.name." + Static.MOD_ID + "." + type;
    }


    /**
     * 使用输入的参数构建并注册一个新的玩家属性。可以静态初始化。
     *
     * @param registerName 以(YourMod.MODID, "attribute_name")形式的属性注册名.
     * @param uuid         属性uuid.
     * @param limit        属性限制项: 这纯粹是供附属使用的，完全不影响属性的功能.
     * @param type         属性的体现数据; 如果有疑问查看 {@link IPlayerAttribute.Type#ALL}. 通过forge注册新的属性如果 {@link IPlayerAttribute.Type#GAME} 不是输入值.
     * @param supplier     属性的功能方法.
     * @return 一个完整态的创建属性的方法.
     */
    public static IPlayerAttribute registerAttribute(final ResourceLocation registerName, final UUID uuid, final Limit limit, final IPlayerAttribute.Type type, final Supplier<Attribute> supplier) {
        IPlayerAttribute var0 = new PlayerAttribute(registerName, uuid, limit, type, supplier);

        if (type == IPlayerAttribute.Type.ALL || type == IPlayerAttribute.Type.DATA) {
            Attribute var1 = supplier.get();

            var1.setRegistryName(registerName);

            var0 = new PlayerAttribute(registerName, uuid, limit, type, () -> var1);
        }

        ATTRIBUTES.add(var0);

        return var0;
    }

    /**
     * 添加属性之间的关系 {@link IPlayerAttributes#add(Player, IPlayerAttribute, double)}
     *
     * @param registerName IPlayerAttribute 的注册名称，可以手动定义或从{@link IPlayerAttribute#registryName()}获取.
     * @param consumer     TriConsumer 将接收玩家实例、属性能力和注册名以及属性的数量.
     */
    public static void registerAdder(final ResourceLocation registerName, final TriConsumer<Player, IPlayerAttributes, Double> consumer) {
        ADD_CONSUMERS.put(registerName, consumer);
    }

    /**
     * 修改属性之间的关系 {@link IPlayerAttributes#applyModifier(Player, IPlayerAttribute, AttributeModifier)}
     *
     * @param registerName IPlayerAttribute 的注册名称，可以手动定义或从 {@link IPlayerAttribute#registryName()}.
     * @param consumer     TriConsumer 将接收玩家实例、属性能力和注册名.
     */
    public static void registerModifier(final ResourceLocation registerName, final TriConsumer<Player, TriFunction<Player, IPlayerAttribute, AttributeModifier, IPlayerAttributes>, AttributeModifier> consumer) {
        MOD_CONSUMERS.put(registerName, consumer);
    }

    /**
     * 通过注册名获取实例
     *
     * @param registerName 注册名的string : {@link ResourceLocation#toString()}
     * @return 如果存在输入的注册名称，则返回IPlayerAttribute 实例；如果不是，则返回 null
     */
    public static IPlayerAttribute fromRegistryName(String registerName) {
        for (IPlayerAttribute attribute : ATTRIBUTES) {
            if (attribute.toString().equals(registerName)) return attribute;
        }

        return null;
    }

    /**
     * @return ATTRIBUTES 列表的不可变副本
     */
    public static Collection<IPlayerAttribute> attributes() {
        return ImmutableList.copyOf(ATTRIBUTES);
    }

    /**
     * @return ADD_CONSUMERS Multimap 的不可变副本。
     */
    public static Multimap<ResourceLocation, TriConsumer<Player, IPlayerAttributes, Double>> adders() {
        return ImmutableListMultimap.copyOf(ADD_CONSUMERS);
    }

    /**
     * @return MOD_CONSUMERS Multimap 的不可变副本。
     */
    public static Multimap<ResourceLocation, TriConsumer<Player, TriFunction<Player, IPlayerAttribute, AttributeModifier, IPlayerAttributes>, AttributeModifier>> modifiers() {
        return ImmutableListMultimap.copyOf(MOD_CONSUMERS);
    }
}
