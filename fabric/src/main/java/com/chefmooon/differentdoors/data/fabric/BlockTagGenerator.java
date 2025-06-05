package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
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
        ModBlocksImpl.LARGE_DOOR_VARIANTS.forEach(((doorType, blockSupplier) -> getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_AXE)
                .add(blockSupplier.get())));
    }
}
