package dev._2lstudios.spawnplus.portals.impl;

import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.portals.PortalAction;

public class ServerPortalAction extends PortalAction {
    private String server;

    public ServerPortalAction(String server) {
        this.server = server;
    }

    @Override
    public void run(SpawnPlayer player) {
        player.sendToServer(server);
    }
}
