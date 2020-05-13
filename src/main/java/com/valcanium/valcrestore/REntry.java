package com.valcanium.valcrestore;

import de.tr7zw.changeme.nbtapi.NBTContainer;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class REntry {
    private List<String> armor;
    private List<String> inventory;
    private int exp;
    private double health;
    private int x;
    private int y;
    private int z;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    private String world;
    private long timestamp;

    public REntry(PlayerInventory playerInventory, double health, int exp, Location location) {
        timestamp = System.currentTimeMillis();
        this.exp = exp;
        this.health = health;
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.world = location.getWorld().getName();

        armor = new ArrayList<>();
        for (int i = 0; i < playerInventory.getArmorContents().length; i++) {
            ItemStack item = playerInventory.getArmorContents()[i];
            if (item == null || item.getType().equals(Material.AIR)) {
                armor.add(null);
            } else {
                armor.add(convertItemToString(item));
            }
        }

        inventory = new ArrayList<>();
        for (int i = 0; i < playerInventory.getContents().length; i++) {
            ItemStack item = playerInventory.getContents()[i];
            if (item == null || item.getType().equals(Material.AIR)) {
                inventory.add(null);
            } else {
                inventory.add(convertItemToString(item));
            }
        }
    }

    public int getExp() {
        return exp;
    }

    public List<ItemStack> getArmorContents() {
        List<ItemStack> armorContents = new ArrayList<>();
        for (String s : armor) {
            if (s == null) {
                armorContents.add(null);
            } else {
                armorContents.add(convertStringToItem(s));
            }
        }
        return armorContents;
    }

    public List<ItemStack> getInventoryContents() {
        List<ItemStack> invContents = new ArrayList<>();
        for (String s : inventory) {
            if (s == null) {
                invContents.add(null);
            } else {
                invContents.add(convertStringToItem(s));
            }
        }
        return invContents;
    }

    public double getHealth() {
        return health;
    }

    public long getTimestamp() {
        return timestamp;
    }

    private ItemStack convertStringToItem(String string) {
        return NBTItem.convertNBTtoItem(new NBTContainer(string));
    }

    private String convertItemToString(ItemStack stack) {
        return NBTItem.convertItemtoNBT(stack).toString();
    }

    public String getWorld() {
        return world;
    }
}
