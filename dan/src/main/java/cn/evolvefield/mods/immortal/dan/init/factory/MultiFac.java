package cn.evolvefield.mods.immortal.dan.init.factory;

import cn.evolvefield.mods.immortal.dan.common.block.LuBlock;
import cn.evolvefield.mods.multiblocklib.api.Multiblock;
import cn.evolvefield.mods.multiblocklib.api.MultiblockLib;
import cn.evolvefield.mods.multiblocklib.api.pattern.MultiblockPatternKeyBuilder;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.Optional;

/**
 * Project: Immortal
 * Author: cnlimiter
 * Date: 2023/3/5 0:20
 * Description:
 */
public class MultiFac {
    public static void init(){
        blockInit();
        playerInit();
    }
    private static void blockInit(){
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


    private static void playerInit(){
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            Optional<Multiblock> multiblock = MultiblockLib.INSTANCE.getMultiblock(world, hitResult.getBlockPos());
            if (!player.isCrouching()) return InteractionResult.PASS;

            if (multiblock.isPresent()) {
                if (MultiblockLib.INSTANCE.tryDisassembleMultiblock(multiblock.get(), false)) {
                    if (world.isClientSide) {
                        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);
                    }

                    return InteractionResult.SUCCESS;
                }
            } else {
                if (MultiblockLib.INSTANCE.tryAssembleMultiblock(world, player.getDirection(), hitResult.getBlockPos())) {
                    if (world.isClientSide) {
                        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 2.0F, 1.0F);
                    }

                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        });
    }
}
