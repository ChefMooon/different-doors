package com.chefmooon.differentdoors.common.registry.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.data.types.DoorType;
import com.chefmooon.differentdoors.common.item.fabric.LargeDoorBlockItemImpl;
import com.chefmooon.differentdoors.common.registry.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlockItemStateProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static com.chefmooon.differentdoors.common.registry.ModItems.basicItem;

public class ModItemsImpl {
    public static final HashMap<DoorType, Supplier<Item>> LARGE_DOOR_VARIANTS = new HashMap<>();

//    public static final Supplier<Item> SIMPLE_LARGE_DOOR = registerItemWithTab(ModItems.SIMPLE_LARGE_DOOR, new BlockItem(ModBlocksImpl.SIMPLE_LARGE_DOOR.get(), basicItem())); // TODO : remove after testing

    private static void registerLargeDoorVariants() {
        for (DoorType doorType : DoorType.values()) {
            Supplier<Item> item = registerItemWithTab(ModItems.LARGE_DOOR.withPrefix(doorType.name().toLowerCase() + "_"), new LargeDoorBlockItemImpl(ModBlocksImpl.LARGE_DOOR_VARIANTS.get(doorType).get(),
                    basicItem().component(DataComponents.BLOCK_STATE, new BlockItemStateProperties(Map.of()).with(LargeDoorBlock.SWING, Boolean.FALSE)), 1600));
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
