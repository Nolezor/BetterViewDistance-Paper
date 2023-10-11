package me.nolezor.betterviewdistance;

import me.nolezor.betterviewdistance.Commands.SetViewDistanceCommand;
import me.nolezor.betterviewdistance.Commands.SetViewDistanceTabCompleter;
import me.nolezor.betterviewdistance.Utils.PlayerDataStorageUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class BetterViewDistance extends JavaPlugin {

    private static BetterViewDistance plugin;
    @Override
    public void onEnable() {

        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        try {
            PlayerDataStorageUtils.loadPlayersData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getCommand("setplayerviewdistance").setExecutor(new SetViewDistanceCommand());
        getCommand("setplayerviewdistance").setTabCompleter(new SetViewDistanceTabCompleter());

        getLogger().info(ChatColor.GREEN + "Loaded successfully.");

    }

    @Override
    public void onDisable() {
        try {
            PlayerDataStorageUtils.fixData();
            PlayerDataStorageUtils.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BetterViewDistance getPlugin() {
        return plugin;
    }
}
