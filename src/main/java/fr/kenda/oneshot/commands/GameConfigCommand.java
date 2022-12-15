package fr.kenda.oneshot.commands;

import fr.kenda.oneshot.file.File.CustomFile;
import fr.kenda.oneshot.file.File.LocationsFile;
import fr.kenda.oneshot.managers.FileManager;
import fr.kenda.oneshot.utils.EFiles;
import fr.kenda.oneshot.utils.LocationsUtils;
import fr.kenda.oneshot.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;


public class GameConfigCommand implements CommandExecutor {

    private static final String PREFIX = MessageUtils.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "sender can't execute this command");
            return false;
        }
        if (!player.hasPermission(MessageUtils.getPermission("gameconfig.use"))) {
            player.sendMessage(MessageUtils.getMessage("no_permissions"));
            return false;
        }
        if (args.length == 0 || args.length > 2) {
            getHelp(player);
            return false;
        }
        String category = args[0];
        if(category.equalsIgnoreCase("reload")){
            player.sendMessage(PREFIX + ChatColor.GREEN + "Reloading all files...");
            for(CustomFile file : FileManager.getFiles()){
                player.sendMessage(PREFIX + ChatColor.GREEN + "Reloading " + file.getName() + " file");
                file.reloadConfig();
            }
            player.sendMessage(PREFIX + ChatColor.GREEN + "Reloading all files completed");
            return true;
        }
        if (category.equals("locations")) {
            if (args.length != 2) {
                getHelp(player);
                return true;
            }
            String subCommand = args[1];
            YamlConfiguration config = FileManager.getConfig("locations");
            LocationsFile file = (LocationsFile) FileManager.getFile("locations");

            switch (subCommand) {
                case "see" -> config.getConfigurationSection("locations").getKeys(false)
                        .forEach(key -> {
                            String[] argsLoc = LocationsUtils.getArgumentsLocation(LocationsUtils.locationParse(player.getLocation()));
                            String world = argsLoc[0];
                            String x = argsLoc[1];
                            String y = argsLoc[2];
                            String z = argsLoc[3];
                            String pitch = argsLoc[4];
                            String yaw = argsLoc[5];
                            player.sendMessage(MessageUtils.getMessage("locations.see",
                                    "%number%", key, "%world%", world, "%locX%", x," %locY%", y, "%locZ%", z, "%pitch%", pitch, "%yaw%", yaw));
                        });
                case "clear" -> file.clearLocations(player);
                case "remove" -> file.removeLocation(player);
                case "add" -> file.addLocation(player);
                case "setspawn" -> file.setSpawn(player);
                default -> getHelp(player);
            }
        } else
            getHelp(player);
        return false;
    }

    private void getHelp(Player player) {
        player.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "================");
        player.sendMessage(ChatColor.GREEN + "/gameconfig locations clear: " + ChatColor.RESET + "Clear all locations in file config");
        player.sendMessage(ChatColor.GREEN + "/gameconfig locations add: " + ChatColor.RESET + "Add your position location in file config");
        player.sendMessage(ChatColor.GREEN + "/gameconfig locations remove: " + ChatColor.RESET + "Remove last position if file config");
        player.sendMessage(ChatColor.GREEN + "/gameconfig locations see: " + ChatColor.RESET + "Show all locations in file config");
        player.sendMessage(ChatColor.GREEN + "/gameconfig locations setspawn: " + ChatColor.RESET + "Set the spawn of lobby");
        player.sendMessage(ChatColor.GREEN + "/gameconfig reload: " + ChatColor.RESET + "Reload all configuration files");
        player.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "================");
    }
}