package dev._2lstudios.spawnplus.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.portals.Portal;

public class PlayerMoveListener implements Listener {
    private SpawnPlus plugin;

    public PlayerMoveListener(SpawnPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event) {
        SpawnPlayer player = this.plugin.getPlayerManager().getPlayer(event.getPlayer());

        if (event.getFrom().distance(event.getTo()) > 0.1) {
            if (player.hasPendingTeleportActive()) {
                player.cancelPendingTeleport();
                player.sendI18nMessage("common.cancelled-due-movement");
            }
        }

        for (Portal portal : this.plugin.getPortalManager().getPortals()) {
            portal.checkAndHandle(player);
        }
    }
}
