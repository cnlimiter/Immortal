package cn.evolvefield.mods.immortal.common.cap;

import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttribute;
import cn.evolvefield.mods.immortal.api.core.attribute.IPlayerAttributes;
import cn.evolvefield.mods.immortal.api.core.attribute.PlayerAttributes;
import cn.evolvefield.mods.immortal.common.net.packets.SyncPlayerAttributesPacket;
import cn.evolvefield.mods.immortal.init.config.CommonConfig;
import cn.evolvefield.mods.immortal.init.handler.PacketHandler;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/28 10:16
 * Version: 1.0
 */
public class AttributesCap implements IPlayerAttributes {
    private final Map<IPlayerAttribute, Float> clientStore;
    private final Map<EquipmentSlot, ItemStack> equipmentStore;
    private final AttributeMap attributeModifierManager;
    private CompoundTag tag;
    private double offset, scale;

    public AttributesCap() {
        AttributeSupplier.Builder builder = AttributeSupplier.builder();
        ListTag listTag = new ListTag();
        CompoundTag compoundTag = new CompoundTag();

        this.tag = new CompoundTag();
        this.tag.put("Attributes", listTag);
        this.tag.put("Modifiers", compoundTag);

        for (IPlayerAttribute playerAttribute : PlayerAttributes.attributes()) {
            putAttribute(playerAttribute, 0D);

            if (playerAttribute.type() == IPlayerAttribute.Type.ALL || playerAttribute.type() == IPlayerAttribute.Type.DATA) {
                builder = builder.add(playerAttribute.get());
            }
        }

        this.offset = 0D;
        this.scale = 0D;
        this.attributeModifierManager = new AttributeMap(builder.build());
        this.clientStore = new HashMap<>();
        this.equipmentStore = new HashMap<>();
        this.equipmentStore.put(EquipmentSlot.HEAD, ItemStack.EMPTY);
        this.equipmentStore.put(EquipmentSlot.CHEST, ItemStack.EMPTY);
        this.equipmentStore.put(EquipmentSlot.LEGS, ItemStack.EMPTY);
        this.equipmentStore.put(EquipmentSlot.FEET, ItemStack.EMPTY);
        this.equipmentStore.put(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
    }

    /**
     * @param player
     * @param playerAttribute
     * @return 基于输入 IPlayerAttribute值的正确的 AttributeModifierManager 实例。
     */
    private AttributeMap getAttributeModifier(Player player, IPlayerAttribute playerAttribute) {
        if (playerAttribute.type() == IPlayerAttribute.Type.GAME) {
            return player.getAttributes();
        } else if (playerAttribute.type() == IPlayerAttribute.Type.ALL || playerAttribute.type() == IPlayerAttribute.Type.DATA) {
            return this.attributeModifierManager;
        }

        return null;
    }

    /**
     * @return 如果属性列表为空，则为真；
     */
    private boolean isAttributeListEmpty() {
        return this.tag.getList("Attributes", 10).isEmpty();
    }

    /**
     * 使用输入的值为输入 IPlayerAttribute 创建一个 CompoundTag 标记。
     *
     * @param playerAttribute
     * @param amount
     */
    private void createAttributeTag(IPlayerAttribute playerAttribute, double amount) {
        CompoundTag var1 = new CompoundTag();

        var1.putString("Name", playerAttribute.toString());
        var1.putDouble("Value", amount);

        this.tag.getList("Attributes", 10).add(var1);
    }

    /**
     * 如果输入 IPlayerAttribute 的 Attribute Tag 不存在，则创建一个 {@link #createAttributeTag(IPlayerAttribute, double)}.
     * 如果输入 IPlayerAttribute 的属性标签确实存在，则将存储值设置为输入值。
     *
     * @param playerAttribute
     * @param amount
     */
    private void putAttribute(IPlayerAttribute playerAttribute, double amount) {
        if (isAttributeListEmpty()) {
            createAttributeTag(playerAttribute, amount);

            return;
        }

        for (Tag var : this.tag.getList("Attributes", 10)) {
            CompoundTag tag1 = (CompoundTag) var;
            String name = tag1.getString("Name");

            if (name.equals(playerAttribute.toString())) {
                tag1.putDouble("Value", amount);

                return;
            }
        }

        createAttributeTag(playerAttribute, amount);
    }

    /**
     * @param playerAttribute
     * @return 输入 IPlayerAttribute 实例的存储值（如果存在）；否则返回输入 IPlayerAttribute 实例的默认值。
     */
    private double getAttribute(IPlayerAttribute playerAttribute) {
        double defaultValue = playerAttribute.get().getDefaultValue();

        if (isAttributeListEmpty()) return defaultValue;

        for (Tag tag : this.tag.getList("Attributes", 10)) {
            CompoundTag tag1 = (CompoundTag) tag;
            String name = tag1.getString("Name");

            if (name.equals(playerAttribute.toString())) return tag1.getDouble("Value");
        }

        return defaultValue;
    }

    /**
     * 将新的 CompountNBT 标签添加到相关自定义列表以保存输入 AttributeModifier；如果标签已经存在，那么什么都不做。如果输入属性的自定义尚不存在，则还创建属性的自定义列表。
     *
     * @param playerAttribute
     * @param modifier
     */
    private void putModifier(IPlayerAttribute playerAttribute, AttributeModifier modifier) {
        CompoundTag modifiers = this.tag.getCompound("Modifiers");
        ListTag listTag = (modifiers.contains(playerAttribute.toString()) ? modifiers.getList(playerAttribute.toString(), 10) : new ListTag());

        for (Tag tag : listTag) {
            CompoundTag var2 = (CompoundTag) tag;
            UUID uuid = var2.getUUID("UUID");
            UUID id = modifier.getId();

            if (uuid.equals(id)) return;
        }

        listTag.add(modifier.save());
        modifiers.put(playerAttribute.toString(), listTag);
    }

    /**
     * 如果存在，则从属性的自定义列表中删除输入的自定义标记。如果在移除后，属性的自定义列表为空，则也移除自定义列表。
     *
     * @param iPlayerAttribute
     * @param attributeModifier
     */
    private void removeModifier(IPlayerAttribute iPlayerAttribute, AttributeModifier attributeModifier) {
        CompoundTag tagCompound = this.tag.getCompound("Modifiers");

        if (!tagCompound.contains(iPlayerAttribute.toString())) return;

        ListTag listTag = tagCompound.getList(iPlayerAttribute.toString(), 10);

        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag var2 = listTag.getCompound(i);
            UUID uuid = var2.getUUID("UUID");
            UUID modifierId = attributeModifier.getId();

            if (uuid.equals(modifierId)) {
                listTag.remove(i);
            }
        }

        if (listTag.isEmpty()) tagCompound.remove(iPlayerAttribute.toString());
    }

    /**
     * 从自定义列表获取所有并且重新修改他们
     *
     * @param player
     */
    private void refreshModifierMap(Player player) {
        CompoundTag modifiers = this.tag.getCompound("Modifiers");
        Multimap<IPlayerAttribute, AttributeModifier> modifierMultimap = ArrayListMultimap.create();

        if (modifiers.isEmpty()) return;

        for (String playerAttri : modifiers.getAllKeys()) {
            ListTag modifiersList = modifiers.getList(playerAttri, 10);
            IPlayerAttribute playerAttribute = PlayerAttributes.fromRegistryName(playerAttri);

            if (playerAttribute == null) return;

            for (Tag tag : modifiersList) {
                CompoundTag compoundTag = (CompoundTag) tag;
                AttributeModifier modifier = AttributeModifier.load(compoundTag);

                modifierMultimap.put(playerAttribute, modifier);
            }
        }

        Multimap<Attribute, AttributeModifier> modifierMultimapGame = HashMultimap.create();
        Multimap<Attribute, AttributeModifier> modifierMultimapAll = HashMultimap.create();

        modifierMultimap.forEach((iPlayerAttribute, attributeModifier) -> {
            if (iPlayerAttribute.type() == IPlayerAttribute.Type.GAME) {
                modifierMultimapGame.put(iPlayerAttribute.get(), attributeModifier);
            } else if (iPlayerAttribute.type() == IPlayerAttribute.Type.ALL || iPlayerAttribute.type() == IPlayerAttribute.Type.DATA) {
                modifierMultimapAll.put(iPlayerAttribute.get(), attributeModifier);
            }
        });

        player.getAttributes().addTransientAttributeModifiers(modifierMultimapGame);
        this.attributeModifierManager.addTransientAttributeModifiers(modifierMultimapAll);
    }

    @Override
    public double expCoeff(Player player) {
        return get(player, PlayerAttributes.EXPERIENCE) / (this.offset + (this.scale * Math.pow(get(player, PlayerAttributes.LEVEL), 2.0D)));
    }

    @Override
    public double get(Player player, IPlayerAttribute attribute) {
        if (player.level.isClientSide) return this.clientStore.getOrDefault(attribute, 0F);

        return Objects.requireNonNull(getAttributeModifier(player, attribute)).getValue(attribute.get());
    }

    @Override
    public void add(Player player, IPlayerAttribute playerAttribute, double amount) {
        double value = getAttribute(playerAttribute) + amount;

        Multimap<Attribute, AttributeModifier> modifierMultimap = HashMultimap.create();
        AttributeModifier modifier = new AttributeModifier(playerAttribute.uuid(), playerAttribute.toString(), value, AttributeModifier.Operation.ADDITION);

        modifierMultimap.put(playerAttribute.get(), modifier);

        Objects.requireNonNull(getAttributeModifier(player, playerAttribute)).addTransientAttributeModifiers(modifierMultimap);
        putAttribute(playerAttribute, value);

        PlayerAttributes.adders().get(playerAttribute.registryName()).forEach(doubleTriConsumer -> doubleTriConsumer.accept(player, this, amount));

        send(player);
    }

    @Override
    public void forceSet(Player player, IPlayerAttribute playerAttribute, double amount) {
        Multimap<Attribute, AttributeModifier> modifierMultimap = HashMultimap.create();
        AttributeModifier modifier = new AttributeModifier(playerAttribute.uuid(), playerAttribute.toString(), 0D, AttributeModifier.Operation.ADDITION);

        modifierMultimap.put(playerAttribute.get(), modifier);

        Objects.requireNonNull(getAttributeModifier(player, playerAttribute)).addTransientAttributeModifiers(modifierMultimap);
        putAttribute(playerAttribute, 0D);

        send(player);
    }

    @Override
    public IPlayerAttributes applyModifier(Player player, IPlayerAttribute playerAttribute, AttributeModifier modifier) {
        if (player == null || playerAttribute == null || modifier == null) return this;

        Multimap<Attribute, AttributeModifier> modifierMultimap = HashMultimap.create();

        modifierMultimap.put(playerAttribute.get(), modifier);

        putModifier(playerAttribute, modifier);
        Objects.requireNonNull(getAttributeModifier(player, playerAttribute)).addTransientAttributeModifiers(modifierMultimap);

        PlayerAttributes.modifiers().get(playerAttribute.registryName()).forEach(triConsumer -> triConsumer.accept(player, this::applyModifier, modifier));

        send(player);

        return this;
    }

    @Override
    public IPlayerAttributes removeModifier(Player player, IPlayerAttribute playerAttribute, AttributeModifier modifier) {
        if (player == null || playerAttribute == null || modifier == null) return this;

        Multimap<Attribute, AttributeModifier> modifierMultimap = HashMultimap.create();

        modifierMultimap.put(playerAttribute.get(), modifier);

        removeModifier(playerAttribute, modifier);
        Objects.requireNonNull(getAttributeModifier(player, playerAttribute)).addTransientAttributeModifiers(modifierMultimap);

        PlayerAttributes.modifiers().get(playerAttribute.registryName()).forEach(triConsumer -> triConsumer.accept(player, this::removeModifier, modifier));

        send(player);

        return this;
    }

    @Override
    public CompoundTag write() {
        return this.tag;
    }

    @Override
    public void read(CompoundTag tag) {
        this.tag = tag;
    }

    /**
     * 在死亡和克隆等事件期间更新和刷新能力数据。开发人员应该避免使用它。
     *
     * @param player Player instance.
     */
    public void update(Player player) {
        for (IPlayerAttribute iPlayerAttribute : PlayerAttributes.attributes()) {
            double attribute = getAttribute(iPlayerAttribute);

            Multimap<Attribute, AttributeModifier> modifierMultimap = HashMultimap.create();
            AttributeModifier modifier = new AttributeModifier(iPlayerAttribute.uuid(), iPlayerAttribute.toString(), attribute, AttributeModifier.Operation.ADDITION);

            modifierMultimap.put(iPlayerAttribute.get(), modifier);

            Objects.requireNonNull(getAttributeModifier(player, iPlayerAttribute)).addTransientAttributeModifiers(modifierMultimap);
        }

        refreshModifierMap(player);

        this.offset = CommonConfig.COMMON.offset.get();
        this.scale = CommonConfig.COMMON.scale.get();
    }

    /**
     * 从服务器接受数据
     *
     * @param tag
     * @param offset
     * @param scale
     */
    public void receive(CompoundTag tag, double offset, double scale) {
        if (tag == null) return;
        if (!tag.contains("Data")) return;

        ListTag tagList = tag.getList("Data", 10);

        for (Tag tag1 : tagList) {
            CompoundTag tag2 = (CompoundTag) tag1;
            IPlayerAttribute attribute = PlayerAttributes.fromRegistryName(tag2.getString("Name"));

            this.clientStore.put(attribute, tag2.getFloat("Value"));
        }

        this.offset = offset;
        this.scale = scale;
    }

    /**
     * 从服务器向客户端发送数据
     *
     * @param player 玩家.
     */
    public void send(Player player) {
        if (player == null) return;
        if (player.level.isClientSide) return;

        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();

        for (IPlayerAttribute iPlayerAttribute : PlayerAttributes.attributes()) {
            CompoundTag compoundTag1 = new CompoundTag();

            compoundTag1.putString("Name", iPlayerAttribute.toString());
            compoundTag1.putFloat("Value", (float) get(player, iPlayerAttribute));
            listTag.add(compoundTag1);
        }

        compoundTag.put("Data", listTag);

        PacketHandler.INSTANCE.sendTo(new SyncPlayerAttributesPacket(compoundTag, this.offset, this.scale), ((ServerPlayer) player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * 将装备存入缓存
     *
     * @param slot
     * @param itemStack
     */
    public void putEquipment(EquipmentSlot slot, ItemStack itemStack) {
        this.equipmentStore.put(slot, itemStack);
    }

    /**
     * @param slot
     * @return 装备槽的物品
     */
    public ItemStack getEquipment(EquipmentSlot slot) {
        return this.equipmentStore.getOrDefault(slot, ItemStack.EMPTY);
    }
}
