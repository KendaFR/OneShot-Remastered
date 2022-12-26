package fr.kenda.oneshot;

import fr.kenda.oneshot.gamestatus.GameStatus;
import fr.kenda.oneshot.gamestatus.GameStatusManager;
import fr.kenda.oneshot.managers.CommandManager;
import fr.kenda.oneshot.managers.EventsManager;
import fr.kenda.oneshot.managers.FileManager;
import fr.kenda.oneshot.managers.GameManager;
import fr.kenda.oneshot.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Oneshot extends JavaPlugin {

    public static final GameManager GAME_MANAGER = new GameManager();
    private static Oneshot instance;

    public static Oneshot getInstance() {
        return instance;
    }


    /*
    On server start
    save the configuration and set the status of game on WAITING
    register all command
    register all files custom
     */
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        GameStatusManager.setStatus(GameStatus.WAITING);
        new CommandManager().register();
        new FileManager().register();
        new EventsManager().register();

        //TODO make connection to database

    }

    @Override
    public void onDisable() {
        GameStatusManager.setStatus(GameStatus.FINISH);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(MessageUtils.getPrefix() + "Plugin OneShot has been reloaded. Please reconnect");
        }
    }

}
