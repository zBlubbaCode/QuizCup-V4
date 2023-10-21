package de.zblubba.quizcupv4.listeners;

import de.zblubba.quizcupv4.QuizCupV4;
import de.zblubba.quizcupv4.util.MessageCollection;
import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class JoinQuitListener implements Listener {

    public static int taskid;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        p.sendMessage(MessageCollection.getPersonalJoinMessage(p));

        event.setJoinMessage(MessageCollection.getJoinMessage(p));
        if(!p.hasPermission("quizcup.helper")) {
            QuizCupV4.playerList.add(p.getName());
        }

        // CLOSE SERVER CHECK??
        if(p.hasPermission("quizcup.admin")) {
            ItemStack boots = new ItemStack(Material.LEATHER_BOOTS); LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta(); meta.setColor(Color.fromRGB(255, 0 ,0));meta.addEnchant(Enchantment.PROTECTION_FALL, 100, true); meta.addItemFlags(ItemFlag.HIDE_ENCHANTS); meta.setDisplayName("§cAdmin §8| §c" + p.getName());
            boots.setItemMeta(meta);
            p.getInventory().setItem(36, boots);
        }

        QuizCupV4.checkInvis();

        if(QuizCupV4.config.getBoolean("join.playsound")) {
            p.playSound(p.getLocation(), Sound.valueOf(QuizCupV4.config.getString("join.sound")), 255, 1);
        }

        Configuration config = QuizCupV4.config;
        if(config.getBoolean("join.teleport_to_spawn")) {
            p.teleport(new Location(Bukkit.getWorld(config.getString("spawn.world")), config.getDouble("spawn.x"), config.getDouble("spawn.y"), config.getDouble("spawn.z"), (float) config.getDouble("spawn.yaw"), (float) config.getDouble("config.pitch")));
        }

        QuizCupV4.getInstance().scoreboard.setScoreboard(p);
        QuizCupV4.getInstance().scoreboard.setTab(p);

        for(Player players : Bukkit.getOnlinePlayers()) {
            QuizCupV4.getInstance().scoreboard.updateTab(players);
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        event.setQuitMessage(MessageCollection.getQuitMessage(p));
        if(!p.hasPermission("quizcup.helper")) QuizCupV4.playerList.remove(p.getName());
    }


}
