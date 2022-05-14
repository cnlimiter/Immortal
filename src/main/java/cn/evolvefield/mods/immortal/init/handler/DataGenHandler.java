package cn.evolvefield.mods.immortal.init.handler;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.init.data.gen.ENLangGen;
import cn.evolvefield.mods.immortal.init.data.gen.ZHLangGen;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/5/14 14:34
 * Version: 1.0
 */
@Mod.EventBusSubscriber(modid = Static.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenHandler {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
//            generator.addProvider(new TutRecipes(generator));
//            generator.addProvider(new TutLootTables(generator));
//            TutBlockTags blockTags = new TutBlockTags(generator, event.getExistingFileHelper());
//            generator.addProvider(blockTags);
//            generator.addProvider(new TutItemTags(generator, blockTags, event.getExistingFileHelper()));
        }
        if (event.includeClient()) {
//            generator.addProvider(new TutBlockStates(generator, event.getExistingFileHelper()));
//            generator.addProvider(new TutItemModels(generator, event.getExistingFileHelper()));
            generator.addProvider(new ENLangGen(generator, "en_us"));
            generator.addProvider(new ZHLangGen(generator, "zh_cn"));

        }
    }
}
