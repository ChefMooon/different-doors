package com.chefmooon.differentdoors;

import com.chefmooon.differentdoors.common.registry.*;
import com.chefmooon.differentdoors.common.util.ModItemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DifferentDoors {
    public static final String MOD_ID = "differentdoors";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    public static void init() {
        ModItemProperties.init(); // client?
//        ModDataComponentTypes.init();

        ModBlocks.init();
        ModItems.init();
        ModBlockEntities.init();
//        ModSounds.init();
        ModRecipeSerializers.init();
    }
}
