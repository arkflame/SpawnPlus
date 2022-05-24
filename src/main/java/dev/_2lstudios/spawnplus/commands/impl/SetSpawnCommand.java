package dev._2lstudios.spawnplus.commands.impl;

import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

@Command(
  name = "setspawn", 
  permission = "spawnplus.setspawn"
)
public class SetSpawnCommand extends CommandListener {
  @Override
  public void onExecuteByPlayer(CommandContext ctx) {
    Configuration spawnConfig = ctx.getPlugin().getSpawnConfig();
    SpawnPlayer player = ctx.getPlayer();
    spawnConfig.setLocation(player.getBukkitPlayer().getLocation());
    spawnConfig.safeSave();
    player.sendI18nMessage("setspawn.success");
  }
}
