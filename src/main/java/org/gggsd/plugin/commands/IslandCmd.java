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
import org.gggsd.plugin.TpPoints;

import static org.gggsd.plugin.Main.plugin;


public class IslandCmd implements CommandExecutor {

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
                    Player p = (Player) sender;
                    if(TpPoints.getIndex(plugin.islands, strings[1]) == -1) {
                        p.sendMessage(ChatColor.RED+"ОШИБКА! Данного острова не существует!");
                        break;
                    }
                    int islInd = TpPoints.getIndex(plugin.islands, strings[1]);
                    String yamlname = strings[1] + ".owner";
                    String yamlowner = plugin.islands[islInd].getPlayerOwner();

                        if (!yamlowner.equalsIgnoreCase("empty") && !yamlowner.equalsIgnoreCase(p.getName())) {
                            sender.sendMessage(ChatColor.RED + "ОШИБКА! Телепортация невозможна, т.к. остров уже занят игроком: " + plugin.islands[islInd].getPlayerOwner());
                            break;
                        }


                        int x, y, z;

                        x = plugin.islands[islInd].getX();
                        y = plugin.islands[islInd].getY();
                        z = plugin.islands[islInd].getZ();



                        p.teleport(new Location(Bukkit.getWorld("world"), x, y, z));

                        p.sendMessage(ChatColor.GOLD + "Телепортация по координатам"+ChatColor.GREEN+" X: " + x + " Y: " + y + " Z: " + z);
                        
                        if(!yamlowner.equalsIgnoreCase(p.getName())) {
                            config.set(yamlname, p.getName());
                            plugin.islands[islInd].setPlayerOwner(p.getName());
                            Bukkit.broadcastMessage(ChatColor.YELLOW + strings[1].toLowerCase() + " передан во владению игроку: " + p.getName());
                        }
                    break;
                case "home":
                    int homeX = plugin.homePoint.getHomeX();
                    int homeY = plugin.homePoint.getHomeY();
                    int homeZ = plugin.homePoint.getHomeZ();
                      p = (Player) sender;
                      p.teleport(new Location(Bukkit.getWorld("world"),homeX, homeY, homeZ));
                      p.sendMessage(ChatColor.GOLD + "Телепортация на hub-остров");
                    break;
                case "homecreate":
                    homeX = plugin.homePoint.getHomeX();
                    homeY = plugin.homePoint.getHomeY();
                    homeZ = plugin.homePoint.getHomeZ();
                    if(strings.length != 4) {
                        sender.sendMessage(ChatColor.YELLOW +"X: " +homeX + " ; Y: " +homeY+" ; Z: " + homeZ);
                        return true;
                    }
                    if (isInteger(strings[1]) && isInteger(strings[2]) && isInteger(strings[3])) {
                        plugin.homePoint.setHomeXYZ(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
                        config.set("homexyz.x", Integer.parseInt(strings[1]));
                        config.set("homexyz.y", Integer.parseInt(strings[2]));
                        config.set("homexyz.z", Integer.parseInt(strings[3]));
                        plugin.saveConfig();

                        homeX = plugin.homePoint.getHomeX();
                        homeY = plugin.homePoint.getHomeY();
                        homeZ = plugin.homePoint.getHomeZ();
                        sender.sendMessage(ChatColor.GOLD+"Координаты hub-острова изменены на "+ChatColor.GREEN+"X: "+homeX +
                                " ; Y: " +homeY+" ; Z: " + homeZ);

                    }
                    else sender.sendMessage(ChatColor.YELLOW +"X: " +homeX + " ; Y: " +homeY+" ; Z: " + homeZ);
                    break;
             /*   case "tpcreate":

                    break;*/
                default:
                    sender.sendMessage( ChatColor.RED +"ОШИБКА! Пример ввода комманды: /island <tp/home/homecreate> [Доп аргументы]");
            }
            return true;
        }

        return true;
    }
}
