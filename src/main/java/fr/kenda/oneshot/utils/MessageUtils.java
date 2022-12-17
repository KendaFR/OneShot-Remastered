package fr.kenda.oneshot.utils;

import fr.kenda.oneshot.Oneshot;
import org.bukkit.ChatColor;

public class MessageUtils {

    private static final Oneshot instance = Oneshot.getInstance();

    /**
     * Get the prefix in config.yml
     *
     * @return String
     */
    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("prefix")) + " ";
    }

    /**
     * Get the message in path
     *
     * @param path String
     * @return String
     */
    public static String getMessage(String path) {
        return getPrefix() + ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("messages." + path));
    }

    /**
     * Get the message in config.yml with path and replace the placeholder
     *
     * @param path String
     * @param args String...
     * @return String
     */
    public static String getMessage(String path, String... args) {
        String str = ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("messages." + path));
        int size = args.length;
        if (size % 2 == 0) {
            for (int i = 0; i < size; i += 2) {
                str = str.replace(args[i], args[i + 1]);
            }
        }
        return getPrefix() + str;
    }

    /**
     * Get the permission in config.yml
     *
     * @param path String
     * @return String
     */
    public static String getPermission(String path) {
        return instance.getConfig().getString("permissions." + path);
    }
}
