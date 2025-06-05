package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.data.types.DoorType;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends FabricRecipeProvider {
    private static RecipeOutput OUTPUT;
    public RecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        OUTPUT = recipeOutput;

        for (DoorType doorType : DoorType.values()) {
            buildLargeDoorRecipe(doorType, ModItemsImpl.LARGE_DOOR_VARIANTS.get(doorType).get());
        }
    }

    private void buildLargeDoorRecipe(DoorType doorType, Item item) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, item)
                .pattern("BBB")
                .pattern("ABA")
                .pattern("BBB")
                .define('A', doorType.getPrimaryCraftingIngredient())
                .define('B', doorType.getSecondaryCraftingIngredient())
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(
                        doorType.getPrimaryCraftingIngredient(),
                        doorType.getSecondaryCraftingIngredient()))
                ).save(OUTPUT, RecipeProvider.getSimpleRecipeName(item));
    }
}
