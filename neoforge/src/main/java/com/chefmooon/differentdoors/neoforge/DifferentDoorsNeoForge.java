package com.chefmooon.differentdoors.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.registry.neoforge.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(DifferentDoors.MOD_ID)
public class DifferentDoorsNeoForge {
    public DifferentDoorsNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        DifferentDoors.init();

        ModBlocksImpl.register(modEventBus);
        ModItemsImpl.register(modEventBus);
//        ModBlockEntitiesImpl.register(modEventBus);
//        ModDataComponentTypesImpl.register(modEventBus);
        ModCreativeTabsImpl.register(modEventBus);
        ModSoundsImpl.register(modEventBus);
        ModRecipeSerializersImpl.register(modEventBus);
    }
}
