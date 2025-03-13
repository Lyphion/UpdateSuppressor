package dev.lyphium.suppressor.listener;

import dev.lyphium.suppressor.manager.RegionManager;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class BlockListener implements Listener {

    private final RegionManager regionManager;

    public BlockListener(@NotNull RegionManager regionManager) {
        this.regionManager = regionManager;
    }
}
