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
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ZeroxTV
 */
public class Merchant {
    private static ArrayList<Merchant> merchants = new ArrayList<>();

    static void loadMerchants() {
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

    static Merchant isMerchant(Entity entity) {
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
    private ArrayList<MerchantItem> offers = new ArrayList<>();
    private ArrayList<MerchantItem> requests = new ArrayList<>();
    private MerchantGUI menu;

    public Merchant(Location location, String name) {
        villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setProfession(Villager.Profession.NITWIT);
        villager.setAI(false);
        villager.setCustomName(name);
        villager.setInvulnerable(true);
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
        resetRequests();
        menu = new MerchantGUI(this);
        merchants.add(this);
    }

    private Merchant(UUID uuid) {
        configFile = new File("ZConomy/merchants/" + uuid + ".yml");
        config = new YamlConfiguration();
        villager = (Villager) getEntityByUniqueId(uuid);
        if (villager == null) {
            villager = (Villager) Bukkit.getWorld(config.getString("world")).spawnEntity(new Location(Bukkit.getWorld(config.getString("world")),config.getDouble("X"), config.getDouble("Y"), config.getDouble("Z")), EntityType.VILLAGER);
            villager.setProfession(Villager.Profession.NITWIT);
            villager.setAI(false);
            villager.setCustomName(config.getString("name"));
            villager.setInvulnerable(true);
            villager.setCustomNameVisible(true);
        }
        this.uuid = uuid;
        configFile = new File("ZConomy/merchants/" + uuid + ".yml");
        config = new YamlConfiguration();
        resetOffers();
        resetRequests();
        menu = new MerchantGUI(this);
        merchants.add(this);
    }

    private void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Villager getVillager() {
        return this.villager;
    }

    private void resetOffers() {
        for (int i = 0; i < 9; i++) {
            boolean found = false;
            while (!found) {
                MerchantItem item = MerchantItem.getRandomOffer();
                if (item == null) System.out.println("item null");
                if (!tradeExistent(item.getItem(), offers)) {
                    found = true;
                    offers.add(item);
                }
            }
        }
    }

    private void resetRequests() {
        for (int i = 0; i < 3; i++) {
            boolean found = false;
            while (!found) {
                MerchantItem item = MerchantItem.getRandomRequest();
                if (!tradeExistent(item.getItem(), requests)) {
                    found = true;
                    requests.add(item);
                }
            }
        }
    }

    private boolean tradeExistent(ItemStack itemStack, ArrayList<MerchantItem> array) {
        if (array == null) System.out.println("array null");
        for (MerchantItem item : array) {
            if (item == null) System.out.println("item null");
            if (item.getItem() == null) System.out.println("item item null");
            if (itemStack == null) System.out.println("itemstack null");
            if (item.getItem().getType().equals(itemStack.getType()) &&
                    item.getItem().getDurability() == itemStack.getDurability() &&
                    item.getItem().getData().equals(itemStack.getData())) {
                return true;
            }
        }
        return false;
    }

    ArrayList<MerchantItem> getOffers() {
        return offers;
    }

    ArrayList<MerchantItem> getRequests() {
        return requests;
    }

    void openMenu(Player player) {
        menu.open(player);
    }

    private void kill() {
        this.villager.setHealth(0);
    }

    private Entity getEntityByUniqueId(UUID uniqueId) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uniqueId))
                    return entity;
            }
        }

        return null;
    }

}
