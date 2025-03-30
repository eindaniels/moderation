package de.eindaniel.moderation;

import de.eindaniel.moderation.commands.BanCMD;
import de.eindaniel.moderation.commands.Unban;
import de.eindaniel.moderation.config.Messages;
import de.eindaniel.moderation.config.Players;
import de.eindaniel.moderation.utils.MessageUtils;
import de.eindaniel.moderation.utils.MessageUtilsSingleton;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Main extends JavaPlugin {
    private Messages messages;
    private Players players;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        getLogger().info("Thanks for using my plugin! Made by @eindaniels");
        messages = new Messages(this);
        players = new Players(this);
        MessageUtilsSingleton.initialize(this);

        registerCommands();
        registerCommands();
    }

    @Override
    public void onDisable() {
    }

    public void registerCommands() {
        CommandMap commandMap = getServer().getCommandMap();
        String pluginName = "moderation";
        commandMap.register(pluginName, new BanCMD(this, players));
        commandMap.register(pluginName, new Unban());
    }

    public void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
    }

    public Messages getMessages() {
        return messages;
    }

    @Override
    public FileConfiguration getConfig() {
        return super.getConfig();
    }
    public Players getPlayers() {
        return players;
    }
}
