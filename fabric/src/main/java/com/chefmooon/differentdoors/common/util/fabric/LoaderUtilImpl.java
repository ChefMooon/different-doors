package com.chefmooon.differentdoors.common.util.fabric;

import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
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
        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            for (int i = DoorStyleType.values().length - 1; i >= 0; i--) { // Reverse reverse
                entries.addAfter(doorMaterialType.getPrimaryCraftingIngredient(), ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(new DoorInfoRecord(doorMaterialType, DoorStyleType.values()[i])).get());
            }
        }
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
