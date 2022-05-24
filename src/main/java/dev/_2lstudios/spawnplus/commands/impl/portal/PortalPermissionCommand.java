package dev._2lstudios.spawnplus.commands.impl.portal;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.portals.Portal;

@Command(
    name = "permission",
    arguments = { Argument.STRING, Argument.STRING },
    minArguments = 2,
    usageKey = "portal.permission.usage"
)
public class PortalPermissionCommand extends CommandListener {
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

        String permission = ctx.getArguments().getString(0);
        portal.setPermission(permission);
        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("portal.permission.setted")
                .replace("{portal}", portalName)
                .replace("{permission}", permission)
        );
    }
}
