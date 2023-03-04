package cn.evolvefield.mods.immortal.dan.init.factory;

import cn.evolvefield.mods.immortal.dan.common.block.LuBlock;
import cn.evolvefield.mods.multiblocklib.api.MultiblockLib;
import cn.evolvefield.mods.multiblocklib.api.pattern.MultiblockPatternKeyBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 0:20
 * Description:
 */
public class MultiFac {

    public static void init(){
        MultiblockLib.INSTANCE.registerMultiblock(
                new ResourceLocation("im_dan", "common_lu"),
                LuBlock::new,
                MultiblockPatternKeyBuilder.start()
                        .where('L', BlockInWorld.hasState(state -> state.is(BlockTags.LOGS)))
                        .where('P', BlockInWorld.hasState(state -> state.is(BlockTags.PLANKS)))
                        .where('I', BlockInWorld.hasState(state -> state.is(Blocks.IRON_BLOCK)))
                        .build()
        );
        MultiblockLib.INSTANCE.registerMultiblock(
                new ResourceLocation("im_dan", "iron_lu"),
                LuBlock::new,
                MultiblockPatternKeyBuilder.start()
                        .where('L', BlockInWorld.hasState(state -> state.is(BlockTags.LOGS)))
                        .where('P', BlockInWorld.hasState(state -> state.is(BlockTags.PLANKS)))
                        .where('I', BlockInWorld.hasState(state -> state.is(Blocks.IRON_BLOCK)))
                        .build()
        );
        MultiblockLib.INSTANCE.registerMultiblock(
                new ResourceLocation("im_dan", "copper_lu"),
                LuBlock::new,
                MultiblockPatternKeyBuilder.start()
                        .where('L', BlockInWorld.hasState(state -> state.is(BlockTags.LOGS)))
                        .where('P', BlockInWorld.hasState(state -> state.is(BlockTags.PLANKS)))
                        .where('I', BlockInWorld.hasState(state -> state.is(Blocks.IRON_BLOCK)))
                        .build()
        );
        MultiblockLib.INSTANCE.registerMultiblock(
                new ResourceLocation("im_dan", "gold_lu"),
                LuBlock::new,
                MultiblockPatternKeyBuilder.start()
                        .where('L', BlockInWorld.hasState(state -> state.is(BlockTags.LOGS)))
                        .where('P', BlockInWorld.hasState(state -> state.is(BlockTags.PLANKS)))
                        .where('I', BlockInWorld.hasState(state -> state.is(Blocks.IRON_BLOCK)))
                        .build()
        );
        MultiblockLib.INSTANCE.registerMultiblock(
                new ResourceLocation("im_dan", "nether_lu"),
                LuBlock::new,
                MultiblockPatternKeyBuilder.start()
                        .where('L', BlockInWorld.hasState(state -> state.is(BlockTags.LOGS)))
                        .where('P', BlockInWorld.hasState(state -> state.is(BlockTags.PLANKS)))
                        .where('I', BlockInWorld.hasState(state -> state.is(Blocks.IRON_BLOCK)))
                        .build()
        );
        MultiblockLib.INSTANCE.registerMultiblock(
                new ResourceLocation("im_dan", "xin_lu"),
                LuBlock::new,
                MultiblockPatternKeyBuilder.start()
                        .where('L', BlockInWorld.hasState(state -> state.is(BlockTags.LOGS)))
                        .where('P', BlockInWorld.hasState(state -> state.is(BlockTags.PLANKS)))
                        .where('I', BlockInWorld.hasState(state -> state.is(Blocks.IRON_BLOCK)))
                        .build()
        );
    }
}
