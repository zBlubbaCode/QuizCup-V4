package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZumStartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("quizcup.start")) {

            for(Player players : Bukkit.getOnlinePlayers()) {
                players.sendMessage("§8 ");
                players.sendMessage("§8 ");
                players.sendMessage("§8 ");
                players.sendMessage("§8 ");
                players.sendMessage("§8 ");
                players.sendMessage("§8------------------------------------");
                players.sendMessage("§cACHTUNG §8| §7Es sollen sich bitte §calle sofort zum Spawn §7begeben!");
                players.sendMessage("§7Die nächste Frage folgt in Kürze!");
                players.sendMessage("§8------------------------------------");

                players.sendTitle("§4§c⚠", "§cBitte alle zum Spawn kommen!", 1, 180, 1);
            }

        } else {
            sender.sendMessage(MessageCollection.getNoPerms());
        }
        return false;
    }
}
