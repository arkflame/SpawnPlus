package dev._2lstudios.spawnplus.commands.impl.portal;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.portals.Portal;

@Command(
    name = "price",
    arguments = { Argument.STRING, Argument.INT },
    minArguments = 2,
    usageKey = "portal.price.usage"
)
public class PortalPriceCommand extends CommandListener {
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

        int price = ctx.getArguments().getInt(0);
        portal.setPrice(price);

        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("portal.price.setted")
                .replace("{portal}", portalName)
                .replace("{price}", String.valueOf(price))
        );
    }
}
