package cn.evolvefield.mods.immortal.dan.core.cao;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.Map;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:25
 * Description:
 */
public class Cao {


    @Expose
    private final String id;
    @Expose
    private final int level;
    @Expose
    private final ZhuType zhu;
    @Expose
    private final FuType fu;
    @Expose
    private final YinType yin;
    @Expose
    private final int value;//药力
    @Expose
    private final int cost;
    public Cao(String id, int level, ZhuType zhu, FuType fu, YinType yin, int value, int cost){
        this.id = id;
        this.level = level;
        this.zhu = zhu;
        this.fu = fu;
        this.yin = yin;
        this.value = value;
        this.cost = cost;
    }

    public String getNameKey(){
        return "cao." + id;
    }

    public String getId() {
        return id;
    }
    public int getLevel() {
        return level;
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

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Cao: id=" + id + "level=" + level + "";
    }


    public Cao fromNbt(CompoundTag tag){
        String id = tag.contains("id") ? tag.getString("id") : "null";
        int level = tag.contains("level") ? tag.getInt("level") : 0;
        ZhuType zhu = tag.contains("zhu") ? ZhuType.of(tag.getInt("zhu")) : ZhuType.DEFAULT;
        FuType fu = tag.contains("fu") ? FuType.of(tag.getInt("fu")) : FuType.DEFAULT;
        YinType yin = tag.contains("yin") ? YinType.of(tag.getInt("yin")) : YinType.DEFAULT;
        int value = tag.contains("value") ? tag.getInt("value") : 0;
        int cost = tag.contains("cost") ? tag.getInt("cost") : 0;

        return new Cao(id, level, zhu, fu, yin, value, cost);
    }

    public void toNbt(CompoundTag tag){
        tag.putString("id", id);
        tag.putInt("level", level);
        tag.putInt("zhu", zhu.index);
        tag.putInt("fu", fu.index);
        tag.putInt("yin", yin.index);
        tag.putInt("value", value);
        tag.putInt("cost", cost);
    }
}
