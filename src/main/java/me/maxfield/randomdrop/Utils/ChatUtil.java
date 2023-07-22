package me.maxfield.randomdrop.Utils;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String colorString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
