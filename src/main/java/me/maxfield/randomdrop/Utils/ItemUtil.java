package me.maxfield.randomdrop.Utils;

import me.maxfield.randomdrop.Randomdrop;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ItemUtil {
    private static HashMap<Player, ArrayList<ItemStack>> playerItemList = new HashMap<>();
    private static HashMap<Player, String> playerLocation = new HashMap<>();
    private static final FileConfiguration config = Randomdrop.getPluginConfig();
    private static final int dropAmount = config.getInt("dropAmount");


    public static void dropPlayerItem(Player player, int dropAmount) {
        Inventory inventory = player.getInventory();

        ArrayList<ItemStack> drop = new ArrayList();
        for (int droppedItem = dropAmount; droppedItem > 0; --droppedItem) {
            int slot = (new Random()).nextInt(inventory.getSize());
            if (player.getInventory().getItem(slot) != null) {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getItem(slot));
                drop.add(player.getInventory().getItem(slot));
                player.getInventory().setItem(slot, new ItemStack(Material.AIR));
            }
        }
        if (drop != null) {
            Location playerLocation = player.getLastDeathLocation();
            int lX = (int) playerLocation.getX();
            int lY = (int) playerLocation.getY();
            int lZ = (int) playerLocation.getZ();
            addPlayerLocation(player,player.getLocation().getWorld().getName() + " X:" + lX + " Y:" + lY + " Z:" + lZ);
            addDropList(player, drop);
        }
    }

    public static String getItemsInfo(ArrayList<ItemStack> itemStacks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ItemStack itemStack : itemStacks) {
            stringBuilder.append(getItemInfo(itemStack) + " ");
        }
        return stringBuilder.toString();
    }

    public static String colorString(String string) {
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }

    public static String getItemInfo(ItemStack itemStack) {
        StringBuilder stringBuilder = new StringBuilder();
        Material material = itemStack.getType();
        String name;
        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            name = itemStack.getItemMeta().getDisplayName();
        } else {
            name = itemStack.getType().name();
        }
        List<String> lore = itemStack.getItemMeta().getLore();
        int amount = itemStack.getAmount();
        stringBuilder.append(ChatColor.YELLOW).append(name).append(" x" + amount).append("\n");
        if (lore != null) {
            stringBuilder.append(ChatColor.YELLOW + "附魔: ").append(lore.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public static int getDropAmount() {
        return dropAmount;
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
