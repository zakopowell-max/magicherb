package net.magicherb.item;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.magicherb.MagicHerbMod;
import net.magicherb.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final Item MAGIC_HERB = register("magic_herb",
        new Item(props("magic_herb").stacksTo(64)));

    public static final Item DRIED_HERB = register("dried_herb",
        new Item(props("dried_herb").stacksTo(64)));

    public static final Item HERB_SEEDS = register("herb_seeds",
        new BlockItem(ModBlocks.HERB_CROP, props("herb_seeds").stacksTo(64)));

    public static final Item PIPE = register("pipe",
        new PipeItem(props("pipe").stacksTo(1).durability(64)));

    public static final Item JOINT = register("joint",
        new JointItem(props("joint").stacksTo(1).durability(10)));

    private static Item.Properties props(String name) {
        return new Item.Properties().setId(
            ResourceKey.create(Registries.ITEM,
                Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name)));
    }

    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
            Identifier.fromNamespaceAndPath(MagicHerbMod.MOD_ID, name), item);
    }

    public static void register() {
        ResourceKey<CreativeModeTab> natural = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            Identifier.withDefaultNamespace("natural"));
        CreativeModeTabEvents.modifyOutputEvent(natural).register(output -> {
            output.prepend(MAGIC_HERB);
            output.prepend(DRIED_HERB);
            output.prepend(HERB_SEEDS);
        });

        ResourceKey<CreativeModeTab> tools = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
            Identifier.withDefaultNamespace("tools"));
        CreativeModeTabEvents.modifyOutputEvent(tools).register(output -> {
            output.prepend(PIPE);
            output.prepend(JOINT);
        });
    }
}
