package dev.lyphium.suppressor.command;

import dev.lyphium.suppressor.manager.RegionManager;
import dev.lyphium.suppressor.util.ColorConstants;
import dev.lyphium.suppressor.util.TextConstants;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public final class SuppressorToggleCommand implements SubCommand {

    private final RegionManager regionManager;

    public SuppressorToggleCommand(@NotNull RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @Override
    public boolean handleCommand(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.error.only_player", ColorConstants.WARNING)));
            return true;
        }

        // Check if arguments have the right amount of members
        if (args.length != 0)
            return false;

        final UUID uuid = player.getUniqueId();
        if (regionManager.getShowOutlines().contains(uuid)) {
            regionManager.getShowOutlines().remove(uuid);
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.toggle.hide", ColorConstants.ERROR)));
        } else {
            regionManager.getShowOutlines().add(uuid);
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.toggle.show", ColorConstants.SUCCESS)));
        }

        return true;
    }

    @Override
    public List<String> handleTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
