package de.zblubba.quizcupv4.fragesystem;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class WinnerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("quizcup.winner")) {
            if (args.length == 1) {
                switch (args[0]) {
                    case "green" -> addWinners(47, 59, -42, -30, "§agrün");
                    case "red" -> addWinners(-7, 5, -42, -30, "§crot");
                    case "blue" -> addWinners(-7, 5, -18, -6, "§bblau");
                    case "yellow" -> addWinners(47, 59, -18, -6, "§egelb");

                    default -> sender.sendMessage(MessageCollection.getPrefix() + "§cNutze: /winner <green | red | blue | yellow>");
                }

                for (Player players : Bukkit.getOnlinePlayers()) {
                    QuizCupV4.getInstance().scoreboard.setScoreboard(players);
                }
            } else sender.sendMessage(MessageCollection.getPrefix() + "§cNutze: /winner <green | red | blue | yellow>");
        } else sender.sendMessage(MessageCollection.getNoPerms());
        return false;
    }

    public static void addWinners(int xmin, int xmax, int zmin, int zmax, String color) {
        try {
            Connection connection = QuizCupV4.connection;
            String query = "update points set points = points + 1 where uuid = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            String prefix = MessageCollection.getPrefix();

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation().getX() >= xmin && player.getLocation().getX() <= xmax && player.getLocation().getZ() >= zmin && player.getLocation().getZ() <= zmax) {
                    player.sendMessage(prefix + "Du hast diese Frage §arichtig §7beantwortet!");
                    statement.setString(1, player.getUniqueId().toString());
                    statement.addBatch();

                }
                player.sendMessage(prefix + "Die Antwort " + color + " §7war richtig!");
            }

            statement.executeBatch();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
