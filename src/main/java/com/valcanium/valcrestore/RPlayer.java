package com.valcanium.valcrestore;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class RPlayer {
    private UUID uuid;
    private HashMap<RestoreType, List<REntry>> entries;

    public RPlayer(OfflinePlayer player) {
        uuid = player.getUniqueId();
        entries = new HashMap<>();
    }

    public void addEntry(REntry entry, RestoreType type) {
        if (entries == null) entries = new HashMap<>();

        if (!entries.containsKey(type)) {
            entries.put(type, new ArrayList<>());
        }
        entries.get(type).add(entry);
    }

    public List<REntry> getEntries(RestoreType type) {
        if (!entries.containsKey(type)) {
            entries.put(type, new ArrayList<>());
        }

        return entries.get(type).stream().sorted(Comparator.comparingLong(entry -> -entry.getTimestamp())).collect(Collectors.toList());
    }

    public UUID getUniqueId() {
        return uuid;
    }
}
