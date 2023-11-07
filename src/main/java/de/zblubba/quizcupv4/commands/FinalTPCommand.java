package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FinalTPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(sender.hasPermission("quizcup.final")) {

            for(Player players : Bukkit.getOnlinePlayers()) {
                if(QuizCupV4.config.getList("finale_list").contains(players.getName())) {
                    players.teleport(new Location(Bukkit.getWorlds().get(0), 27.5, 58, 97.5, 180, 0));
                }
            }

        } else sender.sendMessage(MessageCollection.getNoPerms());
        return false;
    }
}
