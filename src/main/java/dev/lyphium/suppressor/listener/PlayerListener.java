package dev.lyphium.suppressor.listener;

import dev.lyphium.suppressor.manager.RegionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerListener implements Listener {

    private final RegionManager regionManager;

    public PlayerListener(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        regionManager.getShowOutlines().remove(event.getPlayer().getUniqueId());
    }

}
