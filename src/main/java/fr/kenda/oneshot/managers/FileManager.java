package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.file.File.CustomFile;
import fr.kenda.oneshot.file.File.LocationsFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;

public class FileManager implements IManager {

    private static final HashMap<String, CustomFile> files = new HashMap<>();

    public static void addFile(String shortcut, CustomFile file) {
        files.put(shortcut, file);
    }

    public static YamlConfiguration getConfig(String file) {
        return files.get(file).getConfig();
    }

    @Override
    public void register() {
        new LocationsFile();
        for (CustomFile file : files.values()) {
            file.addDefaults();
        }
    }
}
