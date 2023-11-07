package de.zblubba.quizcupv4.fragesystem;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class StartFrageCommand implements CommandExecutor {

    public static int currentQuestionID;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("quizcup.frage.start")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("next")) {
                    currentQuestionID++;
                    startQuestion(currentQuestionID);
                } else {
                    currentQuestionID = Integer.parseInt(args[0]);
                    startQuestion(currentQuestionID);
                }
            } else sender.sendMessage("§cNutze: /startfrage <fragenID | next>");
        } else sender.sendMessage(MessageCollection.getNoPerms());

        return false;
    }

    public static void startQuestion(int questionID) {
        try {
            String frage = "", antwort1 = "", antwort2 = "", antwort3 = "", antwort4 = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(MessageCollection.getMySQLHost() + MessageCollection.getMySQLDatabase(), MessageCollection.getMySQLUser(), MessageCollection.getMySQLPassword());

            Statement statement = connection.createStatement();
            String query = "select * from fragen where id = " + questionID;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                frage = resultSet.getString("frage");
                antwort1 = resultSet.getString("antwortA");
                antwort2 = resultSet.getString("antwortB");
                antwort3 = resultSet.getString("antwortC");
                antwort4 = resultSet.getString("antwortD");
            }

            resultSet.close();
            statement.close();
            connection.close();

            for(Player players : Bukkit.getOnlinePlayers()) {
                players.sendTitle("§aFrage " + questionID, "", 1, 60, 1);

                players.sendMessage("§f======== §7Frage §a" + questionID + " §f========");
                players.sendMessage("§6" + frage);
                if(!antwort1.isEmpty() && !antwort1.isBlank()) players.sendMessage("§7[§cA§7]§a " + antwort1);
                if(!antwort2.isEmpty() && !antwort2.isBlank()) players.sendMessage("§7[§bB§7]§a " + antwort2);
                if(!antwort3.isEmpty() && !antwort3.isBlank()) players.sendMessage("§7[§aC§7]§a " + antwort3);
                if(!antwort4.isEmpty() && !antwort4.isBlank()) players.sendMessage("§7[§eD§7]§a " + antwort4);
                players.sendMessage("§f======== §7Frage §a" + questionID + " §f========");
            }

            if(QuizCupV4.config.getBoolean("actionbar.enabled")) {
                Bukkit.getScheduler().cancelTask(QuizCupV4.actionBarTaskID);

                QuizCupV4.actionBarTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(QuizCupV4.getPlugin(QuizCupV4.class), () -> {
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eQuizCup §8| §7Frage §c" + questionID));
                    }
                }, 0, 40);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static int getCurrentQuestionID() {return currentQuestionID;}
    public static void setCurrentQuestionID(int currentQuestionID) {StartFrageCommand.currentQuestionID = currentQuestionID;}
}
