package dev._2lstudios.spawnplus.portals.impl;

import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.portals.PortalAction;

public class PlayerCommandPortalAction extends PortalAction {
    private String command;

    public PlayerCommandPortalAction(String command) {
        this.command = command;
    }

    @Override
    public void run(SpawnPlayer player) {
        String cmd = command.replace("{player}", player.getName());
        player.getBukkitPlayer().chat("/" + cmd);
    }
}
