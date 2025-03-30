package de.eindaniel.moderation.commands;

import de.eindaniel.moderation.Main;
import de.eindaniel.moderation.config.Players;
import de.eindaniel.moderation.utils.MessageUtils;
import de.eindaniel.moderation.utils.MessageUtilsSingleton;
import io.papermc.paper.ban.BanListType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Collections;

public class BanCMD extends Command {
    private Players players;
    private Main plugin;

    public BanCMD(Main plugin, Players players) {
        super("ban");
        setAliases(Collections.singletonList("banplayer"));
        this.players = players;
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player player;
        MessageUtils messageUtils = MessageUtilsSingleton.getInstance();
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        } else {
            commandSender.sendMessage(Component.text("You must be a player to use this command"));
            return false;
        }

        if (!player.hasPermission("moderation.ban")) {
            return false;
        }

        if (strings.length < 1) {
            messageUtils.sendMessage(player, "ban.usage");
            return false;
        }

        if (strings.length > 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(strings[0]);
            // TODO Add bypass
//            if (target.getPlayer().hasPermission("moderation.ban.bypass")) {
//                messageUtils.sendMessage(player, "bypass.ban");
//                return false;
//            }
            String reason = strings[1];
            banPlayer(player, target, reason);
            messageUtils.sendMessage(player, "ban.success", target.getName(), reason);
            if (plugin.getConfig().get("broadcast-moderation").equals("everyone")) {
                messageUtils.broadcastMessage("ban.broadcast", target.getName(), reason, player.getName());
            } if (plugin.getConfig().get("broadcast-moderation").equals("permission")) {
                messageUtils.broadcastMessagePermission("ban.broadcast", "moderation.broadcast", target.getName(), reason, player.getName());
            }
        }

        return false;
    }

    public void banPlayer(OfflinePlayer mod, OfflinePlayer target, String reason) {
        MessageUtils messageUtils = MessageUtilsSingleton.getInstance();
        Duration expire = null;
        if (reason == null) {
            reason = "Reason not given";
        }
        Bukkit.getBanList(BanListType.PROFILE).addBan(target.getPlayerProfile(), reason, expire, mod.getName());
        if (target.isOnline()) {
            target.getPlayer().kick(messageUtils.getMessage("ban.kicked", reason, "Permanent"));
        }
        players.addBan(target, mod, reason);
    }
}
