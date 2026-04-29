package net.magicherb.block;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.magicherb.MagicHerbMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {

    public static final MenuType<StashMenu> STASH = Registry.register(
        BuiltInRegistries.MENU,
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "stash"),
        new ExtendedMenuType<>(
            (syncId, inv, pos) -> new StashMenu(syncId, inv, pos),
            BlockPos.STREAM_CODEC.cast()
        )
    );

    public static void register() {}
}
