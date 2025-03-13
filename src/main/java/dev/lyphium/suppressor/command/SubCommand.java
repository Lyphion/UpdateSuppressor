package dev.lyphium.suppressor.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Command with is part of another one.
 */
public interface SubCommand {

    /**
     * Handler when executing the sub command.
     *
     * @param sender Executor of the command
     * @param args   Additional parameter of the command
     * @return {@code true} if command was executed successfully.
     */
    boolean handleCommand(@NotNull CommandSender sender, @NotNull String @NotNull [] args);

    /**
     * Handler for receiving tab completion list.
     *
     * @param sender Executor of the command
     * @param args   Additional parameter of the command
     * @return List of possible completions or {@code null} for online players.
     */
    @Nullable List<String> handleTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args);

}
