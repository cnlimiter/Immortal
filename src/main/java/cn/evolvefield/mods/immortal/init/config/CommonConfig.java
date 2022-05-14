package cn.evolvefield.mods.immortal.init.config;

import cn.evolvefield.mods.immortal.Static;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {


    /**
     * Initialised instance of the forge common config specifications.
     */
    public static final ForgeConfigSpec COMMON_SPEC;
    /**
     * Initialised instance of our common config.
     */
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);

        COMMON_SPEC = common.getRight();
        COMMON = common.getLeft();
    }

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }

    /**
     * The common config file (for the server really, but needs to be available to the client).
     */
    public static class Common {

        /**
         * Starting attribute.
         */
        public final IntValue constitution, strength, dexterity, intelligence, luckiness, mentality;
        /**
         * expCoeff parameters.
         */
        public final DoubleValue offset, scale, experienceSplit;
        /**
         * reset attributes on death
         */
        public final BooleanValue resetOnDeath;

        public final BooleanValue maxHealth, knockbackRes, healthRegen, meleeDamage, armor, rangedDamage, attackSpeed, movementSpeed, meleeCritDamage, healthRegenAmp, rangedCritDamage, lifesteal, luck, meleeCritChance, rangedCritChance, evasion;

        public Common(ForgeConfigSpec.Builder builder) {
            //////////////////
            builder.comment("These only have an affect when a player is first joining the world.").push("attributes");

            this.constitution = builder
                    .comment("Starting Constitution (also starting hp in half hearts) - cannot and should not be set to 0 or less than 0.")
                    .translation(Static.MOD_ID + ".config.common.constitution")
                    .worldRestart()
                    .defineInRange("constitution", 5, 0, 20);
            this.strength = builder
                    .comment("Starting Strength - cannot and should not be set to less than 0.")
                    .translation(Static.MOD_ID + ".config.common.strength")
                    .worldRestart()
                    .defineInRange("strength", 5, 0, 20);
            this.dexterity = builder
                    .comment("Starting Dexterity - cannot and should not be set to less than 0.")
                    .translation(Static.MOD_ID + ".config.common.dexterity")
                    .worldRestart()
                    .defineInRange("dexterity", 5, 0, 20);
            this.intelligence = builder
                    .comment("Starting Intelligence - cannot and should not be set to less than 0.")
                    .translation(Static.MOD_ID + ".config.common.intelligence")
                    .worldRestart()
                    .defineInRange("intelligence", 5, 0, 20);
            this.luckiness = builder
                    .comment("Starting Luckiness - cannot and should not be set to less than 0.")
                    .translation(Static.MOD_ID + ".config.common.luckiness")
                    .worldRestart()
                    .defineInRange("luckiness", 5, 0, 20);
            this.mentality = builder
                    .comment("Starting Mentality - cannot and should not be set to less than 0.")
                    .translation(Static.MOD_ID + ".config.common.mentality")
                    .worldRestart()
                    .defineInRange("mentality", 5, 0, 20);

            builder.pop();

            /////////////////////
            builder.comment("These are adder/modifier functions implemented by default. This is a stopgap config option to disable some until datadriven mechanics are released.").push("functions");
            builder.comment("If you want to add custom attributes, use the API.");

            this.maxHealth = builder.comment("Added on Constitution").worldRestart().define("addMaxHealth", true);
            this.knockbackRes = builder.comment("Added on Constitution").worldRestart().define("addKnockbackRes", true);
            this.healthRegen = builder.comment("Added on Strength").worldRestart().define("addHealthRegen", true);
            this.meleeDamage = builder.comment("Added on Strength").worldRestart().define("addMeleeDamage", true);
            this.armor = builder.comment("Added on Strength").worldRestart().define("addArmor", true);
            this.rangedDamage = builder.comment("Added on Dexterity").worldRestart().define("addRangedDamage", true);
            this.attackSpeed = builder.comment("Added on Dexterity").worldRestart().define("addAttackSpeed", true);
            this.movementSpeed = builder.comment("Added on Dexterity").worldRestart().define("addMovementSpeed", true);
            this.meleeCritDamage = builder.comment("Added on Dexterity").worldRestart().define("addMeleeCritDamage", true);
            this.healthRegenAmp = builder.comment("Added on Intelligence").worldRestart().define("addHealthRegenAmp", true);
            this.rangedCritDamage = builder.comment("Added on Intelligence").worldRestart().define("addRangedCritDamage", true);
            this.lifesteal = builder.comment("Added on Intelligence").worldRestart().define("addLifesteal", true);
            this.luck = builder.comment("Added on Luckiness").worldRestart().define("addLuck", true);
            this.meleeCritChance = builder.comment("Added on Luckiness").worldRestart().define("addMeleeCritChance", true);
            this.rangedCritChance = builder.comment("Added on Luckiness").worldRestart().define("addRangedCritChance", true);
            this.evasion = builder.comment("Added on Luckiness").worldRestart().define("addEvasion", true);

            builder.pop();

            ///////////////////
            builder.push("misc");

            this.resetOnDeath = builder
                    .comment("If true, resets all attributes to their defaults on death.")
                    .worldRestart()
                    .define("resetOnDeath", false);

            builder.pop();
            builder.push("experienceSplit");

            this.experienceSplit = builder.comment("The percentage of experience that contributes to Immortal levels.").translation(Static.MOD_ID + ".config.common.experiencesplit").defineInRange("experienceSplit", 50D, 1D, 99D);

            builder.pop();


            ////////////////
            builder.push("expcoeff");

            this.offset = builder
                    .comment("ExpCoeff offset - cannot and should not be set to 0 or less than 0.")
                    .translation(Static.MOD_ID + ".config.common.offset")
                    .worldRestart()
                    .defineInRange("offset", 3D, 1D, 20D);
            this.scale = builder
                    .comment("ExpCoeff scaling - cannot and should not be set to 0 or less than 0.")
                    .translation(Static.MOD_ID + ".config.common.scale")
                    .worldRestart()
                    .defineInRange("scale", 1D, 1D, 20D);
            builder.pop();


        }
    }
}
