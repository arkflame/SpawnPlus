package dev._2lstudios.spawnplus.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.utils.BukkitUtils;

public class PlayerRespawnListener implements Listener {
    private Configuration config;
    private Configuration spawnConfig;

    public PlayerRespawnListener(SpawnPlus plugin) {
        this.config = plugin.getConfig();
        this.spawnConfig = plugin.getSpawnConfig();
    }

    private boolean shouldTeleport(PlayerRespawnEvent e) {
        return config.getBoolean("respawn.teleport-to-spawn") && (
            config.getBoolean("respawn.bypass-bed") || !e.isBedSpawn()
        );
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (this.shouldTeleport(event)) {
            Location spawn = spawnConfig.getLocation();

            if (spawn != null) {
                event.setRespawnLocation(BukkitUtils.getRandomizedLocation(spawn, config.getInt("spawn.radius")));
            }
        }
    }
}
