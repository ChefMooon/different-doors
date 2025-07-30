package com.chefmooon.differentdoors.common.util;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class LoaderUtil {
    @ExpectPlatform
    public static boolean isModLoaded(String modId) {
        return false;
    }
}
