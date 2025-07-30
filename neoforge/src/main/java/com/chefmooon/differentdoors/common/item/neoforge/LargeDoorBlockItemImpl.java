package com.chefmooon.differentdoors.common.item.neoforge;

import com.chefmooon.differentdoors.common.item.LargeDoorBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class LargeDoorBlockItemImpl extends LargeDoorBlockItem {
    private final int burnTime;
    public LargeDoorBlockItemImpl(Block block, Properties properties) {
        this(block, properties, 0);
    }

    public LargeDoorBlockItemImpl(Block block, Properties properties, int burnTime) {
        super(block, properties);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }
}
