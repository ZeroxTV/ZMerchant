package de.zeroxtv.zmerchant.Util.MenuUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by ZeroxTV
 */
public class InventoryListener implements Listener {
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getViewers().get(0);
        if(event.getClickedInventory().getName().equals(ItemMenu.getCurrentlyOpenMenuName(player.getUniqueId()))) {
            //for (MenuItem item : ItemMenu.getCurrentlyOpenMenu(player.getUniqueId()))
        }
    }
}
