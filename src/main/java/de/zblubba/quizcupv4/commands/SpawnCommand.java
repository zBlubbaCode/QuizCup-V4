package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p) {
            Configuration config = QuizCupV4.config;
            if(!config.getList("finale_list").contains(p.getName())) {
                p.teleport(new Location(Bukkit.getWorld(config.getString("spawn.world")), config.getDouble("spawn.x"), config.getDouble("spawn.y"), config.getDouble("spawn.z"), (float) config.getDouble("spawn.yaw"), (float) config.getDouble("config.pitch")));
                p.sendMessage(MessageCollection.getPrefix() + "Du wurdest zum §aSpawn §7teleportiert!");
            } else p.sendMessage(MessageCollection.getPrefix() + "§cDu kannst dich im (Halb-)Finale nicht zum Spawn teleportieren!");
        }
        return false;
    }
}
