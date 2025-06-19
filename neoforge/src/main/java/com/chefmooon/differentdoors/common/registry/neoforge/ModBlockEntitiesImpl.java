package com.chefmooon.differentdoors.common.registry.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntitiesImpl {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DifferentDoors.MOD_ID);

//    public static final Supplier<BlockEntityType<LargeDoorBlockEntity>> LARGE_DOOR_VARIANTS = BLOCK_ENTITIES.register(ModBlockEntities.LARGE_DOOR.getPath(),
//            () -> BlockEntityType.Builder.of(LargeDoorBlockEntity::new,
//                    ModBlocksImpl.LARGE_DOOR_VARIANTS.values().stream().map(Supplier::get).toArray(Block[]::new)
//            ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
