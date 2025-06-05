package com.chefmooon.differentdoors.common.block.entity;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.registry.ModBlockEntities;
import com.chefmooon.differentdoors.common.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LargeDoorBlockEntity extends BlockEntity {
    private int slideTicks;
    private int lastSlideTicks;
    public LargeDoorBlockEntity(BlockPos pos, BlockState blockState) {
        super(BuiltInRegistries.BLOCK_ENTITY_TYPE.get(ModBlockEntities.LARGE_DOOR), pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        slideTicks = tag.getInt("SlideTicks");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putInt("SlideTicks", slideTicks);
    }

    public int slideTicks() {
        return slideTicks;
    }

    public int lastSlideTicks() {
        return lastSlideTicks;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, LargeDoorBlockEntity largeDoorBlockEntity) {
        boolean isOpen = blockState.getValue(LargeDoorBlock.OPEN) || blockState.getValue(LargeDoorBlock.POWERED);
        largeDoorBlockEntity.lastSlideTicks = largeDoorBlockEntity.slideTicks;

        if (!level.isClientSide()) {
            if (blockState.getBlock() instanceof LargeDoorBlock largeDoorBlock) {
                if (!isOpen && largeDoorBlockEntity.slideTicks == 97) {
                    largeDoorBlock.playSound(level, blockPos, false);
                } else if (isOpen && largeDoorBlockEntity.slideTicks == 3) {
                    largeDoorBlock.playSound(level, blockPos, true);
                }
            }
        }
        largeDoorBlockEntity.slideTicks = Mth.clamp(largeDoorBlockEntity.slideTicks + (isOpen ? 3 : -3), 0, 100);
    }
}
