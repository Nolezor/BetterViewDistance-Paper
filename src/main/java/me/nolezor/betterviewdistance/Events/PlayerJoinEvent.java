package me.nolezor.betterviewdistance.Events;

import me.nolezor.betterviewdistance.BetterViewDistance;
import me.nolezor.betterviewdistance.Data.PlayerData;
import me.nolezor.betterviewdistance.Utils.PlayerDataStorageUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

import java.util.EventListener;

public class PlayerJoinEvent implements EventListener {
    Plugin plugin = BetterViewDistance.getPlugin();

    @EventHandler
    public void OnPlayerJoin(org.bukkit.event.player.PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            PlayerData data = PlayerDataStorageUtils.searchData(e.getPlayer().getUniqueId().toString());
            if (data.getCurrentViewDistance() != e.getPlayer().getViewDistance()) {
                PlayerDataStorageUtils.setPlayerViewDistance(data);
            }
        });
    }
}
