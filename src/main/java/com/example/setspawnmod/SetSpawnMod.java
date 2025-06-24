package com.example.setspawnmod;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.common.Mod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod("setspawnmod")
@EventBusSubscriber
public class SetSpawnMod {
    private static BlockPos globalSpawn = null;
    private static String dimensionName = "minecraft:overworld";

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("setspawn")
                .requires(cs -> cs.hasPermission(2))
                .executes(ctx -> {
                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                    Vec3 pos = player.position();
                    globalSpawn = new BlockPos(pos);
                    dimensionName = player.level().dimension().location().toString();
                    ctx.getSource().sendSuccess(() -> net.minecraft.network.chat.Component.literal("Spawn set to: " + pos + " in " + dimensionName), true);
                    return 1;
                }));

        dispatcher.register(Commands.literal("spawn")
                .executes(ctx -> {
                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                    if (globalSpawn != null && player.level().dimension().location().toString().equals(dimensionName)) {
                        player.teleportTo(globalSpawn.getX() + 0.5, globalSpawn.getY(), globalSpawn.getZ() + 0.5);
                        ctx.getSource().sendSuccess(() -> net.minecraft.network.chat.Component.literal("Teleported to spawn."), false);
                        return 1;
                    } else {
                        ctx.getSource().sendFailure(net.minecraft.network.chat.Component.literal("Spawn not set or in different dimension."));
                        return 0;
                    }
                }));
    }
}
