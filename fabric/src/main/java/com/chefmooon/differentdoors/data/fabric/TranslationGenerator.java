package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.ModBlocks;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import com.chefmooon.differentdoors.common.tag.ModTags;
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
        String FORMATTED_MOD_ID = "Different Doors";
        String SUBTITLE = MOD_ID + ".subtitles.";
        String TOOLTIP = MOD_ID + ".tooltip.";
        String ADVANCEMENT = MOD_ID + ".advancement.";

        translationBuilder.add("itemGroup." + MOD_ID, FORMATTED_MOD_ID);

        for (DoorMaterialType doorMaterialType : DoorMaterialType.values()) {
            if (doorMaterialType == DoorMaterialType.IRON || doorMaterialType == DoorMaterialType.COPPER) continue;
            for (DoorStyleType doorStyleType : DoorStyleType.values()) {
                String doorName = doorStyleType.getSerializedName() + "_" + doorMaterialType.getSerializedName();
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

        // Tags
        translationBuilder.add(ModTags.WOODEN_DOUBLE_DOORS_ITEM, "Wooden Double Doors");
        translationBuilder.add(ModTags.DOUBLE_DOORS_ITEM, "Double Doors");
        translationBuilder.add(ModTags.WOODEN_DOUBLE_DOORS, "Wooden Double Doors");
        translationBuilder.add(ModTags.DOUBLE_DOORS, "Double Doors");

        // Subtitles
        translationBuilder.add(SUBTITLE + "double_door.open", "Double Door Opening");
        translationBuilder.add(SUBTITLE + "double_door.close", "Double Door Closing");

        // Tooltips
        translationBuilder.add(TOOLTIP + "double_door.swinging", "Swing");
        translationBuilder.add(TOOLTIP + "double_door.sliding", "Slide");

        // Advancements
        translationBuilder.add(ADVANCEMENT + "root", FORMATTED_MOD_ID);
        translationBuilder.add(ADVANCEMENT + "root.desc", "Find the perfect door");

        translationBuilder.add(ADVANCEMENT + "change_style", "Change Swing Style");
        translationBuilder.add(ADVANCEMENT + "change_style.desc", "Shift-Right click with a Pickaxe to change the door open/close style");

        translationBuilder.add(ADVANCEMENT + "wind_charge_double_door", "Shut the Front Door!");
        translationBuilder.add(ADVANCEMENT + "wind_charge_double_door.desc", "Any door you can open by hand can also be opened with a wind charge. Enjoy the breeze!");

        translationBuilder.add(ADVANCEMENT + "copper_doors", "Oxidized");
        translationBuilder.add(ADVANCEMENT + "copper_doors.desc", "Copper doors will oxidize over time. Shift-Right click with an Axe to scrape off the oxidation");

        translationBuilder.add(ADVANCEMENT + "copper_double_door_wax_on", "Wax On");
        translationBuilder.add(ADVANCEMENT + "copper_double_door_wax_on.desc", "Shift-Right click with Honeycomb to apply wax to a copper door");

        translationBuilder.add(ADVANCEMENT + "copper_double_door_wax_off", "Wax Off");
        translationBuilder.add(ADVANCEMENT + "copper_double_door_wax_off.desc", "Shift-Right click with an Axe to remove wax from a copper door");
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
