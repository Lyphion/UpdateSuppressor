package dev.lyphium.suppressor.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class TextConstants {

    public static final Component SUPPRESSOR = MiniMessage.miniMessage().deserialize("<gradient:#e70000:#f88d09:#ffd100>Suppressor</gradient>");

    public static final TextComponent PREFIX = Component.text()
            .content("| ").color(ColorConstants.HIGHLIGHT)
            .append(SUPPRESSOR)
            .append(Component.text(" » ", ColorConstants.HIGHLIGHT))
            .build();

}
