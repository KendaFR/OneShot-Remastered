package fr.kenda.oneshot.file.File;

import fr.kenda.oneshot.utils.EFiles;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LocationsFile extends CustomFile {

    private final String folder= EFiles.LOCATIONS.getFolder();;
    private final String fileName = EFiles.LOCATIONS.getFileName();;
    public LocationsFile() {
        super("locations", "locations");
    }

    @Override
    public void addDefaults() {
        final File file = getFile(folder, fileName);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);
        config.addDefault("locations.spawn.x", 0);
        config.addDefault("locations.spawn.y", 100);
        config.addDefault("locations.spawn.z", 0);
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
}
