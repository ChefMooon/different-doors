package com.chefmooon.differentdoors.common.block.fabric;

import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.data.types.DoorType;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LargeDoorBlockImpl extends LargeDoorBlock {
    public LargeDoorBlockImpl(DoorType doorType, Properties properties) {
        super(doorType, properties);
    }

    public static void init() {
        UseBlockCallback.EVENT.register(LargeDoorBlockImpl.ChangeStyleEvent::onSneakWithTool);
    }

    public static class ChangeStyleEvent {
        public static InteractionResult onSneakWithTool(Player player, Level level, InteractionHand hand, BlockHitResult hit) {
            if (player.isSpectator()) return InteractionResult.PASS;

            if (player.isCrouching()) {
                ItemStack heldItem = player.getItemInHand(hand);
                if (heldItem.is(ItemTags.AXES)) {
                    BlockPos blockPos = hit.getBlockPos();
                    BlockState state = level.getBlockState(blockPos);
                    if (state.getBlock() instanceof LargeDoorBlock largeDoorBlock) {
                        BlockPos controllerPos = getController(state, blockPos);
                        BlockState controllerState = level.getBlockState(controllerPos);

                        if (controllerState.isAir()) return InteractionResult.PASS;

                        largeDoorBlock.setSwing(level, controllerPos, controllerState, heldItem, player, hand, !state.getValue(LargeDoorBlock.SWING));
                    }
                }
            }

            return InteractionResult.PASS;
        }
    }
}
