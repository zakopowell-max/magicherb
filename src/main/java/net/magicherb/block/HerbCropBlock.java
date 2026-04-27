package net.magicherb.block;

import com.mojang.serialization.MapCodec;
import net.magicherb.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class HerbCropBlock extends CropBlock {

    public static final MapCodec<HerbCropBlock> CODEC = simpleCodec(HerbCropBlock::new);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public HerbCropBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<HerbCropBlock> codec() {
        return CODEC;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.HERB_SEEDS;
    }
}
