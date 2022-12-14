package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.commands.GameCommand;

public class CommandManager implements IManager {

    private final Oneshot instance = Oneshot.getInstance();

    @Override
    public void register() {
        instance.getCommand("game").setExecutor(new GameCommand());
    }
}