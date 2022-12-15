package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.file.File.CustomFile;
import fr.kenda.oneshot.file.File.LocationsFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;

public class FileManager implements IManager {

    private static final HashMap<String, CustomFile> files = new HashMap<>();

    public static void addFile(String shortcut, CustomFile file) {
        files.put(shortcut, file);
    }

    public static YamlConfiguration getConfig(String file) {
        return files.get(file).getConfig();
    }
    public static ArrayList<CustomFile> getFiles() {
        return new ArrayList<>(files.values());
    }

    public static CustomFile getFile(String file) {
        return files.get(file);
    }

    @Override
    public void register() {
        new LocationsFile();
        for (CustomFile file : files.values()) {
            file.addDefaults();
        }
    }
}
