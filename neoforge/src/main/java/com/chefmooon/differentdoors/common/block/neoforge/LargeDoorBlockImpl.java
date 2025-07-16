package com.chefmooon.differentdoors.common.block.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.LargeDoorBlock;
import com.chefmooon.differentdoors.common.data.types.DoorType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class LargeDoorBlockImpl extends LargeDoorBlock {
    public LargeDoorBlockImpl(DoorType doorType, Properties properties) {
        super(doorType, properties);
    }

    @EventBusSubscriber(modid = DifferentDoors.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class ChangeStyleEvent {
        @SubscribeEvent
        @SuppressWarnings("unused")
        public static void onSneakWithTool(PlayerInteractEvent.RightClickBlock event) {
            Level level = event.getLevel();
            Player player = event.getEntity();
            InteractionHand hand = event.getHand();

            if (player.isCrouching()) {
                ItemStack heldItem = player.getItemInHand(hand);
                if (heldItem.is(ItemTags.AXES)) {
                    BlockPos blockPos = event.getPos();
                    BlockState state = level.getBlockState(blockPos);
                    if (state.getBlock() instanceof LargeDoorBlock largeDoorBlock) {
                        BlockPos controllerPos = getController(state, blockPos);
                        BlockState controllerState = level.getBlockState(controllerPos);

                        if (controllerState.isAir()) return;

                        largeDoorBlock.setSwing(level, controllerPos, controllerState, heldItem, player, hand, !state.getValue(LargeDoorBlock.SWING));
                        event.setCanceled(true);
                        event.setCancellationResult(InteractionResult.SUCCESS);
                    }
                }
            }
        }
    }
}
