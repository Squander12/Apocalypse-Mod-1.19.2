package me.squander.apocalypse.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TradeItem extends Item {
    private final boolean isCraftingItem;

    public TradeItem(boolean isCraftingItem, Properties pProperties) {
        super(pProperties);
        this.isCraftingItem = isCraftingItem;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag isAdvanced) {
        list.add(Component.translatable("tradeItem.lore"));
        if(this.isCraftingItem){
            list.add(Component.translatable("tradeItem.lore_crafting"));
        }
    }
}
