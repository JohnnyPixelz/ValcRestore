package com.valcanium.valcrestore.gui;

import com.valcanium.valcrestore.RestoreType;
import com.valcanium.valcrestore.ValcRestore;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import io.github.johnnypixelz.utilizer.itemstack.ItemBuilder;
import io.github.johnnypixelz.utilizer.itemstack.PaneType;
import io.github.johnnypixelz.utilizer.itemstack.PremadeItems;
import io.github.johnnypixelz.utilizer.message.Colors;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.stream.IntStream;

public class CategoryGUI implements InventoryProvider {
    private OfflinePlayer target;

    public static void open(Player player, OfflinePlayer target) {

        SmartInventory.builder()
                .provider(new CategoryGUI(target))
                .size(5, 9)
                .title(Colors.color("&f&l* &b" + target.getName() + "'s &fData &f&l*"))
                .manager(ValcRestore.getInventoryManager())
                .build()
                .open(player);
    }

    public CategoryGUI(OfflinePlayer target) {
        this.target = target;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(PremadeItems.getCustomPane(PaneType.GRAY)));

        contents.set(2, 2, getItem(target, Material.COMPASS, RestoreType.JOIN, contents.inventory()));
        contents.set(2, 3, getItem(target, Material.BED, RestoreType.QUIT, contents.inventory()));
        contents.set(2, 5, getItem(target, Material.DIAMOND_SWORD, RestoreType.DEATH, contents.inventory()));
        contents.set(2, 6, getItem(target, Material.GRASS, RestoreType.WORLD_CHANGE, contents.inventory()));

    }

    public ClickableItem getItem(OfflinePlayer target, Material material, RestoreType type, SmartInventory parent) {
        return ClickableItem.of(
                new ItemBuilder(material).displayname("&b" + type.getType()).build(),
                click -> {
                    Player clicker = (Player) click.getWhoClicked();
                    clicker.playSound(clicker.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
                    EntryGUI.open(clicker, target, type, parent);
                }
        );
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {

    }
}
