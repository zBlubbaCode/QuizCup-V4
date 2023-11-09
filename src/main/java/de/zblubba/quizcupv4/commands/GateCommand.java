package de.zblubba.quizcupv4.commands;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("quizcup.gates")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("1")) {
                    replaceGate(Material.RED_STAINED_GLASS, 21, 31, -29, -19, 63);
                    replaceGate(Material.RED_STAINED_GLASS, 21, 31, -29, -19, 62);

                    sender.sendMessage(QuizCupRemastered.getPrefix + "Gate §c#1 §7wurde aktualisiert!");
                } else if(args[0].equalsIgnoreCase("2")) {
                    replaceGate(Material.RED_STAINED_GLASS, -7, 5, -42, -30, 65);
                    replaceGate(Material.BLUE_STAINED_GLASS, -7, 5, -18, -6, 65);
                    replaceGate(Material.GREEN_STAINED_GLASS, 47, 59, -42, -30, 65);
                    replaceGate(Material.YELLOW_STAINED_GLASS, 47, 59, -18, -6, 65);

                    sender.sendMessage(QuizCupRemastered.getPrefix + "Gate §a#2 §7wurde aktualisiert!");
                }
            } else sender.sendMessage(QuizCupRemastered.getPrefix + "§cAlias: /gate <1 | 2 | 3>");
        } else sender.sendMessage(MessageCollection.getNoPerms());
        return false;
    }

    public void replaceGate(Material type, int xmin, int xmax, int zmin, int zmax, int y) {
        //for(int x = xmin; x < xmax; x++) {
            //for(int z = zmin; z < zmax; z++) {
                World world = Bukkit.getWorlds().get(0);
                //if(world.getBlockAt(x, y, z).getType() == type) {
                    //world.getBlockAt(x, y, z).setType(Material.AIR);
                //} else if(world.getBlockAt(x, y, z).getType() == Material.AIR) {
                    //world.getBlockAt(x, y, z).setType(type);
                //}
            //}
        //}
    }

  replaceMiddleGates(Material type) {
    //Change gate area
    for(int x = -200; x < 200; x++) {
      for(int y = 800; y < 1200; y++) {
        for(int z = 30; z < 40; z++) {
          World world = Bukkit.getWorlds().get(0);
          Block blockAbove = world.getBlockAt(x, y+1, z);
          switch(world.getBlockAt(x, y, z).getMaterial) {
            case "RED_STAINED_GLASS" -> blockAbove.setType(RED_STAINED_GLASS);
            case "BLUE_STAINED_GLASS" -> blockAbove.setType(BLUE_STAINED_GLASS);
            case "YELLOW_STAINED_GLASS" -> blockAbove.setType(YELLOW_STAINED_GLASS);
            case "LIME_STAINED_GLASS" -> blockAbove.setType(LIME_STAINED_GLASS);
          }
        }
      }
    }
  }

  
}
