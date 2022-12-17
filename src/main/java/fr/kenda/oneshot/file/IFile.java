package fr.kenda.oneshot.file;

import org.bukkit.configuration.file.YamlConfiguration;

public interface IFile {

    /**
     * Add default value
     */
    public void addDefaults();

    /**
     * Get the config file
     * @return YamlConfiguration
     */
    public YamlConfiguration getConfig();

    /**
     * Reload the configuration
     * @return Boolean
     */
    public boolean reloadConfig();
}
