package fr.kenda.oneshot.utils;

import fr.kenda.oneshot.Oneshot;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings({"all"})
public class Settings {


    private static final YamlConfiguration CONFIG = (YamlConfiguration) Oneshot.getInstance().getConfig();

    public static int getMinPlayers() {
        return CONFIG.getInt("settings.minplayers");
    }

    public static int getTimerBeforeStart() {
        return CONFIG.getInt("settings.timer_before_start");
    }

    public static Sound getSound() {
        return Sound.valueOf(CONFIG.getString("settings.sound").toUpperCase());
    }

    public static String getName(String item) {
        return ChatColor.translateAlternateColorCodes('&', CONFIG.getString("settings.items." + item + "_name"));
    }

    public static Material getMaterial(String item) {
        return Material.valueOf(CONFIG.getString("settings.items." + item + "_material"));

    }

    public static int getSlot(String item) {
        return CONFIG.getInt("settings.items." + item + "_slot");
    }

    public static int getKillStreak() {
        return CONFIG.getInt("settings.killstreak_win");
    }

    public static int getTimerGivenArrow() {
        return CONFIG.getInt("settings.timer_given_arrow");
    }

    public static int getMaxArrow() {
        return CONFIG.getInt("settings.items.max_arrow");
    }

    public static int getTimerRespawn() {
        return CONFIG.getInt("settings.respawn");
    }

    public static Sound getKilledSound() {
        return Sound.valueOf(CONFIG.getString("settings.killed_sound").toUpperCase());
    }
}