package dev.lyphium.suppressor.command;

import dev.lyphium.suppressor.manager.RegionManager;
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
        return false;
    }

    @Override
    public List<String> handleTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
