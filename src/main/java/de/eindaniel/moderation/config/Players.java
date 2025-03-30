package de.eindaniel.moderation.config;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Players {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public Players(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "players.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("players.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void addBan(OfflinePlayer target, OfflinePlayer mod , String reason) {
        config.set(target.getUniqueId() + ".ban", true);
        config.set(target.getUniqueId() + ".banreason", reason);
        config.set(target.getUniqueId() + ".banmoderator", mod.getName());
        saveConfig();
    }

    public void removeBan(OfflinePlayer target) {
        config.set(target.getUniqueId() + ".ban", null);
        config.set(target.getUniqueId() + ".banreason", null);
        config.set(target.getUniqueId() + ".banmoderator", null);
        saveConfig();
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
