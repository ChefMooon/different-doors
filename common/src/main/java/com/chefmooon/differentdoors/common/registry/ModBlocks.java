package com.chefmooon.differentdoors.common.registry;

import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.resources.ResourceLocation;

public class ModBlocks {
    public static final ResourceLocation TEST_DOOR = block("test_door");
    public static final ResourceLocation DOUBLE_DOOR = block("double_door");
    public static final ResourceLocation SIMPLE_LARGE_DOOR = block("simple_large_door");

    public static final ResourceLocation IRON_DOUBLE_DOOR = block("iron_double_door");
//    public static final ResourceLocation COPPER_DOUBLE_DOOR = block("copper_double_door");
//    public static final ResourceLocation EXPOSED_COPPER_DOUBLE_DOOR = block("exposed_copper_double_door");
//    public static final ResourceLocation WEATHERED_COPPER_DOUBLE_DOOR = block("weathered_copper_double_door");
//    public static final ResourceLocation OXIDIZED_COPPER_DOUBLE_DOOR = block("oxidized_copper_double_door");
//    public static final ResourceLocation WAXED_COPPER_DOUBLE_DOOR = block("waxed_copper_double_door");
//    public static final ResourceLocation WAXED_EXPOSED_COPPER_DOUBLE_DOOR = block("waxed_exposed_copper_double_door");
//    public static final ResourceLocation WAXED_WEATHERED_COPPER_DOUBLE_DOOR = block("waxed_weathered_copper_double_door");
//    public static final ResourceLocation WAXED_OXIDIZED_COPPER_DOUBLE_DOOR = block("waxed_oxidized_copper_double_door");

    private static ResourceLocation block(String string) {
        return TextUtil.res(string);
    }
    public static void init() {
    }
}
