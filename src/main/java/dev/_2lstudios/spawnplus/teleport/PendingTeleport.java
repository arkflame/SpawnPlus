package dev._2lstudios.spawnplus.teleport;

import org.bukkit.Location;

import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class PendingTeleport {
    private SpawnPlayer player;
    private Location location;
    private int time;

    private String targetName;
    private PendingTeleportReason reason;

    public PendingTeleport(SpawnPlayer player, Location location, PendingTeleportReason reason, String targetName, int time) {
        this.player = player;
        this.location = location;
        this.reason = reason;
        this.targetName = targetName;
        this.time = time;
    }

    public PendingTeleport(SpawnPlayer player, Location location, int time) {
        this(player, location, PendingTeleportReason.UNKNOWN, "", time);
    }

    public SpawnPlayer getPlayer() {
        return player;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getTargetName() {
        return this.targetName;
    }

    public PendingTeleportReason getReason() {
        return this.reason;
    }

    public int getTime() {
        return this.time;
    }

    public int tick() {
        return --time;
    }
}
