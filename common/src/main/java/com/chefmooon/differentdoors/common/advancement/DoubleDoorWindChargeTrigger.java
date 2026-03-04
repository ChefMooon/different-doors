//package com.chefmooon.differentdoors.common.advancement;
//
//import com.chefmooon.differentdoors.common.registry.ModAdvancements;
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.advancements.Criterion;
//import net.minecraft.advancements.critereon.ContextAwarePredicate;
//import net.minecraft.advancements.critereon.EntityPredicate;
//import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
//import net.minecraft.server.level.ServerPlayer;
//
//import java.util.Optional;
//
//public class DoubleDoorWindChargeTrigger extends SimpleCriterionTrigger<DoubleDoorWindChargeTrigger.TriggerInstance> {
//    @Override
//    public Codec<DoubleDoorWindChargeTrigger.TriggerInstance> codec() {
//        return TriggerInstance.CODEC;
//    }
//
//    public void trigger(ServerPlayer player) {
//        this.trigger(player, TriggerInstance::test);
//    }
//
//    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
//        public static final Codec<DoubleDoorWindChargeTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
//                builder -> builder.group(
//                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(DoubleDoorWindChargeTrigger.TriggerInstance::player))
//                        .apply(builder, DoubleDoorWindChargeTrigger.TriggerInstance::new)
//        );
//        public static Criterion<TriggerInstance> simple() {
//            return ModAdvancements.DOUBLE_DOOR_WIND_CHARGE_TRIGGER.get().createCriterion(
//                    new DoubleDoorWindChargeTrigger.TriggerInstance(Optional.empty())
//            );
//        }
//
//        public boolean test() {
//            return true;
//        }
//    }
//}
