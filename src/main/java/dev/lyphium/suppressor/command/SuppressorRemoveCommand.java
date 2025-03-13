package dev.lyphium.suppressor.command;

import dev.lyphium.suppressor.data.SuppressorRegion;
import dev.lyphium.suppressor.manager.RegionManager;
import dev.lyphium.suppressor.util.ColorConstants;
import dev.lyphium.suppressor.util.TextConstants;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SuppressorRemoveCommand implements SubCommand {

    private final RegionManager regionManager;

    public SuppressorRemoveCommand(@NotNull RegionManager regionManager) {
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

        final SuppressorRegion region = regionManager.getRegion(player.getLocation());
        if (region != null) {
            regionManager.removeRegion(region);
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.remove.success", ColorConstants.SUCCESS)));
        } else {
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.remove.unknown", ColorConstants.ERROR)));
        }

        return true;
    }

    @Override
    public List<String> handleTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
