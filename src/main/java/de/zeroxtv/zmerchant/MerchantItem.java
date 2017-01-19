package de.zeroxtv.zmerchant;

import de.zeroxtv.zconomy.ZObjects.ZItem;
import de.zeroxtv.zcore.OtherUtil.NumberUtils;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ZeroxTV
 */
public class MerchantItem {
    private Double value;
    private ItemStack item;

    public static MerchantItem getRandomOffer() {
        ArrayList<ZItem> zItems = ZItem.getItems();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            ZItem zItem = zItems.get(random.nextInt(zItems.size()));
            if (!zItem.hasOffer()) continue;
            Double value = NumberUtils.parseDouble(zItem.offerValueMin + ((zItem.offerValueMax - zItem.offerValueMin) * Math.random()), 1);
            return new MerchantItem(value, zItem.getItem());
        }
        return null;
    }

    public static MerchantItem getRandomRequest() {
        ArrayList<ZItem> zItems = ZItem.getItems();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            ZItem zItem = zItems.get(random.nextInt(zItems.size()));
            if (!zItem.hasRequest()) continue;
            Double value = NumberUtils.parseDouble(zItem.requestValueMin + ((zItem.requestValueMax - zItem.requestValueMin) * Math.random()), 1);
            return new MerchantItem(value, zItem.getItem());
        }
        return null;
    }

    private MerchantItem(Double value, ItemStack item) {
        this.value = value;
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

    public Double getValue() {
        return value;
    }

    public MerchantItem clone() {
        return new MerchantItem(value, item);
    }
}
