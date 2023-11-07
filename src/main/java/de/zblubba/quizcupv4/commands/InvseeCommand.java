package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p) {
            if(strings.length == 1) {
                Player target = Bukkit.getPlayer(strings[0]);

                if(target == null) {
                    p.sendMessage(MessageCollection.getPrefix() + "§cungültiger Spieler!");
                    return false;
                }

                p.openInventory(target.getInventory());
                p.sendMessage(MessageCollection.getPrefix() + "Inventar von §c" + target.getName() + " §7geöffnet.");
            } else p.sendMessage(MessageCollection.getPrefix() + "§cNutze: /invsee <spieler>");
        }
        return false;
    }
}
