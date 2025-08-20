package com.chefmooon.differentdoors.common.util.neoforge;

import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.neoforge.ModItemsImpl;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class LoaderUtilImpl {
    public static void init(IEventBus modEventBus) {
        modEventBus.register(LoaderUtilImpl.class);
    }

    @SubscribeEvent
    public static void populateItemGroups(BuildCreativeModeTabContentsEvent event) {
        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            if (doorMaterialType == DoorMaterialType.IRON || doorMaterialType == DoorMaterialType.COPPER) continue;
            for (int i = DoorStyleType.values().length - 1; i >= 0; i--) { // Reverse reverse
                if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS && doorMaterialType.getCreativeModeTab() == CreativeModeTabs.BUILDING_BLOCKS) {
                    event.insertAfter(doorMaterialType.getPrimaryCraftingIngredient().getDefaultInstance(), ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(new DoorInfoRecord(doorMaterialType, DoorStyleType.values()[i])).get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            // Metal doors
            event.insertAfter(new ItemStack(Items.IRON_DOOR), ModItemsImpl.IRON_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.COPPER_DOOR.getDefaultInstance(), ModItemsImpl.COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.EXPOSED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.WEATHERED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.OXIDIZED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.WAXED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.WAXED_EXPOSED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.WAXED_WEATHERED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(Items.WAXED_OXIDIZED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static boolean isModLoaded(String modId) {
        if (LoadingModList.get().getModFileById(modId) != null) return true;
        return ModList.get().isLoaded(modId);
    }
}
