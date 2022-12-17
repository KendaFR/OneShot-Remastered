package fr.kenda.oneshot.file.File;

import fr.kenda.oneshot.Oneshot;
import fr.kenda.oneshot.file.IFile;
import fr.kenda.oneshot.managers.FileManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomFile implements IFile {

    private static final Oneshot instance = Oneshot.getInstance();
    private static final File dataFolder = instance.getDataFolder();
    private final String fileName;

    /**
     * Custom File constructor
     *
     * @param folder   String (folder name or null)
     * @param fileName the file name
     */
    public CustomFile(String folder, String fileName) {
        this.fileName = fileName;
        final String fileExtension = fileName + ".yml";
        File file;
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        //check if folder is not null
        if (folder != null) {
            //get the file at dataFolder and name
            File fileFolder = new File(dataFolder, folder);
            //create the folder
            if (!fileFolder.exists()) {
                fileFolder.mkdir();
            }
            //Get file at dataFolder and subFolder
            file = new File(dataFolder + "/" + folder, fileExtension);
        } else
            file = new File(dataFolder, fileExtension);

        //Create file
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Add this file in FileManager
        FileManager.addFile(fileName, this);
    }

    /**
     * Get the file with folder and name
     *
     * @param folder   String
     * @param fileName String
     * @return File
     */
    public static File getFile(String folder, String fileName) {
        if (folder != null)
            return new File(dataFolder + "/" + folder, fileName + ".yml");
        else
            return new File(dataFolder, fileName + ".yml");
    }

    /**
     * Get name of file
     *
     * @return String
     */
    public String getName() {
        return fileName;
    }

    /**
     * Add default value at file
     */
    @Override
    public void addDefaults() {

    }

    /**
     * Get the config of file
     *
     * @return YamlConfiguration
     */
    @Override
    public YamlConfiguration getConfig() {
        return null;
    }

    /**
     * Reload the configuration of file
     *
     * @return Boolean
     */
    @Override
    public boolean reloadConfig() {
        return false;
    }
}