package org.gggsd.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

import static org.gggsd.plugin.Main.plugin;

public class IslandCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {

        if(strings.length == 1) {
            return List.of(
                    "tp",
                    "home",
                    "homecreate"
            );
        }
        if(strings.length == 2) {
           if(strings[0].equalsIgnoreCase("tp")) {
                List list = new ArrayList<>();
                for(int i = 1; i <= plugin.landsNumber;i++) {
                    list.add("land"+i);
                }
                return list;
            }
        }

        return null;
    }
}
