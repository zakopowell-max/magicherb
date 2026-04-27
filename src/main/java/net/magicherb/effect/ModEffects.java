package net.magicherb.effect;

import net.magicherb.MagicHerbMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
    public static Holder<MobEffect> BLAZED;

    public static void register() {
        BLAZED = Registry.registerForHolder(
            BuiltInRegistries.MOB_EFFECT,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "blazed"),
            new BlazedEffect()
        );
    }
}
