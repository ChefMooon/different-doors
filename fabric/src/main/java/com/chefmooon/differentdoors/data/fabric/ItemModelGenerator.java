package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.data.types.DoorType;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import com.chefmooon.differentdoors.common.util.ModTemplates;
import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class ItemModelGenerator {
    private static ItemModelGenerators GENERATOR;
    public static void generateItemModels(ItemModelGenerators itemModelGenerators) {
        GENERATOR = itemModelGenerators;

        ModBlocksImpl.LARGE_DOOR_VARIANTS.forEach(((doorType, blockSupplier) -> registerLargeDoorItemModel(doorType, blockSupplier)));
    }

    private static void registerLargeDoorItemModel(DoorType doorType, Supplier<Block> block) {
        ModelTemplates.FLAT_ITEM.createWithSuffix(block.get(), "_slide",
                TextureMapping.singleSlot(TextureSlot.LAYER0, TextUtil.res("item/" + doorType.name().toLowerCase() + "_large_door")), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.createWithSuffix(block.get(), "_swing",
                TextureMapping.singleSlot(TextureSlot.LAYER0, TextUtil.res("item/" + doorType.name().toLowerCase() + "_large_door_swing")), GENERATOR.output);
    }
}
