package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.advancement.DoubleDoorChangeStyleTrigger;
import com.chefmooon.differentdoors.common.data.DoorInfoRecord;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import com.chefmooon.differentdoors.common.registry.fabric.ModItemsImpl;
import com.chefmooon.differentdoors.common.util.TextUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class AdvancementGenerator extends FabricAdvancementProvider {
    protected AdvancementGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement()
                .display(new DisplayInfo(
                        new ItemStack(ModItemsImpl.DOUBLE_DOOR_VARIANTS.get(new DoorInfoRecord(DoorMaterialType.OAK, DoorStyleType.TWELVE_LITE)).get()),
                        TextUtil.getTranslatable("advancement.root"),
                        TextUtil.getTranslatable("advancement.root.desc"),
                        new ResourceLocation("textures/block/stripped_oak_log.png"),
                        FrameType.TASK,
                        true, true, false
                ))
                .addCriterion("has_door", RecipeProvider.has(ItemTags.DOORS))
                .build(getAdvancementName("root"));
        consumer.accept(root);

        // Double Door Change Style
        Advancement changeStyle = getAdvancement(root, Items.GOLDEN_PICKAXE, "change_style", FrameType.TASK, true, false, false)
                .addCriterion("double_door_change_style_trigger", DoubleDoorChangeStyleTrigger.TriggerInstance.simple())
                .build(getAdvancementName("change_style"));
        consumer.accept(changeStyle);

//        // Wind Charge Open/Close Double Door
//        Advancement windChargeDoubleDoor = getAdvancement(root, Items.WIND_CHARGE, "wind_charge_double_door", AdvancementType.TASK, true, true, true)
//                .addCriterion("wind_charge_double_door", DoubleDoorWindChargeTrigger.TriggerInstance.simple())
//                .build(getAdvancementName("wind_charge_double_door"));
//        consumer.accept(windChargeDoubleDoor);
//
//        // Copper Doors
//        Advancement copperDoors = getAdvancement(root, ModItemsImpl.WEATHERED_COPPER_DOUBLE_DOOR.get(), "copper_doors", AdvancementType.TASK, true, false, false)
//                .addCriterion(getHasName("weathered_copper_double_door"), InventoryChangeTrigger.TriggerInstance.hasItems(ModItemsImpl.COPPER_DOUBLE_DOOR.get()))
//                .build(getAdvancementName("copper_doors"));
//        consumer.accept(copperDoors);
//
//        // Wax On Copper Doors
//        Advancement copperDoubleDoorWaxOn = getAdvancement(copperDoors, Items.HONEYCOMB, "copper_double_door_wax_on", AdvancementType.TASK, true, false, false)
//                .addCriterion("copper_double_door_wax_on", CopperDoubleDoorWaxOnTrigger.TriggerInstance.simple())
//                .build(getAdvancementName("copper_double_door_wax_on"));
//        consumer.accept(copperDoubleDoorWaxOn);
//
//        // Wax Off Copper Doors
//        Advancement copperDoubleDoorWaxOff = getAdvancement(copperDoubleDoorWaxOn, Items.GOLDEN_AXE, "copper_double_door_wax_off", AdvancementType.TASK, true, false, false)
//                .addCriterion("copper_double_door_wax_off", CopperDoubleDoorWaxOffTrigger.TriggerInstance.simple())
//                .build(getAdvancementName("copper_double_door_wax_off"));
//        consumer.accept(copperDoubleDoorWaxOff);
    }

    private static Advancement.Builder getAdvancement(Advancement parent, ItemLike icon, String name, FrameType type, boolean showToast, boolean announceChat, boolean hidden) {
        return Advancement.Builder.advancement()
                .parent(parent)
                .display(new ItemStack(icon),
                        TextUtil.getTranslatable("advancement." + name),
                        TextUtil.getTranslatable("advancement." + name + ".desc"),
                        null, type, showToast, announceChat, hidden);
    }

    private static String getHasName(String string) {
        return "has_" + string;
    }

    private static ResourceLocation getAdvancementName(String string) {
        return TextUtil.res("main/" + string);
    }
}
