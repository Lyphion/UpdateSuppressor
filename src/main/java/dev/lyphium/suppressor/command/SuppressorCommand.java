package dev.lyphium.suppressor.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.lyphium.suppressor.manager.RegionManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Main command for managing the plugin.
 */
public final class SuppressorCommand {

    public static final String DESCRIPTION = "Central command for update suppression";

    /**
     * Collection of all available sub commands.
     */
    @Getter(AccessLevel.PACKAGE)
    private final SubCommand[] subCommands;

    public SuppressorCommand(RegionManager regionManager) {
        this.subCommands = new SubCommand[]{
                new SuppressorAddCommand(regionManager),
                new SuppressorEditCommand(regionManager),
                new SuppressorHelpCommand(this),
                new SuppressorReloadCommand(regionManager),
                new SuppressorRemoveCommand(regionManager),
                new SuppressorToggleCommand(regionManager)
        };
    }

    public LiteralCommandNode<CommandSourceStack> construct() {
        LiteralArgumentBuilder<CommandSourceStack> cmd = Commands.literal("suppressor")
                .requires(s -> s.getSender().hasPermission("suppressor.admin"));

        for (final SubCommand command : subCommands) {
            cmd = cmd.then(command.construct());
        }

        return cmd.build();
    }

}
