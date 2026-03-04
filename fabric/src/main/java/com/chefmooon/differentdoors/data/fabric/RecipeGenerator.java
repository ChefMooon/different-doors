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
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    private static Consumer<FinishedRecipe> OUTPUT;
    public RecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        OUTPUT = exporter;

        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            if (doorMaterialType == DoorMaterialType.IRON) continue; // Skip metal doors for now
            for (DoorStyleType doorStyleType : DoorStyleType.values()) {
                DoorInfoRecord doorInfoRecord = new DoorInfoRecord(doorMaterialType, doorStyleType);
                buildDoubleDoorRecipe(doorInfoRecord, ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get());
//                buildDoubleDoorSwingRecipe(doorInfoRecord, ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get());
//                buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get());
//                buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(doorInfoRecord).get());
            }
        }

        DoorInfoRecord ironDoorInfoRecord = new DoorInfoRecord(DoorMaterialType.IRON, null);
        buildDoubleDoorRecipe(ironDoorInfoRecord, ModItemsImpl.IRON_DOUBLE_DOOR.get());
//        buildDoubleDoorSwingRecipe(ironDoorInfoRecord, ModItemsImpl.IRON_DOUBLE_DOOR.get());
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.IRON_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.IRON_DOUBLE_DOOR.get());

//        DoorInfoRecord copperDoorInfoRecord = new DoorInfoRecord(DoorMaterialType.COPPER, null);
//        buildDoubleDoorRecipe(copperDoorInfoRecord, ModItemsImpl.COPPER_DOUBLE_DOOR.get());
//        buildDoubleDoorSwingRecipe(copperDoorInfoRecord, ModItemsImpl.COPPER_DOUBLE_DOOR.get());
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.COPPER_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.COPPER_DOUBLE_DOOR.get());
//
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get());
//
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get());
//
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get());
//
//        buildWaxedCopperDoubleDoorRecipe(ModItemsImpl.COPPER_DOUBLE_DOOR.get(), ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get());
//        buildWaxedCopperDoubleDoorRecipe(ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR.get(), ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get());
//        buildWaxedCopperDoubleDoorRecipe(ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get(), ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get());
//        buildWaxedCopperDoubleDoorRecipe(ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get(), ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get());
//
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR.get());
//
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get());
//
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get());
//
//        buildDoubleDoorToSwingDoubleDoorRecipe(ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get());
//        buildSwingDoubleDoorToDoubleDoorRecipe(ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get());
    }

    private void buildDoubleDoorRecipe(DoorInfoRecord doorInfoRecord, Item item) {
        ItemLike ingredient = doorInfoRecord.doorStyleType() != null ? doorInfoRecord.doorStyleType().getBaseStyleIngredient() : doorInfoRecord.doorMaterialType().getPrimaryCraftingIngredient();
        SwingDoubleDoorShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, item)
                .pattern("ABA")
                .pattern("BBB")
                .define('A', ingredient)
                .define('B', doorInfoRecord.doorMaterialType().getSecondaryCraftingIngredient())
                .group("large_door_slide")
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(
                        doorInfoRecord.doorMaterialType().getPrimaryCraftingIngredient(),
                        doorInfoRecord.doorMaterialType().getSecondaryCraftingIngredient()).build())
                ).save(OUTPUT, RecipeProvider.getSimpleRecipeName(item));
    }
    private void buildDoubleDoorSwingRecipe(DoorInfoRecord doorInfoRecord, Item item) {
        ItemLike ingredient = doorInfoRecord.doorStyleType() != null ? doorInfoRecord.doorStyleType().getBaseStyleIngredient() : doorInfoRecord.doorMaterialType().getPrimaryCraftingIngredient();
        SwingDoubleDoorShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, item)
                .pattern("BBB")
                .pattern("ABA")
                .define('A', ingredient)
                .define('B', doorInfoRecord.doorMaterialType().getSecondaryCraftingIngredient())
                .group("large_door_swing")
                .setSwing(Boolean.TRUE)
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(
                        doorInfoRecord.doorMaterialType().getPrimaryCraftingIngredient(),
                        doorInfoRecord.doorMaterialType().getSecondaryCraftingIngredient()).build())
                ).save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "_swing");
    }

    private void buildSwingDoubleDoorToDoubleDoorRecipe(Item item) {
        SwingDoubleDoorShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, item)
                .requires(item)
                .group("large_door_slide")
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(item).build()))
                .save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "_swing_to_" + RecipeProvider.getSimpleRecipeName(item) + "_slide");
    }

    private void buildDoubleDoorToSwingDoubleDoorRecipe(Item item) {
        SwingDoubleDoorShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, item)
                .requires(item)
                .group("large_door_swing")
                .setSwing(Boolean.TRUE)
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(item).build()))
                .save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "_slide_to_" + RecipeProvider.getSimpleRecipeName(item) + "_swing");
    }

//    private void buildWaxedCopperDoubleDoorRecipe(Item item, Item waxedItem) {
//        WaxedCopperDoubleDoorShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, waxedItem)
//                .requires(item)
//                .requires(Items.HONEYCOMB)
//                .group("waxed_copper_double_door")
//                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(item, Items.HONEYCOMB)))
//                .save(OUTPUT, RecipeProvider.getSimpleRecipeName(waxedItem) + "_slide_from_" + RecipeProvider.getSimpleRecipeName(Items.HONEYCOMB));
//
//        WaxedCopperDoubleDoorShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, waxedItem)
//                .requires(item)
//                .requires(Items.HONEYCOMB)
//                .group("waxed_copper_double_door")
//                .setSwing(Boolean.TRUE)
//                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(item, Items.HONEYCOMB)))
//                .save(OUTPUT, RecipeProvider.getSimpleRecipeName(waxedItem) + "_swing_from_" + RecipeProvider.getSimpleRecipeName(Items.HONEYCOMB));
//    }
}
