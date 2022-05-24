package dev._2lstudios.spawnplus.commands.impl;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

@Command(
    name = "top",
    permission = "spawnplus.top"
)
public class TopCommand extends CommandListener {
    @Override
    public void onExecuteByPlayer(CommandContext ctx) {
        SpawnPlayer player = ctx.getPlayer();
        World world = player.getBukkitPlayer().getWorld();

        Location playerPos = player.getBukkitPlayer().getLocation();
        Location target = null;
 
        int x = (int) playerPos.getX();
        int z = (int) playerPos.getZ();

        for (int y = world.getMaxHeight(); y > 0; y--) {
            Block block = world.getBlockAt(x, y, z);
            
            if (block != null && block.getType() != Material.AIR) {
                target = block.getRelative(0, 1, 0).getLocation();
                break;
            }
        }

        if (target == null || (int) target.getY() == (int) playerPos.getY()) {
            player.sendI18nMessage("top.no-block-above");
        } else {
            player.teleport(target);
            player.sendI18nMessage("top.teleported");
        }
    }
}
