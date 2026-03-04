//package com.chefmooon.differentdoors.common.crafting;
//
//import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
//import com.chefmooon.differentdoors.common.registry.ModRecipeSerializers;
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.DataResult;
//import com.mojang.serialization.MapCodec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import it.unimi.dsi.fastutil.ints.IntList;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.NonNullList;
//import net.minecraft.core.component.DataComponents;
//import net.minecraft.core.registries.BuiltInRegistries;
//import net.minecraft.network.RegistryFriendlyByteBuf;
//import net.minecraft.network.codec.StreamCodec;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.*;
//import net.minecraft.world.level.Level;
//
//import java.util.Objects;
//
//public class WaxedCopperShaplessRecipe implements CraftingRecipe {
//    final String group;
//    final CraftingBookCategory category;
//    final ItemStack result;
//    final NonNullList<Ingredient> ingredients;
//
//    public WaxedCopperShaplessRecipe(String group, CraftingBookCategory category, ItemStack result, NonNullList<Ingredient> ingredients) {
//        this.group = group;
//        this.category = category;
//        this.result = result;
//        this.ingredients = ingredients;
//    }
//
//    public String getGroup() {
//        return this.group;
//    }
//
//
//    public NonNullList<Ingredient> getIngredients() {
//        return this.ingredients;
//    }
//
//    @Override
//    public CraftingBookCategory category() {
//        return this.category;
//    }
//
//    @Override
//    public boolean matches(CraftingInput input, Level level) {
//        if (input.ingredientCount() != this.ingredients.size()) {
//            return false;
//        } else {
//            boolean isResultSwinging = Boolean.TRUE.equals(this.result.get(DataComponents.BLOCK_STATE).get(DoubleDoorBlock.SWING));
//            ItemStack doorItemCheck1 = input.getItem(0);
//            if (doorItemCheck1.has(DataComponents.BLOCK_STATE) && doorItemCheck1.get(DataComponents.BLOCK_STATE).get(DoubleDoorBlock.SWING) != null) {
//                boolean isSwinging = Boolean.TRUE.equals(doorItemCheck1.get(DataComponents.BLOCK_STATE).get(DoubleDoorBlock.SWING));
//                if (isSwinging == isResultSwinging) {
//                    return input.stackedContents().canCraft(this, (IntList)null);
//                }
//            }
//            ItemStack doorItemCheck2 = input.getItem(1);
//            if (doorItemCheck2.has(DataComponents.BLOCK_STATE) && doorItemCheck2.get(DataComponents.BLOCK_STATE).get(DoubleDoorBlock.SWING) != null) {
//                boolean isSwinging = Boolean.TRUE.equals(doorItemCheck2.get(DataComponents.BLOCK_STATE).get(DoubleDoorBlock.SWING));
//                if (isSwinging == isResultSwinging) {
//                    return input.stackedContents().canCraft(this, (IntList)null);
//                }
//            }
//            return false;
//        }
//    }
//
//    @Override
//    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
//        return this.result.copy();
//    }
//
//    @Override
//    public boolean canCraftInDimensions(int width, int height) {
//        return width * height >= this.ingredients.size();
//    }
//
//    @Override
//    public ItemStack getResultItem(HolderLookup.Provider registries) {
//        return this.result;
//    }
//
//    @Override
//    public RecipeSerializer<?> getSerializer() {
//        return Objects.requireNonNull(BuiltInRegistries.RECIPE_SERIALIZER.get(ModRecipeSerializers.WAXED_COPPER));
//    }
//
//    public static class Serializer implements RecipeSerializer<WaxedCopperShaplessRecipe> {
//        private static final MapCodec<WaxedCopperShaplessRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter((shapelessRecipe) -> shapelessRecipe.group), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((shapelessRecipe) -> shapelessRecipe.category), ItemStack.STRICT_CODEC.fieldOf("result").forGetter((shapelessRecipe) -> shapelessRecipe.result), Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((list) -> {
//            Ingredient[] ingredients = (Ingredient[])list.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray((i) -> new Ingredient[i]);
//            if (ingredients.length == 0) {
//                return DataResult.error(() -> "No ingredients for waxed copper shapeless recipe");
//            } else {
//                return ingredients.length > 9 ? DataResult.error(() -> "Too many ingredients for waxed copper shapeless recipe") : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
//            }
//        }, DataResult::success).forGetter((WaxedCopperShaplessRecipe) -> WaxedCopperShaplessRecipe.ingredients)).apply(instance, WaxedCopperShaplessRecipe::new));
//        public static final StreamCodec<RegistryFriendlyByteBuf, WaxedCopperShaplessRecipe> STREAM_CODEC = StreamCodec.of(WaxedCopperShaplessRecipe.Serializer::toNetwork, WaxedCopperShaplessRecipe.Serializer::fromNetwork);
//
//        public Serializer() {
//        }
//
//        public MapCodec<WaxedCopperShaplessRecipe> codec() {
//            return CODEC;
//        }
//
//        public StreamCodec<RegistryFriendlyByteBuf, WaxedCopperShaplessRecipe> streamCodec() {
//            return STREAM_CODEC;
//        }
//
//        private static WaxedCopperShaplessRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
//            String string = buffer.readUtf();
//            CraftingBookCategory craftingBookCategory = (CraftingBookCategory)buffer.readEnum(CraftingBookCategory.class);
//            int i = buffer.readVarInt();
//            NonNullList<Ingredient> nonNullList = NonNullList.withSize(i, Ingredient.EMPTY);
//            nonNullList.replaceAll((ingredient) -> (Ingredient)Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
//            ItemStack itemStack = (ItemStack)ItemStack.STREAM_CODEC.decode(buffer);
//            return new WaxedCopperShaplessRecipe(string, craftingBookCategory, itemStack, nonNullList);
//        }
//
//        private static void toNetwork(RegistryFriendlyByteBuf buffer, WaxedCopperShaplessRecipe recipe) {
//            buffer.writeUtf(recipe.group);
//            buffer.writeEnum(recipe.category);
//            buffer.writeVarInt(recipe.ingredients.size());
//
//            for(Ingredient ingredient : recipe.ingredients) {
//                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
//            }
//
//            ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
//        }
//    }
//}
