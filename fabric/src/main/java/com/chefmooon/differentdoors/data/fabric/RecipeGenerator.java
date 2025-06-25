package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.data.types.DoorType;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import com.chefmooon.differentdoors.data.builder.fabric.LargeSwingDoorShapedRecipeBuilder;
import com.chefmooon.differentdoors.data.builder.fabric.LargeSwingDoorShapelessRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;

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
            buildLargeDoorSwingRecipe(doorType, ModItemsImpl.LARGE_DOOR_VARIANTS.get(doorType).get());
            buildLargeDoorToLargeSwingDoorRecipe(ModItemsImpl.LARGE_DOOR_VARIANTS.get(doorType).get());
            buildLargeSwingDoorToLargeDoorRecipe(ModItemsImpl.LARGE_DOOR_VARIANTS.get(doorType).get());
        }
    }

    private void buildLargeDoorRecipe(DoorType doorType, Item item) {
        LargeSwingDoorShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, item)
                .pattern("BBB")
                .pattern("ABA")
                .pattern("BBB")
                .define('A', doorType.getPrimaryCraftingIngredient())
                .define('B', doorType.getSecondaryCraftingIngredient())
                .group("large_door")
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(
                        doorType.getPrimaryCraftingIngredient(),
                        doorType.getSecondaryCraftingIngredient()))
                ).save(OUTPUT, RecipeProvider.getSimpleRecipeName(item));
    }
    private void buildLargeDoorSwingRecipe(DoorType doorType, Item item) {
        LargeSwingDoorShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, item)
                .pattern("BBB")
                .pattern("ACA")
                .pattern("BBB")
                .define('A', doorType.getPrimaryCraftingIngredient())
                .define('B', doorType.getSecondaryCraftingIngredient())
                .define('C', Items.IRON_NUGGET)
                .group("large_door_swing")
                .setSwing(Boolean.TRUE)
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(
                        doorType.getPrimaryCraftingIngredient(),
                        doorType.getSecondaryCraftingIngredient(),
                        Items.IRON_NUGGET))
                ).save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "_swing");
    }

    private void buildLargeSwingDoorToLargeDoorRecipe(Item item) {
        LargeSwingDoorShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, item)
                .requires(item)
                .requires(Items.FLINT)
                .group("large_door")
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(item)))
                .save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "_swing_to_large_door");
    }

    private void buildLargeDoorToLargeSwingDoorRecipe(Item item) {
        LargeSwingDoorShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, item)
                .requires(item)
                .requires(Items.IRON_NUGGET)
                .group("large_door_swing")
                .setSwing(Boolean.TRUE)
                .unlockedBy("has_any", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(item)))
                .save(OUTPUT, RecipeProvider.getSimpleRecipeName(item) + "large_door_to_large_swing_door");
    }
}
