package fr.kenda.oneshot.events;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.file.file.LocationsFile;
import fr.kenda.oneshot.managers.FileManager;
import fr.kenda.oneshot.managers.GameManager;
import fr.kenda.oneshot.managers.ScoreboardManager;
import fr.kenda.oneshot.sql.PlayerStatsSQL;
import fr.kenda.oneshot.stats.PlayerStats;
import fr.kenda.oneshot.utils.ConfigDatabase;
import fr.kenda.oneshot.utils.LocationsUtils;
import fr.kenda.oneshot.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class PlayerJoin implements Listener {

    private static final GameManager GAME_MANAGER = Oneshot.GAME_MANAGER;
    private final ScoreboardManager sm = GAME_MANAGER.getScoreboards();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        LocationsFile lf = (LocationsFile) FileManager.getFile("locations");
        Location spawn = LocationsUtils.getParsedLocation(lf.getConfig().getString("locations.spawn"));
        player.teleport(spawn);
        player.getInventory().clear();
        player.setFoodLevel(20);
        player.setHealth(20);
        player.setGameMode(GameMode.ADVENTURE);
        e.setJoinMessage(MessageUtils.getMessage("join", false,
                "%player%", player.getName(),
                "%online%", String.valueOf(Bukkit.getOnlinePlayers().size()),
                "%maxplayers%", String.valueOf(Bukkit.getMaxPlayers())));

        if (ConfigDatabase.getProcessSave().equalsIgnoreCase("mysql") || ConfigDatabase.getProcessSave().equalsIgnoreCase("sqlite"))
            //TODO a changer
            GAME_MANAGER.getStatsOfPlayers().add(new PlayerStats(player, PlayerStatsSQL.getKills(player.getName()), PlayerStatsSQL.getDeaths(player.getName())));
            //TODO get infos from BDD

        else
            GAME_MANAGER.getStatsOfPlayers().add(new PlayerStats(player, 0, 0));

        if (GAME_MANAGER.canStart()) {
            ArrayList<PlayerStats> ps = GAME_MANAGER.getStatsOfPlayers();
            Player p1 = ps.get(0).getPlayer();
            Player p2 = ps.get(1).getPlayer();
            sm.setSpawnBoard(p1, p2);
            sm.setSpawnBoard(p2, p1);
            GAME_MANAGER.startGame();
        } else
            sm.setWaitingBoard(player);
    }
}
