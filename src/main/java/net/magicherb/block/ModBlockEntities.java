package net.magicherb.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.magicherb.MagicHerbMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final BlockEntityType<StashBlockEntity> STASH = Registry.register(
        BuiltInRegistries.BLOCK_ENTITY_TYPE,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "stash"),
        FabricBlockEntityTypeBuilder.create(StashBlockEntity::new, ModBlocks.STASH).build()
    );

    public static void register() {}
}
