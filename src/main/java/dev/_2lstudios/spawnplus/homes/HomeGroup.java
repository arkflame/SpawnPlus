package dev._2lstudios.spawnplus.homes;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;

import dev._2lstudios.spawnplus.config.Configuration;

public class HomeGroup {
    private HomesSaveTask homesSaveTask;
    private Configuration config;
    private Map<String, Home> homes;
    private boolean loaded;
    private boolean beingLoaded;

    public HomeGroup(HomesSaveTask homesSaveTask, Configuration config) {
        this.homesSaveTask = homesSaveTask;
        this.config = config;
        this.homes = new HashMap<>();
        this.loaded = false;
        this.beingLoaded = false;
    }

    public String getFirstHome() {
        for (String home : this.homes.keySet()) {
            return home;
        }

        return null;
    }

    public Collection<String> getHomesNames() {
        return this.homes.keySet();
    }

    public String getAllHomesAsString() {
        String result = "";
        for (String home : this.getHomesNames()) {
            if (result != "") {
                result += ", ";
            }

            result += home;
        }
        return result;
    }

    public int getCount() {
        return this.homes.size();
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void load() {
        this.homes.clear();

        if (this.config.exists()) {
            try {
                this.config.load();

                for (String key : this.config.getKeys(false)) {
                    if (this.config.isConfigurationSection(key)) {
                        Location location = this.config.getLocation(key);
                        Home home = new Home(location);
                        this.homes.put(key, home);
                    }
                }
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

        this.loaded = true;
    }

    public void save() {
        try {
            this.config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queueSave() {
        this.homesSaveTask.add(this);
    }

    public Home addHome(String name, Home home) {
        this.homes.put(name, home);
        this.config.setLocation(name, home.getLocation());
        this.queueSave();
        return home;
    }

    public Home addHome(String name, Location location) {
        return this.addHome(name, new Home(location));
    }

    public Home getHome(String name) {
        return this.homes.get(name);
    }

    public Home delHome(String name) {
        Home home = this.homes.remove(name);
        if (home != null) {
            this.config.set(name, null);
            this.queueSave();
        }
        return home;
    }

    public boolean isBeingLoaded() {
        return this.beingLoaded;
    }

    public void setBeingLoaded(boolean beingLoaded) {
        this.beingLoaded = beingLoaded;
    }
}
