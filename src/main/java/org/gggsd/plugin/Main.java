package org.gggsd.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import org.gggsd.plugin.commands.IslandCompleter;
import org.gggsd.plugin.commands.Islandcmd;
import org.gggsd.plugin.eventHandlers.MainEvents;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.io.File;

public class Main extends JavaPlugin{

    public boolean DEBUG = false; // ПОСЛЕ РАЗРАБОТКИ ОБЯЗАТЕЛЬНО ВЫКЛЮЧИТЬ!!!!
    public static Main plugin;
    {
        plugin = this;

    }
    @Override
    public void onDisable() {
        getServer().getLogger().info("Plugin stopped");
    }
    @Override
    public void onEnable() {
            getServer().getPluginManager().registerEvents(new MainEvents(), this); // Регистрация класса с евентами
            getServer().getPluginCommand("island").setExecutor(new Islandcmd()); // Регистрация основной команды
            getServer().getPluginCommand("island").setTabCompleter(new IslandCompleter());

            if(DEBUG) getLogger().warning("DEBUG MODE ENABLED");
            saveResource("config.yml", DEBUG); // Запись конфига
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Server Plugin Starts");
    }


    
}
