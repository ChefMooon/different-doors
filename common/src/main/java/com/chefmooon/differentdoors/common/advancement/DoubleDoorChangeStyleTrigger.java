package com.chefmooon.differentdoors.common.advancement;

import com.chefmooon.differentdoors.common.registry.ModAdvancements;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class DoubleDoorChangeStyleTrigger extends SimpleCriterionTrigger<DoubleDoorChangeStyleTrigger.TriggerInstance> {

    @Override
    public Codec<DoubleDoorChangeStyleTrigger.TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player,TriggerInstance::test);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<DoubleDoorChangeStyleTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DoubleDoorChangeStyleTrigger.TriggerInstance::player))
                        .apply(builder, DoubleDoorChangeStyleTrigger.TriggerInstance::new)
        );
        public static Criterion<DoubleDoorChangeStyleTrigger.TriggerInstance> simple() {
            return ModAdvancements.DOUBLE_DOOR_CHANGE_STYLE_TRIGGER.get().createCriterion(
                    new DoubleDoorChangeStyleTrigger.TriggerInstance(Optional.empty())
            );
        }

        public boolean test() {
            return true;
        }
    }
}
