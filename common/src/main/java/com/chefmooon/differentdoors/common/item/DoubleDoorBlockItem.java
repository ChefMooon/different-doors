package com.chefmooon.differentdoors.common.item;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DoubleDoorBlockItem extends BlockItem {
    public DoubleDoorBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        CompoundTag blockStateTag = new CompoundTag();
        blockStateTag.putString(DoubleDoorBlock.SWING.getName(), "false");
        stack.getOrCreateTag().put("BlockStateTag", blockStateTag);
        return stack;
    }
}
