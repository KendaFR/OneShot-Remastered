package fr.kenda.oneshot.commands;

import fr.kenda.oneshot.file.file.LocationsFile;
import fr.kenda.oneshot.managers.FileManager;
import fr.kenda.oneshot.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameConfigTeleport implements CommandExecutor {
    private final String prefix = MessageUtils.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(prefix + ChatColor.RED + "You can't execute this command here");
            return false;
        }
        //check if sender has specific permission
        if (!player.hasPermission(MessageUtils.getPermission("gameconfig.use"))) {
            player.sendMessage(MessageUtils.getMessage("no_permissions", true));
            return false;
        }

        LocationsFile lm = (LocationsFile) FileManager.getFile("locations");
        //get the argument of command
        int number = Integer.parseInt(args[0]) - 1;

        //get the location at number in config
        Location loc = lm.getLocation(number);
        player.teleport(loc);
        return false;
    }
}
