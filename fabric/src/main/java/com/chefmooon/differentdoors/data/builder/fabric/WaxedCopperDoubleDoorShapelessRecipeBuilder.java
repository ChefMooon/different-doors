//package com.chefmooon.differentdoors.data.builder.fabric;
//
//import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
//import com.chefmooon.differentdoors.common.crafting.WaxedCopperShaplessRecipe;
//import net.minecraft.advancements.Advancement;
//import net.minecraft.advancements.AdvancementRequirements;
//import net.minecraft.advancements.AdvancementRewards;
//import net.minecraft.advancements.Criterion;
//import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
//import net.minecraft.core.NonNullList;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.data.recipes.RecipeBuilder;
//import net.minecraft.data.recipes.RecipeCategory;
//import net.minecraft.data.recipes.RecipeOutput;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.component.BlockItemStateProperties;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.level.ItemLike;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Objects;
//
//public class WaxedCopperDoubleDoorShapelessRecipeBuilder implements RecipeBuilder {
//    private final RecipeCategory category;
//    private final Item result;
//    private final int count;
//    private final NonNullList<Ingredient> ingredients = NonNullList.create();
//    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
//    @Nullable
//    private String group;
//    private boolean swing = Boolean.FALSE;
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder(RecipeCategory category, ItemLike result, int count) {
//        this.category = category;
//        this.result = result.asItem();
//        this.count = count;
//    }
//
//    public static WaxedCopperDoubleDoorShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike result) {
//        return new WaxedCopperDoubleDoorShapelessRecipeBuilder(category, result, 1);
//    }
//
//    public static WaxedCopperDoubleDoorShapelessRecipeBuilder shapeless(RecipeCategory category, ItemLike result, int count) {
//        return new WaxedCopperDoubleDoorShapelessRecipeBuilder(category, result, count);
//    }
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder requires(TagKey<Item> tag) {
//        return this.requires(Ingredient.of(tag));
//    }
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder requires(ItemLike item) {
//        return this.requires((ItemLike)item, 1);
//    }
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder requires(ItemLike item, int quantity) {
//        for(int i = 0; i < quantity; ++i) {
//            this.requires(Ingredient.of(new ItemLike[]{item}));
//        }
//
//        return this;
//    }
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder requires(Ingredient ingredient) {
//        return this.requires((Ingredient)ingredient, 1);
//    }
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder requires(Ingredient ingredient, int quantity) {
//        for(int i = 0; i < quantity; ++i) {
//            this.ingredients.add(ingredient);
//        }
//
//        return this;
//    }
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
//        this.criteria.put(name, criterion);
//        return this;
//    }
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder group(@Nullable String groupName) {
//        this.group = groupName;
//        return this;
//    }
//
//    public WaxedCopperDoubleDoorShapelessRecipeBuilder setSwing(Boolean swing) {
//        this.swing = swing;
//        return this;
//    }
//
//    public Item getResult() {
//        return this.result;
//    }
//
//    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
//        this.ensureValid(id);
//        Advancement.Builder builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
//        Objects.requireNonNull(builder);
//        this.criteria.forEach(builder::addCriterion);
//        ItemStack itemStack = new ItemStack(this.result, this.count);
//        if (swing) {
//            itemStack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY.with(DoubleDoorBlock.SWING, Boolean.TRUE));
//        }
//        WaxedCopperShaplessRecipe waxedCopperShaplessRecipe = new WaxedCopperShaplessRecipe((String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), itemStack, this.ingredients);
//        recipeOutput.accept(id, waxedCopperShaplessRecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
//    }
//
//    private void ensureValid(ResourceLocation id) {
//        if (this.criteria.isEmpty()) {
//            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(id));
//        }
//    }
//}
