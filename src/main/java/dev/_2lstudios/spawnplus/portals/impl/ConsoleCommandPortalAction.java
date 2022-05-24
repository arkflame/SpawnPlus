package dev._2lstudios.spawnplus.portals.impl;

import org.bukkit.Server;

import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.portals.PortalAction;

public class ConsoleCommandPortalAction extends PortalAction {
    private String command;

    public ConsoleCommandPortalAction(String command) {
        this.command = command;
    }

    @Override
    public void run(SpawnPlayer player) {
        String cmd = command.replace("{player}", player.getName());

        Server server = player.getPlugin().getServer();
        server.dispatchCommand(server.getConsoleSender(), cmd);
    }
}
