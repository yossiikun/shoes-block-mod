package io.github.yossiikun.shoesblockmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ShoesBlockmod.MODID)
public class ShoesBlockmod {
    public static final String MODID = "shoesblockmod";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MODID);
    public static final DeferredHolder<Block, Block> ORANGE_SHOES = BLOCKS.register("orangeshoes",
            () -> new ShoesBlock(shoesBlockProperties()));
    public static final DeferredHolder<Block, Block> BLUE_SHOES = BLOCKS.register("blueshoes",
            () -> new ShoesBlock(shoesBlockProperties()));
    public static final DeferredHolder<Block, Block> RED_SHOES = BLOCKS.register("redshoes",
            () -> new ShoesBlock(shoesBlockProperties()));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredHolder<Item, Item> ORANGE_SHOES_ITEM = ITEMS.register("orangeshoes",
            () -> new BlockItem(ORANGE_SHOES.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLUE_SHOES_ITEM = ITEMS.register("blueshoes",
            () -> new BlockItem(BLUE_SHOES.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> RED_SHOES_ITEM = ITEMS.register("redshoes",
            () -> new BlockItem(RED_SHOES.get(), new Item.Properties()));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SHOES_TAB =
            CREATIVE_MODE_TABS.register("shoes", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.shoesblockmod"))
                    .icon(() -> new ItemStack(ORANGE_SHOES_ITEM.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(ORANGE_SHOES_ITEM.get());
                        output.accept(BLUE_SHOES_ITEM.get());
                        output.accept(RED_SHOES_ITEM.get());
                    })
                    .build());

    public ShoesBlockmod(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }

    private static BlockBehaviour.Properties shoesBlockProperties() {
        return BlockBehaviour.Properties.of().strength(0.5F);
    }

}
