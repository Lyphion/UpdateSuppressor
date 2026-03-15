package dev.lyphium.suppressor.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("SameReturnValue")
public final class SuppressorHelpCommand implements SubCommand {

    @Getter
    private final String name = "help";

    @Getter
    private final Component description = Component.translatable("suppressor.command.suppressor.help.description");

    private final SuppressorCommand parent;

    public SuppressorHelpCommand(SuppressorCommand parent) {
        this.parent = parent;
    }

    public LiteralCommandNode<CommandSourceStack> construct() {
        return Commands.literal(name)
                .executes(this::handle)
                .build();
    }

    private int handle(CommandContext<CommandSourceStack> ctx) {
        final CommandSender executor = ctx.getSource().getExecutor() == null ? ctx.getSource().getSender() : ctx.getSource().getExecutor();

        executor.sendMessage(Component.translatable("suppressor.chat.prefix").append(Component.translatable("suppressor.command.suppressor.help.menu")));

        // Format all sub commands, and filter with missing permission
        for (final SubCommand command : parent.getSubCommands()) {
            if (command.getMinimumPermission() != null && !executor.hasPermission(command.getMinimumPermission()))
                continue;

            final TextComponent.Builder builder = Component.text()
                    .content("» ").color(NamedTextColor.GRAY)
                    .append(Component.text(command.getName(), dev.lyphium.suppressor.util.ColorConstants.HIGHLIGHT).clickEvent(ClickEvent.suggestCommand("/suppressor " + command.getName())))
                    .append(Component.text(": ", NamedTextColor.GRAY))
                    .append(command.getDescription().color(NamedTextColor.WHITE));

            executor.sendMessage(builder.build());
        }

        return Command.SINGLE_SUCCESS;
    }
}
