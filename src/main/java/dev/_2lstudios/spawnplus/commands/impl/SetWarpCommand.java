package dev._2lstudios.spawnplus.commands.impl;

import org.bukkit.Location;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.warps.Warp;
import dev._2lstudios.spawnplus.warps.WarpManager;

@Command(
    name = "setwarp", 
    permission = "spawnplus.setwarp", 
    arguments = { 
        Argument.STRING
    },
    minArguments = 1
)
public class SetWarpCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        SpawnPlayer player = ctx.getPlayer();
        Location location = player.getBukkitPlayer().getLocation();
        String warpID = ctx.getArguments().getString(0);
        WarpManager warpManager = ctx.getPlugin().getWarpManager();
        
        if (warpManager.existWarp(warpID)) {
            Warp warp = warpManager.getWarp(warpID);
            warp.setLocation(location);
            warp.save();
            player.sendMessage(
                player.getI18nMessage("setwarp.modified")
                    .replace("{warp}", warp.getID())
            );
        } else {
            warpManager.createWarp(warpID, location);
            player.sendMessage(
                player.getI18nMessage("setwarp.created")
                    .replace("{warp}", warpID)
            );
        }
    }
}
