package me.nolezor.betterviewdistance.Commands;

import me.nolezor.betterviewdistance.BetterViewDistance;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SetViewDistanceTabCompleter implements TabCompleter {
    Plugin plugin = BetterViewDistance.getPlugin();
    private int maxViewDistance = plugin.getConfig().getInt("MaxViewDistance");
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> playersName = new ArrayList<>();
            Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (Player p: players) {
                playersName.add(p.getName());
            }
            return playersName;
        }

        if (args.length == 2) {
            List<String> suggestedViewDistances = new ArrayList<>();
            int tempViewDistance = 4;
            while (tempViewDistance <= maxViewDistance) {
                suggestedViewDistances.add(String.valueOf(tempViewDistance));
                tempViewDistance *= 2;
            }
            if (!(suggestedViewDistances.contains(String.valueOf(maxViewDistance)))) {
                suggestedViewDistances.add(String.valueOf(maxViewDistance));
            }
            return suggestedViewDistances;
        }

        return null;
    }
}
