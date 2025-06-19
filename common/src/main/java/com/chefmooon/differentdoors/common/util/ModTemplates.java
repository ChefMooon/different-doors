package com.chefmooon.differentdoors.common.util;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;

import java.util.Optional;

public class ModTemplates {
//    public static final ModelTemplate LARGE_DOOR = block("template_large_door", TextureSlot.ALL, TextureSlot.PARTICLE);
//    public static final ModelTemplate LARGE_DOOR_FLIPPED = block("template_large_door_flipped", TextureSlot.ALL);

    public static final ModelTemplate LARGE_DOOR_TOP_LEFT = block("template_large_door_top_left", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_TOP = block("template_large_door_top", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_TOP_RIGHT = block("template_large_door_top_right", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_LEFT = block("template_large_door_left", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_CENTER = block("template_large_door_center", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_RIGHT = block("template_large_door_right", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_BOTTOM_LEFT = block("template_large_door_bottom_left", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_BOTTOM = block("template_large_door_bottom", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_BOTTOM_RIGHT = block("template_large_door_bottom_right", TextureSlot.PARTICLE, TextureSlot.ALL);

    public static final ModelTemplate LARGE_DOOR_BOTTOM_OPEN = block("template_large_door_bottom_open", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_BOTTOM_LEFT_OPEN = block("template_large_door_bottom_left_open", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_LEFT_OPEN = block("template_large_door_left_open", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_TOP_LEFT_OPEN = block("template_large_door_top_left_open", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_BOTTOM_RIGHT_OPEN = block("template_large_door_bottom_right_open", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_RIGHT_OPEN = block("template_large_door_right_open", TextureSlot.PARTICLE, TextureSlot.ALL);
    public static final ModelTemplate LARGE_DOOR_TOP_RIGHT_OPEN = block("template_large_door_top_right_open", TextureSlot.PARTICLE, TextureSlot.ALL);

    public static final ModelTemplate LARGE_DOOR_ITEM = item("template_large_door_item", TextureSlot.ALL);

    private static ModelTemplate item(String string, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(TextUtil.res("item/" + string)), Optional.empty(), textureSlots);
    }

    private static ModelTemplate block(String string, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(TextUtil.res("block/" + string)), Optional.empty(), textureSlots);
    }
}
