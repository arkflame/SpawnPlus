package dev._2lstudios.spawnplus;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.spawnplus.api.SpawnPlusAPI;
import dev._2lstudios.spawnplus.api.events.AbstractEvent;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.commands.impl.BackCommand;
import dev._2lstudios.spawnplus.commands.impl.DelHomeCommand;
import dev._2lstudios.spawnplus.commands.impl.DelWarpCommand;
import dev._2lstudios.spawnplus.commands.impl.HomeCommand;
import dev._2lstudios.spawnplus.commands.impl.PortalCommand;
import dev._2lstudios.spawnplus.commands.impl.SetHomeCommand;
import dev._2lstudios.spawnplus.commands.impl.SetSpawnCommand;
import dev._2lstudios.spawnplus.commands.impl.SetWarpCommand;
import dev._2lstudios.spawnplus.commands.impl.SpawnCommand;
import dev._2lstudios.spawnplus.commands.impl.TPAcceptCommand;
import dev._2lstudios.spawnplus.commands.impl.TPACommand;
import dev._2lstudios.spawnplus.commands.impl.TPAHereCommand;
import dev._2lstudios.spawnplus.commands.impl.TopCommand;
import dev._2lstudios.spawnplus.commands.impl.WarpCommand;
import dev._2lstudios.spawnplus.commands.impl.WorldCommand;
import dev._2lstudios.spawnplus.config.ConfigManager;
import dev._2lstudios.spawnplus.config.Configuration;
import dev._2lstudios.spawnplus.homes.HomesLoadTask;
import dev._2lstudios.spawnplus.homes.HomesSaveTask;
import dev._2lstudios.spawnplus.i18n.LanguageManager;
import dev._2lstudios.spawnplus.listeners.EntityDamageByEntityListener;
import dev._2lstudios.spawnplus.listeners.PlayerInteractListener;
import dev._2lstudios.spawnplus.listeners.PlayerJoinListener;
import dev._2lstudios.spawnplus.listeners.PlayerMoveListener;
import dev._2lstudios.spawnplus.listeners.PlayerQuitListener;
import dev._2lstudios.spawnplus.listeners.PlayerRespawnListener;
import dev._2lstudios.spawnplus.listeners.PlayerTeleportListener;
import dev._2lstudios.spawnplus.players.SpawnPlayerManager;
import dev._2lstudios.spawnplus.portals.PortalManager;
import dev._2lstudios.spawnplus.teleport.PendingTeleportManager;
import dev._2lstudios.spawnplus.teleport.TeleportProcessorTask;
import dev._2lstudios.spawnplus.warps.WarpManager;

public class SpawnPlus extends JavaPlugin {
    private HomesSaveTask homesSaveTask;
    private HomesLoadTask homesLoadTask;

    private ConfigManager configManager;
    private LanguageManager languageManager;
    private SpawnPlayerManager playerManager;
    private PendingTeleportManager teleportManager;
    private PortalManager portalManager;
    private WarpManager warpManager;

    private void addCommand(CommandListener command) {
        command.register(this, false);
    }

    private void addListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
    
    public boolean callEvent(AbstractEvent event) {
        this.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public void addTaskTimer(Runnable task, long interval) {
        this.getServer().getScheduler().runTaskTimer(this, task, interval, interval);
    }

    public void addAsyncTaskTimer(Runnable task, long interval) {
        this.getServer().getScheduler().runTaskTimerAsynchronously(this, task, interval, interval);
    }
    
    @Override
    public void onEnable () {
        // Initialize API
        new SpawnPlusAPI(this);

        // Create tasks
        this.homesSaveTask = new HomesSaveTask();
        this.homesLoadTask = new HomesLoadTask(this);

        // Instantiate managers
        this.configManager = new ConfigManager(this);
        this.languageManager = new LanguageManager(this);
        this.playerManager = new SpawnPlayerManager(this);
        this.portalManager = new PortalManager(this);
        this.teleportManager = new PendingTeleportManager();
        this.warpManager = new WarpManager(this);

        // Load data
        this.languageManager.loadLanguagesSafe();
        this.playerManager.addAll();
        this.portalManager.load();
        this.warpManager.load();

        // Register listeners
        this.addListener(new EntityDamageByEntityListener(this));
        this.addListener(new PlayerInteractListener(this));
        this.addListener(new PlayerJoinListener(this));
        this.addListener(new PlayerMoveListener(this));
        this.addListener(new PlayerQuitListener(this));
        this.addListener(new PlayerRespawnListener(this));
        this.addListener(new PlayerTeleportListener(this));

        // Register commands
        this.addCommand(new BackCommand());
        this.addCommand(new DelHomeCommand());
        this.addCommand(new DelWarpCommand());
        this.addCommand(new HomeCommand());
        this.addCommand(new PortalCommand());
        this.addCommand(new SetHomeCommand());
        this.addCommand(new SetSpawnCommand());
        this.addCommand(new SetWarpCommand());
        this.addCommand(new SpawnCommand());
        this.addCommand(new TopCommand());
        this.addCommand(new TPAcceptCommand());
        this.addCommand(new TPACommand());
        this.addCommand(new TPAHereCommand());
        this.addCommand(new WarpCommand());
        this.addCommand(new WorldCommand());

        // Register tasks
        this.addTaskTimer(new TeleportProcessorTask(this), 20L);
        this.addAsyncTaskTimer(this.homesSaveTask, 20L * 30);
        this.addAsyncTaskTimer(this.homesLoadTask, 20L);

        // Register channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    // Configuration getters
    public Configuration getConfig() {
        return this.configManager.getConfig("config.yml");
    }

    public Configuration getSpawnConfig() {
        return this.configManager.getConfig("spawn.yml");
    }

    // Managers getters
    public LanguageManager getLanguageManager() {
        return this.languageManager;
    }

    public SpawnPlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public PortalManager getPortalManager() {
        return this.portalManager;
    }

    public PendingTeleportManager getTeleportManager() {
        return this.teleportManager;
    }

    public WarpManager getWarpManager() {
        return this.warpManager;
    }

    // Others getters
    public Location getSpawn() {
        return this.getSpawnConfig().getLocation();
    }
    
    public boolean hasPlugin(String pluginName) {
        Plugin plugin = this.getServer().getPluginManager().getPlugin(pluginName);
        return plugin != null && plugin.isEnabled();
    }

    public HomesSaveTask getHomesSaveTask() {
        return homesSaveTask;
    }

    public HomesLoadTask getHomesLoadTask() {
        return homesLoadTask;
    }
}