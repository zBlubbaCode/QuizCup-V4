package de.zblubba.quizcupv4.fragesystem;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.*;

public class PointsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("quizcup.points")) {
            if(args.length >= 2) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if(target == null) {
                    sender.sendMessage(MessageCollection.getPrefix() + "§cungültiger Spieler!");
                    return false;
                }
                if(args[0].equalsIgnoreCase("get")) {
                    sender.sendMessage(MessageCollection.getPrefix() + "Der Spieler §a" + target.getName() + " §7hat §c" + getPoints(target) + " §7Punkte!");
                } else if(args[0].equalsIgnoreCase("add")) {
                    int points = getPoints(target);
                    int newValue = Integer.parseInt(args[2]);

                    checkRegistered(target);

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(MessageCollection.getMySQLHost() + MessageCollection.getMySQLDatabase(), MessageCollection.getMySQLUser(), MessageCollection.getMySQLPassword());

                        String query = "update points set points = ? where uuid = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, points + newValue);
                        statement.setString(2, target.getUniqueId().toString());

                        statement.execute();
                        statement.close();
                        connection.close();

                    } catch(Exception e){e.printStackTrace();}

                    sender.sendMessage(MessageCollection.getPrefix() + "Die Punkte des Spielers §a" + target.getName() + " §7wurden auf §c" + Integer.parseInt(String.valueOf(newValue + points)) + " §7gesetzt!");
                } else if(args[0].equalsIgnoreCase("set")) {
                    int newValue = Integer.parseInt(args[2]);
                    checkRegistered(target);

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(MessageCollection.getMySQLHost() + MessageCollection.getMySQLDatabase(), MessageCollection.getMySQLUser(), MessageCollection.getMySQLPassword());

                        String query = "update points set points = ? where uuid = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, newValue);
                        statement.setString(2, target.getUniqueId().toString());

                        statement.execute();
                        statement.close();
                        connection.close();

                    } catch(Exception e){e.printStackTrace();}

                    sender.sendMessage(MessageCollection.getPrefix() + "Die Punkte des Spielers §a" + target.getName() + " §7wurden auf §c" + newValue + " §7gesetzt!");
                } else sender.sendMessage(MessageCollection.getPrefix() + "§cNutze: /points <get | set | add> <name>");
                QuizCupV4.getInstance().scoreboard.setScoreboard(Bukkit.getPlayer(target.getUniqueId()));
                QuizCupV4.getInstance().scoreboard.setTab(Bukkit.getPlayer(target.getUniqueId()));
            } else sender.sendMessage(MessageCollection.getPrefix() + "§cNutze: /points <get | set | add> <name>");
        } else sender.sendMessage(MessageCollection.getNoPerms());
        return false;

    }

    public static int getPoints(OfflinePlayer player) {
        checkRegistered(player);

        int points = 0;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(MessageCollection.getMySQLHost() + MessageCollection.getMySQLDatabase(), MessageCollection.getMySQLUser(), MessageCollection.getMySQLPassword());

            Statement statement = connection.createStatement();
            String query = "select * from points where uuid = \"" + player.getUniqueId() + "\";";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                points = resultSet.getInt("points");
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }

    public static void checkRegistered(OfflinePlayer p) {
        boolean registered = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(MessageCollection.getMySQLHost() + MessageCollection.getMySQLDatabase(), MessageCollection.getMySQLUser(), MessageCollection.getMySQLPassword());

            Statement statement = connection.createStatement();
            String query = "select * from points where uuid = \"" + p.getUniqueId() + "\";";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) registered = true;

            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!registered) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(MessageCollection.getMySQLHost() + MessageCollection.getMySQLDatabase(), MessageCollection.getMySQLUser(), MessageCollection.getMySQLPassword());

                String query = "INSERT INTO points(name, uuid, points) VALUES (?, ?, ?);";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, p.getName());
                statement.setString(2, p.getUniqueId().toString());
                statement.setInt(3, 0);

                statement.execute();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}