package net.magicherb.item;

import net.magicherb.ModAdvancements;
import net.magicherb.ModConfig;
import net.magicherb.effect.ModEffects;
import net.magicherb.network.ModPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

import java.util.List;

public class JointItem extends Item {

    public JointItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (!(level instanceof ServerLevel serverLevel) || !(user instanceof ServerPlayer player)) return stack;

        boolean badTrip = Math.random() < ModConfig.INSTANCE.bad_trip_chance;

        applyEffects(player, 1.0f, badTrip);

        List<ServerPlayer> nearby = serverLevel.getEntitiesOfClass(
            ServerPlayer.class,
            player.getBoundingBox().inflate(ModConfig.INSTANCE.joint_aoe_range),
            p -> p != player && !p.isSpectator()
        );
        nearby.forEach(p -> applyEffects(p, 0.5f, false));

        ModPackets.sendSmoke(player, player.position());
        level.playSound(null, player.blockPosition(),
            SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.3f, 1.6f);

        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

        ModAdvancements.grant(player, "first_smoke");
        if (!nearby.isEmpty()) ModAdvancements.grant(player, "sharing_is_caring");
        if (badTrip)           ModAdvancements.grant(player, "bad_trip");

        return stack;
    }

    private void applyEffects(ServerPlayer player, float potency, boolean badTrip) {
        ModConfig cfg = ModConfig.INSTANCE;
        int base = cfg.joint_effect_base_duration;
        int dur  = Math.round(base * potency);

        if (badTrip) {
            player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, Math.round(300 * potency), 0, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,  Math.round(200 * potency), 0, false, true));
        } else {
            player.addEffect(new MobEffectInstance(ModEffects.BLAZED,       Math.round(base * 1.5f * potency), 0, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS,     dur,     0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, dur / 2, 0, false, false));
        }
        player.getFoodData().addExhaustion(cfg.hunger_exhaustion * potency);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return ModConfig.INSTANCE.joint_use_ticks;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.EAT;
    }
}
