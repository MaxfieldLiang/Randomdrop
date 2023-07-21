package me.maxfield.randomdrop;

import me.maxfield.randomdrop.command.MainCommand;
import me.maxfield.randomdrop.listener.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Randomdrop extends JavaPlugin {
    private Listeners listeners = new Listeners();
    private static Randomdrop randomdrop;

    @Override
    public void onEnable() {
        // Plugin startup logic
        randomdrop = this;
        Bukkit.getPluginManager().registerEvents(listeners, this);
        this.getCommand("randomdrop").setExecutor(new MainCommand());
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FileConfiguration getPluginConfig() {
        return randomdrop.getConfig();
    }
}
