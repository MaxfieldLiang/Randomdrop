package me.maxfield.randomdrop.command;

import me.maxfield.randomdrop.Randomdrop;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("本指令只有游戏里面可以使用");
            return true;
        }
        if (sender.isOp()) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    Randomdrop.getPlugin(Randomdrop.class).reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "重新加载配置成功！");
                    return true;
                } else if (args[0].equalsIgnoreCase("add")) {
                    ItemStack itemStack = ((Player) sender).getItemInHand();
                    if (itemStack.getType() != Material.AIR) {
                        String configItemList = Randomdrop.getPluginConfig().getString("notDropItemList");
                        if (!configItemList.contains(itemStack.getType().name())) {
                            StringBuffer stringBuffer = new StringBuffer(configItemList);
                            stringBuffer.append(" " + itemStack.getType().name());
                            Randomdrop.getPluginConfig().set("notDropItemList", stringBuffer.toString());
                            Randomdrop.getPlugin(Randomdrop.class).saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "添加成功！");
                        } else {
                            String newConfig = configItemList.replace(itemStack.getType().name(), "");
                            Randomdrop.getPluginConfig().set("notDropItemList", newConfig);
                            Randomdrop.getPlugin(Randomdrop.class).saveConfig();
                            sender.sendMessage(ChatColor.GREEN + "删除成功！");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "请在手上拿着物品");
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "/randomdrop reload或者add");
                    return true;
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "您没有权限使用这个指令");
            return true;
        }
        return false;
    }
}
