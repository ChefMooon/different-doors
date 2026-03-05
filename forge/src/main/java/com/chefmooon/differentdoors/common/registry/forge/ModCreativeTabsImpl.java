package com.chefmooon.differentdoors.common.registry.forge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabsImpl {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DifferentDoors.MOD_ID);
    public static final Supplier<CreativeModeTab> TAB_DIFFERENT_DOORS = CREATIVE_MODE_TAB.register(DifferentDoors.MOD_ID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + DifferentDoors.MOD_ID))
                    .icon(() -> new ItemStack(ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(new DoorInfoRecord(DoorMaterialType.OAK, DoorStyleType.TWELVE_LITE)).get()))
                    .displayItems((parameters, output) -> ModItemsImpl.CREATIVE_TAB_ITEMS.forEach((item) -> output.accept(item.get())))
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
