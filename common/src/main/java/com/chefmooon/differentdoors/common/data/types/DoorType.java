package com.chefmooon.differentdoors.common.data.types;

import com.chefmooon.differentdoors.common.registry.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;
import java.util.function.Supplier;

public enum DoorType implements StringRepresentable {
    OAK(MapColor.WOOD, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.WOOD, Items.OAK_DOOR, Items.OAK_PLANKS),
    SPRUCE(MapColor.PLANT, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.WOOD, Items.SPRUCE_DOOR, Items.SPRUCE_PLANKS),
    BIRCH(MapColor.PLANT, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.WOOD, Items.BIRCH_DOOR, Items.BIRCH_PLANKS),
    JUNGLE(MapColor.PLANT, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.WOOD, Items.JUNGLE_DOOR, Items.JUNGLE_PLANKS),
    ACACIA(MapColor.COLOR_ORANGE, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.WOOD, Items.ACACIA_DOOR, Items.ACACIA_PLANKS),
    DARK_OAK(MapColor.COLOR_BROWN, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.WOOD, Items.DARK_OAK_DOOR, Items.DARK_OAK_PLANKS),
    MANGROVE(MapColor.PLANT, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.WOOD, Items.MANGROVE_DOOR, Items.MANGROVE_PLANKS),
    CHERRY(MapColor.TERRACOTTA_WHITE, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.CHERRY_WOOD, Items.CHERRY_DOOR, Items.CHERRY_PLANKS),
    BAMBOO(MapColor.COLOR_YELLOW, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.BAMBOO_WOOD, Items.BAMBOO_DOOR, Items.BAMBOO_BLOCK),
    CRIMSON(MapColor.CRIMSON_STEM, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.NETHER_WOOD, Items.CRIMSON_DOOR, Items.CRIMSON_PLANKS),
    WARPED(MapColor.WARPED_STEM, 3.0F, 3.0F, ModSounds.LARGE_DOOR_OPEN, ModSounds.LARGE_DOOR_CLOSE, SoundType.NETHER_WOOD, Items.WARPED_DOOR, Items.WARPED_PLANKS),
    
//    IRON(MapColor.METAL, 5.0F, 5.0F, ModSounds.LARGE_DOOR_OPEN_METAL, ModSounds.LARGE_DOOR_CLOSE_METAL, SoundType.METAL, Items.IRON_DOOR, Items.IRON_INGOT),
//    COPPER(MapColor.COLOR_ORANGE, 3.0F, 6.0F, ModSounds.LARGE_DOOR_OPEN_METAL, ModSounds.LARGE_DOOR_CLOSE_METAL, SoundType.COPPER, Items.COPPER_DOOR, Items.COPPER_INGOT),
    ;

    private final MapColor mapColor;
    private final Float destroyTime;
    private final float strength;
    private final Supplier<SoundEvent> openSound;
    private final Supplier<SoundEvent> closeSound;
    private final SoundType soundType;
    private final Item primaryCraftingIngredient;
    private final Item secondaryCraftingIngredient;
    DoorType(
            MapColor mapColor,
            float destroyTime,
            float strength,
            Supplier<SoundEvent> openSound,
            Supplier<SoundEvent> closeSound,
            SoundType soundType,
            Item primaryCraftingIngredient,
            Item secondaryCraftingIngredient
    ) {
        this.mapColor = mapColor;
        this.destroyTime = destroyTime;
        this.strength = strength;
        this.openSound = openSound;
        this.closeSound = closeSound;
        this.soundType = soundType;
        this.primaryCraftingIngredient = primaryCraftingIngredient;
        this.secondaryCraftingIngredient = secondaryCraftingIngredient;
    }

    public MapColor getMapColor() {
        return mapColor;
    }
    
    public float getDestroyTime() {
        return destroyTime;
    }

    public float getStrength() {
        return strength;
    }

    public Supplier<SoundEvent> getOpenSound() {
        return openSound;
    }

    public Supplier<SoundEvent> getCloseSound() {
        return closeSound;
    }
    
    public SoundType getSoundType() {
        return soundType;
    }

    public Item getPrimaryCraftingIngredient() {
        return primaryCraftingIngredient;
    }

    public Item getSecondaryCraftingIngredient() {
        return secondaryCraftingIngredient;
    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
