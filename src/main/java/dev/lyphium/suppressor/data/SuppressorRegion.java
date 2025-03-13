package dev.lyphium.suppressor.data;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public record SuppressorRegion(@NotNull String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, @NotNull Color color) {

    private static final Random random = new Random(0);

    public SuppressorRegion(@NotNull String world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this(world, minX, minY, minZ, maxX, maxY, maxZ, Color.fromRGB(java.awt.Color.HSBtoRGB(random.nextFloat(), 1, 1) & 0xFFFFFF));
    }

    /**
     * Check if location is inside the region.
     *
     * @param world Name of the world
     * @param x     X coordinate of the location
     * @param y     Y coordinate of the location
     * @param z     Z coordinate of the location
     * @return {@code true} if location is inside the region.
     */
    public boolean contains(@NotNull String world, int x, int y, int z) {
        return this.world.equals(world) && x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    /**
     * Check if location is inside the region.
     *
     * @param location Location to test
     * @return {@code true} if location is inside the region.
     */
    public boolean contains(@NotNull Location location) {
        return contains(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Show outline of region to player.
     *
     * @param player Player to show outline.
     */
    public void showOutline(@NotNull Player player) {
        final Particle.DustOptions options = new Particle.DustOptions(color, 1);

        for (int x = minX + 1; x <= maxX; x++) {
            player.spawnParticle(Particle.DUST, x, minY, minZ, 1, options);
            player.spawnParticle(Particle.DUST, x, maxY + 1, minZ, 1, options);
            player.spawnParticle(Particle.DUST, x, minY, maxZ + 1, 1, options);
            player.spawnParticle(Particle.DUST, x, maxY + 1, maxZ + 1, 1, options);
        }

        for (int y = minY; y <= maxY + 1; y++) {
            player.spawnParticle(Particle.DUST, minX, y, minZ, 1, options);
            player.spawnParticle(Particle.DUST, maxX + 1, y, minZ, 1, options);
            player.spawnParticle(Particle.DUST, minX, y, maxZ + 1, 1, options);
            player.spawnParticle(Particle.DUST, maxX + 1, y, maxZ + 1, 1, options);
        }

        for (int z = minZ + 1; z <= maxZ; z++) {
            player.spawnParticle(Particle.DUST, minX, minY, z, 1, options);
            player.spawnParticle(Particle.DUST, maxX + 1, minY, z, 1, options);
            player.spawnParticle(Particle.DUST, minX, maxY + 1, z, 1, options);
            player.spawnParticle(Particle.DUST, maxX + 1, maxY + 1, z, 1, options);
        }
    }
}
