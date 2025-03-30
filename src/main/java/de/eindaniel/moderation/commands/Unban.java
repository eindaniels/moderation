package de.eindaniel.moderation.commands;

import de.eindaniel.moderation.utils.MessageUtils;
import de.eindaniel.moderation.utils.MessageUtilsSingleton;
import io.papermc.paper.ban.BanListType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.print.attribute.standard.MediaSize;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Unban extends Command {
    public Unban() {
        super("unban");
        setAliases(Collections.singletonList("pardon"));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String @NotNull [] strings) {
        MessageUtils messageUtils = MessageUtilsSingleton.getInstance();
        if (!commandSender.hasPermission("moderation.unban")) {
            messageUtils.sendMessage((Player) commandSender, "noperm");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(strings[0]);
        if (target.isBanned()) {
            Bukkit.getBanList(BanListType.PROFILE).pardon(target.getPlayerProfile());
            messageUtils.sendMessage((Player) commandSender, "unban.success");
        }
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        if (args.length == 0) {
            return Bukkit.getBannedPlayers().stream().map(OfflinePlayer::getName).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
