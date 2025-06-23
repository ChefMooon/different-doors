package com.chefmooon.differentdoors.common.util;

import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static ResourceLocation LARGE_DOOR_SWING = property("large_door_swing");

    private static ResourceLocation property(String string) {
        return TextUtil.res(string);
    }
    public static void init() {
    }
}
