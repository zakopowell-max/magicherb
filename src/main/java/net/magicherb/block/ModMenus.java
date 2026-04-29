package net.magicherb.block;

import net.magicherb.MagicHerbMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.SimpleContainer;

public class ModMenus {

    public static final MenuType<StashMenu> STASH = Registry.register(
        BuiltInRegistries.MENU,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "stash"),
        new MenuType<>(
            (syncId, inv) -> new StashMenu(syncId, inv, new SimpleContainer(9)),
            FeatureFlags.DEFAULT_FLAGS
        )
    );

    public static void register() {}
}
