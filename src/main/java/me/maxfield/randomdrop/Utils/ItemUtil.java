package me.maxfield.randomdrop.Utils;

import me.maxfield.randomdrop.Randomdrop;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ItemUtil {
    private static HashMap<Player, ArrayList<ItemStack>> playerItemList = new HashMap<>();
    private static HashMap<Player, String> playerLocation = new HashMap<>();


    public static void dropPlayerItem(Player player, int dropAmount) {
        Inventory inventory = player.getInventory();

        ArrayList<ItemStack> drop = new ArrayList();

        for (int dropA = dropAmount; dropA > 0; --dropA) {
            int slot = (new Random()).nextInt(inventory.getSize());
            ItemStack itemStack = player.getInventory().getItem(slot);
            if (itemStack != null) {
                if (itemStack.getType() != Material.AIR) {
                    if (!Randomdrop.getPluginConfig().getString("notDropItemList").contains(itemStack.getType().name())) {
                        player.getWorld().dropItem(player.getLocation(), itemStack);
                        drop.add(itemStack);
                        player.getInventory().setItem(slot, new ItemStack(Material.AIR));
                    }
                }
            }
        }
        if (!drop.isEmpty()) {
            Location location = player.getLocation();
            int lX = (int) location.getX();
            int lY = (int) location.getY();
            int lZ = (int) location.getZ();
            addPlayerLocation(player,player.getLocation().getWorld().getName() + " X:" + lX + " Y:" + lY + " Z:" + lZ);
            addDropList(player, drop);
        }
    }

    public static String getItemsInfo(ArrayList<ItemStack> itemStacks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ItemStack itemStack : itemStacks) {
            stringBuilder.append(getItemInfo(itemStack)).append(" ");
        }
        return stringBuilder.toString();
    }



    public static String getItemInfo(ItemStack itemStack) {
        StringBuilder stringBuilder = new StringBuilder();
        String name;
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            name = itemStack.getItemMeta().getDisplayName();
        } else {
            name = itemStack.getType().name();
        }
        int amount = itemStack.getAmount();
        stringBuilder.append(ChatColor.YELLOW).append(name).append(" x" + amount).append("\n");
        return stringBuilder.toString();
    }

    public static HashMap<Player, ArrayList<ItemStack>> getPlayerItemList() {
        return playerItemList;
    }

    public static ArrayList<ItemStack> getDropList(Player player) {
        ArrayList<ItemStack> list = new ArrayList();
        if (playerItemList.containsKey(player)) {
            list = playerItemList.get(player);
        }

        return list;
    }

    public static void addDropList(Player player, ArrayList<ItemStack> drops) {
        if (!playerItemList.containsKey(player)) {
            playerItemList.put(player, drops);
        }

    }

    public static void removeDropList(Player player) {
        if (playerItemList.containsKey(player)) {
            playerItemList.remove(player);
        }

    }

    public static void addPlayerLocation(Player player, String location) {
        if (!playerLocation.containsKey(player)) {
            playerLocation.put(player, location);
        }
    }

    public static String getPlayerLocation(Player player) {
        if (playerLocation.containsKey(player)) {
            return playerLocation.get(player);
        } else {
            return "Error001";
        }
    }

    public static void removePlayerLocation(Player player) {
        if (playerLocation.containsKey(player)) {
            playerLocation.remove(player);
        }
    }

    public static HashMap<Player, String> getPlayerLocation() {
        return playerLocation;
    }
}
