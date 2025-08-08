package com.chefmooon.differentdoors.data.builder.fabric;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import com.chefmooon.differentdoors.common.crafting.SlideToSwingShapelessRecipe;
import com.chefmooon.differentdoors.common.crafting.SwingToSlideShapelessRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SwingDoubleDoorShapelessRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
    @Nullable
    private String group;
    private boolean swing = Boolean.FALSE;

    public SwingDoubleDoorShapelessRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
        this.category = category;
        this.result = result.asItem();
        this.count = count;
    }

    public static SwingDoubleDoorShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike result) {
        return new SwingDoubleDoorShapelessRecipeBuilder(category, result, 1);
    }

    public static SwingDoubleDoorShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike result, int count) {
        return new SwingDoubleDoorShapelessRecipeBuilder(category, result, count);
    }

    public SwingDoubleDoorShapelessRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public SwingDoubleDoorShapelessRecipeBuilder requires(ItemLike item) {
        return this.requires((ItemLike)item, 1);
    }

    public SwingDoubleDoorShapelessRecipeBuilder requires(ItemLike item, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.requires(Ingredient.of(new ItemLike[]{item}));
        }

        return this;
    }

    public SwingDoubleDoorShapelessRecipeBuilder requires(Ingredient ingredient) {
        return this.requires((Ingredient)ingredient, 1);
    }

    public SwingDoubleDoorShapelessRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public SwingDoubleDoorShapelessRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public SwingDoubleDoorShapelessRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    public SwingDoubleDoorShapelessRecipeBuilder setSwing(Boolean swing) {
        this.swing = swing;
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        ItemStack itemStack = new ItemStack(this.result, this.count);
        itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(DoubleDoorBlock.SWING, swing));
        if (swing) {
            SlideToSwingShapelessRecipe slideToSwingShapelessRecipe = new SlideToSwingShapelessRecipe((String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), itemStack, this.ingredients);
            recipeOutput.accept(id, slideToSwingShapelessRecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
        } else {
            SwingToSlideShapelessRecipe swingToSlideShapelessRecipe = new SwingToSlideShapelessRecipe((String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), itemStack, this.ingredients);
            recipeOutput.accept(id, swingToSlideShapelessRecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
        }
    }

    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(id));
        }
    }
}
