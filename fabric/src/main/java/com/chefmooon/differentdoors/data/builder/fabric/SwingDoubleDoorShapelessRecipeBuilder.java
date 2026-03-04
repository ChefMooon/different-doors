package com.chefmooon.differentdoors.data.builder.fabric;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SwingDoubleDoorShapelessRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
//    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
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

    public SwingDoubleDoorShapelessRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterionTrigger) {
        this.advancement.addCriterion(name, criterionTrigger);
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

    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        this.ensureValid(recipeId);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        ItemStack itemStack = new ItemStack(this.result, this.count);
        CompoundTag blockStateTag = new CompoundTag();
        blockStateTag.putString(DoubleDoorBlock.SWING.getName(), String.valueOf(swing));
        itemStack.getOrCreateTag().put("BlockStateTag", blockStateTag);
//        if (swing) {
//            SlideToSwingShapelessRecipe slideToSwingShapelessRecipe = new SlideToSwingShapelessRecipe((String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), itemStack, this.ingredients);
//            finishedRecipeConsumer.accept(id, slideToSwingShapelessRecipe, builder.build(recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/"))));
//        } else {
//            SwingToSlideShapelessRecipe swingToSlideShapelessRecipe = new SwingToSlideShapelessRecipe((String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), itemStack, this.ingredients);
//            finishedRecipeConsumer.accept(id, swingToSlideShapelessRecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
//        }
        finishedRecipeConsumer.accept(new ShapelessRecipeBuilder.Result(recipeId, this.result, this.count, this.group == null ? "" : this.group, determineBookCategory(this.category), this.ingredients, this.advancement, recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation id) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
