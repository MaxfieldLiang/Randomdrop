package me.maxfield.randomdrop.listener;


import me.maxfield.randomdrop.Randomdrop;
import me.maxfield.randomdrop.Utils.ChatUtil;
import me.maxfield.randomdrop.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
        Player player = event.getEntity().getPlayer();
        if (!event.getKeepInventory() && Randomdrop.getPluginConfig().getBoolean("enable")) {
            event.setKeepInventory(true);
            if (player.getGameMode() != GameMode.SPECTATOR && player.getGameMode() != GameMode.CREATIVE) {
                ItemUtil.dropPlayerItem(player, Randomdrop.getPluginConfig().getInt("dropAmount"));
                ArrayList<ItemStack> drop = ItemUtil.getDropList(player);
                if (!drop.isEmpty()) {
                    player.sendMessage(ChatUtil.colorString(Randomdrop.getPluginConfig().getString("dropItemMessage").replace("%player%", player
                            .getName()).replace("%location%", ItemUtil.getPlayerLocation(player)).replace("%itemlist%"
                            , ItemUtil.getItemsInfo(drop))));
                    for (Player serverPlayer : Bukkit.getOnlinePlayers()) {
                        serverPlayer.sendMessage(ChatUtil.colorString(Randomdrop.getPluginConfig().getString("dropItemMessage").replace("%player%", player
                                .getName()).replace("%location%", ItemUtil.getPlayerLocation(player)).replace("%itemlist%"
                                , ItemUtil.getItemsInfo(drop))));
                    }
                    ItemUtil.removeDropList(player);
                    ItemUtil.removePlayerLocation(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

    }
}
