package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.block.properties.DoorPartProperty;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import com.chefmooon.differentdoors.common.util.ModTemplates;
import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class BlockModelGenerator {
    private static BlockModelGenerators GENERATOR;
    public static void generateBlockModels(BlockModelGenerators blockModelGenerators) {
        GENERATOR = blockModelGenerators;

        ModBlocksImpl.LARGE_DOOR_VARIANTS.forEach((doorType, block) -> largeDoorModel(block));
    }

    private static void largeDoorModel(Supplier<Block> block) {
        ResourceLocation location = ModelLocationUtils.getModelLocation(block.get());

        TextureMapping textureMapping = TextureMapping.particle(TextUtil.res("block/particle/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()))
                .put(TextureSlot.ALL, location);

        ResourceLocation TOP_LEFT = ModTemplates.LARGE_DOOR_TOP_LEFT.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP = ModTemplates.LARGE_DOOR_TOP.create(location.withSuffix("_" + DoorPartProperty.TOP.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT = ModTemplates.LARGE_DOOR_TOP_RIGHT.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation LEFT = ModTemplates.LARGE_DOOR_LEFT.create(location.withSuffix("_" + DoorPartProperty.LEFT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation CENTER = ModTemplates.LARGE_DOOR_CENTER.create(location.withSuffix("_" + DoorPartProperty.CENTER.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation RIGHT = ModTemplates.LARGE_DOOR_RIGHT.create(location.withSuffix("_" + DoorPartProperty.RIGHT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_LEFT = ModTemplates.LARGE_DOOR_BOTTOM_LEFT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM = ModTemplates.LARGE_DOOR_BOTTOM.create(location.withSuffix("_" + DoorPartProperty.BOTTOM.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT = ModTemplates.LARGE_DOOR_BOTTOM_RIGHT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT.getSerializedName()), textureMapping, GENERATOR.modelOutput);

        ResourceLocation BOTTOM_OPEN = ModTemplates.LARGE_DOOR_BOTTOM_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_LEFT_OPEN = ModTemplates.LARGE_DOOR_BOTTOM_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation LEFT_OPEN = ModTemplates.LARGE_DOOR_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.LEFT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_LEFT_OPEN = ModTemplates.LARGE_DOOR_TOP_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT_OPEN = ModTemplates.LARGE_DOOR_BOTTOM_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation RIGHT_OPEN = ModTemplates.LARGE_DOOR_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.RIGHT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT_OPEN = ModTemplates.LARGE_DOOR_TOP_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);

        GENERATOR.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get(),
                        Variant.variant().with(VariantProperties.MODEL, BOTTOM))
                        .with(BlockModelGenerators.createHorizontalFacingDispatch())
                        .with(PropertyDispatch.properties(LargeDoorBlock.OPEN, LargeDoorBlock.PART)
                                .select(Boolean.TRUE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_OPEN))
                                .select(Boolean.TRUE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                                .select(Boolean.TRUE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_OPEN))
                                .select(Boolean.TRUE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT_OPEN))
                                .select(Boolean.TRUE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                                .select(Boolean.TRUE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT_OPEN))
                                .select(Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_OPEN))
                                .select(Boolean.TRUE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                                .select(Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_OPEN))

                                .select(Boolean.FALSE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT))
                                .select(Boolean.FALSE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, TOP))
                                .select(Boolean.FALSE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT))
                                .select(Boolean.FALSE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT))
                                .select(Boolean.FALSE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, CENTER))
                                .select(Boolean.FALSE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT))
                                .select(Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT))
                                .select(Boolean.FALSE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM))
                                .select(Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT))
                        )
        );
        GENERATOR.skipAutoItemBlock(block.get());
    }
}
