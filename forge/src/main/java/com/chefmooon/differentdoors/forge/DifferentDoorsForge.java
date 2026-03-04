package com.chefmooon.differentdoors.forge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.registry.forge.*;
import com.chefmooon.differentdoors.common.util.forge.LoaderUtilImpl;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DifferentDoors.MOD_ID)
public class DifferentDoorsForge {
    public DifferentDoorsForge() {
        DifferentDoors.init();

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocksImpl.register(modEventBus);
        ModItemsImpl.register(modEventBus);
        ModCreativeTabsImpl.register(modEventBus);
        ModRecipeSerializersImpl.register(modEventBus);
//        ModAdvancementsImpl.register(modEventBus);

        LoaderUtilImpl.init(modEventBus);
    }
}
