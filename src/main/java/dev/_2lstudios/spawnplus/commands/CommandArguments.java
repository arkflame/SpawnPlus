package dev._2lstudios.spawnplus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.errors.BadArgumentException;
import dev._2lstudios.spawnplus.errors.PlayerOfflineException;
import dev._2lstudios.spawnplus.errors.WorldNotFoundException;
import dev._2lstudios.spawnplus.players.SpawnPlayer;

public class CommandArguments {
    private SpawnPlus plugin;
    private Argument[] requiredArguments;
    private boolean isParsingLargeString = false;
    
    private List<Object> arguments = new ArrayList<>();

    public CommandArguments(SpawnPlus plugin, Argument[] requiredArguments) {
        this.plugin = plugin;
        this.requiredArguments = requiredArguments;
    }

    public boolean hasIndex(int index) {
        return index < arguments.size() && index >= 0;
    }

    public World getWorld(int index) {
        if (!this.hasIndex(index)) {
            return null;
        }

        return (World) this.arguments.get(index);
    }

    public String getString(int index) {
        if (!this.hasIndex(index)) {
            return null;
        }

        return (String) this.arguments.get(index);
    }

    public int getInt(int index) {
        if (!this.hasIndex(index)) {
            return 0;
        }

        return (int) this.arguments.get(index);
    }

    public boolean getBoolean(int index) {
        if (!this.hasIndex(index)) {
            return false;
        }

        return (boolean) this.arguments.get(index);
    }

    public SpawnPlayer getPlayer(int index) {
        if (!this.hasIndex(index)) {
            return null;
        }

        return (SpawnPlayer) this.arguments.get(index);
    }

    public void parse(String[] args) throws BadArgumentException, PlayerOfflineException, WorldNotFoundException {
        int i = 0;

        for (String arg : args) {
            if (this.requiredArguments.length <= i) {
                if (isParsingLargeString) {
                    int index = this.arguments.size() - 1;
                    String str = (String) this.arguments.get(index);
                    str += " " + arg;
                    this.arguments.set(index, str);
                } else {
                    this.arguments.add(arg);
                }
                continue;
            }

            Argument type = this.requiredArguments[i];
            Object value = null;

            if (type == Argument.LARGE_STRING)
            {
                isParsingLargeString = true;
                value = arg;
            } 
            
            else if (type == Argument.BOOL) {
                if (arg.equalsIgnoreCase("true")) {
                    value = true;
                } else if (arg.equalsIgnoreCase("false")) {
                    value = false;
                }
                throw new BadArgumentException(arg, "boolean");
            }

            else if (type == Argument.INT) {
                try {
                    value = Integer.parseInt(arg);
                } catch (Exception _exception) {
                    throw new BadArgumentException(arg, "number");
                }
            }

            else if (type == Argument.ONLINE_PLAYER) {
                Player player = Bukkit.getServer().getPlayerExact(arg);
                if (player.isOnline()) {
                    value = this.plugin.getPlayerManager().getPlayer(player);
                } else {
                    throw new PlayerOfflineException(arg);
                }
            }

            else if (type == Argument.WORLD) {
                World world = Bukkit.getServer().getWorld(arg);
                if (world != null) {
                    value = world;
                } else {
                    throw new WorldNotFoundException(arg);
                }
            }

            else if (type == Argument.STRING) {
                value = arg;
            }

            i++;
            this.arguments.add(value);
        }
    }
}
