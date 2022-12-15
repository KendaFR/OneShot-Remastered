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

    public CustomFile(String folder, String fileName) {
        this.fileName = fileName;
        final String fileExtension = fileName + ".yml";
        File file;
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        if (folder != null) {
            File fileFolder = new File(dataFolder, folder);
            if (!fileFolder.exists()) {
                fileFolder.mkdir();
            }
            file = new File(dataFolder + "/" + folder, fileExtension);
        } else
            file = new File(dataFolder, fileExtension);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileManager.addFile(fileName, this);
    }

    public String getName() {
        return fileName;
    }

    public static File getFile(String folder, String fileName) {
        if (folder != null)
            return new File(dataFolder + "/" + folder, fileName + ".yml");
        else
            return new File(dataFolder, fileName + ".yml");
    }

    @Override
    public void addDefaults() {

    }

    @Override
    public YamlConfiguration getConfig() {
        return null;
    }

    @Override
    public boolean reloadConfig() {
        return false;
    }
}