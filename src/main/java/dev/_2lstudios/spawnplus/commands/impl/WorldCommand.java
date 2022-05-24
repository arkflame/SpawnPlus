package dev._2lstudios.spawnplus.commands.impl;

import org.bukkit.World;

import dev._2lstudios.spawnplus.commands.Argument;
import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

@Command(
    name = "world",
    arguments = { Argument.WORLD },
    minArguments = 1,
    permission = "spawnplus.world"
)
public class WorldCommand extends CommandListener {
    @Override
    public void onWorldNotFound(CommandContext ctx, String worldName) {
        ctx.getPlayer().sendMessage(
            ctx.getPlayer().getI18nMessage("world.world-not-found")
                .replace("{world}", worldName)  
        );
    }

    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        SpawnPlayer player = ctx.getPlayer();
        World world = ctx.getArguments().getWorld(0);

        player.teleport(world.getSpawnLocation());
        player.sendMessage(
            player.getI18nMessage("world.teleported")
                .replace("{location}", world.getName())  
        );
    }
}
