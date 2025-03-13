package dev.lyphium.suppressor.listener;

import dev.lyphium.suppressor.manager.RegionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerListener implements Listener {

    private final RegionManager regionManager;

    public PlayerListener(@NotNull RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @EventHandler
    private void onQuit(@NotNull PlayerQuitEvent event) {
        regionManager.getShowOutlines().remove(event.getPlayer().getUniqueId());
    }

}
