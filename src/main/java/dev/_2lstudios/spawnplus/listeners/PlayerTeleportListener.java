package dev._2lstudios.spawnplus.listeners;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class PlayerTeleportListener implements Listener {
  private SpawnPlus plugin;
  private Configuration config;

  public PlayerTeleportListener(SpawnPlus plugin) {
    this.plugin = plugin;
    this.config = plugin.getConfig();
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
  public void onPlayerTeleport(PlayerTeleportEvent e) {
    SpawnPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
    // Can be null on join.
    if (player != null && e.getFrom() != null) {
      Sound sound = config.getSound("teleport.sound");

      if (sound != null) {
        player.playSound(sound);
      }

      player.setPreviousLocation(e.getFrom());
    }
  }
}
