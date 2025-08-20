package com.chefmooon.differentdoors.common.block.fabric;

import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class DoubleDoorBlockImpl extends DoubleDoorBlock {
    public DoubleDoorBlockImpl(DoorMaterialType doorMaterialType, Properties properties) {
        super(doorMaterialType, properties);
    }

    public static void init() {
        UseBlockCallback.EVENT.register(DoubleDoorBlockImpl.ChangeStyleEvent::onSneakWithTool);
    }

    public static class ChangeStyleEvent {
        public static InteractionResult onSneakWithTool(Player player, Level level, InteractionHand hand, BlockHitResult hit) {
            if (player.isSpectator()) return InteractionResult.PASS;

            if (player.isCrouching()) {
                ItemStack heldItem = player.getItemInHand(hand);
                if (heldItem.is(ItemTags.PICKAXES)) {
                    BlockPos blockPos = hit.getBlockPos();
                    BlockState state = level.getBlockState(blockPos);
                    if (state.getBlock() instanceof DoubleDoorBlock doubleDoorBlock) {
                        BlockPos controllerPos = getController(state, blockPos);
                        BlockState controllerState = level.getBlockState(controllerPos);

                        if (controllerState.isAir()) return InteractionResult.PASS;

                        doubleDoorBlock.setSwing(level, controllerPos, controllerState, heldItem, player, hand, !state.getValue(DoubleDoorBlock.SWING));
                        return InteractionResult.SUCCESS;
                    }
                } else if (heldItem.is(ItemTags.AXES) || heldItem.is(Items.HONEYCOMB)) {
                    BlockPos blockPos = hit.getBlockPos();
                    BlockState state = level.getBlockState(blockPos);
                    if (state.getBlock() instanceof DoubleDoorBlock doubleDoorBlock) {
                        if (doubleDoorBlock.getDoorMaterialType() == DoorMaterialType.COPPER) {
                            return heldItem.is(Items.HONEYCOMB) ?
                                    doubleDoorBlock.tryUseHoneycombItem(state, level, blockPos, player, heldItem) :
                                    doubleDoorBlock.tryUseAxeItem(state, level, blockPos, player, heldItem);
                        }
                    }
                }
            }

            return InteractionResult.PASS;
        }
    }
}
