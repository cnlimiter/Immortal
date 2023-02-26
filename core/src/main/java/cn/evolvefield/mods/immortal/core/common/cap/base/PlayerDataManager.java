package cn.evolvefield.mods.immortal.core.common.cap.base;

import cn.evolvefield.mods.immortal.core.api.CoreApi;
import cn.evolvefield.mods.immortal.core.api.EntityAttributeSupplier;
import cn.evolvefield.mods.immortal.core.api.data.PlayerData;
import com.github.clevernucleus.dataattributes.api.attribute.IEntityAttributeInstance;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.sync.ComponentPacketWriter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 10:57
 * Description:
 */
public class PlayerDataManager implements PlayerData, AutoSyncedComponent {
    private static final String
            KEY_SET = "Set",
            KEY_REMOVE = "Remove",
            KEY_RESET = "Reset",
            KEY_MODIFIERS = "Modifiers",
            KEY_MONEY_POINTS = "MoneyPoints";//灵石
    private final Player player;
    private final Map<ResourceLocation, Double> data;
    private int refundPoints, moneyPoints;
    public boolean hasNotifiedLevelUp;

    public PlayerDataManager(final Player player) {
        this.player = player;
        this.data = new HashMap<ResourceLocation, Double>();
        this.hasNotifiedLevelUp = false;
    }

    private void sync(ComponentPacketWriter packet) {
        if(this.player.level.isClientSide) return;
        CoreApi.PLAYER_DATA.sync(this.player, packet);
    }

    private void readModifiersFromNbt(CompoundTag tag, BiFunction<ResourceLocation, Double, Object> function) {
        ListTag modifiers = tag.getList(KEY_MODIFIERS, Tag.TAG_COMPOUND);

        for(int i = 0; i < modifiers.size(); i++) {
            CompoundTag entry = modifiers.getCompound(i);
            ResourceLocation ResourceLocation = new ResourceLocation(entry.getString("Key"));
            final double value = entry.getDouble("Value");
            function.apply(ResourceLocation, value);
        }
    }

    private boolean isValid(final ResourceLocation registryKey, final Consumer<AttributeInstance> ifPresent, final Consumer<AttributeInstance> otherwise) {
        Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(registryKey);

        if(attribute == null) return false;
        AttributeMap container = this.player.getAttributes();
        AttributeInstance instance = container.getInstance(attribute);

        if(instance == null) return false;
        if(instance.getModifier(CoreApi.IM_MODIFIER_ID) != null) {
            ifPresent.accept(instance);
        } else {
            otherwise.accept(instance);
        }

        return true;
    }

    private boolean trySet(final ResourceLocation registryKey, final double valueIn) {
        if(!this.isValid(registryKey, instance -> ((IEntityAttributeInstance)instance).updateModifier(CoreApi.IM_MODIFIER_ID, valueIn), instance -> {
            AttributeModifier modifier = new AttributeModifier(CoreApi.IM_MODIFIER_ID, "IM Attribute", valueIn, AttributeModifier.Operation.ADDITION);
            instance.addPermanentModifier(modifier);
        })) return false;

        this.data.put(registryKey, valueIn);
        return true;
    }

    private boolean tryRemove(final ResourceLocation registryKey, final Consumer<ResourceLocation> consumer) {
        if(!this.isValid(registryKey, instance -> instance.removeModifier(CoreApi.IM_MODIFIER_ID), instance -> {})) return false;
        consumer.accept(registryKey);
        return true;
    }

    @Override
    public double get(final EntityAttributeSupplier attributeIn) {
        if(attributeIn.get() == null) return 0.0D;
        return this.data.getOrDefault(attributeIn.getId(), 0.0D);
    }

    @Override
    public void set(final EntityAttributeSupplier attributeIn, final double valueIn) {
        Attribute attribute = attributeIn.get();

        if(attribute == null) return;
        ResourceLocation ResourceLocation = attributeIn.getId();
        double value = attribute.sanitizeValue(valueIn);

        if(!this.trySet(ResourceLocation, value)) return;
        this.sync((buf, player) -> {
            CompoundTag tag = new CompoundTag();
            CompoundTag entry = new CompoundTag();
            entry.putString("Key", ResourceLocation.toString());
            entry.putDouble("Value", value);
            tag.put(KEY_SET, entry);
            buf.writeNbt(tag);
        });
    }

    @Override
    public void add(final EntityAttributeSupplier attributeIn, final double valueIn) {
        final double value = this.get(attributeIn);
        this.set(attributeIn, value + valueIn);
    }

    @Override
    public void remove(final EntityAttributeSupplier attributeIn) {
        ResourceLocation ResourceLocation = attributeIn.getId();

        if(!this.tryRemove(ResourceLocation, this.data::remove)) return;
        this.sync((buf, player) -> {
            CompoundTag tag = new CompoundTag();
            tag.putString(KEY_REMOVE, ResourceLocation.toString());
            buf.writeNbt(tag);
        });
    }

    @Override
    public void reset() {
        ListTag list = new ListTag();

        for(Iterator<ResourceLocation> iterator = this.data.keySet().iterator(); iterator.hasNext();) {
            ResourceLocation ResourceLocation = iterator.next();

            if(!this.tryRemove(ResourceLocation, id -> iterator.remove())) continue;
            list.add(StringTag.valueOf(ResourceLocation.toString()));
        }

        this.moneyPoints = 0;
        this.sync((buf, player) -> {
            CompoundTag tag = new CompoundTag();
            tag.put(KEY_RESET, list);
            buf.writeNbt(tag);
        });
    }

    @Override
    public void addMoneyPoints(final int pointsIn) {
        this.moneyPoints += pointsIn;
        this.sync((buf, player) -> {
            CompoundTag tag = new CompoundTag();
            tag.putInt(KEY_MONEY_POINTS, this.moneyPoints);
            buf.writeNbt(tag);
        });
    }


    @Override
    public int moneyPoints() {
        return this.moneyPoints;
    }


    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        return player == this.player;
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();

        if(tag == null) return;
        if(tag.contains(KEY_SET)) {
            CompoundTag entry = tag.getCompound(KEY_SET);
            ResourceLocation ResourceLocation = new ResourceLocation(entry.getString("Key"));
            final double value = entry.getDouble("Value");
            this.data.put(ResourceLocation, value);
        }

        if(tag.contains(KEY_REMOVE)) {
            ResourceLocation ResourceLocation = new ResourceLocation(tag.getString(KEY_REMOVE));
            this.data.remove(ResourceLocation);
        }

        if(tag.contains(KEY_RESET)) {
            ListTag list = tag.getList(KEY_RESET, Tag.TAG_STRING);

            for(int i = 0; i < list.size(); i++) {
                ResourceLocation ResourceLocation = new ResourceLocation(list.getString(i));
                this.data.remove(ResourceLocation);
            }

            this.moneyPoints = 0;
            this.hasNotifiedLevelUp = false;
        }

        if(tag.contains(KEY_MODIFIERS)) {
            this.readModifiersFromNbt(tag, this.data::put);
        }


        if(tag.contains(KEY_MONEY_POINTS)) {
            this.moneyPoints = tag.getInt(KEY_MONEY_POINTS);
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.readModifiersFromNbt(tag, this::trySet);
        this.moneyPoints = tag.getInt(KEY_MONEY_POINTS);
        this.hasNotifiedLevelUp = tag.getBoolean("NotifiedLevelUp");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        ListTag modifiers = new ListTag();

        for(ResourceLocation ResourceLocation : this.data.keySet()) {
            CompoundTag entry = new CompoundTag();
            double value = this.data.get(ResourceLocation);
            entry.putString("Key", ResourceLocation.toString());
            entry.putDouble("Value", value);
            modifiers.add(entry);
        }

        tag.put(KEY_MODIFIERS, modifiers);
        tag.putInt(KEY_MONEY_POINTS, this.moneyPoints);
        tag.putBoolean("NotifiedLevelUp", this.hasNotifiedLevelUp);
    }
}
