package de.zblubba.quizcupv4.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InteractionListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action action = event.getAction();

        if(action == Action.RIGHT_CLICK_BLOCK && !p.hasPermission("quizcup.helper")) {
            if(event.getClickedBlock().getType() == Material.LEVER) event.setCancelled(true);
        }

        if(p.hasPermission("quizcup.tools")) {
            if (action != Action.RIGHT_CLICK_AIR) return;
            ItemStack item = p.getItemInHand();
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();

                switch(meta.getLocalizedName()) {
                    case "gate1item" -> p.performCommand("gate 1");
                    case "gate2item" -> p.performCommand("gate 2");
                    case "gate3item" -> p.performCommand("gate 3");
                    case "nextquestionItem" -> p.performCommand("startfrage next");

                    // SERVER STATE ITEM??

                    case "zumstartitem" -> p.performCommand("zumspawn");

                }

            }
        }

    }
}
