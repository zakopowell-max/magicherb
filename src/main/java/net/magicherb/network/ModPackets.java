package net.magicherb.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.magicherb.MagicHerbMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class ModPackets {

    public record SmokePayload(double x, double y, double z) implements CustomPacketPayload {

        public static final CustomPacketPayload.Type<SmokePayload> ID =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "smoke"));

        public static final StreamCodec<RegistryFriendlyByteBuf, SmokePayload> CODEC =
            StreamCodec.of(
                (buf, p) -> { buf.writeDouble(p.x); buf.writeDouble(p.y); buf.writeDouble(p.z); },
                buf -> new SmokePayload(buf.readDouble(), buf.readDouble(), buf.readDouble())
            );

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return ID;
        }
    }

    public static void registerServer() {
        PayloadTypeRegistry.clientboundPlay().register(SmokePayload.ID, SmokePayload.CODEC);
    }

    public static void sendSmoke(ServerPlayer source, Vec3 pos) {
        SmokePayload payload = new SmokePayload(pos.x, pos.y + 1.6, pos.z);
        PlayerLookup.around((ServerLevel) source.level(), pos, 24).forEach(p ->
            ServerPlayNetworking.send(p, payload)
        );
    }
}
