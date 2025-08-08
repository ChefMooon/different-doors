package com.chefmooon.differentdoors.common.registry.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModBlocksImpl {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, DifferentDoors.MOD_ID);

    public static final HashMap<DoorInfoRecord, Supplier<Block>> DOUBLE_DOOR_VARIANTS = new HashMap<>();

//    public static final Supplier<Block> LARGE_DOOR = registerBlock(ModBlocks.LARGE_DOOR, () -> new LargeDoorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR).explosionResistance(6).mapColor(MapColor.METAL)));

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
                        () -> new DoubleDoorBlock(doorMaterialType, finalProperties)
                );
                DOUBLE_DOOR_VARIANTS.put(new DoorInfoRecord(doorMaterialType, doorStyleType), block);
            }
        }
    }

    public static Supplier<Block> registerBlock(final ResourceLocation location, final Supplier<Block> block) {
        return BLOCKS.register(location.getPath(), block);
    }

    public static void register(IEventBus eventBus) {
        registerVanillaWoodenDoubleDoors();
        BLOCKS.register(eventBus);
    }
}
