package dev._2lstudios.spawnplus.teleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class PendingTeleportManager {
    private Map<UUID, PendingTeleport> teleports = new HashMap<>();

    public PendingTeleport getPendingTeleport(UUID uuid) {
        return teleports.getOrDefault(uuid, null);
    }

    public PendingTeleport getPendingTeleport(Player player) {
        return getPendingTeleport(player.getUniqueId());
    }

    public PendingTeleport getPendingTeleport(SpawnPlayer player) {
        return getPendingTeleport(player.getBukkitPlayer());
    }

    public PendingAskTeleport getPendingAskTeleport(SpawnPlayer sender, SpawnPlayer receiver) {
        PendingTeleport teleport = this.getPendingTeleport(sender);
        if (teleport instanceof PendingAskTeleport) {
            PendingAskTeleport tpa = (PendingAskTeleport) teleport;

            if (tpa.getTarget().equals(receiver)) {
                return tpa;
            }
        }

        return null;
    }

    public void removePendingTeleport(UUID uuid) {
        teleports.remove(uuid);
    }

    public void removePendingTeleport(Player player) {
        removePendingTeleport(player.getUniqueId());
    }

    public void removePendingTeleport(SpawnPlayer player) {
        removePendingTeleport(player.getBukkitPlayer());
    }

    public void addPendingTeleport(PendingTeleport teleport) {
        teleports.put(teleport.getPlayer().getBukkitPlayer().getUniqueId(), teleport);
    }

    public Map<UUID, PendingTeleport> getPendingTeleports() {
        return teleports;
    }
}
