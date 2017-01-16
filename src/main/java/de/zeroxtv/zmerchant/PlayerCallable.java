package de.zeroxtv.zmerchant;

import org.bukkit.entity.Player;

/**
 * Created by ZeroxTV
 */
public interface PlayerCallable {
    public Object call(Player player) throws Exception;
}
