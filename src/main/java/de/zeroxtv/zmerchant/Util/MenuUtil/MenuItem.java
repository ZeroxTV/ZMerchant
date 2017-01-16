package de.zeroxtv.zmerchant.Util.MenuUtil;

import de.zeroxtv.zmerchant.PlayerCallable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by ZeroxTV
 */
public class MenuItem {

    private ItemStack item;
    private boolean opensMenu;
    private PlayerCallable method;
    private ItemMenu menu;
    private String superMenuName;

    public MenuItem(ItemStack item, ItemMenu menu, String superMenuName) {
        this.item = item;
        this.menu = menu;
        this.opensMenu = true;
        this.superMenuName = superMenuName;
    }

    public MenuItem(ItemStack item, PlayerCallable method, String superMenuName) {
        this.item = item;
        this.method = method;
        this.opensMenu = false;
        this.superMenuName = superMenuName;
    }

    public void click(Player player) {
        if (this.opensMenu) {
            menu.open(player);
        } else {
            try {
                method.call(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ItemStack getItem() {
        return this.item;
    }

    public String getSuperMenuName() {
        return superMenuName;
    }
}
