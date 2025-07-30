package com.chefmooon.differentdoors.common.item.fabric;

import com.chefmooon.differentdoors.common.item.LargeDoorBlockItem;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.level.block.Block;

public class LargeDoorBlockItemImpl extends LargeDoorBlockItem {
    private final int burnTime;
    public LargeDoorBlockItemImpl(Block block, Properties properties) {
        this(block, properties, 0);
    }

    public LargeDoorBlockItemImpl(Block block, Properties properties, int burnTime) {
        super(block, properties);
        this.burnTime = burnTime;
        if (burnTime > 0) FuelRegistry.INSTANCE.add(this, this.burnTime);
    }
}
