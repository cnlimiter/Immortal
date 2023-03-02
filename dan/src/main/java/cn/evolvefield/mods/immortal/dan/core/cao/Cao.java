package cn.evolvefield.mods.immortal.dan.core.cao;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/2 0:25
 * Description:
 */
public class Cao {


    private final String id;
    private final int level;
    private final ZhuType zhu;
    private final FuType fu;
    private final YinType yin;
    private final int value;
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

    public static Cao fromJson(JsonObject json){
        String id = json.has("id") ? GsonHelper.getAsString(json, "id") : null;
        int level = json.has("level") ? GsonHelper.getAsInt(json, "level") : 0;
        ZhuType zhu = json.has("zhu") ? ZhuType.of(GsonHelper.getAsInt(json, "zhu")) : null;
        FuType fu = json.has("fu") ? FuType.of(GsonHelper.getAsInt(json, "fu")) : null;
        YinType yin = json.has("yin") ? YinType.of(GsonHelper.getAsInt(json, "yin")) : null;
        int value = json.has("value") ? GsonHelper.getAsInt(json, "value") : 0;
        int cost = json.has("cost") ? GsonHelper.getAsInt(json, "cost") : 0;

        return new Cao(id, level, zhu, fu, yin, value, cost);
    }

    @Override
    public String toString() {
        return "Cao: id=" + id + "level=" + level + "";
    }
}
