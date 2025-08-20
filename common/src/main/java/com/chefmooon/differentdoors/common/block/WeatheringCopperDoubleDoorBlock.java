package com.chefmooon.differentdoors.common.block;

import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.registry.ModBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public class WeatheringCopperDoubleDoorBlock extends DoubleDoorBlock implements WeatheringCopper {
    public static final Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> buildNextByBlock().build());
    public static final Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    public static final Supplier<BiMap<Block, Block>> WAXABLES = Suppliers.memoize(() -> buildWaxables().build());
    public static final Supplier<BiMap<Block, Block>> WAX_OFF_BY_BLOCK = Suppliers.memoize(() -> WAXABLES.get().inverse());
    public static final MapCodec<WeatheringCopperDoubleDoorBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(DoorMaterialType.CODEC.fieldOf("door_material_type").forGetter(DoubleDoorBlock::getDoorMaterialType), WeatherState.CODEC.fieldOf("weathering_state").forGetter(WeatheringCopperDoubleDoorBlock::getAge), propertiesCodec()).apply(instance, WeatheringCopperDoubleDoorBlock::new));
    private final WeatheringCopper.WeatherState weatherState;

    private static ImmutableBiMap.Builder<Block, Block> buildNextByBlock() {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        builder.put(BuiltInRegistries.BLOCK.get(ModBlocks.COPPER_DOUBLE_DOOR), BuiltInRegistries.BLOCK.get(ModBlocks.EXPOSED_COPPER_DOUBLE_DOOR));
        builder.put(BuiltInRegistries.BLOCK.get(ModBlocks.EXPOSED_COPPER_DOUBLE_DOOR), BuiltInRegistries.BLOCK.get(ModBlocks.OXIDIZED_COPPER_DOUBLE_DOOR));
        builder.put(BuiltInRegistries.BLOCK.get(ModBlocks.OXIDIZED_COPPER_DOUBLE_DOOR), BuiltInRegistries.BLOCK.get(ModBlocks.WEATHERED_COPPER_DOUBLE_DOOR));
        return  builder;
    }

    private static ImmutableBiMap.Builder<Block, Block> buildWaxables() {
        ImmutableBiMap.Builder<Block, Block> builder = ImmutableBiMap.builder();
        builder.put(BuiltInRegistries.BLOCK.get(ModBlocks.COPPER_DOUBLE_DOOR), BuiltInRegistries.BLOCK.get(ModBlocks.WAXED_COPPER_DOUBLE_DOOR));
        builder.put(BuiltInRegistries.BLOCK.get(ModBlocks.EXPOSED_COPPER_DOUBLE_DOOR), BuiltInRegistries.BLOCK.get(ModBlocks.WAXED_EXPOSED_COPPER_DOUBLE_DOOR));
        builder.put(BuiltInRegistries.BLOCK.get(ModBlocks.OXIDIZED_COPPER_DOUBLE_DOOR), BuiltInRegistries.BLOCK.get(ModBlocks.WAXED_OXIDIZED_COPPER_DOUBLE_DOOR));
        builder.put(BuiltInRegistries.BLOCK.get(ModBlocks.WEATHERED_COPPER_DOUBLE_DOOR), BuiltInRegistries.BLOCK.get(ModBlocks.WAXED_WEATHERED_COPPER_DOUBLE_DOOR));
        return builder;
    }

    @Override
    public MapCodec<WeatheringCopperDoubleDoorBlock> codec() {
        return CODEC;
    }
    public WeatheringCopperDoubleDoorBlock(DoorMaterialType doorMaterialType, WeatheringCopper.WeatherState weatherState, Properties properties) {
        super(doorMaterialType, properties);
        this.weatherState = weatherState;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(PART).isController()) {
            changeOverTime(state, level, pos, random);
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return getNext(state.getBlock()).isPresent();
    }

    @Override
    public WeatheringCopper.WeatherState getAge() {
        return this.weatherState;
    }

    public static Optional<BlockState> getWaxed(BlockState blockState) {
        return Optional.ofNullable(WAXABLES.get().get(blockState.getBlock())).map((block) -> block.withPropertiesOf(blockState));
    }

    public static Optional<BlockState> getUnwaxed(BlockState blockState) {
        return Optional.ofNullable(WAX_OFF_BY_BLOCK.get().get(blockState.getBlock())).map((block) -> block.withPropertiesOf(blockState));
    }

    public static Optional<Block> getPrevious(Block block) {
        return Optional.ofNullable((Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get(block));
    }

    public static Block getFirst(Block block) {
        Block block2 = block;

        for(Block block3 = (Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get(block); block3 != null; block3 = (Block)((BiMap)PREVIOUS_BY_BLOCK.get()).get(block3)) {
            block2 = block3;
        }

        return block2;
    }

    public static Optional<BlockState> getPrevious(BlockState state) {
        return getPrevious(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    public static Optional<Block> getNext(Block block) {
        return Optional.ofNullable((Block)((BiMap)NEXT_BY_BLOCK.get()).get(block));
    }

    public static BlockState getFirst(BlockState state) {
        return getFirst(state.getBlock()).withPropertiesOf(state);
    }

    @Override
    public Optional<BlockState> getNext(BlockState state) {
        return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    @Override
    public Optional<BlockState> getNextState(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int i = this.getAge().ordinal();
        int j = 0;
        int k = 0;

        for(BlockPos blockPos : BlockPos.withinManhattan(pos, 4, 4, 4)) {
            int l = blockPos.distManhattan(pos);
            if (l > 4) {
                break;
            }

            if (!blockPos.equals(pos)) {
                Block block = level.getBlockState(blockPos).getBlock();
                if (block instanceof ChangeOverTimeBlock<?> changeOverTimeBlock && !(block instanceof WeatheringCopperDoubleDoorBlock)) {
                    Enum<?> enum_ = changeOverTimeBlock.getAge();
                    if (this.getAge().getClass() == enum_.getClass()) {
                        int m = enum_.ordinal();
                        if (m < i) {
                            return Optional.empty();
                        }

                        if (m > i) {
                            ++k;
                        } else {
                            ++j;
                        }
                    }
                }
            }
        }

        float f = (float)(k + 1) / (float)(k + j + 1);
        float g = f * f * this.getChanceModifier();
        boolean shouldWeather = random.nextFloat() < g;
        return shouldWeather ? this.getNext(state) : Optional.empty();
    }
}
