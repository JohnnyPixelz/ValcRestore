package com.valcanium.valcrestore;

import io.github.johnnypixelz.utilizer.exp.SetExpFix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RestoreListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RestoreManager.registerEntryAsync(player, new REntry(player.getInventory(), player.getHealth(), SetExpFix.getTotalExperience(player), player.getLocation()), RestoreType.JOIN);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        RestoreManager.registerEntryAsync(player, new REntry(player.getInventory(), player.getHealth(), SetExpFix.getTotalExperience(player), player.getLocation()), RestoreType.QUIT);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        RestoreManager.registerEntryAsync(player, new REntry(player.getInventory(), player.getHealth(), SetExpFix.getTotalExperience(player), player.getLocation()), RestoreType.DEATH);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        RestoreManager.registerEntryAsync(player, new REntry(player.getInventory(), player.getHealth(), SetExpFix.getTotalExperience(player), player.getLocation()), RestoreType.WORLD_CHANGE);
    }

}
