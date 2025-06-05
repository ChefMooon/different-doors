package com.chefmooon.differentdoors.common.block.neoforge;

import com.chefmooon.differentdoors.common.block.entity.LargeDoorBlockEntity;
import com.chefmooon.differentdoors.common.registry.neoforge.ModBlockEntitiesImpl;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class LargeDoorBlockImpl {

    public static BlockEntityType<LargeDoorBlockEntity> getBlockEntityType() {
        return ModBlockEntitiesImpl.LARGE_DOOR_VARIANTS.get();
    }
}
