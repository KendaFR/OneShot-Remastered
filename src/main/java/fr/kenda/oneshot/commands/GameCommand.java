package fr.kenda.oneshot.commands;

import fr.kenda.oneshot.gamestatus.GameStatus;
import fr.kenda.oneshot.gamestatus.GameStatusManager;
import fr.kenda.oneshot.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class GameCommand implements CommandExecutor {

    private static final String PREFIX = MessageUtils.getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Check if the sender has a specific permission the use
        if (!sender.hasPermission(MessageUtils.getPermission("game.use"))) {
            sender.sendMessage(MessageUtils.getMessage("no_permissions", true));
            return false;
        }
        //Send help mesasge if command has no args
        if (args.length != 1) {
            getHelp(sender);
            return false;
        }
        switch (args[0]) {
            /*
            if args is start, check if status is WAITING or STARTING
             */
            case "start" -> {
                if (!GameStatusManager.isStatus(GameStatus.WAITING))
                    sender.sendMessage(MessageUtils.getMessage("already_started", true));
                else {
                    GameStatusManager.setStatus(GameStatus.STARTING);
                    sender.sendMessage(MessageUtils.getMessage("status_change", true, "%status%", GameStatusManager.getStatus()));
                }
            }
             /*
            if args is stop, check if status is GAME or FINISH
             */
            case "stop" -> {
                if (!GameStatusManager.isStatus(GameStatus.GAME)) {
                    sender.sendMessage(MessageUtils.getMessage("already_stopped", true));
                } else {
                    GameStatusManager.setStatus(GameStatus.FINISH);
                    sender.sendMessage(MessageUtils.getMessage("status_change", true, "%status%", GameStatusManager.getStatus()));
                }
            }
        }
        return false;
    }

    /**
     * Get the help message
     *
     * @param sender CommandSender
     */
    private void getHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "================");
        sender.sendMessage(ChatColor.GREEN + "/game start: " + ChatColor.RESET + "start game");
        sender.sendMessage(ChatColor.GREEN + "/game stop: " + ChatColor.RESET + "stop game");
        sender.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "================");
    }
}
