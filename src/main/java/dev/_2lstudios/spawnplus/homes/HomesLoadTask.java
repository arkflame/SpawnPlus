package dev._2lstudios.spawnplus.homes;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.plugin.Plugin;

public class HomesLoadTask implements Runnable {
    private Plugin plugin;
    private Collection<HomeGroupLoadEntry> loadQueue = new HashSet<>();

    public HomesLoadTask(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!loadQueue.isEmpty()) {
            Collection<HomeGroupLoadEntry> loadQueue = new HashSet<>();

            loadQueue.addAll(this.loadQueue);
            this.loadQueue.clear();

            for (HomeGroupLoadEntry homeGroupLoadEntry : loadQueue) {
                HomeGroup homeGroup = homeGroupLoadEntry.getHomeGroup();

                homeGroup.load();
            }

            plugin.getServer().getScheduler().runTask(plugin, () -> {
                for (HomeGroupLoadEntry homeGroupLoadEntry : loadQueue) {
                    homeGroupLoadEntry.runAfterLoad();
                }
            });
        }
    }

    public void add(HomeGroupLoadEntry entry) {
        if (!entry.getHomeGroup().isBeingLoaded()) {
            this.loadQueue.add(entry);
            entry.getHomeGroup().setBeingLoaded(true);
        }
    }
}
