package cn.evolvefield.mods.immortal.init.data.gen;

import cn.evolvefield.mods.immortal.Static;
import cn.evolvefield.mods.immortal.api.core.lock.Requirement;
import cn.evolvefield.mods.immortal.init.data.LockJsonListener;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/5/14 14:35
 * Version: 1.0
 */
public class ZHLangGen extends LanguageProvider {
    public ZHLangGen(DataGenerator gen, String locale) {
        super(gen, Static.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + Static.MOD_ID, "Immortal");
        add("tooltip.requirements", "需求");
        for (Requirement s : LockJsonListener.getRequirements()) {
            String[] des = s.attribute.split(":");

            add("tooltip." + des[0] + "." + des[1], des[1]);
        }
    }
}
