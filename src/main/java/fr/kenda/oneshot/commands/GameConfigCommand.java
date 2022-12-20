package fr.kenda.oneshot.commands;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.file.File.CustomFile;
import fr.kenda.oneshot.file.File.LocationsFile;
import fr.kenda.oneshot.managers.FileManager;
import fr.kenda.oneshot.utils.LocationsUtils;
import fr.kenda.oneshot.utils.MessageUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class GameConfigCommand implements CommandExecutor {

    private static final String PREFIX = MessageUtils.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //check if sender is player
        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "sender can't execute this command");
            return false;
        }
        //check if sender has specific permission
        if (!player.hasPermission(MessageUtils.getPermission("gameconfig.use"))) {
            player.sendMessage(MessageUtils.getMessage("no_permissions"));
            return false;
        }
        //Check if args is 0 or more than 2, send help
        if (args.length < 2 || args.length > 3) {
            getHelp(player);
            return false;
        }

        //get the first args and name is category
        String category = args[0];

        //check if args is reload
        if (category.equalsIgnoreCase("reload")) {
            player.sendMessage(PREFIX + ChatColor.GREEN + "Reloading all files...");

            //get all customFile and reload configuration
            for (CustomFile file : FileManager.getFiles()) {
                player.sendMessage(PREFIX + ChatColor.GREEN + "Reloading " + file.getName() + " file");
                file.reloadConfig();
            }
            player.sendMessage(PREFIX + ChatColor.GREEN + "Reloading all files completed");
            return true;
        }
        //check if /gameconfig locations
        if (category.equals("locations")) {
            //get the sub category
            String subCommand = args[1];

            //get config of locations.yml
            YamlConfiguration config = FileManager.getConfig("locations");

            //get file of locations.yml
            LocationsFile file = (LocationsFile) FileManager.getFile("locations");

            switch (subCommand) {
               //check if "see" to see all locations
                case "see" -> {
                    ArrayList<String> list = file.getLocations();
                    int size = list.size();
                    for(int i = 0; i < size; i++) {
                        String[] argsLoc = LocationsUtils.getArgumentsLocation(LocationsUtils.locationParse(player.getLocation()));
                        TextComponent mainComponent = new TextComponent(MessageUtils.getMessage("locations.see",
                                "%number%", String.valueOf( i+ 1), "%world%", argsLoc[0], "%locX%", argsLoc[1], " %locY%", argsLoc[2], "%locZ%", argsLoc[3], "%pitch%", argsLoc[4], "%yaw%", argsLoc[5]));
                       mainComponent.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( ChatColor.GREEN + "Teleport to location " + (i+1)).create()));
                        mainComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/gameconfigteleport " + (i + 1)));
                        player.spigot().sendMessage(mainComponent);

                    }
                //TODO faire les particules
                }

                //clear all locations in locations.yml (except for spawn)
                case "clear" -> file.clearLocations(player);
                //Remove the last location in locations.yml
                case "remove" -> {
                    if (args.length == 3) {
                        try {
                            int index = Integer.parseInt(args[2]);
                            file.removeLocation(player, index);
                        } catch (NumberFormatException ex) {
                            player.sendMessage(PREFIX + "The index must be an integer");
                        }
                    } else
                        file.removeLocation(player, -1);
                }
                //Add the current location of player in locations.yml
                case "add" -> file.addLocation(player);
                //Set the new spawn point in locations.yml
                case "setspawn" -> file.setSpawn(player);
                default -> getHelp(player);
            }
        } else
            getHelp(player);
        return false;
    }

    /**
     * Get help message
     *
     * @param player Player
     */
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