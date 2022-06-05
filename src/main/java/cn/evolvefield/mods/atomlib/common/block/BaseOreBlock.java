package cn.evolvefield.mods.atomlib.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/6/2 20:49
 * Version: 1.0
 */
public class BaseOreBlock extends BaseBlock {
    private final int minExp;
    private final int maxExp;

    public BaseOreBlock(Material material, Function<Properties, Properties> properties, int minExp, int maxExp) {
        super(material, properties.compose(Properties::requiresCorrectToolForDrops));
        this.minExp = minExp;
        this.maxExp = maxExp;
    }

    public BaseOreBlock(Material material, SoundType sound, float hardness, float resistance, int minExp, int maxExp) {
        this(material, p -> p.sound(sound).strength(hardness, resistance), minExp, maxExp);
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? Mth.nextInt(RANDOM, this.minExp, this.maxExp) : 0;
    }
}
