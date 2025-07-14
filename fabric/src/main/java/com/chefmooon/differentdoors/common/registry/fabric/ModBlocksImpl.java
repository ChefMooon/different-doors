package com.chefmooon.differentdoors.common.registry.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.block.fabric.LargeDoorBlockImpl;
import com.chefmooon.differentdoors.common.data.types.DoorType;
import com.chefmooon.differentdoors.common.registry.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModBlocksImpl {
    public static final HashMap<DoorType, Supplier<Block>> LARGE_DOOR_VARIANTS = registerLargeDoorVariantsAll();

//    public static final Supplier<Block> SIMPLE_LARGE_DOOR = registerBlock(ModBlocks.SIMPLE_LARGE_DOOR, new LargeDoorBlock(DoorType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR))); // TODO : remove after testing

    private static HashMap<DoorType, Supplier<Block>> registerLargeDoorVariantsAll() {
        HashMap<DoorType, Supplier<Block>> hashMap = new HashMap<>();
        for (DoorType doorType : DoorType.values()) {
            BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
                .mapColor(doorType.getMapColor())
                .strength(doorType.getDestroyTime(), doorType.getStrength())
                .sound(doorType.getSoundType())
                .noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY);

            if (doorType.getNoteBlockInstrument() != null) {
                properties = properties.instrument(doorType.getNoteBlockInstrument());
            }

            BlockBehaviour.Properties finalProperties = properties;
            Supplier<Block> block = registerBlock(
                ModBlocks.LARGE_DOOR.withPrefix(doorType.name().toLowerCase() + "_"),
                new LargeDoorBlockImpl(doorType, finalProperties)
            );
            hashMap.put(doorType, block);
        }
        return hashMap;
    }

    public static Supplier<Block> registerBlock(final ResourceLocation location, final Block block) {
        Registry.register(BuiltInRegistries.BLOCK, location, block);
        return () -> block;
    }

    public static void register() {
    }
}
