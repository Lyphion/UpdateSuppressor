package dev.lyphium.suppressor.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.lyphium.suppressor.data.SuppressorRegion;
import dev.lyphium.suppressor.manager.RegionManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public final class SuppressorRemoveCommand implements SubCommand {

    @Getter
    private final String name = "remove";

    @Getter
    private final Component description = Component.translatable("suppressor.command.suppressor.remove.description");

    private final RegionManager regionManager;

    public SuppressorRemoveCommand(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    public LiteralCommandNode<CommandSourceStack> construct() {
        return Commands.literal(name)
                .executes(ctx -> {
                    final CommandSender executor = SubCommand.getExecutor(ctx);
                    final Location location = ctx.getSource().getLocation();

                    final SuppressorRegion region = regionManager.getRegion(location);
                    if (region != null) {
                        regionManager.removeRegion(region);
                        executor.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.remove.success")));
                    } else {
                        executor.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.remove.unknown")));
                    }

                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }
}
