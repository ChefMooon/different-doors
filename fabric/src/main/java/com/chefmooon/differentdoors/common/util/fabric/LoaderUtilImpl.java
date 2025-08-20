package com.chefmooon.differentdoors.common.util.fabric;

import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public class LoaderUtilImpl {
    public static void init() {
        populateItemGroups();
    }
    public static void populateItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(LoaderUtilImpl::populateBuildingBlocks);
    }

    private static void populateBuildingBlocks(FabricItemGroupEntries entries) {
        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            if (doorMaterialType == DoorMaterialType.IRON || doorMaterialType == DoorMaterialType.COPPER) continue;
            for (int i = DoorStyleType.values().length - 1; i >= 0; i--) { // Reverse reverse
                entries.addAfter(doorMaterialType.getPrimaryCraftingIngredient(), ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(new DoorInfoRecord(doorMaterialType, DoorStyleType.values()[i])).get());
            }
        }

        // Metal doors
        entries.addAfter(Items.IRON_DOOR, ModItemsImpl.IRON_DOUBLE_DOOR.get());
        entries.addAfter(Items.COPPER_DOOR, ModItemsImpl.COPPER_DOUBLE_DOOR.get());
        entries.addAfter(Items.EXPOSED_COPPER_DOOR, ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get());
        entries.addAfter(Items.WEATHERED_COPPER_DOOR, ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get());
        entries.addAfter(Items.OXIDIZED_COPPER_DOOR, ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get());
        entries.addAfter(Items.WAXED_COPPER_DOOR, ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get());
        entries.addAfter(Items.WAXED_EXPOSED_COPPER_DOOR, ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get());
        entries.addAfter(Items.WAXED_WEATHERED_COPPER_DOOR, ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get());
        entries.addAfter(Items.WAXED_OXIDIZED_COPPER_DOOR, ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get());
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
