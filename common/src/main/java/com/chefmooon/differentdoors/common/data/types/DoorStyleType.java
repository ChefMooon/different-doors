package com.chefmooon.differentdoors.common.data.types;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum DoorStyleType implements StringRepresentable {
    TWELVE_LITE(Items.OAK_DOOR),
    FORTIFIED(Items.SPRUCE_DOOR),
    HALF_PAPER(Items.BIRCH_DOOR),
    HALF_MOON(Items.JUNGLE_DOOR),
    SLATTED(Items.ACACIA_DOOR),
    SIX_PANEL(Items.DARK_OAK_DOOR),
    ELEGANT(Items.MANGROVE_DOOR),
    HALF_WINDOW(Items.CHERRY_DOOR),
    HALF_GRATED(Items.BAMBOO_DOOR),
    RUSTIC(Items.CRIMSON_DOOR),
    VINED(Items.WARPED_DOOR),
    ;

    private final Item baseStyleIngredient;
    DoorStyleType(Item baseStyleIngredient) {
        this.baseStyleIngredient = baseStyleIngredient;
    }

    public Item getBaseStyleIngredient() {
        return baseStyleIngredient;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
