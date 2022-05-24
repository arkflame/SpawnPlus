package dev._2lstudios.spawnplus.portals;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.players.MagicWand;

public class PortalManager {
    private File directory;
    private Map<String, Portal> portals;
    
    public PortalManager(SpawnPlus plugin) {
        this.directory = new File(plugin.getDataFolder(), "portals");
        this.portals = new HashMap<>();
    }

    public boolean createPortal(String name, MagicWand wand) {
        name = name.toLowerCase();

        if (this.getPortal(name) != null) {
            return false;
        }

        File file = new File(this.directory, name + ".yml");
        Configuration config = new Configuration(file);

        try {
            config.create();
            Portal portal = new Portal(config);
            portal.setLocations(wand.getFirstPosition(), wand.getSecondPosition());
            portal.save();
            this.portals.put(name, portal);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void load() {
        if (!this.directory.exists()) {
            this.directory.mkdirs();
        } else {
            for (File file : this.directory.listFiles()) {
                if (file.getName().endsWith(".yml")) {
                    Configuration portalConfig = new Configuration(file);
                    try {
                        portalConfig.load();

                        Portal portal = new Portal(portalConfig);
                        portal.load();
                        this.portals.put(file.getName().split("\\.")[0], portal);
                        System.out.println("Added");
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public Collection<Portal> getPortals() {
        return this.portals.values();
    }

    public Portal getPortal(String name) {
        return this.portals.get(name.toLowerCase());
    }
}
