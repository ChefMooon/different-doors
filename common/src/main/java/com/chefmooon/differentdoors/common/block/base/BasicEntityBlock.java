package com.chefmooon.differentdoors.common.block.base;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BasicEntityBlock extends BaseEntityBlock {
//    public static final MapCodec<BasicEntityBlock> CODEC = simpleCodec(BasicEntityBlock::new);

    protected BasicEntityBlock(Properties properties) {
        super(properties);
    }

//    @Override
//    protected MapCodec<? extends BaseEntityBlock> codec() {
//        return CODEC;
//    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return null; // maybe throw an error if this is not overridden, cannot be null
    }
}
