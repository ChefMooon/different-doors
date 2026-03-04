package com.chefmooon.differentdoors.common.util.forge;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import com.chefmooon.differentdoors.common.registry.forge.ModItemsImpl;
import com.chefmooon.differentdoors.common.util.ModItemProperties;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
//import net.minecraft.world.item.component.BlockItemStateProperties;

public class ModItemPropertiesImpl {

    public static void addCustomItemProperties() {
        ModItemsImpl.DOUBLE_DOOR_VARIANTS.forEach((doorInfoRecord, item) -> registerLargeDoorProperties(item.get()));

        registerLargeDoorProperties(ModItemsImpl.IRON_DOUBLE_DOOR.get());
//        registerLargeDoorProperties(ModItemsImpl.COPPER_DOUBLE_DOOR.get());
//        registerLargeDoorProperties(ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get());
//        registerLargeDoorProperties(ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get());
//        registerLargeDoorProperties(ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get());
//        registerLargeDoorProperties(ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get());
//        registerLargeDoorProperties(ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get());
//        registerLargeDoorProperties(ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get());
//        registerLargeDoorProperties(ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get());
    }

    private static void registerLargeDoorProperties(Item item) {
        ItemProperties.register(item, ModItemProperties.DOUBLE_DOOR_SWING,
                (itemStack, clientLevel, livingEntity, i) -> {
                    if (itemStack.getTag() != null) {
                        CompoundTag blockStateTag = itemStack.getTag().getCompound("BlockStateTag");
                        String swingValue = blockStateTag.getString(DoubleDoorBlock.SWING.getName());
                        if (!swingValue.isEmpty()) {
                            boolean swing = Boolean.parseBoolean(swingValue);
                            return swing ? 1.0F : 0.0F;
                        }
                    }
                    return 0.0F;
                });
    }
}
