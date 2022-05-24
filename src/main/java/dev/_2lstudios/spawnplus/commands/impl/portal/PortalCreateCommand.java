package dev._2lstudios.spawnplus.commands.impl.portal;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.MagicWand;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

@Command(
    name = "create",
    arguments = { Argument.STRING },
    minArguments = 1,
    usageKey = "portal.create.usage"
)
public class PortalCreateCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        SpawnPlayer player = ctx.getPlayer();
        MagicWand wand = player.getMagicWand();
        String portalName = ctx.getArguments().getString(0);

        if (wand == null) {
            player.sendI18nMessage("portal.create.no-wand");
            return;
        } else if (!wand.isComplete()) {
            player.sendI18nMessage("portal.create.no-wand-complete");
            return;
        } else if (!ctx.getPlugin().getPortalManager().createPortal(portalName, wand)) {
            player.sendMessage(
                player.getI18nMessage("portal.create.already-exist")
                    .replace("{portal}", portalName)  
            );
        } else {
            player.sendMessage(
                player.getI18nMessage("portal.create.created")
                    .replace("{portal}", portalName)  
            );
            player.deleteMagicWand();
        }
    }
}
