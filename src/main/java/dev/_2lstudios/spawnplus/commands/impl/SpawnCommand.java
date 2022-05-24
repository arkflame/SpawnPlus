package dev._2lstudios.spawnplus.commands.impl;

import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

@Command(
  name = "spawn", 
  permission = "spawnplus.spawn"
)
public class SpawnCommand extends CommandListener {
  @Override
  public void onExecuteByPlayer(CommandContext ctx) {
    SpawnPlayer player = ctx.getPlayer();

    int delay = player.teleportToSpawnWithDelay();
    if (delay == 0) {
      player.sendI18nMessage("spawn.teleported");
    } else {
      player.sendMessage(
        player.getI18nMessage("spawn.teleporting")
          .replace("{time}", String.valueOf(delay))
      );
    }
  }
}
