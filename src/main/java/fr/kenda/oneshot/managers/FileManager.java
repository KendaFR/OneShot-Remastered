package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.file.File.CustomFile;
import fr.kenda.oneshot.file.File.LocationsFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

public class FileManager implements IManager {

    private static final HashMap<String, CustomFile> files = new HashMap<>();

    /**
     * Add file in map with shortcut (easy to get the file custom)
     *
     * @param shortcut String
     * @param file     CustomFile
     */
    public static void addFile(String shortcut, CustomFile file) {
        files.put(shortcut, file);
    }

    /**
     * Get config file form file name
     *
     * @param file String
     * @return YamlConfiguration
     */
    public static YamlConfiguration getConfig(String file) {
        return files.get(file).getConfig();
    }

    /**
     * Get all files
     *
     * @return ArrayList<CustomFile>
     */
    public static ArrayList<CustomFile> getFiles() {
        return new ArrayList<>(files.values());
    }

    /**
     * Get the file
     *
     * @param file String
     * @return CustomFile
     */
    public static CustomFile getFile(String file) {
        return files.get(file);
    }

    /**
     * Register all files
     */
    @Override
    public void register() {
        new LocationsFile();

    }
}
