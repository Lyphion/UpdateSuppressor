package dev.lyphium.suppressor.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.lyphium.suppressor.manager.RegionManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public final class SuppressorReloadCommand implements SubCommand {

    @Getter
    private final String name = "reload";

    @Getter
    private final Component description = Component.translatable("suppressor.command.suppressor.reload.description");

    private final RegionManager regionManager;

    public SuppressorReloadCommand(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    public LiteralCommandNode<CommandSourceStack> construct() {
        return Commands.literal(name)
                .executes(ctx -> {
                    final CommandSender executor = SubCommand.getExecutor(ctx);

                    try {
                        regionManager.loadRegions();

                        executor.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.reload.success")));
                    } catch (Exception e) {
                        executor.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.reload.failure")));
                    }

                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }
}
