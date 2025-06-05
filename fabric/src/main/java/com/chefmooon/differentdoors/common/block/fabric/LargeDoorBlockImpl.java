package com.chefmooon.differentdoors.common.block.fabric;

import com.chefmooon.differentdoors.common.block.entity.LargeDoorBlockEntity;
import com.chefmooon.differentdoors.common.registry.fabric.ModBlockEntitiesImpl;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LargeDoorBlockImpl {
    public static BlockEntityType<LargeDoorBlockEntity> getBlockEntityType() {
        return ModBlockEntitiesImpl.LARGE_DOOR_VARIANTS;
    }
}
