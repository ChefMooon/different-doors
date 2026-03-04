package com.chefmooon.differentdoors.common.registry.forge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.crafting.SlideToSwingShapelessRecipe;
import com.chefmooon.differentdoors.common.crafting.SwingToSlideShapelessRecipe;
import com.chefmooon.differentdoors.common.registry.ModRecipeSerializers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeSerializersImpl {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, DifferentDoors.MOD_ID);

    public static final Supplier<RecipeSerializer<?>> SLIDE_TO_SWING = RECIPE_SERIALIZERS.register(ModRecipeSerializers.SLIDE_TO_SWING.getPath(), SlideToSwingShapelessRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<?>> SWING_TO_SLIDE = RECIPE_SERIALIZERS.register(ModRecipeSerializers.SWING_TO_SLIDE.getPath(), SwingToSlideShapelessRecipe.Serializer::new);
//    public static final Supplier<RecipeSerializer<?>> WAXED_COPPER = RECIPE_SERIALIZERS.register(ModRecipeSerializers.WAXED_COPPER.getPath(), WaxedCopperShaplessRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
