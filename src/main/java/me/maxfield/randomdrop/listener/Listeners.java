package me.maxfield.randomdrop.listener;


import me.maxfield.randomdrop.Randomdrop;
import me.maxfield.randomdrop.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;


public class Listeners implements Listener {
    @EventHandler
    public void onPlayerDead(PlayerDeathEvent event) {
        if (event.getKeepInventory()) {
            ItemUtil.dropPlayerItem(event.getEntity().getPlayer(), ItemUtil.getDropAmount());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        ArrayList<ItemStack> drop = ItemUtil.getDropList(event.getPlayer());
        if (!drop.isEmpty()) {
            event.getPlayer().sendMessage(ItemUtil.colorString(Randomdrop.getPluginConfig().getString("dropItemMessage").replace("%player%", event.getPlayer()
                    .getName()).replace("%location%", ItemUtil.getPlayerLocation(event.getPlayer())).replace("%itemlist%"
                    , ItemUtil.getItemsInfo(drop))));
            for (Player serverPlayer : Bukkit.getOnlinePlayers()) {
                serverPlayer.sendMessage(ItemUtil.colorString(Randomdrop.getPluginConfig().getString("dropItemMessage").replace("%player%", event.getPlayer()
                        .getName()).replace("%location%", ItemUtil.getPlayerLocation(event.getPlayer())).replace("%itemlist%"
                        , ItemUtil.getItemsInfo(drop))));
            }
            ItemUtil.removeDropList(event.getPlayer());
            ItemUtil.removePlayerLocation(event.getPlayer());
        }
    }
}
