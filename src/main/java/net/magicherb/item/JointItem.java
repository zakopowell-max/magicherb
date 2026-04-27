package net.magicherb.item;

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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class JointItem extends Item {

    private static final int USE_TICKS = 32;
    private static final double AOE_RANGE = 4.0;

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
        if (level instanceof ServerLevel serverLevel && user instanceof ServerPlayer player) {
            applyEffects(player, 1.0f);

            serverLevel.getEntitiesOfClass(
                ServerPlayer.class,
                player.getBoundingBox().inflate(AOE_RANGE),
                p -> p != player && !p.isSpectator()
            ).forEach(nearby -> applyEffects(nearby, 0.5f));

            ModPackets.sendSmoke(player, player.position());
            level.playSound(null, player.blockPosition(),
                SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.3f, 1.6f);

            stack.shrink(1);
        }
        return stack;
    }

    private void applyEffects(ServerPlayer player, float potency) {
        int dur = Math.round(600 * potency);
        player.addEffect(new MobEffectInstance(ModEffects.BLAZED,   Math.round(900 * potency), 0, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS,     dur,     0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, dur / 2, 0, false, false));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return USE_TICKS;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.EAT;
    }
}
