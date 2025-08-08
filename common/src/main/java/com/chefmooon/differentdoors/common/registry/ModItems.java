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

    private static ResourceLocation item(String string) {
        return TextUtil.res(string);
    }
    public static void init() {
    }
}
