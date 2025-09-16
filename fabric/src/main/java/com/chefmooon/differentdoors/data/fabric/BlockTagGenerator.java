package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import com.chefmooon.differentdoors.common.tag.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Mod Tags
        ModBlocksImpl.DOUBLE_DOOR_VARIANTS.forEach(((doorInfoRecord, blockSupplier) -> {
            if (doorInfoRecord.doorMaterialType() != DoorMaterialType.IRON && doorInfoRecord.doorMaterialType() != DoorMaterialType.COPPER) {
                getOrCreateTagBuilder(ModTags.WOODEN_DOUBLE_DOORS)
                        .add(blockSupplier.get());
            }
        }));

        getOrCreateTagBuilder(ModTags.DOUBLE_DOORS)
                .addTag(ModTags.WOODEN_DOUBLE_DOORS)
                .add(ModBlocksImpl.IRON_DOUBLE_DOOR.get(),
                        ModBlocksImpl.COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.EXPOSED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WEATHERED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WAXED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get()
                );

        // Vanilla Tags
        ModBlocksImpl.DOUBLE_DOOR_VARIANTS.forEach(((doorInfoRecord, blockSupplier) -> getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(blockSupplier.get())));

        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocksImpl.IRON_DOUBLE_DOOR.get(),
                        ModBlocksImpl.COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.EXPOSED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WEATHERED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WAXED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get(),
                        ModBlocksImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get()
                );

        // Decide do I add modded doors to vanilla tag?
//        getOrCreateTagBuilder(BlockTags.WOODEN_DOORS)
//                .addTag(ModTags.WOODEN_DOUBLE_DOORS);
//
//        getOrCreateTagBuilder(BlockTags.DOORS)
//                .addTag(ModTags.DOUBLE_DOORS);
    }
}
