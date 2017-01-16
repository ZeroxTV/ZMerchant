package de.zeroxtv.zmerchant;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ZeroxTV
 */
public class Merchant {
    public static ArrayList<Merchant> merchants = new ArrayList<>();

    public static void loadMerchants() {
        File pathFile = new File("ZConomy/merchants/");
        if (!pathFile.exists()) {
            pathFile.mkdirs() ;
            return;
        }
        File[] listFiles = pathFile.listFiles();

        for (File file : listFiles) {
            if (file.isFile()) {
                loadMerchant(file);
            }
        }
    }

    public static void killAll() {
        for (Merchant merchant : merchants) {
            merchant.kill();
        }
    }

    public static Merchant isMerchant(Entity entity) {
        for (Merchant merchant : merchants) {
            if (merchant.getVillager().getUniqueId().equals(entity.getUniqueId())) {
                return merchant;
            }
        }
        return null;
    }

    private static void loadMerchant(File file) {
        try {
            YamlConfiguration config = new YamlConfiguration();
            config.load(file);
            merchants.add(new Merchant(UUID.fromString(file.getName().replace(".yml", ""))));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private Villager villager;
    private UUID uuid;
    private File configFile;
    private YamlConfiguration config;
    private ArrayList<MerchantItem> offers = new ArrayList<MerchantItem>();
    private MerchantGUI menu;

    public Merchant(Location location, String name) {
        villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setProfession(Villager.Profession.NITWIT);
        villager.setAI(false);
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);
        uuid = villager.getUniqueId();
        configFile = new File("ZConomy/merchants/" + uuid + ".yml");
        config = new YamlConfiguration();

        config.options().copyDefaults(true);
        config.addDefault("X", location.getX());
        config.addDefault("Y", location.getY());
        config.addDefault("Z", location.getZ());
        config.addDefault("world", location.getWorld().getName());
        config.addDefault("name", name);
        save();

        resetOffers();
        menu = new MerchantGUI(this);
        merchants.add(this);
    }

    public Merchant(UUID uuid) {
        villager = (Villager) getEntityByUniqueId(uuid);
        this.uuid = uuid;
        configFile = new File("ZConomy/merchants/" + uuid + ".yml");
        config = new YamlConfiguration();
        resetOffers();
        menu = new MerchantGUI(this);
        merchants.add(this);
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Villager getVillager() {
        return this.villager;
    }

    public void resetOffers() {
        for (int i = 0; i < 9; i++) {
            boolean found = false;
            while (!found) {
                MerchantItem item = MerchantItem.getRandomTrade();
                if (!offerExistent(item.getItem().getType())) {
                    found = true;
                    offers.add(item);
                }
            }
        }
    }

    private boolean offerExistent(Material material) {
        for (MerchantItem item : offers) {
            if (item.getItem().getType().equals(material)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<MerchantItem> getOffers() {
        return offers;
    }

    public void openMenu(Player player) {
        //menu.open(player);
    }

    private void kill() {
        this.villager.setHealth(0);
    }

    public Entity getEntityByUniqueId(UUID uniqueId) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uniqueId))
                    return entity;
            }
        }

        return null;
    }

}
