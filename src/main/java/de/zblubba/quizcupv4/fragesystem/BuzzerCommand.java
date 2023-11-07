package de.zblubba.quizcupv4.fragesystem;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuzzerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(sender instanceof Player p) {
            if(QuizCupV4.config.getList("finale_list").contains(p.getName())) {

                for(Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage("§6");
                    players.sendMessage("§8============================");
                    players.sendMessage("§c§l" + p.getName() + " §7hat den §cBuzzer §7gedrückt!");
                    players.sendMessage("§8============================");
                    players.sendMessage("§6");
                }

            } else p.sendMessage(MessageCollection.getNoPerms());
        } else sender.sendMessage(MessageCollection.mustbePlayer());
        return false;
    }
}
