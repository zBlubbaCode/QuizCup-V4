package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CloseChatCommand implements CommandExecutor {

    public static String isChatClosed = "true";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("quizcup.chat.close")) {

            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("close")) {
                    setIsChatClosed("true");
                    for(Player players : Bukkit.getOnlinePlayers()) {players.sendMessage(MessageCollection.getCloseChat());}
                } else if(args[0].equalsIgnoreCase("open")){
                    setIsChatClosed("false");
                    for(Player players : Bukkit.getOnlinePlayers()) {players.sendMessage(MessageCollection.getOpenChat());}
                } else if(args[0].equalsIgnoreCase("final")) {
                    setIsChatClosed("final");
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        players.sendMessage(MessageCollection.getCloseChat());
                        if(QuizCupV4.config.getList("finale_list").contains(players.getName()))
                            players.sendMessage(MessageCollection.getPrefix() + "§7Für dich ist nun der Chat §aerlaubt§7!");
                    }
                } else if(args[0].equalsIgnoreCase("toggle")){
                    if(isChatClosed.equals("true")) {
                        setIsChatClosed("false");
                        for(Player players : Bukkit.getOnlinePlayers()) {players.sendMessage(MessageCollection.getOpenChat());}
                    } else {
                        setIsChatClosed("true");
                        for(Player players : Bukkit.getOnlinePlayers()) {players.sendMessage(MessageCollection.getCloseChat());}
                    }
                } else sender.sendMessage(MessageCollection.getPrefix() + "Nutze §c/closechat <close | open | final>");
            } else sender.sendMessage(MessageCollection.getPrefix() + "Nutze §c/closechat <close | open | final>");

        } else {
            sender.sendMessage(MessageCollection.getNoPerms());
        }

        return false;
    }

    public static void setIsChatClosed(String isChatClosed) {CloseChatCommand.isChatClosed = isChatClosed;}
}