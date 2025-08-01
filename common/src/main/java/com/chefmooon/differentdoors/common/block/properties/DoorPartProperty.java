package com.chefmooon.differentdoors.common.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum DoorPartProperty implements StringRepresentable {
    TOP_LEFT(-1, 2, 0, false),
    TOP(0, 2, 0, false),
    TOP_RIGHT(1, 2, 0, false),
    LEFT(-1, 1, 0, false),
    CENTER(0, 1, 0, false),
    RIGHT(1, 1, 0, false),
    BOTTOM_LEFT(-1, 0, 0, false),
    BOTTOM(0, 0, 0, false),
    BOTTOM_RIGHT(1, 0, 0, false),

    TOP_LEFT_OPEN_EXT(-1, 2, 1, true),
    TOP_RIGHT_OPEN_EXT(1, 2, 1, true),
    LEFT_OPEN_EXT(-1, 1, 1, true),
    RIGHT_OPEN_EXT(1, 1, 1, true),
    BOTTOM_LEFT_OPEN_EXT(-1, 0, 1, true),
    BOTTOM_RIGHT_OPEN_EXT(1, 0, 1, true),
    ;

    private final int xOffset;
    private final int yOffset;
    private final int zOffset;
    private final boolean isExtension;

    DoorPartProperty(int xOffset, int yOffset, int zOffset, boolean isExtension) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.isExtension = isExtension;
    }

    public int xOffset() {
        return this.xOffset;
    }

    public int yOffset() {
        return this.yOffset;
    }

    public int zOffset() {
        return this.zOffset;
    }

    public boolean isExtension() {
        return this.isExtension;
    }

    public boolean isController() {
        return this == BOTTOM;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return getSerializedName();
    }
}
