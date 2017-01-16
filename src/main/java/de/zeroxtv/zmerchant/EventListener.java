package de.zeroxtv.zmerchant;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
}
