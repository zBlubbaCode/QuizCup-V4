package de.zblubba.quizcupv4.util;

import de.zblubba.quizcupv4.QuizCupV4;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class MessageCollection {

    static Configuration config = QuizCupV4.config;
    static Configuration mysql = QuizCupV4.mysqlConfig;

    // ---------------------------------------------------------------

    //MYSQL
    public static String getMySQLDatabase() {
        String database = mysql.getString("database");
        return database;
    }
    public static String getMySQLUser() {
        String user = mysql.getString("user");
        return user;
    }
    public static String getMySQLPassword() {
        String password = mysql.getString("password");
        return password;
    }
    public static String getMySQLHost() {
        String host = mysql.getString("host");
        return host;
    }

    // ---------------------------------------------------------------

    public static String getPrefix() {String prefix = config.getString("prefix"); prefix = replaceWithVariables(prefix);return prefix;}
    public static String getNoPerms() {String noPerms = config.getString("no_permission"); noPerms = replaceWithVariables(noPerms);return noPerms;}
    public static String mustbePlayer() {String mustbePlayer = config.getString("must_be_a_player"); mustbePlayer = replaceWithVariables(mustbePlayer);return mustbePlayer;}
    public static String commandNotExist() {String notExist = config.getString("command_does_not_exist");notExist = replaceWithVariables(notExist);return notExist;}

    // ---------------------------------------------------------------

    //chat
    public static String getCloseChat() {String close_chat = config.getString("chat.close_chat"); close_chat = replaceWithVariables(close_chat);return close_chat;}
    public static String getOpenChat() {String openChat = config.getString("chat.open_chat"); openChat = replaceWithVariables(openChat);return openChat;}
    public static String getChatClosed() {String chatClosed = config.getString("chat.chat_is_closed"); chatClosed = replaceWithVariables(chatClosed); return chatClosed;}

    // ---------------------------------------------------------------

    public static String getPrefixOfPlayer(Player p) {
        if(p.hasPermission("quizcup.admin")) {
            return getAdminPrefix(p);
        } else if(p.hasPermission("quizcup.helper")) {
            return getHelperPrefix(p);
        } else return getPlayerPrefix(p);
    }

    //prefixes
    public static String getAdminPrefix(Player p) {
        String adminPrefix = config.getString("prefixes.admin");
        adminPrefix = replaceWithVariables(adminPrefix);
        adminPrefix = adminPrefix.replace("{player}", p.getName());
        return adminPrefix;
    }

    public static String getHelperPrefix(Player p) {
        String helperPrefix = config.getString("prefixes.helper");
        helperPrefix = replaceWithVariables(helperPrefix);
        helperPrefix = helperPrefix.replace("{player}", p.getName());
        return helperPrefix;
    }

    public static String getPlayerPrefix(Player p) {
        String playerPrefix = config.getString("prefixes.player");
        playerPrefix = replaceWithVariables(playerPrefix);
        playerPrefix = playerPrefix.replace("{player}", p.getName());
        return playerPrefix;
    }

    // ---------------------------------------------------------------

    //join | quit
    public static String getJoinMessage(Player p) {
        String joinFormat = config.getString("join.message_format");
        joinFormat = replaceWithVariables(joinFormat);
        joinFormat = joinFormat.replace("{player}", getPrefixOfPlayer(p));
        return joinFormat;
    }

    public static String getPersonalJoinMessage(Player p) {
        String joinMessage = config.getString("join.personal_message");
        joinMessage = replaceWithVariables(joinMessage);
        joinMessage = joinMessage.replace("{player}", getPrefixOfPlayer(p));
        return joinMessage;
    }

    public static String getQuitMessage(Player p) {
        String quitFormat = config.getString("quit.message_format");
        quitFormat = replaceWithVariables(quitFormat);
        quitFormat = quitFormat.replace("{player}", getPrefixOfPlayer(p));
        return quitFormat;
    }

    public static String actionbarMessage(Player p) {String actionbarMessage = config.getString("actionbar.message"); actionbarMessage = replaceWithVariables(actionbarMessage); actionbarMessage = actionbarMessage.replace("{player}", p.getName()); return actionbarMessage;}


    // ---------------------------------------------------------------

    public static String replaceWithVariables(String input) {
        if(input == null) {
            QuizCupV4.getInstance().getLogger().info("ERROR | Fail on message-send.");
            return "§cERROR";
        }
        String prefix = config.getString("prefix"); prefix = prefix.replace('&', '§');
        int onlinePlayersInt = Bukkit.getServer().getOnlinePlayers().size();String onlinePlayers = String.valueOf(onlinePlayersInt);
        int maxPlayersInt = Bukkit.getMaxPlayers();String maxPlayers = String.valueOf(maxPlayersInt);

        input = input.replace('&', '§');
        input = input.replace("{prefix}", prefix);
        input = input.replace("%onlineplayers%", onlinePlayers);
        input = input.replace("%maxplayers%", maxPlayers);
        input = input.replace("%n%", "\n");

        return input;
    }

}