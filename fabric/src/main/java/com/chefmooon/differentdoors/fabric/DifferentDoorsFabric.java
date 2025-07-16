package com.chefmooon.differentdoors.fabric;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.fabric.LargeDoorBlockImpl;
import com.chefmooon.differentdoors.common.registry.fabric.*;
import net.fabricmc.api.ModInitializer;

public class DifferentDoorsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DifferentDoors.init();

        ModBlocksImpl.register();
        ModItemsImpl.register();
//        ModBlockEntitiesImpl.register();
//        ModDataComponentTypesImpl.register();
        ModCreativeTabsImpl.register();
//        ModSoundsImpl.register();
        ModRecipeSerializersImpl.register();

        LargeDoorBlockImpl.init();
    }
}
