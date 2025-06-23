package com.chefmooon.differentdoors.common.registry.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.registry.ModDataComponentTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponentTypesImpl {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, DifferentDoors.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> LARGE_DOOR_DATA = DATA_COMPONENTS.registerComponentType(
            ModDataComponentTypes.LARGE_DOOR_SWING_DATA.getPath(),
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
    );

    public static DataComponentType<Boolean> getLargeDoorSwingData() {
        return LARGE_DOOR_DATA.get();
    }
    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
