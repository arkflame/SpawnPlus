package dev._2lstudios.spawnplus.commands.impl;

import org.bukkit.Location;

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
    name = "sethome",
    permission = "spawnplus.home",
    arguments = { Argument.STRING }
)
public class SetHomeCommand extends CommandListener {
    private void doAction(CommandContext ctx, HomeGroup homeGroup, SpawnPlayer player) {
        String homeName = ctx.getArguments().getString(0);

        if (homeName == null) {
            if (homeGroup.getCount() == 0) {
                homeName = "default";
            } else {
                this.onBadUsage(ctx);
                return;
            }
        } else {
            homeName = homeName.toLowerCase();
        }

        Home home = homeGroup.getHome(homeName);
        Location location = ctx.getPlayer().getBukkitPlayer().getLocation();

        if (home == null) {
            homeGroup.addHome(homeName, location);
            player.sendMessage(
                player.getI18nMessage("sethome.success")
                    .replace("{home}", homeName)  
            );
        } else {
            player.sendMessage(
                player.getI18nMessage("sethome.already-exist")
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
