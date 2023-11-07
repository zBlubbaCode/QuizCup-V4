package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player p) {
            if(args.length == 1) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                if(target == null) {
                    p.sendMessage(MessageCollection.getPrefix() + "§cungültiger Spieler!");
                    return false;
                }

                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setOwningPlayer(target);
                skullMeta.setDisplayName("§c" + target.getName() + "'s Kopf");
                skull.setItemMeta(skullMeta);

                p.getInventory().addItem(skull);
                p.sendMessage(MessageCollection.getPrefix() + "Du hast den Kopf von §c" + target.getName() + " §7erhalten!");

            } else p.sendMessage(MessageCollection.getPrefix() + "§cNutze /skull <spieler>");
        }
        return false;
    }
}
