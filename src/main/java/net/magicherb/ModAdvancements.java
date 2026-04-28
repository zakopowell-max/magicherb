package net.magicherb;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ModAdvancements {

    public static void grant(ServerPlayer player, String name) {
        var server = ((ServerLevel) player.level()).getServer();
        AdvancementHolder holder = server.getAdvancements().get(
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name));
        if (holder != null) {
            player.getAdvancements().award(holder, "magicherb");
        }
    }
}
