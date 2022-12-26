package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.file.file.LocationsFile;
import fr.kenda.oneshot.gamestatus.GameStatus;
import fr.kenda.oneshot.gamestatus.GameStatusManager;
import fr.kenda.oneshot.schedulers.TimerArrow;
import fr.kenda.oneshot.stats.PlayerStats;
import fr.kenda.oneshot.utils.ItemBuilder;
import fr.kenda.oneshot.utils.LocationsUtils;
import fr.kenda.oneshot.utils.MessageUtils;
import fr.kenda.oneshot.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private static final ArrayList<PlayerStats> playersStats = new ArrayList<>();
    private final ScoreboardManager sm = new ScoreboardManager(this);
    private Location oldLocationSelected = null;

    /**
     * Check if player has win
     *
     * @param player Player
     * @return Boolean
     */
    public static boolean hasWin(Player player) {
        return getStatsOfPlayer(player).getKills() == 5;
    }

    /**
     * Get the playerSTats of player
     *
     * @param player Player
     * @return PlayerStats
     */
    public static PlayerStats getStatsOfPlayer(Player player) {
        return playersStats.stream().filter(playerStats -> playerStats.getPlayer() == player).findFirst().orElse(null);
    }

    /**
     * Give items to player
     *
     * @param player Player
     */
    public static void giveItem(Player player) {
        player.getInventory().clear();
        player.getInventory().addItem(new ItemBuilder(Settings.getMaterial("sword")).setName(Settings.getName("sword")).toItemStack());
        player.getInventory().addItem(new ItemBuilder(Settings.getMaterial("bow")).setName(Settings.getName("bow")).toItemStack());
        player.getInventory().addItem(new ItemBuilder(Settings.getMaterial("arrow")).setName(Settings.getName("arrow")).toItemStack());
    }

    /**
     * Check if game can start
     *
     * @return Boolean
     */
    public boolean canStart() {
        return Bukkit.getOnlinePlayers().size() == 2 && GameStatusManager.isStatus(GameStatus.WAITING);
    }

    /**
     * Start the game with parameter
     */
    public void startGame() {
        new BukkitRunnable() {
            private int timer = Settings.getTimerBeforeStart();

            @Override
            public void run() {
                if (timer == 0) {
                    if (!canStart()) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(MessageUtils.getMessage("not_enough_player", true, "%minplayer%", String.valueOf(Settings.getMinPlayers())));
                            cancel();
                        }
                    }
                    GameStatusManager.setStatus(GameStatus.GAME);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.teleport(getRandomLocation());
                        player.playSound(player, Settings.getSound(), 1, 1);
                        player.sendTitle(MessageUtils.getMessage("title.start.title_after_timer", false),
                                MessageUtils.getMessage("title.start.subtitle_after_timer", false),
                                0, 40, 20);
                        giveItem(player);
                    }
                    TimerArrow timerArrow = new TimerArrow();
                    timerArrow.runTaskTimer(Oneshot.getInstance(), 20, 20);
                    reloadScoreboard();
                    cancel();
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle(MessageUtils.getMessage("title.start.title", false, "%time%", String.valueOf(timer)),
                            MessageUtils.getMessage("title.start.subtitle", false, "%time%", String.valueOf(timer)),
                            0, 40, 20);
                }
                timer--;
            }
        }.runTaskTimer(Oneshot.getInstance(), 20, 20);
    }

    /**
     * Get random location to teleport player
     *
     * @return Location
     */
    public Location getRandomLocation() {
        LocationsFile lf = (LocationsFile) FileManager.getFile("locations");
        if (lf == null)
            return null;
        ArrayList<Location> locations = new ArrayList<>();
        lf.getLocations().forEach(loc -> locations.add(LocationsUtils.getParsedLocation(loc)));
        Random random = new Random();
        int index = random.nextInt(locations.size());
        if (oldLocationSelected == null)
            return locations.get(index);
        else {
            Location loc = locations.get(index);
            while (oldLocationSelected == loc) {
                index = random.nextInt(locations.size());
                loc = locations.get(index);
            }
            oldLocationSelected = loc;
            return loc;
        }
    }

    /**
     * Get the list of playerStats
     *
     * @return ArrayList<PlayerStats>
     */
    public ArrayList<PlayerStats> getStatsOfPlayers() {
        return playersStats;
    }

    /**
     * Save all stats for player
     */
    public void saveStats() {

    }

    public void reloadScoreboard() {
        sm.reloadBoard();
    }

    public ScoreboardManager getScoreboards() {
        return sm;
    }
}
