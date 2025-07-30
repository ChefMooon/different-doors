package com.chefmooon.differentdoors.common.registry.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.data.types.DoorType;
import com.chefmooon.differentdoors.common.item.neoforge.LargeDoorBlockItemImpl;
import com.chefmooon.differentdoors.common.registry.ModItems;
import com.google.common.collect.Sets;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.function.Supplier;

import static com.chefmooon.differentdoors.common.registry.ModItems.basicItem;

public class ModItemsImpl {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, DifferentDoors.MOD_ID);
    public static LinkedHashSet<Supplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    public static final HashMap<DoorType, Supplier<Item>> LARGE_DOOR_VARIANTS = new HashMap<>();

//    public static final Supplier<Item> TEST_ITEM = registerItemWithTab(ModItems.TEST_ITEM, () -> new Item(basicItem()));
//    public static final Supplier<Item> LARGE_DOOR = registerItemWithTab(ModItems.LARGE_DOOR, () -> new BlockItem(ModBlocksImpl.LARGE_DOOR.get(), basicItem()));

    private static void registerLargeDoorVariants() {
        for (DoorType doorType : DoorType.values()) {
            Supplier<Item> item = registerItemWithTab(ModItems.LARGE_DOOR.withPrefix(doorType.name().toLowerCase() + "_"), () -> new LargeDoorBlockItemImpl(ModBlocksImpl.LARGE_DOOR_VARIANTS.get(doorType).get(),
                    basicItem().component(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(LargeDoorBlock.SWING, Boolean.FALSE)), 1600));
            LARGE_DOOR_VARIANTS.put(doorType, item);
        }
    }

    public static Supplier<Item> registerItemWithTab(final ResourceLocation location, final Supplier<Item> supplier) {
        Supplier<Item> item = ITEMS.register(location.getPath(), supplier);
        CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    public static Supplier<Item> registerItem(final ResourceLocation location, final Supplier<Item> supplier) {
        return ITEMS.register(location.getPath(), supplier);
    }

    public static void register(IEventBus eventBus) {
        registerLargeDoorVariants();
        ITEMS.register(eventBus);
    }
}
