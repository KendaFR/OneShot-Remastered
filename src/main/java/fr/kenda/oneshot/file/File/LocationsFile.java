package fr.kenda.oneshot.file.File;

import fr.kenda.oneshot.utils.EFiles;
import fr.kenda.oneshot.utils.LocationsUtils;
import fr.kenda.oneshot.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationsFile extends CustomFile {

    private final String folder = EFiles.LOCATIONS.getFolder();
    private final String fileName = EFiles.LOCATIONS.getFileName();

    /**
     * Create locationsFile
     */
    public LocationsFile() {
        super("locations", "locations");
    }

    /**
     * Add default value
     */
    @Override
    public void addDefaults() {
        final File file = getFile(folder, fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);
        config.addDefault("locations.spawn", "world,0,0,0,0,0");

        config.addDefault("locations.list", List.of("world,0,0,0,0,0"));
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get config of file
     *
     * @return YamlConfiguration
     */
    @Override
    public YamlConfiguration getConfig() {
        final File file = getFile(folder, fileName);
        return YamlConfiguration.loadConfiguration(file);

    }

    /**
     * Get the size of spawns
     *
     * @return Integer
     */
    public int getNumberOfPosition() {
        YamlConfiguration config = getConfig();
        return config.getConfigurationSection("locations").getKeys(false).size() - 1;
    }

    /**
     * Get all locations
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getLocations() {
        return (ArrayList<String>) getConfig().getStringList("locations.list");

    }

    /**
     * Get the location at index
     *
     * @param index Integer
     * @return Location
     */
    public Location getLocation(int index) {
        ArrayList<String> locations = getLocations();
        return LocationsUtils.getParsedLocation(locations.get(index));
    }

    private void setList(ArrayList<String> list) {
        getConfig().set("locations.list", list);
    }

    /**
     * Save the configuration
     *
     * @param config YamlConfiguration
     * @return Boolean
     */
    public boolean saveConfig(YamlConfiguration config) {
        try {
            File file = getFile(folder, fileName);
            config.save(file);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add a new location in locations
     *
     * @param player Player
     */
    public void addLocation(Player player) {
        //TODO A corriger car sa marche pas
        YamlConfiguration config = getConfig();
        String location = LocationsUtils.locationParse(player.getLocation());
        ArrayList<String> locations = getLocations();
        locations.add(location);
        config.set("locations.list", locations);
        if (saveConfig(config))
            player.sendMessage(MessageUtils.getMessage("locations.add", "%location%", location));
        else
            player.sendMessage(ChatColor.RED + "An error was occured during add location in config file");

    }

    /**
     * Remove the location at index or last location
     *
     * @param player Player
     * @param index  Integer
     */
    public void removeLocation(Player player, int index) {
        //TODO A testé
        ArrayList<String> locations = getLocations();
        YamlConfiguration config = getConfig();
        int number = getNumberOfPosition();
        if (number == 0) {
            player.sendMessage(MessageUtils.getMessage("locations.no_location"));
            return;
        }
        int size = locations.size();
        String locationStr;

        //check if player whould remove location at index
        if (index == -1) {
            locationStr = locations.get(size - 1);
            locations.remove(size - 1);
            setList(locations);
        } else {
            if (index < 0 || index > size) {
                player.sendMessage(ChatColor.RED + "Index out of bound. Please retry between 0 and " + size);
                return;
            }
            locationStr = locations.get(index);
            locations.remove(index);

        }
        if (saveConfig(config))
            player.sendMessage(MessageUtils.getMessage("locations.remove", "%number%", String.valueOf(number), "%location%", locationStr));
        else
            player.sendMessage(ChatColor.RED + "An error was occured during removed location in config file");

    }

    /**
     * Clear all location
     *
     * @param player Player
     */
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

    /**
     * Set the new spawn point
     *
     * @param player Player
     */
    public void setSpawn(Player player) {
        YamlConfiguration config = getConfig();
        String location = LocationsUtils.locationParse(player.getLocation());
        config.set("locations.spawn", location);
        saveConfig(config);
        player.sendMessage(MessageUtils.getMessage("locations.setspawn", "%location%", location));
    }

    /**
     * Reload the configuration
     *
     * @return Boolean
     */
    @Override
    public boolean reloadConfig() {
        return saveConfig(getConfig());
    }
}