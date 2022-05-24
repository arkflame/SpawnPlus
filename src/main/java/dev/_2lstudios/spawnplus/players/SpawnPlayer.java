package dev._2lstudios.spawnplus.players;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.commands.CommandExecutor;
import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.homes.HomeGroup;
import dev._2lstudios.spawnplus.homes.HomesSaveTask;
import dev._2lstudios.spawnplus.teleport.PendingAskTeleport;
import dev._2lstudios.spawnplus.teleport.PendingTeleport;
import dev._2lstudios.spawnplus.teleport.PendingTeleportReason;
import dev._2lstudios.spawnplus.utils.BukkitUtils;
import dev._2lstudios.spawnplus.utils.PacketUtils;
import dev._2lstudios.spawnplus.utils.PlayerUtils;
import dev._2lstudios.spawnplus.utils.ServerUtils;
import dev._2lstudios.spawnplus.warps.Warp;
import lib__net.md_5.bungee.api.chat.BaseComponent;
import lib__net.md_5.bungee.api.chat.ComponentBuilder;
import lib__net.md_5.bungee.chat.ComponentSerializer;

import me.clip.placeholderapi.PlaceholderAPI;

public class SpawnPlayer extends CommandExecutor {
    private HomesSaveTask homesSaveTask;
    private Player bukkitPlayer;
    private Location previousLocation;
    private HomeGroup homes;
    private MagicWand wand;

    public SpawnPlayer(SpawnPlus plugin, Player bukkitPlayer) {
        super(plugin, bukkitPlayer);
        this.homesSaveTask = plugin.getHomesSaveTask();
        this.bukkitPlayer = bukkitPlayer;
    }

    public Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    public MagicWand getMagicWand() {
        return this.wand;
    }

    public void deleteMagicWand() {
        this.wand = null;
        this.getBukkitPlayer().getInventory().remove(Material.BLAZE_ROD);
    }

    public void createMagicWand() {
        this.wand = new MagicWand();

        ItemStack wandItem = new ItemStack(Material.BLAZE_ROD);
        ItemMeta wandMeta = wandItem.getItemMeta();
        wandMeta.setDisplayName(this.formatMessage(this.getI18nMessage("misc.wand-name")));
        wandMeta.setLore(Arrays.asList(this.formatMessage(this.getI18nMessage("misc.wand-name")).split("\n")));
        wandItem.setItemMeta(wandMeta);
        this.getBukkitPlayer().getInventory().addItem(wandItem);
    }

    @Override
    public String formatMessage(String message) {
        String output = super.formatMessage(message);

        if (this.getPlugin().hasPlugin("PlaceholderAPI")) {
            output = PlaceholderAPI.setPlaceholders(this.getBukkitPlayer(), output);
        }

        return output;
    }

    @Override
    public String getLang() {
        String lang = null;

        if (ServerUtils.hasPlayerGetLocaleAPI()) {
            lang = this.getBukkitPlayer().getLocale();
        } else {
            lang = PlayerUtils.getPlayerLocaleInLegacyWay(this.bukkitPlayer);
        }

        return lang == null ? super.getLang() : lang;
    }

    public String getName() {
        return this.bukkitPlayer.getName();
    }

    public String getLowerName() {
        return this.getName().toLowerCase();
    }

    public void download() {
        this.homes = new HomeGroup(homesSaveTask,
                new Configuration(
                        new File(
                                new File(this.getPlugin().getDataFolder(), "homes"),
                                bukkitPlayer.getUniqueId().toString() + ".yml")));
    }

    public void sendRawMessage(String component, byte type) {
        if (ServerUtils.hasChatComponentAPI()) {
            this.bukkitPlayer.spigot().sendMessage(net.md_5.bungee.chat.ComponentSerializer.parse(component));
        } else {
            PacketUtils.sendJSON(this.getBukkitPlayer(), component, type);
        }
    }

    public void sendRawMessage(String component) {
        this.sendRawMessage(component, (byte) 0);
    }

    public void sendActionBar(String text) {
        this.sendRawMessage(ComponentSerializer.toString(new ComponentBuilder(text).create()), (byte) 2);
    }

    public void sendMessage(BaseComponent component) {
        this.sendRawMessage(ComponentSerializer.toString(component));
    }

    public void sendMessage(BaseComponent[] components) {
        this.sendRawMessage(ComponentSerializer.toString(components));
    }

    public void sendToServer(String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            this.getBukkitPlayer().sendPluginMessage(this.getPlugin(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception e) {
            this.getBukkitPlayer().sendMessage(ChatColor.RED + "Error when trying to connect to " + server);
        }
    }

    public HomeGroup getHomes() {
        return this.homes;
    }

    public Location getPreviousLocation() {
        return this.previousLocation;
    }

    public void setPreviousLocation(Location location) {
        this.previousLocation = location;
    }

    public void playSound(Sound sound) {
        this.getBukkitPlayer().playSound(this.getBukkitPlayer().getLocation(), sound, 1.0f, 1.0f);
    }

    public int getTeleportTimeDelay() {
        Player player = this.getBukkitPlayer();

        if (player.hasPermission("spawnplus.bypass-delay")) {
            return 0;
        }

        int distance = this.getPlugin().getConfig().getInt("teleport.nearby-radius");
        int time = this.getPlugin().getConfig().getInt("teleport.delay");
        int nearbyTime = this.getPlugin().getConfig().getInt("teleport.delay-players-nearby");

        Location location = player.getLocation();

        for (Player target : player.getWorld().getPlayers()) {
            if (player != target && location.distance(target.getLocation()) <= distance) {
                return nearbyTime;
            }
        }

        return time;
    }

    public boolean hasPendingTeleportActive() {
        PendingTeleport teleport = this.getPlugin().getTeleportManager().getPendingTeleport(this);
        if (teleport instanceof PendingAskTeleport) {
            PendingAskTeleport tpa = (PendingAskTeleport) teleport;
            return tpa.isAccepted();
        } else {
            return false;
        }
    }

    public boolean hasPendingTeleport() {
        return this.getPlugin().getTeleportManager().getPendingTeleport(this) != null;
    }

    public boolean cancelPendingTeleport() {
        if (this.hasPendingTeleport()) {
            this.getPlugin().getTeleportManager().removePendingTeleport(this);
            return true;
        } else {
            return false;
        }
    }

    public void teleport(Location location) {
        if (location != null) {
            this.bukkitPlayer.teleport(location);
        }
    }

    public int teleportWithDelay(Location location, PendingTeleportReason reason, String targetName) {
        int time = this.getTeleportTimeDelay();

        if (time == 0) {
            this.teleport(location);
        } else {
            this.getPlugin().getTeleportManager().addPendingTeleport(
                    new PendingTeleport(this, location, reason, targetName, time));
        }

        return time;
    }

    public int teleportWithDelay(Location location, PendingTeleportReason reason) {
        return this.teleportWithDelay(location, reason, "");
    }

    public int teleportToSpawnWithDelay() {
        Location location = BukkitUtils.getRandomizedLocation(
                this.getPlugin().getSpawn(),
                this.getPlugin().getConfig().getInt("spawn.radius"));

        return this.teleportWithDelay(location, PendingTeleportReason.SPAWN);
    }

    private boolean evalWarpRequirements(Warp warp) {
        if (!warp.getPermission().isEmpty()) {
            if (!this.getBukkitPlayer().hasPermission(warp.getPermission())) {
                this.sendI18nMessage("warp.no-permission");
                return false;
            }
        }

        return true;
    }

    public boolean sendToWarp(Warp warp, boolean skipRequirements) {
        if (!skipRequirements && !evalWarpRequirements(warp)) {
            return false;
        } else {
            this.teleport(warp.getLocation());
            return true;
        }
    }

    public int sendToWarpWithDelay(Warp warp, boolean skipRequirements) {
        if (!skipRequirements && !evalWarpRequirements(warp)) {
            return -1;
        } else {
            return this.teleportWithDelay(warp.getLocation(), PendingTeleportReason.WARP, warp.getDisplayName());
        }
    }

    public boolean sendToWarp(Warp warp) {
        return this.sendToWarp(warp, false);
    }

    public int sendToWarpWithDelay(Warp warp) {
        return this.sendToWarpWithDelay(warp, false);
    }

    public Location getLocation() {
        return this.bukkitPlayer.getLocation();
    }
}