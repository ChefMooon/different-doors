package com.chefmooon.differentdoors.common.util;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;

import java.util.Optional;

public class ModTemplates {
    public static final ModelTemplate LARGE_DOOR = block("template_large_door", TextureSlot.ALL, TextureSlot.PARTICLE);
    public static final ModelTemplate LARGE_DOOR_FLIPPED = block("template_large_door_flipped", TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_ITEM = item("template_large_door_item", TextureSlot.ALL);

    private static ModelTemplate item(String string, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(TextUtil.res("item/" + string)), Optional.empty(), textureSlots);
    }

    private static ModelTemplate block(String string, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(TextUtil.res("block/" + string)), Optional.empty(), textureSlots);
    }
}
