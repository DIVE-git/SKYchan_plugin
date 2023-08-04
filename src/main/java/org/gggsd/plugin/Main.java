package org.gggsd.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import org.gggsd.plugin.commands.IslandCompleter;
import org.gggsd.plugin.commands.IslandCmd;
import org.gggsd.plugin.eventHandlers.MainEvents;

public class Main extends JavaPlugin{

    public boolean DEBUG = false; // ПОСЛЕ РАЗРАБОТКИ ОБЯЗАТЕЛЬНО ВЫКЛЮЧИТЬ!!!!
    public int landsNumber = getConfig().getInt("lands_numbers"); // переменная, хранящая число островов из config.yml
    public TpPoints[] islands = new TpPoints[landsNumber]; // Массив с хранящимися даннами об островав из config.yml
    public HomePoint homePoint = new HomePoint();
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
            getServer().getPluginCommand("island").setExecutor(new IslandCmd()); // Регистрация основной команды
            getServer().getPluginCommand("island").setTabCompleter(new IslandCompleter()); // Регистрация комплитера основной команды


            if(DEBUG) getLogger().warning("DEBUG MODE ENABLED");

            saveResource("config.yml", DEBUG); // Запись конфига

        /*
        Поясню за цикл снизу: в данном цикле при КАЖДОМ запуске плагина, записываются данные об островах из config.yml. Сделано это
        для дальнейшего упрощения работы с данными.
         */

        for (int i = 1;i <=landsNumber;i++) {
            try {
            String yamlName = "land"+i;
            int[] yamlXYZ = new int[] {getConfig().getInt(yamlName + ".x"),getConfig().getInt(yamlName + ".y"),getConfig().getInt(yamlName + ".z")};
            String yamlOwner = getConfig().getString(yamlName + ".owner");

            islands[i-1] = new TpPoints(yamlXYZ[0], yamlXYZ[1], yamlXYZ[2], yamlOwner, yamlName);
            }
            catch (NullPointerException e) {

                getLogger().warning("ОШИБКА! Неккоректная настройка конфига!");
            }
        }

        homePoint.setHomeXYZ(getConfig().getInt("homexyz.x"), getConfig().getInt("homexyz.y"), getConfig().getInt("homexyz.z"));

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Server Plugin Starts");
    }


    
}
