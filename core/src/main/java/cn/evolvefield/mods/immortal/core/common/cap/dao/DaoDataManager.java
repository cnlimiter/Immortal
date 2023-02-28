package cn.evolvefield.mods.immortal.core.common.cap.dao;

import cn.evolvefield.mods.immortal.core.api.CoreApi;
import cn.evolvefield.mods.immortal.core.api.data.DaoData;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.sync.ComponentPacketWriter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
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
public class DaoDataManager implements DaoData, AutoSyncedComponent {
    private static final String
            KEY_SET = "Set",
            KEY_RESET = "Reset",
            KEY_MODIFIERS = "Modifiers",
            KEY_DAO_POINTS = "DaoPoints";//道境点
    private final Player player;
    private final Map<DaoType, Double> data;
    private int daoPoints;

    public DaoDataManager(final Player player) {
        this.player = player;
        this.data = new HashMap<DaoType, Double>();
    }

    private void sync(ComponentPacketWriter packet) {
        if(this.player.level.isClientSide) return;
        CoreApi.PLAYER_DATA.sync(this.player, packet);
    }

    private void readModifiersFromNbt(CompoundTag tag, BiFunction<DaoType, Double, Object> function) {
        ListTag modifiers = tag.getList(KEY_MODIFIERS, Tag.TAG_COMPOUND);
        for(int i = 0; i < modifiers.size(); i++) {
            CompoundTag entry = modifiers.getCompound(i);
            DaoType type = DaoType.values()[entry.getInt("Id")];
            final double value = entry.getDouble("Value");
            function.apply(type, value);
        }
    }


    private boolean trySet(final DaoType type, final double valueIn) {
        this.data.put(type, valueIn);
        return true;
    }

    private boolean tryRemove(final DaoType registryKey, final Consumer<DaoType> consumer) {
        consumer.accept(registryKey);
        return true;
    }

    @Override
    public double get(final DaoType daoType) {
        if(daoType == null) return 0.0D;
        return this.data.getOrDefault(daoType, 0.0D);
    }

    @Override
    public void set(final DaoType daoType, final double valueIn) {

        if(daoType == null) return;
        int index = daoType.index;

        if(!this.trySet(daoType, valueIn)) return;
        this.sync((buf, player) -> {
            CompoundTag tag = new CompoundTag();
            CompoundTag entry = new CompoundTag();
            entry.putInt("Id", index);
            entry.putDouble("Value", valueIn);
            tag.put(KEY_SET, entry);
            buf.writeNbt(tag);
        });
    }

    @Override
    public void add(final DaoType daoType, final double valueIn) {
        final double value = this.get(daoType);
        this.set(daoType, value + valueIn);
    }



    @Override
    public void reset() {
        ListTag list = new ListTag();

        for(Iterator<DaoType> iterator = this.data.keySet().iterator(); iterator.hasNext();) {
            DaoType type = iterator.next();

            if(!this.tryRemove(type, id -> iterator.remove())) continue;
            list.add(StringTag.valueOf(type.toString()));
        }

        this.daoPoints = 0;
        this.sync((buf, player) -> {
            CompoundTag tag = new CompoundTag();
            tag.put(KEY_RESET, list);
            buf.writeNbt(tag);
        });
    }

    @Override
    public void addDaoPoints(final int pointsIn) {
        this.daoPoints += pointsIn;
        this.sync((buf, player) -> {
            CompoundTag tag = new CompoundTag();
            tag.putInt(KEY_DAO_POINTS, this.daoPoints);
            buf.writeNbt(tag);
        });
    }


    @Override
    public int daoPoints() {
        return this.daoPoints;
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
            DaoType type = DaoType.of(entry.getInt("Id"));
            final double value = entry.getDouble("Value");
            this.data.put(type, value);
        }


        if(tag.contains(KEY_RESET)) {
            ListTag list = tag.getList(KEY_RESET, Tag.TAG_STRING);

            for(int i = 0; i < list.size(); i++) {
                DaoType type = DaoType.of(list.getInt(i));
                this.data.remove(type);
            }

            this.daoPoints = 0;
        }

        if(tag.contains(KEY_MODIFIERS)) {
            this.readModifiersFromNbt(tag, this.data::put);
        }


        if(tag.contains(KEY_DAO_POINTS)) {
            this.daoPoints = tag.getInt(KEY_DAO_POINTS);
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.readModifiersFromNbt(tag, this::trySet);
        this.daoPoints = tag.getInt(KEY_DAO_POINTS);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        ListTag modifiers = new ListTag();

        for(DaoType type : this.data.keySet()) {
            CompoundTag entry = new CompoundTag();
            double value = this.data.get(type);
            entry.putInt("Id", type.index);
            entry.putDouble("Value", value);
            modifiers.add(entry);
        }

        tag.put(KEY_MODIFIERS, modifiers);
        tag.putInt(KEY_DAO_POINTS, this.daoPoints);
    }
}
