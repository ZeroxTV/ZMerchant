package de.zeroxtv.zmerchant;

import de.zeroxtv.zmerchant.Commands.CMerchant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class ZMerchant extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerCommands();
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        this.getLogger().info(ChatColor.GREEN + "ZMerchant enabled!");
        Merchant.loadMerchants();

    }

    @Override
    public void onDisable() {
        this.getLogger().info(ChatColor.RED + "ZMerchant disabled!");
    }

    private void registerCommands() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register("merchant", new CMerchant("merchant"));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
