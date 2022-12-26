package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.events.CancelEvent;
import fr.kenda.oneshot.events.DamageEvent;
import fr.kenda.oneshot.events.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager implements IManager {

    private static final Oneshot INSTANCE = Oneshot.getInstance();

    @Override
    public void register() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoin(), INSTANCE);
        pm.registerEvents(new CancelEvent(), INSTANCE);
        pm.registerEvents(new DamageEvent(), INSTANCE);
    }
}
