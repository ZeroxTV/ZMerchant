package de.zeroxtv.zmerchant;

import org.bukkit.inventory.ItemStack;

/**
 * Created by ZeroxTV
 */
public class MerchantItem {
    private Double value;
    private ItemStack item;

    public static MerchantItem getRandomTrade() {
        return new MerchantItem(ShopItem.random());
    }

    public MerchantItem(Double value, ItemStack item) {
        this.value = value;
        this.item = item;
    }

    public MerchantItem(ShopItem shopItem) {
        this.item = shopItem.getItem();
        this.value = shopItem.getValueMin() + ((shopItem.getValueMax() - shopItem.getValueMin()) * Math.random());
    }

    public ItemStack getItem() {
        return item;
    }

    public Double getValue() {
        return value;
    }
}
