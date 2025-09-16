package com.chefmooon.differentdoors;

import com.chefmooon.differentdoors.common.registry.ModAdvancements;
import com.chefmooon.differentdoors.common.registry.ModBlocks;
import com.chefmooon.differentdoors.common.registry.ModItems;
import com.chefmooon.differentdoors.common.registry.ModRecipeSerializers;
import com.chefmooon.differentdoors.common.util.ModItemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DifferentDoors {
    public static final String MOD_ID = "differentdoors";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    public static void init() {
        ModItemProperties.init(); // client?

        ModBlocks.init();
        ModItems.init();
        ModRecipeSerializers.init();
        ModAdvancements.init();
    }
}
