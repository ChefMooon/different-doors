package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.block.properties.DoorPartProperty;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlocksImpl;
import com.chefmooon.differentdoors.common.util.ModTemplates;
import com.chefmooon.differentdoors.common.util.TextUtil;
import net.minecraft.core.Direction;
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

        ResourceLocation BASE = ModTemplates.LARGE_DOOR.create(location,
               TextureMapping.particle(TextUtil.res("block/particle/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath())).put(TextureSlot.ALL, location), GENERATOR.modelOutput);

        ModTemplates.LARGE_DOOR_FLIPPED.create(ModelLocationUtils.getModelLocation(block.get(), "_flipped"),
                TextureMapping.singleSlot(TextureSlot.ALL, location), GENERATOR.modelOutput);

        GENERATOR.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get(),
                Variant.variant().with(VariantProperties.MODEL, BASE))
                .with(PropertyDispatch.property(LargeDoorBlock.FACING)
                        .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, BASE))
                )
                .with(PropertyDispatch.property(LargeDoorBlock.OPEN)
                        .select(Boolean.TRUE, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(Boolean.FALSE, Variant.variant().with(VariantProperties.MODEL, BASE))
                )
                .with(PropertyDispatch.property(LargeDoorBlock.POWERED)
                        .select(Boolean.TRUE, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(Boolean.FALSE, Variant.variant().with(VariantProperties.MODEL, BASE))
                )
                .with(PropertyDispatch.property(LargeDoorBlock.PART)
                        .select(DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BASE))
                        .select(DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BASE))
                )
        );
        GENERATOR.skipAutoItemBlock(block.get());
    }
}
