package com.chefmooon.differentdoors.common.block;

import com.chefmooon.differentdoors.DifferentDoors;
import com.chefmooon.differentdoors.common.block.properties.DoorPartProperty;
import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
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

import java.util.EnumMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DoubleDoorBlock extends Block {
    public enum OpenType { CLOSED, SWING, SLIDE }
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty SWING = BooleanProperty.create("swing");
    public static final EnumProperty<DoorPartProperty> PART  = EnumProperty.create("part", DoorPartProperty.class);
    public final DoorMaterialType doorMaterialType;
    private static final VoxelShape[] CLOSED_SHAPES = {
            Block.box(0, 0, 12, 16, 16, 15), // SOUTH (index 0 when facing NORTH? -> we map via get2DDataValue)
            Block.box(1, 0, 0, 4, 16, 16),   // WEST
            Block.box(0, 0, 1, 16, 16, 4),   // NORTH
            Block.box(12, 0, 0, 15, 16, 16)  // EAST
    };

    private static final DoorPartProperty[] LEFT_PARTS = {
            DoorPartProperty.TOP_LEFT, DoorPartProperty.LEFT, DoorPartProperty.BOTTOM_LEFT
    };
    private static final DoorPartProperty[] RIGHT_PARTS = {
            DoorPartProperty.TOP_RIGHT, DoorPartProperty.RIGHT, DoorPartProperty.BOTTOM_RIGHT
    };
    private static final DoorPartProperty[] LEFT_EXT_PARTS = {
            DoorPartProperty.TOP_LEFT_OPEN_EXT, DoorPartProperty.LEFT_OPEN_EXT, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT
    };
    private static final DoorPartProperty[] RIGHT_EXT_PARTS = {
            DoorPartProperty.TOP_RIGHT_OPEN_EXT, DoorPartProperty.RIGHT_OPEN_EXT, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT
    };
    private static final DoorPartProperty[] MIDDLE_EMPTY_PARTS = {
            DoorPartProperty.TOP, DoorPartProperty.CENTER, DoorPartProperty.BOTTOM
    };
    private static final DoorPartProperty[] ALL_PARTS = DoorPartProperty.values();

    private final EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>> swingOpenShapes;
    private final EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>> slideOpenShapes;
    private final EnumMap<OpenType, EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>>> shapeMap;
    private final EnumMap<DoorPartProperty, DoorPartProperty> extensionMap;

    public DoubleDoorBlock(DoorMaterialType doorMaterialType, Properties properties) {
        super(properties);
        this.doorMaterialType = doorMaterialType;
        this.swingOpenShapes = initSwingOpenShapes();
        this.slideOpenShapes = initSlideOpenShapes();
        this.shapeMap = initShapeMap();
        this.extensionMap = initExtensionMap();
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

    private EnumMap<OpenType, EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>>> initShapeMap() {
        EnumMap<OpenType, EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>>> map = new EnumMap<>(OpenType.class);

        EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>> closed = new EnumMap<>(Direction.class);
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            EnumMap<DoorPartProperty, VoxelShape> perPart = new EnumMap<>(DoorPartProperty.class);
            VoxelShape shape = CLOSED_SHAPES[dir.get2DDataValue()];
            for (DoorPartProperty p : ALL_PARTS) perPart.put(p, shape);
            closed.put(dir, perPart);
        }

        map.put(OpenType.CLOSED, closed);
        map.put(OpenType.SWING, swingOpenShapes);
        map.put(OpenType.SLIDE, slideOpenShapes);
        return map;
    }

    private static EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>> initSwingOpenShapes() {
        EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>> map = new EnumMap<>(Direction.class);
        map.put(Direction.NORTH, makeSwingShapeMap(
                Block.box(13, 0, 1, 16, 16, 16),
                Block.box(0, 0, 1, 3, 16, 16),
                Block.box(13, 0, 0, 16, 16, 9),
                Block.box(0, 0, 0, 3, 16, 9)
        ));
        map.put(Direction.EAST, makeSwingShapeMap(
                Block.box(0, 0, 13, 15, 16, 16),
                Block.box(0, 0, 0, 15, 16, 3),
                Block.box(7, 0, 13, 16, 16, 16),
                Block.box(7, 0, 0, 16, 16, 3)
        ));
        map.put(Direction.SOUTH, makeSwingShapeMap(
                Block.box(0, 0, 0, 3, 16, 15),
                Block.box(13, 0, 0, 16, 16, 15),
                Block.box(0, 0, 7, 3, 16, 16),
                Block.box(13, 0, 7, 16, 16, 16)
        ));
        map.put(Direction.WEST, makeSwingShapeMap(
                Block.box(1, 0, 0, 16, 16, 3),
                Block.box(1, 0, 13, 16, 16, 16),
                Block.box(0, 0, 0, 9, 16, 3),
                Block.box(0, 0, 13, 9, 16, 16)
        ));
        return map;
    }

    private static EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>> initSlideOpenShapes() {
        EnumMap<Direction, EnumMap<DoorPartProperty, VoxelShape>> map = new EnumMap<>(Direction.class);
        map.put(Direction.NORTH, makeSlideShapeMap(
                Block.box(8, 0, 1, 16, 16, 4),
                Block.box(0, 0, 1, 8, 16, 4),
                Block.box(0, 0, 1, 16, 16, 4)
        ));
        map.put(Direction.EAST, makeSlideShapeMap(
                Block.box(12, 0, 8, 15, 16, 16),
                Block.box(12, 0, 0, 15, 16, 8),
                Block.box(12, 0, 0, 15, 16, 16)
        ));
        map.put(Direction.SOUTH, makeSlideShapeMap(
                Block.box(0, 0, 12, 8, 16, 15),
                Block.box(8, 0, 12, 16, 16, 15),
                Block.box(0, 0, 12, 16, 16, 15)
        ));
        map.put(Direction.WEST, makeSlideShapeMap(
                Block.box(1, 0, 0, 4, 16, 8),
                Block.box(1, 0, 8, 4, 16, 16),
                Block.box(1, 0, 0, 4, 16, 16)
        ));
        return map;
    }

    private static EnumMap<DoorPartProperty, VoxelShape> makeSwingShapeMap(VoxelShape left, VoxelShape right,
                                                                           VoxelShape leftExt, VoxelShape rightExt) {
        EnumMap<DoorPartProperty, VoxelShape> map = new EnumMap<>(DoorPartProperty.class);
        for (DoorPartProperty p : LEFT_PARTS) map.put(p, left);
        for (DoorPartProperty p : RIGHT_PARTS) map.put(p, right);
        for (DoorPartProperty p : LEFT_EXT_PARTS) map.put(p, leftExt);
        for (DoorPartProperty p : RIGHT_EXT_PARTS) map.put(p, rightExt);
        for (DoorPartProperty p : MIDDLE_EMPTY_PARTS) map.put(p, Shapes.empty());
        return map;
    }

    private static EnumMap<DoorPartProperty, VoxelShape> makeSlideShapeMap(VoxelShape left, VoxelShape right, VoxelShape ext) {
        EnumMap<DoorPartProperty, VoxelShape> map = new EnumMap<>(DoorPartProperty.class);
        for (DoorPartProperty p : LEFT_PARTS) map.put(p, left);
        for (DoorPartProperty p : RIGHT_PARTS) map.put(p, right);
        for (DoorPartProperty p : new DoorPartProperty[]{
                DoorPartProperty.TOP_LEFT_OPEN_EXT, DoorPartProperty.LEFT_OPEN_EXT, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT,
                DoorPartProperty.TOP_RIGHT_OPEN_EXT, DoorPartProperty.RIGHT_OPEN_EXT, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT
        }) map.put(p, ext);
        for (DoorPartProperty p : MIDDLE_EMPTY_PARTS) map.put(p, Shapes.empty());
        return map;
    }

    private static EnumMap<DoorPartProperty, DoorPartProperty> initExtensionMap() {
        EnumMap<DoorPartProperty, DoorPartProperty> map = new EnumMap<>(DoorPartProperty.class);
        map.put(DoorPartProperty.BOTTOM_LEFT, DoorPartProperty.BOTTOM_LEFT_OPEN_EXT);
        map.put(DoorPartProperty.LEFT, DoorPartProperty.LEFT_OPEN_EXT);
        map.put(DoorPartProperty.TOP_LEFT, DoorPartProperty.TOP_LEFT_OPEN_EXT);
        map.put(DoorPartProperty.BOTTOM_RIGHT, DoorPartProperty.BOTTOM_RIGHT_OPEN_EXT);
        map.put(DoorPartProperty.RIGHT, DoorPartProperty.RIGHT_OPEN_EXT);
        map.put(DoorPartProperty.TOP_RIGHT, DoorPartProperty.TOP_RIGHT_OPEN_EXT);
        return map;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        OpenType type = !state.getValue(OPEN) ? OpenType.CLOSED : (state.getValue(SWING) ? OpenType.SWING : OpenType.SLIDE);
        return shapeMap.get(type).get(state.getValue(FACING)).get(state.getValue(PART));
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
            BlockPos partPos = blockPos.relative(facing.getCounterClockWise(), part.xOffset()).above(part.yOffset());
            isAreaClear = level.getBlockState(partPos).canBeReplaced(context);
            if (!isAreaClear) break;
        }
        if (blockPos.getY() < level.getMaxBuildHeight() - 2 && isAreaClear) {
            boolean powered = false;
            for (DoorPartProperty part : DoorPartProperty.values()) {
                BlockPos partPos = blockPos.relative(facing.getCounterClockWise(), part.xOffset()).above(part.yOffset());
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
        Direction facing = controllerState.getValue(FACING);
        boolean swing = controllerState.getValue(SWING);
        boolean open = controllerState.getValue(OPEN);

        toggleAllParts(level, controllerPos, facing, swing, open, player, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    public boolean isOpen(BlockState blockState) {
        return blockState.getValue(OPEN);
    }

    public void setOpen(@Nullable Entity entity, Level level, BlockPos pos, BlockState state, boolean open) {
        if (!state.is(this)) return;
        BlockPos controllerPos = getController(state, pos);
        BlockState controller = level.getBlockState(controllerPos);
        if (!controller.is(this)) return;
        if (controller.getValue(OPEN) == open) return;

        Direction facing = controller.getValue(FACING);
        boolean swing = controller.getValue(SWING);
        toggleAllParts(level, controllerPos, facing, swing, open, entity, pos);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction facing = state.getValue(FACING);
        for (DoorPartProperty part : DoorPartProperty.values()) {
            if (!part.isExtension()) {
                BlockPos partPos = pos.relative(facing.getCounterClockWise(), part.xOffset()).above(part.yOffset());
                level.setBlock(partPos, state.setValue(PART, part), Block.UPDATE_CLIENTS);
            } else if (state.getValue(POWERED) || state.getValue(OPEN)) {
                boolean swing = state.getValue(SWING);
                BlockPos newPartPos = swing ?
                        pos.relative(facing.getCounterClockWise(), part.xOffset())
                                .relative(facing.getOpposite(), part.zOffset())
                                .above(part.yOffset()) :
                        pos.relative(facing.getCounterClockWise(), part.xOffset() * 2).above(part.yOffset());
                BlockState newPartState = this.defaultBlockState()
                        .setValue(FACING, facing)
                        .setValue(OPEN, true)
                        .setValue(LOCKED, false)
                        .setValue(POWERED, false)
                        .setValue(SWING, swing)
                        .setValue(PART, part);
                if (level.getBlockState(newPartPos).isAir()) level.setBlock(newPartPos, newPartState, Block.UPDATE_ALL);
            }
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        BlockPos controllerPos = getController(state, pos);
        BlockState controller = level.getBlockState(controllerPos);
        if (!controller.is(this)) return;

        Direction facing = controller.getValue(FACING);
        Direction left = facing.getCounterClockWise();
        final boolean[] anyPowered = {false};
        forEachBasePart(part -> {
            if (!anyPowered[0]) {
                BlockPos p = controllerPos.relative(left, part.xOffset()).above(part.yOffset());
                if (level.hasNeighborSignal(p)) {
                    anyPowered[0] = true;
                }
            }
        });

        boolean oldPowered = controller.getValue(POWERED);
        if (anyPowered[0] == oldPowered) return; // no change

        boolean oldOpen = controller.getValue(OPEN);
        if (anyPowered[0] != oldOpen) {
            playSound(null, level, pos, anyPowered[0]);
            level.gameEvent(null, anyPowered[0] ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }

        forEachAllParts(part -> {
            BlockPos partPos = partPos(part, controller.getValue(SWING), facing, controllerPos);
            BlockState partState = level.getBlockState(partPos);
            if (partState.getBlock() instanceof DoubleDoorBlock && !part.isExtension()) {
                level.setBlock(partPos,
                        partState.setValue(POWERED, anyPowered[0]).setValue(OPEN, anyPowered[0]),
                        Block.UPDATE_CLIENTS);
            } else if (part.isExtension() && !anyPowered[0]) {
                // remove extension geometry when closing
                if (partState.is(this)) {
                    level.setBlock(partPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                }
            }
        });
        if (neighborBlock instanceof PistonHeadBlock || neighborBlock instanceof MovingPistonBlock) {
            // TODO: If a piston is pushing the door, we need to check if the door can still survive
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
                tooltipComponents.add(Component.translatable(DifferentDoors.MOD_ID + ".tooltip.double_door.swinging").withStyle(ChatFormatting.GRAY));
            } else {
                tooltipComponents.add(Component.translatable(DifferentDoors.MOD_ID + ".tooltip.double_door.sliding").withStyle(ChatFormatting.GRAY));
            }
        }
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        ItemStack stack = super.getCloneItemStack(level, pos, state);
        stack.set(DataComponents.BLOCK_STATE, stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY).with(DoubleDoorBlock.SWING, state.getValue(SWING)));
        return stack;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        DoorPartProperty part = state.getValue(PART);
        if (part.isExtension()) {
            return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        }

        Direction facing = state.getValue(FACING);
        // Vertical stability
        if (direction.getAxis() == Direction.Axis.Y) {
            if (direction == Direction.DOWN) {
                if (part.yOffset() == 0) {
                    // Base layer: ensure at least one bottom support remains
                    BlockPos controllerPos = getController(state, pos);
                    BlockState controllerState = level.getBlockState(controllerPos);
                    BlockPos blPos = controllerPos.relative(facing.getCounterClockWise(), DoorPartProperty.BOTTOM_LEFT.xOffset())
                            .above(DoorPartProperty.BOTTOM_LEFT.yOffset());
                    BlockPos brPos = controllerPos.relative(facing.getCounterClockWise(), DoorPartProperty.BOTTOM_RIGHT.xOffset())
                            .above(DoorPartProperty.BOTTOM_RIGHT.yOffset());
                    if (!canSurvive(level.getBlockState(blPos), level, blPos) &&
                            !canSurvive(controllerState, level, controllerPos) &&
                            !canSurvive(level.getBlockState(brPos), level, brPos)) {
                        if (level instanceof Level realLevel) destroy(realLevel, pos, state, true, null);
                        return Blocks.AIR.defaultBlockState();
                    }
                } else {
                    if (!canSurvive(state, level, pos)) return Blocks.AIR.defaultBlockState();
                }
            } else if (direction == Direction.UP) {
                if (part.yOffset() != 2 && !level.getBlockState(pos.above()).is(this)) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
            return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        }

        // Horizontal integrity check: ensure neighboring parts remain
        int xOffset = part.xOffset();
        Direction left = facing.getCounterClockWise();
        Direction right = facing.getClockWise();

        if (xOffset == -1 && direction == left) {
            if (!level.getBlockState(pos.relative(left)).is(this)) return Blocks.AIR.defaultBlockState();
        } else if (xOffset == 1 && direction == right) {
            if (!level.getBlockState(pos.relative(right)).is(this)) return Blocks.AIR.defaultBlockState();
        } else if (xOffset == 0) {
            if (direction == left && !level.getBlockState(pos.relative(left)).is(this)) return Blocks.AIR.defaultBlockState();
            if (direction == right && !level.getBlockState(pos.relative(right)).is(this)) return Blocks.AIR.defaultBlockState();
        }

        // Handle extension spawn if opened
        if (state.getValue(OPEN) && extensionMap.containsKey(part)) {
            boolean swing = state.getValue(SWING);
            DoorPartProperty extPart = extensionMap.get(part);
            BlockPos extSpawnPos = extensionPosForUpdate(part, swing, facing, pos);
            if (level.getBlockState(extSpawnPos).isAir()) {
                BlockState extState = newExtensionState(facing, swing, extPart);
                level.setBlock(extSpawnPos, extState, 10);
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
        Direction facing = controllerState.getValue(FACING);
        boolean wasSwing = controllerState.getValue(SWING);
        if (wasSwing == swing) return; // no change

        // Update base parts
        forEachAllParts(part -> {
            if (!part.isExtension()) {
                BlockPos partPos = partPos(part, wasSwing, facing, controllerPos);
                BlockState partState = level.getBlockState(partPos);
                if (partState.is(this)) {
                    level.setBlockAndUpdate(partPos, partState.setValue(SWING, swing));
                }
            }
        });

        // Rebuild extension geometry only if door is open
        if (controllerState.getValue(OPEN)) {
            for (DoorPartProperty part : ALL_PARTS) {
                if (!part.isExtension()) continue;
                // Remove old extension
                BlockPos oldPos = partPos(part, wasSwing, facing, controllerPos);
                if (level.getBlockState(oldPos).is(this)) {
                    level.setBlock(oldPos, Blocks.AIR.defaultBlockState(), 10);
                }
                // Add new extension
                BlockPos newPos = partPos(part, swing, facing, controllerPos);
                if (level.getBlockState(newPos).isAir()) {
                    level.setBlockAndUpdate(newPos, newExtensionState(facing, swing, part));
                }
            }
        }
        playSetSwingSound(level, controllerPos, swing);
    }

    private void destroy(Level level, BlockPos pos, BlockState state, boolean dropBlock, @Nullable Player player) {
        if (!state.is(this)) return;
        BlockPos controllerPos = getController(state, pos);
        BlockState controllerState = level.getBlockState(controllerPos);

        if (dropBlock) Block.dropResources(controllerState, level, controllerPos);

        level.setBlock(controllerPos, Blocks.AIR.defaultBlockState(), 35);
        if (state.getValue(OPEN)) {
            if (controllerState.is(this)) {
                Direction facing = controllerState.getValue(FACING);
                boolean swing = controllerState.getValue(SWING);
                for (DoorPartProperty part : DoorPartProperty.values()) {
                    if (part.isExtension()) {
                        BlockPos partPos = swing
                            ? controllerPos.relative(facing.getCounterClockWise(), part.xOffset())
                                          .relative(facing.getOpposite(), part.zOffset())
                                          .above(part.yOffset())
                            : controllerPos.relative(facing.getCounterClockWise(), part.xOffset() * 2)
                                          .above(part.yOffset());
                        if (level.getBlockState(partPos).is(this)) {
                            level.setBlock(partPos, Blocks.AIR.defaultBlockState(), 35);
                            level.levelEvent(player, 2001, partPos, Block.getId(level.getBlockState(partPos)));
                        }
                    }
                }
            }
        }
        if (player != null) level.levelEvent(player, 2001, controllerPos, Block.getId(controllerState));
    }

    public static BlockPos getController(BlockState state, BlockPos pos) {
        DoorPartProperty part = state.getValue(PART);
        Direction direction = state.getValue(FACING).getClockWise();
        return state.getValue(SWING) ? pos.relative(direction.getOpposite(), -part.xOffset())
                .relative(state.getValue(FACING), part.zOffset())
                .below(part.yOffset()) :
                pos.relative(direction.getOpposite(), part.isExtension() ? -part.xOffset() * 2 : -part.xOffset())
                        .below(part.yOffset());
    }

    private static BlockPos extensionPosForUpdate(DoorPartProperty basePart, boolean swing, Direction facing, BlockPos basePos) {
        if (basePart == DoorPartProperty.BOTTOM_LEFT || basePart == DoorPartProperty.LEFT || basePart == DoorPartProperty.TOP_LEFT) {
            return swing ? basePos.relative(facing.getOpposite()) : basePos.relative(facing.getClockWise());
        } else {
            return swing ? basePos.relative(facing.getOpposite()) : basePos.relative(facing.getCounterClockWise());
        }
    }

    private static BlockPos partPos(DoorPartProperty part, boolean swing, Direction facing, BlockPos controller) {
        Direction left = facing.getCounterClockWise();
        if (swing) {
            return controller.relative(left, part.xOffset())
                    .relative(facing.getOpposite(), part.zOffset())
                    .above(part.yOffset());
        } else {
            int xOffset = part.isExtension() ? part.xOffset() * 2 : part.xOffset();
            return controller.relative(left, xOffset).above(part.yOffset());
        }
    }

    private void updatePartBlock(Level level, Direction facing, boolean swing, boolean open, DoorPartProperty part, BlockPos partPos, BlockState partState) {
        if (open) {
            if (part.isExtension()) {
                if (partState.is(this) && partState.getValue(PART).isExtension()) {
                    level.setBlock(partPos, Blocks.AIR.defaultBlockState(), 10);
                }
            } else if (partState.getBlock() instanceof DoubleDoorBlock) {
                level.setBlock(partPos, partState.cycle(OPEN), 10);
            }
        } else {
            if (part.isExtension()) {
                if (partState.isAir()) {
                    BlockState newPartState = this.defaultBlockState()
                            .setValue(FACING, facing)
                            .setValue(OPEN, true)
                            .setValue(LOCKED, false)
                            .setValue(POWERED, false)
                            .setValue(SWING, swing)
                            .setValue(PART, part);
                    level.setBlock(partPos, newPartState, Block.UPDATE_CLIENTS);
                }
            } else if (partState.getBlock() instanceof DoubleDoorBlock) {
                level.setBlock(partPos, partState.cycle(OPEN), 10);
            }
        }
    }

    private static void forEachBasePart(Consumer<DoorPartProperty> consumer) {
        for (DoorPartProperty p : ALL_PARTS) {
            if (!p.isExtension()) consumer.accept(p);
        }
    }

    private static void forEachAllParts(Consumer<DoorPartProperty> consumer) {
        for (DoorPartProperty p : ALL_PARTS) consumer.accept(p);
    }

    private void toggleAllParts(Level level, BlockPos controllerPos, Direction facing, boolean swing, boolean targetOpen, @Nullable Entity source, BlockPos soundPos) {
        forEachAllParts(part -> {
            BlockPos partPos = partPos(part, swing, facing, controllerPos);
            BlockState partState = level.getBlockState(partPos);
            updatePartBlock(level, facing, swing, targetOpen, part, partPos, partState);
        });
        playSound(source, level, soundPos, targetOpen);
        level.gameEvent(source, targetOpen ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, soundPos);
    }

    private BlockState newExtensionState(Direction facing, boolean swing, DoorPartProperty extPart) {
        return defaultBlockState()
                .setValue(FACING, facing)
                .setValue(OPEN, true)
                .setValue(LOCKED, false)
                .setValue(POWERED, false)
                .setValue(SWING, swing)
                .setValue(PART, extPart);
    }

    public void playSetSwingSound(Level level, BlockPos blockPos, boolean swing) {
        level.playSound(null, blockPos, swing ? doorMaterialType.getAddSwingSound() : doorMaterialType.getRemoveSwingSound(), SoundSource.BLOCKS, 0.5f, 1);
    }

    public void playSound(@Nullable Entity entity, Level level, BlockPos blockPos, boolean isOpen) {
        level.playSound(entity, blockPos, isOpen ? doorMaterialType.getOpenSound() : doorMaterialType.getCloseSound(), SoundSource.BLOCKS, 0.5f, 1);
    }
}
