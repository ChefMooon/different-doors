package com.chefmooon.differentdoors.common.registry;

import com.chefmooon.differentdoors.common.advancement.DoubleDoorChangeStyleTrigger;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;

import java.util.function.Supplier;

public class ModAdvancements {
    public static DoubleDoorChangeStyleTrigger DOUBLE_DOOR_CHANGE_STYLE_TRIGGER = new DoubleDoorChangeStyleTrigger();
//    public static final Supplier<DoubleDoorWindChargeTrigger> DOUBLE_DOOR_WIND_CHARGE_TRIGGER = registerTrigger("double_door_wind_charge", DoubleDoorWindChargeTrigger::new);
//
//    public static final Supplier<CopperDoubleDoorWaxOnTrigger> COPPER_DOUBLE_DOOR_WAX_ON_TRIGGER = registerTrigger("copper_double_door_wax_on", CopperDoubleDoorWaxOnTrigger::new);
//    public static final Supplier<CopperDoubleDoorWaxOffTrigger> COPPER_DOUBLE_DOOR_WAX_OFF_TRIGGER = registerTrigger("copper_double_door_wax_off", CopperDoubleDoorWaxOffTrigger::new);
    @ExpectPlatform
    public static <T extends CriterionTrigger<?>> Supplier<T> registerTrigger(String name, Supplier<T> triggerSupplier) {
        throw new AssertionError();
    }

    public static void init() {
        CriteriaTriggers.register(DOUBLE_DOOR_CHANGE_STYLE_TRIGGER);
    }
}
