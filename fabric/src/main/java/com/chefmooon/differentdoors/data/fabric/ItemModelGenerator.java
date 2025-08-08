package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
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
    }

    private static void registerDoubleDoorItemModel(DoorInfoRecord doorInfoRecord, Supplier<Item> item) {
        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item.get(), "_slide"),
                TextureMapping.singleSlot(TextureSlot.LAYER0, TextUtil.res("item/" + doorInfoRecord.doorMaterialType().getSerializedName() + "_" + doorInfoRecord.doorStyleType().getSerializedName() + "_double_door")), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item.get(), "_swing"),
                TextureMapping.singleSlot(TextureSlot.LAYER0, TextUtil.res("item/" + doorInfoRecord.doorMaterialType().getSerializedName() + "_" + doorInfoRecord.doorStyleType().getSerializedName() + "_double_door_swing")), GENERATOR.output);

        ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item.get()),
                TextureMapping.singleSlot(TextureSlot.LAYER0, TextUtil.res("item/" + doorInfoRecord.doorMaterialType().getSerializedName() + "_" + doorInfoRecord.doorStyleType().getSerializedName() + "_double_door")), GENERATOR.output, ItemModelGenerator::generateBaseDoorTemplate);
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
