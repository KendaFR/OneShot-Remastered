package fr.kenda.oneshot.file;

import org.bukkit.configuration.file.YamlConfiguration;

public interface IFile {

    /**
     * Add default value
     */
    void addDefaults();

    /**
     * Get the config file
     *
     * @return YamlConfiguration
     */
    YamlConfiguration getConfig();

    /**
     * Reload the configuration
     *
     * @return Boolean
     */
    boolean reloadConfig();
}
