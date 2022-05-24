package dev._2lstudios.spawnplus.portals;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.utils.LocationUtils;

public class Portal {
    private Configuration config;

    private List<PortalAction> actions;
    private Location pos1;
    private Location pos2;
    private String permission;
    private int price;

    private List<SpawnPlayer> players;
    
    public Portal(Configuration config) {
        this.actions = new ArrayList<>();
        this.config = config;
        this.players = new ArrayList<>();
    }

    public void load() {
        this.actions = PortalAction.parseActions(config.getStringList("actions"));
        this.pos1 = config.getLocation("area.first");
        this.pos2 = config.getLocation("area.second");
        this.permission = config.getString("permission");
        this.price = config.getInt("price");
    }

    public void save() {
        config.set("permission", this.permission);
        config.set("price", this.price);
        config.setLocation("area.first", this.pos1);
        config.setLocation("area.second", this.pos2);

        config.safeSave();
    }

    public boolean addAction(String type, String arg) {
        String rawAction = type + ": " + arg;
        PortalAction action = PortalAction.parseAction(rawAction);

        if (action != null) {
            List<String> list = config.getStringList("actions");
            list.add(rawAction);
            config.set("actions", list);
            
            config.safeSave();
            this.actions.add(action);
            return true;
        }

        return false;
    }

    public boolean addAction(PortalActionType type, String arg) {
        return this.addAction(type.name().toLowerCase(), arg);
    }

    public void setLocations(Location first, Location second) {
        this.pos1 = first;
        this.pos2 = second;
    }

    public Location getFirstPosition() {
        return this.pos1;
    }

    public Location getSecondPosition() {
        return this.pos2;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void run(SpawnPlayer player) {
        for (PortalAction action : this.actions) {
            action.run(player);
        }
    }

    public boolean isInPortalRegion(SpawnPlayer player) {
        Location playerPosition = player.getBukkitPlayer().getLocation();
        return LocationUtils.inCuboid(playerPosition, pos1, pos2);
    }

    public void handle(SpawnPlayer player) {
        if (this.permission != null && !this.permission.isEmpty() && !player.hasPermission(this.permission)) {
            player.sendI18nMessage("portal.no-permission");
            return;
        }

        if (this.price > 1) {
            player.sendI18nMessage("portal.no-balance");
            return;
        }

        this.run(player);
    }

    public void checkAndHandle(SpawnPlayer player) {
        if (this.isInPortalRegion(player)) {
            if (!this.players.contains(player)) {
                this.players.add(player);
                this.handle(player);
            }
        } else if (this.players.contains(player)) {
            this.players.remove(player);
        }
    }
}
