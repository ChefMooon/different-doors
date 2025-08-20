package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import com.chefmooon.differentdoors.common.util.TextUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ItemModelGenerator {
    private static final List<DoorModelData> GENERATED_DOOR_MODELS = List.of(new DoorModelData("slide", 0.0F), new DoorModelData("swing", 1.0F));
    private static ItemModelGenerators GENERATOR;
    public static void generateItemModels(ItemModelGenerators itemModelGenerators) {
        GENERATOR = itemModelGenerators;

        ModItemsImpl.DOUBLE_DOOR_VARIANTS.forEach((ItemModelGenerator::registerDoubleDoorItemModel));
//        ModItemsImpl.METAL_DOUBLE_DOOR_VARIANTS.forEach((ItemModelGenerator::registerDoubleDoorItemModel));
        registerDoubleDoorItemModel(new DoorInfoRecord(DoorMaterialType.IRON, null), ModItemsImpl.IRON_DOUBLE_DOOR);
        registerCopperDoubleDoorItemModel(ModItemsImpl.COPPER_DOUBLE_DOOR, ModItemsImpl.WAXED_COPPER_DOUBLE_DOOR);
        registerCopperDoubleDoorItemModel(ModItemsImpl.EXPOSED_COPPER_DOUBLE_DOOR, ModItemsImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR);
        registerCopperDoubleDoorItemModel(ModItemsImpl.OXIDIZED_COPPER_DOUBLE_DOOR, ModItemsImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR);
        registerCopperDoubleDoorItemModel(ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR, ModItemsImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR);
    }

    private static void registerCopperDoubleDoorItemModel(Supplier<Item> item, Supplier<Item> waxedItem) {
        ResourceLocation itemModelLocation = ModelLocationUtils.getModelLocation(item.get());
        ModelTemplates.FLAT_ITEM.create(itemModelLocation.withSuffix("_slide"),
                TextureMapping.singleSlot(TextureSlot.LAYER0, itemModelLocation), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.create(itemModelLocation.withSuffix("_swing"),
                TextureMapping.singleSlot(TextureSlot.LAYER0, itemModelLocation.withSuffix("_swing")), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item.get()),
                TextureMapping.singleSlot(TextureSlot.LAYER0, itemModelLocation), GENERATOR.output, ItemModelGenerator::generateBaseDoorTemplate);

        ResourceLocation waxedItemModelLocation = ModelLocationUtils.getModelLocation(waxedItem.get());
        ModelTemplates.FLAT_ITEM.create(waxedItemModelLocation.withSuffix("_slide"),
                TextureMapping.singleSlot(TextureSlot.LAYER0, itemModelLocation), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.create(waxedItemModelLocation.withSuffix("_swing"),
                TextureMapping.singleSlot(TextureSlot.LAYER0, itemModelLocation.withSuffix("_swing")), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(waxedItem.get()),
                TextureMapping.singleSlot(TextureSlot.LAYER0, itemModelLocation), GENERATOR.output, ItemModelGenerator::generateBaseDoorTemplate);
    }

    private static void registerDoubleDoorItemModel(DoorInfoRecord doorInfoRecord, Supplier<Item> item) {
        String doorStyleType = doorInfoRecord.doorStyleType() != null ? "_" + doorInfoRecord.doorStyleType().getSerializedName() : "";
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item.get(), "_slide"),
                TextureMapping.singleSlot(TextureSlot.LAYER0, TextUtil.res("item/" + doorInfoRecord.doorMaterialType().getSerializedName() + doorStyleType + "_double_door")), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item.get(), "_swing"),
                TextureMapping.singleSlot(TextureSlot.LAYER0, TextUtil.res("item/" + doorInfoRecord.doorMaterialType().getSerializedName() + doorStyleType + "_double_door_swing")), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item.get()),
                TextureMapping.singleSlot(TextureSlot.LAYER0, TextUtil.res("item/" + doorInfoRecord.doorMaterialType().getSerializedName() + doorStyleType + "_double_door")), GENERATOR.output, ItemModelGenerator::generateBaseDoorTemplate);
    }

    public static JsonObject generateBaseDoorTemplate(ResourceLocation modelLocation, Map<TextureSlot, ResourceLocation> modelGetter) {
        JsonObject jsonObject = ModelTemplates.FLAT_ITEM.createBaseTemplate(modelLocation, modelGetter);
        JsonArray jsonArray = new JsonArray();

        for (DoorModelData doorModelData : GENERATED_DOOR_MODELS) {
            JsonObject modelObject = new JsonObject();
            JsonObject predicateObject = new JsonObject();
            predicateObject.addProperty(TextUtil.res("double_door_swing").toString(), doorModelData.getItemModelIndex());
            modelObject.add("predicate", predicateObject);
            modelObject.addProperty("model",modelLocation.withSuffix("_" + doorModelData.getName()).toString());
            jsonArray.add(modelObject);
        }

        jsonObject.add("overrides", jsonArray);
        return jsonObject;
    }

    private record DoorModelData(String name, float itemModelIndex) {
        DoorModelData(String name, float itemModelIndex) {
            this.name = name;
            this.itemModelIndex = itemModelIndex;
        }

        public String getName() {
            return name;
        }

        public float getItemModelIndex() {
            return itemModelIndex;
        }
    }
}
