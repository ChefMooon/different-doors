package com.chefmooon.differentdoors.data.fabric;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
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
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class BlockModelGenerator {
    private static BlockModelGenerators GENERATOR;
    public static void generateBlockModels(BlockModelGenerators blockModelGenerators) {
        GENERATOR = blockModelGenerators;

        ModBlocksImpl.DOUBLE_DOOR_VARIANTS.forEach((doorInfoRecord, block) -> doubleDoorModel(block, null));

        doubleDoorModel(ModBlocksImpl.IRON_DOUBLE_DOOR, null);
        copperDoubleDoorModel(ModBlocksImpl.COPPER_DOUBLE_DOOR, ModBlocksImpl.WAXED_COPPER_DOUBLE_DOOR);
        copperDoubleDoorModel(ModBlocksImpl.EXPOSED_COPPER_DOUBLE_DOOR, ModBlocksImpl.WAXED_EXPOSED_COPPER_DOUBLE_DOOR);
        copperDoubleDoorModel(ModBlocksImpl.OXIDIZED_COPPER_DOUBLE_DOOR, ModBlocksImpl.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR);
        copperDoubleDoorModel(ModBlocksImpl.WEATHERED_COPPER_DOUBLE_DOOR, ModBlocksImpl.WAXED_WEATHERED_COPPER_DOUBLE_DOOR);
    }

    private static void copperDoubleDoorModel(Supplier<Block> block, Supplier<Block> waxedBlock) {
        doubleDoorModel(block, ModelLocationUtils.getModelLocation(block.get()));
        doubleDoorModel(waxedBlock, ModelLocationUtils.getModelLocation(block.get()));
    }

    private static void doubleDoorModel(Supplier<Block> block, @Nullable ResourceLocation textureLocation) {
        ResourceLocation location = ModelLocationUtils.getModelLocation(block.get());
        if (textureLocation == null) textureLocation = location;

        TextureMapping textureMapping = TextureMapping.particle(TextUtil.res("block/particle/" + textureLocation.getPath().replace("block/", ""))) // revert when texture is fixed
//        TextureMapping textureMapping = TextureMapping.particle(textureLocation)
                .put(TextureSlot.ALL, textureLocation);
        TextureMapping textureMappingSwing = TextureMapping.particle(TextUtil.res("block/particle/" + textureLocation.getPath().replace("block/", ""))) // revert when texture is fixed
//        TextureMapping textureMappingSwing = TextureMapping.particle(textureLocation.withSuffix("_swing"))
                .put(TextureSlot.ALL, textureLocation.withSuffix("_swing"));

        // Slide Models
        ResourceLocation TOP_LEFT = ModTemplates.DOUBLE_DOOR_TOP_LEFT.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP = ModTemplates.DOUBLE_DOOR_TOP.create(location.withSuffix("_" + DoorPartProperty.TOP.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT = ModTemplates.DOUBLE_DOOR_TOP_RIGHT.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation LEFT = ModTemplates.DOUBLE_DOOR_LEFT.create(location.withSuffix("_" + DoorPartProperty.LEFT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation CENTER = ModTemplates.DOUBLE_DOOR_CENTER.create(location.withSuffix("_" + DoorPartProperty.CENTER.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation RIGHT = ModTemplates.DOUBLE_DOOR_RIGHT.create(location.withSuffix("_" + DoorPartProperty.RIGHT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_LEFT = ModTemplates.DOUBLE_DOOR_BOTTOM_LEFT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM = ModTemplates.DOUBLE_DOOR_BOTTOM.create(location.withSuffix("_" + DoorPartProperty.BOTTOM.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT = ModTemplates.DOUBLE_DOOR_BOTTOM_RIGHT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT.getSerializedName()), textureMapping, GENERATOR.modelOutput);

        ResourceLocation BOTTOM_OPEN = ModTemplates.DOUBLE_DOOR_BOTTOM_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_LEFT_OPEN = ModTemplates.DOUBLE_DOOR_BOTTOM_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation LEFT_OPEN = ModTemplates.DOUBLE_DOOR_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.LEFT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_LEFT_OPEN = ModTemplates.DOUBLE_DOOR_TOP_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT_OPEN = ModTemplates.DOUBLE_DOOR_BOTTOM_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation RIGHT_OPEN = ModTemplates.DOUBLE_DOOR_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.RIGHT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT_OPEN = ModTemplates.DOUBLE_DOOR_TOP_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT.getSerializedName() + "_open"), textureMapping, GENERATOR.modelOutput);

        ResourceLocation BOTTOM_LEFT_OPEN_EXT = ModTemplates.DOUBLE_DOOR_BOTTOM_LEFT_OPEN_EXT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT_OPEN_EXT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation LEFT_OPEN_EXT = ModTemplates.DOUBLE_DOOR_LEFT_OPEN_EXT.create(location.withSuffix("_" + DoorPartProperty.LEFT_OPEN_EXT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_LEFT_OPEN_EXT = ModTemplates.DOUBLE_DOOR_TOP_LEFT_OPEN_EXT.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT_OPEN_EXT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT_OPEN_EXT = ModTemplates.DOUBLE_DOOR_BOTTOM_RIGHT_OPEN_EXT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation RIGHT_OPEN_EXT = ModTemplates.DOUBLE_DOOR_RIGHT_OPEN_EXT.create(location.withSuffix("_" + DoorPartProperty.RIGHT_OPEN_EXT.getSerializedName()), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT_OPEN_EXT = ModTemplates.DOUBLE_DOOR_TOP_RIGHT_OPEN_EXT.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT_OPEN_EXT.getSerializedName()), textureMapping, GENERATOR.modelOutput);

        ResourceLocation BOTTOM_LEFT_OPEN_ALT = ModTemplates.DOUBLE_DOOR_BOTTOM_LEFT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT.getSerializedName() + "_open_alt"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation LEFT_OPEN_ALT = ModTemplates.DOUBLE_DOOR_LEFT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.LEFT.getSerializedName() + "_open_alt"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_LEFT_OPEN_ALT = ModTemplates.DOUBLE_DOOR_TOP_LEFT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT.getSerializedName() + "_open_alt"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT_OPEN_ALT = ModTemplates.DOUBLE_DOOR_BOTTOM_RIGHT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT.getSerializedName() + "_open_alt"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation RIGHT_OPEN_ALT = ModTemplates.DOUBLE_DOOR_RIGHT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.RIGHT.getSerializedName() + "_open_alt"), textureMapping, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT_OPEN_ALT = ModTemplates.DOUBLE_DOOR_TOP_RIGHT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT.getSerializedName() + "_open_alt"), textureMapping, GENERATOR.modelOutput);

        // Swing Models
        ResourceLocation TOP_LEFT_SWING = ModTemplates.DOUBLE_DOOR_TOP_LEFT.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation TOP_SWING = ModTemplates.DOUBLE_DOOR_TOP.create(location.withSuffix("_" + DoorPartProperty.TOP.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT_SWING = ModTemplates.DOUBLE_DOOR_TOP_RIGHT.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation LEFT_SWING = ModTemplates.DOUBLE_DOOR_LEFT.create(location.withSuffix("_" + DoorPartProperty.LEFT.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation CENTER_SWING = ModTemplates.DOUBLE_DOOR_CENTER.create(location.withSuffix("_" + DoorPartProperty.CENTER.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation RIGHT_SWING = ModTemplates.DOUBLE_DOOR_RIGHT.create(location.withSuffix("_" + DoorPartProperty.RIGHT.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_LEFT_SWING = ModTemplates.DOUBLE_DOOR_BOTTOM_LEFT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_SWING = ModTemplates.DOUBLE_DOOR_BOTTOM.create(location.withSuffix("_" + DoorPartProperty.BOTTOM.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT_SWING = ModTemplates.DOUBLE_DOOR_BOTTOM_RIGHT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT.getSerializedName() + "_swing"), textureMappingSwing, GENERATOR.modelOutput);

        ResourceLocation BOTTOM_SWING_OPEN = ModTemplates.DOUBLE_DOOR_SWING_BOTTOM_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM.getSerializedName() + "_swing_open"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_LEFT_SWING_OPEN = ModTemplates.DOUBLE_DOOR_SWING_BOTTOM_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT.getSerializedName() + "_swing_open"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation LEFT_SWING_OPEN = ModTemplates.DOUBLE_DOOR_SWING_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.LEFT.getSerializedName() + "_swing_open"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation TOP_LEFT_SWING_OPEN = ModTemplates.DOUBLE_DOOR_SWING_TOP_LEFT_OPEN.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT.getSerializedName() + "_swing_open"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT_SWING_OPEN = ModTemplates.DOUBLE_DOOR_SWING_BOTTOM_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT.getSerializedName() + "_swing_open"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation RIGHT_SWING_OPEN = ModTemplates.DOUBLE_DOOR_SWING_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.RIGHT.getSerializedName() + "_swing_open"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT_SWING_OPEN = ModTemplates.DOUBLE_DOOR_SWING_TOP_RIGHT_OPEN.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT.getSerializedName() + "_swing_open"), textureMappingSwing, GENERATOR.modelOutput);

        ResourceLocation BOTTOM_LEFT_SWING_OPEN_EXT = ModTemplates.DOUBLE_DOOR_SWING_BOTTOM_LEFT_OPEN_EXT.create(location.withSuffix("_swing_" + DoorPartProperty.BOTTOM_LEFT_OPEN_EXT.getSerializedName()), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation LEFT_SWING_OPEN_EXT = ModTemplates.DOUBLE_DOOR_SWING_LEFT_OPEN_EXT.create(location.withSuffix("_swing_" + DoorPartProperty.LEFT_OPEN_EXT.getSerializedName()), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation TOP_LEFT_SWING_OPEN_EXT = ModTemplates.DOUBLE_DOOR_SWING_TOP_LEFT_OPEN_EXT.create(location.withSuffix("_swing_" + DoorPartProperty.TOP_LEFT_OPEN_EXT.getSerializedName()), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT_SWING_OPEN_EXT = ModTemplates.DOUBLE_DOOR_SWING_BOTTOM_RIGHT_OPEN_EXT.create(location.withSuffix("_swing_" + DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT.getSerializedName()), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation RIGHT_SWING_OPEN_EXT = ModTemplates.DOUBLE_DOOR_SWING_RIGHT_OPEN_EXT.create(location.withSuffix("_swing_" + DoorPartProperty.RIGHT_OPEN_EXT.getSerializedName()), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT_SWING_OPEN_EXT = ModTemplates.DOUBLE_DOOR_SWING_TOP_RIGHT_OPEN_EXT.create(location.withSuffix("_swing_" + DoorPartProperty.TOP_RIGHT_OPEN_EXT.getSerializedName()), textureMappingSwing, GENERATOR.modelOutput);

        ResourceLocation BOTTOM_LEFT_SWING_OPEN_ALT = ModTemplates.DOUBLE_DOOR_SWING_BOTTOM_LEFT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_LEFT.getSerializedName() + "_swing_open_alt"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation LEFT_SWING_OPEN_ALT = ModTemplates.DOUBLE_DOOR_SWING_LEFT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.LEFT.getSerializedName() + "_swing_open_alt"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation TOP_LEFT_SWING_OPEN_ALT = ModTemplates.DOUBLE_DOOR_SWING_TOP_LEFT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.TOP_LEFT.getSerializedName() + "_swing_open_alt"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation BOTTOM_RIGHT_SWING_OPEN_ALT = ModTemplates.DOUBLE_DOOR_SWING_BOTTOM_RIGHT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.BOTTOM_RIGHT.getSerializedName() + "_swing_open_alt"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation RIGHT_SWING_OPEN_ALT = ModTemplates.DOUBLE_DOOR_SWING_RIGHT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.RIGHT.getSerializedName() + "_swing_open_alt"), textureMappingSwing, GENERATOR.modelOutput);
        ResourceLocation TOP_RIGHT_SWING_OPEN_ALT = ModTemplates.DOUBLE_DOOR_SWING_TOP_RIGHT_OPEN_ALT.create(location.withSuffix("_" + DoorPartProperty.TOP_RIGHT.getSerializedName() + "_swing_open_alt"), textureMappingSwing, GENERATOR.modelOutput);

        GENERATOR.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get(),
                        Variant.variant().with(VariantProperties.MODEL, BOTTOM))
                .with(BlockModelGenerators.createHorizontalFacingDispatch())
                .with(PropertyDispatch.properties(DoubleDoorBlock.SWING, DoubleDoorBlock.OPEN, DoubleDoorBlock.ALT, DoubleDoorBlock.PART)
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_OPEN))

                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, LEFT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, RIGHT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_OPEN_EXT))

                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_OPEN_ALT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_OPEN_ALT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT_OPEN_ALT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT_OPEN_ALT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_OPEN_ALT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_OPEN_ALT))

                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, LEFT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, RIGHT_OPEN_EXT))
                        .select(Boolean.FALSE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_OPEN_EXT))


                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, TOP))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, CENTER))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT))

                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))

                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, TOP))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, CENTER))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT))

                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))


                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, BOTTOM_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, BOTTOM_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_SWING_OPEN))

                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, LEFT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.TOP_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, RIGHT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_SWING_OPEN_EXT))

                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_SWING_OPEN_ALT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, BOTTOM_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_SWING_OPEN_ALT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT_SWING_OPEN_ALT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, BOTTOM_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT_SWING_OPEN_ALT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_SWING_OPEN_ALT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM_SWING_OPEN))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_SWING_OPEN_ALT))

                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, LEFT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.TOP_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, RIGHT_SWING_OPEN_EXT))
                        .select(Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_SWING_OPEN_EXT))


                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, TOP_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, CENTER_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_SWING))

                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.TOP_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))

                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP_LEFT, Variant.variant().with(VariantProperties.MODEL, TOP_LEFT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP, Variant.variant().with(VariantProperties.MODEL, TOP_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP_RIGHT, Variant.variant().with(VariantProperties.MODEL, TOP_RIGHT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.LEFT, Variant.variant().with(VariantProperties.MODEL, LEFT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.CENTER, Variant.variant().with(VariantProperties.MODEL, CENTER_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.RIGHT, Variant.variant().with(VariantProperties.MODEL, RIGHT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_LEFT_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM, Variant.variant().with(VariantProperties.MODEL, BOTTOM_SWING))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_RIGHT_SWING))

                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.TOP_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                        .select(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT, Variant.variant().with(VariantProperties.MODEL, BOTTOM_OPEN))
                )
        );
        GENERATOR.skipAutoItemBlock(block.get());
    }
}
