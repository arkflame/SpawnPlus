package dev._2lstudios.spawnplus.commands.impl;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.homes.Home;
import dev._2lstudios.spawnplus.homes.HomeGroup;
import dev._2lstudios.spawnplus.homes.HomeGroupLoadEntry;
import dev._2lstudios.spawnplus.homes.HomesLoadTask;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.teleport.PendingTeleportReason;

@Command(
    name = "home",
    permission = "spawnplus.home",
    arguments = { Argument.STRING }
)
public class HomeCommand extends CommandListener {
    private void doAction(CommandContext ctx, HomeGroup homeGroup, SpawnPlayer player) {
        String homeName = ctx.getArguments().getString(0);
        Home home = null;

        if (homeName == null && homeGroup.getCount() == 0) {
            player.sendI18nMessage("home.not-set");
            return;
        } else if (homeName == null && homeGroup.getCount() == 1) {
            home = homeGroup.getHome(homeGroup.getFirstHome());
        } else if (homeName == null && homeGroup.getCount() > 1) {
            player.sendMessage(player.getI18nMessage("home.list").replace("{homes}", homeGroup.getAllHomesAsString()));
            return;
        } else {
            home = homeGroup.getHome(homeName.toLowerCase());
        }

        if (home == null) {
            player.sendMessage(
                    player.getI18nMessage("home.not-found")
                            .replace("{home}", homeName));
        } else {
            int delay = player.teleportWithDelay(home.getLocation(), PendingTeleportReason.HOME, homeName);

            if (delay == 0) {
                player.sendMessage(
                    player.getI18nMessage("home.teleported")
                        .replace("{home}", homeName)
                );
            } else {
                player.sendMessage(
                    player.getI18nMessage("home.teleporting")
                            .replace("{home}", homeName)
                            .replace("{time}", String.valueOf(delay)));
            }
        }
    }

    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        SpawnPlayer player = ctx.getPlayer();
        HomeGroup homeGroup = player.getHomes();
        HomesLoadTask homesLoadTask = ctx.getPlugin().getHomesLoadTask();

        if (!homeGroup.isLoaded()) {
            if (!homeGroup.isBeingLoaded()) {
                homesLoadTask.add(new HomeGroupLoadEntry(homeGroup, () -> {
                    doAction(ctx, homeGroup, player);
                }));
            }
        } else {
            doAction(ctx, homeGroup, player);
        }
    }
}
