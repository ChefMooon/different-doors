package com.chefmooon.differentdoors.common.block;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.properties.DoorPartProperty;
import com.chefmooon.differentdoors.common.data.types.DoorType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;

public class LargeDoorBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty SWING = BooleanProperty.create("swing");
    public static final EnumProperty<DoorPartProperty> PART  = EnumProperty.create("part", DoorPartProperty.class);
    public final DoorType doorType;
    private static final VoxelShape[] SHAPES = {
            Block.box(0, 0, 12, 16, 16, 15), // South
            Block.box(1, 0, 0, 4, 16, 16), // West
            Block.box(0, 0, 1, 16, 16, 4), // North
            Block.box(12, 0, 0, 15, 16, 16), // East
    };

    private static final VoxelShape[][] SWING_OPEN_SHAPES = {
            {Block.box(0, 0, 0, 3, 16, 15), // South Left Swing Open
                    Block.box(13, 0, 0, 16, 16, 15)}, // South Right Swing Open
            {Block.box(1, 0, 0, 16, 16, 3), // West Left Swing Open
                    Block.box(1, 0, 13, 16, 16, 16)}, // West Right Swing Open
            {Block.box(13, 0, 1, 16, 16, 16), // North Left Swing Open
                    Block.box(0, 0, 1, 3, 16, 16)}, // North Right Swing Open
            {Block.box(0, 0, 13, 15, 16, 16), // East Left Swing Open
                    Block.box(0, 0, 0, 15, 16, 3)}, // East Right Swing Open
    };

    private static final VoxelShape[][] SLIDE_OPEN_SHAPES = {
            {Block.box(0, 0, 12, 8, 16, 15), // South Left Slide Open
                    Block.box(8, 0, 12, 16, 16, 15)}, // South Right Slide Open
            {Block.box(1, 0, 0, 4, 16, 8),  // West Left Slide Open
                    Block.box(1, 0, 8, 4, 16, 16)}, // West Right Slide Open
            {Block.box(8, 0, 1, 16, 16, 4), // North Left Slide Open
                    Block.box(0, 0, 1, 8, 16, 4)}, // North Right Slide Open
            {Block.box(12, 0, 8, 15, 16, 16), // East Left Slide Open
                    Block.box(12, 0, 0, 15, 16, 8)}, // East Right Slide Open
    };
    public LargeDoorBlock(DoorType doorType, Properties properties) {
        super(properties);
        this.doorType = doorType;
        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(LOCKED, false)
                .setValue(POWERED, false)
                .setValue(SWING, false)
                .setValue(PART, DoorPartProperty.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, OPEN, LOCKED, POWERED, SWING, PART);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(OPEN)) {
            DoorPartProperty part = state.getValue(PART);
            boolean swing = state.getValue(SWING);
            int xOffset = part.xOffset();
            Direction facing = state.getValue(FACING);

            if (xOffset == -1 || xOffset == 1) {
                if (swing) {
                    return SWING_OPEN_SHAPES[facing.get2DDataValue()][xOffset == -1 ? 0 : 1]; // 0 for left, 1 for right
                } else {
                    return SLIDE_OPEN_SHAPES[facing.get2DDataValue()][xOffset == -1 ? 0 : 1]; // 0 for left, 1 for right
                }
            } else {
                return Shapes.empty();
            }
        } else {
            return SHAPES[state.getValue(FACING).get2DDataValue()];
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        BlockState controllerState = level.getBlockState(getController(state, pos));
        if (!controllerState.getValues().containsKey(PART)) {
            return super.getCollisionShape(state, level, pos, context);
        }
        return controllerState.getValue(OPEN) ? Shapes.empty() : super.getCollisionShape(state, level, pos, context);
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return switch(type) {
            case LAND, AIR -> state.getValue(OPEN);
            case WATER -> false;
        };
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean swing = false;
        BlockItemStateProperties blockStateProperties = context.getItemInHand().get(DataComponents.BLOCK_STATE);
        if (blockStateProperties != null && blockStateProperties.get(SWING) != null) swing = Boolean.TRUE.equals(blockStateProperties.get(SWING));
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        Direction facing = context.getHorizontalDirection().getOpposite();
        boolean isAreaClear = true;
        for (DoorPartProperty part : DoorPartProperty.values()) {
            BlockPos partPos = blockPos.relative(facing.getClockWise().getOpposite(), part.xOffset()).above(part.yOffset());
            isAreaClear = level.getBlockState(partPos).canBeReplaced(context);
            if (!isAreaClear) break;
        }
        if (blockPos.getY() < level.getMaxBuildHeight() - 2 && isAreaClear) {
            boolean powered = false;
            for (DoorPartProperty part : DoorPartProperty.values()) {
                BlockPos partPos = blockPos.relative(facing.getClockWise().getOpposite(), part.xOffset()).above(part.yOffset());
                powered = level.hasNeighborSignal(partPos);
                if (powered) break;
            }
            return this.defaultBlockState()
                    .setValue(FACING, facing)
                    .setValue(OPEN, powered)
                    .setValue(LOCKED, false)
                    .setValue(POWERED, powered)
                    .setValue(SWING, swing);
        } else {
            return null;
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BlockPos controllerPos = getController(state, pos);
        BlockState controllerState = level.getBlockState(controllerPos);
        for (DoorPartProperty part : DoorPartProperty.values()) {
            BlockPos partPos = controllerPos.relative(controllerState.getValue(FACING).getClockWise().getOpposite(), part.xOffset()).above(part.yOffset());
            BlockState partState = level.getBlockState(partPos);
            BlockState blockState = partState.cycle(OPEN);
            level.setBlock(partPos, blockState, 10);
        }
        this.playSound(player, level, pos, state.getValue(OPEN));
        level.gameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public boolean isOpen(BlockState blockState) {
        return blockState.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity entity, Level level, BlockPos pos, BlockState state, boolean open) {
        BlockPos controllerPos = getController(state, pos);
        BlockState controllerState = level.getBlockState(controllerPos);
        if (state.is(this) && controllerState.getValue(OPEN) != open) {
            for (DoorPartProperty part : DoorPartProperty.values()) {
                BlockPos partPos = controllerPos.relative(state.getValue(FACING).getClockWise().getOpposite(), part.xOffset()).above(part.yOffset());
                BlockState partState = level.getBlockState(partPos);
                if (partState.getBlock() instanceof LargeDoorBlock) {
                    level.setBlock(partPos, partState.setValue(OPEN, open), 10);
                }
            }
            this.playSound(entity, level, pos, open);
            level.gameEvent(entity, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction direction = state.getValue(FACING).getClockWise();
        for (DoorPartProperty part : DoorPartProperty.values()) {
            BlockPos partPos = pos.relative(direction.getOpposite(), part.xOffset()).above(part.yOffset());
            level.setBlock(partPos, state.setValue(PART, part), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        BlockPos controllerPos = getController(state, pos);
        boolean bl = false;
        for (DoorPartProperty part : DoorPartProperty.values()) {
            BlockPos partPos = controllerPos.relative(state.getValue(FACING).getClockWise().getOpposite(), part.xOffset()).above(part.yOffset());
            bl = level.hasNeighborSignal(partPos);
            if (bl) break;
        }
        if (!this.defaultBlockState().is(neighborBlock) && bl != state.getValue(POWERED)) {
            if (bl != state.getValue(OPEN)) {
                this.playSound(null, level, pos, bl);
                level.gameEvent((Entity)null, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
            }
            for (DoorPartProperty part : DoorPartProperty.values()) {
                BlockPos partPos = controllerPos.relative(state.getValue(FACING).getClockWise().getOpposite(), part.xOffset()).above(part.yOffset());
                BlockState partState = level.getBlockState(partPos);
                if (partState.getBlock() instanceof LargeDoorBlock) {
                    level.setBlock(partPos, partState.setValue(POWERED, bl).setValue(OPEN, bl), Block.UPDATE_CLIENTS);
                }
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        if (!blockState.is(this)) return false;
        DoorPartProperty part = blockState.getValue(PART);
        BlockPos belowBlockPos = blockPos.below();
        BlockState belowBlockState = level.getBlockState(belowBlockPos);
        if (part == DoorPartProperty.BOTTOM || part == DoorPartProperty.BOTTOM_LEFT || part == DoorPartProperty.BOTTOM_RIGHT) {
            return belowBlockState.isFaceSturdy(level, belowBlockPos, Direction.UP);
        } else {
            return belowBlockState.is(this);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        BlockItemStateProperties blockStateProperties = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        if (blockStateProperties.get(SWING) != null) {
            boolean isSwinging = Boolean.TRUE.equals((blockStateProperties.get(SWING)));
            if (isSwinging) {
                tooltipComponents.add(Component.translatable(DifferentDoors.MOD_ID + ".tooltip.large_door.swinging").withStyle(ChatFormatting.GRAY));
            } else {
                tooltipComponents.add(Component.translatable(DifferentDoors.MOD_ID + ".tooltip.large_door.sliding").withStyle(ChatFormatting.GRAY));
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        ItemStack stack = super.getCloneItemStack(level, pos, state);
        stack.set(DataComponents.BLOCK_STATE, stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).with(LargeDoorBlock.SWING, state.getValue(SWING)));
        return stack;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoorPartProperty part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        if (direction.getAxis() == Direction.Axis.Y) {
            if (direction == Direction.DOWN) {
                if (part.yOffset() == 0) {
                    BlockPos controllerPos = getController(state, pos);
                    BlockState controllerState = level.getBlockState(controllerPos);
                    BlockPos bottomLeftPos = controllerPos.relative(facing.getClockWise().getOpposite(), DoorPartProperty.BOTTOM_LEFT.xOffset()).above(DoorPartProperty.BOTTOM_LEFT.yOffset());
                    BlockPos bottomRightPos = controllerPos.relative(facing.getClockWise().getOpposite(), DoorPartProperty.BOTTOM_RIGHT.xOffset()).above(DoorPartProperty.BOTTOM_RIGHT.yOffset());
                    if (!canSurvive(level.getBlockState(bottomLeftPos), level, bottomLeftPos) && !canSurvive(controllerState, level, controllerPos) && !canSurvive(level.getBlockState(bottomRightPos), level, bottomRightPos)) {
                        if (level instanceof  Level) destroy((Level) level, pos, state, true, null);
                        return Blocks.AIR.defaultBlockState();
                    }
                } else {
                    if (!canSurvive(state, level, pos)) {
                        return Blocks.AIR.defaultBlockState();
                    }
                }
            } else if (direction == Direction.UP) {
                if (part.yOffset() != 2 && !level.getBlockState(pos.above()).is(this)) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        } else {
            int xOffset = part.xOffset();
            Direction left = facing.getClockWise().getOpposite();
            Direction right = facing.getClockWise();
            BlockPos neighborPosCheck = pos;

            if (xOffset == -1 && direction == left) {
                neighborPosCheck = pos.relative(left);
                if (!level.getBlockState(neighborPosCheck).is(this)) {
                    return Blocks.AIR.defaultBlockState();
                }
            } else if (xOffset == 1 && direction == right) {
                neighborPosCheck = pos.relative(right);
                if (!level.getBlockState(neighborPosCheck).is(this)) {
                    return Blocks.AIR.defaultBlockState();
                }
            } else if (xOffset == 0) {
                if (direction == left) {
                    neighborPosCheck = pos.relative(left);
                    if (!level.getBlockState(neighborPosCheck).is(this)) {
                        return Blocks.AIR.defaultBlockState();
                    }
                } else if (direction == right) {
                    neighborPosCheck = pos.relative(right);
                    if (!level.getBlockState(neighborPosCheck).is(this)) {
                        return Blocks.AIR.defaultBlockState();
                    }
                }
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void onExplosionHit(BlockState blockState, Level level, BlockPos blockPos, Explosion explosion, BiConsumer<ItemStack, BlockPos> biConsumer) {
        if (explosion.canTriggerBlocks() && !blockState.getValue(LOCKED) && !blockState.getValue(POWERED)) {
            this.setOpen((Entity) null, level, blockPos, blockState, !this.isOpen(blockState));
        }
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        destroy(level, pos, state, !level.isClientSide && !player.isCreative(), player);
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        // TODO: fix tnt explosion not destroying the door
        DifferentDoors.LOGGER.info("I go Boom!");
        if (!level.isClientSide()) {
            for (Direction direction : Direction.values()) {
                BlockPos offsetPos = pos.relative(direction);
                BlockState offsetState = level.getBlockState(offsetPos);
                if (offsetState.getBlock().equals(this)) {
//                    destroy(level, offsetPos, offsetState, true);
                }
            }
        }
        super.wasExploded(level, pos, explosion);
    }

    public void setSwing(Level level, BlockPos controllerPos, BlockState controllerState, ItemStack heldItem, Player player, InteractionHand hand, Boolean swing) {
        Direction direction = controllerState.getValue(FACING).getClockWise();
        for (DoorPartProperty part : DoorPartProperty.values()) {
            BlockPos partPos = controllerPos.relative(direction.getOpposite(), part.xOffset()).above(part.yOffset());
            BlockState partState = level.getBlockState(partPos);
            level.setBlockAndUpdate(partPos, swing ?
                partState.setValue(SWING, true) :
                partState.setValue(SWING, false));
        }
        playSetSwingSound(level, controllerPos, swing);
        if (!player.getAbilities().instabuild) {
            if (heldItem.is(ItemTags.AXES)) { // TODO: review before final release, is this check needed?
                EquipmentSlot slot = player.getUsedItemHand() == hand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                heldItem.hurtAndBreak(1, player, slot);
            }
        }
    }

    private void destroy(Level level, BlockPos pos, BlockState state, boolean dropBlock, @Nullable Player player) {
        if (!state.is(this)) return;
        BlockPos controllerPos = getController(state, pos);
        BlockState controllerState = level.getBlockState(controllerPos);

        if (dropBlock) Block.dropResources(controllerState, level, controllerPos);

        level.setBlock(controllerPos, Blocks.AIR.defaultBlockState(), 35);
        if (player != null) level.levelEvent(player, 2001, controllerPos, Block.getId(controllerState));
    }

    public static BlockPos getController(BlockState state, BlockPos pos) {
        DoorPartProperty part = state.getValue(PART);
        Direction direction = state.getValue(FACING).getClockWise();
        return pos.relative(direction.getOpposite(), -part.xOffset()).below(part.yOffset());
    }

    public void playSetSwingSound(Level level, BlockPos blockPos, boolean swing) {
        if (!level.isClientSide()) {
            if (swing) {
                level.playSound(null, blockPos, doorType.getAddSwingSound(), SoundSource.BLOCKS, 0.5f, 1);
            } else {
                level.playSound(null, blockPos, doorType.getRemoveSwingSound(), SoundSource.BLOCKS, 0.5f, 1);
            }
        }
    }

    public void playSound(@Nullable Entity entity, Level level, BlockPos blockPos, boolean isOpen) {
        if (isOpen) {
            level.playSound(entity, blockPos, doorType.getOpenSound(), SoundSource.BLOCKS, 0.5f, 1);
        } else {
            level.playSound(entity, blockPos, doorType.getCloseSound(), SoundSource.BLOCKS, 0.5f, 1);
        }
    }
}
