package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {

            if(args.length == 0) {
                p.setAllowFlight(!p.getAllowFlight());
                if(p.getAllowFlight()) {p.sendMessage(MessageCollection.getPrefix() + "§7Flugmodus §aaktiviert");} else
                    p.sendMessage(MessageCollection.getPrefix() + "§7Flugmodus §cdeaktiviert");
            } else if(args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    p.sendMessage(MessageCollection.getPrefix() + "§cungültiger Spieler!");
                    return false;
                }

                target.setAllowFlight(!target.getAllowFlight());
                if(target.getAllowFlight()) {p.sendMessage(MessageCollection.getPrefix() + "§7Flugmodus von " + target.getName() + " §aaktiviert");} else
                    p.sendMessage(MessageCollection.getPrefix() + "§7Flugmodus von " + target.getName() + " §cdeaktiviert");
            } else {
                p.sendMessage(MessageCollection.getPrefix() + "§cAlias: /fly <spieler>");
            }

        }
        return false;
    }
}
