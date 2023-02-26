package cn.evolvefield.mods.immortal.core.api.util;

import cn.evolvefield.mods.immortal.core.api.CoreApi;
import cn.evolvefield.mods.immortal.core.api.EntityAttributeSupplier;
import com.github.clevernucleus.dataattributes.api.DataAttributesAPI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/27 1:02
 * Description:
 */
public class DataUtil {
    public static void add(Player player, EntityAttributeSupplier supplier, long value){
        var data = CoreApi.PLAYER_DATA.get(player);
        ResourceLocation identifier = new ResourceLocation(supplier.getId().toString());
        EntityAttributeSupplier attribute = EntityAttributeSupplier.of(identifier);
        DataAttributesAPI.ifPresent(player, attribute, 0, a -> {
            data.add(attribute, a + value);
            return 0;
        });
    }

    public static void set(Player player, EntityAttributeSupplier supplier, long value){
        var data = CoreApi.PLAYER_DATA.get(player);
        ResourceLocation identifier = new ResourceLocation(supplier.getId().toString());
        EntityAttributeSupplier attribute = EntityAttributeSupplier.of(identifier);
        DataAttributesAPI.ifPresent(player, attribute, 0, a -> {
            data.set(attribute, value);
            return 0;
        });
    }

    public static void minus(Player player, EntityAttributeSupplier supplier, long value){
        var data = CoreApi.PLAYER_DATA.get(player);
        ResourceLocation identifier = new ResourceLocation(supplier.getId().toString());
        EntityAttributeSupplier attribute = EntityAttributeSupplier.of(identifier);
        DataAttributesAPI.ifPresent(player, attribute, 0, a -> {
            data.set(attribute, a - value);
            return 0;
        });
    }

    public static void multiply(Player player, EntityAttributeSupplier supplier, long value){
        var data = CoreApi.PLAYER_DATA.get(player);
        ResourceLocation identifier = new ResourceLocation(supplier.getId().toString());
        EntityAttributeSupplier attribute = EntityAttributeSupplier.of(identifier);
        DataAttributesAPI.ifPresent(player, attribute, 0, a -> {
            data.set(attribute, a * value);
            return 0;
        });
    }

}
