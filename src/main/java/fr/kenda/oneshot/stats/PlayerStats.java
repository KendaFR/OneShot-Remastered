package fr.kenda.oneshot.stats;

import org.bukkit.entity.Player;

public class PlayerStats {

    private final Player player;
    private int kills;
    private int deaths;

    private int gameKills;
    private int gameDeaths;

    public PlayerStats(Player player, int kills, int deaths) {

        this.player = player;
        this.kills = kills;
        this.deaths = deaths;
        gameKills = 0;
        gameDeaths = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getGameKills() {
        return gameKills;
    }

    public void addGameKill() {
        kills++;
        gameKills++;
    }

    public int getGameDeaths() {
        return gameDeaths;
    }

    public void addGameDeath() {
        deaths++;
        gameDeaths++;
        gameKills = gameKills > 0 ? gameKills - 1 : 0;
    }
}
