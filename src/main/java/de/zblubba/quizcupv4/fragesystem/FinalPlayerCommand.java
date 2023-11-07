package de.zblubba.quizcupv4.fragesystem;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FinalPlayerCommand implements CommandExecutor {

    Configuration config = QuizCupV4.config;

    String prefix = MessageCollection.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("quizcup.finale")) {
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if(target == null) { //prüft, ob der eingegebene Spieler gültig ist
                    sender.sendMessage(prefix + "§cBitte gebe einen Spieler an, der online ist!");
                    return false;
                }
                ArrayList<String> finalPlayerList = (ArrayList<String>) config.getList("finale_list");
                if(finalPlayerList == null) {sender.sendMessage("§cError bei der Liste"); return false;}

                if(args[0].equalsIgnoreCase("add")) {
                    //hinzufüge den Spieler auf die Liste und lade die aktualisierte Liste anschließend wieder hoch
                    finalPlayerList.add(target.getName());
                    target.sendMessage(prefix+ "Herzlichen Glückwunsch! Du bist nun im §7(§cHalb-§7)§cFinale§7!");
                    config.set("finale_list", finalPlayerList);

                    sender.sendMessage(prefix + "§7Spieler §c" + target.getName() + " §7wurde erfolgreich zur FinalListe hinzugefügt!");
                } else if(args[0].equalsIgnoreCase("remove")) {
                    //entferne den Spieler von der Liste und lade die aktualisierte Liste anschließend wieder hoch
                    finalPlayerList.remove(target.getName());
                    config.set("finale_list", finalPlayerList);

                    sender.sendMessage(prefix + "§7Spieler §c" + target.getName() + " §7wurde erfolgreich von der FinalListe entfernt!");
                } else sender.sendMessage(prefix + "§cNutze: /finalplayer <add | remove | reset> <name>");

                // speichere die Datei
                QuizCupV4.saveFile(QuizCupV4.configFile, QuizCupV4.config);
            }
            if(args[0].equalsIgnoreCase("reset")) {
                ArrayList<String> finalList = new ArrayList<>();
                config.set("finale_list", finalList);
                sender.sendMessage(prefix + "§7Die FinalPlayer-Liste wurde erfolgreich §azurückgesetzt!");
                QuizCupV4.saveFile(QuizCupV4.configFile, QuizCupV4.config);
            }
        } else sender.sendMessage(MessageCollection.getNoPerms());
        return false;
    }
}
