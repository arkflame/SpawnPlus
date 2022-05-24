package dev._2lstudios.spawnplus.commands.impl.portal;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.portals.Portal;
import dev._2lstudios.spawnplus.portals.PortalActionType;

@Command(
    name = "addserver",
    arguments = { Argument.STRING, Argument.STRING },
    minArguments = 2,
    usageKey = "portal.addserver.usage"
)
public class PortalAddServerCommand extends CommandListener {
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

        String server = ctx.getArguments().getString(1);
        portal.addAction(PortalActionType.SERVER, server);
        
        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("portal.addserver.added")
                .replace("{portal}", portalName)
                .replace("{server}", server)
        );
    }
}
