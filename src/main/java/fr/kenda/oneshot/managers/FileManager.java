package fr.kenda.oneshot.managers;

import fr.kenda.oneshot.file.file.CustomFile;
import fr.kenda.oneshot.file.file.LocationsFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

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
        CustomFile cf = files.get(file);
        if (cf != null)
            return cf;
        else
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "An error was occured. " + file + " was not found");
        return null;
    }

    /**
     * Register all files
     */
    @Override
    public void register() {
        new LocationsFile("locations", "locations");
    }


}