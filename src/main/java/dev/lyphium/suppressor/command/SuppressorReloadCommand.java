package dev.lyphium.suppressor.command;

import dev.lyphium.suppressor.manager.RegionManager;
import dev.lyphium.suppressor.util.ColorConstants;
import dev.lyphium.suppressor.util.TextConstants;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SuppressorReloadCommand implements SubCommand {

    private final RegionManager regionManager;

    public SuppressorReloadCommand(@NotNull RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @Override
    public boolean handleCommand(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        // Check if arguments have the right amount of members
        if (args.length != 0)
            return false;

        try {
            regionManager.loadRegions();

            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.reload.success", ColorConstants.SUCCESS)));
        } catch (Exception e) {
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.reload.failure", ColorConstants.ERROR)));
        }

        return true;
    }

    @Override
    public List<String> handleTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
