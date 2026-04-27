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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;

public class PipeItem extends Item {

    private static final int USE_TICKS = 40;
    private static final double AOE_RANGE = 5.0;

    public PipeItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack offhand = player.getOffhandItem();
        if (isHerb(offhand)) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        if (!(level instanceof ServerLevel serverLevel) || !(user instanceof ServerPlayer player)) return stack;

        ItemStack offhand = player.getOffhandItem();
        if (!isHerb(offhand)) return stack;

        boolean dried = offhand.is(ModItems.DRIED_HERB);
        offhand.shrink(1);

        applyEffects(player, dried, 1.0f);

        serverLevel.getEntitiesOfClass(
            ServerPlayer.class,
            player.getBoundingBox().inflate(AOE_RANGE),
            p -> p != player && !p.isSpectator()
        ).forEach(nearby -> applyEffects(nearby, dried, 0.5f));

        stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

        ModPackets.sendSmoke(player, player.position());
        level.playSound(null, player.blockPosition(),
            SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.3f, 1.5f);

        return stack;
    }

    private void applyEffects(ServerPlayer player, boolean dried, float potency) {
        int base = dried ? 1200 : 600;
        int dur = Math.round(base * potency);
        player.addEffect(new MobEffectInstance(ModEffects.BLAZED, Math.round(base * 1.5f * potency), 0, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS,     dur,     0, false, false));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, dur / 2, 0, false, false));
    }

    private boolean isHerb(ItemStack stack) {
        return stack.is(ModItems.MAGIC_HERB) || stack.is(ModItems.DRIED_HERB);
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
