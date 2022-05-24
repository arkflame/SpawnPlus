package dev._2lstudios.spawnplus.players;

import org.bukkit.Location;

public class MagicWand {
    private Location pos1;
    private Location pos2;
    
    public Location getFirstPosition() {
        return this.pos1;
    }

    public Location getSecondPosition() {
        return this.pos2;
    }

    public void setFirstPosition(Location pos1) {
        this.pos1 = pos1;
    }

    public void setSecondPosition(Location pos2) {
        this.pos2 = pos2;
    }

    public boolean isComplete() {
        return this.pos1 != null && this.pos2 != null;
    }
}
