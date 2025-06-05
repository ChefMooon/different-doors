package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.data.types.DoorType;
import com.chefmooon.differentdoors.common.util.ModTemplates;
import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

public class ItemModelGenerator {
    private static ItemModelGenerators GENERATOR;
    public static void generateItemModels(ItemModelGenerators itemModelGenerators) {
        GENERATOR = itemModelGenerators;

        for (DoorType doorType : DoorType.values()) {
            registerLargeDoorItemModel(doorType.name().toLowerCase());
        }
    }

    private static void registerLargeDoorItemModel(String string) {
        ResourceLocation location = TextUtil.res("item/" + string + "_large_door");
        ResourceLocation textureLocation = TextUtil.res("block/" + string + "_large_door");
        ModTemplates.LARGE_DOOR_ITEM.create(location, TextureMapping.singleSlot(TextureSlot.ALL, textureLocation), GENERATOR.output);
    }
}
