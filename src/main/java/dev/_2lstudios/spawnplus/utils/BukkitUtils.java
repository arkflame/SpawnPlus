package dev._2lstudios.spawnplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class BukkitUtils {
    public static float getRandom(float min, float max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    public static void spawnFireworks(Location location, int amount) {
        Location loc = location;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(0);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.WHITE, Color.YELLOW, Color.RED).trail(true).build());
        fw.setFireworkMeta(fwm);

        for (int i = 0; i < amount - 1; i++) {
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }

    public static Location getRandomizedLocation(Location location, int radius) {
        if (location != null) {
            float randomY = getRandom(-radius, radius);
            float randomZ = getRandom(-radius, radius);

            return location.clone().add(randomZ, 0, randomY);
        }

        return location;
    }

    public static void teleport(Entity entity, Location location, int radius) {
        if (location != null) {
            entity.teleport(getRandomizedLocation(location, radius));
        }
    }

    public static Sound getSound(String name) {
        for (final Sound sound : Sound.values()) {
            if (name.equalsIgnoreCase(sound.name())) {
                return sound;
            }
        }

        Bukkit.getLogger().warning("Couldn't load sound '" + name + "' (Invalid name?)");
        return null;
    }

    public static Material getMaterial(String name) {
        for (final Material mat : Material.values()) {
            if (name.equals(mat.name())) {
                return mat;
            }
        }

        Bukkit.getLogger().warning("Couldn't load material '" + name + "' (Invalid name?)");
        return null;
    }
}
