package fr.kenda.oneshot.utils;

import fr.kenda.oneshot.Oneshot;
import org.bukkit.ChatColor;

public class MessageUtils {

    private static Oneshot instance = Oneshot.getInstance();

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("prefix"));
    }

    public static String getMessage(String path) {
        return getPrefix() + ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("messages." + path));
    }

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
}