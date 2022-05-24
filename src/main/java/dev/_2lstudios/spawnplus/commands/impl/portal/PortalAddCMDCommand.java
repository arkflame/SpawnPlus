package dev._2lstudios.spawnplus.commands.impl.portal;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.portals.Portal;
import dev._2lstudios.spawnplus.portals.PortalActionType;

@Command(
    name = "addcmd",
    arguments = { Argument.STRING, Argument.LARGE_STRING },
    minArguments = 2,
    usageKey = "portal.addcmd.usage"
)
public class PortalAddCMDCommand extends CommandListener {
    @Override
    public void onExecute(CommandContext ctx) {
        String portalName = ctx.getArguments().getString(0);
        Portal portal = ctx.getPlugin().getPortalManager().getPortal(portalName);

        if (portal == null) {
            ctx.getExecutor().sendMessage(
                ctx.getExecutor().getI18nMessage("portal.not-exist")
                .replace("{portal}", portalName)
            );
            return;
        }

        String command = ctx.getArguments().getString(1);
        portal.addAction(PortalActionType.PLAYER, command);
        
        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("portal.addcmd.added")
                .replace("{portal}", portalName)
                .replace("{command}", command)
        );
    }
}
