package me.nolezor.betterviewdistance.Commands;

import me.nolezor.betterviewdistance.BetterViewDistance;
import me.nolezor.betterviewdistance.Data.PlayerData;
import me.nolezor.betterviewdistance.Utils.PlayerDataStorageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Server;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetViewDistanceCommand implements CommandExecutor {
    private final static Plugin plugin = BetterViewDistance.getPlugin();
    private static int maxViewDistance = plugin.getConfig().getInt("MaxViewDistance");
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!(p.hasPermission("betterviewdistance.setplayerviewdistance"))) {
                p.sendMessage(Component.text("You don't have permission to run this command!", NamedTextColor.RED));
                return true;
            }
            if (args.length < 2) {
                p.sendMessage(Component.text("Command syntax: " + command.getUsage(), NamedTextColor.GREEN));
                return true;
            }

            String playerName = args[0];
            if (playerName.length() >= 16) {
                p.sendMessage(Component.text( "Enter a valid player name", NamedTextColor.GREEN));
                return true;
            }
            Server server = plugin.getServer();
            Player commandSubject = server.getPlayer(args[0]);
            if (commandSubject == null || !commandSubject.getName().equals(args[0])) {
                p.sendMessage(Component.text("No user found with username: " + args[0], NamedTextColor.GREEN));
                return true;
            }
            try {
                int newViewDistance = Integer.parseInt(args[1]);
                if (newViewDistance > maxViewDistance) {
                    p.sendMessage(Component.text("The maximum view distance allowed is " + maxViewDistance, NamedTextColor.GREEN));
                    return true;
                }
                PlayerData data = PlayerDataStorageUtils.searchData(commandSubject.getUniqueId().toString());
                if (data == null) {
                    data = PlayerDataStorageUtils.createData(commandSubject.getUniqueId().toString(), newViewDistance);
                }
                data.setCurrentViewDistance(newViewDistance);
                PlayerDataStorageUtils.setPlayerViewDistance(data);
                p.sendMessage(Component.text("The new view distance for player " + commandSubject.getName()+ " has been set to " + newViewDistance, NamedTextColor.GREEN));

            } catch (NumberFormatException e) {
                p.sendMessage(Component.text("The new view distance must be an integer", NamedTextColor.RED));
            }

        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length < 2) {
                plugin.getLogger().info("Command syntax: " + command.getUsage());
                return true;
            }

            String playerName = args[0];
            if (playerName.length() >= 16) {
                plugin.getLogger().info("Enter a valid player name");
                return true;
            }
            Server server = plugin.getServer();
            Player commandSubject = server.getPlayer(args[0]);
            if (commandSubject == null || !commandSubject.getName().equals(args[0])) {
                plugin.getLogger().info( "No user found with username: " + args[0]);
                return true;
            }
            try {
                int newViewDistance = Integer.parseInt(args[1]);
                if (newViewDistance > maxViewDistance) {
                    plugin.getLogger().warning("The given view distance exceeds the config maximum view distance: " + plugin.getConfig().getInt("MaxViewDistance"));
                }
                PlayerData data = PlayerDataStorageUtils.searchData(commandSubject.getUniqueId().toString());
                if (data == null) {
                    data = PlayerDataStorageUtils.createData(commandSubject.getUniqueId().toString(), newViewDistance);
                }
                data.setCurrentViewDistance(newViewDistance);
                PlayerDataStorageUtils.setPlayerViewDistance(data);
                plugin.getLogger().info("The new view distance for player " + commandSubject.getName()+ " has been set to " + newViewDistance + ".");

            } catch (NumberFormatException e) {
                plugin.getLogger().info("The new view distance must be integer.");
            }

        } else if (sender instanceof BlockCommandSender) {
            CommandBlock c = (CommandBlock) sender;
            plugin.getLogger().warning("BetterViewDistance + A command block tried to run the command - World: " + c.getLocation().getWorld() + " XYZ: " + c.getLocation().getX() + " " + c.getLocation().getY() + " " + c.getLocation().getZ() + " ");
        }
        return true;
    }
}
