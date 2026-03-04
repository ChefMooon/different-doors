package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import com.chefmooon.differentdoors.common.tag.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Mod Tags
        ModItemsImpl.DOUBLE_DOOR_VARIANTS.forEach(((doorInfoRecord, itemSupplier) -> {
            if (doorInfoRecord.doorMaterialType() != DoorMaterialType.IRON) {
                getOrCreateTagBuilder(ModTags.WOODEN_DOUBLE_DOORS_ITEM)
                        .add(itemSupplier.get());
            }
        }));

        getOrCreateTagBuilder(ModTags.DOUBLE_DOORS_ITEM)
                .addTag(ModTags.WOODEN_DOUBLE_DOORS_ITEM)
                .add(ModItemsImpl.IRON_DOUBLE_DOOR.get()
//                        ModItemsImpl.COPPER_DOUBLE_DOOR.get(),
//                        ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get(),
//                        ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get(),
//                        ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get(),
//                        ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get(),
//                        ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get(),
//                        ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get(),
//                        ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get()
                );
    }
}
