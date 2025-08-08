package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import com.chefmooon.differentdoors.data.builder.fabric.SwingDoubleDoorShapedRecipeBuilder;
import com.chefmooon.differentdoors.data.builder.fabric.SwingDoubleDoorShapelessRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
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

        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            for (DoorStyleType doorStyleType : DoorStyleType.values()) {
                DoorInfoRecord doorInfoRecord = new DoorInfoRecord(doorMaterialType, doorStyleType);
                buildDoubleDoorRecipe(doorInfoRecord, ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get());
                buildDoubleDoorSwingRecipe(doorInfoRecord, ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get());
                buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get());
                buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get());
            }
        }
    }

    private void buildDoubleDoorRecipe(DoorInfoRecord doorInfoRecord, Item item) {
        SwingDoubleDoorShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, item)
                .pattern("ABA")
                .pattern("BBB")
                .define('A', doorInfoRecord.doorStyleType().getBaseStyleIngredient())
                .define('B', doorInfoRecord.doorMaterialType().getSecondaryCraftingIngredient())
                .group("large_door_slide")
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(
                        doorInfoRecord.doorMaterialType().getPrimaryCraftingIngredient(),
                        doorInfoRecord.doorMaterialType().getSecondaryCraftingIngredient()))
                ).save(OUTPUT, RecipeProvider.getSimpleRecipeName(item));
    }
    private void buildDoubleDoorSwingRecipe(DoorInfoRecord doorInfoRecord, Item item) {
        SwingDoubleDoorShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, item)
                .pattern("BBB")
                .pattern("ABA")
                .define('A', doorInfoRecord.doorStyleType().getBaseStyleIngredient())
                .define('B', doorInfoRecord.doorMaterialType().getSecondaryCraftingIngredient())
                .group("large_door_swing")
                .setSwing(Boolean.TRUE)
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(
                        doorInfoRecord.doorMaterialType().getPrimaryCraftingIngredient(),
                        doorInfoRecord.doorMaterialType().getSecondaryCraftingIngredient()))
                ).save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "_swing");
    }

    private void buildSwingDoubleDoorToDoubleDoorRecipe(Item item) {
        SwingDoubleDoorShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, item)
                .requires(item)
                .group("large_door_slide")
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(item)))
                .save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "_swing_to_" + RecipeProvider.getSimpleRecipeName(item) + "_slide");
    }

    private void buildDoubleDoorToSwingDoubleDoorRecipe(Item item) {
        SwingDoubleDoorShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, item)
                .requires(item)
                .group("large_door_swing")
                .setSwing(Boolean.TRUE)
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(item)))
                .save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "_slide_to_" + RecipeProvider.getSimpleRecipeName(item) + "_swing");
    }
}
