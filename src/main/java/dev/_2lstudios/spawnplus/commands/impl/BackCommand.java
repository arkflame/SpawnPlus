package dev._2lstudios.spawnplus.commands.impl;

import org.bukkit.Location;

import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.teleport.PendingTeleportReason;

@Command(
    name = "back",
    permission = "spawnplus.back"
)
public class BackCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        SpawnPlayer player = ctx.getPlayer();
        Location previousLocation = player.getPreviousLocation();

        if (previousLocation == null) {
            player.sendI18nMessage("back.no-previous-location");
        } else {
            int delay = player.teleportWithDelay(previousLocation, PendingTeleportReason.BACK);
            if (delay == 0) {
                player.sendI18nMessage("back.teleported");
            } else {
                player.sendMessage(
                    player.getI18nMessage("back.teleporting")
                        .replace("{time}", String.valueOf(delay))
                );
            }
        }
    }
}
