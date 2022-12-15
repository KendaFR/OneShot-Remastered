package fr.kenda.oneshot.file.File;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.managers.FileManager;
import fr.kenda.oneshot.utils.EFiles;
import fr.kenda.oneshot.utils.LocationsUtils;
import fr.kenda.oneshot.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class LocationsFile extends CustomFile {

    private final String folder = EFiles.LOCATIONS.getFolder();
    private final String fileName = EFiles.LOCATIONS.getFileName();

    public LocationsFile() {
        super("locations", "locations");
    }

    @Override
    public void addDefaults() {
        final File file = getFile(folder, fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);
        config.addDefault("locations.spawn", "world,0,0,0,0,0");

        config.addDefault("locations.1", "world,0,0,0,0,0");
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public YamlConfiguration getConfig() {
        final File file = getFile(folder, fileName);
        return YamlConfiguration.loadConfiguration(file);

    }

    public int getNumberOfPosition() {
        YamlConfiguration config = getConfig();
        return config.getConfigurationSection("locations").getKeys(false).size() - 1;
    }

    public String getLocationString(int number) {
        YamlConfiguration config = getConfig();
        return config.getString("locations." + number);
    }

    public Location getLocation(int number) {
        //TODO make return location in file
        return null;
    }

    public boolean saveConfig(YamlConfiguration config) {
        try {
            File file = getFile(folder, fileName);
            config.save(file);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLocation(Player player) {
        YamlConfiguration config = getConfig();
        String location = LocationsUtils.locationParse(player.getLocation());
        int number = getNumberOfPosition() + 1;
        config.set("locations." + number, location);
        if (saveConfig(config))
            player.sendMessage(MessageUtils.getMessage("locations.add", "%location%", location));
        else
            player.sendMessage(ChatColor.RED + "An error was occured during add location in config file");

    }

    public void removeLocation(Player player) {
        YamlConfiguration config = getConfig();
        int number = getNumberOfPosition();
        if (number != 0) {
            String location = getLocationString(number);
            config.set("locations." + number, null);
            if (saveConfig(config))
                player.sendMessage(MessageUtils.getMessage("locations.remove", "%number%", String.valueOf(number), "%location%", location));
            else
                player.sendMessage(ChatColor.RED + "An error was occured during removed location in config file");
        } else
            player.sendMessage(MessageUtils.getMessage("locations.no_location"));
    }

    public void clearLocations(Player player) {
        YamlConfiguration config = getConfig();
        if (getNumberOfPosition() == 0) {
            player.sendMessage(MessageUtils.getMessage("locations.no_location"));
            return;
        }
        config.getConfigurationSection("locations").getKeys(false)
                .forEach(key -> {
                    if (!key.equalsIgnoreCase("spawn"))
                        config.set("locations." + key, null);
                });
        if (saveConfig(config))
            player.sendMessage(MessageUtils.getMessage("locations.clear"));
        else
            player.sendMessage(ChatColor.RED + "An error was occured during clearing all locations");
    }

    public void setSpawn(Player player) {
        YamlConfiguration config = getConfig();
        String location = LocationsUtils.locationParse(player.getLocation());
        config.set("locations.spawn", location);
        saveConfig(config);
        player.sendMessage(MessageUtils.getMessage("locations.setspawn", "%location%", location));
    }

    @Override
    public boolean reloadConfig() {
        Oneshot.getInstance().reloadConfig();
        YamlConfiguration config = getConfig();
        return saveConfig(config);
    }
}
