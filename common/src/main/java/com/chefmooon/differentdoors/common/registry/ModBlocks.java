package com.chefmooon.differentdoors.common.registry;

import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.resources.ResourceLocation;

public class ModBlocks {
    public static final ResourceLocation TEST_DOOR = block("test_door");
    public static final ResourceLocation DOUBLE_DOOR = block("double_door");
    public static final ResourceLocation SIMPLE_LARGE_DOOR = block("simple_large_door");

    private static ResourceLocation block(String string) {
        return TextUtil.res(string);
    }
    public static void init() {
    }
}
