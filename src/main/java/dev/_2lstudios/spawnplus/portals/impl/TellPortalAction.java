package dev._2lstudios.spawnplus.portals.impl;

import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.portals.PortalAction;

public class TellPortalAction extends PortalAction {
    private String message;

    public TellPortalAction(String message) {
        this.message = message;
    }

    @Override
    public void run(SpawnPlayer player) {
        player.sendMessage(this.message.replace("{player}", player.getName()));
    }
}
