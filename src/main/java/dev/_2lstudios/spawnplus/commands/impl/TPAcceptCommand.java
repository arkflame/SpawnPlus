package dev._2lstudios.spawnplus.commands.impl;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.teleport.PendingAskTeleport;

@Command(
    name = "tpaccept",
    arguments = { Argument.ONLINE_PLAYER },
    minArguments = 1,
    permission = "spawnplus.tpa"
)
public class TPAcceptCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        SpawnPlus plugin = ctx.getPlugin();
        SpawnPlayer receiver = ctx.getPlayer();
        SpawnPlayer sender = ctx.getArguments().getPlayer(0);

        PendingAskTeleport tpa = plugin.getTeleportManager().getPendingAskTeleport(sender, receiver);

        if (tpa == null) {
            receiver.sendI18nMessage("tpaccept.not-pending");
        } else if (tpa.isAccepted()) {
            receiver.sendI18nMessage("tpaccept.already-accepted");
        } else {
            receiver.sendMessage(receiver.getI18nMessage("tpaccept.accepted")
                .replace("{player}", sender.getName())
            );
            sender.sendMessage(sender.getI18nMessage("tpaccept.accepted-notify")
                .replace("{player}", receiver.getName())
            );
            
            if (tpa.isInverted()) {
                receiver.sendMessage(sender.getI18nMessage("tpaccept.teleporting")
                    .replace("{time}", String.valueOf(tpa.getTime()))
                    .replace("{player}", receiver.getName())
                );
            } else {
                sender.sendMessage(sender.getI18nMessage("tpaccept.teleporting")
                    .replace("{time}", String.valueOf(tpa.getTime()))
                    .replace("{player}", receiver.getName())
                );
            }

            tpa.accept();
        }
    }
}
