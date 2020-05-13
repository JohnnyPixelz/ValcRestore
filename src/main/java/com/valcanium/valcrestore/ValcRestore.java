package com.valcanium.valcrestore;

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import fr.minuskube.inv.InventoryManager;
import io.github.johnnypixelz.utilizer.plugin.UtilPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ValcRestore extends UtilPlugin {
    private static PaperCommandManager commandManager;
    private static InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        commandManager = new PaperCommandManager(this);
        commandManager.setFormat(MessageType.SYNTAX, ChatColor.GRAY, ChatColor.AQUA);
        commandManager.registerCommand(new RestoreCommand());

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        Bukkit.getPluginManager().registerEvents(new RestoreListener(), this);

        RestoreManager.load();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, RestoreManager::save, 20L, 20*60);
    }

    @Override
    public void onDisable() {
        RestoreManager.save();
    }

    public static PaperCommandManager getCommandManager() {
        return commandManager;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
