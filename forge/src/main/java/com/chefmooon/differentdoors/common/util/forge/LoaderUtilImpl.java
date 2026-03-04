package com.chefmooon.differentdoors.common.util.forge;

import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.forge.ModItemsImpl;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;

public class LoaderUtilImpl {
    public static void init(IEventBus modEventBus) {
        modEventBus.register(LoaderUtilImpl.class);
    }

    @SubscribeEvent
    public static void populateItemGroups(BuildCreativeModeTabContentsEvent event) {
        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            if (doorMaterialType == DoorMaterialType.IRON) continue;
            for (int i = DoorStyleType.values().length - 1; i >= 0; i--) { // Reverse reverse
                if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS && doorMaterialType.getCreativeModeTab() == CreativeModeTabs.BUILDING_BLOCKS) {
                    event.getEntries().putAfter(doorMaterialType.getPrimaryCraftingIngredient().getDefaultInstance(), ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(new DoorInfoRecord(doorMaterialType, DoorStyleType.values()[i])).get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
        }

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            // Metal doors
            event.getEntries().putAfter(new ItemStack(Items.IRON_DOOR), ModItemsImpl.IRON_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.COPPER_DOOR.getDefaultInstance(), ModItemsImpl.COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.EXPOSED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.WEATHERED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.OXIDIZED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.WAXED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.WAXED_EXPOSED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.WAXED_WEATHERED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//            event.insertAfter(Items.WAXED_OXIDIZED_COPPER_DOOR.getDefaultInstance(), ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static boolean isModLoaded(String modId) {
        if (LoadingModList.get().getModFileById(modId) != null) return true;
        return ModList.get().isLoaded(modId);
    }
}
