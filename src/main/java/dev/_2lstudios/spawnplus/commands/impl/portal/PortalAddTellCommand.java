package dev._2lstudios.spawnplus.commands.impl.portal;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.portals.Portal;
import dev._2lstudios.spawnplus.portals.PortalActionType;

@Command(
    name = "addtell",
    arguments = { Argument.STRING, Argument.LARGE_STRING },
    minArguments = 2,
    usageKey = "portal.addtell.usage"
)
public class PortalAddTellCommand extends CommandListener {
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

        String message = ctx.getArguments().getString(1);
        portal.addAction(PortalActionType.TELL, message);
        
        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("portal.addtell.added")
                .replace("{portal}", portalName)
                .replace("{message}", message)
        );
    }
}
