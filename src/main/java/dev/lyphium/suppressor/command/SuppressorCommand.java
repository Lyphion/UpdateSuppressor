package dev.lyphium.suppressor.command;

import dev.lyphium.suppressor.manager.RegionManager;
import dev.lyphium.suppressor.util.ColorConstants;
import dev.lyphium.suppressor.util.TextConstants;
import lombok.AccessLevel;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Main command for managing the plugin.
 */
public final class SuppressorCommand implements CommandExecutor, TabCompleter {

    /**
     * Collection of all available sub commands.
     */
    @Getter(AccessLevel.PACKAGE)
    private final Map<String, SubCommand> subCommands;

    public SuppressorCommand(@NotNull RegionManager regionManager) {
        this.subCommands = new TreeMap<>(Map.of(
                "add", new SuppressorAddCommand(regionManager),
                "edit", new SuppressorEditCommand(regionManager),
                "reload", new SuppressorReloadCommand(regionManager),
                "remove", new SuppressorRemoveCommand(regionManager),
                "toggle", new SuppressorToggleCommand(regionManager)
        ));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        // Missing sub command
        if (args.length == 0) {
            sender.sendMessage(errorMessage("help"));
            return true;
        }

        // Check if sub command exists
        final String name = args[0].toLowerCase();
        if (!subCommands.containsKey(name)) {
            sender.sendMessage(errorMessage("help"));
            return true;
        }

        final SubCommand subCommand = subCommands.get(name);

        // Run subcommand
        final String[] remaining = Arrays.copyOfRange(args, 1, args.length);
        final boolean success = subCommand.handleCommand(sender, remaining);

        // If something bad happened, print error/help message for command
        if (!success) {
            sender.sendMessage(errorMessage(name));
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        final String name = args[0].toLowerCase();

        // Find matching subcommands
        if (args.length == 1) {
            return subCommands.keySet()
                    .stream()
                    .filter(subCommand -> subCommand.startsWith(name))
                    .toList();
        }

        // Check if sub command exists
        if (!subCommands.containsKey(name)) {
            return List.of();
        }

        // Get completion from sub command
        final SubCommand subCommand = subCommands.get(name);
        final String[] remaining = Arrays.copyOfRange(args, 1, args.length);
        return subCommand.handleTabComplete(sender, remaining);
    }

    /**
     * Set this object as an executor and tab completer for the command
     *
     * @param command Command to be handled.
     */
    public void register(@NotNull PluginCommand command) {
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    /**
     * Get error/help message for the command.
     *
     * @param label Used label of the command
     * @return Component with formated message.
     */
    private Component errorMessage(@NotNull String label) {
        final String name = label.toLowerCase();
        final String key = "command.suppressor." + name + ".usage";

        return TextConstants.PREFIX
                .append(Component.translatable("command.suppressor.error.wrong_usage", ColorConstants.ERROR, Component.translatable(key)));
    }

}
