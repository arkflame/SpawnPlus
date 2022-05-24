package dev._2lstudios.spawnplus.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class PlayerQuitListener implements Listener {
  private SpawnPlus plugin;
  private Configuration config;

  public PlayerQuitListener(SpawnPlus plugin) {
    this.plugin = plugin;
    this.config = plugin.getConfig();
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    SpawnPlayer player = this.plugin.getPlayerManager().removePlayer(e.getPlayer());

    // Quit message
    if (player.hasPermission("spawnplus.quit-message")) {
      String quitMessage = config.getString("quit.message");

      if (quitMessage.startsWith("i18n:")) {
        String key = quitMessage.split(":")[1];

        for (SpawnPlayer target : this.plugin.getPlayerManager().getPlayers()) {
          String message = target.getI18nMessage(key);
          String formatted = player.formatMessage(message);
          target.sendMessage(formatted);
        }

        e.setQuitMessage(null);
      } else {
        e.setQuitMessage(player.formatMessage(quitMessage));
      }
    } else {
      e.setQuitMessage(null);
    }
    
    // Quit teleport
    if (config.getBoolean("quit.teleport-to-spawn")) {
      player.teleportToSpawnWithDelay();
    }

    // Cancel current teleport
    if (player.hasPendingTeleportActive()) {
      player.cancelPendingTeleport();
      player.sendI18nMessage("common.cancelled-due-movement");
    }
  }
}
