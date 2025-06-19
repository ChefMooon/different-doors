package com.chefmooon.differentdoors.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntitiesImpl {
//    public static final BlockEntityType<LargeDoorBlockEntity> LARGE_DOOR_VARIANTS = registerBlockEntity(ModBlockEntities.LARGE_DOOR,
//            BlockEntityType.Builder.of(LargeDoorBlockEntity::new,
//                    ModBlocksImpl.LARGE_DOOR_VARIANTS.values().stream().map(Supplier::get).toArray(Block[]::new))
//    );

    public static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(ResourceLocation location, BlockEntityType.Builder<T> builder) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, location, builder.build(null));
    }

    public static void register() {
    }
}
