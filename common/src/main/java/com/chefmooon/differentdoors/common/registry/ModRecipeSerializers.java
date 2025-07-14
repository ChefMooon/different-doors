package com.chefmooon.differentdoors.common.registry;

import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.resources.ResourceLocation;

public class ModRecipeSerializers {

    public static final ResourceLocation SLIDE_TO_SWING = recipeSerializer("slide_to_swing");
    public static final ResourceLocation SWING_TO_SLIDE = recipeSerializer("swing_to_slide");
    private static ResourceLocation recipeSerializer(String string) {
        return TextUtil.res(string);
    }

    public static void init() {
    }
}
