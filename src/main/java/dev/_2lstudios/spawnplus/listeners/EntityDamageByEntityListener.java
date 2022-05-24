package dev._2lstudios.spawnplus.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class EntityDamageByEntityListener implements Listener {
    private SpawnPlus plugin;

    public EntityDamageByEntityListener(SpawnPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damaged = event.getEntity();

        if (damaged instanceof Player) {
            SpawnPlayer player =  this.plugin.getPlayerManager().getPlayer((Player) damaged);

            if (player != null && player.hasPendingTeleportActive()) {
                player.cancelPendingTeleport();
                player.sendI18nMessage("common.cancelled-due-movement");
            }
        }
    }
}
