package fr.kenda.oneshot.utils;

import fr.kenda.oneshot.Oneshot;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

@SuppressWarnings({"all"})
public class MessageUtils {
    private static final YamlConfiguration CONFIG = (YamlConfiguration) Oneshot.getInstance().getConfig();

    /**
     * Get the prefix in config.yml
     *
     * @return String
     */
    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', CONFIG.getString("prefix")) + " ";
    }

    /**
     * Get the message in path
     *
     * @param path String
     * @return String
     */
    public static String getMessage(String path, boolean showPrefix) {
        return (showPrefix ? getPrefix() : "") + ChatColor.translateAlternateColorCodes('&', CONFIG.getString("messages." + path));
    }

    /**
     * Get the message in config.yml with path and replace the placeholder
     *
     * @param path String
     * @param args String...
     * @return String
     */
    public static String getMessage(String path, boolean showPrefix, String... args) {
        String str = ChatColor.translateAlternateColorCodes('&', CONFIG.getString("messages." + path));
        int size = args.length;
        if (size % 2 == 0) {
            for (int i = 0; i < size; i += 2) {
                str = str.replace(args[i], args[i + 1]);
            }
        }
        return (showPrefix ? getPrefix() : "") + str;
    }

    /**
     * Get the permission in config.yml
     *
     * @param path String
     * @return String
     */
    public static String getPermission(String path) {
        return CONFIG.getString("permissions." + path);
    }

    public static ArrayList<String> getBoardLines(String path, String... args) {
        ArrayList<String> lines = new ArrayList<>(CONFIG.getStringList("messages." + path));
        int sizeList = lines.size();
        for (int i = 0; i < sizeList; i++)
            lines.set(i, ChatColor.translateAlternateColorCodes('&', lines.get(i)));
        int size = args.length;
        if (size == 0)
            return lines;
        else if (size % 2 == 0)
            for (int i = 0; i < sizeList; i++)
                for (int arg = 0; arg < size; arg += 2)
                    lines.set(i, lines.get(i).replace(args[arg], args[arg + 1]));
        return lines;
    }
}