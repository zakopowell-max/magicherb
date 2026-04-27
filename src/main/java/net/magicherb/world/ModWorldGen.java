package net.magicherb.world;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.magicherb.MagicHerbMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModWorldGen {

    private static final ResourceKey<PlacedFeature> WILD_HERB_KEY = ResourceKey.create(
        Registries.PLACED_FEATURE,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "wild_herb")
    );

    public static void register() {
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld(),
            GenerationStep.Decoration.VEGETAL_DECORATION,
            WILD_HERB_KEY
        );
    }
}
