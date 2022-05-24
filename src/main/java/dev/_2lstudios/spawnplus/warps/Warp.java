package dev._2lstudios.spawnplus.warps;

import org.bukkit.Location;
import org.bukkit.Material;

import dev._2lstudios.spawnplus.config.Configuration;

public class Warp {
    private Configuration config;

    private String id;
    private String displayName;
    private String description = "No description provided";
    private String permission = "";
    private int price = 0;
    private Location location;
    private Material icon = Material.BEDROCK;

    public Warp(String id, Configuration config) {
        this.id = id;
        this.config = config;
        this.displayName = id;
    }

    public void load() {
        this.displayName = config.getString("display-name", id);
        this.description = config.getString("description", description);
        this.permission = config.getString("permission", permission);
        this.price = config.getInt("price", price);
        this.location = config.getLocation("location");
        this.icon = config.getMaterial("icon", icon);
    }

    public void save() {
        config.set("display-name", this.displayName);
        config.set("description", this.description);
        config.set("permission", this.permission);
        config.set("price", this.price);
        config.setLocation("location", this.location);
        config.setMaterial("icon", this.icon);
        config.safeSave();
    }

    public void delete() {
        this.config.delete();
    }

    public String getID() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPermission() {
        return this.permission;
    }

    public Location getLocation() {
        return this.location;
    }

    public Material getIcon() {
        return this.icon;
    }

    public Warp setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public Warp setDescription(String description) {
        this.description = description;
        return this;
    }

    public Warp setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public Warp setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Warp setIcon(Material icon) {
        this.icon = icon;
        return this;
    }
}
