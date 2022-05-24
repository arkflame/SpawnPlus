package dev._2lstudios.spawnplus.portals.impl;

import org.bukkit.Sound;

import dev._2lstudios.spawnplus.players.SpawnPlayer;
import dev._2lstudios.spawnplus.portals.PortalAction;
import dev._2lstudios.spawnplus.utils.BukkitUtils;

public class SoundPortalAction extends PortalAction {
    private Sound sound;

    public SoundPortalAction(String sound) {
        this.sound = BukkitUtils.getSound(sound);
    }

    @Override
    public void run(SpawnPlayer player) {
        if (this.sound != null) {
            player.playSound(this.sound);
        }
    }
}
