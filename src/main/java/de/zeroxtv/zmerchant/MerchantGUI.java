package de.zeroxtv.zmerchant;

import de.zeroxtv.zconomy.Accounts.PlayerAccount;
import de.zeroxtv.zcore.MenuUtil.ItemMenu;
import de.zeroxtv.zcore.MenuUtil.MenuItem;
import de.zeroxtv.zcore.ZObjects.AdvancedCallable;
import org.bukkit.Material;
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
        ItemMenu buying = new ItemMenu(9, "Kannst du mir das verkaufen?");
        menu = new ItemMenu(9, "Wie kann ich Ihnen helfen?");

        //Offers
        ArrayList<MenuItem> sells = new ArrayList<>();
        for (MerchantItem item : merchant.getOffers()) {

            AdvancedCallable callable = playerP -> {
                Player player = (Player) playerP;
                if (PlayerAccount.getPlayerAccount(player).withdraw(item.getValue())) {
                    player.getInventory().addItem(item.getItem());
                }
                return null;
            };

            //Meta-Management
            ItemStack itemStack = item.getItem().clone();
            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(Arrays.asList("§eKosten: §c" + item.getValue() + " Münzen"));
            itemStack.setItemMeta(meta);

            MenuItem menuItem = new MenuItem(itemStack, callable, "Heutige Angebote");
            sells.add(menuItem);
        }
        selling.setItems(sells);

        //Requests
        ArrayList<MenuItem> buys = new ArrayList<>();
        for (MerchantItem item : merchant.getRequests()) {
            AdvancedCallable callable = playerP -> {
                Player player = (Player) playerP;
                if (player.getInventory().contains(item.getItem().getType())) {
                    ItemStack[] contents = player.getInventory().getContents();
                    int toRemove = 1;
                    for (int i = 0; i < contents.length; i++) {
                        ItemStack itemS = contents[i];

                        if (itemS == null || !itemS.getType().equals(item.getItem().getType())) {continue;}

                        if (itemS.getDurability() != item.getItem().getDurability()) {continue;}

                        int amountLeft = itemS.getAmount() - toRemove;

                        if (amountLeft < 0) {
                            itemS.setAmount(toRemove);
                            break;
                        }

                        if (amountLeft < 0) {
                            toRemove = Math.abs(amountLeft);
                            contents[i] = null;
                        }
                    }
                    PlayerAccount.getPlayerAccount(player).deposit(item.getValue());
                }
                return null;
            };

            //Meta-Management
            ItemStack itemStack = item.getItem().clone();
            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(Arrays.asList("§eWert: §a" + item.getValue() + " Münzen"));
            itemStack.setItemMeta(meta);

            MenuItem menuItem = new MenuItem(itemStack, callable, "Kannst du mir das verkaufen?");
            buys.add(menuItem);
        }
        buying.setItems(buys);

        //Main Menu
            //Buy
        ItemStack buy = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta buyMeta = buy.getItemMeta();
        buyMeta.setDisplayName("§r§aIch möchte etwas kaufen");
        buyMeta.setLore(Arrays.asList("§7Ich habe jeden Tag neue Angebot", "§7Es lohnt sich vorbeizuschauen"));
        buy.setItemMeta(buyMeta);

        MenuItem buyItem = new MenuItem(buy, selling, "Wie kann ich Ihnen helfen?");

            //Sell
        ItemStack sell = new ItemStack(Material.IRON_INGOT, 1);
        ItemMeta sellMeta = sell.getItemMeta();
        sellMeta.setDisplayName("§r§cIch möchte etwas verkaufen");
        sellMeta.setLore(Arrays.asList("§7Es gibt viele Sachen, die", "§7sich in anderen Regionen", "§7gut verkaufen lassen"));
        sell.setItemMeta(sellMeta);

        MenuItem sellItem = new MenuItem(sell, buying, "Wie kann ich Ihnen helfen?");

        ArrayList<MenuItem> main = new ArrayList<>();
        main.add(buyItem);
        main.add(sellItem);
        menu.setItems(main);

    }
    public void open(Player player) {
        menu.open(player);
    }
}
