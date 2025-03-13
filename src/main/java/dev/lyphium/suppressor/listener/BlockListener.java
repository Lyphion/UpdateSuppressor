package dev.lyphium.suppressor.listener;

import dev.lyphium.suppressor.manager.RegionManager;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.jetbrains.annotations.NotNull;

public final class BlockListener implements Listener {

    private final RegionManager regionManager;

    public BlockListener(@NotNull RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @EventHandler
    private void onPhysics(@NotNull BlockPhysicsEvent event) {
        final Block block = event.getBlock();

        if (regionManager.isInRegion(block.getWorld().getName(), block.getX(), block.getY(), block.getZ())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInteract(@NotNull PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL)
            return;

        final Block block = event.getClickedBlock();
        if (block == null)
            return;

        if (regionManager.isInRegion(block.getWorld().getName(), block.getX(), block.getY(), block.getZ())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockMove(@NotNull BlockFromToEvent event) {
        final Block from = event.getBlock();
        final Block to = event.getToBlock();

        if (regionManager.isInRegion(from.getWorld().getName(), from.getX(), from.getY(), from.getZ())) {
            event.setCancelled(true);
        } else if (regionManager.isInRegion(to.getWorld().getName(), to.getX(), to.getY(), to.getZ())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onGrow(@NotNull BlockGrowEvent event) {
        final Block block = event.getBlock();

        if (regionManager.isInRegion(block.getWorld().getName(), block.getX(), block.getY(), block.getZ())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onGrow(@NotNull StructureGrowEvent event) {
        for (final BlockState block : event.getBlocks()) {
            if (regionManager.isInRegion(block.getWorld().getName(), block.getX(), block.getY(), block.getZ())) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    private void onFertilize(@NotNull BlockFertilizeEvent event) {
        final Block block = event.getBlock();

        if (regionManager.isInRegion(block.getWorld().getName(), block.getX(), block.getY(), block.getZ())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onAbsorb(@NotNull SpongeAbsorbEvent event) {
        final Block block = event.getBlock();

        if (regionManager.isInRegion(block.getWorld().getName(), block.getX(), block.getY(), block.getZ())) {
            event.setCancelled(true);
        }

        event.getBlocks().removeIf(b -> regionManager.isInRegion(b.getWorld().getName(), b.getX(), b.getY(), b.getZ()));
    }
}
