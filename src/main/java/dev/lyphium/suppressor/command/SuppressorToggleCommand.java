package dev.lyphium.suppressor.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.lyphium.suppressor.manager.RegionManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class SuppressorToggleCommand implements SubCommand {

    @Getter
    private final String name = "toggle";

    @Getter
    private final Component description = Component.translatable("suppressor.command.suppressor.toggle.description");

    private final RegionManager regionManager;

    public SuppressorToggleCommand(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    public LiteralCommandNode<CommandSourceStack> construct() {
        return Commands.literal(name)
                .requires(s -> s.getExecutor() instanceof Player)
                .executes(ctx -> {
                    if (!(ctx.getSource().getExecutor() instanceof Player player))
                        return Command.SINGLE_SUCCESS;

                    final UUID uuid = player.getUniqueId();
                    if (regionManager.getShowOutlines().contains(uuid)) {
                        regionManager.getShowOutlines().remove(uuid);
                        player.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.toggle.hide")));
                    } else {
                        regionManager.getShowOutlines().add(uuid);
                        player.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.toggle.show")));
                    }

                    return Command.SINGLE_SUCCESS;
                })
                .build();
    }
}
