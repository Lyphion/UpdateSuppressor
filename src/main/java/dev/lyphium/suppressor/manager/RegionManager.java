package dev.lyphium.suppressor.manager;

import dev.lyphium.suppressor.data.BlockPosition;
import dev.lyphium.suppressor.data.SuppressorRegion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class RegionManager {

    /**
     * Saving delay.
     */
    public static final int SAVE_DELAY = 20 * 2;

    private final JavaPlugin plugin;

    private final Set<SuppressorRegion> regions = new HashSet<>();
    private final Set<BlockPosition> positionCache = new HashSet<>();

    @Getter
    private final List<UUID> showOutlines = new ArrayList<>();

    private BukkitTask saveTask;

    public RegionManager(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean addRegion(@NotNull SuppressorRegion region) {
        positionCache.clear();

        return regions.add(region);
    }

    public boolean removeRegion(@NotNull SuppressorRegion region) {
        positionCache.clear();

        return regions.remove(region);
    }

    public @Nullable SuppressorRegion getRegion(@NotNull Location location) {
        for (final SuppressorRegion region : regions) {
            if (region.contains(location)) {
                return region;
            }
        }

        return null;
    }

    public @Nullable SuppressorRegion getRegion(@NotNull String world, int x, int y, int z) {
        for (final SuppressorRegion region : regions) {
            if (region.contains(world, x, y, z)) {
                return region;
            }
        }

        return null;
    }

    public boolean isInRegion(@NotNull Location location) {
        final BlockPosition position = new BlockPosition(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (positionCache.contains(position))
            return true;

        final SuppressorRegion region = getRegion(location);
        if (region == null)
            return false;

        positionCache.add(position);
        return true;
    }

    public boolean isInRegion(@NotNull String world, int x, int y, int z) {
        final BlockPosition position = new BlockPosition(world, x, y, z);

        if (positionCache.contains(position))
            return true;

        final SuppressorRegion region = getRegion(world, x, y, z);
        if (region == null)
            return false;

        positionCache.add(position);
        return true;
    }

    public void loadRegions() {

    }

    public void saveRegions() {
        if (saveTask != null)
            saveTask.cancel();

        // Delay saving, if multiple edits are made
        // Run saving asynchronously
        saveTask = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this::saveRegionsHandle, SAVE_DELAY);
    }

    private void saveRegionsHandle() {

    }
}
