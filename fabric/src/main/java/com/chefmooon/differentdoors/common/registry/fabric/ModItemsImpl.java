package com.chefmooon.differentdoors.common.registry.fabric;

import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.item.fabric.DoubleDoorBlockItemImpl;
import com.chefmooon.differentdoors.common.registry.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.function.Supplier;

import static com.chefmooon.differentdoors.common.registry.ModItems.basicItem;

public class ModItemsImpl {
    public static final HashMap<DoorInfoRecord, Supplier<Item>> DOUBLE_DOOR_VARIANTS = new HashMap<>();

    public static final Supplier<Item> IRON_DOUBLE_DOOR = registerItemWithTab(ModItems.IRON_DOUBLE_DOOR,
            new DoubleDoorBlockItemImpl(ModBlocksImpl.IRON_DOUBLE_DOOR.get(),
                    basicItem()));
//    public static final Supplier<Item> COPPER_DOUBLE_DOOR = registerItemWithTab(ModItems.COPPER_DOUBLE_DOOR,
//            new DoubleDoorBlockItemImpl(ModBlocksImpl.COPPER_DOUBLE_DOOR.get(),
//                    basicItem()));
//    public static final Supplier<Item> EXPOSED_COPPER_DOUBLE_DOOR = registerItemWithTab(ModItems.EXPOSED_COPPER_DOUBLE_DOOR,
//            new DoubleDoorBlockItemImpl(ModBlocksImpl.EXPOSED_COPPER_DOUBLE_DOOR.get(),
//                    basicItem()));
//    public static final Supplier<Item> WEATHERED_COPPER_DOUBLE_DOOR = registerItemWithTab(ModItems.WEATHERED_COPPER_DOUBLE_DOOR,
//            new DoubleDoorBlockItemImpl(ModBlocksImpl.WEATHERED_COPPER_DOUBLE_DOOR.get(),
//                    basicItem()));
//    public static final Supplier<Item> OXIDIZED_COPPER_DOUBLE_DOOR = registerItemWithTab(ModItems.OXIDIZED_COPPER_DOUBLE_DOOR,
//            new DoubleDoorBlockItemImpl(ModBlocksImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get(),
//                    basicItem()));
//    public static final Supplier<Item> WAXED_COPPER_DOUBLE_DOOR = registerItemWithTab(ModItems.WAXED_COPPER_DOUBLE_DOOR,
//            new DoubleDoorBlockItemImpl(ModBlocksImpl.WAXED_COPPER_DOUBLE_DOOR.get(),
//                    basicItem()));
//    public static final Supplier<Item> WAXED_EXPOSED_COPPER_DOUBLE_DOOR = registerItemWithTab(ModItems.WAXED_EXPOSED_COPPER_DOUBLE_DOOR,
//            new DoubleDoorBlockItemImpl(ModBlocksImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get(),
//                    basicItem()));
//    public static final Supplier<Item> WAXED_WEATHERED_COPPER_DOUBLE_DOOR = registerItemWithTab(ModItems.WAXED_WEATHERED_COPPER_DOUBLE_DOOR,
//            new DoubleDoorBlockItemImpl(ModBlocksImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get(),
//                    basicItem()));
//    public static final Supplier<Item> WAXED_OXIDIZED_COPPER_DOUBLE_DOOR = registerItemWithTab(ModItems.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR,
//            new DoubleDoorBlockItemImpl(ModBlocksImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get(),
//                    basicItem()));

    private static void registerVanillaWoodenDoubleDoors() {
        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            if (doorMaterialType == DoorMaterialType.IRON) continue;
            for (DoorStyleType doorStyleType : DoorStyleType.values()) {
                DoorInfoRecord doorInfoRecord = new DoorInfoRecord(doorMaterialType, doorStyleType);
                Supplier<Item> item = registerItemWithTab(ModItems.DOUBLE_DOOR.withPrefix(doorStyleType.getSerializedName() + "_" + doorMaterialType.getSerializedName() + "_"), new DoubleDoorBlockItemImpl(ModBlocksImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get(),
                        basicItem(), doorMaterialType.isFuel() ? 1600 : 0));
                DOUBLE_DOOR_VARIANTS.put(doorInfoRecord, item);
            }
        }
    }

    public static Supplier<Item> registerItemWithTab(final ResourceLocation location, final Item item) {
        Registry.register(BuiltInRegistries.ITEM, location, item);
        ItemGroupEvents.modifyEntriesEvent(ModCreativeTabsImpl.ITEM_GROUP).register(entries -> entries.accept(item));
        return () -> item;
    }

    public static Supplier<Item> registerItem(final ResourceLocation location, final Item item) {
        Registry.register(BuiltInRegistries.ITEM, location, item);
        return () -> item;
    }

    public static void register() {
        registerVanillaWoodenDoubleDoors();
    }
}
