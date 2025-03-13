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

public final class SuppressorAddCommand implements SubCommand {

    private final RegionManager regionManager;

    public SuppressorAddCommand(@NotNull RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @Override
    public boolean handleCommand(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.error.only_player", ColorConstants.WARNING)));
            return true;
        }

        // Check if arguments have the right amount of members
        if (args.length != 6)
            return false;

        // Check if provided numbers are valid
        if (!args[0].matches("[+-]?\\d+") || !args[1].matches("[+-]?\\d+") || !args[2].matches("[+-]?\\d+")
                || !args[3].matches("[+-]?\\d+") || !args[4].matches("[+-]?\\d+") || !args[5].matches("[+-]?\\d+")) {
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.error.invalid_format", ColorConstants.ERROR)));
            return true;
        }

        final int x1 = Integer.parseInt(args[0]);
        final int y1 = Integer.parseInt(args[1]);
        final int z1 = Integer.parseInt(args[2]);
        final int x2 = Integer.parseInt(args[3]);
        final int y2 = Integer.parseInt(args[4]);
        final int z2 = Integer.parseInt(args[5]);

        final int minX = Math.min(x1, x2);
        final int maxX = Math.max(x1, x2);
        final int minY = Math.min(y1, y2);
        final int maxY = Math.max(y1, y2);
        final int minZ = Math.min(z1, z2);
        final int maxZ = Math.max(z1, z2);

        final SuppressorRegion region = new SuppressorRegion(player.getWorld().getName(), minX, minY, minZ, maxX, maxY, maxZ);

        final boolean success = regionManager.addRegion(region);
        if (success) {
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.add.success", ColorConstants.SUCCESS)));
        } else {
            sender.sendMessage(TextConstants.PREFIX.append(Component.translatable("command.suppressor.add.duplicate", ColorConstants.ERROR)));
        }

        return true;
    }

    @Override
    public List<String> handleTabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player))
            return List.of();

        return switch (args.length) {
            case 1, 4 -> List.of(String.valueOf(player.getLocation().getBlockX()));
            case 2, 5 -> List.of(String.valueOf(player.getLocation().getBlockY()));
            case 3, 6 -> List.of(String.valueOf(player.getLocation().getBlockZ()));
            default -> List.of();
        };
    }
}
