package com.chefmooon.differentdoors.common.registry.fabric;

import com.chefmooon.differentdoors.common.block.fabric.DoubleDoorBlockImpl;
import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModBlocksImpl {
    public static final HashMap<DoorInfoRecord, Supplier<Block>> DOUBLE_DOOR_VARIANTS = new HashMap<>();

//    public static final Supplier<Block> SIMPLE_LARGE_DOOR = registerBlock(ModBlocks.SIMPLE_LARGE_DOOR, new LargeDoorBlock(DoorType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR))); // TODO : remove after testing

    private static void registerVanillaWoodenDoubleDoors() {
        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
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
                        ModBlocks.DOUBLE_DOOR.withPrefix(doorMaterialType.getSerializedName() + "_" + doorStyleType.getSerializedName() + "_"),
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
