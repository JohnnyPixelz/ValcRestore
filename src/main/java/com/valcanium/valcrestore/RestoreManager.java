package com.valcanium.valcrestore;

import com.google.gson.reflect.TypeToken;
import io.github.johnnypixelz.utilizer.file.Persistence;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestoreManager {
    private static Map<UUID, RPlayer> rplayerMap;

    public static void load() {
        rplayerMap = Persistence.loadFile("data", new TypeToken<Map<UUID, RPlayer>>(){});

        if (rplayerMap == null) rplayerMap = new HashMap<>();
    }

    public static void save() {
        Persistence.saveFile("data", rplayerMap);
    }

    public static void registerEntry(Player player, REntry entry, RestoreType type) {
            getRPlayer(player).addEntry(entry, type);
    }

    public static void registerEntryAsync(Player player, REntry entry, RestoreType type) {
        Bukkit.getScheduler().runTaskAsynchronously(ValcRestore.getInstance(), () -> registerEntry(player, entry, type));
    }

    public static RPlayer getRPlayer(OfflinePlayer player) {
        RPlayer rplayer = rplayerMap.get(player.getUniqueId());
        if (rplayer == null) {
            rplayer = new RPlayer(player);
            rplayerMap.put(player.getUniqueId(), rplayer);
        }

        return rplayer;
    }
}
