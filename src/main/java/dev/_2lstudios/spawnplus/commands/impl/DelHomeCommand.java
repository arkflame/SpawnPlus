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

@Command(
    name = "delhome",
    permission = "spawnplus.home",
    arguments = { Argument.STRING },
    minArguments = 1
)
public class DelHomeCommand extends CommandListener {
    private void doAction(CommandContext ctx, HomeGroup homeGroup, SpawnPlayer player) {
        String homeName = ctx.getArguments().getString(0).toLowerCase();
        Home home = homeGroup.delHome(homeName);

        if (home == null) {
            player.sendMessage(
                player.getI18nMessage("delhome.not-found")
                    .replace("{home}", homeName)  
            );
        } else {
            player.sendMessage(
                player.getI18nMessage("delhome.deleted")
                    .replace("{home}", homeName)
            );
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
