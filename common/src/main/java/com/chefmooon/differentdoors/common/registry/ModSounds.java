package com.chefmooon.differentdoors.common.registry;

import com.chefmooon.differentdoors.common.util.TextUtil;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class ModSounds {
//    public static final Supplier<SoundEvent> DOUBLE_DOOR_OPEN = registerSound("block.double_door.open");
//    public static final Supplier<SoundEvent> DOUBLE_DOOR_CLOSE = registerSound("block.double_door.close");

    private static Supplier<SoundEvent> registerSound(String string) {
        return registerSound(TextUtil.res(string), () -> SoundEvent.createVariableRangeEvent(TextUtil.res(string)));
    }

    @ExpectPlatform
    public static <T extends SoundEvent> Supplier<T> registerSound(ResourceLocation id, Supplier<T> supplier) {
        throw new AssertionError();
    }

    public static void init() {
    }
}
