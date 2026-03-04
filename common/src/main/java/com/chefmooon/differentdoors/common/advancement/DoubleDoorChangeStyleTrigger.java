package com.chefmooon.differentdoors.common.advancement;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.registry.ModAdvancements;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class DoubleDoorChangeStyleTrigger extends SimpleCriterionTrigger<DoubleDoorChangeStyleTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(DifferentDoors.MOD_ID, "double_door_change_style");
    @Override
    protected DoubleDoorChangeStyleTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new DoubleDoorChangeStyleTrigger.TriggerInstance(predicate);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, DoubleDoorChangeStyleTrigger.TriggerInstance::test);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        public TriggerInstance(ContextAwarePredicate player) {
            super(DoubleDoorChangeStyleTrigger.ID, player);
        }

        public static DoubleDoorChangeStyleTrigger.TriggerInstance simple() {
            return new DoubleDoorChangeStyleTrigger.TriggerInstance(ContextAwarePredicate.ANY);
        }

        public boolean test() {
            return true;
        }
    }
}
