package fr.kenda.oneshot.gamestatus;

public class GameStatusManager {

    private static GameStatus gStatus = GameStatus.WAITING;

    /**
     * Check is status is same with parameter
     * @param status GameStatuc
     * @return Boolean
     */
    public static boolean isStatus(GameStatus status) {
        return gStatus == status;
    }

    /**
     * Get the status name
     * @return String
     */
    public static String getStatus() {
        return gStatus.name().toLowerCase();
    }

    /**
     * Set the new status
     * @param status GameStatus
     */
    public static void setStatus(GameStatus status) {
        gStatus = status;
    }
}
