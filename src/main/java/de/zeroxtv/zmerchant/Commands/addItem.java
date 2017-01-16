package de.zeroxtv.zmerchant.Commands;

import de.zeroxtv.zmerchant.ShopItem;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

/**
 * Created by ZeroxTV
 */
public class addItem extends BukkitCommand {
    public addItem(String name) {
        super(name);
        super.setDescription("Füge ein Item hinzu");
        super.setPermission("ZM.create");
        super.setUsage("/additem <MinValue> <MaxValue> <Probability>");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            ShopItem.createNewItem(p.getInventory().getItemInMainHand(), Double.valueOf(args[0]), Double.valueOf(args[1]), Integer.valueOf(args[2]));
            return true;
        }
        return true;

    }
}
