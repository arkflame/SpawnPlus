package dev._2lstudios.spawnplus.listeners;

import java.util.Collection;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.utils.BukkitUtils;

public class PlayerJoinListener implements Listener {
  private SpawnPlus plugin;

  private Configuration config;

  public PlayerJoinListener(SpawnPlus plugin) {
    this.plugin = plugin;
    this.config = plugin.getConfig();
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    SpawnPlayer player = this.plugin.getPlayerManager().addPlayer(e.getPlayer());
    player.download();

    // Join message
    if (player.hasPermission("spawnplus.join-message")) {
      String joinMessage = config.getString("join.message");

      if (joinMessage.startsWith("i18n:")) {
        String key = joinMessage.split(":")[1];

        for (SpawnPlayer target : this.plugin.getPlayerManager().getPlayers()) {
          String message = target.getI18nMessage(key);
          String formatted = player.formatMessage(message);
          target.sendMessage(formatted);
        }

        e.setJoinMessage(null);
      } else {
        e.setJoinMessage(player.formatMessage(joinMessage));
      }
    } else {
      e.setJoinMessage(null);
    }
    
    
    // Join teleport
    if (config.getBoolean("join.teleport-to-spawn")) {
      player.teleportToSpawnWithDelay();
    }

    // Join sounds
    if (player.hasPermission("spawnplus.join-sounds")) {
      Collection<String> spawnSounds = config.getStringList("join-sounds");

      if (spawnSounds != null && !spawnSounds.isEmpty()) {
        for (String soundName : spawnSounds) {
          Sound sound = config.getSound(soundName);
          for (Player otherPlayer : plugin.getServer().getOnlinePlayers()) {
            otherPlayer.playSound(otherPlayer.getLocation(), sound, 1.0f, 1.0f);
          }
        }
      }
    }

    if (player.hasPermission("spawnplus.join-fireworks")) {
      BukkitUtils.spawnFireworks(player.getLocation(), 1);
    }
  }
}
