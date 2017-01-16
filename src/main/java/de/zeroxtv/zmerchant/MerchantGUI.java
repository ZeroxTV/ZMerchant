package de.zeroxtv.zmerchant;

import de.zeroxtv.zconomy.Accounts.PlayerAccount;
import de.zeroxtv.zmerchant.Util.MenuUtil.ItemMenu;
import de.zeroxtv.zmerchant.Util.MenuUtil.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ZeroxTV
 */
public class MerchantGUI {
    private ItemMenu menu;
    private Merchant merchant;

    public MerchantGUI(Merchant merchant) {
        this.merchant = merchant;
        ItemMenu selling = new ItemMenu(9, "Heutige Angebote");

        ArrayList<MenuItem> sells = new ArrayList<>();
        for (MerchantItem item : merchant.getOffers()) {

            PlayerCallable callable = new PlayerCallable() {
                @Override
                public Object call(Player player) throws Exception {
                    if (PlayerAccount.getPlayerAccount(player).withdraw(item.getValue())) {
                        player.getInventory().addItem(item.getItem());
                    }
                    return null;
                }
            };

            ItemStack itemStack = item.getItem();
            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(Arrays.asList("Kosten: " + item.getValue() + "Münzen"));
            itemStack.setItemMeta(meta);
            MenuItem menuItem = new MenuItem(itemStack, callable, "Heutige Angebote");
            sells.add(menuItem);
        }

    }
}
