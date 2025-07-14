package com.chefmooon.differentdoors.common.registry.fabric;

import com.chefmooon.differentdoors.common.registry.ModRecipeSerializers;
import com.chefmooon.differentdoors.common.crafting.SlideToSwingShapelessRecipe;
import com.chefmooon.differentdoors.common.crafting.SwingToSlideShapelessRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

public class ModRecipeSerializersImpl {
    public static final Supplier<RecipeSerializer<?>> SLIDE_TO_SWING = registerRecipeSerializer(ModRecipeSerializers.SLIDE_TO_SWING, SlideToSwingShapelessRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<?>> SWING_TO_SLIDE = registerRecipeSerializer(ModRecipeSerializers.SWING_TO_SLIDE, SwingToSlideShapelessRecipe.Serializer::new);

    public static <B extends RecipeSerializer<?>> Supplier<B> registerRecipeSerializer(ResourceLocation resourceLocation, Supplier<B> supplier) {
        return registerRecipeSerializer(resourceLocation, supplier, BuiltInRegistries.RECIPE_SERIALIZER);
    }
    public static <R, T extends R> Supplier<T> registerRecipeSerializer(ResourceLocation resourceLocation, Supplier<T> supplier, Registry<R> registry) {
        T object = supplier.get();
        Registry.register(registry, resourceLocation, object);
        return () -> object;
    }

    public static void register() {
    }
}
