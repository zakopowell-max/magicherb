package net.magicherb;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.magicherb.block.ModMenus;
import net.magicherb.client.StashScreen;
import net.magicherb.network.ModPackets;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import java.util.concurrent.ThreadLocalRandom;

public class MagicHerbModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MenuScreens.register(ModMenus.STASH, StashScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SmokePayload.ID, (payload, context) ->
            context.client().execute(() -> {
                ClientLevel level = context.client().level;
                if (level == null) return;

                var rand = ThreadLocalRandom.current();
                for (int i = 0; i < 10; i++) {
                    level.addParticle(
                        ParticleTypes.LARGE_SMOKE,
                        payload.x() + rand.nextDouble() * 0.6 - 0.3,
                        payload.y() + rand.nextDouble() * 0.4,
                        payload.z() + rand.nextDouble() * 0.6 - 0.3,
                        rand.nextDouble() * 0.06 - 0.03,
                        rand.nextDouble() * 0.1 + 0.05,
                        rand.nextDouble() * 0.06 - 0.03
                    );
                }
            })
        );
    }
}
