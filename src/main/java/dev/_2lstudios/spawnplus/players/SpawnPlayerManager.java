package dev._2lstudios.spawnplus.players;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import dev._2lstudios.spawnplus.SpawnPlus;

public class SpawnPlayerManager {
    private SpawnPlus plugin;

    private Map<Player, SpawnPlayer> players;

    public SpawnPlayerManager(SpawnPlus plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
    }

    public SpawnPlayer addPlayer(Player bukkitPlayer) {
        SpawnPlayer player = new SpawnPlayer(this.plugin, bukkitPlayer);
        this.players.put(bukkitPlayer, player);
        return player;
    }

    public SpawnPlayer removePlayer(Player bukkitPlayer) {
        return this.players.remove(bukkitPlayer);
    }

    public SpawnPlayer getPlayer(Player bukkitPlayer) {
        return this.players.get(bukkitPlayer);
    }

    public SpawnPlayer getPlayer(String name) {
        Player bukkitPlayer = this.plugin.getServer().getPlayerExact(name);
        if (bukkitPlayer != null && bukkitPlayer.isOnline()) {
            return this.getPlayer(bukkitPlayer);
        } else {
            return null;
        }
    }

    public Collection<SpawnPlayer> getPlayers() {
        return this.players.values();
    }

    public void clear() {
        this.players.clear();
    }

    public void addAll() {
        this.clear();
        
        for (Player bukkitPlayer : this.plugin.getServer().getOnlinePlayers()) {
            this.addPlayer(bukkitPlayer).download();
        }
    }
}