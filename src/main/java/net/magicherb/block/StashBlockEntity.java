package net.magicherb.block;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.magicherb.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class StashBlockEntity extends BaseContainerBlockEntity implements ExtendedMenuProvider<BlockPos> {

    private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

    public StashBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STASH, pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.magicherb.stash");
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInv) {
        return new StashMenu(syncId, playerInv, this);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return stack.is(ModItems.JOINT);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer player) {
        return this.getBlockPos();
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        if (this.level != null) {
            Containers.dropContents(this.level, pos, this);
        }
        super.preRemoveSideEffects(pos, state);
    }
}
