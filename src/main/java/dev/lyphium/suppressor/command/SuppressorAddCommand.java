package dev.lyphium.suppressor.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.lyphium.suppressor.data.SuppressorRegion;
import dev.lyphium.suppressor.manager.RegionManager;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.math.BlockPosition;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

@SuppressWarnings("UnstableApiUsage")
public final class SuppressorAddCommand implements SubCommand {

    @Getter
    private final String name = "add";

    @Getter
    private final Component description = Component.translatable("suppressor.command.suppressor.add.description");

    private final RegionManager regionManager;

    public SuppressorAddCommand(RegionManager regionManager) {
        this.regionManager = regionManager;
    }

    public LiteralCommandNode<CommandSourceStack> construct() {
        return Commands.literal(name)
                .then(Commands.argument("pos1", ArgumentTypes.blockPosition())
                        .then(Commands.argument("pos2", ArgumentTypes.blockPosition())
                                .executes(ctx -> {
                                    final CommandSender executor = SubCommand.getExecutor(ctx);

                                    final BlockPosition pos1 = ctx.getArgument("pos1", BlockPositionResolver.class).resolve(ctx.getSource());
                                    final BlockPosition pos2 = ctx.getArgument("pos2", BlockPositionResolver.class).resolve(ctx.getSource());

                                    final int minX = Math.min(pos1.blockX(), pos2.blockX());
                                    final int maxX = Math.max(pos1.blockX(), pos2.blockX());
                                    final int minY = Math.min(pos1.blockY(), pos2.blockY());
                                    final int maxY = Math.max(pos1.blockY(), pos2.blockY());
                                    final int minZ = Math.min(pos1.blockZ(), pos2.blockZ());
                                    final int maxZ = Math.max(pos1.blockZ(), pos2.blockZ());

                                    final World world = ctx.getSource().getLocation().getWorld();
                                    final SuppressorRegion region = new SuppressorRegion(world.getName(), minX, minY, minZ, maxX, maxY, maxZ);

                                    final boolean success = regionManager.addRegion(region);
                                    if (success) {
                                        executor.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.add.success")));
                                    } else {
                                        executor.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.add.duplicate")));
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
                .build();
    }
}
