package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.commands.GameCommand;
import fr.kenda.oneshot.commands.GameConfigCommand;

public class CommandManager implements IManager {

    private final Oneshot instance = Oneshot.getInstance();

    /**
     * Register all commands
     */
    @Override
    public void register() {
        instance.getCommand("game").setExecutor(new GameCommand());
        instance.getCommand("gameconfig").setExecutor(new GameConfigCommand());
    }
}
