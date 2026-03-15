package dev.lyphium.suppressor.listener;

import dev.lyphium.suppressor.manager.RegionManager;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;

public final class BlockListener implements Listener {

    private final RegionManager regionManager;

    public BlockListener(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    @EventHandler
    private void onPhysics(BlockPhysicsEvent event) {
        final Block block = event.getBlock();

        if (isInRegion(block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL)
            return;

        final Block block = event.getClickedBlock();
        if (block == null)
            return;

        if (isInRegion(block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onBlockMove(BlockFromToEvent event) {
        final Block from = event.getBlock();
        final Block to = event.getToBlock();

        if (isInRegion(from) || isInRegion(to)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onGrow(BlockGrowEvent event) {
        final Block block = event.getBlock();

        if (isInRegion(block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onGrow(StructureGrowEvent event) {
        for (final BlockState block : event.getBlocks()) {
            if (isInRegion(block.getBlock())) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    private void onFertilize(BlockFertilizeEvent event) {
        final Block block = event.getBlock();

        if (isInRegion(block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onAbsorb(SpongeAbsorbEvent event) {
        final Block block = event.getBlock();

        if (isInRegion(block)) {
            event.setCancelled(true);
        }

        event.getBlocks().removeIf(b -> isInRegion(b.getBlock()));
    }

    @EventHandler
    private void onFade(BlockFadeEvent event) {
        final Block block = event.getBlock();

        if (isInRegion(block)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onDecay(LeavesDecayEvent event) {
        final Block block = event.getBlock();

        if (isInRegion(block)) {
            event.setCancelled(true);
        }
    }

    private boolean isInRegion(Block block) {
        return regionManager.isInRegion(block.getWorld().getName(), block.getX(), block.getY(), block.getZ());
    }
}
