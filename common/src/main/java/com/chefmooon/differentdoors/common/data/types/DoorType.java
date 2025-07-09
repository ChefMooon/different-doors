package com.chefmooon.differentdoors.common.data.types;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum DoorType implements StringRepresentable {
    OAK(MapColor.WOOD, 3.0F, 3.0F, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOOD_HIT, SoundEvents.WOOD_HIT, SoundType.WOOD, NoteBlockInstrument.BASS, Items.OAK_DOOR, Items.OAK_PLANKS),
    SPRUCE(MapColor.PLANT, 3.0F, 3.0F, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOOD_HIT, SoundEvents.WOOD_HIT, SoundType.WOOD, NoteBlockInstrument.BASS, Items.SPRUCE_DOOR, Items.SPRUCE_PLANKS),
    BIRCH(MapColor.PLANT, 3.0F, 3.0F, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOOD_HIT, SoundEvents.WOOD_HIT, SoundType.WOOD, NoteBlockInstrument.BASS, Items.BIRCH_DOOR, Items.BIRCH_PLANKS),
    JUNGLE(MapColor.PLANT, 3.0F, 3.0F, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOOD_HIT, SoundEvents.WOOD_HIT, SoundType.WOOD, NoteBlockInstrument.BASS, Items.JUNGLE_DOOR, Items.JUNGLE_PLANKS),
    ACACIA(MapColor.COLOR_ORANGE, 3.0F, 3.0F, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOOD_HIT, SoundEvents.WOOD_HIT, SoundType.WOOD, NoteBlockInstrument.BASS, Items.ACACIA_DOOR, Items.ACACIA_PLANKS),
    DARK_OAK(MapColor.COLOR_BROWN, 3.0F, 3.0F, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOOD_HIT, SoundEvents.WOOD_HIT, SoundType.WOOD, NoteBlockInstrument.BASS, Items.DARK_OAK_DOOR, Items.DARK_OAK_PLANKS),
    MANGROVE(MapColor.PLANT, 3.0F, 3.0F, SoundEvents.WOODEN_DOOR_OPEN, SoundEvents.WOODEN_DOOR_CLOSE, SoundEvents.WOOD_HIT, SoundEvents.WOOD_HIT, SoundType.WOOD, NoteBlockInstrument.BASS, Items.MANGROVE_DOOR, Items.MANGROVE_PLANKS),
    CHERRY(MapColor.TERRACOTTA_WHITE, 3.0F, 3.0F, SoundEvents.CHERRY_WOOD_DOOR_OPEN, SoundEvents.CHERRY_WOOD_HIT, SoundEvents.CHERRY_WOOD_HIT, SoundEvents.CHERRY_WOOD_DOOR_CLOSE, SoundType.CHERRY_WOOD, NoteBlockInstrument.BASS, Items.CHERRY_DOOR, Items.CHERRY_PLANKS),
    BAMBOO(MapColor.COLOR_YELLOW, 3.0F, 3.0F, SoundEvents.BAMBOO_WOOD_DOOR_OPEN, SoundEvents.BAMBOO_WOOD_HIT, SoundEvents.BAMBOO_WOOD_HIT, SoundEvents.BAMBOO_WOOD_DOOR_CLOSE, SoundType.BAMBOO_WOOD, NoteBlockInstrument.BASS, Items.BAMBOO_DOOR, Items.BAMBOO_BLOCK),
    CRIMSON(MapColor.CRIMSON_STEM, 3.0F, 3.0F, SoundEvents.NETHER_WOOD_DOOR_OPEN, SoundEvents.NETHER_WOOD_HIT, SoundEvents.NETHER_WOOD_HIT, SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundType.NETHER_WOOD, NoteBlockInstrument.BASS, Items.CRIMSON_DOOR, Items.CRIMSON_PLANKS),
    WARPED(MapColor.WARPED_STEM, 3.0F, 3.0F, SoundEvents.NETHER_WOOD_DOOR_OPEN, SoundEvents.NETHER_WOOD_HIT, SoundEvents.NETHER_WOOD_HIT, SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundType.NETHER_WOOD, NoteBlockInstrument.BASS, Items.WARPED_DOOR, Items.WARPED_PLANKS),
    
//    IRON(MapColor.METAL, 5.0F, 5.0F, ModSounds.LARGE_DOOR_OPEN_METAL, ModSounds.LARGE_DOOR_CLOSE_METAL, SoundType.METAL, null, Items.IRON_DOOR, Items.IRON_INGOT),
//    COPPER(MapColor.COLOR_ORANGE, 3.0F, 6.0F, ModSounds.LARGE_DOOR_OPEN_METAL, ModSounds.LARGE_DOOR_CLOSE_METAL, SoundType.COPPER, null, Items.COPPER_DOOR, Items.COPPER_INGOT),
    ;

    private final MapColor mapColor;
    private final Float destroyTime;
    private final float strength;
    private final SoundEvent openSound;
    private final SoundEvent closeSound;
    private final SoundEvent addSwingSound;
    private final SoundEvent removeSwingSound;
    private final SoundType soundType;
    private final NoteBlockInstrument noteBlockInstrument;
    private final Item primaryCraftingIngredient;
    private final Item secondaryCraftingIngredient;
    DoorType(
            MapColor mapColor,
            float destroyTime,
            float strength,
            SoundEvent openSound,
            SoundEvent closeSound,
            SoundEvent addSwingSound,
            SoundEvent removeSwingSound,
            SoundType soundType,
            NoteBlockInstrument noteBlockInstrument,
            Item primaryCraftingIngredient,
            Item secondaryCraftingIngredient
    ) {
        this.mapColor = mapColor;
        this.destroyTime = destroyTime;
        this.strength = strength;
        this.openSound = openSound;
        this.closeSound = closeSound;
        this.addSwingSound = addSwingSound;
        this.removeSwingSound = removeSwingSound;
        this.soundType = soundType;
        this.noteBlockInstrument = noteBlockInstrument;
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

    public SoundEvent getOpenSound() {
        return openSound;
    }

    public SoundEvent getCloseSound() {
        return closeSound;
    }

    public SoundEvent getAddSwingSound() {
        return addSwingSound;
    }

    public SoundEvent getRemoveSwingSound() {
        return removeSwingSound;
    }
    
    public SoundType getSoundType() {
        return soundType;
    }

    public NoteBlockInstrument getNoteBlockInstrument() {
        return noteBlockInstrument;
    }

    public Item getPrimaryCraftingIngredient() {
        return primaryCraftingIngredient;
    }

    public Item getSecondaryCraftingIngredient() {
        return secondaryCraftingIngredient;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
