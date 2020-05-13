package com.valcanium.valcrestore;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.valcanium.valcrestore.gui.CategoryGUI;
import de.tr7zw.changeme.nbtapi.NBTItem;
import io.github.johnnypixelz.utilizer.itemstack.ItemUtils;
import io.github.johnnypixelz.utilizer.itemstack.PaneType;
import io.github.johnnypixelz.utilizer.itemstack.PremadeItems;
import io.github.johnnypixelz.utilizer.message.Colors;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@CommandAlias("valcrestore|restore")
public class RestoreCommand extends BaseCommand {

    @Default
    @CommandPermission("valcrestore.use")
    @CommandCompletion("@players ")
    public void onRestore(CommandSender sender, @Optional OfflinePlayer player) {
        if (player == null) {

            List<String> response = Arrays.asList(
                    "&f&m--------&f< &9&lRestore &f>&m--------",
                    "",
                    " &9/restore &f<name>",
                    ""
            );

            response.stream().map(Colors::color).forEach(sender::sendMessage);
        } else if (sender instanceof Player) {
            ((Player) sender).playSound(((Player) sender).getLocation(), Sound.CHEST_OPEN, 1, 1);
            CategoryGUI.open((Player) sender, player);
            PremadeItems.getCustomPane(PaneType.GRAY);
        }
    }

}
