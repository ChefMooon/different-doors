package com.chefmooon.differentdoors.common.registry.fabric;

import com.chefmooon.differentdoors.common.registry.ModDataComponentTypes;
import com.chefmooon.differentdoors.common.util.TextUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.function.UnaryOperator;

public class ModDataComponentTypesImpl {
    public static final DataComponentType<Boolean> LARGE_DOOR_DATA = register(
            ModDataComponentTypes.LARGE_DOOR_SWING_DATA.getPath(),
            (builder) -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
    );
    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return (DataComponentType) Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, TextUtil.res(name), ((DataComponentType.Builder)builder.apply(DataComponentType.builder())).build());
    }

    public static DataComponentType<Boolean> getLargeDoorSwingData() {
        return LARGE_DOOR_DATA;
    }
    public static void register() {
    }
}
