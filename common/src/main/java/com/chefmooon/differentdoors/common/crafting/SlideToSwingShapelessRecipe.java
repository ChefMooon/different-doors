package com.chefmooon.differentdoors.common.crafting;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import com.chefmooon.differentdoors.common.registry.ModRecipeSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class SlideToSwingShapelessRecipe implements CraftingRecipe {
    final String group;
    final CraftingBookCategory category;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;

    public SlideToSwingShapelessRecipe(String group, CraftingBookCategory category, ItemStack result, NonNullList<Ingredient> ingredients) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    public RecipeSerializer<?> getSerializer() {
        return Objects.requireNonNull(BuiltInRegistries.RECIPE_SERIALIZER.get(ModRecipeSerializers.SLIDE_TO_SWING));
    }

    public String getGroup() {
        return this.group;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    public CraftingBookCategory category() {
        return this.category;
    }

    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        StackedContents stackedContents = new StackedContents();
        int i = 0;

        for(int j = 0; j < container.getContainerSize(); ++j) {
            ItemStack itemStack = container.getItem(j);
            if (!itemStack.isEmpty()) {
                ++i;
                stackedContents.accountStack(itemStack, 1);
            }
        }

        ItemStack doorItem = container.getItem(0);
        CompoundTag tag = doorItem.getTag();
        if (tag != null && tag.contains("BlockStateTag")) {
            String swingValue = tag.getCompound("BlockStateTag").getString(DoubleDoorBlock.SWING.getName());
            if (!swingValue.isEmpty()) {
                boolean isSwinging = Boolean.parseBoolean(swingValue);
                if (isSwinging) {
                    return false; // already swinging, cannot slide to swing
                }
            }
        }

        return i == this.ingredients.size() && stackedContents.canCraft(this, (IntList)null);

//        if (container.getItems().size() != this.ingredients.size()) {
//            return false;
//        } else {
//            ItemStack doorItem = container.getItem(0);
//            CompoundTag tag = doorItem.getTag();
//            if (tag != null && tag.contains("BlockStateTag")) {
//                String swingValue = tag.getCompound("BlockStateTag").getString(DoubleDoorBlock.SWING.getName());
//                if (!swingValue.isEmpty()) {
//                    boolean isSwinging = Boolean.parseBoolean(swingValue);
//                    if (isSwinging) {
//                        return false; // already swinging, cannot slide to swing
//                    }
//                }
//            }
//            if (input.getItem(0).has(DataComponents.BLOCK_STATE) && doorItem.get(DataComponents.BLOCK_STATE).get(DoubleDoorBlock.SWING) != null) {
//                boolean isSwinging = Boolean.TRUE.equals(doorItem.get(DataComponents.BLOCK_STATE).get(DoubleDoorBlock.SWING));
//                if (isSwinging) {
//                    return false; // already swinging, cannot slide to swing
//                }
//            }
//            return false;
//            return container.getItems().size() == 1 && this.ingredients.size() == 1 ? ((Ingredient)this.ingredients.getFirst()).test(input.getItem(0)) : input.stackedContents().canCraft(this, (IntList)null);
//        }
    }

    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        return this.result.copy();
    }

    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    public static class Serializer implements RecipeSerializer<SlideToSwingShapelessRecipe> {
//        private static final MapCodec<SlideToSwingShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(Codec.STRING.optionalFieldOf("group", "").forGetter((shapelessRecipe) -> shapelessRecipe.group), CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((shapelessRecipe) -> shapelessRecipe.category), ItemStack.STRICT_CODEC.fieldOf("result").forGetter((shapelessRecipe) -> shapelessRecipe.result), Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((list) -> {
//            Ingredient[] ingredients = (Ingredient[])list.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray((i) -> new Ingredient[i]);
//            if (ingredients.length == 0) {
//                return DataResult.error(() -> "No ingredients for shapeless recipe");
//            } else {
//                return ingredients.length > 9 ? DataResult.error(() -> "Too many ingredients for shapeless recipe") : DataResult.success(NonNullList.of(Ingredient.EMPTY, ingredients));
//            }
//        }, DataResult::success).forGetter((slideToSwingShapelessRecipe) -> slideToSwingShapelessRecipe.ingredients)).apply(instance, SlideToSwingShapelessRecipe::new));
//        public static final StreamCodec<RegistryFriendlyByteBuf, SlideToSwingShapelessRecipe> STREAM_CODEC = StreamCodec.of(SlideToSwingShapelessRecipe.Serializer::toNetwork, SlideToSwingShapelessRecipe.Serializer::fromNetwork);
//
//        public Serializer() {
//        }
//
//        public MapCodec<SlideToSwingShapelessRecipe> codec() {
//            return CODEC;
//        }
//
//        public StreamCodec<RegistryFriendlyByteBuf, SlideToSwingShapelessRecipe> streamCodec() {
//            return STREAM_CODEC;
//        }

        public SlideToSwingShapelessRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String string = GsonHelper.getAsString(json, "group", "");
            CraftingBookCategory craftingBookCategory = (CraftingBookCategory)CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", (String)null), CraftingBookCategory.MISC);
            NonNullList<Ingredient> nonNullList = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (nonNullList.isEmpty()) {
                throw new JsonParseException("No ingredients for slide to swing shapeless recipe");
            } else if (nonNullList.size() > 9) {
                throw new JsonParseException("Too many ingredients for slide to swing shapeless recipe");
            } else {
                ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                return new SlideToSwingShapelessRecipe(string, craftingBookCategory, itemStack, nonNullList);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonNullList = NonNullList.create();

            for(int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i), false);
                if (!ingredient.isEmpty()) {
                    nonNullList.add(ingredient);
                }
            }

            return nonNullList;
        }

        public SlideToSwingShapelessRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String string = buffer.readUtf();
            CraftingBookCategory craftingBookCategory = (CraftingBookCategory)buffer.readEnum(CraftingBookCategory.class);
            int i = buffer.readVarInt();
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(i, Ingredient.EMPTY);
            nonNullList.replaceAll(ignored -> Ingredient.fromNetwork(buffer));
            ItemStack itemStack = buffer.readItem();
            return new SlideToSwingShapelessRecipe(string, craftingBookCategory, itemStack, nonNullList);
        }

        public void toNetwork(FriendlyByteBuf buffer, SlideToSwingShapelessRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category);
            buffer.writeVarInt(recipe.ingredients.size());

            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.result);
        }
    }
}
