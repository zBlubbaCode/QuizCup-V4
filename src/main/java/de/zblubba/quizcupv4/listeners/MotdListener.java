package de.zblubba.quizcupv4.listeners;

import de.zblubba.quizcupv4.QuizCupV4;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String motdMessageFirst = QuizCupV4.config.getString("motd.firstLine"); motdMessageFirst = ChatColor.translateAlternateColorCodes('&', motdMessageFirst);
        String motdMessageSecond = QuizCupV4.config.getString("motd.secondLine"); motdMessageSecond = ChatColor.translateAlternateColorCodes('&', motdMessageSecond);

        event.setMotd(motdMessageFirst + "\n" + motdMessageSecond);
    }
}