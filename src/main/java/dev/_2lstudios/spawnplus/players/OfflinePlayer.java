package dev._2lstudios.spawnplus.players;

import dev._2lstudios.spawnplus.SpawnPlus;

public class OfflinePlayer extends SpawnPlayer {
    private String username;

    public OfflinePlayer(SpawnPlus plugin, String username) {
        super(plugin, null);
        this.username = username.toLowerCase();
    }

    @Override
    public String getLowerName() {
        return this.username;
    }
}