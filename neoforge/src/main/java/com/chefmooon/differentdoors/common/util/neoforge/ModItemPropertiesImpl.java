package com.chefmooon.differentdoors.common.util.neoforge;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import com.chefmooon.differentdoors.common.registry.neoforge.ModItemsImpl;
import com.chefmooon.differentdoors.common.util.ModItemProperties;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlockItemStateProperties;

public class ModItemPropertiesImpl {

    public static void addCustomItemProperties() {
        ModItemsImpl.DOUBLE_DOOR_VARIANTS.forEach((doorInfoRecord, item) -> registerLargeDoorProperties(item.get()));

        registerLargeDoorProperties(ModItemsImpl.IRON_DOUBLE_DOOR.get());
        registerLargeDoorProperties(ModItemsImpl.COPPER_DOUBLE_DOOR.get());
        registerLargeDoorProperties(ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get());
        registerLargeDoorProperties(ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get());
        registerLargeDoorProperties(ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get());
        registerLargeDoorProperties(ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get());
        registerLargeDoorProperties(ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get());
        registerLargeDoorProperties(ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get());
        registerLargeDoorProperties(ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get());
    }

    private static void registerLargeDoorProperties(Item item) {
        ItemProperties.register(item, ModItemProperties.DOUBLE_DOOR_SWING,
                (itemStack, clientLevel, livingEntity, i) -> {
                    BlockItemStateProperties properties = itemStack.get(DataComponents.BLOCK_STATE);
                    if (properties != null && properties.get(DoubleDoorBlock.SWING) != null) {
                        boolean swing = Boolean.TRUE.equals(properties.get(DoubleDoorBlock.SWING));
                        return swing ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });
    }
}
