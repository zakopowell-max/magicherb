package net.magicherb.block;

import net.magicherb.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class StashMenu extends AbstractContainerMenu {

    private final Container container;

    // Client-side: called by ExtendedMenuType factory with block pos
    public StashMenu(int syncId, Inventory playerInv, BlockPos pos) {
        this(syncId, playerInv, new SimpleContainer(9));
    }

    // Server-side: called by StashBlockEntity.createMenu
    public StashMenu(int syncId, Inventory playerInv, Container container) {
        super(ModMenus.STASH, syncId);
        this.container = container;

        for (int i = 0; i < 9; i++) {
            final int slot = i;
            addSlot(new Slot(container, slot, 8 + i * 18, 18) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(ModItems.JOINT);
                }
            });
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 51 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInv, col, 8 + col * 18, 109));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < 9) {
                if (!moveItemStackTo(stack, 9, slots.size(), true)) return ItemStack.EMPTY;
            } else {
                if (!stack.is(ModItems.JOINT)) return ItemStack.EMPTY;
                if (!moveItemStackTo(stack, 0, 9, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return result;
    }
}
