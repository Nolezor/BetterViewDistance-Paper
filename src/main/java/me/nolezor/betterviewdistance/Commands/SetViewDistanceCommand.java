package me.nolezor.betterviewdistance.Commands;

import me.nolezor.betterviewdistance.BetterViewDistance;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetViewDistanceCommand implements CommandExecutor {
    Plugin plugin = BetterViewDistance.getPlugin();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setplayerviewdistance")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("betterviewdistance.setplayerviewdistance")) {
                    if (args.length < 2) {
                        p.sendMessage(ChatColor.RED + "Command syntax: " + command.getUsage());
                    } else {
                        String playerName = args[0];
                        if (playerName.length() <= 16) {
                            Server server = plugin.getServer();
                            Player commandSubject = server.getPlayer(args[0]);
                            if (commandSubject != null && commandSubject.getName().equals(args[0])) {
                                try {
                                    int newViewDistance = Integer.parseInt(args[1]);
                                    if (newViewDistance <= plugin.getConfig().getInt("MaxViewDistance")) {
                                        commandSubject.setViewDistance(newViewDistance);
                                        p.sendMessage(ChatColor.GREEN + "The new view distance for player " + commandSubject.getName()+ " has been set to " + newViewDistance + ".");
                                    } else {
                                        p.sendMessage(ChatColor.RED + "The maximum view distance allowed is " + plugin.getConfig().getInt("MaxViewDistance"));
                                    }
                                } catch (NumberFormatException e) {
                                    p.sendMessage(ChatColor.RED + "The new view distance must be integer.");
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "No user found with username: " + args[0]);
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Enter a valid player name");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + command.permissionMessage().toString());
                }
            } else if (sender instanceof ConsoleCommandSender) {
                if (args.length < 2) {
                    plugin.getLogger().info("Command syntax: " + command.getUsage());
                } else {
                    String playerName = args[0];
                    if (playerName.length() <= 16) {
                        Server server = plugin.getServer();
                        Player commandSubject = server.getPlayer(args[0]);
                        if (commandSubject != null && commandSubject.getName().equals(args[0])) {
                            try {
                                int newViewDistance = Integer.parseInt(args[1]);
                                if (newViewDistance <= plugin.getConfig().getInt("MaxViewDistance")) {
                                    commandSubject.setViewDistance(newViewDistance);
                                    plugin.getLogger().info("The new view distance for player " + commandSubject.getName()+ " has been set to " + newViewDistance + ".");
                                } else {
                                    plugin.getLogger().warning("The given view distance exceeds the config maximum view distance: " + plugin.getConfig().getInt("MaxViewDistance"));
                                }
                            } catch (NumberFormatException e) {
                                plugin.getLogger().info("The new view distance must be integer.");
                            }
                        } else {
                            plugin.getLogger().info( "No user found with username: " + args[0]);
                        }
                    } else {
                        plugin.getLogger().info("Enter a valid player name");
                    }
                }
            } else if (sender instanceof BlockCommandSender) {
                CommandBlock c = (CommandBlock) sender;
                plugin.getLogger().warning("BetterViewDistance + A command block tried to run the command - World: " + c.getLocation().getWorld() + " XYZ: " + c.getLocation().getX() + " " + c.getLocation().getY() + " " + c.getLocation().getZ() + " ");
            }

        }

        return true;
    }
}
