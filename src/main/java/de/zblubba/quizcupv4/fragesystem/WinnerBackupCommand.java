package de.zblubba.quizcupv4.fragesystem;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class WinnerBackupCommand implements CommandExecutor {

    static Plugin plugin = QuizCupV4.getPlugin(QuizCupV4.class);

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


            String prefix = MessageCollection.getPrefix();

            ArrayList<Player> playersInRegion = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation().getX() >= xmin && player.getLocation().getX() <= xmax && player.getLocation().getZ() >= zmin && player.getLocation().getZ() <= zmax) {
                    playersInRegion.add(player);
                }
                player.sendMessage(prefix + "Die Antwort " + color + " §7war richtig!");
            }

            AtomicInteger i = new AtomicInteger();

            int taskid = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
                try {
                    PreparedStatement statement = connection.prepareStatement(query);
                    Player p = Bukkit.getPlayer(playersInRegion.get(i.get()).getUniqueId());
                    assert p != null;
                    p.sendMessage(prefix + "Du hast diese Frage §arichtig §7beantwortet!");
                    statement.setString(1, p.getUniqueId().toString());
                    statement.addBatch();
                    statement.executeBatch();
                    statement.close();
                    i.getAndIncrement();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }, 50, 10);

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                Bukkit.getScheduler().cancelTask(taskid);
            }, 50 + playersInRegion.toArray().length);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
