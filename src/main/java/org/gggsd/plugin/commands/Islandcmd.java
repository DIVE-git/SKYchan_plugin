package org.gggsd.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.gggsd.plugin.Main;

import static org.gggsd.plugin.Main.plugin;


public class Islandcmd implements CommandExecutor {

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("island")) {
            if(strings.length == 0) return false;

             if (sender instanceof ConsoleCommandSender && !plugin.DEBUG) {
                sender.sendMessage(ChatColor.RED +"Данная команда не может быть использована из консоли!");
                return true;
            }
            FileConfiguration config = plugin.getConfig(); // Подгрузка конфига
            switch (strings[0].toLowerCase()) {
                case "tp":
                    if (strings.length == 1) return false;
                    String yamlname = strings[1] + ".owner";
                    String yamlowner = config.getString(yamlname);
                    try {
                        if (!yamlowner.equalsIgnoreCase("empty") && !yamlowner.equalsIgnoreCase(sender.getName())) {
                            sender.sendMessage(ChatColor.RED + "ОШИБКА! Телепортация невозможна, т.к. остров уже занят игроком: " + config.getString(yamlname));

                            break;
                        }
                    }
                    catch (NullPointerException e) {
                        sender.sendMessage(ChatColor.RED + "ОШИБКА! введено имя несуществуещего острова!");
                        break;
                    }
                        int x, y, z;

                        Player p = (Player) sender;
                        x = config.getInt(strings[1] + ".x");
                        y = config.getInt(strings[1] + ".y");
                        z = config.getInt(strings[1] + ".z");

                        p.teleport(new Location(Bukkit.getWorld("world"), x, y, z));

                        p.sendMessage(ChatColor.GOLD + "Телепортация по координатам"+ChatColor.GREEN+" X: " + x + " Y: " + y + " Z: " + z);
                        if(!yamlowner.equalsIgnoreCase(sender.getName())) {
                            config.set(yamlname, p.getName());
                            Bukkit.broadcastMessage(ChatColor.YELLOW + strings[1].toLowerCase() + " передан во владению игроку: " + p.getName());
                        }
                    break;
                case "home":
                      p = (Player) sender;
                      p.teleport(new Location(Bukkit.getWorld("world"),config.getInt("homexyz.x"),config.getInt("homexyz.y"), config.getInt("homexyz.z")));
                      p.sendMessage(ChatColor.GOLD + "Телепортация на hub-остров");
                    break;
                case "homecreate":
                    if(strings.length != 4) {
                        sender.sendMessage(ChatColor.YELLOW +"X: " +config.getInt("homexyz.x") + " ; Y: " +config.getInt("homexyz.y")+" ; Z: " + config.getInt("homexyz.z"));
                        return true;
                    }
                    if (isInteger(strings[1]) && isInteger(strings[2]) && isInteger(strings[3])) {
                        config.set("homexyz.x", Integer.parseInt(strings[1]));
                        config.set("homexyz.y", Integer.parseInt(strings[2]));
                        config.set("homexyz.z", Integer.parseInt(strings[3]));
                        plugin.saveConfig();
                        sender.sendMessage(ChatColor.GOLD+"Координаты hub-острова изменены на "+ChatColor.GREEN+"X: "+config.getInt("homexyz.x") +
                                " ; Y: " +config.getInt("homexyz.y")+" ; Z: " + config.getInt("homexyz.z"));

                    }
                    else sender.sendMessage(ChatColor.YELLOW +"X: " +config.getInt("homexyz.x") + " ; Y: " +config.getInt("homexyz.y")+" ; Z: " + config.getInt("homexyz.z"));
                    break;
                case "tpcreate":

                    break;
                default:
                    sender.sendMessage( ChatColor.RED +"ОШИБКА! Пример ввода комманды: /island <tp/home/homecreate> [Доп аргументы]");
            }
            return true;
        }

        return true;
    }
}
