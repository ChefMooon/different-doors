package com.chefmooon.differentdoors.common.registry;

import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.resources.ResourceLocation;

public class ModBlockEntities {
    public static final ResourceLocation LARGE_DOOR = blockEntity("large_door");

    private static ResourceLocation blockEntity(String string) {
        return TextUtil.res(string);
    }

    public static void init() {
    }
}
