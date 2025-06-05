package com.chefmooon.differentdoors.common.registry.fabric;

import com.chefmooon.differentdoors.common.data.types.DoorType;
import com.chefmooon.differentdoors.common.registry.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.function.Supplier;

import static com.chefmooon.differentdoors.common.registry.ModItems.basicItem;

public class ModItemsImpl {
    public static final HashMap<DoorType, Supplier<Item>> LARGE_DOOR_VARIANTS = new HashMap<>();

//    public static final Supplier<Item> TEST_ITEM = registerItemWithTab(ModItems.TEST_ITEM, () -> new Item(basicItem()));
//    public static final Supplier<Item> LARGE_DOOR = registerItemWithTab(ModItems.LARGE_DOOR, () -> new BlockItem(ModBlocksImpl.LARGE_DOOR.get(), basicItem()));

    private static void registerLargeDoorVariants() {
        for (DoorType doorType : DoorType.values()) {
            Supplier<Item> item = registerItemWithTab(ModItems.LARGE_DOOR.withPrefix(doorType.name().toLowerCase() + "_"), new BlockItem(ModBlocksImpl.LARGE_DOOR_VARIANTS.get(doorType).get(), basicItem()));
            LARGE_DOOR_VARIANTS.put(doorType, item);
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
        registerLargeDoorVariants();
    }
}
