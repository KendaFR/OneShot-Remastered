package fr.kenda.oneshot.gamestatus;

public class GameStatusManager {

    private static GameStatus gStatus = GameStatus.WAITING;

    public static boolean isStatus(GameStatus status) {
        return gStatus == status;
    }

    public static String getStatus() {
        return gStatus.name().toLowerCase();
    }

    public static void setStatus(GameStatus status) {
        gStatus = status;
    }
}
