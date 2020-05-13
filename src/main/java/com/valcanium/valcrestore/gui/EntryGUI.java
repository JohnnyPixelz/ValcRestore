package com.valcanium.valcrestore.gui;

import com.valcanium.valcrestore.*;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import io.github.johnnypixelz.utilizer.itemstack.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EntryGUI implements InventoryProvider {
    private OfflinePlayer target;
    private RestoreType type;

    public static void open(Player player, OfflinePlayer target, RestoreType type, SmartInventory parent) {
        SmartInventory.builder()
                .parent(parent)
                .size(6, 9)
                .manager(ValcRestore.getInventoryManager())
                .provider(new EntryGUI(target, type))
                .build()
                .open(player);
    }

    public EntryGUI(OfflinePlayer target, RestoreType type) {
        this.target = target;
        this.type = type;
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyy");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void init(Player player, InventoryContents contents) {
        RPlayer rplayer = RestoreManager.getRPlayer(target);
        List<REntry> entryList = rplayer.getEntries(type);

        List<ClickableItem> cItems = new ArrayList<>();
        entryList.forEach(entry -> {

            // Building the item for every entry
            ItemStack item = new ItemBuilder(Material.CHEST)
                    .displayname("&8» &f" + dateFormat.format(entry.getTimestamp()))
                    .lore(
                            "&8» &f" + timeFormat.format(entry.getTimestamp()),
                            "",
                            " &f&l* &9Exp &8» &f" + entry.getExp(),
                            " &f&l* &9Health &8» &f" + entry.getHealth(),
                            "",
                            " &f&l* &9World &8» &f" + entry.getWorld(),
                            " &f&l* &9X &8» &f" + entry.getX(),
                            " &f&l* &9Y &8» &f" + entry.getY(),
                            " &f&l* &9Z &8» &f" + entry.getZ()
                    )
                    .build();

            // Building the clickable item for every entry
            ClickableItem cItem = ClickableItem.of(item, click -> {
                Player clicker = (Player) click.getWhoClicked();
                clicker.playSound(clicker.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                RestoreGUI.open(clicker, target, contents.inventory(), entry);
            });

            // Adding the clickable items to a list
            cItems.add(cItem);
        });

        Pagination pagination = contents.pagination();

        ClickableItem[] itemArray = new ClickableItem[cItems.size()];
        for (int i = 0; i < itemArray.length; i++) itemArray[i] = cItems.get(i);

        pagination.setItems(itemArray);

        pagination.setItemsPerPage(45);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));

        contents.set(5, 4, ClickableItem.of(
                new ItemBuilder(Material.BARRIER).displayname("&c&lBack").build(),
                click -> {
                    Player clicker = (Player) click.getWhoClicked();
                    clicker.playSound(clicker.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                    contents.inventory().getParent().get().open(clicker);
                }
        ));

        contents.set(5, 3, ClickableItem.of(
                new ItemBuilder(Material.ARROW).displayname("&fPrevious Page").build(),
                click -> {
                    Player clicker = (Player) click.getWhoClicked();
                    clicker.playSound(clicker.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                    contents.inventory().open(clicker, pagination.previous().getPage());
                }
        ));

        contents.set(5, 5, ClickableItem.of(
                new ItemBuilder(Material.ARROW).displayname("&fNext Page").build(),
                click -> {
                    Player clicker = (Player) click.getWhoClicked();
                    clicker.playSound(clicker.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                    contents.inventory().open(clicker, pagination.next().getPage());
                }
        ));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
