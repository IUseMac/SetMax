package commands;

import SetMaxPlayers.IUseMac.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class setmax implements CommandExecutor {
    private Main plugin;

    public setmax(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("setmax").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[SetMax] Only Players can Execute this Command!");
        }

        if (sender instanceof Player) {
            Player p = (Player) sender;
            String maxPlayers = plugin.getConfig().getString("maxPlayers");
            String online = String.valueOf(Bukkit.getOnlinePlayers().size());

            if (p.hasPermission("setmax.use")) {
                if (args.length == 1) {

                    String changeTo = args[0];
                    if (changeTo.equalsIgnoreCase("reload")) {
                        //setmax reload
                        plugin.saveDefaultConfig();
                        plugin.getConfig().options().copyDefaults(true);
                        plugin.reloadConfig();
                        plugin.saveConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("reloadedConfig")));
                        return true;

                    } else {
                        //string checker
                        int format = -1;
                        try {
                            format = Integer.parseInt(args[0]);
                        } catch (NumberFormatException e) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("invalidCommand")));
                            return true;
                        }

                        //setmax players
                        int change = Integer.parseInt(changeTo);
                        if (change < 1) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("needHigherInput")));
                        } else {
                            plugin.getConfig().set("maxPlayers", changeTo);
                            plugin.saveConfig();
                            plugin.reloadConfig();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("changedMax").replace("{oldMax}", maxPlayers).replace("{newMax}", changeTo)));
                            maxPlayers = args[0];
                            return true;

                        }
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("invalidCommand")));
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("noPermission")));
                return true;
            }
        }
        return true;
        }
    }


