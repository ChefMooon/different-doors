package com.chefmooon.differentdoors.common.registry;


import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {
    public static Item.Properties basicItem() {
        return new Item.Properties();
    }

    public static Item.Properties noStack() {
        return new Item.Properties().stacksTo(1);
    }

//    public static final ResourceLocation TEST_ITEM = item("test_item");
    public static final ResourceLocation DOUBLE_DOOR = item("double_door");
    public static final ResourceLocation SIMPLE_LARGE_DOOR = item("simple_large_door");

    public static final ResourceLocation IRON_DOUBLE_DOOR = item("iron_double_door");
    public static final ResourceLocation COPPER_DOUBLE_DOOR = item("copper_double_door");
    public static final ResourceLocation EXPOSED_COPPER_DOUBLE_DOOR = item("exposed_copper_double_door");
    public static final ResourceLocation WEATHERED_COPPER_DOUBLE_DOOR = item("weathered_copper_double_door");
    public static final ResourceLocation OXIDIZED_COPPER_DOUBLE_DOOR = item("oxidized_copper_double_door");
    public static final ResourceLocation WAXED_COPPER_DOUBLE_DOOR = item("waxed_copper_double_door");
    public static final ResourceLocation WAXED_EXPOSED_COPPER_DOUBLE_DOOR = item("waxed_exposed_copper_double_door");
    public static final ResourceLocation WAXED_WEATHERED_COPPER_DOUBLE_DOOR = item("waxed_weathered_copper_double_door");
    public static final ResourceLocation WAXED_OXIDIZED_COPPER_DOUBLE_DOOR = item("waxed_oxidized_copper_double_door");

    private static ResourceLocation item(String string) {
        return TextUtil.res(string);
    }
    public static void init() {
    }
}
