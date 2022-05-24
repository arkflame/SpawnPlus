package dev._2lstudios.spawnplus.commands.impl;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandExecutor;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.warps.WarpManager;

@Command(
    name = "delwarp", 
    permission = "spawnplus.delwarp", 
    arguments = { 
        Argument.STRING
    },
    minArguments = 1
)
public class DelWarpCommand extends CommandListener {
    @Override
    public void onExecute(CommandContext ctx) {
        CommandExecutor executor = ctx.getExecutor();
        String warpID = ctx.getArguments().getString(0);
        WarpManager warpManager = ctx.getPlugin().getWarpManager();
        
        if (warpManager.deleteWarp(warpID)) {
            executor.sendMessage(
                executor.getI18nMessage("delwarp.deleted")
                    .replace("{warp}", warpID)
            );
        } else {
            executor.sendMessage(
                executor.getI18nMessage("delwarp.warp-not-exist")
                    .replace("{warp}", warpID)
            );
        }
    }
}
