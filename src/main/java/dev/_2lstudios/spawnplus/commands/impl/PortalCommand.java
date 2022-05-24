package dev._2lstudios.spawnplus.commands.impl;

import dev._2lstudios.spawnplus.commands.Command;
import dev._2lstudios.spawnplus.commands.CommandContext;
import dev._2lstudios.spawnplus.commands.CommandListener;
import dev._2lstudios.spawnplus.commands.impl.portal.PortalAddCMDCommand;
import dev._2lstudios.spawnplus.commands.impl.portal.PortalAddServerCommand;
import dev._2lstudios.spawnplus.commands.impl.portal.PortalAddSoundCommand;
import dev._2lstudios.spawnplus.commands.impl.portal.PortalAddTellCommand;
import dev._2lstudios.spawnplus.commands.impl.portal.PortalCreateCommand;
import dev._2lstudios.spawnplus.commands.impl.portal.PortalPermissionCommand;
import dev._2lstudios.spawnplus.commands.impl.portal.PortalPriceCommand;
import dev._2lstudios.spawnplus.commands.impl.portal.PortalWandCommand;

@Command(
    name = "portal",
    permission = "spawnplus.admin-portal"
)
public class PortalCommand extends CommandListener {
    public PortalCommand() {
        this.addSubcommand(new PortalAddCMDCommand());
        this.addSubcommand(new PortalAddServerCommand());
        this.addSubcommand(new PortalAddSoundCommand());
        this.addSubcommand(new PortalAddTellCommand());
        this.addSubcommand(new PortalCreateCommand());
        this.addSubcommand(new PortalPermissionCommand());
        this.addSubcommand(new PortalPriceCommand());
        this.addSubcommand(new PortalWandCommand());
    }

    @Override
    public void onExecute(CommandContext ctx) {
        ctx.getExecutor().sendI18nMessage("portal.help");
    }
}
