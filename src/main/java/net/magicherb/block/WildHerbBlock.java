package net.magicherb.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WildHerbBlock extends BushBlock {

    public WildHerbBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<BushBlock> codec() {
        return BushBlock.CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(Blocks.GRASS_BLOCK)
            || floor.is(Blocks.DIRT)
            || floor.is(Blocks.COARSE_DIRT)
            || floor.is(Blocks.PODZOL)
            || floor.is(Blocks.ROOTED_DIRT)
            || floor.is(Blocks.MOSS_BLOCK)
            || floor.is(Blocks.MUD);
    }
}
