package cn.evolvefield.mods.immortal.core.init.handler;

import cn.evolvefield.mods.immortal.core.api.CoreApi;
import cn.evolvefield.mods.immortal.core.api.EntityAttributeSupplier;
import cn.evolvefield.mods.immortal.core.api.util.DataUtil;
import com.github.clevernucleus.dataattributes.api.DataAttributesAPI;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Project: Immortal-fabric
 * Author: cnlimiter
 * Date: 2023/2/26 16:48
 * Description:
 */
public class CmdHandler {

    public static void init(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            dispatcher.register(
                    Commands.literal("im_core")
                            .then(
                                    Commands.literal("set")
                                            .then(Commands.argument("value", LongArgumentType.longArg())
                                                    .executes(CmdHandler::execute))

                            )


            );




        });
    }


    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        //context.getSource().getPlayerOrException().getAttribute(CoreApi.AGE.get()).setBaseValue(context.getArgument("value", Long.class));

        var player = context.getSource().getPlayerOrException();

        DataUtil.set(player, CoreApi.SHOU_YUAN, context.getArgument("value", Long.class));


        return Command.SINGLE_SUCCESS;
    }
}
