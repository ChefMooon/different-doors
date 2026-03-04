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
//public class CopperDoubleDoorWaxOnTrigger extends SimpleCriterionTrigger<CopperDoubleDoorWaxOnTrigger.TriggerInstance> {
//
//    @Override
//    public Codec<CopperDoubleDoorWaxOnTrigger.TriggerInstance> codec() {
//        return CopperDoubleDoorWaxOnTrigger.TriggerInstance.CODEC;
//    }
//
//    public void trigger(ServerPlayer player) {
//        this.trigger(player, CopperDoubleDoorWaxOnTrigger.TriggerInstance::test);
//    }
//
//    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
//        public static final Codec<CopperDoubleDoorWaxOnTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
//                builder -> builder.group(
//                                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CopperDoubleDoorWaxOnTrigger.TriggerInstance::player))
//                        .apply(builder, CopperDoubleDoorWaxOnTrigger.TriggerInstance::new)
//        );
//        public static Criterion<CopperDoubleDoorWaxOnTrigger.TriggerInstance> simple() {
//            return ModAdvancements.COPPER_DOUBLE_DOOR_WAX_ON_TRIGGER.get().createCriterion(
//                    new CopperDoubleDoorWaxOnTrigger.TriggerInstance(Optional.empty())
//            );
//        }
//
//        public boolean test() {
//            return true;
//        }
//    }
//}
