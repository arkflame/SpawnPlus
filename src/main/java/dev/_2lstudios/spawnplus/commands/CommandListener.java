package dev._2lstudios.spawnplus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import dev._2lstudios.spawnplus.SpawnPlus;
import dev._2lstudios.spawnplus.errors.BadArgumentException;
import dev._2lstudios.spawnplus.errors.PlayerOfflineException;
import dev._2lstudios.spawnplus.errors.WorldNotFoundException;
import dev._2lstudios.spawnplus.utils.ArrayUtils;

public abstract class CommandListener implements CommandExecutor {
    protected Command command;
    protected SpawnPlus plugin;

    private List<CommandListener> subCommands = new ArrayList<>();

    // For override
    protected void onExecuteByPlayer(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("common.no-by-player");
    }

    protected void onExecuteByConsole(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("common.no-by-console");
    }

    protected void onExecute(CommandContext ctx) {
        if (ctx.isPlayer()) {
            this.onExecuteByPlayer(ctx);
        } else {
            this.onExecuteByConsole(ctx);
        }
    }

    protected void onMissingPermission(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("common.no-permission");
    }

    protected void onBadUsage(CommandContext ctx) {
        String key = command.usageKey();
        if (key == null || key.isEmpty()) {
            key = command.name() + ".usage";
        }

        ctx.getExecutor().sendI18nMessage(key);
    }

    protected void onWorldNotFound(CommandContext ctx, String worldName) {
        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("common.world-not-found")
                .replace("{world}", worldName)
        );
    }

    protected void onPlayerOffline(CommandContext ctx, String playerName) {
        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("common.offline-player")
                .replace("{player}", playerName)
        );
    }

    protected void onBadArgument(CommandContext ctx, String value, String type) {
        ctx.getExecutor().sendMessage(
            ctx.getExecutor().getI18nMessage("common.arg-must-be-" + type)
                .replace("{arg}",value));
    }

    // Utils
    public Command getCommandInfo() {
        return this.command;
    }

    public void addSubcommand(CommandListener subCommand) {
        this.subCommands.add(subCommand);
    }

    public CommandListener getSubCommand(String name) {
        for (CommandListener subCommand : this.subCommands) {
            if (subCommand.getCommandInfo().name().equalsIgnoreCase(name)) {
                return subCommand;
            }
        }

        return null;
    }

    public void register(SpawnPlus plugin, boolean isSubCommand) {
        this.plugin = plugin;
        this.command = this.getClass().getAnnotation(Command.class);

        if (!isSubCommand) {
            plugin.getCommand(this.command.name()).setExecutor(this);
        }

        for (CommandListener subCommand : this.subCommands) {
            subCommand.register(this.plugin, true);
        }
    }

    // Command logic
    public void execute(CommandSender sender, String[] args) {
        CommandContext ctx = new CommandContext(this.plugin, sender, command.arguments());

        // Check for permissions
        if (!command.permission().isEmpty() && !sender.hasPermission(command.permission())) {
            this.onMissingPermission(ctx);
            return;
        }

        // Check for subcommands
        if (args.length > 0) {
            String possibleSubCommand = args[0];
            CommandListener sc = this.getSubCommand(possibleSubCommand);
            if (sc != null) {
                sc.execute(sender, ArrayUtils.removeFirstElement(args));
                return;
            }
        }

        // Check for arguments count
        if (command.minArguments() > args.length) {
            this.onBadUsage(ctx);
            return;
        }

        // Check for arguments type
        try {
            ctx.parseArguments(args);
            // Execute
            this.onExecute(ctx);
        } catch (PlayerOfflineException e) {
            this.onPlayerOffline(ctx, e.getUsername());
        } catch (WorldNotFoundException e) {
            this.onWorldNotFound(ctx, e.getWorldName());
        } catch (BadArgumentException e) {
            this.onBadArgument(ctx, e.getArg(), e.getWaiting());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        this.execute(sender, args);
        return true;
    }
}