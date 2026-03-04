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
//public class CopperDoubleDoorWaxOffTrigger extends SimpleCriterionTrigger<CopperDoubleDoorWaxOffTrigger.TriggerInstance> {
//
//    @Override
//    public Codec<CopperDoubleDoorWaxOffTrigger.TriggerInstance> codec() {
//        return CopperDoubleDoorWaxOffTrigger.TriggerInstance.CODEC;
//    }
//
//    public void trigger(ServerPlayer player) {
//        this.trigger(player, CopperDoubleDoorWaxOffTrigger.TriggerInstance::test);
//    }
//
//    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
//        public static final Codec<CopperDoubleDoorWaxOffTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
//                builder -> builder.group(
//                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CopperDoubleDoorWaxOffTrigger.TriggerInstance::player))
//                        .apply(builder, CopperDoubleDoorWaxOffTrigger.TriggerInstance::new)
//        );
//        public static Criterion<CopperDoubleDoorWaxOffTrigger.TriggerInstance> simple() {
//            return ModAdvancements.COPPER_DOUBLE_DOOR_WAX_OFF_TRIGGER.get().createCriterion(
//                    new CopperDoubleDoorWaxOffTrigger.TriggerInstance(Optional.empty())
//            );
//        }
//
//        public boolean test() {
//            return true;
//        }
//    }
//}
