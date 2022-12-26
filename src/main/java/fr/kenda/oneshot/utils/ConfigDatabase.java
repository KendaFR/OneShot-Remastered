package fr.kenda.oneshot.utils;

import fr.kenda.oneshot.Oneshot;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigDatabase {

    private static final YamlConfiguration CONFIG = (YamlConfiguration) Oneshot.getInstance().getConfig();

    public static String getProcessSave() {
        return CONFIG.getString("statistics.database_system");
    }

    public static String getHost() {
        return CONFIG.getString("statistics.host");
    }

    public static String getUser() {
        return CONFIG.getString("statistics.user");
    }

    public static String getPassword() {
        return CONFIG.getString("statistics.password");
    }

    public static String getPort() {
        return CONFIG.getString("statistics.port");
    }

    public static String getDatabase() {
        return CONFIG.getString("statistics.database");
    }
}