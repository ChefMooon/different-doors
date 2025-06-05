package com.chefmooon.differentdoors.common.registry.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.data.types.DoorType;
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

    public static final HashMap<DoorType, Supplier<Block>> LARGE_DOOR_VARIANTS = registerLargeDoorVariantsAll();

//    public static final Supplier<Block> LARGE_DOOR = registerBlock(ModBlocks.LARGE_DOOR, () -> new LargeDoorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_DOOR).explosionResistance(6).mapColor(MapColor.METAL)));

    private static HashMap<DoorType, Supplier<Block>> registerLargeDoorVariantsAll() {
        HashMap<DoorType, Supplier<Block>> hashMap = new HashMap<>();
        for (DoorType doorType : DoorType.values()) {
            Supplier<Block> block = registerBlock(ModBlocks.LARGE_DOOR.withPrefix(doorType.name().toLowerCase() + "_"),
                    () -> new LargeDoorBlock(doorType, BlockBehaviour.Properties.of()
                            .mapColor(doorType.getMapColor())
                            .strength(doorType.getDestroyTime(), doorType.getStrength())
                            .sound(doorType.getSoundType())
                            .noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY))); // TODO : add instrument for wood doors? does not apply to metal doors));
            hashMap.put(doorType, block);
        }
        return hashMap;
    }

    public static Supplier<Block> registerBlock(final ResourceLocation location, final Supplier<Block> block) {
        return BLOCKS.register(location.getPath(), block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
