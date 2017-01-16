package de.zeroxtv.zmerchant;

import de.zeroxtv.zmerchant.Commands.CMerchant;
import de.zeroxtv.zmerchant.Commands.addItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class Zmerchant extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        ShopItem.loadItems();
        Merchant.loadMerchants();

    }

    @Override
    public void onDisable() {
    }

    private void registerCommands() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register("merchant", new CMerchant("merchant"));
            commandMap.register("additem", new addItem("additem"));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
