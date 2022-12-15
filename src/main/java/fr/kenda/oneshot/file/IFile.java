package fr.kenda.oneshot.file;

import org.bukkit.configuration.file.YamlConfiguration;

public interface IFile {

    public void addDefaults();

    public YamlConfiguration getConfig();
    public boolean reloadConfig();
}
