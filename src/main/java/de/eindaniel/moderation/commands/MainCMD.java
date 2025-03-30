package de.eindaniel.moderation.commands;

import de.eindaniel.moderation.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MainCMD extends Command {
    private Main plugin;

    public MainCMD(Main plugin) {
        super("moderation");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String @NotNull [] strings) {
        return false;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        if (args.length == 0) {
            return List.of("reload", "version", "info");
        } if (args.length == 1 && args[0].equals("reload")) {
            return List.of("config", "messages", "punished-players");
        }
        return Collections.emptyList();
    }
}
