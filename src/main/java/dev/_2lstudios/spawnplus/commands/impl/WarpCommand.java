package dev._2lstudios.spawnplus.commands.impl;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandArguments;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandExecutor;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.warps.Warp;
import dev._2lstudios.spawnplus.warps.WarpManager;

@Command(
    name = "warp", 
    permission = "spawnplus.warp", 
    arguments = { 
        Argument.STRING,
        Argument.ONLINE_PLAYER 
    }, 
    minArguments = 1
)
public class WarpCommand extends CommandListener {
    private void sendPlayerToWarp(Warp warp, SpawnPlayer player, boolean skipRequirements) {
        if (skipRequirements) {
            player.sendToWarp(warp, skipRequirements);
        } else {
            int delay = player.sendToWarpWithDelay(warp);
            if (delay == 0) {
                player.sendMessage(
                    player.getI18nMessage("warp.teleported")
                        .replace("{warp}", warp.getDisplayName())
                );
            } else if (delay > 0) {
                player.sendMessage(
                    player.getI18nMessage("warp.teleporting")
                        .replace("{warp}", warp.getDisplayName())
                        .replace("{time}", String.valueOf(delay))
                );
            }
        }
    }

    @Override
    public void onExecute(CommandContext ctx) {
        CommandExecutor executor = ctx.getExecutor();
        CommandArguments arguments = ctx.getArguments();

        String warpName = arguments.getString(0);
        SpawnPlayer target = arguments.getPlayer(1);
        boolean isOther = true;

        if (target != null && !executor.hasPermission("spawnplus.warp.other")) {
            this.onMissingPermission(ctx);
            return;
        } else if (target == null && !ctx.isPlayer()) {
            this.onBadUsage(ctx);
            return;
        } else if (target == null) {
            target = ctx.getPlayer();
            isOther = false;
        }

        SpawnPlus plugin = ctx.getPlugin();
        WarpManager warpManager = plugin.getWarpManager();
        Warp warp = warpManager.getWarp(warpName);

        if (warp == null) {
            executor.sendMessage(
                executor.getI18nMessage("warp.warp-not-found")
                    .replace("{warp}", warpName)
            );
        } else {
            this.sendPlayerToWarp(warp, target, isOther);
            if (isOther) {
                executor.sendMessage(
                    executor.getI18nMessage("warp.teleporting-other")
                        .replace("{warp}", warp.getDisplayName())
                        .replace("{target}", target.getName())  
                );
            }
        }
    }
}
