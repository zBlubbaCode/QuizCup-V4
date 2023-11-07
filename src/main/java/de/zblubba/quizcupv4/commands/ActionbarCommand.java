package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActionbarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("quizcup.helper")) {
            if(args.length >= 1) {
                if(args[0].equalsIgnoreCase("stop")) {
                    Bukkit.getScheduler().cancelTask(QuizCupV4.actionBarTaskID);
                    sender.sendMessage(MessageCollection.getPrefix() + "§cActionbar gelöscht");
                    return false;
                }

                String message = "";
                for(int i = 0; i < args.length; i++) {
                    message = message + " " + args[i];
                }

                message = message.replace("%p%", MessageCollection.getPrefix());
                message = ChatColor.translateAlternateColorCodes('&', message);

                if(QuizCupV4.config.getBoolean("actionbar.enabled")) {
                    Bukkit.getScheduler().cancelTask(QuizCupV4.actionBarTaskID);

                    String finalMessage1 = message;
                    QuizCupV4.actionBarTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(QuizCupV4.getPlugin(QuizCupV4.class), () -> {
                        for(Player players : Bukkit.getOnlinePlayers()) {
                            String finalMessage = finalMessage1;
                            players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(finalMessage));
                        }
                    }, 0, 40);

                    sender.sendMessage(MessageCollection.getPrefix() + "Actionbar erfolgreich gesetzt!");
                } else sender.sendMessage(MessageCollection.getPrefix() + "§cActionbar ist ausgeschaltet!");



            } else sender.sendMessage(MessageCollection.getPrefix() + "§cNutze /actionbar <Nachricht | %p% = prefix von qc>");
        } else sender.sendMessage(MessageCollection.getNoPerms());
        return false;
    }
}