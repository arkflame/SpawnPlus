package dev._2lstudios.spawnplus.teleport;

import org.bukkit.Location;

import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class PendingAskTeleport extends PendingTeleport {
    private SpawnPlayer target;
    private boolean accepted;
    private long expiration;
    private boolean inverted;

    public PendingAskTeleport(SpawnPlayer player, SpawnPlayer target, int time, long expiration, boolean inverted) {
        super(
            player, 
            null, 
            PendingTeleportReason.PLAYER,
            inverted ? player.getName() : target.getName(),
            time
        );
        this.target = target;
        this.accepted = false;
        this.expiration = expiration;
        this.inverted = inverted;
    }

    public PendingAskTeleport(SpawnPlayer player, SpawnPlayer target, int time, long expiration) {
        this(player, target, time, expiration, false);
    }

    @Override
    public Location getLocation() {
        if (this.inverted) {
            return this.getPlayer().getLocation();
        } else {
            return this.target.getLocation();
        }
    }

    public SpawnPlayer getTarget() {
        return target;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.expiration;
    }

    public boolean isInverted() {
        return this.inverted;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    public void accept() {
        this.accepted = true;
    }
}
