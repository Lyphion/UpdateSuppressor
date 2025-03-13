package dev.lyphium.suppressor.data;

import org.jetbrains.annotations.NotNull;

public record BlockPosition(@NotNull String world, int x, int y, int z) {
}
