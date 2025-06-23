package com.chefmooon.differentdoors.common.registry;

import com.chefmooon.differentdoors.common.util.TextUtil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;

public class ModDataComponentTypes {
    public static final ResourceLocation LARGE_DOOR_SWING_DATA = dataComponent("large_door_swing_data");

    private static ResourceLocation dataComponent(String string) {
        return TextUtil.res(string);
    }

    @ExpectPlatform
    public static DataComponentType<Boolean> getLargeDoorSwingData() {
        throw new AssertionError();
    }

    public static void init() {
    }
}
