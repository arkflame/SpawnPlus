package dev._2lstudios.spawnplus.teleport;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class TeleportProcessorTask implements Runnable {
    private PendingTeleportManager teleportManager;

    public TeleportProcessorTask(SpawnPlus plugin) {
        this.teleportManager = plugin.getTeleportManager();
    }

    @Override
    public void run() {
        Map<UUID, PendingTeleport> pendingTeleportsMap = teleportManager.getPendingTeleports();

        if (!pendingTeleportsMap.isEmpty()) {
            Iterator<PendingTeleport> iterator = pendingTeleportsMap.values().iterator();

            while (iterator.hasNext()) {
                PendingTeleport teleport = iterator.next();
                
                if (teleport instanceof PendingAskTeleport) {
                    PendingAskTeleport tpa = (PendingAskTeleport) teleport;

                    if (tpa.isExpired()) {
                        iterator.remove();
                        continue;
                    } else if (!tpa.isAccepted()) {
                        continue;
                    }
                }

                int remaining = teleport.tick();

                if (remaining <= 0) {
                    iterator.remove();

                    SpawnPlayer target = teleport.getPlayer();

                    if (teleport instanceof PendingAskTeleport) {
                        PendingAskTeleport tpa = (PendingAskTeleport) teleport;
                        if (tpa.isInverted()) {
                            target = tpa.getTarget();
                        }
                    }

                    target.teleport(teleport.getLocation());

                    String key = teleport.getReason().name().toLowerCase();
                    String path = key;
                    if (path.equals("player")) {
                        path = "tpaccept";
                    }
                    path += ".teleported";

                    target.sendMessage(
                        target.getI18nMessage(path)
                            .replace("{" + key + "}", teleport.getTargetName())
                    );
                }
            }
        }
    }
}