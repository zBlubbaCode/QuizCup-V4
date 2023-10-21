package de.zblubba.quizcupv4.fragesystem;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.*;

public class StressTestCommand implements CommandExecutor {

    Plugin plugin = QuizCupV4.getPlugin(QuizCupV4.class);

    private int taskid;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("quizcup.admin")) {
            if(args.length == 3) {
                int duration = Integer.parseInt(args[0]) * 20;
                int intervall = Integer.parseInt(args[1]);

                taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

                    Player target = Bukkit.getPlayer(args[2]);
                    int points = getPoints(target);

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection connection = DriverManager.getConnection(MessageCollection.getMySQLHost() + MessageCollection.getMySQLDatabase(), MessageCollection.getMySQLUser(), MessageCollection.getMySQLPassword());

                        String query = "update points set points = ? where uuid = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, points + 1);
                        statement.setString(2, target.getUniqueId().toString());

                        statement.execute();
                        statement.close();
                        connection.close();

                    } catch(Exception e){e.printStackTrace();}
                }, 0, intervall);

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    Bukkit.getScheduler().cancelTask(taskid);
                }, duration);
            }
        }
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

        if (!registered) {
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
