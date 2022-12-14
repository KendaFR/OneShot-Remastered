package fr.kenda.oneshot.commands;

import fr.kenda.oneshot.gamestatus.GameStatus;
import fr.kenda.oneshot.gamestatus.GameStatusManager;
import fr.kenda.oneshot.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length != 1) {
            getHelp(sender);
        }
        switch (args[0]) {
            case "start":
                if (!GameStatusManager.isStatus(GameStatus.WAITING))
                    sender.sendMessage(MessageUtils.getMessage("already_started"));
                else {
                    GameStatusManager.setStatus(GameStatus.STARTING);
                    sender.sendMessage(MessageUtils.getMessage("status_change"), "%status%", GameStatusManager.getStatus());
                }
                break;
            case "stop":
                if (!GameStatusManager.isStatus(GameStatus.GAME)) {
                    sender.sendMessage(MessageUtils.getMessage("already_stopped"));
                } else {
                    GameStatusManager.setStatus(GameStatus.FINISH);
                    sender.sendMessage(MessageUtils.getMessage("status_change"), "%status%", GameStatusManager.getStatus());
                }
                break;
        }
        return false;
    }

    private void getHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "================");
        sender.sendMessage(ChatColor.GREEN + "/game start: " + ChatColor.RESET + "start game");
        sender.sendMessage(ChatColor.GREEN + "/game stop: " + ChatColor.RESET + "stop game");
        sender.sendMessage(ChatColor.RED + "" + ChatColor.STRIKETHROUGH + "================");
    }
}
