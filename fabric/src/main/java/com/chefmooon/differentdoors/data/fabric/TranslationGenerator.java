package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.ModBlocks;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class TranslationGenerator extends FabricLanguageProvider {
    protected TranslationGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        String MOD_ID = DifferentDoors.MOD_ID;
        String SUBTITLE = MOD_ID + ".subtitles.";
        String TOOLTIP = MOD_ID + ".tooltip.";

        translationBuilder.add("itemGroup." + MOD_ID, "Different Doors");

        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            if (doorMaterialType == DoorMaterialType.IRON || doorMaterialType == DoorMaterialType.COPPER) continue;
            for (DoorStyleType doorStyleType : DoorStyleType.values()) {
                String doorName = doorMaterialType.getSerializedName() + "_" + doorStyleType.getSerializedName();
                translationBuilder.add("block." + MOD_ID + "." + doorName + "_double_door", capitalize(doorName) + " Double Door");
            }
        }

        translationBuilder.add(ModBlocksImpl.IRON_DOUBLE_DOOR.get(), "Iron Double Door");
        translationBuilder.add(ModBlocksImpl.COPPER_DOUBLE_DOOR.get(), "Copper Double Door");
        translationBuilder.add(ModBlocksImpl.EXPOSED_COPPER_DOUBLE_DOOR.get(), "Exposed Copper Double Door");
        translationBuilder.add(ModBlocksImpl.OXIDIZED_COPPER_DOUBLE_DOOR.get(), "Oxidized Copper Double Door");
        translationBuilder.add(ModBlocksImpl.WEATHERED_COPPER_DOUBLE_DOOR.get(), "Weathered Copper Double Door");
        translationBuilder.add(ModBlocksImpl.WAXED_COPPER_DOUBLE_DOOR.get(), "Waxed Copper Double Door");
        translationBuilder.add(ModBlocksImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR.get(), "Waxed Exposed Copper Double Door");
        translationBuilder.add(ModBlocksImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR.get(), "Waxed Oxidized Copper Double Door");
        translationBuilder.add(ModBlocksImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR.get(), "Waxed Weathered Copper Double Door");

        translationBuilder.add(SUBTITLE + "double_door.open", "Double Door Opening");
        translationBuilder.add(SUBTITLE + "double_door.close", "Double Door Closing");

        translationBuilder.add(TOOLTIP + "double_door.swinging", "Swing");
        translationBuilder.add(TOOLTIP + "double_door.sliding", "Slide");
    }

    private static String capitalize(String name) {
        String[] parts = name.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0)))
                  .append(part.substring(1))
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }
}
