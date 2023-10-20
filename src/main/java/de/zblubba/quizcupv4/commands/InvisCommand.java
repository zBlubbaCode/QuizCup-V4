package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvisCommand implements CommandExecutor {

    public static boolean isInvis;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("quizcup.visibility")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("on")) {
                    setIsInvis(true);

                    for(Player players : Bukkit.getOnlinePlayers()) {
                        for(int i = 0; i < QuizCupV4.playerList.size(); i++) {
                            Player p = Bukkit.getPlayer(QuizCupV4.playerList.get(i));
                            if(!players.hasPermission("quizcup.helper")) {
                                p.hidePlayer(players);
                            }
                        }
                    }

                    sender.sendMessage(MessageCollection.getPrefix() + "Alle Spieler sind nun für andere Spieler §cunsichtbar§7!");
                } else if(args[0].equalsIgnoreCase("off")) {
                    setIsInvis(false);

                    for(Player players : Bukkit.getOnlinePlayers()) {
                        for(int i = 0; i < QuizCupV4.playerList.size(); i++) {
                            Player p = Bukkit.getPlayer(QuizCupV4.playerList.get(i));
                            if(!players.hasPermission("quizcup.helper")) {
                                p.showPlayer(players);
                            }
                        }
                    }

                    sender.sendMessage(MessageCollection.getPrefix() + "Alle Spieler sind nun für andere Spieler §asichtbar§7!");
                } else sender.sendMessage("§cNutze: /invis <on | off>");
            } else sender.sendMessage("§cNutze: /invis <on | off>");
        } else sender.sendMessage(MessageCollection.getNoPerms());
        return false;
    }

    public static void setIsInvis(boolean isInvis) {
        InvisCommand.isInvis = isInvis;

        QuizCupV4.config.set("invis_enabled", isInvis);
        QuizCupV4.saveFile(QuizCupV4.configFile, QuizCupV4.config);
    }

    public static boolean getIsInvis() {
        return isInvis;
    }
}
