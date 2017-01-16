package de.zeroxtv.zmerchant.Commands;

import de.zeroxtv.zmerchant.Merchant;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

/**
 * Created by ZeroxTV
 */
public class CMerchant extends BukkitCommand {
    public CMerchant(String name) {
        super(name);
        super.setDescription("Erschaffe einen Händler");
        super.setPermission("ZM.create");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (args.length != 1) return false;
            Player p = (Player) sender;
            new Merchant(p.getLocation(), args[0]);

        } else {
            sender.sendMessage("Dieser Befehl ist nur für Spieler.");
        }
        return true;
    }
}
