package com.chefmooon.differentdoors.common.block.neoforge;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.DoubleDoorBlock;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class DoubleDoorBlockImpl extends DoubleDoorBlock {
    public DoubleDoorBlockImpl(DoorMaterialType doorMaterialType, Properties properties) {
        super(doorMaterialType, properties);
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
                if (heldItem.is(ItemTags.PICKAXES)) {
                    BlockPos blockPos = event.getPos();
                    BlockState state = level.getBlockState(blockPos);
                    if (state.getBlock() instanceof DoubleDoorBlock doubleDoorBlock) {
                        BlockPos controllerPos = getController(state, blockPos);
                        BlockState controllerState = level.getBlockState(controllerPos);

                        if (controllerState.isAir()) return;

                        doubleDoorBlock.setSwing(level, controllerPos, controllerState, heldItem, player, hand, !state.getValue(DoubleDoorBlock.SWING));
                        event.setCanceled(true);
                        event.setCancellationResult(InteractionResult.SUCCESS);
                    }
                } else if (heldItem.is(ItemTags.AXES) || heldItem.is(Items.HONEYCOMB)) {
                    BlockPos blockPos = event.getPos();
                    BlockState state = level.getBlockState(blockPos);
                    if (state.getBlock() instanceof DoubleDoorBlock doubleDoorBlock) {
                        if (doubleDoorBlock.getDoorMaterialType() == DoorMaterialType.COPPER) {
                            if (heldItem.is(Items.HONEYCOMB)) {
                                event.setCanceled(true);
                                event.setCancellationResult(doubleDoorBlock.tryUseHoneycombItem(state, level, blockPos, player, heldItem));
                            } else {
                                event.setCanceled(true);
                                event.setCancellationResult(doubleDoorBlock.tryUseAxeItem(state, level, blockPos, player, heldItem));
                            }
                        }
                    }
                }
            }
        }
    }
}
