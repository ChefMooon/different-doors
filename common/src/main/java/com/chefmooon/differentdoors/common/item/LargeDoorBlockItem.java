package com.chefmooon.differentdoors.common.item;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.Block;

import java.util.List;

// TODO: is this class required? appended text in the block instead
public class LargeDoorBlockItem extends BlockItem {
    public LargeDoorBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        BlockItemStateProperties properties = stack.get(DataComponents.BLOCK_STATE);
        if (properties != null && properties.get(LargeDoorBlock.SWING) != null) {
            boolean isSwinging = Boolean.TRUE.equals(properties.get(LargeDoorBlock.SWING));
            if (isSwinging) { // might need some updates
                tooltipComponents.add(Component.translatable("itemGroup." + DifferentDoors.MOD_ID).withStyle(ChatFormatting.BLUE));
                tooltipComponents.add(Component.translatable(DifferentDoors.MOD_ID + ".tooltip.large_door.swinging").withStyle(ChatFormatting.GRAY));
            }
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
