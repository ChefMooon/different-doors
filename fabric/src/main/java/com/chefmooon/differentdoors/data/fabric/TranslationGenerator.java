package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
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
            for (DoorStyleType doorStyleType : DoorStyleType.values()) {
                String doorName = doorMaterialType.getSerializedName() + "_" + doorStyleType.getSerializedName();
                translationBuilder.add("block." + MOD_ID + "." + doorName + "_double_door", capitalize(doorName) + " Double Door");
            }
        }

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
