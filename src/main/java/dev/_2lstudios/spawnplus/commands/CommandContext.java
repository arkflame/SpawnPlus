package dev._2lstudios.spawnplus.commands;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.errors.BadArgumentException;
import dev._2lstudios.spawnplus.errors.PlayerOfflineException;
import dev._2lstudios.spawnplus.errors.WorldNotFoundException;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class CommandContext {
    private SpawnPlus plugin;
    private CommandExecutor executor;
    private CommandArguments arguments;

    public CommandContext(SpawnPlus plugin, CommandSender sender, Argument[] requiredArguments) {
        if (sender instanceof Player) {
            this.executor = plugin.getPlayerManager().getPlayer((Player) sender);
        } else {
            this.executor = new CommandExecutor(plugin, sender);
        }

        this.plugin = plugin;
        this.arguments = new CommandArguments(plugin, requiredArguments);
    }

    public void parseArguments(String[] args) throws BadArgumentException, PlayerOfflineException, WorldNotFoundException {
        this.arguments.parse(args);
    }

    public SpawnPlus getPlugin() {
        return this.plugin;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    public SpawnPlayer getPlayer() {
        return (SpawnPlayer) this.executor;
    }

    public boolean isPlayer() {
        return this.executor.isPlayer();
    }

    public CommandArguments getArguments() {
        return this.arguments;
    }
}