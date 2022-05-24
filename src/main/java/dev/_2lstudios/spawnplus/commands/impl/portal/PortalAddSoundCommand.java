package dev._2lstudios.spawnplus.commands.impl.portal;

import org.bukkit.Sound;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.portals.Portal;
import dev._2lstudios.spawnplus.portals.PortalActionType;
import dev._2lstudios.spawnplus.utils.BukkitUtils;

@Command(
    name = "addsound",
    arguments = { Argument.STRING, Argument.STRING },
    minArguments = 2,
    usageKey = "portal.addsound.usage"
)
public class PortalAddSoundCommand extends CommandListener {
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

        String soundName = ctx.getArguments().getString(1);
        Sound sound = BukkitUtils.getSound(soundName);

        if (sound == null) {
            ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("portal.addsound.sound-not-found")
                .replace("{sound}", soundName)
            );
            return;
        }

        portal.addAction(PortalActionType.SOUND, soundName);
        
        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("portal.addsound.added")
                .replace("{portal}", portalName)
                .replace("{sound}", soundName)
        );
    }
}
