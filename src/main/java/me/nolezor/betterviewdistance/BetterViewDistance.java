package me.nolezor.betterviewdistance;

import me.nolezor.betterviewdistance.Commands.SetViewDistanceCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterViewDistance extends JavaPlugin {

    private static BetterViewDistance plugin;
    @Override
    public void onEnable() {

        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("setplayerviewdistance").setExecutor(new SetViewDistanceCommand());

        getLogger().info(ChatColor.GREEN + "BetterViewDistance finished loading.");

    }

    public static BetterViewDistance getPlugin() {
        return plugin;
    }
}
