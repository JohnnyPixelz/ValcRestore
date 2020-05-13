package com.valcanium.valcrestore.gui;

import com.valcanium.valcrestore.REntry;
import com.valcanium.valcrestore.ValcRestore;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import io.github.johnnypixelz.utilizer.exp.SetExpFix;
import io.github.johnnypixelz.utilizer.itemstack.ItemBuilder;
import io.github.johnnypixelz.utilizer.itemstack.PaneType;
import io.github.johnnypixelz.utilizer.itemstack.PremadeItems;
import io.github.johnnypixelz.utilizer.message.Colors;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RestoreGUI implements InventoryProvider {
    private OfflinePlayer target;
    private REntry entry;

    public static void open(Player player, OfflinePlayer target, SmartInventory parent, REntry entry) {
        SmartInventory.builder()
                .manager(ValcRestore.getInventoryManager())
                .provider(new RestoreGUI(target, entry))
                .parent(parent)
                .size(6, 9)
                .title(Colors.color("&f&l* &b" + target.getName() + "'s &fData &f&l*"))
                .build()
                .open(player);
    }

    public RestoreGUI(OfflinePlayer target, REntry entry) {
        this.target = target;
        this.entry = entry;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillRow(4, ClickableItem.empty(PremadeItems.getCustomPane(PaneType.GRAY)));

        for (int i = 0; i < 9; i++) {
            ItemStack stack = entry.getInventoryContents().get(i);
            if (stack == null) continue;
            contents.set(3, i, getClickItem(stack));
        }

        for (int i = 9; i < 36; i++) {
            ItemStack stack = entry.getInventoryContents().get(i);
            if (stack == null) continue;
            contents.set((i / 9) - 1, i % 9, getClickItem(stack));
        }

        for (int i = 0; i < 4; i++) {
            ItemStack stack = entry.getArmorContents().get(i);
            if (stack == null) continue;
            contents.set(5, i, getClickItem(stack));
        }

        contents.set(5, 8, ClickableItem.of(
                new ItemBuilder(Material.GOLDEN_APPLE)
                        .displayname("&fRestore &a&lHealth &7(&a" + (int) entry.getHealth() + " &fHP&7)")
                        .build(),
                click -> {
                    if (target.isOnline()) {
                        target.getPlayer().setHealth(entry.getHealth());
                        click.getWhoClicked().sendMessage(Colors.color("&7You have restored &b" + target.getName() + "'s &7health."));
                    } else {
                        click.getWhoClicked().sendMessage(Colors.color("&7Targeted player is not online."));
                    }
                }
        ));

        contents.set(5, 7, ClickableItem.of(
                new ItemBuilder(Material.EXP_BOTTLE)
                        .displayname("&fRestore &d&lExperience &7(&d" + entry.getExp() + " &fExp&7)")
                        .lore("", "&7Left Click to set the user's exp to &f" + entry.getExp() + "&7.", "&7Right Click to give &f" + entry.getExp() + " &7exp to user.")
                        .build(),
                click -> {
                    if (target.isOnline()) {
                        if (click.isLeftClick()) {
                            SetExpFix.setTotalExperience(target.getPlayer(), entry.getExp());
                            click.getWhoClicked().sendMessage(Colors.color("&7Successfully set &b" + target.getName() + "'s &7exp to &f" + entry.getExp() + "&7."));
                        } else if (click.isRightClick()) {
                            SetExpFix.setTotalExperience(target.getPlayer(), SetExpFix.getTotalExperience(target.getPlayer()) + entry.getExp());
                            click.getWhoClicked().sendMessage(Colors.color("&7Successfully given &f" + entry.getExp() + " &7exp to &b" + target.getName() + "&7."));
                        }
                    } else {
                        click.getWhoClicked().sendMessage(Colors.color("&7Targeted player is not online."));
                    }
                }
        ));

        contents.set(5, 6, ClickableItem.of(
                new ItemBuilder(Material.ENDER_PEARL)
                        .displayname("&fTeleport to entry's location")
                        .build(),
                click -> {
                    click.getWhoClicked().teleport(new Location(Bukkit.getWorld(entry.getWorld()), entry.getX(), entry.getY(), entry.getZ()));
                    click.getWhoClicked().sendMessage(Colors.color("&7You have teleported to &f" + (int) entry.getX() + " " + (int) entry.getY() + " " + (int) entry.getZ()));
                }
        ));

        contents.set(5, 5, ClickableItem.of(
                new ItemBuilder(Material.CHEST)
                        .displayname("&fRestore player's items")
                        .build(),
                click -> {
                    if (target.isOnline()) {
                        ItemStack[] invContents = new ItemStack[entry.getInventoryContents().size()];
                        entry.getInventoryContents().toArray(invContents);
                        target.getPlayer().getInventory().setContents(invContents);

                        ItemStack[] armContents = new ItemStack[entry.getArmorContents().size()];
                        entry.getArmorContents().toArray(armContents);
                        target.getPlayer().getInventory().setArmorContents(armContents);
                        click.getWhoClicked().sendMessage(Colors.color("&7You have restored &b" + target.getName() + "'s &7inventory to a previous state."));
                    } else {
                        click.getWhoClicked().sendMessage(Colors.color("&7Targeted player is not online."));
                    }
                }
        ));

        contents.set(5, 4, ClickableItem.empty(PremadeItems.getCustomPane(PaneType.GRAY)));

        contents.set(4, 4, ClickableItem.of(new ItemBuilder(Material.BARRIER).displayname("&c&lBack").build(), click -> {
            contents.inventory().getParent().get().open((Player) click.getWhoClicked());
        }));
    }

    public ClickableItem getClickItem(ItemStack stack) {
        ItemStack clone = stack.clone();
        return ClickableItem.of(clone, click -> {
            Player clicker = (Player) click.getWhoClicked();
            clicker.playSound(clicker.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
            click.getWhoClicked().getInventory().addItem(clone);
        });
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
