package dev.lyphium.suppressor.data;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public record SuppressorRegion(@NotNull String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {

    public boolean contains(@NotNull String world, int x, int y, int z) {
        return this.world.equals(world)
                && x >= minX && x <= maxX
                && y >= minY && y <= maxY
                && z >= minZ && z <= maxZ;
    }

    public boolean contains(@NotNull Location location) {
        return contains(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

}
