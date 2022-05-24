package dev._2lstudios.spawnplus.commands.impl;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.teleport.PendingAskTeleport;
import dev._2lstudios.spawnplus.utils.ComponentUtils;
import lib__net.md_5.bungee.api.chat.ComponentBuilder;

@Command(
    name = "tpahere",
    arguments = { Argument.ONLINE_PLAYER },
    minArguments = 1,
    permission = "spawnplus.tpahere"
)
public class TPAHereCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        SpawnPlus plugin = ctx.getPlugin();
        SpawnPlayer sender = ctx.getPlayer();
        SpawnPlayer receiver = ctx.getArguments().getPlayer(0);

        if (plugin.getTeleportManager().getPendingAskTeleport(sender, receiver) != null) {
            sender.sendI18nMessage("tpa.already-sent");
            return;
        }

        int delay = receiver.getTeleportTimeDelay();
        long expiration = System.currentTimeMillis() + (plugin.getConfig().getInt("teleport.expiration") * 1000);

        PendingAskTeleport tpa = new PendingAskTeleport(sender, receiver, delay, expiration, true);
        plugin.getTeleportManager().addPendingTeleport(tpa);

        sender.sendMessage(
            sender.getI18nMessage("tpahere.sent")
                .replace("{player}", receiver.getName())
        );

        String rawMessage = receiver.formatMessage(
            receiver.getI18nMessage("tpahere.request")
                .replace("{sender}", sender.getName())
        );
        String[] parts = rawMessage.split("\\{accept}");

        ComponentBuilder builder = new ComponentBuilder();
        builder.append(receiver.formatMessage(parts[0]));
        if (parts.length > 0) {
            builder.append(ComponentUtils.createClickeableText(
                sender.formatMessage(
                    sender.getI18nMessage("common.accept")
                ), "/tpaccept " + sender.getName()
            ));
            builder.append(parts[1]);
        }
        receiver.sendMessage(builder.create());
    }
}
