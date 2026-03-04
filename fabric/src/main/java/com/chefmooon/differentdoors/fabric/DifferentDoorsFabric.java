package com.chefmooon.differentdoors.fabric;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.fabric.DoubleDoorBlockImpl;
import com.chefmooon.differentdoors.common.registry.fabric.*;
import com.chefmooon.differentdoors.common.util.fabric.LoaderUtilImpl;
import net.fabricmc.api.ModInitializer;

public class DifferentDoorsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        DifferentDoors.init();

        ModBlocksImpl.register();
        ModItemsImpl.register();
        ModCreativeTabsImpl.register();
        ModRecipeSerializersImpl.register();
//        ModAdvancementsImpl.register();

        DoubleDoorBlockImpl.init();
        LoaderUtilImpl.init();
    }
}
