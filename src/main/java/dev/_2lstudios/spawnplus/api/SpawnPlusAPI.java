package dev._2lstudios.spawnplus.api;

import org.bukkit.entity.Player;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class SpawnPlusAPI {
    private SpawnPlus plugin;
    private static SpawnPlusAPI instance;
    
    public SpawnPlusAPI(SpawnPlus plugin) {
        this.plugin = plugin;
        SpawnPlusAPI.instance = this;
    }

    public SpawnPlayer getPlayer(Player player) {
        return plugin.getPlayerManager().getPlayer(player);
    }

    public static SpawnPlusAPI getAPI() {
        return instance;
    }
}
