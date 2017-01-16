package de.zeroxtv.zmerchant;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ZeroxTV
 */
public class ShopItem {
    private static ArrayList<ShopItem> items = new ArrayList<ShopItem>();
    private static ArrayList<ShopItem> itemProbabilities = new ArrayList<ShopItem>();
    private static Random random = new Random();

    public static void loadItems() {
        File pathFile = new File("ZConomy/merchants/offers");
        if (!pathFile.exists()) {
            pathFile.mkdirs() ;
            return;
        }
        File[] listFiles = pathFile.listFiles();
        for (File file : listFiles) {
            if (file.isFile()) {
                loadItem(file);
            }
        }
    }

    public static void createNewItem(ItemStack item, Double valueMin, Double valueMax, int probability) {
        try {
            File Item = new File("ZConomy/merchants/offers/"+ item.getType().name() +".yml");
            Item.createNewFile();
            YamlConfiguration config = new YamlConfiguration();
            config.load(Item);
            config.options().copyDefaults(true);
            config.addDefault("material", item.getType().name());
            config.addDefault("valueMin", valueMin);
            config.addDefault("valueMax", valueMax);
            config.addDefault("probability", probability);
            config.save(Item);
            loadItem(Item);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static ShopItem random() {
        return itemProbabilities.get(random.nextInt(itemProbabilities.size()));
    }

    private ItemStack item;
    private Double valueMin;
    private Double valueMax;

    public ShopItem(ItemStack item, Double valueMin, Double valueMax) {
        this.item = item;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
    }

    public Double getValueMin() {
        return valueMin;
    }

    public Double getValueMax() {
        return valueMax;
    }

    public ItemStack getItem() {
        return item;
    }

    private static void loadItem(File file) {
        try {
            YamlConfiguration config = new YamlConfiguration();
            config.load(file);
            Material material = Material.getMaterial(config.getString("material"));
            ItemStack itemStack = new ItemStack(material, 1);
            ShopItem item = new ShopItem(itemStack, config.getDouble("valueMin"), config.getDouble("valueMax"));
            items.add(item);
            int probable = (int) Math.ceil(config.getInt("probability")/0.9);
            for (int i = 0; i < probable; i++) {
                itemProbabilities.add(item);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
