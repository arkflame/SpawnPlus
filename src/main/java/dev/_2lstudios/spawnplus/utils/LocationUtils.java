package dev._2lstudios.spawnplus.utils;

import org.bukkit.Location;

public class LocationUtils {
    private static boolean isBetween(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean inCuboid(Location origin, Location l1, Location l2){
        int x = origin.getBlockX();
        int y = origin.getBlockY();
        int z = origin.getBlockZ();

        int minX = Math.min(l1.getBlockX(), l2.getBlockX());
        int minY = Math.min(l1.getBlockY(), l2.getBlockY());
        int minZ = Math.min(l1.getBlockZ(), l2.getBlockZ());

        int maxX = Math.max(l1.getBlockX(), l2.getBlockX());
        int maxY = Math.max(l1.getBlockY(), l2.getBlockY());
        int maxZ = Math.max(l1.getBlockZ(), l2.getBlockZ());

        return isBetween(x, minX, maxX) && isBetween(y, minY, maxY) && isBetween(z, minZ, maxZ);
    }
}
