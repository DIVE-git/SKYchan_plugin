package org.gggsd.plugin.eventHandlers;

import org.bukkit.ChatColor;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;

import static org.gggsd.plugin.Main.plugin;

public class MainEvents implements Listener {
    @EventHandler
    private void JoinEvent (PlayerJoinEvent e) {
        Player p = e.getPlayer();

        p.sendMessage(ChatColor.GOLD + "Привет! Статус всех островов сервера:");

        for (int i = 1;i <=plugin.getConfig().getInt("lands_numbers");i++) {
            try {
                if (plugin.islands[i-1].getPlayerOwner().equalsIgnoreCase("empty")) {
                    p.sendMessage(ChatColor.GOLD + "Остров " + i + ChatColor.GREEN + "   [ДОСТУПЕН]");
                } else {
                    p.sendMessage(ChatColor.GOLD + "Остров " + i + ChatColor.RED + "   [ЗАНЯТ ИГРОКОМ: " + plugin.islands[i-1].getPlayerOwner() + "]");
                }
            }
            catch (NullPointerException ex) {

            p.sendMessage(ChatColor.RED+"ОШИБКА! Неккоректная настройка конфига! Остров " + i + " отсутствует!");
            }
        }
    }
}

