package dev._2lstudios.spawnplus.warps;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.config.Configuration;

public class WarpManager {
    private File directory;
    private Map<String, Warp> warps;

    public WarpManager(SpawnPlus plugin) {
        this.directory = new File(plugin.getDataFolder(), "warps");
        this.warps = new HashMap<>();

        if (!this.directory.exists()) {
            this.directory.mkdirs();
        }
    }

    public void addWarp(Warp warp) {
        this.warps.put(warp.getID().toLowerCase(), warp);
    }

    public boolean deleteWarp(Warp warp) {
        if (warp != null) {
            this.warps.remove(warp.getID());
            warp.delete();
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteWarp(String warpID) {
        return this.deleteWarp(this.getWarp(warpID));
    }

    public boolean existWarp(String warpID) {
        return this.warps.containsKey(warpID.toLowerCase());
    }

    public boolean createWarp(String id, Location location) {
        if (!this.existWarp(id)) {
            File file = new File(this.directory, id.toLowerCase() + ".yml");

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            Warp warp = new Warp(id, new Configuration(file));
            warp.setLocation(location);
            warp.save();
            this.addWarp(warp);
            return true;
        } else {
            return false;
        }
    }

    public void load() {
        this.warps.clear();

        if (!this.directory.exists()) {
            this.directory.mkdirs();
        } else {
            for (File file : this.directory.listFiles()) {
                if (file.getName().endsWith(".yml")) {
                    String id = file.getName().split("\\.")[0];
                    Configuration config = new Configuration(file);

                    try {
                        config.load();
                        Warp warp = new Warp(id, config);
                        warp.load();
                        this.addWarp(warp);
                    } catch (IOException | InvalidConfigurationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Warp getWarp(String warpID) {
        return this.warps.get(warpID.toLowerCase());
    }
    
    public Collection<Warp> getWarps() {
        return this.warps.values();
    }
}
