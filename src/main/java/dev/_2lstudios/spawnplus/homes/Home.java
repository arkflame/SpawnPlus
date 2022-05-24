package dev._2lstudios.spawnplus.homes;

import org.bukkit.Location;

public class Home {
    private Location location;

    public Home(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
