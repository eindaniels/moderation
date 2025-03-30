package de.eindaniel.moderation.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class Messages {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public Messages(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public Component getMessage(String key, Object... args) {
        if (config.getString(key) != null) {
            MiniMessage miniMessage = MiniMessage.miniMessage();
            String messageTemplate = config.getString(key);
            String formattedMessage = MessageFormat.format(messageTemplate, args);
            Component prefix = miniMessage.deserialize(config.getString("prefix"));
            Component message = miniMessage.deserialize(formattedMessage);
            return prefix.append(message);
        } else {
            return Component.text("Key not found: " + key + "! Report this to https://github.com/eindaniels/moderation").color(TextColor.color(0xFF1A1E));
        }
    }

    public Component getMessageWithoutPrefix(String key) {
        if (key != null) {
            MiniMessage miniMessage = MiniMessage.miniMessage();
            Component message = miniMessage.deserialize(config.getString(key));
            return message;
        } else {
            return Component.text("Key not found: " + key + "! Report this to https://github.com/eindaniels/moderation").color(TextColor.color(0xFF1A1E));
        }
    }
}
