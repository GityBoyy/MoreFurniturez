package org.chubby.morefurniturez.common.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mrcrayfish.furniture.refurbished.block.ElectricityGeneratorBlock;
import com.mrcrayfish.furniture.refurbished.block.FurnitureHorizontalBlock;
import com.mrcrayfish.furniture.refurbished.block.FurnitureHorizontalEntityBlock;
import com.mrcrayfish.furniture.refurbished.block.MetalType;
import com.mrcrayfish.furniture.refurbished.blockentity.ElectricityGeneratorBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import com.mrcrayfish.furniture.refurbished.util.VoxelShapeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.chubby.morefurniturez.common.blockentities.SolarPanelBlockEntity;
import org.chubby.morefurniturez.common.init.BlockEntityInit;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class SolarPanelBlock extends FurnitureHorizontalEntityBlock implements BlockTagSupplier {
    private static final MapCodec<SolarPanelBlock> CODEC = RecordCodecBuilder.mapCodec((builder) -> {
        return builder.group(MetalType.CODEC.fieldOf("metal_type").forGetter((block) -> {
            return block.type;
        }), propertiesCodec()).apply(builder, SolarPanelBlock::new);
    });
    private final MetalType type;

    public SolarPanelBlock(MetalType type ,Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH).setValue(POWERED, false));
        this.type = type;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states) {
        VoxelShape base = Block.box(0.0F,0.0F,0.0F,16.0F,16.0F,16.0F);
        VoxelShape rod = Shapes.box(0.0f,0.0f,0.0f,2.0f,8.0f,2.0f);
        VoxelShape solar_panel_combined = VoxelShapeHelper.combine(List.of(base,rod));
        ImmutableMap.Builder<BlockState, VoxelShape> builder = new ImmutableMap.Builder<>();

        BlockState state;
        VoxelShape rotatedSolarPanel;
        for(UnmodifiableIterator<BlockState> var10 = states.iterator(); var10.hasNext(); builder.put(state, rotatedSolarPanel)) {
            state = var10.next();
            Direction direction = state.getValue(DIRECTION);
            rotatedSolarPanel = VoxelShapeHelper.rotateHorizontally(solar_panel_combined, direction);
        }

        return builder.build();
    }


    @Override
    public List<TagKey<Block>> getTags() {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE,BlockTags.NEEDS_IRON_TOOL);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ?
                createTicker(pBlockEntityType, BlockEntityInit.SOLAR_PANEL.get(),SolarPanelBlockEntity::clientTick):null;

    }
}
