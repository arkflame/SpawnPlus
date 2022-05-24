package dev._2lstudios.spawnplus.portals;

import java.util.ArrayList;
import java.util.List;

import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.portals.impl.ConsoleCommandPortalAction;
import dev._2lstudios.spawnplus.portals.impl.PlayerCommandPortalAction;
import dev._2lstudios.spawnplus.portals.impl.ServerPortalAction;
import dev._2lstudios.spawnplus.portals.impl.SoundPortalAction;
import dev._2lstudios.spawnplus.portals.impl.TellPortalAction;

public abstract class PortalAction {
    public abstract void run(SpawnPlayer player);

    public static PortalAction parseAction(String rawAction) {
        String[] parts = rawAction.split(":");
        if (parts.length > 1) {
            String type = parts[0].trim();
            String arg = parts[1].trim();

            switch(type) {
                case "console":
                    return new ConsoleCommandPortalAction(arg);
                case "player":
                    return new PlayerCommandPortalAction(arg);
                case "server":
                    return new ServerPortalAction(arg);
                case "sound":
                    return new SoundPortalAction(arg);
                case "tell":
                    return new TellPortalAction(arg); 
            }
        }

        return null;
    }

    public static List<PortalAction> parseActions(List<String> rawActions) {
        List<PortalAction> actions = new ArrayList<>();
        if (rawActions != null) {
            for (String rawAction : rawActions) {
                PortalAction action = PortalAction.parseAction(rawAction);
                if (action != null) {
                    actions.add(action);
                }
            }
        }
        return actions;
    }
}
