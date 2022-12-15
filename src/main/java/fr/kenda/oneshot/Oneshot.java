package fr.kenda.oneshot;

import fr.kenda.oneshot.gamestatus.GameStatus;
import fr.kenda.oneshot.gamestatus.GameStatusManager;
import fr.kenda.oneshot.managers.CommandManager;
import fr.kenda.oneshot.managers.FileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Oneshot extends JavaPlugin {

    private static Oneshot instance;

    public static Oneshot getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        GameStatusManager.setStatus(GameStatus.WAITING);
        new CommandManager().register();
        new FileManager().register();

    }

    @Override
    public void onDisable() {
        GameStatusManager.setStatus(GameStatus.FINISH);
    }

}
