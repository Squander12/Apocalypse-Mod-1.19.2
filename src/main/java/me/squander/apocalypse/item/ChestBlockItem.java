package me.squander.apocalypse.item;

import me.squander.apocalypse.client.model.ChestItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class ChestBlockItem extends BlockItem {
    public ChestBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            final Minecraft minecraft = Minecraft.getInstance();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new ChestItemRenderer<>(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
            }
        });
    }
}
