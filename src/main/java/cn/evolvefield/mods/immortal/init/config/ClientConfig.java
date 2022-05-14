package cn.evolvefield.mods.immortal.init.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/29 20:36
 * Version: 1.0
 */
public class ClientConfig {
    public static final ForgeConfigSpec COMMON_SPEC;

    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Client::new);

        COMMON_SPEC = common.getRight();
        CLIENT = common.getLeft();
    }

    public static class Client {
        public final ForgeConfigSpec.BooleanValue health_percentage, show_numbers_health;

        public Client(ForgeConfigSpec.Builder builder) {


            this.health_percentage = builder
                    .comment("If true,show health percent.")
                    .worldRestart()
                    .define("health_percentage", false);
            this.show_numbers_health = builder
                    .comment("If true,show numbers health.")
                    .worldRestart()
                    .define("show_numbers_health", false);

        }

    }
}
