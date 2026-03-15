package dev.lyphium.suppressor.manager;

import dev.lyphium.suppressor.data.BlockPosition;
import dev.lyphium.suppressor.data.SuppressorRegion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.logging.Level;

public final class RegionManager {

    /**
     * Saving delay.
     */
    public static final int SAVE_DELAY = 20 * 2;

    private final JavaPlugin plugin;

    private final Set<SuppressorRegion> regions = new HashSet<>();
    private final Set<BlockPosition> positionCache = new HashSet<>();

    @Getter
    private final Set<UUID> showOutlines = new HashSet<>();

    @Nullable
    private BukkitTask saveTask;

    public RegionManager(JavaPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::handleOutlines, 5, 5);
    }

    /**
     * Add a new region to the pool.
     *
     * @param region Region to be added
     * @return {@code true} if region was added.
     */
    public boolean addRegion(SuppressorRegion region) {
        final boolean success = regions.add(region);
        if (success)
            saveRegions();

        return success;
    }

    /**
     * Remove new region from the pool.
     *
     * @param region Region to be added
     * @return {@code true} if region was added.
     */
    public boolean removeRegion(SuppressorRegion region) {
        final boolean success = regions.remove(region);
        if (success) {
            positionCache.clear();
            saveRegions();
        }

        return success;
    }

    /**
     * Get first region where location is contained.
     *
     * @param location Location contained in the region
     * @return Region of the location.
     */
    public @Nullable SuppressorRegion getRegion(Location location) {
        for (final SuppressorRegion region : regions) {
            if (region.contains(location)) {
                return region;
            }
        }

        return null;
    }

    /**
     * Get first region where location is contained.
     *
     * @param world Name of the world
     * @param x     X coordinate of the location
     * @param y     Y coordinate of the location
     * @param z     Z coordinate of the location
     * @return Region of the location.
     */
    public @Nullable SuppressorRegion getRegion(String world, int x, int y, int z) {
        for (final SuppressorRegion region : regions) {
            if (region.contains(world, x, y, z)) {
                return region;
            }
        }

        return null;
    }

    /**
     * Check if location is inside a region.
     *
     * @param location Location to test
     * @return {@code true} if location is inside a region.
     */
    public boolean isInRegion(Location location) {
        final BlockPosition position = new BlockPosition(location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (positionCache.contains(position))
            return true;

        final SuppressorRegion region = getRegion(location);
        if (region == null)
            return false;

        positionCache.add(position);
        return true;
    }

    /**
     * Check if location is inside a region.
     *
     * @param world Name of the world
     * @param x     X coordinate of the location
     * @param y     Y coordinate of the location
     * @param z     Z coordinate of the location
     * @return {@code true} if location is inside a region.
     */
    public boolean isInRegion(String world, int x, int y, int z) {
        final BlockPosition position = new BlockPosition(world, x, y, z);

        if (positionCache.contains(position))
            return true;

        final SuppressorRegion region = getRegion(world, x, y, z);
        if (region == null)
            return false;

        positionCache.add(position);
        return true;
    }

    /**
     * Load all regions from the config.
     */
    public void loadRegions() {
        regions.clear();
        positionCache.clear();

        final YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "regions.yml"));
        for (final Object o : config.getList("regions", List.of())) {
            if (!(o instanceof Map<?, ?> data))
                continue;

            final String world = (String) data.get("World");
            final int minX = (int) data.get("MinX");
            final int minY = (int) data.get("MinY");
            final int minZ = (int) data.get("MinZ");
            final int maxX = (int) data.get("MaxX");
            final int maxY = (int) data.get("MaxY");
            final int maxZ = (int) data.get("MaxZ");

            final SuppressorRegion region = new SuppressorRegion(world, minX, minY, minZ, maxX, maxY, maxZ);
            regions.add(region);
        }
    }

    /**
     * Save all regions to the config.
     */
    public void saveRegions() {
        if (saveTask != null)
            saveTask.cancel();

        // Delay saving, if multiple edits are made
        // Run saving asynchronously
        saveTask = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this::saveRegionsHandle, SAVE_DELAY);
    }

    /**
     * Save all regions to the config.
     */
    private void saveRegionsHandle() {
        final YamlConfiguration config = new YamlConfiguration();

        final List<Map<?, ?>> data = new ArrayList<>();
        for (final SuppressorRegion region : regions) {
            final Map<String, Object> map = Map.of(
                    "World", region.world(),
                    "MinX", region.minX(),
                    "MinY", region.minY(),
                    "MinZ", region.minZ(),
                    "MaxX", region.maxX(),
                    "MaxY", region.maxY(),
                    "MaxZ", region.maxZ()
            );

            data.add(map);
        }

        config.set("regions", data);

        try {
            config.save(new File(plugin.getDataFolder(), "regions.yml"));
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error saving regions", e);
        }
    }

    /**
     * Show outline of regions to players, who enabled it.
     */
    private void handleOutlines() {
        if (showOutlines.isEmpty())
            return;

        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (!showOutlines.contains(player.getUniqueId()))
                continue;

            for (final SuppressorRegion region : regions) {
                if (region.world().equals(player.getWorld().getName())) {
                    region.showOutline(player);
                }
            }
        }
    }
}
