package com.chefmooon.differentdoors.common.util.fabric;

import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTabs;

public class LoaderUtilImpl {
    public static void init() {
        populateItemGroups();
    }
    public static void populateItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(LoaderUtilImpl::populateBuildingBlocks);
    }

    private static void populateBuildingBlocks(FabricItemGroupEntries entries) {
        ModItemsImpl.LARGE_DOOR_VARIANTS.forEach(((doorType, itemSupplier) -> {
            entries.addAfter(doorType.getPrimaryCraftingIngredient(), itemSupplier.get());
        }));
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
