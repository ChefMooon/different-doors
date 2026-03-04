package com.chefmooon.differentdoors.common.util;

import com.chefmooon.differentdoors.DifferentDoors;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class TextUtil {
    public static MutableComponent getTranslatable(String string, Object... args) {
        return Component.translatable(DifferentDoors.MOD_ID + "." + string, args);
    }

    public static ResourceLocation res(String string) {
        return new ResourceLocation(DifferentDoors.MOD_ID, string);
    }
}
