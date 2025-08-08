package com.chefmooon.differentdoors.common.util.neoforge;

import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.registry.neoforge.ModItemsImpl;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
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
            for (int i = DoorMaterialType.values().length - 1; i >= 0; i--) { // Reverse reverse
                if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS && doorMaterialType.getCreativeModeTab() == CreativeModeTabs.BUILDING_BLOCKS) {
                    event.insertAfter(doorMaterialType.getPrimaryCraftingIngredient().getDefaultInstance(), ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(new com.chefmooon.differentdoors.common.data.DoorInfoRecord(doorMaterialType, com.chefmooon.differentdoors.common.data.types.DoorStyleType.values()[i])).get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
        }
    }

    public static boolean isModLoaded(String modId) {
        if (LoadingModList.get().getModFileById(modId) != null) return true;
        return ModList.get().isLoaded(modId);
    }
}
