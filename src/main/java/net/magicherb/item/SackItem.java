package net.magicherb.item;

import net.magicherb.block.StashMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class SackItem extends Item {

    public SackItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (!(level instanceof ServerLevel)) return InteractionResult.SUCCESS;
        ItemStack sack = player.getItemInHand(hand);
        SackContainer container = new SackContainer(sack);
        player.openMenu(new SimpleMenuProvider(
            (syncId, inv, p) -> new StashMenu(syncId, inv, container),
            Component.translatable("container.magicherb.stash")
        ));
        return InteractionResult.SUCCESS;
    }

    private static final class SackContainer extends SimpleContainer {
        private final ItemStack sack;

        SackContainer(ItemStack sack) {
            super(9);
            this.sack = sack;
            List<ItemStack> items = sack.getOrDefault(ModItems.SACK_CONTENTS, List.of());
            for (int i = 0; i < Math.min(items.size(), 9); i++) {
                setItem(i, items.get(i).copy());
            }
        }

        @Override
        public void setChanged() {
            super.setChanged();
            List<ItemStack> items = new ArrayList<>(9);
            for (int i = 0; i < getContainerSize(); i++) {
                items.add(getItem(i).copy());
            }
            sack.set(ModItems.SACK_CONTENTS, List.copyOf(items));
        }
    }
}
