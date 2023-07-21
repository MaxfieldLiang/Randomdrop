package me.maxfield.randomdrop.command;

import me.maxfield.randomdrop.Randomdrop;
import org.bukkit.ChatColor;
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
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                Randomdrop.getPlugin(Randomdrop.class).reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "重新加载配置成功！");
                return true;
            } else if(args[0].equalsIgnoreCase("add")) {
                ItemStack itemStack = ((Player) sender).getItemInHand();
                StringBuffer stringBuffer = new StringBuffer(Randomdrop.getPluginConfig().getString("notDropItemList"));
                stringBuffer.append(" " + itemStack.getItemMeta());
                Randomdrop.getPluginConfig().set("notDropItemList", stringBuffer.toString());


                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "/randomdrop reload或者add");
                return true;
            }
        }
        return false;
    }
}
