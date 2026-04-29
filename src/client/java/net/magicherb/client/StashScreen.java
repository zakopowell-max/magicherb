package net.magicherb.client;

import net.magicherb.MagicHerbMod;
import net.magicherb.block.StashMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class StashScreen extends AbstractContainerScreen<StashMenu> {

    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, "textures/gui/stash.png");

    public StashScreen(StashMenu menu, Inventory inv, Component title) {
        super(menu, inv, title, 176, 133);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractContents(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        extractor.blit(TEXTURE, leftPos, topPos, imageWidth, imageHeight, 0f, 0f, 1f, 1f);
        super.extractContents(extractor, mouseX, mouseY, partialTick);
    }
}
