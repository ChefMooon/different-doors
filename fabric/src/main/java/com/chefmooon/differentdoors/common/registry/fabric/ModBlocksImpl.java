package com.chefmooon.differentdoors.common.registry.fabric;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.WeatheringCopperDoubleDoorBlock;
import com.chefmooon.differentdoors.common.block.fabric.DoubleDoorBlockImpl;
import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModBlocksImpl {
    public static final HashMap<DoorInfoRecord, Supplier<Block>> DOUBLE_DOOR_VARIANTS = new HashMap<>();

    public static final Supplier<Block> IRON_DOUBLE_DOOR = registerBlock(ModBlocks.IRON_DOUBLE_DOOR,
            new DoubleDoorBlockImpl(DoorMaterialType.IRON,
                    BlockBehaviour.Properties.of().mapColor(DoorMaterialType.IRON.getMapColor())
                            .strength(DoorMaterialType.IRON.getStrength())
                            .sound(DoorMaterialType.IRON.getSoundType())
                            .requiresCorrectToolForDrops()
                            .noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> COPPER_DOUBLE_DOOR = registerBlock(ModBlocks.COPPER_DOUBLE_DOOR,
            new WeatheringCopperDoubleDoorBlock(DoorMaterialType.COPPER, WeatheringCopper.WeatherState.UNAFFECTED,
                    BlockBehaviour.Properties.of().mapColor(Blocks.COPPER_BLOCK.defaultMapColor())
                            .strength(DoorMaterialType.COPPER.getDestroyTime(), DoorMaterialType.COPPER.getStrength())
                            .sound(DoorMaterialType.COPPER.getSoundType())
                            .requiresCorrectToolForDrops()
                            .noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> EXPOSED_COPPER_DOUBLE_DOOR = registerBlock(ModBlocks.EXPOSED_COPPER_DOUBLE_DOOR,
            new WeatheringCopperDoubleDoorBlock(DoorMaterialType.COPPER, WeatheringCopper.WeatherState.EXPOSED,
                    BlockBehaviour.Properties.ofFullCopy(COPPER_DOUBLE_DOOR.get()).mapColor(Blocks.EXPOSED_COPPER.defaultMapColor())));
    public static final Supplier<Block> WEATHERED_COPPER_DOUBLE_DOOR = registerBlock(ModBlocks.WEATHERED_COPPER_DOUBLE_DOOR,
            new WeatheringCopperDoubleDoorBlock(DoorMaterialType.COPPER, WeatheringCopper.WeatherState.WEATHERED,
                    BlockBehaviour.Properties.ofFullCopy(COPPER_DOUBLE_DOOR.get()).mapColor(Blocks.WEATHERED_COPPER.defaultMapColor())));
    public static final Supplier<Block> OXIDIZED_COPPER_DOUBLE_DOOR = registerBlock(ModBlocks.OXIDIZED_COPPER_DOUBLE_DOOR,
            new WeatheringCopperDoubleDoorBlock(DoorMaterialType.COPPER, WeatheringCopper.WeatherState.OXIDIZED,
                    BlockBehaviour.Properties.ofFullCopy(COPPER_DOUBLE_DOOR.get()).mapColor(Blocks.OXIDIZED_COPPER.defaultMapColor())));
    public static final Supplier<Block> WAXED_COPPER_DOUBLE_DOOR = registerBlock(ModBlocks.WAXED_COPPER_DOUBLE_DOOR,
            new DoubleDoorBlockImpl(DoorMaterialType.COPPER, BlockBehaviour.Properties.ofFullCopy(COPPER_DOUBLE_DOOR.get())));
    public static final Supplier<Block> WAXED_EXPOSED_COPPER_DOUBLE_DOOR = registerBlock(ModBlocks.WAXED_EXPOSED_COPPER_DOUBLE_DOOR,
            new DoubleDoorBlockImpl(DoorMaterialType.COPPER, BlockBehaviour.Properties.ofFullCopy(EXPOSED_COPPER_DOUBLE_DOOR.get())));
    public static final Supplier<Block> WAXED_WEATHERED_COPPER_DOUBLE_DOOR = registerBlock(ModBlocks.WAXED_WEATHERED_COPPER_DOUBLE_DOOR,
            new DoubleDoorBlockImpl(DoorMaterialType.COPPER, BlockBehaviour.Properties.ofFullCopy(WEATHERED_COPPER_DOUBLE_DOOR.get())));
    public static final Supplier<Block> WAXED_OXIDIZED_COPPER_DOUBLE_DOOR = registerBlock(ModBlocks.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR,
            new DoubleDoorBlockImpl(DoorMaterialType.COPPER, BlockBehaviour.Properties.ofFullCopy(OXIDIZED_COPPER_DOUBLE_DOOR.get())));

    private static void registerVanillaWoodenDoubleDoors() {
        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            if (doorMaterialType == DoorMaterialType.IRON || doorMaterialType == DoorMaterialType.COPPER) continue;
            for (DoorStyleType doorStyleType : DoorStyleType.values()) {
                BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                        .mapColor(doorMaterialType.getMapColor())
                        .strength(doorMaterialType.getDestroyTime(), doorMaterialType.getStrength())
                        .sound(doorMaterialType.getSoundType())
                        .noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY);

                if (doorMaterialType.getNoteBlockInstrument() != null) {
                    properties = properties.instrument(doorMaterialType.getNoteBlockInstrument());
                }

                BlockBehaviour.Properties finalProperties = properties;
                Supplier<Block> block = registerBlock(
                        ModBlocks.DOUBLE_DOOR.withPrefix(doorStyleType.getSerializedName() + "_" + doorMaterialType.getSerializedName() + "_"),
                        new DoubleDoorBlockImpl(doorMaterialType, finalProperties)
                );
                DOUBLE_DOOR_VARIANTS.put(new DoorInfoRecord(doorMaterialType, doorStyleType), block);
            }
        }
    }

    public static Supplier<Block> registerBlock(final ResourceLocation location, final Block block) {
        Registry.register(BuiltInRegistries.BLOCK, location, block);
        return () -> block;
    }

    public static void register() {
        registerVanillaWoodenDoubleDoors();
    }
}
