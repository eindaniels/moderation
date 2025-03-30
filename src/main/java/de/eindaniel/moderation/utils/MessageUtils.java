package de.eindaniel.moderation.utils;

import com.google.common.xml.XmlEscapers;
import de.eindaniel.moderation.Main;
import de.eindaniel.moderation.config.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageUtils {
    private Messages messages;

    public MessageUtils(Main plugin) {
        messages = plugin.getMessages();
    }

    public void sendMessage(Player player, String key, Object... args) {
        Component message = messages.getMessage(key, args);
        player.sendMessage(message);
    }

    public void broadcastMessage(String key, Object... args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendMessage(p, key, args);
        }
    }

    public void broadcastMessagePermission(String key, String permission, Object... args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission(permission)) {
                sendMessage(p, key, args);
            }
        }
    }
    public Component getMessage(String key, Object... args) {
        Component message = messages.getMessage(key, args);
        return message;
    }
}
