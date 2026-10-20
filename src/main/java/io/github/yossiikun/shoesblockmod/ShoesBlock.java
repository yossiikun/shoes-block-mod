package io.github.yossiikun.shoesblockmod;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** A collision and outline shape matching the two shoe models. */
public class ShoesBlock extends Block {
    public static final MapCodec<ShoesBlock> CODEC = simpleCodec(ShoesBlock::new);
    public static final BooleanProperty ON_BOTTOM_SLAB = BooleanProperty.create("on_bottom_slab");
    private static final VoxelShape SHAPE = createShape();

    public ShoesBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(ON_BOTTOM_SLAB, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ON_BOTTOM_SLAB);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState support = context.getLevel().getBlockState(context.getClickedPos());
        boolean onBottomSlab = context.getClickedFace() == Direction.UP
                && support.getBlock() instanceof SlabBlock
                && support.getValue(SlabBlock.TYPE) == SlabType.BOTTOM;
        return defaultBlockState().setValue(ON_BOTTOM_SLAB, onBottomSlab);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                        CollisionContext context) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return shapeFor(state);
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return shapeFor(state);
    }

    private static VoxelShape shapeFor(BlockState state) {
        return state.getValue(ON_BOTTOM_SLAB) ? SHAPE.move(0, -0.5, 0) : SHAPE;
    }

    private static VoxelShape createShape() {
        VoxelShape shape = Shapes.empty();

        // Left shoe, matching the cuboids in the block model (coordinates in pixels).
        shape = addBox(shape, 3, 0, 4, 6, 0.3, 10);
        shape = addBox(shape, 3, 0.3, 4, 4, 1.3, 10);
        shape = addBox(shape, 4, 0.3, 4, 5, 1.3, 5);
        shape = addBox(shape, 4, 0.3, 9, 5, 1.3, 10);
        shape = addBox(shape, 5, 0.3, 4, 6, 1.3, 10);
        shape = addBox(shape, 3, 2.3, 4, 6, 3.3, 5);
        shape = addBox(shape, 3, 1.3, 4, 6, 2.3, 5);
        shape = addBox(shape, 3, 1.3, 5, 4, 2.3, 7);
        shape = addBox(shape, 3, 2.3, 5, 4, 3.3, 7);
        shape = addBox(shape, 5, 1.3, 5, 6, 2.3, 7);
        shape = addBox(shape, 5, 2.3, 5, 6, 3.3, 7);
        shape = addBox(shape, 3, 1.3, 7, 6, 2.3, 10);
        shape = addBox(shape, 3.25, 2.3, 7, 5.75, 2.8, 7.7);
        shape = addBox(shape, 3.25, 2.3, 8, 5.75, 2.8, 8.7);

        // Right shoe.
        shape = addBox(shape, 10, 0, 4, 13, 0.3, 10);
        shape = addBox(shape, 10, 0.3, 4, 11, 1.3, 10);
        shape = addBox(shape, 11, 0.3, 4, 12, 1.3, 5);
        shape = addBox(shape, 11, 0.3, 9, 12, 1.3, 10);
        shape = addBox(shape, 12, 0.3, 4, 13, 1.3, 10);
        shape = addBox(shape, 10, 2.3, 4, 13, 3.3, 5);
        shape = addBox(shape, 10, 1.3, 4, 13, 2.3, 5);
        shape = addBox(shape, 10, 1.3, 5, 11, 2.3, 7);
        shape = addBox(shape, 10, 2.3, 5, 11, 3.3, 7);
        shape = addBox(shape, 12, 1.3, 5, 13, 2.3, 7);
        shape = addBox(shape, 12, 2.3, 5, 13, 3.3, 7);
        shape = addBox(shape, 10, 1.3, 7, 13, 2.3, 10);
        shape = addBox(shape, 10.25, 2.3, 7, 12.75, 2.8, 7.7);
        shape = addBox(shape, 10.25, 2.3, 8, 12.75, 2.8, 8.7);

        return shape;
    }

    private static VoxelShape addBox(VoxelShape shape, double x1, double y1, double z1,
                                     double x2, double y2, double z2) {
        return Shapes.or(shape, Block.box(x1, y1, z1, x2, y2, z2));
    }
}
