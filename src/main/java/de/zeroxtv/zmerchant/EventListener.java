package de.zeroxtv.zmerchant;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Created by ZeroxTV
 */
public class EventListener implements Listener {

    @EventHandler
    public void onMerchantClick(PlayerInteractEntityEvent event) {
        Merchant merchant = Merchant.isMerchant(event.getRightClicked());
        if (merchant != null) {
            merchant.openMenu(event.getPlayer());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (Merchant.isMerchant(event.getEntity()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventory(InventoryOpenEvent event) {
        if (event.getInventory().getType().equals(InventoryType.MERCHANT)) {
            event.setCancelled(true);
        }
    }
}
