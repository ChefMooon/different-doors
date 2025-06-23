package com.chefmooon.differentdoors.common.util.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import com.chefmooon.differentdoors.common.util.ModItemProperties;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.BlockItemStateProperties;

public class ModItemPropertiesImpl {

    public static void addCustomItemProperties() {
        ModItemsImpl.LARGE_DOOR_VARIANTS.forEach((entry, item) -> registerLargeDoorProperties(item.get()));
    }

    private static void registerLargeDoorProperties(Item item) {
        ItemProperties.register(item, ModItemProperties.LARGE_DOOR_SWING,
                (itemStack, clientLevel, livingEntity, i) -> {
                    BlockItemStateProperties properties = itemStack.get(DataComponents.BLOCK_STATE);
                    if (properties != null && properties.get(LargeDoorBlock.SWING) != null) {
                        boolean swing = Boolean.TRUE.equals(properties.get(LargeDoorBlock.SWING));
                        return swing ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });
    }
}
