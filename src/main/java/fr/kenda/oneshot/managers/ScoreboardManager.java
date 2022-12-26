package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.stats.PlayerStats;
import fr.kenda.oneshot.utils.FastBoard;
import fr.kenda.oneshot.utils.MessageUtils;
import fr.kenda.oneshot.utils.Settings;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager implements IManager {

    private static Map<Player, FastBoard> boards;
    private final GameManager gm;

    ScoreboardManager(GameManager gm) {
        this.gm = gm;
        register();
    }

    @Override
    public void register() {
        boards = new HashMap<>();
    }

    private FastBoard addBoard(final Player player) {
        FastBoard board = boards.get(player);
        if (board != null)
            return board;
        else {
            boards.put(player, new FastBoard(player));
            return boards.get(player);
        }
    }

    public void removeBoard(final Player player) {
        final FastBoard board = boards.remove(player);
        if (board != null) boards.get(player).delete();
    }

    public void setSpawnBoard(Player p1, Player p2) {
        PlayerStats statsP1 = GameManager.getStatsOfPlayer(p1);
        PlayerStats statsP2 = GameManager.getStatsOfPlayer(p2);

        FastBoard board = addBoard(p1);

        board.updateTitle(MessageUtils.getMessage("board.title", false, "%prefix%", MessageUtils.getPrefix()));

        clearBoard(board);

        ArrayList<String> lines = MessageUtils.getBoardLines("board.before_start",
                "%player1%", p1.getName(),
                "%kills_player1%", String.valueOf(statsP1.getKills()),
                "%death_player1%", String.valueOf(statsP1.getDeaths()),
                "%player2%", p2.getName(),
                "%kills_player2%", String.valueOf(statsP2.getKills()),
                "%death_player2%", String.valueOf(statsP2.getDeaths()));
        board.updateLines(lines);
    }

    public void setWaitingBoard(Player player) {

        FastBoard board = addBoard(player);

        board.updateTitle(MessageUtils.getMessage("board.title", false, "%prefix%", MessageUtils.getPrefix()));

        clearBoard(board);

        ArrayList<String> lines = MessageUtils.getBoardLines("board.waiting_players");
        board.updateLines(lines);
    }

    public void setGameBoard(Player p1, Player p2) {
        PlayerStats statsP1 = GameManager.getStatsOfPlayer(p1);
        PlayerStats statsP2 = GameManager.getStatsOfPlayer(p2);
        FastBoard board = addBoard(p1);

        board.updateTitle(MessageUtils.getMessage("board.title", false, "%prefix%", MessageUtils.getPrefix()));

        clearBoard(board);

        ArrayList<String> lines = MessageUtils.getBoardLines("board.game",
                "%player1%", statsP1.getPlayer().getName(),
                "%kills_player1%", String.valueOf(statsP1.getGameKills()),
                "%death_player1%", String.valueOf(statsP1.getGameDeaths()),
                "%player2%", statsP2.getPlayer().getName(),
                "%kills_player2%", String.valueOf(statsP2.getGameKills()),
                "%death_player2%", String.valueOf(statsP2.getGameDeaths()),
                "%killstreak%", String.valueOf(Settings.getKillStreak()));
        board.updateLines(lines);
    }

    public void reloadBoard() {
        Player p1 = gm.getStatsOfPlayers().get(0).getPlayer();
        Player p2 = gm.getStatsOfPlayers().get(1).getPlayer();
        setGameBoard(p1, p2);
        setGameBoard(p2, p1);
    }

    public void clearBoard(FastBoard board) {
        int size = board.getLines().size();
        for (int i = 0; i < size; i++) {
            board.removeLine(i);
        }
    }
}
