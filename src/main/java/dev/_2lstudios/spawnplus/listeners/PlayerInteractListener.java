package dev._2lstudios.spawnplus.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.players.MagicWand;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class PlayerInteractListener implements Listener {
    private SpawnPlus plugin;
    
    public PlayerInteractListener(SpawnPlus plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        SpawnPlayer player = this.plugin.getPlayerManager().getPlayer(e.getPlayer());
        Block block = e.getClickedBlock();

        if (block != null && player != null && player.getMagicWand() != null) {
            MagicWand wand = player.getMagicWand();
            Location location = block.getLocation();
            ItemStack item = e.getItem();

            if (item == null || item.getType() != Material.BLAZE_ROD) {
                return;
            }

            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                wand.setFirstPosition(location);
                player.sendI18nMessage("misc.wand-first-position");
            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                wand.setSecondPosition(location);
                player.sendI18nMessage("misc.wand-second-position");
            } else {
                return;
            }

            e.setCancelled(true);
        }
    }
}
